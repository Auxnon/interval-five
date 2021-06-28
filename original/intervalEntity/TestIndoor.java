package intervalEntity;

import interval.FileManager;
import interval.Tex;
import interval.Texture;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

public class TestIndoor extends Indoor {

	public TestIndoor(){
		walls = new Tex(FileManager.dir()+"interior1.png");
		boundex=10;
		boundey=10;
		doors = new ArrayList<Door>();
		doors.add(new Door(5,0,0.001f));
		doors.add(new Door(5,10,0.001f));
	}
	public float[]start(){
		return new float[]{5,0.2f};
	}
	public void one(){
		GL11.glColor4f(0.6f,0.6f,0.6f,1f);
	}
	public void two(){
		GL11.glColor4f(0.6f,0.6f,0.6f,0f);
	}
	public void renderInner(){
		walls.bind();
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		GL11.glColor4f(0.2f,0.2f,0.2f,1f);
		GL11.glNormal3f(0,0,1);
		GL11.glTranslatef(0, 0, 0.2f);
		GL11.glBegin(GL11.GL_QUADS);
		
		
		GL11.glTexCoord2f(0.5f, 0);GL11.glVertex3f(boundsx-0.5F, boundsy-0.5F, 0);
		GL11.glTexCoord2f(1f, 0);GL11.glVertex3f(boundex+0.5F, boundsy-0.5F, 0);
		GL11.glTexCoord2f(1f, 2);GL11.glVertex3f(boundex+0.5F, boundey+0.5F, 0);
		GL11.glTexCoord2f(0.5f, 2);GL11.glVertex3f(boundsx-0.5F, boundey+0.5F, 0);
		
		one();
		GL11.glNormal3f(0,-1,0);
		GL11.glTexCoord2f(0f, 1);GL11.glVertex3f(boundsx-0.5F, boundey+0.5F, 0);
		GL11.glTexCoord2f(0.5f, 1);GL11.glVertex3f(boundex+0.5F, boundey+0.5F, 0);
		two();
		GL11.glTexCoord2f(0.5f, 0);GL11.glVertex3f(boundex+0.5F, boundey+0.5F, 5);
		GL11.glTexCoord2f(0f, 0);GL11.glVertex3f(boundsx-0.5F, boundey+0.5F, 5);
		
		one();
		GL11.glNormal3f(0,1,0);
		GL11.glTexCoord2f(0f, 1);GL11.glVertex3f(boundex+0.5F, boundsy-0.5F, 0);
		GL11.glTexCoord2f(0.5f, 1);GL11.glVertex3f(boundsx-0.5F, boundsy-0.5F, 0);
		two();
		GL11.glTexCoord2f(0.5f, 0);GL11.glVertex3f(boundsx-0.5F, boundsy-0.5F, 5);
		GL11.glTexCoord2f(0f, 0);GL11.glVertex3f(boundex+0.5F, boundsy-0.5F, 5);
		
		one();
		GL11.glNormal3f(-1,0,0);
		GL11.glTexCoord2f(0f, 1);GL11.glVertex3f(boundex+0.5F, boundey+0.5F, 0);
		GL11.glTexCoord2f(0.5f, 1);GL11.glVertex3f(boundex+0.5F, boundsy-0.5F, 0);
		two();
		GL11.glTexCoord2f(0.5f, 0);GL11.glVertex3f(boundex+0.5F, boundsy-0.5F, 5);
		GL11.glTexCoord2f(0, 0);GL11.glVertex3f(boundex+0.5F, boundey+0.5F, 5);
		
		one();
		GL11.glNormal3f(1,0,0);
		GL11.glTexCoord2f(0f, 1);GL11.glVertex3f(boundsx-0.5F, boundsy-0.5F, 0);
		GL11.glTexCoord2f(0.5f, 1);GL11.glVertex3f(boundsx-0.5F, boundey+0.5F, 0);
		two();
		GL11.glTexCoord2f(0.5f, 0);GL11.glVertex3f(boundsx-0.5F, boundey+0.5F, 5);
		GL11.glTexCoord2f(0, 0);GL11.glVertex3f(boundsx-0.5F, boundsy-0.5F, 5);
		GL11.glEnd();
		Texture.bind(0);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glBegin(GL11.GL_QUADS);
		renderDoors();
		GL11.glEnd();
		
	}
	
	
}
