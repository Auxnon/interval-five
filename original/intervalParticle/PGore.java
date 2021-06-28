package intervalParticle;

import interval.Main;
import interval.Vec;

public class PGore extends Particle {
	

	public PGore(float x,float y,float z){

		this.x=x;
		this.y=y;
		this.z=z;
		//vx=(float) 2f;
		//vy=(float) (Math.random()-0.5f)*0.9f;
		//vz=(float) (Math.random()-0.5f)*0.9f;
		buffer=0.8f;
		//vz=0.01f;
		//az=-0.01f;
		color1=new Vec(1f,1f,1f);
		color2=color1;
		//colorRange=1;
		bounce=0f;
		size1=0.1f;
		size2=0.1f;
		//sizeRange=100;

			lifetime=10;


		
	}
	public void subTime(){

			for(int i=0;i<10;i++)
			Main.world.addP(new PBlood(x,y,z));
			

	}
}
