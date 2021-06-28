package intervalHistory;

import java.util.ArrayList;
import intervalEntity.Human;

public class Dynasty {
	String lastName;
	Human first;
	ArrayList<Human> members;
	public Dynasty(){
		lastName="Smith";
		members=new ArrayList<Human>();
	}
	public Dynasty(String n){
		lastName=n;
		members=new ArrayList<Human>();
	}
	public String getLastName(){
		return lastName;
	}
}
