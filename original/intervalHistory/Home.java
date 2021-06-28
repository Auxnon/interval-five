package intervalHistory;

import interval.Main;
import intervalEntity.Human;

import java.util.ArrayList;



public class Home {
	ArrayList<Human> members;
	ArrayList<Human> bearers;
	int ID;
	public Home(int id){
		ID=id;
		members=new ArrayList<Human>();
		bearers=new ArrayList<Human>();
	}
	public void time(){
		if(chance(10))
			birth();
	}
	public void birth(){
		
	}
	public void marry(){
		
	}
	public boolean chance(int f){
		return Main.world.Rand(ID, f)==0;
	}
}
