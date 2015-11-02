package com.gemantic.commons.tags;



import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bsming.common.util.PinyinUtil;

public class PinYin extends TagSupport {  

	private static final Log log = LogFactory.getLog(PinYin.class) ;
	
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -3284541362853873891L;
	private String pattern;	
	private String value ;
	
    @Override
    public int doStartTag() throws JspException {
    
    	String result="";
    	if("full".equals(pattern)){
    		//全拼,多音字都只取第一个
    		String[] full_pinyins = PinyinUtil.getPinyin(value);
    		result=full_pinyins[0];
    	}else{
    		String[] acronym_pinyins = PinyinUtil.getPinyinAcronym(value);		//这儿有问题.多音字会给出数据出
    		result=acronym_pinyins[0];
    	}
    
    	
        try {
            pageContext.getOut().write(result);
        } catch (IOException e) {
        	log.error(result);
            log.error(null, e) ;
        }
        return super.doStartTag();
    }
    
    
    

	

	public String getPattern() {
		return pattern;
	}






	public void setPattern(String pattern) {
		this.pattern = pattern;
	}






	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
    
    
    
    
   
}