/**
 * 
 */
package com.bsming.common.compare;

import java.util.Comparator;

/**
 * 实现倒排序
 * @author yhye
 * @2011-11-14下午04:30:25
 */
@SuppressWarnings("unchecked")
public class DescCompare implements Comparator {

	@Override
	public int compare(Object o1, Object o2) {
		if(null == o1 || null == o2){
			return 0;
		}
		return ((Comparable)o2).compareTo((Comparable)o1);
	}

}
