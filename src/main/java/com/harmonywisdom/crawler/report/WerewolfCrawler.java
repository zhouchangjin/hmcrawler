package com.harmonywisdom.crawler.report;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.harmonywisdom.crawler.httputil.HtmlFetcher;

public class WerewolfCrawler {
	
	public static void main(String[] args) {
		try {
			FileWriter fw=new FileWriter(new File("d:/werewolf/user.csv"));
			FileWriter fw2=new FileWriter(new File("d:/werewolf/game.csv"));
			BufferedWriter bw1=new BufferedWriter(fw);
			BufferedWriter bw2=new BufferedWriter(fw2);
			String headline="游戏编号,第n天,操作人,对象,动作类型码,动作类型含义,有效票数,操作人编号,对象编号,操作人角色类型,指向对象类型,新排游戏编号,游戏人数,游戏结果,游戏时间,房间号";
			bw2.write(headline);
			bw2.newLine();
			String url="http://cdnlangren.51shousha.com/WereWolfService/GetGameReportService";
			int idStart=1153969;
			HashMap<String,String> globalPlayer=new HashMap<String, String>();
			int gameCounter=49115;
			for(int i=1;i<50000;i++){
				int _id=idStart+i;
				System.out.println(_id+","+i+"=========================================");
				String result=HtmlFetcher.FetchHtml(url,"gameid="+_id);
				result=result.replaceAll("\\\\", "");
				result=result.replaceAll("\\\"\\[", "\\[");
				result=result.replaceAll("\\]\\\"", "\\]");
	            Gson gson=new Gson();
	            try{
	            	
	           
				HashMap<String,Object> map=gson.fromJson(result, HashMap.class);
				List<LinkedTreeMap<String,Object>> players=(List<LinkedTreeMap<String, Object>>) map.get("players");
				List<LinkedTreeMap<String,Object>> voteInfo=(List<LinkedTreeMap<String,Object>>) map.get("voteinfo");
				String createTime=(String) map.get("createtime");
				String gameresult=(String) map.get("result");
				String roomid=(String)map.get("roomid");
				if(voteInfo==null){
					//System.out.println("==="+result);
				}else{
					//System.out.println(players.get(0));
					gameCounter++;
					HashMap<String,String> playerRole=new HashMap<String, String>();
					 HashMap<String, String> playerMap=new HashMap<String, String>();
					for(int k=0;k<players.size();k++){
						
						LinkedTreeMap<String,Object> player=players.get(k);
						String _nickname=(String) player.get("nickname");
						String _userid=(String)player.get("userid");
						String _seatnum=(String)player.get("seatnum");
						int _roleType=Integer.parseInt((String)player.get("roleid"));
						String _roleName=RoleType.buildRoleType(_roleType).getName();
						globalPlayer.put(_userid,_nickname);
						playerMap.put(_userid, _seatnum);
						playerRole.put(_userid, _roleName);
						bw1.write(_userid+","+_nickname);
						bw1.newLine();
						bw1.flush();
					}
					for(int p=0;p<voteInfo.size();p++){
						LinkedTreeMap<String,Object> votei=voteInfo.get(p);
						Integer daynum=Integer.parseInt((String) votei.get("daynum"));
						String fromid=(String) votei.get("fromid");
						String toid=(String) votei.get("toid");
						String toNum="";
						if(toid==null){
							toid="";
						}
						String toRoleType="";
						if(toid.contains(",")){
							String ids[]=toid.split(",");
							for(String idi:ids){
								toNum+=playerMap.get(idi)+"-";
								toRoleType+=playerRole.get(toid)+";";
							}
							toNum=toNum.substring(0, toNum.length()-1);
						}else{
							toNum=playerMap.get(toid)+"";
							toRoleType=playerRole.get(toid);
						}
						String fromNum=playerMap.get(fromid);
						String roleType=playerRole.get(fromid);
						Integer voteNum=Integer.parseInt((String)votei.get("votenum"));
						Integer voteType=Integer.parseInt((String)votei.get("votetype"));
						System.out.println(votei);
						VoteType type=VoteType.parseType(voteType);
						String line=_id+","+daynum+","+fromNum+","+toNum+","+voteType+","+type.getName()+","+voteNum+","+fromid+","+toid.replaceAll(",", "-")+","+roleType+","+toRoleType+","+gameCounter+","+playerMap.size()+","+gameresult+","+createTime+","+roomid;
						bw2.write(line);
						bw2.newLine();
						bw2.flush();
					}
					
				}
				
	            }catch(Exception e){
	            	e.printStackTrace();
	            }
				
			}
			bw1.close();
			bw2.close();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		
		
	}

}
