package com.harmonywisdom.crawler.init;

import java.lang.reflect.Field;

import com.harmonywisdom.crawler.annotation.PageCrawlerSetting;
import com.harmonywisdom.crawler.page.ObjectPageBingding;
import com.harmonywisdom.crawler.page.PageCrawler;


public class CrawlerInitializer {
	
	
	public void init() {
		Field f[]=this.getClass().getFields();
		for(Field field:f) {
			if(field.getType().equals(PageCrawler.class) && field.isAnnotationPresent(PageCrawlerSetting.class)) {
				PageCrawlerSetting setting=field.getAnnotation(PageCrawlerSetting.class);
				String xmlPath=setting.xmlPath();
				String xmlfile=setting.xmlFile();
				boolean isRes=setting.isResource();
				PageCrawler crawler=new PageCrawler();
				if(isRes) {
					
					String path=CrawlerInitializer.class.getClassLoader().getResource(xmlPath+"/"+xmlfile).getFile();
					ObjectPageBingding binding=ObjectPageBingding.buildFromXML(path);
					crawler.setBinding(binding);
					
				}else {
					System.out.println("尚未制作");
					
				}
				
				try {
					field.set(this, crawler);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
