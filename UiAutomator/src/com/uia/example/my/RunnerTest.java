package com.uia.example.my;
 



 




import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
 
public class RunnerTest extends UiAutomatorTestCase {
	
	
	
	/*
	
 //要支持中文的话项目编码必须用utf-8
    //搜索已关注公众号
    public void testDemo() throws UiObjectNotFoundException, Exception {
    	
    	
    	
    	
    	
 
     //  getUiDevice().click(x, y);
    	//启动微信app
    	 Runtime.getRuntime().exec("am start -n com.tencent.mm/com.tencent.mm.ui.LauncherUI");
    	sleep(5000);
    	
    	for(int i=0;i<2;i++){
    //输入密码
    	UiObject password = new UiObject( new UiSelector().className(android.widget.EditText.class));
    	
    	password.setText("csrhit7293380");
    //点击等陆	
    	UiObject login=new UiObject(new UiSelector().className(android.widget.Button.class).text("登录"));
    	login.clickAndWaitForNewWindow();
    	
    //	TimeUnit.SECONDS.sleep(3);
    	//点击“通讯录”选项卡
    	UiObject tongxunlu=new UiObject(new UiSelector().className(android.widget.TextView.class).text("通讯录"));
    	tongxunlu.clickAndWaitForNewWindow();
    	
    	//TimeUnit.SECONDS.sleep(3);
    //点击公众号
    	
    	UiObject gongzhonghao=new UiObject(new UiSelector().className(android.widget.TextView.class).text("公众号"));
    	gongzhonghao.clickAndWaitForNewWindow();
   
    	//获得公众号列表
    	
    	UiScrollable scroll=new UiScrollable(new UiSelector().className(android.widget.ListView.class));
    	
    	
    	//自定义扫动次数，滑动到结束为止
     //   scroll.scrollToEnd(10,10);
        System.out.println(scroll.getChildCount());//只能计算当前界面的子元素个数
    	
        scroll.scrollForward();//向前滚动一次
        sleep(3);
        scroll.scrollForward();//向前滚动一次
        sleep(3);
        scroll.scrollForward();//向前滚动一次
        sleep(3);
        //滑到指定公众号位置"君弘财富"
    	  UiObject test=new UiObject(new UiSelector().className(android.widget.TextView.class).text("君弘财富"));
          scroll.scrollIntoView(test);
          sleep(3000);
    	
         
        
          	//点击公众号
          test.clickAndWaitForNewWindow();
          //点击右上角聊天信息
          UiObject info=new UiObject(new UiSelector().className(android.widget.TextView.class).description("聊天信息"));
          info.clickAndWaitForNewWindow(); 
          //点击查看历史文章
          UiObject history=new UiObject(new UiSelector().className(android.widget.TextView.class).text("查看历史消息"));
         history.clickAndWaitForNewWindow(); 
    	
         sleep(10000);
         //回退键
         getUiDevice().pressBack();
         
         getUiDevice().pressBack();
         //回到公众号列表
         getUiDevice().pressBack();
         
         
         
         
         
         getUiDevice().pressBack();
         
         //点击“我”选项卡
         UiObject my=new UiObject(new UiSelector().className(android.widget.TextView.class).text("我"));
        my.clickAndWaitForNewWindow();  
         
         //点击设置
        UiObject settings=new UiObject(new UiSelector().className(android.widget.TextView.class).text("设置"));
        settings.clickAndWaitForNewWindow();  
        
        //点击退出
        UiObject exit=new UiObject(new UiSelector().className(android.widget.TextView.class).text("退出"));
        exit.clickAndWaitForNewWindow();
        
        //点击dialog上的退出当前账号或者关闭微信
        
        UiObject off=new UiObject(new UiSelector().className(android.widget.TextView.class).text("退出当前帐号"));
        off.clickAndWaitForNewWindow();
        
        
        //确认退出
        UiObject confirm=new UiObject(new UiSelector().className(android.widget.Button.class).text("退出"));
        confirm.clickAndWaitForNewWindow();
        
        
        sleep(5000);
        
        
    	}
    }
    
    
  

    
	
	
	
    public void testDarg() throws UiObjectNotFoundException{
    	
    	  UiObject settings=new UiObject(new UiSelector().className(android.widget.TextView.class).text("设置"));
    	  
    	  settings.clickAndWaitForNewWindow();
    	  getUiDevice().drag(0 ,700, 0, 0, 400);
    	
    	
    }*/
    
}   


