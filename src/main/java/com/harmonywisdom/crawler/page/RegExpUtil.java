package com.harmonywisdom.crawler.page;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpUtil {
	
	public final static String HTMLTAGREG="<[^<]+?>";
	
	public final static String WHITESPACESTRINGREG="\\s";
	
	public final static String CHINESEREG="[\u4e00-\u9fa5]";
	
	public final static String NONCHINESEREG="[^\u4e00-\u9fa5]";
	
	public final static String IMGTAGREG="<img[^<]+>";
	
	public final static String HTMLESCAPE="&[^&]+;";
	
	public final static String BOOKNAMEREG="《.*》";
	
	public static String findStr(String ori,String reg) {
		Pattern pattern=Pattern.compile("("+reg+")");
		Matcher m=pattern.matcher(ori);
		while(m.find()){
			String findStr=m.group(0);
			return findStr;
		}
		return null;
	}
	
	public static String replaceAllHtmlTag(String input){
		String result=input.replaceAll(HTMLTAGREG, "");
		return result;
	}
	
	public static String replaceAllSpace(String input){
		return input.replaceAll(WHITESPACESTRINGREG, "");
	}
	
	public static String replaceAllEnglish(String input){
		return input.replaceAll(NONCHINESEREG, "");
	}
	
	public static String parseHTMLESCAPE(String replace){
		Pattern pattern=Pattern.compile("("+HTMLESCAPE+")");
		Matcher m=pattern.matcher(replace);
		StringBuffer sb = new StringBuffer();
		while(m.find()){
			String test=m.group(0);
			if(test.equals("&ldquo;")){
				m.appendReplacement(sb, "“");
			}else if(test.equals("&rdquo;")){
				m.appendReplacement(sb, "”");
			}else if(test.equals("&lsquo;")){
				m.appendReplacement(sb, "‘");
			}else if(test.equals("&rsquo;")){
				m.appendReplacement(sb, "’");
			}else if(test.equals("&hellip;")){
				m.appendReplacement(sb, "…");
			}
		}
		m.appendTail(sb);
		return sb.toString();
	}
	
	
	

}
