package com.qding.community.common.weixin.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qding.commnunity.common.weixin.service.impl.WeiXinServiceImpl;
import com.qding.community.common.weixin.service.WeiXinService;
import com.qding.community.common.weixin.vo.template.TemplateEntry;
import com.qding.community.common.weixin.vo.template.TemplateMessage;
import com.qding.community.common.weixin.vo.template.message.CSNotificationsChanges;

/**客服消息转换工具 其他消息格式参考 http://mp.weixin.qq.com/wiki/index.php?title=%E5%8F%91%E9%80%81%E5%AE%A2%E6%9C%8D%E6%B6%88%E6%81%AF
 * @author wangchao
 *
 */
public class CustomMessageUtil {
	
	
	private static final Log log = LogFactory.getLog(CustomMessageUtil.class);


	public static String getCustomTextMessageContent(String OPENID,String msg){
		return
				"{"+
				    "\"touser\":\""+OPENID+"\","+
				    "\"msgtype\":\"text\","+
				    "\"text\":"+
				    "{"+
				    "     \"content\":\""+msg+"\""+
				    "}"+
				"}";
	}
	 
}
