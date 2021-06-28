package interval;

import intervalEntity.Entity;
import intervalEntity.EntityEnemy;
import intervalEntity.EntityKey;
import intervalEntity.EntityNPC;
import intervalEntity.IEntity;
import intervalEntity.gButton;
import intervalEntity.gPlatform;

import java.util.ArrayList;

public class Level {
	private Stats stats;
	ArrayList<Objective> objectives;
	//ArrayList<EntityKey> ents;

	public int sizeO(){
		return objectives.size();
	}
	/*public int sizeE(){
		return ents.size();
	}*/
	public Objective getO(int i){
		return objectives.get(i);
	}
	/*public EntityKey getE(int i){
		return ents.get(i);
	}*/

	//BufferedImage terrain;
	//BufferedImage heightMap;
	private String map;
	private String ent;

	private float startx=5;
	private float starty=5;
	private float startz=5;
	private int startTime=0;
	int levelNum=0;
	private String levelName="default";

	int aYear;
	int aDay;
	int aHour;

	boolean Default = true;

	int T;

	public Level(int j,Stats st){
		setStats(st);
		init(j);
	}
	public Level(int j){
		setStats(new Stats());
		init(j);
	}
	public void init(int u){
		objectives = new ArrayList<Objective>();
		//ents = new ArrayList<EntityKey>();
		levelNum=u;
		setLevelName("leVel "+levelNum);
	}

	public String getMap(){
		return map;
	}
	public void setMap(String n){
		map=n;
		Default=false;
	}

	public void addO(Objective o){
		objectives.add(o);
	}

	public void prime(){
		int m=objectives.size();
		for(int i=0;i<m;i++){
			objectives.get(i).prime();
		}
		T=m;
		parseEnts();
	}


	public void test(){
		int m=objectives.size();
		int u=0;
		for(int i=0;i<m;i++){
			if(!objectives.get(i).isComplete()){
				if(objectives.get(i).satisfied()){
					u++;
				}
			}else{
				u++;
			}
		}
		if(u>=m){
			Changer.win();
		}
	}
	public void parseEnts(){
		/*Entity e;
		for(int i=0;i<ents.size();i++){
			e=new EntityNPC();
			e.setVec(ents.get(i).v);
			Main.world.addE(e);
		}*/
		/*Main.world.addE(new EntityEnemy());

		gButton g1=new gButton();
		gButton g2=new gButton();
		gPlatform p1=new gPlatform(2f,0.2f);

		g1.setPos(16, 16, 2);
		g2.setPos(14, 16, 2);
		p1.setPos(15, 15, 1);

		g2.Useable();
		g1.setOutput(p1);

		g2.height=0.4f;
		Main.world.addE(g1);
		Main.world.addE(g2);
		Main.world.addE(p1);*/
		FileManager.loadEntityMap("testMe");
	}
	/*public void addE(String n,float x,float y,float z){
		ents.add(new EntityKey(n,x,y,z));
	}*/
	public Stats getStats() {
		return stats;
	}
	public void setStats(Stats stats) {
		this.stats = stats;
	}
	public float getStartX() {
		return startx;
	}
	public void setStartX(float startx) {
		this.startx = startx;
	}
	public float getStartY() {
		return starty;
	}
	public void setStartY(float starty) {
		this.starty = starty;
	}
	public float getStartZ() {
		return startz;
	}
	public void setStartZ(float startz) {
		this.startz = startz;
	}
	public int getStartTime() {
		return startTime;
	}
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
}
