package interval;

import intervalEntity.EntityLabel;

public class ObjectivePoint extends Objective {
	float x;
	float y;
	float z;

	public ObjectivePoint(String nam,float xx,float yy,float zz){
		setName(nam);
		x=xx;y=yy;z=zz;
		setComplete(false);
	}
	
	public ObjectivePoint(String nam,String in){
		setName(nam);
		int m1=in.indexOf(",");
		x=Float.parseFloat(in.substring(0,m1));
		in=in.substring(m1+1);
		m1=in.indexOf(",");
		y=Float.parseFloat(in.substring(0,m1));
		z=Float.parseFloat(in.substring(m1+1));

		setComplete(false);
	}
	public void prime(){
		super.prime();
		setComplete(false);
		target=new EntityLabel("TARGET",x,y,z);
		if(!Main.world.contains(target)){
			Main.world.addE(target);
		}
	}
	
	public boolean satisfied(){
		float v=Main.player.getVecP().distance(target.getVec());
		if(v<2){
			setComplete(true);
			target.Destroy();
			alert();
			return true;
		}
		return false;
	}
}
