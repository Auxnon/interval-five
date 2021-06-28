package intervalEntity;

import interval.Vec;

public class EntityKey {

	String name;
	public Vec v;
	
	public EntityKey(String n,float x,float y,float z){
		name=n;
		v=new Vec(x,y,z);
	}
}
