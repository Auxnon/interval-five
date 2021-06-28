package intervalGui;

import interval.Changer;
import interval.Main;
import interval.UserData;

public class GuiEndTime extends Gui {

	public void initer() {
		setStuck();
		UserData.paused=true;
		add(new GI("Initial Time",32,56).centerX());
		add(new GI("Restart Level",32,32).centerX());
		add(new GI("Quit",32,26).centerX());
	}
	public void command(String s,GI g){
		if(s=="Initial Time"){
			Changer.unpause();
			if(Main.player.schedule.task.size()>1){
			Main.player.schedule.addTask("01end");
			}
			Main.player.timeTravel(Main.level.getStartTime());
		}else if(s=="Restart Level"){
			Changer.restart();
		}else if(s=="Quit"){
			UserData.paused=false;
			Changer.title();
		}
	}
	
}
