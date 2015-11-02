package com.gemantic.commons.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bsming.common.constant.SignConstant;
import com.bsming.common.util.StringUtil;

public class RMath extends TagSupport {  

	private static final Log log = LogFactory.getLog(RMath.class) ;
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -3284541362853873891L;
	private Object value;  

    @Override
    public int doStartTag() throws JspException {
    	if(value == null) {
    		return super.doStartTag();
    	}
    	 String s = StringUtil.trim(value.toString());
    	if(s.startsWith(SignConstant.SIGN_MINUS)){
    	 	s = s.substring(s.indexOf(SignConstant.SIGN_MINUS)+1,s.length());
    	}
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

  
}