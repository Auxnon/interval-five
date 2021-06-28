package interval;

public class VecP extends VecF {

	public float tx,ty;
	public short mode; 
	public short state;
	public int vPIN;
	public float health;
	public int index;

	public VecP(float xi,float yi,float zi,float vxi,float vyi,float vzi,float ri,short modei,short statei,int vpini,float healthi,float txi,float tyi,int indexi){
		x=xi;
		y=yi;
		z=zi;
		vx=vxi;
		vy=vyi;
		vz=vzi;
		r=ri;
		mode=modei;
		vPIN=vpini;	
		health=healthi;
		tx=txi;
		ty=tyi;
		state=statei;
		index=indexi;
	}
	
	public VecP(float i, float j, float k) {
		x=i;
		y=j;
		z=k;
	}
	public void set6(float x,float y,float z,float r,float tx,float ty){
		this.x=x;this.y=y;this.z=z;
		this.r=r;this.tx=tx;this.ty=ty;
	}
	

	public float distance(Vec v2){
		float ddx=v2.x - x;
		float ddy=v2.y - y;
		float ddz=v2.z - z;
		
		return (float) Math.sqrt(ddx*ddx + ddy*ddy + ddz*ddz);
	}
	public VecP clone(){
		return new VecP(x,y,z,vx,vy,vz,r,mode,state,vPIN,health,tx,ty,index);
	}
	public String toString(){
		return super.toString()+"\n\t"+tx+","+ty+":"+mode+","+state+"."+vPIN+" h"+health;
	}
}
