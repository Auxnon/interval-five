package intervalGui;

import interval.Changer;
import interval.Main;
import interval.Objective;

public class GuiObjectives extends Gui {
	
	public void initer() {
		setRender(false);
		int m=Main.level.sizeO();
		for(int i=0;i<m;i++){
			Objective ob = Main.level.getO(i);
			add(new GIcompletion(ob.getName(),60-i*6,ob.isComplete()));
		}
		add(new GI("<-Back",0,6).centerLeft());
	}
	public void command(String s,GI g){
		if(s=="Continue"){
	
		}else if(s=="<-Back"){
			Changer.menuUp();
		}
	}
}
