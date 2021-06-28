package interval;

import java.util.HashMap;


public class Item {

	String name;
	String model;
	short useType; //0 none,1 thrown, 2 tool, 3 aimmed tool
	short movement; //0 regular, 1 1.5(sprint), 2 half (sneak), 3 immobile
	
	public Item(String name,String model){
		this.name=name;
		this.model=model;
		set.put(name, this);
	}
	
	public Item(String name,String model,int type){
		this(name,model);
		useType=(short) type;
	}
	public Item(String name,String model,int type,int moveType){
		this(name,model,type);
		movement=(short) moveType;
	}

	public short getMove(){
		return movement;
	}
	public String getName(){
		return name;
	}
	public boolean getAim() {
		return useType==2;
	}
	
	public static HashMap<String, Item> set=new HashMap<String,Item>();
	public static Item Pistol;
	public static Item Bomb;
	public static Item Coin;
	public static Item Silk;
	public static Item Juice;
	public static Item Pants;
	public static Item Skull;
	public static Item Shovel;
	public static Item Sword;
	public static Item Knife;
	public static Item Poop;
	public static Item String;
	public static Item Crystal;
	public static Item Empty;
	
	static{
		Pistol=new Item("Pistol","pistol",3,2);
		Bomb=new Item("Bomb","bomb",1,3);
		Coin=new Item("Coin","coin");
		Silk=new Item("Silk","silk");
		Juice=new Item("Juice","juice");
		Pants=new Item("Pants","pants");
		Skull=new Item("Skull","skull");
		Shovel=new Item("Shovel","shovel",2,3);
		Sword=new Item("Sword","sword",3,2);
		Knife=new Item("Knife","knife",3);
		Poop=new Item("Poop","poop",1);
		String=new Item("String","string");
		Crystal=new Item("Crystal","crystal",1);
		Empty=new Item("Empty","playerIdle");
	}
}
