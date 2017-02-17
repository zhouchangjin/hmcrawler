package com.harmonywisdom.crawler.report;

import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.stream.MalformedJsonException;
import com.harmonywisdom.crawler.httputil.HtmlFetcher;

public class WerewolfCrawler {
	
	public static void main(String[] args) {
		String url="http://cdnlangren.51shousha.com/WereWolfService/GetGameReportService";
		int id=1103970;
		HashMap<String,String> globalPlayer=new HashMap<String, String>();
		for(int i=0;i<200;i++){
			id=id+i;

			String result=HtmlFetcher.FetchHtml(url,"gameid="+id);
			result=result.replaceAll("\\\\", "");
			result=result.replaceAll("\\\"\\[", "\\[");
			result=result.replaceAll("\\]\\\"", "\\]");
            Gson gson=new Gson();
            try{
            	
            HashMap<String, String> playerMap=new HashMap<String, String>();
			HashMap<String,Object> map=gson.fromJson(result, HashMap.class);
			List<LinkedTreeMap<String,Object>> players=(List<LinkedTreeMap<String, Object>>) map.get("players");
			List<HashMap<String,Object>> voteInfo=(List<HashMap<String, Object>>) map.get("voteinfo");
			String createTime=(String) map.get("createtime");
			String gameresult=(String) map.get("result");//���ǳɹ�ʧ����Ϣ
			String roomid=(String)map.get("roomid");
			if(voteInfo==null){
				//System.out.println("==="+result);
			}else{
				//System.out.println(players.get(0));
				for(int k=0;k<players.size();k++){
					LinkedTreeMap<String,Object> player=players.get(k);
					String _nickname=(String) player.get("nickname");
					String _userid=(String)player.get("userid");
					String _seatnum=(String)player.get("seatnum");
					globalPlayer.put(_userid,_nickname);
					playerMap.put(_userid, _seatnum);
				}
				
				
			}
			
            }catch(Exception e){
            	e.printStackTrace();
            }
			
		}
		
		
		
	}

}
