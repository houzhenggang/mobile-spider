package com.jikexueyuancrm.service.impl;


import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;




import com.jikexueyuancrm.entity.HistoryArcticlesRequest;

//spring下载地址
//http://repo.springsource.org/libs-release-local/org/springframework/spring/

@Service

public class HistroyArcticlesLinkServiceImpl extends HibernateDaoSupport  {
	
	
	
	
	
	
	
	//给HibernateDaoSupport注入SessionFactory
	@Autowired 
    public void setSuperSessionFactory(SessionFactory sessionFactory) {  
       super.setSessionFactory(sessionFactory);  
    }  
	

	
	public void save(HistoryArcticlesRequest historyArcticlesRequest) {
		
		
		this.currentSession().setFlushMode(FlushMode.AUTO);
		//等价于this.getHibernateTemplate().getSessionFactory().getCurrentSession().setFlushMode(FlushMode.AUTO);
		this.getHibernateTemplate().save(historyArcticlesRequest);
		
	}




	
	
	
	
}
