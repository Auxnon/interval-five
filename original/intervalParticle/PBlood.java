package intervalParticle;

import interval.Vec;

public class PBlood extends Particle{

	public PBlood(float x,float y,float z){
		this.x=x;
		this.y=y;
		this.z=z;
		vx=(float) (Math.random()-0.5f)*0.2f;
		vy=(float) (Math.random()-0.5f)*0.2f;
		vz=(float) (Math.random()-0.5f)*0.5f;
		
		buffer=0.94f;
		az=-0.008f;
		//az=-0.01f;
		bounce=0f;
		size1=0.15f;
		size2=0f;
		//alpha1=1;
		alpha=1;
		//alpha2=1f;
		//alphaRange=180;
		sizeRange=100;
		colorRange=80;
		lifetime=100;
		color1=new Vec(0.6,0,0);
		color2=new Vec(0.1f,0.03f,0.08f);
	}
}
