package com.jikexueyuancrm.util;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;

public class HttpUtils {
	
	private static Logger log = Logger.getLogger(HttpUtils.class);
	
	public static String getHtmlByHtmlUnit(String url,  Set<Cookie> setCookies, String Referer ,String UserAgent) {
		try{
			
			WebClient webClient = new WebClient(BrowserVersion.FIREFOX_17);
			webClient.getCookieManager().setCookiesEnabled(true);//开启cookie管理
			webClient.getOptions().setJavaScriptEnabled(true);//开启js解析
			webClient.getOptions().setCssEnabled(true);
			webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
			webClient.getOptions().setThrowExceptionOnScriptError(true);
			webClient.getOptions().setTimeout(20000);
			//https
			webClient.getOptions().setUseInsecureSSL(true);
			webClient.getOptions().setRedirectEnabled(true);
			CookieManager cookieManager = webClient.getCookieManager();
			if(setCookies!=null){
			for(  Cookie wxCookie :setCookies ){
				cookieManager.addCookie(wxCookie);
			}
			}
			
			WebRequest request=new WebRequest(new URL(url)); 
			request.setCharset("UTF-8");
			if(Referer!=null){
			request.setAdditionalHeader("Referer", Referer);
			}
		/*	request.setProxyHost("127.0.0.1");
			request.setProxyPort(8888);
			//用于Fiddler
			*/
			
			//添加useragent
			if(UserAgent!=null){
			log.info(UserAgent);
			request.setAdditionalHeader("User-Agent", UserAgent);
			}	
			
			
			
			HtmlPage page = webClient.getPage(request);
			
		//	WebResponse webResponse = page.getWebResponse();
			
			//webResponse.cleanUp();
			  webClient.waitForBackgroundJavaScript(1000*5);  
		        webClient.setJavaScriptTimeout(0);
			String pageXml = page.asXml(); // 以xml的形式获取响应文本
			
			//webClient.closeAllWindows();
			return pageXml;
		}catch(Exception e){
			log.error("HtmlUnit exception",e);
			e.printStackTrace();
			return null;
		}	
	}

	
	
	//返回json
	public static String getJsonByHtmlUnit(String url,  Set<Cookie> setCookies, String Referer ,String UserAgent) {
		
		
		try{
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_17);
		webClient.getCookieManager().setCookiesEnabled(true);//开启cookie管理
		webClient.getOptions().setJavaScriptEnabled(true);//开启js解析
		webClient.getOptions().setCssEnabled(true);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setTimeout(20000);
		//https
		webClient.getOptions().setUseInsecureSSL(true);
		webClient.getOptions().setRedirectEnabled(true);
		CookieManager cookieManager = webClient.getCookieManager();
		if(setCookies!=null){
		for(  Cookie wxCookie :setCookies ){
			cookieManager.addCookie(wxCookie);
		}
		}
		
		WebRequest request=new WebRequest(new URL(url)); 
		request.setCharset("UTF-8");
		if(Referer!=null){
		request.setAdditionalHeader("Referer", Referer);
		}
	/*	request.setProxyHost("127.0.0.1");
		request.setProxyPort(8888);
		//用于Fiddler
		*/
		
		//添加useragent
		if(UserAgent!=null){
		log.info(UserAgent);
		request.setAdditionalHeader("User-Agent", UserAgent);
		}	
		
		
		
		Page page = webClient.getPage(request);
		
	//	WebResponse webResponse = page.getWebResponse();
		
		//webResponse.cleanUp();
		
		WebResponse webResponse   = page.getWebResponse();
		
		String json="";
		if (webResponse.getContentType().equals("application/json")) {
			
			 json = webResponse.getContentAsString();
			
		}
		
		//webClient.closeAllWindows();
		return json;
	}catch(Exception e){
		log.error("HtmlUnit exception",e);
		e.printStackTrace();
		return null;
	}
	}
	
	
	
	
	 /**
     * 解析出url参数中的键值对
     * 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     * @param URL  url地址
     * @return  url请求参数部分
     */
    public static Map<String, String> URLRequest(String URL)
    {
    Map<String, String> mapRequest = new HashMap<String, String>();
   
      String[] arrSplit=null;
     
    String strUrlParam=TruncateUrlPage(URL);
    if(strUrlParam==null)
    {
        return mapRequest;
    }
      //每个键值为一组 www.2cto.com
    arrSplit=strUrlParam.split("[&]");
    for(String strSplit:arrSplit)
    {
          String[] arrSplitEqual=null;         
          arrSplitEqual= strSplit.split("[=]");
         
          //解析出键值
          if(arrSplitEqual.length>1)
          {
              //正确解析
              mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
             
          }
          else
          {
              if(arrSplitEqual[0]!="")
              {
              //只有参数没有值，不加入
              mapRequest.put(arrSplitEqual[0], "");       
              }
          }
    }   
    return mapRequest;   
    }
	
    
    
    /**
     * 去掉url中的路径，留下请求参数部分
     * @param strURL url地址
     * @return url请求参数部分
     */
    private static String TruncateUrlPage(String strURL)
    {
    String strAllParam=null;
      String[] arrSplit=null;
     
      strURL=strURL.trim();
     
      arrSplit=strURL.split("[?]");
      if(strURL.length()>1)
      {
          if(arrSplit.length>1)
          {
                  if(arrSplit[1]!=null)
                  {
                  strAllParam=arrSplit[1];
                  }
          }
      }
     
    return strAllParam;   
    }
}
