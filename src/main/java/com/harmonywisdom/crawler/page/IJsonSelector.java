package com.harmonywisdom.crawler.page;

import com.google.gson.JsonElement;

public interface IJsonSelector {
	
	public JsonElement selectByJsonPath(String path); 
	
	void assemble(Object obj,JSONObjectBinding binding);

}
