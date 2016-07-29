package com.jikexueyuancrm.service.impl;

import org.hibernate.FlushMode;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.jikexueyuancrm.entity.Comm_Msg_Info;




@Service
public class Comm_Msg_InfoServiceImpl  extends HibernateDaoSupport{

	
	

	
		
		
		
		
		
		
		
		//给HibernateDaoSupport注入SessionFactory
		@Autowired 
	    public void setSuperSessionFactory(SessionFactory sessionFactory) {  
	       super.setSessionFactory(sessionFactory);  
	    }  
		

		
		public void save( Comm_Msg_Info comm_Msg_Info) {
			
			
			this.currentSession().setFlushMode(FlushMode.AUTO);
			//等价于this.getHibernateTemplate().getSessionFactory().getCurrentSession().setFlushMode(FlushMode.AUTO);
			this.getHibernateTemplate().save(comm_Msg_Info);
			
		}
	}

