package intervalParticle;

import interval.Vec;

public class PTeley extends Particle{

	public PTeley(boolean h,float x,float y,float z){
		this.x=x;
		this.y=y;
		this.z=z;
		
		float rx=(float) Math.random();
		float ry=(float) Math.random();
		float rz=(float) Math.random();
		
		
		vx= (rx-0.5f)*1f;
		
		vy=(ry-0.5f)*1f;
		
		vz= (rz-0.5f);
		//az=-vz/50f;
		
		
		//vz=0.01f;
		//az=-0.01f;
		alpha1=1f;
		alpha2=0.3f;
		
		float D=30;
		if(h){
			color1=new Vec(0.9f,0.3f,0.4f);
			color2=new Vec(0f,0f,0.5f);
			buffer=0.8f;
			size1=0.8f;
			D=60;
		}else{
			buffer=0.9f;
			ax=-vx/20f;
			ay=-vy/20f;
			az=-0.01f;
			color1=new Vec(0.2f,0.6f,0.8f);
			color2=new Vec(0.5f,0f,0f);
			size1=0.4f;
		}
		alphaRange=D;
		colorRange=D;
		
		bounce=1f;
		
		size2=0.1f;
		sizeRange=D;
		
		lifetime=(int) D;
	}
}
