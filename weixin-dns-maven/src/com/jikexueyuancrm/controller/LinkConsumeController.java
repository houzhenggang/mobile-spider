package com.jikexueyuancrm.controller;



import java.util.Date;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;










import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jikexueyuancrm.entity.ArcticleRequest;
import com.jikexueyuancrm.entity.WebContent;
import com.jikexueyuancrm.entity.WxTitleImg;
import com.jikexueyuancrm.service.impl.ArcticleLinkServiceImpl;
import com.jikexueyuancrm.service.impl.WebContentServiceImpl;
import com.jikexueyuancrm.service.impl.WxTitleImgServiceImpl;
import com.jikexueyuancrm.util.DateUtils;
import com.jikexueyuancrm.util.HttpUtils;
import com.jikexueyuancrm.util.MobileUserAgent;
import com.jikexueyuancrm.util.StringHelper;





@Component
public class LinkConsumeController {
	private static Logger log = Logger.getLogger(LinkConsumeController.class);
	
		// 来源类型
		private static String sourceType = "wx";
		// 来源
		private static String captureWebsite = "微信";
	
	
	@Autowired
	private ArcticleLinkServiceImpl  arcticleLinkServiceImpl ;
	
	@Autowired
	private  WebContentServiceImpl webContentServiceImpl;
	@Autowired
	private 	WxTitleImgServiceImpl wxTitleImgServiceImpl;
	
	public void consume(){
		
		long start =System.currentTimeMillis();
		try {
		log.info("开始执行");
		
	
		
		//数据库中取出一个文章链接
		
		ArcticleRequest ar = arcticleLinkServiceImpl.get();
		
		if(ar==null){
			
			return;
		}
		
		
		
		//发起请求,解析文章
		
		String url=ar.getContent_url();
		String str = HttpUtils.getHtmlByHtmlUnit(url, null, ar.getHistoryActiclesLink(), MobileUserAgent.WeiXin_Version_6_3_18_WebView);
		
		
		
		//若发生网络请求异常
		if(str==null){
			return ;
		}
		
		Document doc =Jsoup.parse(str);
				
				
	
		//文章不存在的时候返回
		if(doc.select("#js_content").isEmpty()){
			return ;
		}
		// 内容
		String content = doc.select("#js_content").first().html();
		content = content.replaceAll("data-src", "src");
		content = StringHelper.preProcessHaveImg(content);
		content = content.replaceAll("<img", "</br><img");
		//给图片标签加div标签
		content = content.replaceAll("</br><img", "</br><div class=\"wximg\"" +"><img");
		content = content.replaceAll("/>", "/></div>");

		// 标题
		String title = doc.select("#activity-name").first().html();
		title = title.replaceAll("&nbsp;", " ");
		String time = doc.select("#post-date").first().text();
		String poster = doc.select("#post-user").first().text();
		//设置参数
		WebContent webContent = new WebContent();
		if (title != null && title.length() > 50) {
			title = title.substring(0, 50) + "...";
		}
		if(!doc.select("#js_content").first().select("img").isEmpty()){
			String imgUrl = "";
			Elements imgs = doc.select("#js_content").first().select("img");
			//列表图片抓取逻辑：
			//1.只抓jpeg图片，gif不抓
			//2.文章图片大于2张，找到jpeg格式的图片就作为标题图片，如果找到第五张还没找到，则不再抓取标题图片；
			//3.文章图片等于2张，抓取第二张jpeg格式的图片;
			//4.文章图片等于1张，抓取jpeg格式的图片就作为标题图片，否则不抓；
			if(imgs!=null&&imgs.size()>0){
				if(imgs.size()>2){
					int i = 1;
					while(i<imgs.size()&&i<5){
						imgUrl = imgs.get(i).attr("data-src");
						if(imgUrl.indexOf("jpeg")!=-1){
							break;
						}else{
							imgUrl = "";
						}
						i++;
					}
				}else if(imgs.size()==2){
					imgUrl = imgs.get(1).attr("data-src");
				}else{
					imgUrl = imgs.get(0).attr("data-src");
				}
				if(imgUrl.indexOf("gif")!=-1 || imgUrl.indexOf("jpeg")==-1){
					imgUrl = "";
				}
				if(imgUrl!=null && !imgUrl.equals("") ){
					WxTitleImg titleImg = new WxTitleImg();
					titleImg.setContentUrl(url);
					titleImg.setFisrtImgurl(imgUrl);
					
					wxTitleImgServiceImpl.save(titleImg);
				}
			}
		}
		webContent.setTitle(title);
		webContent.setResourse(poster);
		webContent.setAuthor(poster);
		webContent.setUrl(url);
		webContent.setCaptureWebsite(captureWebsite);
		webContent.setContent(content);
		webContent.setOriginType(sourceType);
		webContent.setBrowseTime(0);
		webContent.setCommentTime(0);
		//设置发布时间
		webContent.setPublished(DateUtils.parse(time, DateUtils.FORMAT_SHORT));
		webContent.setBiz(ar.getBiz());
		
//		webContent.setCrawlerId(Configure.serverId);
		
		
		
		//保存
		
		
		
		 webContentServiceImpl.save(webContent);
		
		
		//更新删除链接
		
		ar.setStatus(false);
		 arcticleLinkServiceImpl.update( ar );
		
		
		
		
		
		
		
		log.info("执行完毕");
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		  
        long end =System.currentTimeMillis();
		log.info("handler time"+(end-start)/1000+"s");
		
	}
	

	
	
	
/*	 @Scheduled(cron="0/5 * * * * ? ") 
  public   void annotationMethod(){
	
	
	log.info( "进入测试" );
	
     }
	*/
	


}
