package com.harmonywisdom.crawler.page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class PageParser {
	
	public static Map<String,Tuple<String,String>> parsePage(String source){
		HashMap<String, Tuple<String, String>> map=new HashMap<String,Tuple<String,String>>();
		 String reg = "<([a-z]+)[^<]*?>([^<]*?)</[^>]*?>";
		 Pattern p=Pattern.compile(reg);
		 Matcher m=p.matcher(source);
		 while(m.find()){
			 String ele=m.group(1);
			 String text=m.group(2);
			 Tuple<String,String> t=new Tuple<String, String>(ele, text);
			 map.put(ele, t);
		 }
		 return map;
	}
	
	public static List<Tuple<String,String>> match(String source){
		 ArrayList<Tuple<String,String>> res=new ArrayList<Tuple<String,String>>();
		 String reg = "<([a-z]+)[^<]*?>([^<]*?)</[^>]*?>";
		 Pattern p=Pattern.compile(reg);
		 Matcher m=p.matcher(source);
		 while(m.find()){
			 String ele=m.group(1);
			 String text=m.group(2);
			 Tuple<String,String> t=new Tuple<String, String>(ele, text);
			 res.add(t);
		 }
		 return res;
	}

	public static Map<String,String> match(String source, String element, String attr) {
        Map<String,String> result = new LinkedHashMap<String,String>();
        String reg = "<" + element + "[^<>]*?\\s"+ attr+ "=['\"]?([^'\"]+)['\"]?[^<]*?>";
        Matcher m = Pattern.compile(reg).matcher(source);
        while (m.find()) {
            String key = m.group(0);
            String value = m.group(1);
            result.put(key, value);
        }
        return result;
    }
	
	public static Map<String,String> matchPic(String source){
		Map<String,String> result = new LinkedHashMap<String,String>();
        String reg ="(http[^\\s\n]+\\.(jpg|gif|png))";
        Matcher m = Pattern.compile(reg).matcher(source);
        while (m.find()) {
            String key = m.group(0);
            String value = m.group(1);
            result.put(key, value);
        }
        return result;
	}
	/**
	 * 旧系统中抓取新闻包含图片的处理
	 * @param content  页面内容
	 * @param element  页面元素
	 * @param attribute 页面属性
	 * @return  拿出页面所有该元素的属性值，并清空页面中的该元素
	 */
	public static Tuple<String,String> processContent(String content,String element,String attribute){
		Map<String,String> map=match(content, element, attribute);
		Iterator<String> keyIt=map.keySet().iterator();
		String result="";
		while(keyIt.hasNext()){
			String key=keyIt.next();
			//System.out.println(key);
			content=content.replace(key, "");
			result+=map.get(key)+",";
		}
		return new Tuple<String, String>(content,result);
	}
	
	public static void main(String[]args){
	//	System.out.println(matchPic("\u73b0\u5982\u4eca\uff0c\u5730\u94c1\u5df2\u6210\u4e3a\u5927\u5bb6\u4e0a\u4e0b\u73ed\u4ea4\u901a\u4e0d\u53ef\u7f3a\u5c11\u7684\u4e00\u90e8\u5206\uff0c\u4fbf\u6377\u3001\u5feb\u901f\u3001\u62e5\u6324\u662f\u5b83\u7684\u6700\u5927\u7279\u70b9\u3002\u53ef\u80fd\u5404\u4f4d\u5f71\u53cb\u90fd\u5bf9\u5750\u5730\u94c1\u7279\u522b\u4e60\u4ee5\u4e3a\u5e38\u4e86\uff0c\u4f46\u5176\u5b9e\uff0c\u8d8a\u662f\u5e73\u5e38\u7684\u4e1c\u897f\uff0c\u4f60\u4ed4\u7ec6\u6316\u6398\u4e4b\u540e\u8d8a\u6709\u4e1c\u897f\u53ef\u4ee5\u62cd\u7684\u3002\nhttp://pic.yupoo.com/fengjuzhen/EDTyzyEV/7aj8R.jpg\n\u521a\u521a\u6211\u4eec\u8bf4\u6316\u6398\u5730\u94c1\u7ad9\u7684\u53e6\u4e00\u9762\uff0c\u957f\u66dd\u5149\u662f\u4e00\u79cd\u529e\u6cd5\uff0c\u4f7f\u7528\u67d0\u4e9b\u7279\u6b8a\u6548\u679c\u7684\u955c\u5934\u5c31\u662f\u53e6\u4e00\u4e2a\u529e\u6cd5\u4e86\u3002\u8fd9\u5f20\u7167\u7247\u4f7f\u7528EF 15MM F2.8 FISHEYE\u9c7c\u773c\u955c\u5934\u62cd\u6444\u7684\uff0c\u5e73\u65f6\u7565\u663e\u666e\u901a\u7684\u5730\u94c1\u7ad9\u5728\u9c7c\u773c\u955c\u5934\u773c\u4e2d\u88ab\u5938\u5f20\u6f14\u7ece\u6210\u4e86\u4e24\u6761\u66f2\u7ebf\u3002\u6211\u6562\u8bf4\u8fd9\u4e2a\u201c\u5f62\u6001\u201d\u7684\u5730\u94c1\u7ad9\u4f60\u7edd\u5bf9\u6ca1\u89c1\u8fc7\uff01\nhttp://pic.yupoo.com/jinyanjay/EDTyAvW6/10jzR6.jpg\n\u8bf4\u5b8c\u201c\u4e0d\u8d70\u5bfb\u5e38\u8def\u201d\u7684\u62cd\u6444\u624b\u6bb5\u4e4b\u540e\uff0c\u6211\u4eec\u518d\u6765\u804a\u804a\u957f\u66dd\u5149\u3002\u5176\u5b9e\u50cf\u7b2c\u4e00\u5f20\u7167\u7247\u90a3\u79cd\u5f62\u6210\u201c\u8f66\u548c\u5c55\u53f0\u201d\u7684\u5bf9\u6bd4\u53ea\u662f\u4e00\u79cd\u529e\u6cd5\uff0c\u53e6\u4e00\u79cd\u6700\u5e38\u7528\u7684\u529e\u6cd5\u662f\u62cd\u6444\u8fd0\u52a8\u4e2d\u7684\u5730\u94c1\u548c\u9759\u6b62\u7684\u7b49\u5730\u94c1\u7684\u4eba\u7684\u5bf9\u6bd4\u3002\u4f8b\u5982\u4e0a\u9762\u8fd9\u5f20\u7167\u7247\u5c31\u662f\u4f7f\u7528EF 35MM\nF1.4\u62cd\u6444\u7684\uff0c\u7167\u7247\u4e2d\u4e24\u4e2a\u7b49\u5730\u94c1\u7684\u7537\u4eba\u662f\u9759\u6b62\u7684\uff0c\u800c\u5730\u94c1\u5219\u662f\u8fd0\u52a8\u7684\uff0c\u8fd9\u6837\u5c31\u5f62\u6210\u4e86\u201c\u52a8-\u9759\u201d\u201c\u4eba-\u7269\u201d\u7684\u5bf9\u6bd4\u3002\nhttp://pic.yupoo.com/jinyanjay/EDTzeZ0R/TBcbX.jpg\n\u5728\u7b49\u5730\u94c1\u7684\u8fc7\u7a0b\uff0c\u6211\u4eec\u5f80\u5f80\u53ef\u4ee5\u770b\u5230\u5404\u79cd\u622a\u7136\u4e0d\u540c\u7684\u4eba\uff1a\u6709\u95f2\u901b\u7684\uff0c\u6709\u65e0\u804a\u671b\u5929\u7684\uff0c\u6709\u537f\u537f\u6211\u6211\u7684\uff0c\u6709\u4f4e\u5934\u6253\u624b\u673a\u73a9\u624b\u673a\u7684\u2026\u2026\u4f46\u662f\u5f53\u4f60\u628a\u4ed6\u4eec\u5168\u90fd\u62cd\u4e0b\u6765\u4e4b\u540e\uff0c\u5c31\u53d8\u6210\u4e86\u4e00\u5e45\u201c\u4f17\u751f\u76f8\u201d\u3002\u8fd9\u5f20\u7167\u7247\u5c31\u662f\u900f\u8fc7\u7b49\u5730\u94c1\u7684\u73bb\u7483\u62cd\u6444\u7684\u5bf9\u9762\u5c55\u53f0\u7684\u4eba\u4eec\uff0c\u4e0d\u9700\u8981\u4ec0\u4e48\u7279\u6b8a\u955c\u5934\u548c\u6280\u5de7\uff0c\u53ea\u8981\u4e00\u4e2a\u666e\u901a\u7684EF 50 1.8\uff0c\u5580\u5693\u4e00\u58f0\uff0c\u5373\u53ef\u83b7\u5f97\u3002\nhttp://pic.yupoo.com/wulinju/EDTyI72Y/XVQvy.jpg\n\u5730\u94c1\u4e2d\u7684\u53e6\u4e00\u79cd\u62cd\u6444\u7c7b\u578b\u5c31\u662f\u7eaa\u5b9e\uff0c\u4e5f\u5c31\u662f\u770b\u5230\u4ec0\u4e48\u62cd\u4ec0\u4e48\uff0c\u8bb0\u5f55\u4e0b\u4eba\u4eec\u6700\u771f\u5b9e\u7684\u77ac\u95f4\u3002\u4e0a\u9762\u8fd9\u5f20\u7167\u7247\u5c31\u662f\u8fd9\u6837\uff0c\u4e00\u4e2a\u7537\u4eba\u4e0d\u77e5\u9053\u662f\u6628\u591c\u6ca1\u7761\u597d\u8fd8\u662f\u592a\u52b3\u7d2f\uff0c\u5728\u5730\u94c1\u7ad9\u53f0\u7684\u4f11\u606f\u5ea7\u4f4d\u4e0a\u7761\u7740\u4e86\u3002\u8fd9\u65f6\u4f60\u53ea\u8981\u9760\u8fd1\u4ed6\uff0c\u6084\u6084\u7684\u62cd\u4e0b\uff0c\u53ea\u8981\u7167\u7247\u4e0d\u865a\uff0c\u8fd9\u5c31\u662f\u5f20\u5f88\u597d\u7684\u7eaa\u5b9e\u7c7b\u7167\u7247\u4e86\uff01\nhttp://pic.yupoo.com/taneyeah/EDTyxx4z/bKMw8.jpg\n\u5730\u94c1\u4e0a\u6bcf\u5929\u90fd\u5728\u4e0a\u6f14\u5404\u79cd\u60b2\u6b22\u79bb\u5408\uff0c\u5982\u679c\u4f60\u80fd\u7528\u624b\u4e2d\u7684\u76f8\u673a\u8bb0\u5f55\u4e0b\u8fd9\u4e00\u5207\uff0c\u5e76\u4e14\u89c2\u4f17\u53ef\u4ee5\u901a\u8fc7\u4f60\u7684\u7167\u7247\u611f\u53d7\u5230\u753b\u9762\u4e2d\u4eba\u7684\u611f\u60c5\uff0c\u90a3\u5c31\u662f\u4e00\u5f20\u771f\u6b63\u5730\u597d\u7167\u7247\u4e86\u3002\u8fd9\u5f20\u7167\u7247\u5c31\u662f\u7eaf\u7cb9\u7684\u6293\u62cd\u4e86\uff0c\u76f8\u5bf9\u4e8e\u524d\u4e00\u5f20\u7684\u7eaa\u5b9e\u6765\u8bb2\uff0c\u8fd9\u5f20\u7684\u62cd\u6444\u96be\u5ea6\u660e\u663e\u589e\u5927\u3002\u8981\u5728\u60c5\u4fa3\u4e92\u76f8\u51dd\u671b\u7684\u4e00\u77ac\u95f4\u5b8c\u6210\u6d4b\u5149\u3001\u5bf9\u7126\u7b49\u4e00\u7cfb\u5217\u64cd\u4f5c\u5e76\u4e14\u6700\u7ec8\u6293\u5230\uff0c\u5bf9\u4e8e\u65b0\u624b\u6765\u8bf4\u8fd8\u662f\u9700\u8981\u591a\u52a0\u7ec3\u4e60\u624d\u80fd\u505a\u5230\u7684\u3002\n\u53d1\u73b0\u751f\u6d3b\u4e2d\u666e\u901a\u573a\u666f\u4e2d\u4e0d\u666e\u901a\u7684\u4e00\u9762\uff0c\u662f\u6bcf\u4e00\u4e2a\u6444\u5f71\u7231\u597d\u8005\u7684\u5fc5\u4fee\u8bfe\uff0c\u5404\u4f4d\u5f71\u53cb\u8bb0\u5f97\u591a\u89c2\u5bdf\u591a**\u591a\u601d\u8003\u3002\u52a0\u6cb9\uff01\n"));
	//	match("<img src='http://chinese.people.com.cn/img/2012wbn/images/peopleclient.jpg'>", "img", "src");
	    //String c="nhttp://common.jxnews.com.cn/2013/jjcc.jpg'\nsrc='http://common.jxnews.com.cn/2013/knetSealLogo1.jpg'\"; ";
	    //match(c,"img","src");
		String c="<img src='http://chinese.people.com.cn/img/2012wbn/images/peopleclient.jpg'>我是网页正文<img src='http://chinewbn/images/peopleclient.jpg'>";
	   System.out.println(processContent(c,"img","src"));
	}

}
