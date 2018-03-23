package com.harmonywisdom.crawler.website;

import java.util.Date;

public class IndexingStatus {
	public String type;
	public Date time;
	public String site;
	public int totalCnt;
	public int page;
	public String url;
	public String title;
	public Date snapShotTime;
	
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public int getTotalCnt() {
		return totalCnt;
	}
	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getSnapShotTime() {
		return snapShotTime;
	}
	public void setSnapShotTime(Date snapShotTime) {
		this.snapShotTime = snapShotTime;
	}

}
