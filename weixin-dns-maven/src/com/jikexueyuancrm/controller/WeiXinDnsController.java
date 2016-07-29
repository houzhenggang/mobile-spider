package com.jikexueyuancrm.controller;

import java.net.URI;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.MidiDevice.Info;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jikexueyuancrm.entity.App_Msg_Ext_Info;
import com.jikexueyuancrm.entity.ArcticleRequest;
import com.jikexueyuancrm.entity.Comm_Msg_Info;
import com.jikexueyuancrm.entity.HistoryArcticlesRequest;
import com.jikexueyuancrm.service.impl.App_Msg_Ext_InfoServiceImpl;
import com.jikexueyuancrm.service.impl.ArcticleLinkServiceImpl;
import com.jikexueyuancrm.service.impl.Comm_Msg_InfoServiceImpl;
import com.jikexueyuancrm.service.impl.HistroyArcticlesLinkServiceImpl;
import com.jikexueyuancrm.util.HttpUtils;
import com.jikexueyuancrm.util.JsonUtils;
import com.jikexueyuancrm.util.MobileUserAgent;



@Controller
public class WeiXinDnsController {
	private static Logger log = Logger.getLogger(WeiXinDnsController.class);
	
	@Autowired
	private HistroyArcticlesLinkServiceImpl  histroyArcticlesLinkServiceImpl ;
	
	@Autowired
	private ArcticleLinkServiceImpl  arcticleLinkServiceImpl ;
	
	@Autowired
	private	Comm_Msg_InfoServiceImpl comm_Msg_InfoServiceImpl;
	
	@Autowired
	private  App_Msg_Ext_InfoServiceImpl app_Msg_Ext_InfoServiceImpl;
	
	//https请求
	@ResponseBody
	@RequestMapping(value="mp/getmasssendmsg")
    //SpringMVC默认处理的字符集是ISO-8859-1
	public Object historyLink(HttpServletRequest request, HttpServletResponse response){
		
		long start =System.currentTimeMillis();
		
		String fullUrl = request.getRequestURL().toString() +"?" +request.getQueryString();
		
		//还原url
		fullUrl=fullUrl.replace("http", "https").replace("localhost", "mp.weixin.qq.com");
		
		
		HistoryArcticlesRequest historyArcticlesRequest =new HistoryArcticlesRequest(); 
		historyArcticlesRequest.setFullurl(fullUrl);
		historyArcticlesRequest.setBiz(request.getParameter("__biz"));	
		historyArcticlesRequest.setUin(request.getParameter("uin"));
		historyArcticlesRequest.setAppkey(request.getParameter("key"));
		historyArcticlesRequest.setPass_ticket(request.getParameter("pass_ticket"));
			log.info("fullUrl:"+fullUrl);
		   log.info("biz:"+historyArcticlesRequest.getBiz());
           log.info("uin:"+historyArcticlesRequest.getUin());
           log.info("key:"+historyArcticlesRequest.getAppkey());
           log.info("pass_ticket:"+historyArcticlesRequest.getPass_ticket());
		
		if(request.getParameter("f")!=null&&request.getParameter("f").equals("json")){
			//异步加载
			historyArcticlesRequest.setFrommsgid(request.getParameter("frommsgid"));
			
			historyArcticlesRequest.setCount(Integer.valueOf(request.getParameter("count")));
			log.info("异步加载");
			 log.info("frommsgid:"+historyArcticlesRequest.getFrommsgid());
			 log.info("count:"+historyArcticlesRequest.getCount());
			 
			    //获取json结果
	          String json= HttpUtils.getJsonByHtmlUnit(fullUrl, null, null,MobileUserAgent.WeiXin_Version_6_3_18_WebView);
	           log.info("jsonResult:"+json); 
	           
	           //解析json结果，并转换成对象
	           JSONObject      jsonObject =   JSONObject.fromObject(json);
	          if( jsonObject.get("errmsg").equals("ok")){
	        	
	        	String general_msg_list=  (String) jsonObject.get("general_msg_list");
	        //遍历json树	  
	       JsonUtils.decodeJSONObject(JSONObject.fromObject(general_msg_list));
	       
	       JSONArray jsonArray= (JSONArray) JSONObject.fromObject(general_msg_list).get("list");
	        	
	                      for(int i=0;i<jsonArray.size();i++){
	                    	  
	                    	  JSONObject jo =  jsonArray.getJSONObject(i);
	                    	  
	                    	  JSONObject	  comm_msg_info=  (JSONObject) jo.get("comm_msg_info");
	                    	  Comm_Msg_Info cmi=	  (Comm_Msg_Info) JSONObject.toBean( comm_msg_info, Comm_Msg_Info.class);
	                    	  comm_Msg_InfoServiceImpl.save(cmi);
	                    	  
	                    	  
	                    	  JSONObject	  app_msg_ext_info=  (JSONObject) jo.get("app_msg_ext_info");
	                    	  ArcticleRequest arcticleRequest =new ArcticleRequest();
	                    	  arcticleRequest.setHistoryActiclesLink(fullUrl);
	                    	 
	                    	  arcticleRequest.setContent_url( ((String) app_msg_ext_info.get("content_url")).replace("&amp;", "&")); 
	                    	  arcticleRequest.setSource_url( ((String) app_msg_ext_info.get("source_url")).replace("&amp;", "&")); 
	                    	 
	                    	  
	                    	  
	                    	  arcticleLinkServiceImpl.save(arcticleRequest);
	                    	  
	                    	  JSONArray	  multi_app_msg_item_list=  (JSONArray) app_msg_ext_info.get("multi_app_msg_item_list");
	                    	
	                    	  if(multi_app_msg_item_list!=null &&multi_app_msg_item_list.size()>0){
	                    		  
	                    		  log.info(multi_app_msg_item_list.size());
	                    	  for(int j=0;j<multi_app_msg_item_list.size();j++){
	                    		  JSONObject mamil =  multi_app_msg_item_list.getJSONObject(j);
	                    		  ArcticleRequest ar =new ArcticleRequest();
	                    		  
	                    		  ar.setHistoryActiclesLink(fullUrl);
	                    		  ar.setContent_url(((String) mamil.get("content_url")).replace("&amp;", "&"));
	                    		  ar.setSource_url(((String) mamil.get("source_url")).replace("&amp;", "&"));
	                    		  arcticleLinkServiceImpl.save(ar);
	                    	  }
	                    	  
	                    	  }
	                      }
	      
	          };
	           
	           
			 
		}else{
			
			  //爬取网页
	          String page= HttpUtils.getHtmlByHtmlUnit(fullUrl, null, null,MobileUserAgent.WeiXin_Version_6_3_18_WebView);
	           if(page!=null){
	        	   log.info(page);
	        	   
	        	   Document document=Jsoup.parse(page);
	        	   Elements  msg_list= document.select("div.msg_list");
	        	   
				for (Element msg : msg_list) {
					Elements	msg_cover	=msg.select("a.msg_cover");
					if(msg_cover.size()>0){
					
					String coverUrl = msg_cover.first().attr(
							"hrefs");
					
					if(coverUrl!=null&&!coverUrl.trim().isEmpty()){
					ArcticleRequest ar = new ArcticleRequest();
					ar.setHistoryActiclesLink(fullUrl);
					ar.setContent_url(coverUrl.replace("http","https"));
					arcticleLinkServiceImpl.save(ar);
					}
					
					
					
					}
					
					 Elements	sub_msg_items= msg.select("a.sub_msg_item");
						 
						 for(  Element   sub_msg_item:     sub_msg_items  ){
							 ArcticleRequest ar1 = new ArcticleRequest();
							 ar1.setHistoryActiclesLink(fullUrl);
							 ar1.setContent_url(sub_msg_item.attr("hrefs").replace("http","https")); 
							 arcticleLinkServiceImpl.save(ar1);
						 }
						 
					
					
				}
	        	   
	           }
	         
	           
		}
		
		
         
            histroyArcticlesLinkServiceImpl.save(historyArcticlesRequest);
     
            
            long end =System.currentTimeMillis();
    		log.info("handler time"+(end-start)/1000+"s");
          
            
		return historyArcticlesRequest;
	}
	
	//https请求
	@ResponseBody
	@RequestMapping(value="s")
 
	public Object article(HttpServletRequest request, HttpServletResponse response,String __biz,String mid,Integer idx,String sn,String 

key,String pass_ticket ){
		
		
		String fullUrl = request.getRequestURL().toString() +"?"+ request.getQueryString();
		log.info("fullUrl:"+fullUrl);
		
		fullUrl=fullUrl.replace("http", "https").replace("localhost", "mp.weixin.qq.com");
		
		ArcticleRequest arcticleRequest =new ArcticleRequest(); 
		
		
		arcticleRequest.setBiz(__biz);
		arcticleRequest.setMid(mid);
		arcticleRequest.setIdx(idx);
		arcticleRequest.setSn(sn);
		
		
		arcticleRequest.setContent_url(fullUrl);
		arcticleRequest.setPass_ticket(pass_ticket);
		arcticleRequest.setAppkey(key);
		
		
		
		
		log.info("biz:"+__biz);
		log.info("mid:"+mid);
		log.info("idx:"+idx);
		log.info("sn:"+sn);
		log.info("key:"+key);
		log.info("pass_ticket:"+pass_ticket);
		
		arcticleLinkServiceImpl.save(arcticleRequest);
		
		
		return arcticleRequest;
	}
	
	
	
	
	
	//
	
	
	
	
	
	//http请求
	@ResponseBody
	@RequestMapping("/mmbiz/**")//匹配0或者更多的目录

	public Object image(HttpServletRequest request, HttpServletResponse response){
		
		log.info(System.currentTimeMillis()+"劫持请求了");
		
		log.info(request.getRemoteAddr()+request.getRequestURL());
		return "劫持请求了";
	}
	
	
	
	
	

}
