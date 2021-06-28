package intervalGui;

import interval.Changer;
import interval.Message;

public class GuiEditor extends Gui {

	public void initer() {
		add(new GI("Level Editor",32,58).centerX());
		add(new GI("Human Editor",32,54).centerX());
		add(new GI("Model Editor",32,50).centerX());
		add(new GI("Progress Editor",32,46).centerX());
		add(new GI("Gui Editor",32,42).centerX());
		add(new GI("Stats Editor",32,38).centerX());
		add(new GI("Sound Editor",32,34).centerX());
		add(new GI("Music Editor",32,30).centerX());
		
		add(new GI("???",32,10).centerX());
		add(new GI("<- Back",0,6).centerLeft());
	}
	public void command(String s,GI g){
		if(s.contains("Level"))
			Changer.menuDown(new GuiEditorLevelOptions());
		else if(s.contains("Model"))
			Changer.menuDown(new GuiEditorModel());
		else if(s.contains("Progress"))
			Changer.menuDown(new GuiEditorProgress());
		else if(s.contains("Human"))
			Changer.menuDown(new GuiEditorHuman());
		else if(s.contains("Gui"))
			Changer.menuDown(new GuiEditorGui());
		else if(s.contains("Sound"))
			Changer.menuDown(new GuiEditorSound());
		else if(s.contains("Music"))
			Changer.menuDown(new GuiEditorMusic());
		else if(s.contains("Stats"))
			Changer.menuDown(new GuiEditorStats());
		else if(s=="<- Back")
			Changer.menuUp();
		else if(s=="???")
			Message.assMessage(Message.insult(),40);
	}
}
