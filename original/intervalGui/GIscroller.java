package intervalGui;

import interval.FileManager;
import interval.Tex;

public class GIscroller extends GI {

	float ff;
	public GIscroller(int x,int y,float f){
		this.x=x;
		this.y=y;
		sx=2;
		sy=48;
		ff=f;
		tex=new Tex(FileManager.dir()+"scroller.png");
		//colorNormal=new Vec(1,1,1,1);//new Vec(0.2f,0.6f,0.7f,1f);
		//colorOver=new Vec(0.9f,0.8f,0.6f,1f);
		//colorDown=new Vec(1,1,1,0.5f);
		//selection=colorNormal;
	
	}
	
	/*public void render(){
		GL11.glColor4f(1,1,1,1);
		render2();
	}
	public void render2(){
		
		if(!visible){
			return;
		}
		tex.bind();
		GL11.glPushMatrix();
		
		//Texture.bind(tid);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex3f(x,y-sy,-0.02f);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex3f(x,y,-0.02f);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex3f(x+sx,y,-0.02f);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex3f(x+sx,y-sy,-0.02f);
		GL11.glEnd();
		GL11.glPopMatrix();
	}*/
	public String down(){
		return "scroll"+(    (16f) +((sy-(my-y))*ff/sy)   );
	}
}
