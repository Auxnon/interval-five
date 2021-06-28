package intervalEntity;

import interval.Main;
import interval.UserData;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class MouseSelector extends Entity {

	float width=2f;
	float height=2f;
	final float inner=0.2f;


	public MouseSelector(){
		super(false);
		winZ = BufferUtils.createFloatBuffer(1);
		viewport = BufferUtils.createIntBuffer(16);    
		modelview = BufferUtils.createFloatBuffer(16);
		projection = BufferUtils.createFloatBuffer(16);
		pos = BufferUtils.createFloatBuffer(3);//FloatBuffer.allocate(3);
		GL11.glGetFloat( GL11.GL_MODELVIEW_MATRIX, modelview);
		GL11.glGetFloat( GL11.GL_PROJECTION_MATRIX, projection);
		GL11.glGetInteger( GL11.GL_VIEWPORT, viewport);
	}
	IntBuffer viewport;
	FloatBuffer modelview;
	FloatBuffer projection;
	FloatBuffer pos;
	FloatBuffer winZ;

	public void think(){

		if(!Mouse.isButtonDown(1) && (!UserData.selectorLock || !Mouse.isButtonDown(0))){
			GL11.glGetFloat( GL11.GL_MODELVIEW_MATRIX, modelview);
			GL11.glGetFloat( GL11.GL_PROJECTION_MATRIX, projection);
			GL11.glGetInteger( GL11.GL_VIEWPORT, viewport);
			float winX;
			float winY;

			winX = (float)Mouse.getX();
			winY =  (float)Mouse.getY();

			GL11.glReadPixels((int)winX ,(int) winY, 1, 1, GL11.GL_DEPTH_COMPONENT,  GL11.GL_FLOAT, winZ);
			float z2=winZ.get(0);

			GLU.gluUnProject( winX, winY,z2, modelview, projection, viewport, pos);
			
			x=pos.get(0);
			y=pos.get(1);
			z=pos.get(2);
			
			
			dx=(int)Math.round(x);
			dy=(int)Math.round(y);
			if(UserData.getSelectionSize()%2==1){dx+=0.5f;dy+=0.5f;}
				

		}
	}

	float rr=0f;
	public void render(){

			width=UserData.getSelectionSize()/2.0f;
			height=UserData.getSelectionSize()/2.0f;

			float win=width-inner;
			float hin=height-inner;

			GL11.glPushMatrix();
			int sz=Main.level.getStats().getSize();
			if(x>0 && x<sz && y>0 && y<sz){
				z=0.5f+Main.world.land.getHigh((int)dx,(int)dy);
				if(z<0.001f){
					z=0.001f;
				}
			}else{
				z=1;
			}
			
			GL11.glTranslatef(dx,dy,z);

			GL11.glColor4f(1,1,1, 1.0f);
			GL11.glBegin(GL11.GL_QUADS);

			GL11.glVertex2f(-width,-height);
			GL11.glVertex2f(width,-height);
			GL11.glVertex2f(win,-hin);
			GL11.glVertex2f(-(win),-hin);

			GL11.glVertex2f(-width,height);
			GL11.glVertex2f(width,height);
			GL11.glVertex2f(win,hin);
			GL11.glVertex2f(-(win),hin);

			GL11.glVertex2f(-width,-height);
			GL11.glVertex2f(-width,height);
			GL11.glVertex2f(-win,hin);
			GL11.glVertex2f(-win,-hin);

			GL11.glVertex2f(width,-height);
			GL11.glVertex2f(width,height);
			GL11.glVertex2f(win,hin);
			GL11.glVertex2f(win,-hin);

			//GL11.glVertex2f(width,height);

			GL11.glEnd();

			GL11.glPopMatrix();
		

	}

}
