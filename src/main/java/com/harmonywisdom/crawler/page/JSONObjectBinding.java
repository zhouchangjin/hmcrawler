package com.harmonywisdom.crawler.page;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.gamewolf.util.datafile.XMLNode;
import com.gamewolf.util.datafile.XMLReader;

public class JSONObjectBinding {
	
	HashMap<String,String> jsonPathMap;
	
	HashMap<String,String> jsonPathTypeMap;
	
	JSONObjectBinding(){
		jsonPathMap=new HashMap<String,String>();
		jsonPathTypeMap=new HashMap<String,String>();
	}
	
	
	public Set<String> pathlist(){
		return jsonPathMap.keySet();
	}
	
	public void setPath(String path,String property,String type) {
		jsonPathMap.put(path, property);
		jsonPathTypeMap.put(path, type);
	}
	
	public String getPathProperty(String path) {
		return jsonPathMap.get(path);
	}
	
	public String getPathType(String path) {
		return jsonPathTypeMap.get(path);
	}
	
	
	public static JSONObjectBinding buildFromXML(String path) {
		JSONObjectBinding binding=new JSONObjectBinding();
		XMLNode node=XMLReader.loadXMLFile(path);
		List<XMLNode> nodes=node.getNodes("Prop");
		if(nodes==null) {
			return binding;
		}
		for(XMLNode current:nodes) {
			
			String type=current.getAttribute("type").toString();
			String jspath=current.getAttribute("jspath").toString();
			String name=current.getAttribute("name").toString();
			binding.setPath(jspath, name, type);
		}
		return binding;
	}

}
