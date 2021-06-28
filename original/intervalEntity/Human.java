package intervalEntity;

import java.util.HashMap;

import interval.Main;
import interval.ModelManager;
import interval.Tex;
import interval.TextureCache;
import interval.Timeline;
import interval.UserData;
import interval.VGO;
import intervalHistory.Dynasty;
import intervalHistory.Gene;
import intervalHistory.NameStock;
import intervalHistory.Opinion;
import intervalParticle.PFloater;

/**
 * @author Aninon
 * @usage NPC child, intended to be a dynamic member of the society system, is able to have genes, generations, traits and birth more humans
 */

public class Human extends EntityNPC {
	Dynasty dynasty;
	Gene gene;
	String firstName;
	String maiden;
	float age;
	float health;
	boolean gender=true;
	HashMap<Entity,Opinion> thoughts;
	Human spouse;
	PFloater display;
	int maturity=-1;
	
	int[] shirt;

	HashMap<String,Tex> skin;
	
	String currentModel="";
	int currentSkin=0;

	public void marry(Human h){
		dynasty=h.dynasty;
		spouse=h;
		h.spouse=this;
	}
	public Human(){
		this("John");
		


		//-11165921

		//5f 81 3e


		//display.setText("oh hai",6);
	}
	public Human(String name){
		super();
		firstName=name;
		gene=new Gene(0,0,100,50);

		gender=Math.random()>0.5;
		baseModel=gender?"man":"lady";
		health=100;
		thoughts=new HashMap<Entity,Opinion>();
		display=new PFloater(this,2f);
		skin=new HashMap<String,Tex>();
		shirt=new int[]{100,0,0};
	}
	public Human birth(Human dad){
		Human child=new Human("Bob");//NameStock.getFirstName(gender, r));
		child.dynasty=dad.dynasty;
		child.maiden=dynasty.getLastName();
		return child;
	}
	public void defaultHuman(){
		dynasty=NameStock.getDynasty(getPIN());
		maiden=dynasty.getLastName();
	}
	public boolean canBear( Human h){
		return gender!=h.gender && dynasty==h.dynasty && !maiden.equals(h.maiden);
	}
	public float getAge(){
		return age;
	}
	public void setAge(float a){
		 age=a;
	}
	public boolean getGender(){
		return gender;
	}
	public void interact(EntityPerson p){

	}
	public void time(Timeline t){
		super.time(t);
		age+=0.01f;
		if(age>=100)
			age=0;
	}
	@Override
	public void render(){
		
		if(UserData.isGui()&&!Main.world.particles.contains(display)){
			Main.world.addP(display);
		}
		display.age=0;
		display.setText(6,
				firstName+" "+maiden+" D:"+dynasty.getLastName(),
				"id: "+PIN,
				"age: "+Math.round(age)
				);
		int mm=maturity;
		maturity=age>20?age>60?2:1:0;
		if(mm!=maturity){
			mat();
		}
		super.render();
	}

	@Override
	protected void accessModel(String up,String low){
		currentModel=baseModel+up+"-"+baseModel+low;
		VGO vox=ModelManager.getModel(currentModel);
		vox.orientPlain(r);
		vox.orient(base);
		parseModel();
		Tex txx=skin.get(maturity+up);
		currentSkin=(txx==null?0:txx.getID());
		vox.render(currentSkin);
	}

	public void extra(String s){
		String st[]=s.split(",");
		properName=st[0];
		firstName=st[0];
		gene=new Gene(Integer.parseInt(st[1]),Integer.parseInt(st[2]),Integer.parseInt(st[3]),Integer.parseInt(st[4]));
		shirt[0]=Integer.parseInt(st[5]);
		shirt[1]=Integer.parseInt(st[6]);
		shirt[2]=Integer.parseInt(st[7]);
		skins();
		defaultHuman();
	}
	public void skins(){
		if(gender){
			skin2("boy",0);
			skin2("man",1);
			skin2("gramp",2);
		}else{
			skin2("girl",0);
			skin2("lady",1);
			skin2("gran",2);
		}
	}
	
	private  void skin2(String n,int u){
		skin3(u,n,"UIdle");
		skin3(u,n,"UIdle2");
		skin3(u,n,"UWalk1");
		skin3(u,n,"UWalk2");
		skin3(u,n,"UTalk1");
		skin3(u,n,"UTalk2");
	}
	private void skin3(int u,String n,String b){
		skin.put(u+b, TextureCache.genetics(gene,shirt[0],shirt[1],shirt[2],n+b) );
	}
	public String getM(){
		return currentModel;
	}
	public int getS(){
		return currentSkin;
	}
	public void gender(boolean b){
		gender=b;
		 mat();
	}
	private void mat(){
		switch(maturity){
		case 0: baseModel=gender?"boy":"girl";break;
		case 1: baseModel=gender?"man":"lady";break;
		default: baseModel=gender?"gramp":"gran";
		}
	}
	public Gene getGene(){
		return gene;
	}

}