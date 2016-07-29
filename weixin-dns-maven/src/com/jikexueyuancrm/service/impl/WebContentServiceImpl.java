package com.jikexueyuancrm.service.impl;

import org.hibernate.FlushMode;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.jikexueyuancrm.entity.WebContent;
import com.jikexueyuancrm.entity.WxTitleImg;

@Service

public class WebContentServiceImpl extends HibernateDaoSupport  {
	
	
	
	
	
	
	
	//给HibernateDaoSupport注入SessionFactory
	@Autowired 
    public void setSuperSessionFactory(SessionFactory sessionFactory) {  
       super.setSessionFactory(sessionFactory);  
    }  
	

	
	public void save(WebContent webContent ){
		
		
		this.currentSession().setFlushMode(FlushMode.AUTO);
		//等价于this.getHibernateTemplate().getSessionFactory().getCurrentSession().setFlushMode(FlushMode.AUTO);
		this.getHibernateTemplate().save(webContent);
		
	}




	
	
	
	
}
