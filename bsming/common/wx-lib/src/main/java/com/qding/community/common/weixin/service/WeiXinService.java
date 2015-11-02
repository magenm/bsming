package com.qding.community.common.weixin.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.jdom.JDOMException;

import com.qding.community.common.weixin.vo.OpenIDList;
import com.qding.community.common.weixin.vo.WXUser;
import com.qding.community.common.weixin.vo.recv.RecvMessage;
import com.qding.community.common.weixin.vo.send.SendMessage;

public interface WeiXinService {

	public boolean access(String token, String signature, String timestamp, String nonce);

	public RecvMessage recv(InputStream in) throws JDOMException, IOException;

	public void send(SendMessage msg, OutputStream out) throws JDOMException, IOException;

	public SendMessage builderSendByRecv(RecvMessage msg);

	public boolean publishMenu(String appid, String secret, String menu);
	
	public boolean removeMenu(String appid, String secret);
	
	public String queryMenu(String appid, String secret);

	public String getToken(String appid, String secret);
	
	public String getQRTicket(String appid, String secret, Long sceneID);

	public String createLimitQRCode(String ticket);

	public String createCodeRequestUrl(String appid, String secret, String url);

	public String createUserInfoCodeRequestUrl(String appid, String secret, String url);

	public String getOpenID(String appid, String secret, String code);

	public WXUser getUserInfo(String appid, String secret, String openID);
	
	public String sendTemplateMessage(String appid, String secret,String content,boolean asynchrony);

	public SendMessage processMessage(SendMessage sendMsg, String url);

	public String sendCustomMessage(String appid, String secret, String content,boolean asynchrony);
	
	
	public String getAccessTokenByCode(String appid, String secret, String code);

	
	/**
	 * 上传媒体消息素材
	 * @param appid
	 * @param secret
	 * @param content
	 * @return 素材ID
	 */
	public String uploadMedia(String appid, String secret, File file,String type);
	
	
	/**
	 * 上传多图文消息，需要先上传媒体消息，获取媒体消息ID
	 * @param appid
	 * @param secret
	 * @param content
	 * @return 素材ID
	 */
	public String uploadNews(String appid, String secret, String content);
	
	
	
	/**
	 * 根据OpenID群发消息
	 * @param appid
	 * @param secret
	 * @param openIDS
	 */
	public void massSendNewsMessageByOpenID(String appid, String secret,String content);
	
	/**
	 * 生成群发图文消息
	 * @param mediaID
	 * @param openIDS
	 * @return
	 */
	
	public String generateMassMPNewsMessage(String mediaID,List<String> openIDS);
	
	/**
	 * 生成群发文本消息
	 * @param mediaID
	 * @param openIDS
	 * @return
	 */
	
	public String generateMassTextMessage(String text,List<String> openIDS);
	
	
	/**
	 * 生成群发语音消息
	 * @param mediaID
	 * @param openIDS
	 * @return
	 */
	
	public String generateMassVoiceMessage(String mediaID,List<String> openIDS);
	
	/**
	 * 生成群发图片消息
	 * @param mediaID
	 * @param openIDS
	 * @return
	 */
	
	public String generateMassImgMessage(String mediaID,List<String> openIDS);
	
	
	public OpenIDList getOpenIDList(String appid, String secret,String next_openid);
	
	
}
