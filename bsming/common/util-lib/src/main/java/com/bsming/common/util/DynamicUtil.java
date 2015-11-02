package com.bsming.common.util;

import java.util.HashMap;
import java.util.Map;

public class DynamicUtil {

	 public static String withQuote(String value) {
			
		 if (value!=null) {
			return "\""+value+"\"";
		}else {
			return "\"\"";
		}
	}	
	public static Map<String, Object> convert2brokers(Long id,String name,String mobilePhone,String dateStart,String dateEnd,String brokerType,String isHired,Boolean count) {
		
		@SuppressWarnings("unchecked")
		Map<String, Object> params = new HashMap();
		if (id != null) {
			params.put("t_userId & like ", "\"%" + id + "%\"");
		}
		if (DataUtils.isNotNullOrEmpty(name)) {
			params.put("name & like ", "\"%" + name + "%\"");
		}
		if (DataUtils.isNotNullOrEmpty(mobilePhone)) {
			params.put("mobile & like ", "\"%" + mobilePhone + "%\"");
		}
		if (DataUtils.isNotNullOrEmpty(brokerType)) {
			params.put("broker_type", brokerType);
		}
		if (DataUtils.isNotNullOrEmpty(isHired)) {
			params.put("is_hired", isHired);
		}
		if (DataUtils.isNotNullOrEmpty(dateStart)) {
			params.put("update_at & >= ", dateStart);
		}
		if (DataUtils.isNotNullOrEmpty(dateEnd)) {
			params.put("update_at & <= ", dateEnd);
		}
		
		if (count) {
			params.put("@query", "count(distinct id)");
		} else {
			params.put("@order", "is_hired asc, update_at desc");
			params.put("@query", " distinct  id");
		}
		params.put("@table", "user");

		return params;
	}
}
