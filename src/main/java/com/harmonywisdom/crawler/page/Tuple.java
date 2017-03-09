package com.harmonywisdom.crawler.page;

public class Tuple <L,R>{
	
	L left;
	R right;
	
	public Tuple(L l,R r){
		this.left=l;
		this.right=r;
	}
	
	public L getLeft(){
		return left;
	}
    public R getRight(){
    	return right;
    }
    
    public String toString(){
    	return "["+left+","+right+"]";
    }
}
