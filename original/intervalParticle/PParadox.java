package intervalParticle;

import interval.Vec;

public class PParadox extends Particle{

	public PParadox(float x,float y,float z){
		this.x=x;
		this.y=y;
		this.z=z;
		float rx=(float) Math.random();
		float ry=(float) Math.random();
		float rz=(float) Math.random();


		vx= (rx-0.5f)*0.3f;
		vy=(ry-0.5f)*0.3f;
		vz= (rz-0.5f);
		alpha1=1f;
		alpha2=0.3f;

		buffer=0.9f;
		ax=-vx/20f;
		ay=-vy/20f;
		az=0.06f;
		color1=new Vec(0.4f,0.4,0.4);
		color2=new Vec(0f,1f,0f);
		size1=0.4f;

		alphaRange=30;
		colorRange=30;
		bounce=1f;
		size2=0.1f;
		sizeRange=30;
		lifetime=(int) 30;
	}
}
