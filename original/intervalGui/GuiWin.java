package intervalGui;

import interval.Changer;
import interval.IFont;
import interval.MainFrame;
import interval.Message;
import interval.Render;
import interval.UserData;

import org.lwjgl.opengl.GL11;

public class GuiWin extends Gui {

	
	public GuiWin(){
		Message.reset();
		UserData.paused=true;
		setStuck();
	}
	
	public void initer() {
		GI banner=new GI(Message.insult(),32,60,IFont.BLACK).centerX();
		banner.displayOnly=true;
		add(banner);
		add(new GI("Restart Level",32,32).centerX());
		add(new GI("Quit",32,26).centerX());
		Render.border(1, 1,3,1);
	}
	public void command(String s,GI g){
		if(s=="Restart Level"){
			Changer.restart();
		}else if(s=="Quit"){
			Changer.title();
		}
	}

}
