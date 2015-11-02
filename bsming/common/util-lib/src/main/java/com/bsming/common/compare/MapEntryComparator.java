package com.bsming.common.compare;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bsming.common.constant.CommonConstant;

public class MapEntryComparator implements Comparator<Entry> {
	private static Log log = LogFactory.getLog(MapEntryComparator.class);
	private String keyOrValue;
	private String sortType;
	private Class<? extends Comparable> classType;
	private Constructor<? extends Comparable> constructor;
	public MapEntryComparator(String keyOrValue,String sortType,Class<? extends Comparable> classType) throws SecurityException, NoSuchMethodException{
		this.keyOrValue = keyOrValue;
		this.sortType = sortType;
		this.classType = classType;
		this.constructor = classType.getConstructor(new Class[]{String.class});
		
	}
	
	@Override
	public int compare(Entry entry1 , Entry entry2) {
		if(CommonConstant.KEY.equals(keyOrValue)){
			Object key1 = entry1.getKey();
			Object key2 = entry1.getKey();
			try {
				Comparable key1Obj = constructor.newInstance(new Object[]{key1});
				Comparable key2Obj = constructor.newInstance(new Object[]{key2});
				if(CommonConstant.SORT_CRITERIA_ASC.equals(sortType)){
					if(null == key1Obj){
						return 1;
					}
					if(null == key2Obj){
						return -1;
					}
					
					return key1Obj.compareTo(key2Obj);
				}
				else if(CommonConstant.SORT_CRITERIA_DESC.equals(sortType)){
					if(null == key1Obj){
						return 1;
					}
					if(null == key2Obj){
						return -1;
					}
					return key2Obj.compareTo(key1Obj);
				}
			} catch (Exception e) {
				log.error("exception from compare : ",e);
				e.printStackTrace();
			}
		}
		if(CommonConstant.VALUE.equals(keyOrValue)){
			Object value1 = entry1.getValue();
			Object value2 = entry2.getValue();
			try {
				Comparable value1Obj = constructor.newInstance(new Object[]{value1});
				Comparable value2Obj = constructor.newInstance(new Object[]{value2});
				if(CommonConstant.SORT_CRITERIA_ASC.equals(sortType)){
					if(null == value1){
						return 1;
					}
					if(null == value2){
						return -1;
					}
					return new BigDecimal(value1.toString()).compareTo(new BigDecimal(value2.toString()));
				}
				else if(CommonConstant.SORT_CRITERIA_DESC.equals(sortType)){
					if(null == value2){
						return 1;
					}
					if(null == value1){
						return -1;
					}
					return new BigDecimal(value2.toString()).compareTo(new BigDecimal(value1.toString()));
				}
			} catch (Exception e) {
				log.error("exception from compare : ",e);
				e.printStackTrace();
			}
		}
		return 0;
	}

}
