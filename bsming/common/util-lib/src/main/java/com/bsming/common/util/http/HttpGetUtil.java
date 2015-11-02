package com.bsming.common.util.http;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.HttpGet;

public class HttpGetUtil {

	public static List<HttpGet> convert2HttpGet(List<String> urls) {
		List<HttpGet> gets=new ArrayList();
		for(String url:urls){
			HttpGet get = new HttpGet(url);
			gets.add(get);
			
		}
		return gets;
	}

}
