package intervalEntity;

import interval.Main;
import interval.Tex;

import java.util.ArrayList;

public class Indoor extends Container {

	ArrayList <Door> doors;
	Tex walls;
	
	public void renderDoors(){
		int m=doors.size();
		for(int i=0;i<m;i++){
			doors.get(i).render();
		}
	}
	public float[]start(){
		return new float[]{0,0};
	}
	
	public void used(Entity e){
		if(!ents.contains(e)){
			e.contain(this);
			float d[] = start();
			e.place(d[0],d[1],0);
		}
		
	}
	
	public void sendTo(Entity ei,int to){
		//System.out.println(to);
		if(to!=0){
		Entity ee=Main.world.findE(to);
		if(ee!=null && ee instanceof Container){
			Container cc= (Container) ee;
			ei.uncontain();
			cc.used(ei);
		}
		}else{
			ei.uncontain();
			ei.place(x, y);
		}
	}
	
	public void innerTouch(Entity e){
		int m=doors.size();
		for(int i=0;i<m;i++){
			Door d=doors.get(i);
			if(e.x>d.x-d.sx2 && e.x<d.x+d.sx2){
				if(e.y>d.y-d.sy2 && e.y<d.y+d.sy2){
					sendTo(e,d.targ);
				}
				
			}
		}
	}
}
