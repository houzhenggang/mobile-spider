package com.uia.example.my;
 



 






import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;





import java.util.concurrent.TimeUnit;

import util.FileUtils;
import net.sf.json.JSONObject;
import jp.jun_nama.test.utf7ime.helper.Utf7ImeHelper;








import android.os.Environment;




import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
 
public class Runner extends UiAutomatorTestCase {

public static	Map<String, byte[]> publicNumber  =new LinkedHashMap<String, byte[]>();
    //搜索未关注公众号
    public void testUnFollow() throws UiObjectNotFoundException, Exception {
    	
    	  System.out.println(Environment.getDataDirectory());//数据目录
    	    System.out.println(Environment.getDownloadCacheDirectory());//下载缓存目录
    	    System.out.println(Environment.getExternalStorageDirectory());//外部存储目录
    	   
    	
              getPublicNumber();
              
              
              
              
       	//启动微信app
       	 Runtime.getRuntime().exec("am start -n com.tencent.mm/com.tencent.mm.ui.LauncherUI");
       
       	sleep(5000);
       	
       	
   //   loginIn();
       	
 		
        //点击搜索
         	
         	UiObject search=new UiObject(new UiSelector().className(android.widget.TextView.class.getName()).description("搜索"));
         	search.clickAndWaitForNewWindow();
         	
       	//选择公众号
         	UiObject publicAccount=new UiObject(new UiSelector().className(android.widget.LinearLayout.class.getName()).index(2));
         //选择其下的imagebutton
      	UiObject imagebutton=  	publicAccount.getChild(new UiSelector().className(android.widget.ImageButton.class.getName()));
         	
      	imagebutton.clickAndWaitForNewWindow();
         	
       	int count =1;
       	String last="";
       	UiObject searchWord=null;
       	
       	
       	
       	for( String   pn:   publicNumber.keySet()){
       		             System.out.println(pn);
       					pn=pn.toLowerCase();
      
			if (count > 1) {

				searchWord=new UiObject(new UiSelector().className(android.widget.EditText.class.getName()).text(last));
		    	
				//searchWord.clearTextField();//不起作用
				searchWord.clickBottomRight();
				
				/*for (int i=0;i<searchWord.getText().length();i++) {
				    getUiDevice().pressDelete();
				}*/
			
			}
		
			
			// 输入待搜索公众号
			// Configurator.getInstance().setKeyInjectionDelay(40);

			searchWord = new UiObject(new UiSelector().className(android.widget.EditText.class.getName()).text("搜索公众号"));
			// searchWord1.setText(Utf7ImeHelper.e(pn)); //中文支持

			searchWord.setText(pn);
			
			
			TimeUnit.SECONDS.sleep(2);	
    //	Configurator.getInstance().setKeyInjectionDelay(0);
			getUiDevice().pressBack();
			TimeUnit.SECONDS.sleep(2);
			searchWord = new UiObject(new UiSelector().className(
					android.widget.EditText.class.getName()).text(pn));

			searchWord.click();
			TimeUnit.SECONDS.sleep(2);
			//必须两个都按
			getUiDevice().pressSearch();
			getUiDevice().pressEnter();
    	
    	  TimeUnit.SECONDS.sleep(6);
       	
    	//System.out.println(getUiDevice().getCurrentActivityName());
    int width=	getUiDevice().getDisplayWidth();
    System.out.println(width);
    int 	height=getUiDevice().getDisplayHeight();
	System.out.println(height);
    	getUiDevice().click(width/2, 250);
    
       	
    	TimeUnit.SECONDS.sleep(4);
        
        //得到微信号----微信号: xslishi
        
     //  UiObject weixin=new UiObject(new UiSelector().className(android.widget.TextView.class.getName()).resourceId("com.tencent.mm:id/a7w"));
    //  String str=weixin.getText();
       
      // System.out.println(str.substring(str.indexOf(":")+1).trim());
        
        //点击查看历史文章
        
        UiObject history=new UiObject(new UiSelector().className(android.widget.TextView.class.getName()).text("查看历史消息"));
   
        if(history.exists()){
        history.clickAndWaitForNewWindow(); 
        sleep(10000);
        getUiDevice().pressBack();
        getUiDevice().pressBack();
        }else{
       
        getUiDevice().pressBack();
      
      
        }
       
       count++;
       last=pn;
    	
    }
       	
       	
  
       	
}    

 	
    //非test开头的方法不执行
       public  static void   handleLocationMessage() throws UiObjectNotFoundException{
    		
    	   UiObject dialogue=new UiObject(new UiSelector().className(android.widget.TextView.class.getName()).text("提示"));	
    	   
    	   UiObject location=new UiObject(new UiSelector().className(android.widget.TextView.class.getName()).textContains("地理位置"));
		//api大于16
    	  if( dialogue.waitForExists(3000)&& location.waitForExists(3000)){
    		  
    		  
    		  UiObject cancel=new UiObject(new UiSelector().className(android.widget.Button.class.getName()).text("取消"));
    		  cancel.clickAndWaitForNewWindow();
    	  }
    
    	
    	  
    	}
    	
       //读取文本文件中的微信公众号
       
     
	public static  void getPublicNumber(){
		
		FileUtils.loadFile("/publicnumber.txt", publicNumber);
		System.out.println(publicNumber);
		
	}

	  
		public static  void loginIn() throws Exception{
			
			  //输入密码
	       	UiObject password = new UiObject( new UiSelector().className(android.widget.EditText.class.getName()));
	       	
	       	password.setText("csrhit7293380");
	       //点击等陆	
	       	UiObject login=new UiObject(new UiSelector().className(android.widget.Button.class.getName()).text("登录"));
	       	login.clickAndWaitForNewWindow();
	    	TimeUnit.SECONDS.sleep(15);	
			
		}
		
		
		

		public static  void loginOut() throws Exception{
			
			   /*    ----退出登录---- */	
            //点击“我”选项卡
       UiObject my=new UiObject(new UiSelector().className(android.widget.TextView.class.getName()).text("我"));
           my.clickAndWaitForNewWindow();  
            
            //点击设置
           UiObject settings=new UiObject(new UiSelector().className(android.widget.TextView.class.getName()).text("设置"));
           settings.clickAndWaitForNewWindow();  
           
           //点击退出
           UiObject exit=new UiObject(new UiSelector().className(android.widget.TextView.class.getName()).text("退出"));
           exit.clickAndWaitForNewWindow();
           
           //点击dialog上的退出当前账号或者关闭微信
           
           UiObject off=new UiObject(new UiSelector().className(android.widget.TextView.class.getName()).text("退出当前帐号"));
           off.clickAndWaitForNewWindow();
           
           
           //确认退出
           UiObject confirm=new UiObject(new UiSelector().className(android.widget.Button.class.getName()).text("退出"));
           confirm.clickAndWaitForNewWindow();
		
           
        
		}
}   


