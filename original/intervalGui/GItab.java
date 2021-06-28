package intervalGui;

import interval.FileManager;
import interval.Tex;

import org.lwjgl.opengl.GL11;

public class GItab extends GI {

	//Gui parent;
	float fu=0;
	public GItab(float xi,float yi,boolean down){
		x=xi;
		y=yi;
		sy=4;
		sx=8;
		//parent = parenti;
		if(down){
			fu=0.5f;
		}
		tex=new Tex(FileManager.dir()+"tab.png");
		depth=-0.08f;

	}
	public boolean check(float i,float j){
		if(visible){
			return  super.check(i, j);
		}
		return false;
	}
	public String over(){
		return "tabbed";

	}

	public void render(float dx,float dy){
		if(!visible){
			return;
		}
		float X=x+dx;
		float Y=y+dy;
		GL11.glPushMatrix();
		GL11.glColor4f(1,1,1,1);
		tex.bind();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, fu+0.5f);
		GL11.glVertex3f(X,Y-sy,depth);
		GL11.glTexCoord2f(0, fu);
		GL11.glVertex3f(X,Y,depth);
		GL11.glTexCoord2f(1, fu);
		GL11.glVertex3f(X+sx,Y,depth);
		GL11.glTexCoord2f(1, fu+0.5f);
		GL11.glVertex3f(X+sx,Y-sy,depth);
		GL11.glEnd();
		GL11.glPopMatrix();

	}
}
