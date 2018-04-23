package com.harmonywisdom.crawler.proxy;

import java.util.List;

public interface ResultParser {
	
	List<Proxy> parse(String text);

}
