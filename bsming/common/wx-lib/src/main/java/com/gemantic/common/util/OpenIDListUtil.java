package com.gemantic.common.util;

import com.google.gson.Gson;
import com.qding.community.common.weixin.vo.OpenIDList;

public class OpenIDListUtil {

	public static OpenIDList parseContent2OpenIDList(String content) {
		Gson gson = new Gson();
		OpenIDList result = gson.fromJson(content, OpenIDList.class);
		return result;
	}

}
