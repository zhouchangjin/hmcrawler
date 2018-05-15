package com.harmonywisdom.crawler.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.harmonywisdom.crawler.model.CrawlerConfiguration;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface PageCrawlerDBSetting {
	
	 String table() default "crawler_conf";
	 Class javaClass() default CrawlerConfiguration.class;
	 String propertieFile() default "mysql.properties";
	 String propertiePath() default "/";
	 boolean isResource() default true;
	 String colName() default  "conf";
	 String fieldName() default "conf";
	 String idField() default "id";
	 String value();
	 

}
