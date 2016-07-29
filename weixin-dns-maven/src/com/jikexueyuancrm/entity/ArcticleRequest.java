package com.jikexueyuancrm.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jikexueyuancrm.util.HttpUtils;


@Entity
@Table(name="ArcticleLink")
public class ArcticleRequest implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5838216134328143947L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}



	
	
	
	@Column(length=1000)
	public String content_url;
	@Column(length=1000)
	public String source_url;
	
	@Column(length=1000)
	public String historyActiclesLink;
	
	public Boolean status=true;


	public String biz;
	
	public Boolean getStatus() {
		return status;
	}


	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	public String getHistoryActiclesLink() {
		return historyActiclesLink;
	}


	public void setHistoryActiclesLink(String historyActiclesLink) {
		this.historyActiclesLink = historyActiclesLink;
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


	public String mid;

	public String getContent_url() {
		return content_url;
	}


	public void setContent_url(String content_url) {
		this.content_url = content_url;
		this.biz=HttpUtils.URLRequest(content_url).get("__biz")+"==";
		this.mid=HttpUtils.URLRequest(content_url).get("mid");
		this.idx=  Integer.valueOf(HttpUtils.URLRequest(content_url).get("idx"));
		this.sn=HttpUtils.URLRequest(content_url).get("sn");
	}


	public String getSource_url() {
		return source_url;
	}


	public void setSource_url(String source_url) {
		this.source_url = source_url;
		
	}


	public Integer idx;
	public String sn;



	public String appkey;
	public Date time=new Date();
	
	public Date getTime() {
		return time;
	}


	public void setTime(Date time) {
		this.time = time;
	}


	public String pass_ticket;
	public String getMid() {
		return mid;
	}


	public void setMid(String mid) {
		this.mid = mid;
	}


	public Integer getIdx() {
		return idx;
	}


	public void setIdx(Integer idx) {
		this.idx = idx;
	}


	public String getPass_ticket() {
		return pass_ticket;
	}


	public void setPass_ticket(String pass_ticket) {
		this.pass_ticket = pass_ticket;
	}

	public String getSn() {
		return sn;
	}


	public void setSn(String sn) {
		this.sn = sn;
	}

}
