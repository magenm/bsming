package com.bsming.common.util.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class GsonUtil {
	
	private static final Log log = LogFactory.getLog(GsonUtil.class);

	public static <T> T fromJson(String json, Class<T> clasz) {
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(json, new TypeToken<T>(){}.getType());
	}

	public static String toJson(Object src) {
		Gson gson = new GsonBuilder().create();
		return gson.toJson(src);
	}

	public static String get(String key, String snapshot) {
		if (StringUtils.isBlank(key) || StringUtils.isBlank(snapshot)) {
			return null;
		}
		Gson gson = new GsonBuilder().create();
		Map<String, String> map = null;
		try {
			map = (Map) gson.fromJson(snapshot, new TypeToken<Map<String, String>>() {
			}.getType());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (MapUtils.isEmpty(map)) {
			return null;
		}
		return map.get(key);
	}

	public static String put(String snapshot, String key, String value) {
		Gson gson = new GsonBuilder().create();
		Map<String, String> map = (Map) gson.fromJson(snapshot, new TypeToken<Map<String, String>>() {
		}.getType());
		map.put(key, value);
		return gson.toJson(map);
	}
	
	
	public static void main(String[] args) {
		Gson gson = new GsonBuilder().create();
		List<Map<String, String>> list=new ArrayList();
		Map<String, String> map=new HashMap();
		map.put("key", "xxxxxxx");
		map.put("value", "xxxxxxx");
		Map<String, String> map2=new HashMap();
		map2.put("key", "退货说明");
		map2.put("value", "zzz");
		list.add(map);
		list.add(map2);
		String content=gson.toJson(list);
		
	
		log.info("content is "+content);
		List<Map<String, String>> maps = (List) gson.fromJson(content, new TypeToken<List<Map<String, String>>>() {
		}.getType());
		
		log.info("map is "+maps);
		
	}
	

}
