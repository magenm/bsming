package com.bsming.common.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.bsming.common.compare.MapEntryComparator;
import com.bsming.common.constant.NumberConstants;
import com.bsming.common.constant.SignConstant;


public class MyMapUtil {
	private final static Logger log = Logger.getLogger(MyMapUtil.class);

	/**
	 * 计数器,计算Maps中包括Key的次数.做为Value放入Map中
	 * 
	 * @param counter
	 * @param key
	 */
	public static void mergeCounts(Map<String, Integer> counter, String key) {
		if (counter.containsKey(key)) {
			Integer count = counter.get(key);
			counter.put(key, ++count);
		} else {
			counter.put(key, 1);
		}
	}

	/**
	 * 最大值 如果Value值大于当前Map中的值,更新
	 * 
	 * @param maxKeeper
	 *            不能为空
	 * @param key
	 * @param value
	 */
	public static void replaceMaxValue(Map<Integer, BigDecimal> maxKeeper, Integer key, BigDecimal value) {
		BigDecimal maxValue = value;
		BigDecimal oldValue = maxKeeper.get(key);
		if (oldValue == null || value.compareTo(oldValue) == 1) {
			// 取最大值
			maxValue = value;
		} else {

		}
		maxKeeper.put(key, maxValue);
	}

	/**
	 * 把target里面的key从source查出value并填充回target里面
	 * 
	 * @param target
	 *            需要填充value值的Map
	 * @param source
	 *            源数据
	 */
	@SuppressWarnings("unchecked")
	public static void fillMapValueByKey(Map target, Map source) {
		if (MapUtils.isEmpty(target) || MapUtils.isEmpty(source)) {
			return;
		}
		for (Entry entry : (Collection<Entry>) target.entrySet()) {
			if (null == entry || null == entry.getKey()) {
				continue;
			}
			Object value = source.get(entry.getKey());
			if (null == value) {
				continue;
			}
			entry.setValue(value);
		}
	}

	/**
	 * 把值装载入集合中
	 * 
	 * @param source
	 *            <key,List>
	 * @param key
	 * @param value
	 */
	public static void fillMapCollection(Map source, Object key, Object value) {
		if (null == source || null == key || null == value) {
			return;
		}
		Collection keyValue = (Collection) source.get(key);
		if (null == keyValue) {
			keyValue = new ArrayList();
		}
		if (keyValue.contains(value)) {
			return;
		}
		keyValue.add(value);
		source.put(key, keyValue);
	}
	
	
	/**
	 * 把值装载入集合中,如果值已经有了,也会继续插入重复的数据
	 * 
	 * @param source
	 *            <key,List>
	 * @param key
	 * @param value
	 */
	public static void fillMapCollectionIgnoreExist(Map source, Object key, Object value) {
		if (null == source || null == key || null == value) {
			return;
		}
		Collection keyValue = (Collection) source.get(key);
		if (null == keyValue) {
			keyValue = new ArrayList();
		}		
		keyValue.add(value);
		source.put(key, keyValue);
	}
	

	/**
	 * 取map中的键包含keys的返回 (支持key 为数组的判断)
	 * 
	 * @param map
	 * @param keys
	 * @return
	 */
	public static Map intersectKeyMap(Map map, List keys) {
		if (null == map || null == keys) {
			return new HashMap();
		}
		Map result = new HashMap();
		for (Entry entry : (Set<Entry>) map.entrySet()) {
			if (null == entry || null == entry.getKey()) {
				continue;
			}
			if (entry.getKey() instanceof Object[]) {
				int keysSize = keys.size();
				for (int i = 0; i < keysSize; i++) {
					if (null == keys.get(i) || !(keys.get(i) instanceof Object[])) {
						continue;
					}
					if (ArrayUtils.isEquals(entry.getKey(), keys.get(i))) {
						result.put(entry.getKey(), entry.getValue());
					}
				}
			} else if (keys.contains(entry.getKey())) {
				result.put(entry.getKey(), entry.getValue());
			}
		}
		return result;
	}

	/**
	 * 取map中的键包含keys的值返回
	 * 
	 * @param map
	 *            map集合
	 * @param keys
	 *            键集合
	 * @return 值集合
	 */
	public static <R,T> List<T> getMapValues(Map<R,T> map, List<R> keys) {
		if (null == map || null == keys) {
			return new ArrayList<T>();
		}
		List<T> result = new ArrayList<T>();
		for (Entry<R,T> entry : map.entrySet()) {
			if (null == entry || null == entry.getKey() || null == entry.getValue() || !keys.contains(entry.getKey())) {
				continue;
			}
			result.add(entry.getValue());
		}
		return result;
	}

	/**
	 * 根据value来对map进行排序
	 * 
	 * @param key_value
	 *            source map
	 * @param comparator
	 * @return 源map根据value排序后的结果
	 */
	public static <K, V extends Comparable<? super V>> LinkedHashMap<K, V> sortMapKeyByValue(Map<K, V> key_value, Comparator<V> c) {
		if (MapUtils.isEmpty(key_value)) {
			return new LinkedHashMap<K, V>();
		}

		SortedMap<V, List<K>> value_keyList = null;
		if (null == c) {
			value_keyList = new TreeMap<V, List<K>>();
		} else {
			value_keyList = new TreeMap<V, List<K>>(c);
		}

		Map<K, V> key_nullValue = new HashMap<K, V>();
		for (Entry<K, V> entry : (Set<Entry<K, V>>) key_value.entrySet()) {
			V value = entry.getValue();
			if (null == value) {
				key_nullValue.put(entry.getKey(), null);
				continue;
			}

			List<K> keyList = (List<K>) value_keyList.get(entry.getValue());
			if (null == keyList) {
				keyList = new ArrayList<K>();
				value_keyList.put(entry.getValue(), keyList);
			}
			keyList.add(entry.getKey());
		}

		LinkedHashMap<K, V> result = new LinkedHashMap<K, V>();
		for (Entry<V, List<K>> entry : (Set<Entry<V, List<K>>>) value_keyList.entrySet()) {
			List<K> keyList = (List<K>) entry.getValue();
			for (K key : keyList) {
				result.put(key, entry.getKey());
			}
		}

		if (MapUtils.isNotEmpty(key_nullValue)) {
			result.putAll(key_nullValue);
		}
		return result;
	}

	/**
	 * 根据key寻找map里的嵌套map value，如果嵌套map value是null，则new一个新map
	 * 
	 * @param map
	 * @param key
	 * @param tClazz 如果嵌套map value为空时需要new的map的类型
	 * @return 根据key找到的map里的嵌套map value
	 */
	public static <K, V extends Map<?, ?>, T extends V> V lookupMapValueByKey(Map<K, V> map, K key, Class<T> tClazz) {
		return lookUpValueByKey(map, key, tClazz);
	}

	/**
	 * 根据key寻找map里的嵌套collection value，如果嵌套collection value是null，则new一个新collection
	 * 
	 * @param map
	 * @param key
	 * @param tClazz 如果嵌套collection value为空时需要new的collection的类型
	 * @return 根据key找到的map里的嵌套collection value
	 */
	public static <K, V extends Collection<?>, T extends V> V lookupCollectionValueByKey(Map<K, V> map, K key, Class<T> tClazz) {
		return lookUpValueByKey(map, key, tClazz);
	}

	public static <K, V, T extends V> V lookUpValueByKey(Map<K, V> map, K key, Class<T> tClazz) {
		V value = map.get(key);
		if (null == value) {
			try {
				value = tClazz.newInstance();
			} catch (InstantiationException e) {
				log.error(e);
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				log.error(e);
				e.printStackTrace();
			} finally {
				map.put(key, value);
			}
		}
		return value;
	}

	/**
	 * 把keys集合和values集合转换成map的映射关系
	 * 
	 * @param keys
	 *            可有重复值
	 * @param values
	 * @return 唯一的key值，及其对于的value集合
	 */
	public static <R,T> Map<R,List<T>> convertMap(List<R> keys, List<T> values) {
		if (null == keys || null == values || keys.size() != values.size()) {
			return new HashMap<R, List<T>>();
		}
		Map<R, List<T>> map = new HashMap<R, List<T>>();
		for (int i = 0; i < keys.size(); i++) {
			if (null == keys.get(i)) {
				continue;
			}
			List<T> value =  map.get(keys.get(i));
			if (null == value) {
				value = new ArrayList<T>();
				map.put(keys.get(i), value);
			}
			value.add(values.get(i));
		}
		return map;
	}

	/**
	 * 把多个field的值转换成key作为Map转换
	 * 
	 * @param values
	 *            需要转换的数据集合
	 * @param keySplit
	 *            多个field值的分隔符
	 * @param fields
	 *            field数组
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static Map convertMap(List values, String keySplit, Field... fields) throws IllegalArgumentException, IllegalAccessException {
		if (ArrayUtils.isEmpty(fields) || CollectionUtils.isEmpty(values)) {
			return new HashMap();
		}
		for (Field field : fields) {
			field.setAccessible(true);
		}
		Map map = new HashMap();
		StringBuilder sb = new StringBuilder();
		for (Object o : values) {
			if (null == o) {
				continue;
			}
			sb.delete(0, sb.length());
			for (int i = 0; i < fields.length; i++) {
				if (i > 0) {
					sb.append(keySplit);
				}
				Object fieldValue = fields[i].get(o);
				if (null == fieldValue) {
					continue;
				}
				sb.append(fieldValue);
			}
			map.put(sb.toString(), o);

		}
		return map;
	}

	/**
	 * 获取keys下的所有子节点,不包含keys
	 * 
	 * @param sourceTree
	 *            key-父节点，value-子节点
	 * @param keys
	 *            需要获取的父节点
	 * @return
	 */
	public static List getSubordinateValueMap(Map sourceTree, List keys) {
		if (null == sourceTree || null == keys) {
			return new ArrayList();
		}
		List result = new ArrayList();
		fillSubordinateValueMap(sourceTree, keys, result);
		return result;
	}

	private static void fillSubordinateValueMap(Map inSourceTree, List inKeys, List outResult) {
		List sub = new ArrayList();
		for (Object o : inKeys) {
			List value = (List) inSourceTree.get(o);
			if (null == value) {
				continue;
			}
			sub.addAll(value);
		}
		outResult.addAll(sub);
		if (sub.size() > 0) {
			fillSubordinateValueMap(inSourceTree, sub, outResult);
		}
	}
	
	
	/**
	 * 获取Object[]里面序号是keyIndex的值作为返回的key
	 * @param <R> key
	 * @param <T> 结果值
	 * @param objectArrayKeyMap 需要转换的map
	 * @param keyIndex
	 * @return
	 */
	public static <R,T> Map<R,T> convertMap(Map<Object[],T> objectArrayKeyMap,int keyIndex){
		if(null == objectArrayKeyMap){
			return new HashMap<R, T>();
		}
		Map<R,T> result = new HashMap<R, T>();
		for(Entry<Object[],T> entry : objectArrayKeyMap.entrySet()){
			if(null == entry.getKey()){
				continue;
			}
			Object[] key = entry.getKey();
			if(key.length <= keyIndex){
				continue;
			}
			result.put((R) key[keyIndex], entry.getValue());
		}
		return result;
	}
	/**
	 * reverse 注意使用此方法在key和value都唯一的前提下，更换key和value的位置
	 */
	public static Map reverseKeyAndValue(Map map) {
		Map reverseMap  = new HashMap();
		for(Entry entry : (Set<Entry>)map.entrySet()){
			reverseMap.put(entry.getValue(), entry.getKey());
		}
		return reverseMap;
	}

	/**
	 * 2个list转换成map
	 * @param keys key值
	 * @param values value值
	 * @return
	 */
    public static <R,T> Map<R,T> convertList2Map(List<R> keys,List<T> values){
		if (CollectionUtils.isEmpty(keys) || CollectionUtils.isEmpty(values)) {
			return new HashMap<R,T>();
		}
        int keySize = keys.size();
		int valueSize = values.size();
		Map<R,T> result = new HashMap<R,T>();
		for (int i = 0; i < keySize; i++) {
			if(null == keys.get(i)){
				continue;
			}
			T value = null;
			if(i+1 <= valueSize){
				value = values.get(i);
			}
			result.put(keys.get(i), value);
		}
		return result;
    }
    
    
    /**
     * 移除键或者键值为null的数据
     * @param <R>
     * @param <T>
     * @param source
     * @return
     */
    public static <R,T> Map<R,T> removeAllNullValueEntry(Map<R,T> source){
    	if(null == source){
    		return new HashMap<R, T>();
    	}
    	Map<R,T> map = new HashMap<R, T>();
    	for(Entry<R,T> entry : source.entrySet()){
    		if(null == entry.getValue() || null == entry.getKey()){
    			continue;
    		}
    		map.put(entry.getKey(), entry.getValue());
    	}
    	return map;
    }
    
    
    /**
     * 根据值反向获取key
     * @param <R>
     * @param <T>
     * @param source 
     * @param valueList Map的值集合
     * @return Map的key集合
     */
    public static <R,T> List<R> getKeyListByValueList(BidiMap source,List<T> valueList){
    	if(null == source || null == valueList){
    		return new ArrayList<R>();
    	}
    	List<R> result = new ArrayList<R>();
    	for(T value : valueList){
    		R key = (R) source.getKey(value);
    		if(null == key){
    			continue;
    		}
    		result.add(key);
    	}
    	return result;
    }
    
    /**
     * 
     * @param stockQueryParam
     * @param index 获取object[]中的第几个
     * @param removeNull 过滤null值
     * @return
     */
	public static Map<Object, List<Object[]>> getMapFromListObjArr(List<Object[]> stockQueryParam, int index, boolean removeNull) {
		Map<Object, List<Object[]>> stockQueryParamMap = new HashMap<Object, List<Object[]>>();
		for(Object[] arrParam : stockQueryParam){
			Object keyParam = arrParam[index - 1];
			if(null == keyParam){
				continue;
			}
			List<Object[]> queryList = stockQueryParamMap.get(keyParam);
			if(null == queryList){
				queryList = new ArrayList<Object[]>();
				stockQueryParamMap.put(keyParam, queryList);
			}
			queryList.add(arrParam);
		}
		return stockQueryParamMap;
	}

	public static <R,T> Map<R,T> getMapByKeyList(
			Map<R,T> map, List<R> keyList) {
		if(MapUtils.isEmpty(map) || CollectionUtils.isEmpty(keyList)){
    		return new HashMap<R, T>();
    	}
		Map<R,T> returnMap = new HashMap<R,T>();
    	for(R key : keyList){
    		T value=  map.get(key);
    		if(null == value){
    			continue;
    		}
    		returnMap.put(key, value);
    	}
    	return returnMap;
		
	}
	
	/**
	 * CommonConstant.KEY
	 * CommonConstant.SORT_CRITERIA_ASC
	 * CommonConstant.SORT_CRITERIA_DESC
	 * CommonConstant.VALUEs
	 * @param mapToSort
	 * @param keyOrValue
	 * @param sortType
	 * @param comparator
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 */
	public static <R,T> List<Entry<R,T>> sortMapToEntryList(Map<R,T> mapToSort,String keyOrValue,String sortType) throws SecurityException, NoSuchMethodException{
		List<Entry<R,T>> sortMapList = new ArrayList<Entry<R,T>>(mapToSort.entrySet());
		Collections.sort(sortMapList, new MapEntryComparator(keyOrValue,sortType,BigDecimal.class));
		return sortMapList;
	}

	
	/**
	 * 
	 * CommonConstant.KEY
	 * CommonConstant.SORT_CRITERIA_ASC
	 * CommonConstant.SORT_CRITERIA_DESC
	 * CommonConstant.VALUEs
	 * @param mapToSort
	 * @param keyOrValue
	 * @param sortType
	 * @param comparator
	 * @param classType 类型 比较的类型
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 */
	public static <R,T> List<Entry<R,T>> sortMapToEntryList(Map<R,T> mapToSort,String keyOrValue,String sortType,Class<? extends Comparable> classType) throws SecurityException, NoSuchMethodException{
		List<Entry<R,T>> sortMapList = new ArrayList<Entry<R,T>>(mapToSort.entrySet());
		Collections.sort(sortMapList, new MapEntryComparator(keyOrValue,sortType,classType));
		return sortMapList;
	}
	/**
	 * @param value
	 * @return
	 */
	public static <K, V> List<Entry<K, V>> converMapToEntryList(Map<K, V> map) {
		List<Entry<K, V>> entryList = new ArrayList<Entry<K, V>>();
		for(Entry<K,V> entry : map.entrySet()){
			entryList.add(entry);
		}
		return entryList;
	}

	/**
	 * 
	 * @param doneMap
	 * @param keyValuesplitSign 用来分割key和value
	 * @param entrySplitSing 用来分隔entry
	 * @return
	 */
	public static String convertMapToString(Map map,String keyValueSplitSign, String entrySplitSing) {
		if(MapUtils.isEmpty(map)){
			return SignConstant.SIGN_EMPTY;
		}
		String returnStr = SignConstant.SIGN_EMPTY;
		int totalSize = map.size();
		int count = NumberConstants.INTEGER_ONE;
		for(Entry entry : (Set<Entry>)map.entrySet()){
			if(count != totalSize){
				returnStr = StringUtils.join(new Object[]{returnStr,entry.getKey(),keyValueSplitSign,entry.getValue(),entrySplitSing});
			}else{
				returnStr = StringUtils.join(new Object[]{returnStr,entry.getKey(),keyValueSplitSign,entry.getValue()});
			}
			count++;
		}
		return returnStr;
	}
	
}
