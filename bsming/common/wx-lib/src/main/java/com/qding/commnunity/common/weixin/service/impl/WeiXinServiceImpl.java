package com.qding.commnunity.common.weixin.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import com.bsming.common.util.http.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.output.XMLOutputter;

import com.gemantic.common.util.OpenIDListUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.bsming.common.util.json.GsonUtil;
import com.qding.community.common.weixin.parser.WxMsgKit;
import com.qding.community.common.weixin.service.WeiXinService;
import com.qding.community.common.weixin.util.HashUtil;
import com.qding.community.common.weixin.util.WeiXinPropertiesUtil;
import com.qding.community.common.weixin.vo.OpenIDList;
import com.qding.community.common.weixin.vo.WXUser;
import com.qding.community.common.weixin.vo.recv.RecvMessage;
import com.qding.community.common.weixin.vo.send.SendMessage;

public class WeiXinServiceImpl implements WeiXinService {
    private static final Log log = LogFactory.getLog(WeiXinServiceImpl.class);

    private static final Executor exec = Executors.newFixedThreadPool(3);

    public boolean access(String token, String signature, String timestamp, String nonce) {
        List<String> ss = new ArrayList<String>();
        ss.add(timestamp);
        ss.add(nonce);
        ss.add(token);

        Collections.sort(ss);

        StringBuilder builder = new StringBuilder();
        for (String s : ss) {
            builder.append(s);
        }
        return signature.equalsIgnoreCase(HashUtil.sha1(builder.toString()));
    }

    public RecvMessage recv(InputStream in) throws JDOMException, IOException {
        return WxMsgKit.parse(in);
    }

    public void send(SendMessage msg, OutputStream out) throws JDOMException, IOException {
        Document doc = WxMsgKit.parse(msg);
        if (null != doc) {
            new XMLOutputter().output(doc, out);
        } else {
            Logger.getAnonymousLogger().warning("发送消息时,解析出dom为空 msg :" + msg);
        }
    }

    public SendMessage builderSendByRecv(RecvMessage msg) {
        RecvMessage m = new RecvMessage(msg);
        String from = m.getFromUserName();
        m.setFromUserName(m.getToUserName());
        m.setToUserName(from);
        m.setCreateTime((System.currentTimeMillis() / 1000) + "");
        return new SendMessage(m);
    }

    public boolean publishMenu(String appid, String secret, String menu) {
        String accessToken = getToken(appid, secret);

        boolean result = false;

        try {

            log.info(accessToken);
            String menuUrl = WeiXinPropertiesUtil.getProperty("menu");
            menuUrl = menuUrl.replace("{accessToken}", accessToken);
            log.info("menuUrl url is " + menuUrl);

            String httpsresponse = HttpClientUtil.sendPostRequestByJava(menuUrl, menu);
            log.info("generator:" + httpsresponse);
            String errcode = GsonUtil.get("errcode", httpsresponse);

            log.info("errcode is " + errcode);
            if ("0".equals(errcode)) {
                result = true;
            } else {

            }

        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

    public String getToken(String appid, String secret) {
        String url = WeiXinPropertiesUtil.getProperty("token");
        url = url.replace("{appid}", appid);
        url = url.replace("{secret}", secret);
        log.info("token url is " + url);

        String response = HttpClientUtil.sendGetRequest(url, null);
        log.info("getToken:" + response);

        Long start = System.currentTimeMillis();
        log.info("load gson use time " + (System.currentTimeMillis() - start));
        String accessToken = GsonUtil.get("access_token", response);
        return accessToken;
    }

    public String getQRTicket(String appid, String secret, Long sceneID) {
        try {
            if (sceneID > 1000 || sceneID < 0) {
                log.error("id " + appid + " secret " + secret + " sceneID " + sceneID + " is wrong format ");
                return "";
            }

            String accessToken = getToken(appid, secret);

            log.info(accessToken);
            String getQrCodeUrl = WeiXinPropertiesUtil.getProperty("qr");
            getQrCodeUrl = getQrCodeUrl.replace("{accessToken}", accessToken);
            log.info("getQrCodeUrl url is " + getQrCodeUrl);
            String sendData = "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": "
                    + sceneID + "}}}";// 生成二维码的post_data
            String httpsresponse = HttpClientUtil.sendPostRequestByJava(getQrCodeUrl, sendData);
            log.info("generator:" + httpsresponse);
            String ticket = GsonUtil.get("ticket", httpsresponse);
            return ticket;

        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";

    }

    ;

    public String createLimitQRCode(String ticket) {
        if (StringUtils.isBlank(ticket)) {
            return "";
        }

        try {

            String qcImgUrl = WeiXinPropertiesUtil.getProperty("qrImg");

            qcImgUrl = qcImgUrl.replace("{ticket}", ticket);

            return qcImgUrl;

        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";

    }

    public String createUserInfoCodeRequestUrl(String appid, String secret, String url) {
        log.info(appid + " is appid, " + secret + " is secret and url is " + url);

        // local test

        String codeUrl = WeiXinPropertiesUtil.getProperty("userinfo_code");
        codeUrl = codeUrl.replace("{appid}", appid);
        try {
            codeUrl = codeUrl.replace("{redirect_uri}", URLEncoder.encode(url, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("create code url failure " + appid + " is appid, " + secret + " is secret and url is " + url);
            e.printStackTrace();
        }
        log.info("codeUrl url is " + codeUrl);

        return codeUrl;

    }

    public String createCodeRequestUrl(String appid, String secret, String url) {
        log.info(appid + " is appid, " + secret + " is secret and url is " + url);

        // local test

        String codeUrl = WeiXinPropertiesUtil.getProperty("code");
        codeUrl = codeUrl.replace("{appid}", appid);
        try {
            codeUrl = codeUrl.replace("{redirect_uri}", URLEncoder.encode(url, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("create code url failure " + appid + " is appid, " + secret + " is secret and url is " + url);
            e.printStackTrace();
        }
        log.info("codeUrl url is " + codeUrl);

        return codeUrl;

    }

    public WXUser getUserInfo(String appid, String secret, String openID) {
        log.info(appid + " is appid, " + secret + " is secret and openID is " + openID);
        String token = this.getToken(appid, secret);
        String url = WeiXinPropertiesUtil.getProperty("uinfo");
        url = url.replace("{accessToken}", token);
        url = url.replace("{openid}", openID);
        log.info("uinfo url is " + url);

        String response2 = HttpClientUtil.sendGetRequest(url, null);
        log.info("code2TokenUrl:" + response2);

        WXUser user = GsonUtil.fromJson(response2, WXUser.class);

        return user;
    }

    @Override
    public String getOpenID(String appid, String secret, String code) {
        String defaultOpenID = null;

        String response2 = getAccessTokenByCode(appid, secret, code);

        defaultOpenID = GsonUtil.get("openid", response2);
        return defaultOpenID;
    }

    public String getAccessTokenByCode(String appid, String secret, String code) {
        String code2TokenUrl = WeiXinPropertiesUtil.getProperty("code2token");
        code2TokenUrl = code2TokenUrl.replace("{appid}", appid);
        code2TokenUrl = code2TokenUrl.replace("{secret}", secret);
        code2TokenUrl = code2TokenUrl.replace("{code}", code);
        log.info("code2TokenUrl url is " + code2TokenUrl);

        String response2 = HttpClientUtil.sendGetRequest(code2TokenUrl, null);
        log.info("code2TokenUrl:" + response2);
        return response2;
    }

    @Override
    public String sendCustomMessage(final String appid, final String secret, final String content, boolean asynchrony) {
        log.info("appid is " + appid + ",secret=" + secret + ",content is " + content);
        String result = "";
        final String token = this.getToken(appid, secret);
        if (asynchrony) {
            Runnable task = new Runnable() {
                @Override
                public void run() {

                    String url = WeiXinPropertiesUtil.getProperty("custom");
                    url = url.replace("{accessToken}", token);
                    log.info("url is " + url);

                    String httpsresponse = HttpClientUtil.sendPostRequestByJava(url, content);

                    log.info("generator:" + httpsresponse);

                }

            };
            exec.execute(task);
        } else {

            String url = WeiXinPropertiesUtil.getProperty("custom");
            url = url.replace("{accessToken}", token);
            log.info("url is " + url);

            String httpsresponse = HttpClientUtil.sendPostRequestByJava(url, content);

            log.info("generator:" + httpsresponse);
        }

        return result;
    }

    @Override
    public String sendTemplateMessage(String appid, String secret, final String content, boolean asynchrony) {
        log.info(appid + " is appid, " + secret + " is secret and content is " + content);
        String result = "";
        final String token = this.getToken(appid, secret);
        if (asynchrony) {
            Runnable task = new Runnable() {
                @Override
                public void run() {

                    String url = WeiXinPropertiesUtil.getProperty("template");
                    url = url.replace("{accessToken}", token);
                    log.info("url is " + url);
                    String httpsresponse = HttpClientUtil.sendPostRequestByJava(url, content);

                    log.info("generator:" + httpsresponse);
                }

            };
            exec.execute(task);
        } else {

            String url = WeiXinPropertiesUtil.getProperty("template");
            url = url.replace("{accessToken}", token);
            log.info("url is " + url);
            String httpsresponse = HttpClientUtil.sendPostRequestByJava(url, content);

            log.info("generator:" + httpsresponse);
            return httpsresponse;

        }

        return result;
    }

    @Override
    public boolean removeMenu(String appid, String secret) {
        boolean result = false;
        String token = this.getToken(appid, secret);
        String removeMenuUrl = WeiXinPropertiesUtil.getProperty("menu_delete");
        removeMenuUrl = removeMenuUrl.replace("{accessToken}", token);

        log.info("removeMenuUrl url is " + removeMenuUrl);

        String response2 = HttpClientUtil.sendGetRequest(removeMenuUrl, null);
        log.info("removeMenuUrl:" + response2);

        String errcode = GsonUtil.get("errcode", response2);
        log.info("errcode is " + errcode);
        if ("0".equals(errcode)) {
            result = true;
        } else {

        }
        return result;
    }

    @Override
    public String queryMenu(String appid, String secret) {

        String token = this.getToken(appid, secret);
        String queryMenuUrl = WeiXinPropertiesUtil.getProperty("menu_query");
        queryMenuUrl = queryMenuUrl.replace("{accessToken}", token);

        log.info("queryMenuUrl url is " + queryMenuUrl);

        String response2 = HttpClientUtil.sendGetRequest(queryMenuUrl, null);
        log.info("queryMenuUrl:" + response2);

        return response2;
    }

    @Override
    public SendMessage processMessage(SendMessage sendMsg, String url) {
        String response2 = HttpClientUtil.sendGetRequest(url, null);

        return sendMsg;
    }

    @Override
    public String uploadNews(String appid, String secret, String content) {
        try {

            String accessToken = getToken(appid, secret);

            log.info(appid + " and secret " + secret + " get accessToken is " + accessToken);
            String getQrCodeUrl = WeiXinPropertiesUtil.getProperty("upload_news");
            getQrCodeUrl = getQrCodeUrl.replace("{accessToken}", accessToken);
            log.info("upload_news url is " + getQrCodeUrl);

            String httpsresponse = HttpClientUtil.sendPostRequestByJava(getQrCodeUrl, content);
            log.info("generator:" + httpsresponse);
            String media_id = GsonUtil.get("media_id", httpsresponse);
            log.info(appid + " and secret " + secret + " get media_id is " + media_id);
            return media_id;

        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void massSendNewsMessageByOpenID(String appid, String secret, String content) {

        try {

            String accessToken = getToken(appid, secret);

            log.info(accessToken);
            String getQrCodeUrl = WeiXinPropertiesUtil.getProperty("mass");
            getQrCodeUrl = getQrCodeUrl.replace("{accessToken}", accessToken);
            log.info("mass url is " + getQrCodeUrl);
            ;

            log.info(appid + " and secret " + secret + " want send media " + content + " want send data is " + content);

            String httpsresponse = HttpClientUtil.sendPostRequestByJava(getQrCodeUrl, content);
            log.info("generator:" + httpsresponse);

        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public String uploadMedia(String appid, String secret, File file, String type) {
        try {

            String accessToken = getToken(appid, secret);

            log.info(accessToken);
            String upload_media = WeiXinPropertiesUtil.getProperty("upload_media");
            upload_media = upload_media.replace("{accessToken}", accessToken);
            upload_media = upload_media.replace("{type}", accessToken);
            log.info("upload_news url is " + upload_media);

            String httpsresponse = HttpClientUtil.postFile(file, upload_media, "media");

            String media_id = GsonUtil.get("media_id", httpsresponse);
            log.info("appid " + appid + " secret " + secret + " result is " + httpsresponse);
            return media_id;
            /*
			 * = com.qding.common.util.HttpClientUtil.sendPostRequestByJava(
			 * upload_media, content); log.info("generator:" + httpsresponse);
			 * String media_id = GsonUtil.get("media_id", httpsresponse); return
			 * media_id;
			 */

        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String generateMassMPNewsMessage(String mediaID, List<String> openIDS) {

        Gson gson = new GsonBuilder().create();
        String openIDSString = gson.toJson(openIDS);

        String message = WeiXinPropertiesUtil.getProperty("mass_wpnews");
        message = message.replace("{openIDS}", openIDSString);
        message = message.replace("{mediaID}", mediaID);
        return message;
    }

    @Override
    public String generateMassTextMessage(String text, List<String> openIDS) {
        Gson gson = new GsonBuilder().create();
        String openIDSString = gson.toJson(openIDS);

        String message = WeiXinPropertiesUtil.getProperty("mass_text");
        message = message.replace("{openIDS}", openIDSString);
        message = message.replace("{content}", text);
        return message;
    }

    @Override
    public String generateMassVoiceMessage(String mediaID, List<String> openIDS) {
        Gson gson = new GsonBuilder().create();
        String openIDSString = gson.toJson(openIDS);

        String message = WeiXinPropertiesUtil.getProperty("mass_voice");
        message = message.replace("{openIDS}", openIDSString);
        message = message.replace("{mediaID}", mediaID);
        return message;
    }

    @Override
    public String generateMassImgMessage(String mediaID, List<String> openIDS) {
        Gson gson = new GsonBuilder().create();
        String openIDSString = gson.toJson(openIDS);

        String message = WeiXinPropertiesUtil.getProperty("mass_img");
        message = message.replace("{openIDS}", openIDSString);
        message = message.replace("{mediaID}", mediaID);
        return message;
    }

    @Override
    public OpenIDList getOpenIDList(String appid, String secret, String next_openid) {
        try {

            String accessToken = getToken(appid, secret);

            log.info(accessToken);
            String upload_media = WeiXinPropertiesUtil.getProperty("openID_list");
            upload_media = upload_media.replace("{accessToken}", accessToken);
            upload_media = upload_media.replace("{next_openid}", next_openid);
            log.info("upload_news url is " + upload_media);

            String response2 = HttpClientUtil.sendGetRequest(upload_media, null);


            log.info("appid " + appid + " secret " + secret + " result is " + response2);
            OpenIDList list = OpenIDListUtil.parseContent2OpenIDList(response2);
            return list;
			/*
			 * = com.qding.common.util.HttpClientUtil.sendPostRequestByJava(
			 * upload_media, content); log.info("generator:" + httpsresponse);
			 * String media_id = GsonUtil.get("media_id", httpsresponse); return
			 * media_id;
			 */

        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
