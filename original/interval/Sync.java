package interval;

import java.util.ArrayList;

public class Sync {

	
	
	
	boolean updated=false;
	static ArrayList<Object[]> prePass;
	
	public void init(){
		prePass = new ArrayList<Object[]>();
	}
	
	public void run(){
		if(updated){
			parsePass();
		}
	}

	//Message
	public void addPassSafe(String in){
		addPassSafe(in,0);
	}
	public void addPassSafe(String in,int d){
		prePass.add(new Object[]{in,d});
		updated=true;
	}
	
	public void parsePass(){
		while(!prePass.isEmpty()){
			Object[] o=prePass.remove(0);
			Message.addPass((String)o[0],(Integer)o[1]);
		}
	}
}
