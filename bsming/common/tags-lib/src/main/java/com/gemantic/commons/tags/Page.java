package com.gemantic.commons.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Page extends TagSupport {
	private static final Log log = LogFactory.getLog(Page.class);	
	private String totalCount;
	private String totalPage;
	private String currentPosition;
	private String order;
	
	private static final String ORDER_ASC = "asc";
	private static final String BLANK = "0";
	
    @Override
    public int doStartTag() throws JspException {
    	
        String s = "";
        
        if(this.order == null) {
        	this.order = ORDER_ASC;
        }
        
        //position传空值的话，返回-1
        if(StringUtils.isBlank(this.currentPosition)) {
            return writePageContextOut(BLANK);
        }
        
        if(!StringUtils.isBlank(this.totalCount) && 
        		!StringUtils.isBlank(this.totalPage)) {
        	
            long totalCount = Long.valueOf(""+this.totalCount);
            long totalPage = Long.valueOf(""+this.totalPage);
            long currentPosition = Long.valueOf(""+this.currentPosition);
        	
            if(totalPage == 0 || totalCount == 0) {
                return writePageContextOut(s);
            }
            
            long pageSize = totalCount/totalPage;
            long page ;
            if(pageSize==0){
            	page=Long.valueOf(this.currentPosition);            	
            }else{
            	page= currentPosition%pageSize == 0 ? currentPosition/pageSize : currentPosition/pageSize + 1;
            }
            
           
            
            if(page > totalPage) {
            	page = totalPage;
            }
            
            s = String.valueOf(this.order.equalsIgnoreCase(ORDER_ASC) ? page : totalPage - page + 1);
        }
        
        return writePageContextOut(s);
    }

	private int writePageContextOut(String s) throws JspException {
		try {
            pageContext.getOut().write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.doStartTag();
	}
	
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public void setTotalPage(String totalPage) {
		this.totalPage = totalPage;
	}

	public void setCurrentPosition(String currentPosition) {
		this.currentPosition = currentPosition;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	
	public static void main(String[] args) {
		
		Long t=3L;
		Long p=4L;
		Long s=t/p;
	    log.info(s);
		
	}
}