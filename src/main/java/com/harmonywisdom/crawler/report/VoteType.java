package com.harmonywisdom.crawler.report;

public enum VoteType {
	POISION(52,"毒药"),
	RESCUE(51,"解药"),
	KILL(2,"杀人"),
	GUARD(6,"守卫"),
	REVEAL(3,"验人"),
	SUPPORT(9,"混血儿"),
	LINK(8,"连人"),
	COMMIT(-2,"认狼"),
	THIEF(7,"埋牌"),
	EXCUTION(1,"放逐投票"),
	TRANS(13,"飞警徽"),
	GIVEUP(12,"退水"),
	ELECTION(10,"上警"),
	VOTEFORCAP(11,"选警长"),
	SHOT(4,"开枪"),
	UNKNOWN(-999,"unset");
	
	String name;
	int number;
	VoteType(int i,String name){
		this.number=i;
		this.name=name;
	}
	public String getName(){
		return name;
	}
	public static VoteType parseType(int code){
		VoteType type;
		switch(code){
			case(1):type=VoteType.EXCUTION;
			break;
			case(2):type=VoteType.KILL;
			break;
			case(3):type=VoteType.REVEAL;
			break;
			case(4):type=VoteType.SHOT;
			break;
			case(5):type=VoteType.UNKNOWN;
			break;
			case(6):type=VoteType.GUARD;
			break;
			case(7):type=VoteType.THIEF;
			break;
			case(8):type=VoteType.LINK;
			break;
			case(9):type=VoteType.SUPPORT;
			break;
			case(10):type=VoteType.ELECTION;
			break;
			case(11):type=VoteType.VOTEFORCAP;
			break;
			case(12):type=VoteType.GIVEUP;
			break;
			case(13):type=VoteType.TRANS;
			break;
			case(51):type=VoteType.RESCUE;
			break;
			case(52):type=VoteType.POISION;
			break;
			default:type=VoteType.UNKNOWN;
		}
		return type;
	}
}
