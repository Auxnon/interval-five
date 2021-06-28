package intervalGui;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import interval.Changer;
import interval.FileManager;
import interval.Message;

public class GuiEditorStats extends Gui {

	
	public void initer() {
		menuPriority=true;
		add(new GI("Open Game Directory",0,44).centerLeft());
		
		add(new GI("Clear Model Cache",0,36).centerLeft());
		add(new GI("Clear Money",0,32).centerLeft());
		add(new GI("Clear Player Stats",0,28).centerLeft());
		
		add(new GI("<- Back",0,6).centerLeft());
	}
	
	public void command(String s,GI g){
		if(s.contains("Model")){
		
		}else if(s.equals("Level")){
			
		}else if(s.equals("Open")){
			File f=new File(FileManager.dir());
			try {
				Desktop.getDesktop().open(f);
			} catch (IOException e) {
				Message.er(e);
			}
		}else if(s=="<- Back"){
			Changer.menuUp();
		}
	}
	


}

