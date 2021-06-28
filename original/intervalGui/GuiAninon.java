package intervalGui;

import interval.Changer;
import interval.Texture;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiAninon extends Gui {
	
	int timer=0;
	int tt;
	public GuiAninon(){
		tt=Texture.get("aninon");
		setRender(false);
	}
	public void initer(){
	}
	float dr=0;
	public void run(){
		Texture.bind(tt);
		timer++;
		if(timer<60){
			GL11.glColor4f(0,0,0,1);
		}else if(timer <300){
			dr+=0.01f;
			if(dr>1){
				dr=1;
			}
			GL11.glColor4f(dr,dr,dr,1);
		}else if(timer <420){
			dr-=0.01f;
			if(dr<0){
				dr=0;
			}
			GL11.glColor4f(dr,dr,dr,1);
		}
		if(timer>=420 || Keyboard.next() || Mouse.isButtonDown(0)){
			Changer.title();
		}
		
		
	
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex3f(0, 64,-0.01f);
		
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex3f(64, 64,-0.01f);
		
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex3f(64, 0,-0.01f);
		
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex3f(0, 0,-0.01f);
		GL11.glEnd();
	}
}
