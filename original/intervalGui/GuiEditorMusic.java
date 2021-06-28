package intervalGui;


import org.lwjgl.input.Keyboard;

import interval.Main;
import interval.MainFrame;
import interval.Changer;
import interval.Message;
import interval.Music;

public class GuiEditorMusic extends Gui {
	GImusic mus;
	//GItextInput lengthIn;
	Music state;
	int pace=3;
	boolean beep=true;
	
	static String[] toNote;
	static{
		toNote=new String[11];
		toNote[10]="nope"; //C#2
		toNote[9]="D2";
		toNote[8]="D#2";
		toNote[7]="E2";
		toNote[6]="F2";
		toNote[5]="F#2";
		toNote[4]="G2";
		toNote[3]="G#2";
		toNote[2]="A2";
		toNote[1]="C3";
		toNote[0]="C#3";

	}
	public void initer() {
		setRender(false);
		menuPriority=true;
		add(new GI("<- Back",0,6).centerLeft());
		mus=new GImusic(0,64);
		add(mus);

		add(new GI("Generate",32,16).centerX());
		add(new GIkey("Play",Keyboard.KEY_SPACE,32,12).centerX());
		
		add(new GI("Reset",32,4).centerX());
		//lengthIn=
		add(new GItextInput("Default Length",0,32,true,false,true));
		add(new GItextInput("Pace",28,32,true,false,true));
		add(new GItoggle("Loop","", new int[]{14,15}, 40,32));

		mus.redraw();
		state=Music.test;
	}

	public void command(String s,GI gg){
		if(s=="Play"){
			Music.test=state;
			state.renew();
		}else if(s=="Generate"){
			state=new Music();
			state.beep=beep;
			//Main.sound.Noterize(0,200,0,64,50,5f);
			state.setPace(pace);
			int[] f=mus.getDPoints();
			int[] l=mus.getLengths();
			int P=0;
			Message.addPass(arrayPrint(f));
			for(int j=0;j<f.length;j++){
				if(beep)
					state.add(9-f[j],(l[j]+1)*2,P);
				else
					state.add(toNote[f[j]],(l[j]+1)*2,P);
				P=0;//l[j]+1;
			}
			state.renew();

		}else if(s.equals("Default Length")){
			mus.defaultLength=((GItextInput)gg).getInt();
		}else if(s.equals("Pace")){
			Message.addPass("pace "+((GItextInput)gg).getInt());
			pace=((GItextInput)gg).getInt();
			
		}else if(s.equals("Loop")){
			Message.addPass("loop ");
		}else if(s=="Reset"){
			mus.reset();
		}else if(s=="<- Back"){
			Changer.menuUp();
		}else{
			Message.addPass(s);
		}
	}
	/*private String arrayPrint(float[] o){
		String out="[";
		for(float t:o)
			out+=t+",";

		out=out.substring(0,out.length()-1);
		out+="]";
		return out;
	}*/
	private String arrayPrint(int[] o){
		String out="[";
		for(int t:o)
			out+=t+",";

		out=out.substring(0,out.length()-1);
		out+="]";
		return out;
	}

	public void run(){
		if(MainFrame.isLong()){
			float ml=MainFrame.screenLeft();
			segment(ml,0,-0.5f,Math.abs(ml),64,0.3f,0.1f,0.1f,1);
			segment(64,0,-0.5f,Math.abs(ml),64,0.3f,0.1f,0.1f,1);
		}
		segment(0,0,-0.5f,64,64,0.4f,0.3f,0.3f,1f);
		super.run();
	}

}
