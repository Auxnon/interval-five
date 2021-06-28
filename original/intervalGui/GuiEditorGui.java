package intervalGui;

import interval.MainFrame;
import interval.ModelManager;
import interval.Changer;
import interval.Message;

public class GuiEditorGui extends Gui {


	boolean Fit=false;
	int C=1;
	

	public void initer() {
		setRender(false);
		menuPriority=true;
		GIwindow w=new GIwindow(1,32,32,16,16);
		add(w);
		add(new GI("<- Back",0,6).centerLeft());
	}
	
	public void command(String s,GI gg){
		
		if(s.equals("Level")){
		}else if(s.equals("renew")){

			//Image bim=Message.copiedImage();
			//if(bim!=null ){
			//	pic.set((BufferedImage)bim);
			//}

		
		}else if(s.equals(">remove window")){
			if(C>1){
				GIwindow.list.off();
				remove(gg);C--;
			}
		}else if(s.equals(">toggle auto-fit")){
			Fit=!Fit; Message.addPass("Auto-fit "+(Fit?"on":"off"));
		}else if(s.equals(">add window")){
			GIwindow w=new GIwindow(++C,32,32,16,16);
			add(w);
			GIwindow.list.off();
		}else if(s.equals(">process")){
			String text="";
			for(int u=0;u<size();u++){
				GI gi=get(u);
				if(gi instanceof GIwindow){
					GIwindow w=((GIwindow)gi);
					text+=w.toString()+"\n";
				}
			}
			Message.popText("GUI",text);
		}else if(s=="winRelease"){
			fit();
		}else if(s=="<- Back"){
			Changer.menuUp();
		}else if((s.startsWith(">") || s.endsWith(".png"))&&gg instanceof GIwindow){
			((GIwindow) gg).call(s);
		}
	}
	public void fit(){
		if(Fit){
			for(int u=0;u<size();u++){
				GI gi=get(u);
				if(gi instanceof GIwindow){
					boolean da=false;
					if(gi.x<0){
						gi.centerLeft();da=true;
					}else{
						gi.x=Math.round(gi.x);
					}
					if(gi.x>64){
						gi.centerRight();da=true;
					}else{
						gi.y=Math.round(gi.y);
					}
					if(!da)
						gi.orient=0;

					gi.sy=Math.round(gi.sy);
					gi.sx=Math.round(gi.sx);
				}
			}
		}
	}

	public void run(){
		if(MainFrame.isLong()){
			float ml=MainFrame.screenLeft();
			segment(ml,0,-0.5f,Math.abs(ml),64,0.3f,0.1f,0.1f,1);
			segment(64,0,-0.5f,Math.abs(ml),64,0.3f,0.1f,0.1f,1);
			segment(0,0,-0.5f,64,64,0.3f,0.3f,0.3f,1f);
		}
		super.run();
	}

}
