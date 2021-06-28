package intervalHistory;

import java.util.ArrayList;

public class Town {
	String name;
	ArrayList<Home> homes;
	
	public Town(String n){
		homes=new ArrayList<Home>();
		name=n;
	}
	public void time(){
		for(Home h:homes)
			h.time();
	}
	public void add(Home h){
		homes.add(h);
	}
}
