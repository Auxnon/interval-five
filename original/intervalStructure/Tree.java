package intervalStructure;

import interval.Main;

import org.lwjgl.opengl.GL11;

public class Tree extends Structure{

	public float vv=1f;
	public int textureID;
	public void Render(){
		vv=(age/2f)+0.5f;
		//height+=(vv-height)/50f;
		
		//height=vv;
		
		GL11.glColor3f(.4f, .7f, .3f);
		GL11.glBegin(GL11.GL_QUADS);
		/*

		GL11.glNormal3f(0, 0f, 1f);
		GL11.glVertex3f(x-sx,y-sy, z+height);

		//GL11.glNormal3f(0, 1f, 0);
		GL11.glVertex3f(x+sx, y-sy, z+height);

		//GL11.glNormal3f(0, 1f, 0);
		GL11.glVertex3f(x+sx,y+sy, z+height);

		//GL11.glNormal3f(0, 1f, 0);
		GL11.glVertex3f(x-sx, y+sy,z+height);
		
		
		GL11.glNormal3f(0, -1f, 0);
		GL11.glVertex3f(x-sx,y-sy, z+height);
		GL11.glVertex3f(x+sx, y-sy, z+height);
		GL11.glVertex3f(x+sx, y-sy, 0);
		GL11.glVertex3f(x-sx, y-sy,0);
		
		GL11.glNormal3f(0, 1f, 0);
		GL11.glVertex3f(x-sx,y+sy, z+height);
		GL11.glVertex3f(x+sx, y+sy, z+height);
		GL11.glVertex3f(x+sx, y+sy, 0);
		GL11.glVertex3f(x-sx, y+sy,0);
		
		GL11.glNormal3f(-1f, 0,0);
		GL11.glVertex3f(x-sx,y-sy, z+height);
		GL11.glVertex3f(x-sx, y+sy, z+height);
		GL11.glVertex3f(x-sx, y+sy, 0);
		GL11.glVertex3f(x-sx, y-sy,0);
		
		GL11.glNormal3f(1f, 0,0);
		GL11.glVertex3f(x+sx,y-sy, z+height);
		GL11.glVertex3f(x+sx, y+sy, z+height);
		GL11.glVertex3f(x+sx, y+sy, 0);
		GL11.glVertex3f(x+sx, y-sy,0);
		
		
		
		*/
		
		
		GL11.glEnd();

	}
	public Tree(){
		super();
		sx=0.2f;
		sy=0.2f;
	}
	
	public void setTime(int i){
		vv=(i/2f)+0.5f;
		height=vv;
	}
	public Tree(int x,int y){
		this();
		place(x,y);
	}
	public Tree(int x,int y,float z){
		this();
		startYear=Main.world.timeline.getTerra();
		place(x,y,z);
	}
}
