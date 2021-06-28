package intervalParticle;

import interval.Vec;

public class PMuzzleFlash extends Particle{

	public PMuzzleFlash(float x,float y,float z,float dx,float dy){
		this.x=x;
		this.y=y;
		this.z=z;
		vx=(float) (Math.random()-0.5f)*0.2f +dx;
		vy=(float) (Math.random()-0.5f)*0.2f+dy;
		buffer=0.9f;
		az=0.002f;
		//az=-0.01f;
		bounce=0f;
		size1=0.05f;
		size2=0.4f;//0.8f;
		alpha1=1;
		alpha2=0f;
		lifetime=10;
		alphaRange=lifetime;
		sizeRange=lifetime;
		colorRange=6;
		
		color1=new Vec(0.8,0,0);
		color2=new Vec(1,1,1);
	}
}
