package com.qding.community.common.weixin.util;

import org.junit.Test;
import com.qding.community.common.weixin.util.ParseJsonUtil;
import groovy.json.JsonSlurper


 class ParseJsonUtilImpl  implements ParseJsonUtil{
	
	 
	
		String parseJson(String content,String path){

        def slurper = new JsonSlurper()
       
        def states = slurper.parseText(content)

    
        return  states[path];

		};
	
    public static void main(String[] args) {
		
	}

}
