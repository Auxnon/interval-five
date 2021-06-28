package interval;

public class VecF extends Vec {

	public float vx,vy,vz;
	public float r;
	
	public VecF(){
	}
	
	public VecF(float xi,float yi,float zi,float vxi){
		x=xi;y=yi;z=zi;
		vx=vxi;
	}
	
	public VecF(float xi,float yi,float zi,float vxi,float vyi,float vzi){
		set(xi,yi,zi,vxi,vyi,vzi);
	}
	public void set(float xi,float yi,float zi,float vxi,float vyi,float vzi){
		x=xi;y=yi;z=zi;vx=vxi;vy=vyi;vz=vzi;
	}
	
	public VecF(float xi,float yi,float zi,float dxi,float dyi,float vzi,float ri){
		x=xi;y=yi;z=zi;vx=dxi;vy=dyi;vz=vzi;r=ri;
	}
	public void factor(float f){
		super.factor(f);
		vx*=f;
		vy*=f;
		vz*=f;
		r*=f;
	}
	public String toString(){
		return super.toString()+"   "+vx+","+vy+","+vz+"; "+r;
	}
	public VecF clone(){
		return new VecF(x,y,z,vx,vy,vz,r);
	}
}
