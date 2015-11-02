package com.bsming.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 
 * @author hongtaozhang
 *
 */
public class PinyinUtil {

    private static final Log log = LogFactory.getLog(PinyinUtil.class);
    private static String defaultPropertyFile = "unicode_to_hanyu_pinyin.txt";
    public static Map<String, String[]> pinyinMap = new HashMap<String, String[]>();

    /** 初始化拼音的map */
    static {
        String fileName = PinyinUtil.class.getClassLoader().getResource(defaultPropertyFile).getFile();
        File file = new File(fileName);
        int count = 0;
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufReader = new BufferedReader(fileReader);
            String line = null;
            while ((line = bufReader.readLine()) != null) {
                log.debug(line);
                String[] lines = line.split(" ");
                String[] values = {};
                if (lines.length >= 2) {
                    String value = lines[1];
                    value = value.replace("(", "").replace(")", "");
                    String[] res = value.split(",");
                    
                    //remove '[0-9]' char for each pingying
                    values = new String[res.length];
                    if (null != res && res.length >= 1) {
                    	for (int i=0;i<res.length;i++){
                    		values[i] = res[i].substring(0, res[i].length() - 1);
                    	}
                    }
                }
                pinyinMap.put(lines[0], values);
                log.debug(lines[0] + " " + values);
                count++;

            }
            bufReader.close();
            fileReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info(count);
    }

    /**
     * 查出给出的汉字的拼音首字母简称。
     * @param key
     * @return
     */
    public static String[] getPinyinAcronym(String key) {
        
        if (key == null) {
            return null;
        }
        
        //skip English names
//        Pattern p = Pattern.compile("^[A-Za-z ]+$");  
//        Matcher m = p.matcher(key);
//        if (m.find()){  
//        	return key;
//        }
        //for Chinese names

        List<Set<Character>> acronyms = new ArrayList<Set<Character>>();
        for (int i = 0; i < key.length(); i++) {
        	
        	Set<Character> acronym = new LinkedHashSet<Character>();
            char ch = key.charAt(i);
            String ascii = Integer.toHexString(ch).toUpperCase();
            String[] values = pinyinMap.get(ascii);
            if(values == null || values.length <= 0) {
            	acronym.add(ch);
            }
            else {
            	for(int v=0; v<values.length; v++) {
            		String value = values[v];
            		if (value.trim().toUpperCase().length()>0) {
            			String word = value.trim().toUpperCase();
            			if (StringUtils.isEmpty(word)) {
            				continue;
            			}
            			acronym.add(word.charAt(0));
            		}
            	}
            }
            acronyms.add(acronym);
        }
        
    	List<String> result = new ArrayList<String>();
        if (CollectionUtils.isNotEmpty(acronyms)) {
        	for (Set<Character> chars : acronyms) {
        		List<String> sub = composeChar(result, chars);
        		
        		result = sub;
        	}
        }
        if (CollectionUtils.isEmpty(result)) return new String[]{};
        
        String[] r = new String[result.size()];
        int index = 0;
        for (String s : result){
        	r[index] = s;
        	
        	index ++;
        }
        
        return r;
    }


	private static List<String> composeChar(List<String> subList,
			Set<Character> chars) {
		if (CollectionUtils.isEmpty(chars)) return new ArrayList<String>();
		
		List<String> result = new ArrayList<String>();
		for (Character c : chars) {
			if (CollectionUtils.isEmpty(subList)) {
				result.add(String.valueOf(c).trim());
				continue;
			}
			
			for (String sub : subList) {
				result.add((sub + String.valueOf(c)).trim());
			}
		}
		
		return result;
	}


	/**
     * 查出给出的汉字的拼音。
     * @param key
     * @return
     */
    public static String[] getPinyin(String key) {

        if (key == null) {
            return null;
        }
        
        List<Set<String>> acronyms = new ArrayList<Set<String>>();
        for (int i = 0; i < key.length(); i++) {
        	
            char ch = key.charAt(i);
            Set<String> acronym = getCharPinyin(ch);
            acronyms.add(acronym);
        }
        
        List<String> result = new ArrayList<String>();
        if (CollectionUtils.isNotEmpty(acronyms)) {
        	for (Set<String> chars : acronyms) {
        		List<String> sub = composeStr(result, chars);
        		
        		result = sub;
        	}
        }
        
        if (CollectionUtils.isEmpty(result)) return new String[]{};
        
        String[] r = new String[result.size()];
        int index = 0;
        for (String s : result){
        	r[index] = s;
        	
        	index++;
        }
        
        return r;
        
    }
    
	public static String getAcronymFromPinyin(String full) {

		if (StringUtils.isEmpty(full))
			return "";

		String[] words = full.split(" ");

		StringBuffer acronym = new StringBuffer("");
		for (int j = 0; j < words.length; j++) {
			String word = words[j].trim().toUpperCase();
			if (StringUtils.isEmpty(word)) {
				log.info("in-correct pinyin : " + full);
				continue;
			}
			
			acronym.append(word.charAt(0));
		}

		return acronym.toString();
	}
    

    private static List<String> composeStr(List<String> subList, Set<String> strs) {
    	if (CollectionUtils.isEmpty(strs)) return new ArrayList<String>();
		
		List<String> result = new ArrayList<String>();
		for (String c : strs) {
			if (CollectionUtils.isEmpty(subList)) {
				result.add(c.trim());
				continue;
			}
			
			for (String sub : subList) {
				result.add((sub + " " + c).trim());
			}
		}
		
		return result;
	}

	/**
     * 查出给出的汉字的拼音。
     * @param key
     * @return
     */
    public static Set<String> getCharPinyin(char ch) {

    	Set<String> acronym = new LinkedHashSet<String>();
        String ascii = Integer.toHexString(ch).toUpperCase();
        String[] values = pinyinMap.get(ascii);
        if(values==null) {
        	acronym.add(String.valueOf(ch));
        }
        else {
        	for(int v=0; v<values.length; v++) {
        		String value = values[v];
        		if (value.trim().toUpperCase().length()>0) {
        			acronym.add(value.trim());
        		}
        	}
        }
        
        return acronym;
    }
    
    public static String[] judgeByPinyin(String key){
		char ch = key.charAt(0);
        String ascii = Integer.toHexString(ch).toUpperCase();
        String[] value = pinyinMap.get(ascii);
        return value;
    }

    public static void main(String[] args) {
//        PinyinUtil.getPinyinAcronym("Robert F. Ohmes");
    	String[] v = PinyinUtil.judgeByPinyin("z");
        log.info(v);
    }
    
    public static void Load() {
    	//do nothing, call this method will load Pinyin static data
    }

	public static boolean isChinese(String key) {
		
		//判断是否为英文
		char ch = key.charAt(0);
        String ascii = Integer.toHexString(ch).toUpperCase();
        String[] value = PinyinUtil.pinyinMap.get(ascii);//value空为英文，否则为中文
        if(value==null || value.length <= 0){
        	return false;
        }else{
        	return true;
        }
	}

}
