package interval;

import intervalEntity.Entity;

public class Objective{

	Entity target;
	private String name="default";
	private boolean complete;
	boolean firstTime;
	public Objective(){
		setComplete(false);
	}
/*	static Objective point(float xx,float yy,float zz){
		Objective o=new Objective();
		o.x=xx;o.y=yy;o.z=zz;
		
		return o;
	}*/

	public void prime(){
		firstTime=true;
	}
	
	public void alert(){
		if(firstTime){
			firstTime=false;
			Message.addPass("Completed: "+getName());
		}
	}
	public boolean satisfied(){
		
		return false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}
}
