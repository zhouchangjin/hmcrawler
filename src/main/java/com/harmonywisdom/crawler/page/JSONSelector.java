package com.harmonywisdom.crawler.page;

import java.lang.reflect.Method;
import java.util.Iterator;

import com.gamewolf.util.lang.IntegerParser;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class JSONSelector implements IJsonSelector{
	
	String cont;
	JsonElement ele;



	public String getCont() {
		return cont;
	}

	public void setCont(String cont) {
		this.cont = cont;
	}
	
	public void initialize() {
		Gson gson=new Gson();
		ele=gson.fromJson(cont, JsonElement.class);
	}

	@Override
	public JsonElement selectByJsonPath(String path) {
		// TODO Auto-generated method stub
		String paths[]=path.split("/");
		JsonElement current=ele;
		for(String p:paths) {
			if(IntegerParser.isPosInt(p)) {
				current=current.getAsJsonArray().get(IntegerParser.parsePositiveInt(p));
			}else {
				if(current.isJsonPrimitive()) {
					break;
				}else if(current.isJsonObject()) {
					current=current.getAsJsonObject().get(p);
				}else if(current.isJsonNull()) {
					current=null;
				}else {
					System.out.println("不知道为什么会跑进这里，但是我就不抛出异常了");
				}
				
			}
		}
		return current;
	}

	@Override
	public void assemble(Object obj,JSONObjectBinding binding) {
		// TODO Auto-generated method stub
		Iterator<String> paths=binding.pathlist().iterator();
		while(paths.hasNext()) {
			String path=paths.next();
			JsonElement ele=selectByJsonPath(path);
			if(ele==null) {
				//System.out.println("未抓到的属性"+binding.getPathProperty(path)+":"+path+"");
			}else if(ele.isJsonPrimitive()) {
				String type=binding.getPathType(path);
				String prop=binding.getPathProperty(path);
				prop=prop.substring(0,1).toUpperCase()+prop.substring(1);
				try {
					Method m=null;
					if(type.equals("String")) {
						
						m=obj.getClass().getMethod("set"+prop,String.class);
						m.invoke(obj, ele.getAsString());
					}else if(type.equals("Integer")) {
						m=obj.getClass().getMethod("set"+prop,Integer.class);
						m.invoke(obj, ele.getAsInt());
					}else if(type.equals("Double")) {
						m=obj.getClass().getMethod("set"+prop,Double.class);
						m.invoke(obj, ele.getAsDouble());
					}else if(type.equals("Date")) {
						//m=obj.getClass().getMethod("set"+prop,Date.class);
						//m.invoke(obj, ele.getas)
					}
					
				}catch(Exception e) {
					e.printStackTrace();
				}
				
				
			}else if(ele.isJsonNull()) {
				//System.out.println("未抓到的属性"+binding.getPathProperty(path)+":"+path+"");
				
			}else {
				System.out.println("不应该到达的地方"+ele.toString());
			}
		}
	}


}
