package intervalParticle;

import interval.Vec;

public class PGroundPiff extends Particle{

	public PGroundPiff(float x,float y,float z,float modifier){
		modifier/=3f;
		modifier+=0.2f;
		this.x=x;
		this.y=y;
		this.z=z;
		vx=(float) (Math.random()-0.5f)*modifier;
		vy=(float) (Math.random()-0.5f)*modifier;
		vz=0.01f;//(float) (Math.random()-0.5f)*0.5f;
		az=0;
		buffer=0.9f;
		//az=-0.008f;
		//az=-0.01f;
		bounce=0f;
		size1=0.1f;
		size2=0.2f;
		alpha1=0.7f;
		alpha2=0f;
		
		alphaRange=30;
		sizeRange=30;
		//colorRange=80;
		lifetime=30;
		color=new Vec(0.8,0.5,0.3);
		//color2=new Vec(0.1f,0.03f,0.08f);
	}
}
