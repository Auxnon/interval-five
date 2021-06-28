package intervalEntity;

import interval.ModelManager;
import interval.Main;
import interval.Timeline;
import interval.UserData;
import interval.VGO;
import intervalParticle.PFloater;

import org.lwjgl.opengl.GL11;

public class EntityLabel extends Entity {
	PFloater display;
	
	public EntityLabel(String name,float x,float y){
		place(x,y);
		display=new PFloater(name,this,1f);
		//display.text1=name;
	}
	public EntityLabel(String name,float x,float y,float z){
		place(x,y,z);
		display=new PFloater(name,this,1f);
		//display.text1=name;
	}
	
	int rr=0;
	public void render(){
		if(UserData.isGui()&&!Main.world.particles.contains(display)){
			Main.world.addP(display);
		}
		display.age=0;
		
		GL11.glColor4f(1, 0.2f, 0.2f, 1f);
		GL11.glPushMatrix();
		GL11.glTranslatef(x,y, z);
		
		VGO v=ModelManager.getModel("coin");
		v.orient(rr);
		parseModel();
		v.render(0);
		GL11.glPopMatrix();
	}
	@Override
	public void time(Timeline t){
		super.time(t);
		rr++;
		if(rr>360){
			rr=0;
		}
	}
}
