package com.harmonywisdom.crawler.proxy;

public interface ProxyTap  {
	public Proxy getProxy();
	
	public boolean testProxy(Proxy proxy,ProxyTester tester);
}
