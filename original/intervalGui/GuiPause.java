package intervalGui;

import interval.Changer;

public class GuiPause extends Gui {

	
	public void initer() {
		add(new GI("Continue",32,56).centerX());
		add(new GI("Objectives",32,52).centerX());
		
		add(new GI("Options",32,14).centerX());
		add(new GI("Restart Level",32,10).centerX());
		add(new GI("Quit",32,6).centerX());
	}
	public void command(String s,GI g){
		if(s=="Continue"){
			Changer.unpause();
		}else if(s=="Objectives"){
			Changer.menuDown(new GuiObjectives());
		}else if(s=="Options"){
			Changer.menuDown(new GuiOptions());
		}else if(s=="Restart Level"){
			Changer.restart();
		}else if(s=="Quit"){
			Changer.unpause();
			Changer.title();
			
		}
	}
}
