package intervalGui;

import interval.Changer;
import interval.IFont;
import interval.LevelManager;
import interval.Message;
import interval.Render;

public class GuiLevelSelect extends Guiscroll {

	GI GG;
	GIlist list;
	public void initer() {
		setRender(false);
		Render.backColor(0.2f, 0.2f,0.2f);
		this.menuPriority=true;
		list=new GIlist("lvl", LevelManager.getLvlNames(),0,32,32, IFont.BLACK);
		list.centerX();
		list.clip(20);
		list.setAppend("lvl:");
		add(list);
		add(new GI("Load:",16,10));
		GG = new GI(LevelManager.getLvlName(0),26,10,IFont.EMBOSS);
		GG.displayOnly=true;
		GG.canKey=false;
		add(GG);
		add(new GI("<- Back",0,6).centerLeft());
		add(new GI("pardon my mess.",32,60).centerX());
	
		add(new GIlvl("dummy", 1, 0, 24, 0, 0, 0x0055ff));
		add(new GIlvl("dummy", 89, 8, 24, 0, 2, 0x3d8fe3));
		add(new GIlvl("dummy", 4, 16, 24, 1, 0, 0x272874));
		add(new GIlvl("dummy", 8, 24, 24, 1, 1, 0x1b8725));
		add(new GIlvl("dummy", 9, 32, 24, 2, 2, 0x68de19));
		add(new GIlvl("dummy", 12, 40, 24, 3, 1, 0xcbd20d));
		add(new GIlvl("dummy", 14, 48, 24, 3, 2, 0xd26a0d));
		add(new GIlvl("dummy", 25, 56, 24, 3, 3, 0xb81414));
	}
	public void command(String s,GI g){
		if(s=="<- Back"){
			Changer.titleSimple();
		}else if(s.startsWith("lvl:")){
			String d=s.substring(4);
			Message.m(this,"here "+d);
			
			
			if(GG.action!=d){
				GG.redo(d);
			}
		}else if(s=="Load:"){
			Changer.level(GG.action);
		}
	}
}
