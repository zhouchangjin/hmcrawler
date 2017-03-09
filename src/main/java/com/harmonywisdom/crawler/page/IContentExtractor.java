package com.harmonywisdom.crawler.page;

import java.io.DataInputStream;
import java.util.Map;


public interface IContentExtractor {
	
	public void setSourceStream(DataInputStream input );
	
	public Map<String,Object> parse();
}
