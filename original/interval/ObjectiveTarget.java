package interval;

import intervalEntity.Entity;

public class ObjectiveTarget extends Objective {

	Entity secondary;
	int type;
	
	String target1;
	String target2;
	
	public ObjectiveTarget(String name1,String name2){
		target1=name1;
		target2=name2;
	}
	public void prime(){
		super.prime();
		target=Main.world.findE(target1);
		secondary=Main.world.findE(target2);
	}
	
	public boolean satisfied(){
		//target.x - secondary.x;
		
		return false;
	}
}
