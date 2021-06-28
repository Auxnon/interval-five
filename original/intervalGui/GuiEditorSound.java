package intervalGui;

import java.nio.ByteBuffer;

import org.lwjgl.input.Keyboard;

import interval.IFont;
import interval.Main;
import interval.MainFrame;
import interval.Changer;
import interval.Message;
import interval.SoundFX;

public class GuiEditorSound extends Gui {
	SoundFX test;
	GIsound so;
	GIscale time;
	GIscale quality;
	GIscale cut;
	GIscale freq;
	public void initer() {
		setRender(false);
		menuPriority=true;
		add(new GI("<- Back",0,6).centerLeft());
		so=new GIsound(0,64);
		add(so);
		add(new GI("Generate",32,16).centerX());
		
		add(new GIkey("Play",Keyboard.KEY_SPACE,32,12).centerX());
		add(new GI("Convert All Notes",32,4).centerX());
		
		time =new GIscale("~c (ms):","time",0,32,IFont.EMBOSS,500,20,4000);
		quality =new GIscale("quality:","qual",0,28,IFont.EMBOSS,6,16,100);
		cut =new GIscale("cutoff:","cut",0,24,IFont.EMBOSS,0,10,100);
		freq =new GIscale("frequency:","freq",0,20,IFont.EMBOSS,100,16,1000);
		//time.half();
		add(time);
		add(quality);
		add(cut);
		add(freq);
		test=SoundFX.boop;
		so.redraw();

		add(new GI("Output",56,8));
		add(new GI("Input",56,4));
		
		add(new GItextInput("txtIn",56,12));
	}

	public void command(String s,GI gg){
		if(s.equals("Generate")){
			//time
			//quality  x100
			//cutoff
			//amp
			//frequency
			float tyme=time.getIndex()/1000f;
			int qual=quality.getIndex()*100;
			int T=(int) (tyme*qual);
			float cutt=cut.getIndex()/100f;
			int freqq=freq.getIndex();
			//300,0,64,100,so.getPoints()),600

			applySound(T,cutt,freqq,so.getPoints(),qual);
		}else if(s.startsWith("POP")){
			if( s.contains(",")&&s.contains("{")){
				s=s.substring(3);
				String s1[]=s.split("{");
				String s2[]=s1[1].split("}");
				String o[]=(s1[0]+s2[1]).split(",");
				String a[]=s2[0].split(",");
				setSoundPoints(a);
				int T=Integer.parseInt(o[0]);
				float cutt=Float.parseFloat(o[1]);
				int freqq=Integer.parseInt(o[3]);
				int qual=Integer.parseInt(o[4]);
				applySound(T,cutt,freqq,so.getPoints(),qual);
				quality.setSlider(qual/100f);
				time.setSlider(1000*T/qual);
				cut.setSlider(cutt*100);
				freq.setSlider(freqq);
			}else{
				Main.sync.addPassSafe("Improper Sound Input Format!");
			}
		}else if(s=="Input"){
			Message.popText("Sound Input", true);
		}else if(s=="Output"){
			float tyme=time.getIndex()/1000f;
			int qual=quality.getIndex()*100;
			int T=(int) (tyme*qual);
			float cutt=cut.getIndex()/100f;
			int freqq=freq.getIndex();
			String st=T+","+cutt+","+64+","+freqq+","+arrayPrint(so.getPoints())+","+ qual;
			Message.popText("Sound", st);
		}else if(s=="Play"){
			//test.play();
			Main.sound.runInstance(test.getInt());
		}else if(s.startsWith("Convert")){
			//frequency
			float tyme=time.getIndex()/1000f;
			int qual=quality.getIndex()*100;
			int T=(int) (tyme*qual);
			float cutt=cut.getIndex()/100f;
			int freqq=freq.getIndex();
			//300,0,64,100,so.getPoints()),600

			Main.sound.Noterize(0, qual, cutt, 64, freqq, so.getPoints());
			//applySound(T,cutt,freqq,so.getPoints(),qual);
			Message.addPass("Converted");
		}else if(s=="<- Back"){
			Changer.menuUp();
		}
	}
	private void applySound(int T,float cutt,int freqq,float[] f,int qual){
		ByteBuffer by=Main.sound.genGradient(T,cutt,64,freqq,f);
		so.setBack(by.duplicate());
		so.redraw();
		test=new SoundFX(Main.sound.science(by,qual));
		Message.addPass("Generated");
	}
	private String arrayPrint(float[] o){
		String out="[";
		for(float t:o)
			out+=t+",";

		out=out.substring(0,out.length()-1);
		out+="]";
		return out;
	}
	private void setSoundPoints(String s[]){
		float[] f=new float[s.length];
		for(int u=0;u<s.length;u++){
			f[u]=Float.parseFloat(s[u]);
		}
		so.setPoints(f);
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
