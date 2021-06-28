package intervalGui;

import interval.Changer;
import interval.Texture;
import interval.UserData;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiPreGame extends Gui {
	
	int timer=0;
	public GuiPreGame(){
		UserData.paused=false;
	}
	public void run(){
		Texture.bind(0);
		timer++;
		if(timer>=10 || Keyboard.next() || Mouse.isButtonDown(0)){
			Changer.menuNext(new GuiAninon());
		}
		GL11.glColor4f(0, 0.8f, 1, 1);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex3f(-64, 128,-0.01f);
		GL11.glVertex3f(128, 128,-0.01f);
		GL11.glVertex3f(128, -64,-0.01f);
		GL11.glVertex3f(-64, -64,-0.01f);
		GL11.glEnd();
	}
	@Override
	public void initer() {
		
	}
}
