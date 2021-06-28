package intervalGui;

import interval.IFont;
import interval.VecF;

import org.lwjgl.opengl.GL11;

public class GIpass extends GI {

	public float counter=0;
	float sf=1;
	public GIpass(String str,int d){
		super(str,32,32,0.66f,0.66f,-0.001f,IFont.BLACK);
		switch(d){
		case 1:this.centerLeft();selection=new VecF(0.6f,0.8f,1,1);break;
		case 2:this.centerRight();selection=new VecF(0.7f,1f,0.7f,1);break;
		default: this.centerX();selection=new VecF(1,1,1,1);
		}
		ambient();
	}
	public GIpass(String str){
		this(str,0);
	}

	public void render(float dx,float dy){
		if(!visible){
			return;
		}
		//GL11.glPushMatrix();
		float f1=6-counter;
		float ff=(f1>1?1:f1);
	
		float Y=y+dy;
		float X=x+dx;
		GL11.glColor4f(selection.x,selection.y,selection.z,ff);
		tex.bind();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex3f(X,Y-sy*sf,depth);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex3f(X,Y,depth);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex3f(X+sx,Y,depth);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex3f(X+sx,Y-sy*sf,depth);
		GL11.glEnd();
		//GL11.glPopMatrix();

	}
}
