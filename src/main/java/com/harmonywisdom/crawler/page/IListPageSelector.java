package com.harmonywisdom.crawler.page;

import java.util.List;

public interface IListPageSelector {
	
	List<IPageSelector> selectItems(String xpath);

}
