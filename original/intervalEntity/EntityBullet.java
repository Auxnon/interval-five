package intervalEntity;

import interval.Main;
import interval.Timeline;
import interval.Vec;
import intervalParticle.PBulletPiff;

public class EntityBullet extends Entity {

	float range;
	float rangeD;
	//int t=0;
	public boolean active(){
		return false;
	}
	public EntityBullet(float x,float y,float z,float rr){
		this.setVec(new Vec(x,y,z));
		rangeD=2f;
		range=30;
		//t=2;
		r=rr;
	}
	@Override
	public void time(Timeline t){
		//GL11.glPushMatrix();
		//GL11.glTranslatef(x, y, z);
		//GL11.glColor4f(Color.x, Color.y, Color.z, Color.dx);
		//getModel().render(r);
		//GL11.glPopMatrix();
		//GL11.glColor4f(1,1,1,1);
		//Main.world.addP(new PBulletPiff(x,y,z));
		double R=Math.PI*r/180.0;
		double ddx=-Math.sin(R);
		double ddy=Math.cos(R);
		
		for( double u=1;u<range;u+=rangeD)
		Main.world.addP(new PBulletPiff((float)(x+ddx*u),(float)(y+ddy*u),z));
		
		
		Destroy();
	}
	public void logic(){
	//	t--;
		//if(t<=0)
		//Destroy();
	}
}
