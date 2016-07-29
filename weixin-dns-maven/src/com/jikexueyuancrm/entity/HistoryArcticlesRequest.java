package com.jikexueyuancrm.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="HistoryArcticlesLink")
public class HistoryArcticlesRequest  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -907379285097483859L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	public Date time=new Date();
    @Column(length=1000)
	public String fullurl;
	public String biz;
	
	
	public String uin;
	public String appkey;
	
	
	
public String pass_ticket;
	
	public String frommsgid;//起点
	
	 
	public Boolean isfollow=false;//关注
	
	
	public Integer count;
	
	public String getFullurl() {
		return fullurl;
	}
	public void setFullurl(String fullurl) {
		this.fullurl = fullurl;
	}
	public String getBiz() {
		return biz;
	}
	public void setBiz(String biz) {
		this.biz = biz;
	}
	
	public String getAppkey() {
		return appkey;
	}
	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}
	
	
	public Boolean getIsfollow() {
		return isfollow;
	}
	public void setIsfollow(Boolean isfollow) {
		this.isfollow = isfollow;
	}
	
		
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFrommsgid() {
		return frommsgid;
	}
	public void setFrommsgid(String frommsgid) {
		this.frommsgid = frommsgid;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getUin() {
		return uin;
	}
	public void setUin(String uin) {
		this.uin = uin;
	}
	
	public String getPass_ticket() {
		return pass_ticket;
	}
	public void setPass_ticket(String pass_ticket) {
		this.pass_ticket = pass_ticket;
	}
	
}
