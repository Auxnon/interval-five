package intervalParticle;

import interval.Main;
import interval.Vec;

public class PParadoxCore extends Particle {
	
	boolean b;
	public PParadoxCore(boolean h,float x,float y,float z){
		b=h;
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
		color=color2=color1;
		//colorRange=1;
		bounce=0f;
		size1=0.1f;
		size2=0.1f;

			lifetime=40;
			
		
	}
	public void subTime(){
		
		/*if(b){
			for(int i=0;i<10;i++)
			Main.world.addP(new PTeley(b,x,y,z));
			
		}*/
		Main.world.addP(new PParadox(x,y,z));
		Main.world.addP(new PParadox(x,y,z));
	}
}
