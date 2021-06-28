package interval;

public class Vec {

	
	public float x,y,z;
	

	public void setV(float xi,float yi,float zi){
		x=xi;y=yi;z=zi;
	}
	
	public Vec(){
		
	}
	public Vec(float xi,float yi,float zi){
		x=xi;y=yi;z=zi;
		//r=dx=dy=vz=0;
	}
	
	
	public Vec(double xi,double yi,double zi){
		x=(float) xi;y=(float) yi;z=(float) zi;
		//dx=dy=vz=0;
	}
	
	public float distance(Vec v2){
		float ddx=v2.x - x;
		float ddy=v2.y - y;
		float ddz=v2.z - z;
		
		return (float) Math.sqrt(ddx*ddx + ddy*ddy + ddz*ddz);
	}
	
	public void add(float xx,float yy,float zz){
		x+=xx;
		y+=yy;
		z+=zz;
	}
	public void factor(float f){
		x*=f;
		y*=f;
		z*=f;
	}
	public String toString(){
		return x+","+y+","+z;
	}
	public Vec clone(){
		return new Vec(x,y,z);
	}

}
