package intervalEntity;

import java.util.ArrayList;

public class Container extends Gadget {

	ArrayList<Entity> ents;
	float boundsx,boundex;
	float boundsy,boundey;
	
	public Container(){
		//PIN = Main.world.allocateID();
		ents=new ArrayList<Entity>();
	}
	

	
	public void removal(){
		int m= ents.size();
		for(int i=0;i<m;i++){
			ents.get(i).uncontain();
			m--;
		}
		ents.clear();
	}
	public void setTime(long t,int e){
		super.setTime(t,e);
		removal();
	}
	public void renderInner(){
		
	}
	public void used(Entity e){
		System.out.println("USED");
		if(!ents.contains(e)){
			e.contain(this);
			//destx=x;
			//desty=y;
		}
		//JOptionPane.showMessageDialog(null, "alert","you used this vehicle! congratulations?",JOptionPane.OK_OPTION);
	}
	

	public void innerTouch(Entity e){
	}
}
