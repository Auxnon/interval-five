package intervalParticle;

import interval.Vec;

public class PBulletPiff extends Particle{

	public PBulletPiff(float x,float y,float z){
		this.x=x;
		this.y=y;
		this.z=z;
		vx=(float) (Math.random()-0.5f)*0.3f;
		vy=(float) (Math.random()-0.5f)*0.3f;
		buffer=0.9f;
		az=0.002f;
		//az=-0.01f;
		bounce=0f;
		size1=0.05f;
		size2=0.6f;//0.8f;
		alpha1=1;
		alpha2=0f;
		lifetime=60;
		alphaRange=lifetime;
		sizeRange=lifetime;
		colorRange=40;
		
		color1=new Vec(0.35,0.1,0.06);
		color2=new Vec(0.6,0.45,0.35);
	}
}
