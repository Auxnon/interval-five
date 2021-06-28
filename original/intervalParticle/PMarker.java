package intervalParticle;

import interval.Vec;

public class PMarker extends Particle {
	public PMarker(float x,float y,float z,int h){
		this(x,y,z,h,1);
	}
	public PMarker(float x,float y,float z,int h,int l){
		setRemain(true);
		this.x=x;
		this.y=y;
		this.z=z;
		switch(h){
		case 1:color=new Vec(0,1,0);break;
		case 2:color=new Vec(0,0,1);break;
		case 0: color=new Vec(1,0,0);break;
		default: float jj=((float)h/(float)l);color=new Vec(jj,1-jj,0);
		}
		size=0.06f;
		lifetime=1000;
	}
}
