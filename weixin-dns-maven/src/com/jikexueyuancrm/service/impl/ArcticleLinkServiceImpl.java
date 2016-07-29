package com.jikexueyuancrm.service.impl;


import org.apache.log4j.Logger;
import org.eclipse.jetty.util.log.Log;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;







import com.jikexueyuancrm.controller.LinkConsumeController;
import com.jikexueyuancrm.entity.ArcticleRequest;
import com.jikexueyuancrm.entity.HistoryArcticlesRequest;

//spring下载地址
//http://repo.springsource.org/libs-release-local/org/springframework/spring/

@Service

public class ArcticleLinkServiceImpl extends HibernateDaoSupport  {
	
	
	
	private static Logger log = Logger.getLogger(ArcticleLinkServiceImpl.class);
	
	
	
	
	@Autowired 
    public void setSuperSessionFactory(SessionFactory sessionFactory) {  
       super.setSessionFactory(sessionFactory);  
    }  
	

	
	public void save(ArcticleRequest arcticleRequest) {
		
		
		this.currentSession().setFlushMode(FlushMode.AUTO);
		//等价于this.getHibernateTemplate().getSessionFactory().getCurrentSession().setFlushMode(FlushMode.AUTO);
		this.getHibernateTemplate().save(arcticleRequest);
		
		log.info(arcticleRequest.getId());
	}

	
	public ArcticleRequest get() {
		String queryString="from ArcticleRequest where status=true order by id ";
	Query query=this.currentSession().createQuery(queryString);
		
		query.setMaxResults(1);
	
		ArcticleRequest ar=	(ArcticleRequest) query.uniqueResult();
		return ar;
	}



	public void update(ArcticleRequest ar) {
		this.currentSession().setFlushMode(FlushMode.AUTO);
		this.getHibernateTemplate().update(ar);
	}
	
	
}
