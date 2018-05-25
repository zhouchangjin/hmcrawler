package com.harmonywisdom.crawler.page;

public class Tuple <L,R> implements Comparable<Tuple<L,R>>{
	
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

	@Override
	public int compareTo(Tuple<L, R> tuple) {
		if(tuple.getLeft().equals(this.getLeft())) {
			if(tuple.getRight().equals(this.getRight())) {
				return 0;
			}else {
				if(tuple.getRight() instanceof String && this.getRight() instanceof String) {
					return this.getRight().toString().compareTo(tuple.getRight().toString());
				}else if(tuple.getRight() instanceof Integer && this.getRight() instanceof Integer) {
					Integer n=(Integer) tuple.getRight();
					Integer m=(Integer) this.getRight();
					return m-n;
				}else if(tuple.getRight() instanceof Double && this.getRight() instanceof Double) {
					Double n=(Double) tuple.getRight();
					Double m=(Double) this.getRight();
					if(m<n) {
						return -1;
					}else {
						return 1;
					}
				}else if(tuple.getRight() instanceof Float && this.getRight() instanceof Float) {
					Float n=(Float) tuple.getRight();
					Float m=(Float) this.getRight();
					if(m<n) {
						return -1;
					}else {
						return 1;
					}
				}else {
					return -1;
				}
			}
			
			
		}else {
			if(tuple.getLeft() instanceof Integer && this.getLeft() instanceof Integer) {
				Integer n=(Integer) tuple.getLeft();
				Integer m=(Integer) this.getLeft();
				return m-n;
			}else if(tuple.getLeft() instanceof String && this.getLeft() instanceof String) {
				return this.getLeft().toString().compareTo(tuple.getLeft().toString());
			}else if(tuple.getLeft() instanceof Double && this.getLeft() instanceof Double) {
				Double n=(Double) tuple.getLeft();
				Double m=(Double) this.getLeft();
				if(m<n) {
					return -1;
				}else {
					return 1;
				}
			}else if(tuple.getLeft() instanceof Float && this.getLeft() instanceof Float) {
				Float n=(Float) tuple.getLeft();
				Float m=(Float) this.getLeft();
				if(m<n) {
					return -1;
				}else {
					return 1;
				}
			}else {
				return -1;
			}
		}
	}
}
