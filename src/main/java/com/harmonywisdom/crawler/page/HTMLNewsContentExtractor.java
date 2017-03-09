package com.harmonywisdom.crawler.page;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;

public class HTMLNewsContentExtractor implements IContentExtractor{
	
	DataInputStream input;

	public void setSourceStream(DataInputStream input) {
		// TODO Auto-generated method stub
		this.input=input;
		
	}

	public Map<String, Object> parse() {
		// TODO Auto-generated method stub
		LCFStore store=new LCFStore();
		BufferedReader br=new BufferedReader(new InputStreamReader(input));
		String line=null;
		try {
			int rowCounter=0;
			while((line=br.readLine())!=null){
				rowCounter++;
				//System.out.println(line);
//				Map<String,String> map=PageParser.match(line, "img", "src");
//				Iterator<String> keyIt=map.keySet().iterator();
//				while(keyIt.hasNext()){
//					String key=keyIt.next();
//					line=line.replace(key, "图片("+map.get(key)+")");
//				}
//				map=PageParser.match(line, "a", "href");
//				keyIt=map.keySet().iterator();
//				while(keyIt.hasNext()){
//					String key=keyIt.next();
//					line=line.replace(key, "("+map.get(key)+")");
//				}
				List<Tuple<String,String>> list=PageParser.match(line);
				String newLine="";
				for(int i=0;i<list.size();i++){
					Tuple<String,String> t=list.get(i);
					String tag=t.getLeft();
					String cnt=t.getRight();
					if(tag.equals("p")){
						newLine+=RegExpUtil.parseHTMLESCAPE(RegExpUtil.replaceAllSpace(cnt));
					}else if(tag.equals("a")){
						
					}else if(tag.equals("img")){
						
					}else if(tag.equals("span")){
						
					}else if(tag.equals("h")){
						newLine+=RegExpUtil.parseHTMLESCAPE(RegExpUtil.replaceAllSpace(cnt));
					}else if(tag.equals("div")){
						newLine+=RegExpUtil.parseHTMLESCAPE(RegExpUtil.replaceAllSpace(cnt));
					}else if(tag.equals("script")){
						
					}
					
				}
				//System.out.println(newLine);
				int allCharacters=newLine.length();
				String chineseLine=RegExpUtil.replaceAllEnglish(newLine);
				int chineses=chineseLine.length();
				store.addLine(newLine, allCharacters, allCharacters-chineses, chineses);
			}
			store.parse();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	

}
