package intervalEntity;

import interval.Main;
import interval.Texture;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

public class Test2Indoor extends Indoor {

	public Test2Indoor(){
		boundex=10;
		boundey=10;
		doors = new ArrayList<Door>();
		doors.add(new Door(2,0,0.001f));
		Door dor=new Door(2,10,0.001f);
		
		int m = Main.world.sizeE();
		for(int i=0;i<m;i++){
			Entity eee=Main.world.getE(i);
		if(eee instanceof Indoor && eee !=this){
			dor.targ=eee.PIN;
		}
		}
		
		doors.add(dor);
	}
	public float[]start(){
		return new float[]{2,0.2f};
	}
	
	public void renderInner(){
		Texture.bind(0);
		GL11.glColor4f(0.4f,0.4f,0.4f,1f);
		GL11.glNormal3f(0,0,1);
		GL11.glTranslatef(0, 0, 0.2f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex3f(0, 0, 0);
		GL11.glVertex3f(10, 0, 0);
		GL11.glVertex3f(10, 10, 0);
		GL11.glVertex3f(0, 10, 0);
		
		renderDoors();
		GL11.glEnd();
	}
	
	
}
