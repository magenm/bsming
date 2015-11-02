package com.bsming.common.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.bsming.common.compare.DescCompare;
import com.bsming.common.constant.NumberConstants;

public class MyListUtil<T> {
	
	/**
	 * 将sourceList里面的id作为key，对应的targetList的id最为value
	 * 
	 * @param sourceList
	 * @param targetlist
	 * @return
	 */
	public static Map<Long, Long> getIdMap(List<Long> sourceList, List<Long> targetlist) {
		Map<Long, Long> map = new HashMap<Long, Long>();
		if(CollectionUtils.isEmpty(sourceList)||CollectionUtils.isEmpty(targetlist)){
			return map;
		}
		if(sourceList.size() != targetlist.size()){
			return map;
		}
		for(int index = 0; index < sourceList.size(); index++){
			if(null!=sourceList.get(index) && null!=targetlist.get(index)){
				map.put(sourceList.get(index), targetlist.get(index));
			}			
		}
		
		return map;
	}


	/**
	 * 
	 * 转换一个ListToMap,Key为List的值.Value为List对应的下标(position) 排序从1开始.
	 * @param list
	 * @return
	 */
	public static Map<String,Integer> convertPMap(List<String> list) {		
		Map<String,Integer> pMap=new HashMap<String,Integer>();
		if(list==null||list.size()==0){
			return pMap;
		}
		for (int i = 0; i < list.size(); i++) {
			pMap.put(list.get(i), (i+1));
		}
		return pMap;
	}
	
	/**
	 * 
	 * 转换一个ListToMap,Key为List的值.Value为List对应的下标(position) 排序从1开始.
	 * @param list
	 * @return
	 */
	public static Map<Long, Integer> convertListMap(List<Long> list) {		
		Map<Long, Integer> pMap=new HashMap<Long, Integer>();
		if(list==null||list.size()==0){
			return pMap;
		}
		for (int i = 0; i < list.size(); i++) {
			pMap.put(list.get(i), (i+1));
		}
		return pMap;
	}
	
	
    /**将一个List归并到另一个List之中,在Set集中出现过的元素不再归并
     * 
     * @param sourceList 数据源
     * @param resultList 数据结果
     * @param filterSet  过滤集 在此Set中出现的元素不再归并至结果集中
     * @return
     */
	public static Set mergeList(List sourceList,
			List resultList,Set filterSet ) {
		if(sourceList==null){
			return filterSet;
		}
		
		for(Object ratinigAID:sourceList){
    		if(filterSet.contains(ratinigAID)){
    			
    		}else{
    			resultList.add(ratinigAID);
    			filterSet.add(ratinigAID);
    		}
    	}
		return filterSet;
	}



	public static List mergeLists(List<List> mergeLists) {
		Set<String> filterSet=new HashSet(); 
		List results=new ArrayList();
		for(List list:mergeLists){			
	    	MyListUtil.mergeList(list, results, filterSet);  			
		}
		return results;
	}



	public static List<Long> convertString2Long(String[] datas) {
		List<Long> ls=new ArrayList();
		for(String data:datas){
			ls.add(Long.valueOf(data));
		}
		return ls;
	}
	
	public static List<Long> convertString2Long(List<String> datas) {
		List<Long> ls=new ArrayList<Long>();
		for(String data:datas){
			ls.add(Long.valueOf(data));
		}
		return ls;
	}
	
	/**
	 * 未测试
	 * 类型转换可能会丢失
	 * 
	 * @param f
	 * @param datas
	 * @return
	 * @throws IllegalAccessException
	 */
	public static Map convert2Map(Field f,List datas) throws IllegalAccessException {
		if(CollectionUtils.isEmpty(datas)) {
			return new HashMap() ;
		}
		
		f.setAccessible(true);
		Map m=new HashMap();		
		for(Object object:datas){
			if(null == object){
				continue;
			}
			m.put(f.get(object), object);			
		}
		return m;
	}
	
	
	/**
	 * datas里的对象的Field的值包含在keys里面的数据转换成Map
	 * @param f 属性
	 * @param keys 键值
	 * @param datas 数据
	 * @return
	 * @throws IllegalAccessException
	 */
	public static Map convert2Map(Field f,List keys,List datas) throws IllegalAccessException {
		if(CollectionUtils.isEmpty(datas) || CollectionUtils.isEmpty(keys)) {
			return new HashMap() ;
		}
		
		f.setAccessible(true);
		Map m=new HashMap();		
		for(Object object:datas){
			if(null == object){
				continue;
			}
			Object value = f.get(object);
			if(keys.contains(value)){
			   m.put(value, object);	
			}
					
		}
		return m;
	}
	
	
	/**
	 * 根据firstKey所得到的值集合放入子Map中，并以secondKey所表示的feild为key存放
	 * 
	 * 
	 * @param firstKey
	 * @param secondKey
	 * @param datas
	 * @return
	 * @throws IllegalAccessException
	 */
	public static Map convert2RecursiveMap(Field firstKey, Field secondKey, List datas) throws IllegalAccessException {
		if(CollectionUtils.isEmpty(datas)) {
			return new HashMap() ;
		}
		
		firstKey.setAccessible(true);
		secondKey.setAccessible(true);
		Map r=new HashMap();		
		for(Object object:datas){
			if(null == object){
				continue;
			}
			Object fKey = firstKey.get(object);
			Object fValue = r.get(fKey) ;
			if(fValue != null) {
				HashMap secondMap = (HashMap)fValue ;
				secondMap.put(secondKey.get(object), object) ;
			}else {
				HashMap secondMap = new HashMap() ;
				secondMap.put(secondKey.get(object), object) ;
				r.put(fKey, secondMap) ;
			}
		}
		return r;
	}
	
	
	public static Map convert2RecursiveMap(Field firstKey, Field secondKey,Class<? extends Map> firstMapClass,Class<? extends Map> secondMapClass , List datas) throws IllegalAccessException, Exception {
		if(CollectionUtils.isEmpty(datas)) {
			return new HashMap() ;
		}
		
		firstKey.setAccessible(true);
		secondKey.setAccessible(true);
		Map r = firstMapClass.newInstance();		
		for(Object object:datas){
			if(null == object){
				continue;
			}
			Object fKey = firstKey.get(object);
			Object fValue = r.get(fKey) ;
			if(fValue != null) {
				Map secondMap = (Map)fValue ;
				secondMap.put(secondKey.get(object), object) ;
			}else {
				Map secondMap = secondMapClass.newInstance();
				secondMap.put(secondKey.get(object), object) ;
				r.put(fKey, secondMap) ;
			}
		}
		return r;
	}
	
	/**
	 * 未测试
	 * 类型转换可能会丢失
	 * 
	 * @param f
	 * @param datas
	 * @return
	 * @throws IllegalAccessException
	 */
	public static Map convert2Map(Field keyField, Field valueField, List datas) throws IllegalAccessException {
		if(CollectionUtils.isEmpty(datas)) {
			return new HashMap() ;
		}
		
		keyField.setAccessible(true);
		valueField.setAccessible(true);
		Map m=new HashMap();		
		for(Object object:datas){
			if(null == object){
				continue;
			}
			m.put(keyField.get(object), valueField.get(object));			
		}
		return m;
	}


	/**
	 * List to <Key, List<Object>>
	 * @param f
	 * @param datas
	 * @return
	 * @throws IllegalAccessException
	 */
	public static Map convert2ListMap(Field f,List datas) throws IllegalAccessException {
		if(CollectionUtils.isEmpty(datas)) {
			return new HashMap() ;
		}
		
		f.setAccessible(true);
		Map m=new HashMap();		
		for(Object object:datas){
			if(null == object){
				continue;
			}
			Object fKey = f.get(object);
			List fValues = (List) m.get(fKey);
			if (null == fValues) {
				fValues = new ArrayList();
				m.put(f.get(object), fValues);
			}
			fValues.add(object);
		}
		
		return m;
	}
	
	/**
	 * 根据对象的某一属性值排序, 属性的值必须是数字类型,对象中必须有对应的getter/setter方法
	 * 
	 * @param dataList
	 * @param asc false-倒叙，true-正序
	 */
	public static final void sort(List dataList, final String propName,  final boolean asc) {
		Comparator comparator = new Comparator() {

			@Override
			public int compare(Object o1, Object o2) {

				if(o1 == null && o2 == null)  {
					return 0;
				}else if(o1 == null && o2 != null) {
					return asc ? -1 : 1 ;
				}else if(o1 != null && o2 == null) {
					return asc ? 1 : -1;
				}
				
				Object propValue1 = null ;
				Object propValue2 = null ;
				try {
					propValue1 = PropertyUtils.getSimpleProperty(o1, propName);
					propValue2 = PropertyUtils.getSimpleProperty(o2, propName) ;
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(propValue1 == null && propValue2 == null)  {
					return 0;
				}else if(propValue1 == null && propValue2 != null) {
					return asc ? -1 : 1 ;
				}else if(propValue1 != null && propValue2 == null) {
					return asc ? 1 : -1;
				}
				
				
				int compareTo = new BigDecimal(propValue1.toString()).compareTo(new BigDecimal(propValue2.toString()));
				if(compareTo > 0) {
					return asc ? 1 : -1;
				}else if(compareTo < 0) {
					return asc ? -1 : 1 ;
				}else {
					return 0 ;
				}
			}
			
		};
		
		if(CollectionUtils.isNotEmpty(dataList)) {
			Collections.sort(dataList, comparator) ;
		}
	}
	
	
	/**
	 * list转换为value是list集合的Map
	 * @param <R>
	 * @param <T>
	 * @param <C>
	 * @param <L>
	 * @param <M>
	 * @param <MP>
	 * @param f Map中key在对象中的属性的值
	 * @param datas 数据对象集合
	 * @param mapClazz Map的类型
	 * @param listClazz Map中value的集合类型
	 * @return
	 * @throws Exception
	 */
	public static <R,T,C extends Collection<T>,M extends Map<?, ?>> Map<R,C> convert2ListMap(Field f,List<T> datas, Class<M> mapClazz,Class<C> listClazz) throws  Exception {
		return convert2ListMap(f, datas, mapClazz,null, null, listClazz,false);
	}
	
	
	/**
	 *  list转换为value是list集合的Map
	 * @param <R>
	 * @param <T>
	 * @param <C>
	 * @param <L>
	 * @param <M>
	 * @param <MP>
	 * @param f Map中key在对象中的属性的值
	 * @param datas 数据对象集合
	 * @param mapClazz Map的类型
	 * @param mapParam 构造Map的初始化参数
	 * @param listClazz Map中value的集合类型
	 * @param listParam 构造list的
	 * @return
	 * @throws Exception
	 */
	public static <R,T,C extends Collection<T>,M extends Map<?, ?>> Map<R,C> convert2ListMap(Field f,List<T> datas, Class<M> mapClazz,Class[] mapParamClass,Object[] mapParam,Class<C> listClazz,boolean isRemoveNullKey) throws  Exception {
		Map<R,C> m = null;
		if(ArrayUtils.isEmpty(mapParamClass) || ArrayUtils.isEmpty(mapParam)){
			 m = (Map<R, C>) mapClazz.newInstance();	
		}else {
			Constructor constructor = mapClazz.getDeclaredConstructor(mapParamClass);
			m = (Map<R, C>) constructor.newInstance(mapParam);
		}
		if(CollectionUtils.isEmpty(datas)) {
			return m;
		}
		
		f.setAccessible(true);
		for(T object:datas){
			if(null == object){
				continue;
			}
			R fKey = (R) f.get(object);
			if(null == fKey && isRemoveNullKey){
				continue;
			}
			Collection<T> collection = MyMapUtil.lookupCollectionValueByKey(m, fKey, listClazz);
			collection.add(object);
		}
		return m;
	}
	
	
	public static <R,T,C extends Collection<T>,M extends Map<?, ?>> Map<R,C> convert2ListMap(Field f,List<T> datas, Class<M> mapClazz,Class[] mapParamClass,Object[] mapParam,Class<C> listClazz) throws  Exception {
		return convert2ListMap(f,datas,mapClazz,mapParamClass,mapParam,listClazz,false);
	}

	/**
	 * 将datas中值为null的元素删除，并保持原来的顺序返回新的list
	 * 
	 * @param datas
	 * @return
	 */
	public static List trimNull(List datas) {
		List r = new ArrayList() ;
		if(CollectionUtils.isEmpty(datas)) {
			return r ;
		}
		for(Object o  : datas) {
			if(o != null) {
				r.add(o) ;
			}
		}
		return r;
	}



	/**
	 * 将list中的元素的某个属性的值组成新的list
	 * 
	 * @param declaredField
	 * @param hcBlocks
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static List getList(Field declaredField, List datas) throws IllegalArgumentException, IllegalAccessException {
		if(CollectionUtils.isEmpty(datas)) {
			return new ArrayList() ;
		}
		
		declaredField.setAccessible(true);
		List r = new ArrayList() ;
		for(Object object:datas){
			if(null == object){
				continue;
			}
			Object fieldValue = declaredField.get(object);
			if(null == fieldValue){
				continue;
			}
			r.add(fieldValue) ;
		}
		return r;
	}
	
	
	/**
	 * 将list中的元素的某个属性的值组成新的list,消除重复数据
	 * 
	 * @param declaredField
	 * @param hcBlocks
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static List getDistinctList(Field declaredField, List datas) throws IllegalArgumentException, IllegalAccessException {
		if(CollectionUtils.isEmpty(datas)) {
			return new ArrayList() ;
		}
		
		declaredField.setAccessible(true);
		Set r = new HashSet() ;
		for(Object object:datas){
			if(null == object){
				continue;
			}
			Object fieldValue = declaredField.get(object);
			if(null == fieldValue){
				continue;
			}
			r.add(fieldValue) ;
		}
		return new ArrayList(r);
	}
	
	
	
	/**
	 * 将list中的元素的某个属性的值组成新的list
	 * 并去重复
	 * @param declaredField
	 * @param hcBlocks
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static <R,T> List<R> getUniqueList(Field declaredField, List<T> datas) throws IllegalArgumentException, IllegalAccessException {
		if(CollectionUtils.isEmpty(datas)) {
			return new ArrayList<R>() ;
		}
		declaredField.setAccessible(true);
		Set<R> r = new HashSet<R>() ;
		for(Object object:datas){
			if(null == object){
				continue;
			}
			R e = (R) declaredField.get(object);
			if(null == e){
				continue;
			}
			r.add(e) ;
		}
		return new ArrayList<R>(r);
	}
	
	/**
	 * 把list转换成Object[]
	 * @param list
	 * @return
	 */
	public static List<Object[]> getParamListByList(List list){
		if(CollectionUtils.isEmpty(list)){
			return new ArrayList<Object[]>();
		}
		List<Object[]> paramList = new ArrayList<Object[]>();
		for(Object o : list){
			if(null == o){
				continue;
			}
			paramList.add(new Object[]{o});
		}
		return paramList;
	}
	
	
	/**
	 * 获取集合里面的最小值
	 * @param list
	 * @return
	 */
	public static Object getListMinValue(List list) {
		List param = removeAllNullValue(list);
		if(CollectionUtils.isEmpty(param)){
			return null;
		}
		return Collections.min(param);
	}
	
	
	/**
	 * 获取集合里面的最大值
	 * @param list
	 * @return
	 */	
	public static <E extends Comparable> E getListMaxValue(List<E> list) {
		List<E> param = removeAllNullValue(list);
		if(CollectionUtils.isEmpty(param)){
			return null;
		}
		return (E) Collections.max(param);
	}
	
	/**
	 * 去除空值的结果
	 * @param list
	 * @return
	 */
	public static <E> List<E> removeAllNullValue(List<E> list){
		if (CollectionUtils.isEmpty(list)) {
			return new ArrayList<E>();
		}
		List<E> result = new ArrayList<E>();
		for(E o : list){
			if(null == o){
				continue;
			}
			result.add(o);
		}
		return result;
	}

	
	/**
	 * 去重包含某个字段的model list
	 * @param list
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static <E> List<E> removeModelListByField(List<E> list,Field field) throws IllegalArgumentException, IllegalAccessException{
		if(CollectionUtils.isEmpty(list) || null == field){
			return new ArrayList<E>();
		}
		List<E> returnModel = new ArrayList<E>();
		Set fieldValueSet = new HashSet();
		for(E model : list){
			field.setAccessible(true);
			Object fieldValue =  field.get(model);
			if(fieldValueSet.contains(fieldValue)){
				continue;
			}
			returnModel.add(model);
			fieldValueSet.add(fieldValue);
		}
		
		return returnModel;
	}
	
	public static List<String> removeAllBlankValue(List<String> list){
		if (CollectionUtils.isEmpty(list)) {
			return new ArrayList<String>();
		}
		List<String> result = new ArrayList<String>();
		for(String s : list){
			if(StringUtils.isBlank(s)){
				continue;
			}
			result.add(s);
		}
		return result;
	}

	/**
	 * 手动分页，从所有结果List中获取某一页的List
	 * @param totalList 全部条目List
	 * @param pageNum 当前页数（从1开始）
	 * @param pageCount 每页的条目数
	 * @return
	 */
	public static <E> List<E> getPageList(List<E> totalList, int pageNum, int pageSize) {
		if (CollectionUtils.isEmpty(totalList)) {
			return new ArrayList<E>();
		}

		int start = (pageNum - 1) * pageSize;
		int length = totalList.size();
		if (start >= length) {
			return new ArrayList<E>();
		}

		List<E> pageList = new ArrayList<E>();
		int end = Math.min(length, pageNum * pageSize);
		for (int i = start; i < end; i++) {
			pageList.add(totalList.get(i));
		}
		return pageList;
	}
	
	
	/**
	 * 根据区间取出数据集合
	 * @param source
	 * @param start 起始值
	 * @param end 结束值
	 * @return
	 */
	public static List<Long> getListByRange(Collection<Long> source ,Long start,Long end){
		if(null == source){
			return new ArrayList<Long>();
		}
		if(null == start && null == end){
			return new ArrayList(source);
		}
		List<Long> result = new ArrayList<Long>();
		for(Long value : source){
			if(null == value || (null != start && value < start) || (null != end && value > end)){
				continue;
			}
			result.add(value);
		}
		return result;
	}
	
	
	/**
	 * 获得2层级嵌套的数据去重后的结果
	 * Collection<Collection>
	 * Collection<List>
	 * 等
	 * @param source
	 * @return
	 */
	public static List getCollectionUniqueNesting(Collection source) {
		if (null == source) {
			return new ArrayList();
		}
		Set result = new HashSet();
		for (Object c : source) {
			if (null == c) {
				continue;
			}
			Collection co = (Collection)c;
			result.addAll(co);
		}
		return new ArrayList(result);
	}
	
	
	/**
	 * 字符串倒排序集合里面的数据
	 * @param source
	 */
	public static void sortByDesc(List source){
		if(null == source){
			return;
		}
		DescCompare dc = new DescCompare();
		Collections.sort(source,dc);
		
	}
	
	/**
	 * 截取source里面的object[] 数组最为新的数据返回
	 * @param source 
	 * @param start 起始数组索引
	 * @param limit 获取数组元素个数
	 * @return
	 */
	public static List<Object[]> subObjectArrayUniqueList(List<Object[]> source,int start,int limit){
		if(null == source){
			return new ArrayList<Object[]>();
		}
		Set<List> set = new HashSet<List>();
		for(Object[] paramObject : source){
			if(null == paramObject || paramObject.length <= start || paramObject.length < start + limit){
				continue;
			}
			Object[] value = ArrayUtils.subarray(paramObject, start, limit);
			set.add(Arrays.asList(value));
		}
		List<Object[]> result = new ArrayList<Object[]>();
		for(List list : set){
			result.add(list.toArray());
		}
		return result;
	}
	
	/**
	 * 取Object[]的交集
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static List<Object[]> intersectionObjectArray(List<Object[]> list1,List<Object[]> list2){
		   
		   if(CollectionUtils.isEmpty(list1) || CollectionUtils.isEmpty(list2)){
			   return new ArrayList<Object[]>();
		   }
		   List<Object[]> result = new ArrayList<Object[]>();
		   for(Object[] param1 : list1){
			   if(null == param1){
				   continue;
			   }
			   for(Object[] param2 : list2){
				   if(null == param2){
					   continue;
				   }
				   if(ArrayUtils.isEquals(param1, param2)){
					   result.add(param1);
				   }
				}
		   }
		   return result;
		   
	   }
	
	/**
	 * 把n个集合规整成Object[],按照lists的顺序
	 * 数据大小为n个list的size相乘
	 * @param lists
	 * @return
	 */
	public static List<Object[]> getObjectArrayList(List ...lists){
		return getObjectArrayList(true,lists);
	} 
	
	
	/**
	 * 把n个集合规整成Object[],按照lists的顺序
	 * 数据大小为n个list的size相乘
	 * @param isAllNotEmpty 需要组合的参数集合如果有一个为空则返回空集合
	 * @param lists
	 * @return
	 */
	public static List<Object[]> getObjectArrayList(boolean isFilterNull,List ...lists){
		List<Object[]> result = new ArrayList<Object[]>();
		List[] listArray = lists;
		if(isFilterNull){
			listArray = getFilterNullArrayList(listArray);
		}
		
        Map<Integer, Integer> countMap = getCountMap(listArray);
		int mulCount = 1;
		for (Integer mul : countMap.values()) {
			if (mul <= 0) {
				continue;
			}
			mulCount = mulCount * mul;
		}
        int listsLength = listArray.length;
        
		for (int i = 0; i < mulCount; i++) {
			int dividend = 1;
			Object[] arr = new Object[listsLength];
			for (int j = 0; j < listsLength; j++) {
				int listSize = countMap.get(j);
				int index = 0;
				if (listSize > 0) {
					index = (i / dividend) % listSize;
					dividend = dividend * listSize;
				}
				Object element = null;
				if (CollectionUtils.isNotEmpty(listArray[j])) {
					element = listArray[j].get(index);
				}else if(isFilterNull){
					return new ArrayList<Object[]>();
				}
				arr[j] = element;
			}
			result.add(arr);
		}
		return result;
	} 
	
	
	private static List[] getFilterNullArrayList(List[] lists) {
		List[] listArray = new List[lists.length];
		for(int i = 0; i<lists.length;i++){
			listArray[i] = removeAllNullValue(lists[i]);
		}
		return listArray;
	}


	/**
	 * 去除List<Object[]>里的数组项完全相同的重复数组、以及包含null的数组
	 * @param fromList
	 * @return
	 */
	public static List<Object[]> removeDuplicatedObjectArray(List<Object[]> fromList) {
		if (CollectionUtils.isEmpty(fromList)) {
			return new ArrayList<Object[]>();
		}

		List<Object[]> result = new ArrayList<Object[]>();
		for (Object[] f : fromList) {
			if (ArrayUtils.isEmpty(f) || ArrayUtils.contains(f, null)) {
				continue;
			}

			boolean isDuplicated = false;

			for (Object[] t : result) {
				if (ArrayUtils.isEquals(f, t)) {
					isDuplicated = true;
					break;
				}
			}

			if (!isDuplicated) {
				result.add(f);
			}
		}
		return result;
	}
	 
   private static Map<Integer,Integer> getCountMap(List ...lists){
		Map<Integer, Integer> result = new HashMap<Integer, Integer>();
		for (int i = 0; i < lists.length; i++) {
			List list = lists[i];
			if(null == list){
				 result.put(i, 0);
			}else {
			   result.put(i, list.size());
			}
		}
	  return result;
   }
	/**
	 * 获取一个对象list中多个字段，并组装成List<Object[]>
	 * @param objList
	 * @param fieldArr
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
 public static List<Object[]> getMultiFieldFromModel(List objList,Field ...fieldArr) throws IllegalArgumentException, IllegalAccessException{
		if(ArrayUtils.isEmpty(fieldArr)){
			return new ArrayList<Object[]>();
		}
		for(Field field : fieldArr){
			field.setAccessible(true);
		}
		List<Object[]> multiFieldList = new ArrayList<Object[]>();
		for(Object obj : objList){
			Object[] fieldsValue = new Object[fieldArr.length];
			if(null == obj){
				continue;
			}
			for(int i = 0 ;i < fieldArr.length; i ++){
				fieldsValue[i] = fieldArr[i].get(obj);
			}
			multiFieldList.add(fieldsValue);
		}
	
		return multiFieldList;		
	}
   
 
	 /***
	  * 获取一组对象List中多个字段的List，以Map的方式返回，map的key是Field
	  * @param objList 一组对象
	  * @param nullable 获取的字段中是否可以为null，nullable=false:如果其中一个字段为空，则所有字段的list中该条数据不填充进来；
	  * @param Field ...fieldArr  字段的可变长数组。
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	  */
  public static Map<Field,List> getFieldListFromModel(List objList,boolean nullable , Field ...fieldArr) throws IllegalArgumentException, IllegalAccessException{
	  if(ArrayUtils.isEmpty(fieldArr) || CollectionUtils.isEmpty(objList)){
			return new HashMap<Field,List>();
		}
		for(Field field : fieldArr){
			field.setAccessible(true);
		}
		Map<Field,List> fieldListMap = new HashMap<Field,List>();
		for(Field field : fieldArr){
			fieldListMap.put(field, new ArrayList());
		}
		
		for(Object obj : objList){
			if(null == obj){
				continue;
			}
			
			//对于nullable参数，创建出fieldMap暂时存储各个字段的值，然后判断是否保存
			boolean continueToObj = false;
			Map<Field,Object> fieldMap = new HashMap<Field,Object>();
			for(int i = 0 ;i < fieldArr.length; i ++){
				Object fieldsValue = fieldArr[i].get(obj);
				if(null == fieldsValue){
					continueToObj = true;
				}
				
				fieldMap.put(fieldArr[i], fieldsValue);
			}
			if(!nullable){
				if(continueToObj){
					continue;
				}
			}
			
			//保存
			for(Entry fieldListEntry : (Set<Entry<Field,List>>)fieldListMap.entrySet()){
				List fieldValList = (List) fieldListEntry.getValue();
				fieldValList.add(fieldMap.get(fieldListEntry.getKey()));
			}
		}
	
		return fieldListMap;	
  }
  /**
   * 从数组的list中取出数组下标的数据为list；
   * @param listObjArr 数组的list 
   * @param index 数组下标
   * @param notHasNull 是否包含null，notHasNull == true的时候，不包含，null == false, 
   * @param distinct 是否去重, distinct= true 需要去重
   * @return
   */
  public static List getListFromObjArr(List<Object[]> listObjArr, int index , boolean notHasNull,boolean distinct){
	  if(CollectionUtils.isEmpty(listObjArr)){
		  return new ArrayList();
	  }
	  if(listObjArr.get(0).length - NumberConstants.INTEGER_ONE < index){
		  return new ArrayList(); 
	  }
	  List paramList = new ArrayList();
	  for(Object[] objArr : listObjArr){
		  if(BooleanUtils.isTrue(notHasNull) && null == objArr[index]){
			  continue;
		  }
		  paramList.add(objArr[index]);
	  }
	  if(distinct){
		  paramList = new ArrayList(new HashSet(paramList));
	  }
	  return paramList;
  }


  	/**
  	 * 获取一个字段和model的map
  	 * @param reportsList
  	 * @param declaredField
  	 * @return
  	 * @throws IllegalAccessException 
  	 * @throws IllegalArgumentException 
  	 */
	public static Map getFieldAndModelMap(List reportsList,	Field declaredField) throws IllegalArgumentException, IllegalAccessException {
		Map filedAndModelMap = new HashMap();
		declaredField.setAccessible(true);
		for(Object model : reportsList){
			Object key = declaredField.get(model);
			if(null == key){
				continue;
			}
			filedAndModelMap.put(key, model);
		}
		return filedAndModelMap;
	}
  
 
	/**
	 * List的某个字段值作为key,value是对象集合
	 * @param <R>
	 * @param <T>
	 * @param field 字段
	 * @param source 集合
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static <R,T> Map<R,List<T>> convertMapListValue(Field field,List<T> source) throws IllegalArgumentException, IllegalAccessException{
		Map<R,List<T>> result = new HashMap<R, List<T>>();
		field.setAccessible(true);
		for(T object : source){
			if(null == object){
				continue;
			}
			R key = (R) field.get(object);
			if(null == key){
				continue;
			}
			List<T> value = (List<T>) MyMapUtil.lookupCollectionValueByKey(result, key, ArrayList.class);
			value.add(object);
		}
		return result;
	}
  
 
   /**
    * 取orderList与list的交集并且保持orderList的顺序
    * 忽略相同元素的个数
    * @param <R>
    * @param orderList
    * @param list2
    * @return
    */
   public static <R> List<R> intersectionOrder(Collection<R> orderList,Collection<R> list){
	   if(CollectionUtils.isEmpty(orderList) || CollectionUtils.isEmpty(list)){
		   return new LinkedList<R>();
	   }
	   List<R> result = new LinkedList<R>();
	   for(R o : orderList){
		   if(list.contains(o)){
			   result.add(o);
		   }
	   }
	   return result;
   }



   //TODO 修改成泛型,考虑需不需要克隆....
	public static List initListFromOne(Object model, int size) {
		List list = new ArrayList();
		for(int i =0 ; i< size ; i++){
			list.add(model);
		}
		
		return list;
	}

	public static <M extends Comparable> List<M> subListByStartAndEnd(List<M> list,
			M start, M end) {
		List<M> returnList = new ArrayList<M>();
		for(M element : list){
			if(element.compareTo(start)>= 0 && element.compareTo(end)<=0){
				returnList.add(element);
			}
		}
		return returnList;
	}


  

	/**
	 * 根据某个字段求和
	 * @param declaredField
	 * @param sortHolderList
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static Long getTotalValue(Field declaredField,
			List datas) throws IllegalArgumentException, IllegalAccessException {
		if(CollectionUtils.isEmpty(datas)) {
			return null ;
		}
		declaredField.setAccessible(true);
		Long value = 0L;
		for(Object object:datas){
			if(null == object){
				continue;
			}
			value += (Long)declaredField.get(object);
		}
		return value;
	}

	/**
	 * 获取model list 里面的某个字段 
	 * @param objList
	 * @param nullable
	 * @param declaredField
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static List getFieldValueListFromModelList(List objList, boolean nullable, Field declaredField) throws IllegalArgumentException, IllegalAccessException {
		   if(CollectionUtils.isEmpty(objList) || null == declaredField){
				return new ArrayList();
			}
		    List fiedlList = new ArrayList();
			declaredField.setAccessible(true);
			for(Object obj : objList){
				if(null == obj){
					continue;
				}
				Object key = declaredField.get(obj);
				if(!nullable && null == key){
					continue;
				}
				fiedlList.add(key);
			}
			return fiedlList;
	}
	
	/**
	 * 对象集合里面的属性值抽出转换
	 * @param objList
	 * @param left pair左边存放的属性对应的值
	 * @param right pair右边存放的属性对应的值
	 * @param isValueNullAble 是否允许有空值
	 * @return
	 * @throws Exception 
	 * @throws IllegalArgumentException 
	 */
	public static List<Pair> getFieldValueList(List objList,Field left,Field right,boolean isValueNullAble) throws  Exception{
		if(CollectionUtils.isEmpty(objList) || null == left || null == right){
			return new ArrayList<Pair>();
		}
		
		left.setAccessible(true);
		right.setAccessible(true);
		List<Pair> result = new ArrayList<Pair>();	
		for(Object object:objList){
			if(null == object){
				continue;
			}
			Object leftValue = left.get(object);
			Object rightValue = right.get(object) ;
			if(!isValueNullAble && (null == leftValue || null == rightValue)){
				continue;
			}
			result.add(Pair.of(leftValue, rightValue));
		}
		return result;
	}

	
}
