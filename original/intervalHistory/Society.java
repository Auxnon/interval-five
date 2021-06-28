package intervalHistory;

import java.util.ArrayList;

public class Society {
	ArrayList<Town> towns;
	
	public void time() {
		for(Town t:towns)
			t.time();
	}
	public void add(Town t){
		towns.add(t);
	}
	
	
	public Society(){
		towns=new ArrayList<Town>();
		
		
		Town t1=new Town("StankTown");
		Town t2=new Town("PoopyBurg");
		
		Home h1= new Home(0);
		Home h2= new Home(1);
		Home h3= new Home(2);
		Home h4= new Home(3);
		Home h5= new Home(4);
		
		t1.add(h1);
		t1.add(h2);
		
		t2.add(h3);
		t2.add(h4);
		t2.add(h5);
		
		add(t1);add(t2);
	}
	
	/*public void join(String s){
		String st[]=s.split(".");
	}*/

}
