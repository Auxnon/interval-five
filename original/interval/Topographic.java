package interval;

import java.util.ArrayList;
import java.util.HashMap;


public class Topographic {
	//ArrayList<Action> actions;
	HashMap<Integer,ArrayList<Action>> actions;
	
	public Topographic(){
		actions= new HashMap<Integer, ArrayList<Action>>();
	}
	
	public void add(int x,int y,String s,int id){
		int year=Main.world.timeline.getTerra();
		ArrayList<Action> ar =actions.get(year);
		if(ar==null){
			ar= new ArrayList<Action>();
		}
		ar.add(new Action(x,y,s,id));
		actions.put(year, ar);
	}
	public void cause(int i){
		ArrayList<Action> ar =actions.get(i);
		if(ar!=null){
		int m=ar.size();
		for(int j=0;j<m;j++){
			ar.get(j).run(i);
		}
		}
		
	}
}
