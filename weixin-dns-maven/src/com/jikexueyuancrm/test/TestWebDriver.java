package com.jikexueyuancrm.test;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriver.SystemProperty;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.jikexueyuancrm.entity.ArcticleRequest;
import com.jikexueyuancrm.util.HttpUtils;
import com.jikexueyuancrm.util.MobileUserAgent;
import com.jikexueyuancrm.util.ThreadLocalClientFactory;

public class TestWebDriver {
	private static Logger log = Logger.getLogger(TestWebDriver.class);
	static String url ="https://localhost/mp/getmasssendmsg?__biz=MzA3NDMzODk5Mw==&uin=MTkwOTM5OTQ4MA%3D%3D&key=77421cf58af4a653b1eecd5c560d65b409b28c9b35dcce49726866796819702ba88e8ade203eebf9fc838844db73d14e&devicetype=android-19&version=2603123a&lang=zh_CN&nettype=WIFI&pass_ticket=UJ4uAKjKvi5eibuMJLOttU2MC1uJ74xAkoPBKShridrxEVLZvtuBH%2BacaFKjr5QV";
	
	@Test
	public void testJson(){
		
	String json=	HttpUtils.getJsonByHtmlUnit(url, null,null, MobileUserAgent.WeiXin_Version_6_3_18_WebView);
		

		log.info(json);
	}
	
	
	//模拟往下拉异步加载数据(相当于下拉滚动条),执行window.scrollBy(0,20000)成功页面没有变化
	@Test
	public void testAsyncLoad() throws Exception{
		
		WebClient	webClient = ThreadLocalClientFactory.getInstance().getWebClient();
		
		WebRequest request=new WebRequest(new URL(url)); 
		
		HtmlPage page = webClient.getPage(request);
		
		log.info(Jsoup.parse(page.asXml()).select("div.msg_list").size());
		 ScriptResult scriptResult=	 page.executeJavaScript("window.scrollBy(20,100000)");
		webClient.waitForBackgroundJavaScript(10000);
	HtmlPage newPage=	(HtmlPage) scriptResult.getNewPage();
	
log.info(Jsoup.parse(newPage.asXml()).select("div.msg_list").size());
	}
	
	@Test
	public void testChromeDriver() {
		
		//ChromeDriver下载地址
		//ChromeDriver v2.15 Supports Chrome v40-43
		
		//ChromeDriver v2.9只支持Chrome v31-34
		//设置chromedriver路径
		System.setProperty("webdriver.chrome.driver", "c:\\chromedriver_win32\\chromedriver.exe");
		
		WebDriver driver = new ChromeDriver();
		driver.get("http://www.baidu.com/");  
		driver.close();//关闭浏览器
		
		driver.quit();//关闭java程序，退出虚拟机
		
	}
	
	@Test
	public void testFireFoxDriver() {
		WebDriver driver = new FirefoxDriver();
		
	//	WebDriver driver1 = new InternetExplorerDriver();
		//指定火狐浏览器路径
		System.setProperty(SystemProperty.BROWSER_BINARY, "C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
		
		driver.get("http://www.baidu.com/");  
		

	}	
	
	//
public static void main(String[] args) {
	
	
}
}