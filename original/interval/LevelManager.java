package interval;

import java.util.ArrayList;
import java.util.HashMap;

public class LevelManager {

	static Level  menu;
	private static HashMap<String,Level> lvls;
	private static ArrayList<Level> lvls2;
	/*public static int indexOf(Object o){
		return lvls.values().
	}*/
	public static int lvlCount(){
		return lvls.size();
	}
	public static String[] getLvlNames(){
		int m=lvlCount();
		String[] st=new String[m];
		for(int u=0;u<m;u++){
			st[u]=lvls2.get(u).getLevelName();
		}
		return st;
	}
	public static String getLvlName(int u){
		return lvls2.get(u).getLevelName();
	}
	public static Level getMenu(){
		return menu;
	}
	
	public static Level getLevel(String i){
		Level l=lvls.get(i);
		prime(l);
		return l;
	}
	public static Level getLevel(int i){
		Level l=lvls2.get(i);
		prime(l);
		return l;
	}
	private static void prime(Level l){
		//TODO priming is bugged
		//l.prime();
	}
	public static int getLvlNum(){
		return lvls.size();
	}

	public static void add(Level L){
		if(!lvls.containsKey(L.getLevelName())){
			lvls2.add(L);
		}
		lvls.put(L.getLevelName(),L);
	}
	public static void init(){


		lvls=new HashMap<String,Level>();
		lvls2=new ArrayList<Level>();



		Level test2 =new Level(lvls.size()+1);
		test2.addO(new ObjectivePoint("hard",16, 16, 9));
		test2.addO(new ObjectivePoint("easy",16, 16, 1));
		test2.addO(new ObjectivePoint("easy",1, 16, 1));
		test2.addO(new ObjectivePoint("easy",16, 1, 1));
		test2.addO(new ObjectivePoint("easy",8, 16, 1));
		test2.addO(new ObjectivePoint("easy",8, 8, 1));
		test2.setStartY(20);
		test2.getStats().setCapYear(0);
		test2.getStats().setCapDay(20);
		test2.getStats().setCapTerra(1000);
		test2.getStats().setCapEntSnaps(20);
		test2.getStats().computeRates();
		
		test2.setLevelName("tiny level");
		//test2.getStats().lockLight(0.3f);
		//	test2.getStats().setClip2(0.5f);
		add(test2);
		
		
		
		Level test =new Level(lvls.size()+1);
		test.addO(new ObjectivePoint("test objective",10, 10, 1));
		Stats stats=test.getStats();
		stats.setSize(128);
		stats.setCapDay(1);
		stats.setCapTerra(10000);
		stats.lockLight(0.3f);
		stats.lockSeason(0.2f);
		stats.setCapEntSnaps(100);
		stats.setCapYear(0); //1,000,000
		stats.computeRates();
		test.setMap("menumap");
		stats.nonInterval();
		test.setLevelName("An Excessively Long Title For A Level");
		add(test);
		
		

		Level test3 =new Level(lvls.size()+1);
		test3.addO(new ObjectivePoint("test objective",1000, 1000, 1));
		test3.getStats().setSize(4096);
		test3.setStartX(128);
		test3.setStartY(128);

		test3.getStats().setCapTerra(0);
		test3.getStats().setNoTerrainAge(true);
		test3.setLevelName("Giant Level");
		add(test3);
		
		
		
		Level season =new Level(lvls.size()+1);
		season.addO(new ObjectivePoint("test objective",10, 10, 1));
		season.getStats().setSize(16);
		season.setStartX(2);
		season.setStartY(2);
		season.getStats().setCapYear(20);
		season.getStats().setCapDay(10);
		season.getStats().setCapTerra(10);
		season.getStats().setCapEntSnaps(10);
		season.getStats().setNoTerrainAge(true);
		stats.computeRates();
		season.setLevelName("Season Test");
		add(season);
		
		

		menu =new Level(lvls.size()+1,GameMode.Menu.rules);
		menu.setMap("menumap");

	}

	public int[] dateRefine(int[] I){
		return new int[]{0,0,0};
	}
}
