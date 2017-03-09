package com.harmonywisdom.crawler.page;

import java.util.ArrayList;
import java.util.List;

public class LCFStore {
	
	List<LCF> lcfs;
	int width=12;
	
	int lineStart=0;
	
	int lineEnd=0;
	
	
	int maxLine=0;
	
	public int getLineEnd() {
		return lineEnd;
	}

	public void setLineEnd(int lineEnd) {
		this.lineEnd = lineEnd;
	}

	public int getMaxLine() {
		return maxLine;
	}

	public void setMaxLine(int maxLine) {
		this.maxLine = maxLine;
	}

	public int getLineStart() {
		return lineStart;
	}

	public void setLineStart(int lineStart) {
		this.lineStart = lineStart;
	}

	public LCFStore() {
		// TODO Auto-generated constructor stub
		lcfs=new ArrayList<LCF>();
	}
	
	public void addLine(String line,int all,int eng,int zhcn){
		lcfs.add(new LCF(line,all,eng,zhcn));
	}
	
	public void parse(){
		int maxLineNum=0; int maxCnt=0;
		for(int i=0+width;i<lcfs.size()-width;i++){
			int counter=0;
			for(int j=i-width;j<i+width+1;j++){
				if(lcfs.get(j).getZhCnCnt()==0){
				}else{
					counter++;
				}
			}
			if(counter>width){
				if(lcfs.get(i).getZhCnCnt()>maxCnt){
					maxCnt=lcfs.get(i).getZhCnCnt();
					maxLineNum=i;
					//System.out.println(i+"====="+maxCnt);
				}
			}

		}
		if(maxLineNum==0){
			for(int i=0;i<lcfs.size();i++){
				if(lcfs.get(i).getZhCnCnt()>maxCnt){
					maxCnt=lcfs.get(i).getZhCnCnt();
					maxLineNum=i;
					//System.out.println(i+"====="+maxCnt);
				}
			}
		}
		setMaxLine(maxLineNum);
		for(int i=maxLineNum;i>0+width;i--){
			int ivCnt=0;int startLine=0;
			boolean flag=false;
			boolean searchFlag=true;
			for(int j=0;j<width;j++){
				int n=i-j;
				if(n>=0){
					if(lcfs.get(n).getZhCnCnt()==0){
						flag=true;
						ivCnt++;
					}
					if(searchFlag && flag){
						searchFlag=false;
						startLine=n+1;
					}
					if(flag && lcfs.get(n).getZhCnCnt()>0){
						break;
					}
				}
			}
			if(ivCnt>width/2){
				setLineStart(startLine);
				break;
			}
		}
		
		for(int i=maxLineNum;i<lcfs.size()-width;i++){
			int ivCnt=0;int endLine=0;
			boolean flag=false;
			boolean searchFlag=true;
			for(int j=0;j<width;j++){
				int n=i+j;
				
				if(n<lcfs.size()){
					if(lcfs.get(n).getZhCnCnt()==0){
						flag=true;
						ivCnt++;
					}
					if(searchFlag && flag){
						searchFlag=false;
						endLine=n-1;
					}
					
					if(flag && lcfs.get(n).getZhCnCnt()>0){
						break;
					}
					
				}
			}
			if(ivCnt>width/2){
				setLineEnd(endLine);
				break;
			}
		}
		
		for(int i=getLineStart();i<=getLineEnd();i++){
			System.out.println(lcfs.get(i).getLine());
		}
		System.out.println(this.getLineStart()+","+this.getMaxLine()+","+this.getLineEnd());
		
	}

}

class LCF{
	
	public LCF(String line2, int all, int eng, int zhcn) {
		this.line=line2;
		this.allCnt=all;
		this.engCnt=eng;
		this.zhCnCnt=zhcn;
	}
	String line;
	int allCnt;
	int engCnt;
	int zhCnCnt;
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	public int getAllCnt() {
		return allCnt;
	}
	public void setAllCnt(int allCnt) {
		this.allCnt = allCnt;
	}
	public int getEngCnt() {
		return engCnt;
	}
	public void setEngCnt(int engCnt) {
		this.engCnt = engCnt;
	}
	public int getZhCnCnt() {
		return zhCnCnt;
	}
	public void setZhCnCnt(int zhCnCnt) {
		this.zhCnCnt = zhCnCnt;
	}
	
}
