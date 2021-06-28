package intervalParticle;

import interval.Vec;

public class PSmoke extends Particle{

	public PSmoke(float x,float y,float z){
		this.x=x;
		this.y=y;
		this.z=z;
		vx=(float) (Math.random()-0.5f)*0.1f;
		vy=(float) (Math.random()-0.5f)*0.1f;
		buffer=0.9f;
		az=0.008f;
		//az=-0.01f;
		bounce=0f;
		size1=0.1f;
		size2=0.8f;
		alpha1=1;
		alpha2=0f;
		alphaRange=180;
		sizeRange=180;
		colorRange=80;
		lifetime=180;
		color1=new Vec(1,0.6f,0.1f);
		color2=new Vec(0.1f,0.03f,0.08f);
	}
}
