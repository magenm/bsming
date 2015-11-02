package com.bsming.common.util.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.nio.client.DefaultHttpAsyncClient;
import org.apache.http.nio.concurrent.FutureCallback;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.params.SyncBasicHttpParams;
import org.apache.http.util.EntityUtils;
import org.apache.http.nio.reactor.IOReactorExceptionHandler;

/**
 * @author pengxia
 * 
 * HTTP Post / Get
 */
public class HttpNIOClientUtil {
    /**
     * 
     */
    private static final Log log = LogFactory.getLog(HttpNIOClientUtil.class);
    /**
     * 默认字符集
     */
    public static final String DefaultEncoding = "utf-8"; 
    /**
     * 
     */
    private MultiThreadedHttpConnectionManager httpClientManager;
    /**
     * 
     */
    private DefaultHttpAsyncClient httpclient ;
    /**
     * 超时时间
     */
	private int connectionTimeOut=2000;
    /**
     * 
     */
    private static HttpNIOClientUtil _instance;

    /**
     * @return
     * @throws Exception
     */
    public static HttpNIOClientUtil getInstance() throws Exception {
        if (_instance == null) {
            synchronized (HttpNIOClientUtil.class) {
                if (_instance == null) {
                    _instance = new HttpNIOClientUtil();
                }
            }
        }
        return _instance;
    }

    /**
     * 
     * @throws Exception .
     */
    private HttpNIOClientUtil() throws Exception {
        init();
    }

    /**
     * 
     * @throws Exception .
     */
    private void init() throws IOReactorException {
		HttpParams params = new SyncBasicHttpParams();	
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpConnectionParams.setSocketBufferSize(params, 8192);
	
		HttpConnectionParams.setConnectionTimeout(params, connectionTimeOut);		
		HttpConnectionParams.setSoTimeout(params, 3000);
		
		httpclient = new DefaultHttpAsyncClient(params);
		httpclient.start();
        
    }

    
    public String post(HttpUrl httpUrl,Cookie[] cookies) throws Exception {    
    	
    	
    	
    	HttpClientParams.setCookiePolicy(httpclient.getParams(), CookiePolicy.BROWSER_COMPATIBILITY);
    	
        for(Cookie cookie:cookies){
        	httpclient.getCookieStore().addCookie(cookie);
        }
        return this.post(httpUrl);
    }
    /**
     * 
     * @param httpUrl
     * @throws Exception
     */
    public String post(HttpUrl httpUrl) throws Exception {

        if (httpUrl == null)
            return null;

        HostConfiguration config = new HostConfiguration();
        config.setHost(httpUrl.getHost(), httpUrl.getPort());

        PostMethod post = new PostMethod(httpUrl.getPath());
       

        
        List<Cookie> cookies = httpclient.getCookieStore().getCookies();
        int size=cookies.size();
        if (cookies != null &&  size> 0) {
            log.debug("Present cookies : ");
            for (int i = 0; i < size; i++) {
                log.debug(i + " : " + cookies.get(i).getCommentURL()+ " - domain :" +cookies.get(i).getDomain()
                        + " - value :" + cookies.get(i).getValue());
            }
        }
        
        
       final CountDownLatch latch = new CountDownLatch(1);
       final HttpPost httpPost=HttpNIOClientUtil.convert2HttpPost(httpUrl);
       final int result=0;
        httpclient.execute(httpPost, new FutureCallback<HttpResponse>() {  

            

            public void completed(final HttpResponse response) {  
                log.info(httpPost.getRequestLine()+" -> "+response.getStatusLine());                
                latch.countDown();  
               

            }  



            public void failed(final Exception ex) {  
            	

                latch.countDown();  

                ex.printStackTrace();  
                log.error(" error "+httpPost.getRequestLine());

            }  



            public void cancelled() {  

                latch.countDown();           
                log.error(" cancel "+httpPost.getRequestLine());

            }  



        });  
        
        latch.await();  
        
        
        log.debug("HttpClient.executeMethod returns result = [" + result + "]");
        log.debug("HttpClient.executeMethod returns :");
      

        return "";
    }

    private static HttpPost convert2HttpPost(HttpUrl httpUrl) {
		// TODO Auto-generated method stub
		return null;
	}

	
    /**
     * HTTP Get
     * @param httpUrl
     * @return
     * @throws Exception .
     */
    public String get(String url) throws Exception {
       
        final HttpGet httpGet=new HttpGet(url);     
        final CountDownLatch latch=new CountDownLatch(1);
        final HttpUrl httpUrl=new HttpUrl();
         httpclient.execute(httpGet, new FutureCallback<HttpResponse>() {  

             

             public void completed(final HttpResponse response) {  
                 log.info(httpGet.getRequestLine()+" -> "+response.getStatusLine());     
                 try {
                	 String content =EntityUtils.toString(response.getEntity(),"utf-8");
                	 log.info(content);
                	 httpUrl.setResult(content);
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				 
                 
                 
                
				latch.countDown();  
                

             }  



             public void failed(final Exception ex) {  
             	

                 latch.countDown();  

                 ex.printStackTrace();  
                 log.error(" error "+httpGet.getRequestLine());

             }  



             public void cancelled() {  

                 latch.countDown();           
                 log.error(" cancel "+httpGet.getRequestLine());

             }  



         });  
         
         latch.await();  
         

        return httpUrl.getResult();
    }
    
   

	
    
    /**
     * 
     * @param params
     * @param get .
     * @throws UnsupportedEncodingException 
     */
    private void setParams(HashMap<Object , Object> params, GetMethod get) throws UnsupportedEncodingException {
        StringBuffer strParams = new StringBuffer();

        if (params != null && params.size() > 0) {
            Iterator<Object> keys = params.keySet().iterator();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                strParams.append(key);
                strParams.append("=");
                String p=(String) params.get(key);
                p=java.net.URLEncoder.encode(p,"utf-8");
                strParams.append(p);
                strParams.append("&");
            }
        }

        if (strParams.lastIndexOf("&") == strParams.length()-1)
            strParams.deleteCharAt(strParams.length()-1);

        get.setQueryString(strParams.toString());
    }
    
    
   
 
    
    


 


}
