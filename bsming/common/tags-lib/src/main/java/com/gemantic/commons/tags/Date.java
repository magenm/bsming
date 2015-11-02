package com.gemantic.commons.tags;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bsming.common.util.CalendarUtil;

public class Date extends TagSupport {  

	private static final Log log = LogFactory.getLog(Date.class) ;
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -3284541362853873891L;
	private Object value;  

	private String pattern ;
	
    @Override
    public int doStartTag() throws JspException {
    	if(value == null) {
    		return super.doStartTag();
    	}


    	long time  = 0 ;
    	if(value instanceof java.util.Date) {
    		time = ((java.util.Date)value).getTime() ;
    	}else if(value instanceof Calendar) {
    		time = ((Calendar)value).getTimeInMillis() ;
    	}else if(value instanceof Integer) {


    		time = CalendarUtil.convertYyyyMMdd2Date((Integer)value).getTime() ;
    	}else if(value instanceof Long || value instanceof String) {
    		try {
    			time = Long.valueOf(""+value) ;
    		}catch(Exception e) {
    			log.info(value + " : invalid value.", e) ;
    			return super.doStartTag(); 
    		}
    	}else {
    		log.info(value + " : invalid type.") ;
    		return super.doStartTag();
    	}
    	
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
//        SimpleDateFormat dateformat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateformat =new SimpleDateFormat(pattern);
        String s = dateformat.format(c.getTime());
        try {
            pageContext.getOut().write(s);
        } catch (IOException e) {
            log.info(null, e) ;
        }
        return super.doStartTag();
    }
    
    public void setValue(Object value) {  
        this.value = value;  
    }

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
}