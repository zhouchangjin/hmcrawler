package com.harmonywisdom.crawler.report;

public enum RoleType {
	
	SEER(3,"预言家"),
	VILLAGER(1,"村民"),
	WEREWOLF(2,"狼人"),
	HUNTERN(4,"猎人"),
	WIZARD(5,"女巫"),
	GUARD(6,"守卫"),
	THIEF(7,"狼人"),
	CUPID(8,"丘比特"),
	HALF(9,"混血儿"),
	MORON(10,"白痴"),
	WHITEWOLF(11,"白狼王"),
	UNKNOWN(-1,"unknown");
	int num;
	String name;
	RoleType(int num,String name){
		this.num=num;
		this.name=name;
	}
	
	public static RoleType buildRoleType(int num){
		switch(num){
			case(1):return RoleType.VILLAGER;
			case(2):return RoleType.WEREWOLF;
			case(3):return RoleType.SEER;
			case(4):return RoleType.HUNTERN;
			case(5):return RoleType.WIZARD;
			case(6):return RoleType.GUARD;
			case(7):return RoleType.THIEF;
			case(8):return RoleType.GUARD;
			case(9):return RoleType.HALF;
			case(10):return RoleType.MORON;
			case(11):return RoleType.WHITEWOLF;
			default:return RoleType.UNKNOWN;
		}
	}
	String getName(){
		return name;
	}

}
