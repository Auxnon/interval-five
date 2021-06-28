package intervalEntity;

import interval.Main;
import interval.Timeline;


public class gButton extends Gadget {

	
	public gButton(){
		height=0.2f;
		//weight=0f;
		this.sideCollision=true;
	}
	//boolean stop;
	int derr=0;
	@Override
	public void contactTop(Entity e){
		boolean ok=input==null;
		if(!ok)
			ok=input.outState;
		
		if(!e.grounded || !ok)
			return;
		if(derr<=0)
			ON=!ON;
		derr=2;
	}
	@Override
	public void used(Entity e){
		vz=0.2f;
	}
	@Override
	public void time(Timeline t){
		//super.time();
		if(derr>=0)
		derr--;
		
		
		if(ON)
			gadgetOn();
		else
			gadgetOff();

	}
	public void extra(String s){
		String st[]=s.split(",");
		properName=st[0];
		
		if(st.length>1){
			Entity e=Main.world.findE(st[1]);
			if(e!=null)
				this.setInput(e);
		}
	}
	
	
}
