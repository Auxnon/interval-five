package interval;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class Loader extends Thread {

	public boolean RUN=true;
	static int MODE;
	int soundRetries;
	boolean running=true;
	static ArrayList<String> modelBuffer=new ArrayList<String>();;

	public Loader(){
		RUN=true;
		running=false;
	}

	public void run(){
		running=true;
		while(RUN&&!completion()){
			switch(MODE){
			case 1: SOUND();
			case 2: LOAD();
			}
		}
		running=false;
	}

	private boolean completion(){
		if(!Main.sound.loaded){
			MODE=1;
			return false;
		}
		if(!modelBuffer.isEmpty()){
			MODE=2;
			return false;
		}
		return true;
	}

	public void loadModel(String name){
		modelBuffer.add(name);
		retry();
	}
	
	public void retry(){
		if(!running){
			Main.loader=new Loader();
			Main.loader.start();
		}
	}
	private void LOAD(){
		while(!modelBuffer.isEmpty()){
			
			String name=modelBuffer.remove(0);
			Message.m(this, "Generating Model "+name +"("+modelBuffer.size()+" remains)");
			if(name.contains("-")){
				String st[]=name.split("-");
				ModelManager.combine2(st[0],st[1],false,name);
			}else{
				ModelManager.remakeModel2(name);
			}
			status(0);
		}
	}
	public void status(float D){
		Message.loader(D);
	}
	public int generateSound(ByteBuffer b,int rate){
		return Main.sound.science(b, rate);
	}

	private void SOUND(){
		Main.sound.init1();
		//Message.m(this, "am i independent?");
		while(RUN&&!Main.sound.init2()){
			Message.m(this, "sound failed "+soundRetries+" time(s), retrying...");
			soundRetries++;
		}
		Message.m(this, "sound retries: "+soundRetries);
		Music.init();
		MODE=-1;
	}
}
