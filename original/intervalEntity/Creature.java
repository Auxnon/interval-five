package intervalEntity;

import interval.Identity;
import interval.Main;
import interval.Timeline;
import interval.VecF;
import intervalParticle.PFloater;


public class Creature extends Entity{

	
	Identity id;
	PFloater label;
	
	public Creature(float x,float y){
		this();
		place(x,y);
	}
	
	public Creature(){
		id= new Identity();
		
	}
	
	@Override
	public void time(Timeline t){
		
		id.aging();
		if(id.isLiving()){
			Color = new VecF(0,1,0,1);
		}else{
			Color = new VecF(1,0,0,1);
		}
		
		if(!Main.world.particles.contains(label)){
			Main.world.addP(label);
		}
		
	}
	
	public void setTime(int i){
		id.aging();
	}
	//id.adjustTime();
	
}
