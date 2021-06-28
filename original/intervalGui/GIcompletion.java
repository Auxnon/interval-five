package intervalGui;

import interval.IFont;
import interval.TextureCache;
import interval.VecF;

import org.lwjgl.opengl.GL11;

public class GIcompletion extends GI {


	float vv=0;
	public GIcompletion(String str,int yy,boolean success){
		super(str,32,yy,1,1,0,IFont.EMBOSS);
		centerX();
		ambient();
		

		if(success){
			vv=0.5f;
			colorNormal=new VecF(0.5f,0.5f,0.5f,0.5f);
		}else{
			vv=0;
			colorNormal=new VecF(1,1,1,1);
		}
	}
	

	public void render(float dx,float dy){
		if(!visible){
			return;
		}
		float X=x+dx;
		float Y=y+dy;
		GL11.glColor4f(colorNormal.x,colorNormal.y,colorNormal.z,colorNormal.vx);

		float xo=X+sx;
		tex.bind();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex3f(X,Y-sy,depth);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex3f(X,Y,depth);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex3f(xo,Y,depth);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex3f(xo,Y-sy,depth);
		GL11.glEnd();

		TextureCache.check.bind();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, vv+0.5f);
		GL11.glVertex3f(xo,y-sy,depth);
		GL11.glTexCoord2f(0, vv);
		GL11.glVertex3f(xo,y,depth);
		GL11.glTexCoord2f(1, vv);
		GL11.glVertex3f(xo+6,y,depth);
		GL11.glTexCoord2f(1,vv+0.5f);
		GL11.glVertex3f(xo+6,y-sy,depth);
		GL11.glEnd();

	}
}
