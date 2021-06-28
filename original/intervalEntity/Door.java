package intervalEntity;

import org.lwjgl.opengl.GL11;


public class Door {

	public float x,y,z,sx,sy,sz;
	float sx2,sy2;
	
	int targ;
	
	public Door(float xi,float yi,float zi){
		x=xi;
		y=yi;
		z=zi;
		sx=1f;
		sy=0.25f;
		sz=1.5f;
		sx2=sx/2f;
		sy2=sy/2f;
	}
	public void render(){
		GL11.glColor3f(0.8f, 0.65f, 0.3f);
		//GL11.glBegin(GL11.GL_QUADS);
		GL11.glNormal3f(0,0,1);
		GL11.glVertex3f(x-sx2, y-sy2, z);
		GL11.glVertex3f(x+sx2, y-sy2, z);
		GL11.glVertex3f(x+sx2, y+sy2, z);
		GL11.glVertex3f(x-sx2, y+sy2, z);
		
		GL11.glVertex3f(x-sx2, y-sy2, z+sz);
		GL11.glVertex3f(x+sx2, y-sy2, z+sz);
		GL11.glVertex3f(x+sx2, y+sy2, z+sz);
		GL11.glVertex3f(x-sx2, y+sy2, z+sz);
		
		GL11.glVertex3f(x+sx2, y-sy2, z+sz);
		GL11.glVertex3f(x-sx2, y-sy2, z+sz);
		GL11.glVertex3f(x-sx2, y-sy2, z);
		GL11.glVertex3f(x+sx2, y-sy2, z);
		
		GL11.glVertex3f(x-sx2, y+sy2, z+sz);
		GL11.glVertex3f(x+sx2, y+sy2, z+sz);
		GL11.glVertex3f(x+sx2, y+sy2, z);
		GL11.glVertex3f(x-sx2, y+sy2, z);
		
		
		GL11.glVertex3f(x+sx2, y+sy2, z+sz);
		GL11.glVertex3f(x+sx2, y-sy2, z+sz);
		GL11.glVertex3f(x+sx2, y-sy2, z);
		GL11.glVertex3f(x+sx2, y+sy2, z);
		
		GL11.glVertex3f(x-sx2, y-sy2, z+sz);
		GL11.glVertex3f(x-sx2, y+sy2, z+sz);
		GL11.glVertex3f(x-sx2, y+sy2, z);
		GL11.glVertex3f(x-sx2, y-sy2, z);
		
		//GL11.glEnd();
	}
}
