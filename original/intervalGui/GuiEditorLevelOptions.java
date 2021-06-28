package intervalGui;

import interval.Changer;

public class GuiEditorLevelOptions extends Gui {

	int floy=32;
	GIscale Size;
	
	public void initer() {
		Size=new GIscale("Size: 32*2^","SSize",0,50,30f,8);
		add(Size);
		add(new GI("<-Back",0,6).centerLeft());
		add(new GI("Generate",32,32));
	}
	
	//32 64 128 256 512 1024 2048 4096
	public void command(String s,GI g){
		if(s=="<-Back"){
			Changer.menuUp();
		}else if(s=="Generate"){
			Changer.editorLevel(floy);
		}else if(s.contains("SSize")){
			int flo=(int)Math.floor(Float.parseFloat(s.substring(5,s.length())));
			
			
			
			switch(flo){
			case 0: floy=32;break;
			case 1: floy=64;break;
			case 2: floy=128;break;
			case 3: floy=256;break;
			case 4: floy=512;break;
			case 5: floy=1024;break;
			case 6: floy=2048;break;
			case 7: floy=4096;break;
			}
			/*
			String herk="size: "+floy;
			if(!disp.action.contains(herk)){
				disp.redo(herk, IFont.BLACK);
				disp.centerLeft();
			}*/
		}
	}
}
