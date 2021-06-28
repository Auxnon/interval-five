package intervalParticle;

import interval.Main;
import interval.Render;
import interval.Vec;

import org.lwjgl.opengl.GL11;

public class Particle {

	private boolean remain=false;
	float x,y,z,dx,dy,dz,vx,vy,vz,ax,ay,az;
	float size1;
	float size2;
	float sizew1;
	float sizew2;
	float alpha1;
	float alpha2;
	Vec color1=new Vec(1,0,0);
	Vec color2=new Vec(1,0,0);
	Vec color=new Vec(1f,1f,1f);
	float alphaTime;
	float colorTime;
	float sizeTime;
	float sizeRange;
	float alphaRange;
	float colorRange;
	float bounce=0.9f;
	float buffer=0.99f;
	boolean scale;
	boolean unevenScale;
	boolean colorChange;
	boolean fade;
	float size=0.1f;
	float alpha=1f;
	
	int lifetime=100;
	public float age;
	
	public boolean time(double rate){
		float rate2=(float) rate;
		dx=x;
		dy=y;
		dz=z;
		vx+=ax*rate;
		vy+=ay*rate;
		vz+=az*rate;
		float b=(buffer*rate2);
		vx*=b;
		vy*=b;
		vz*=b;
		dx+=vx*rate;
		dy+=vy*rate;
		dz+=vz*rate;
		float h;
		int s=Main.level.getStats().getSize();
		int gx=(int)(Math.floor(dx));
		int gy=(int)(Math.floor(dy));
		if(gx>0 && gx<s && gy>0 && gy<s){
			h=Main.world.land.getHigh(gx, gy);
		}else{
			h=-9000;
		}
		x=dx;
		y=dy;
		if(dz<h){
			dz=h;
			z=h;
			vz*=-bounce*rate;
		}else{
			z=dz;
		}
		if(sizeTime<sizeRange){
			sizeTime+=rate;
			size=size1+((size2-size1)*sizeTime/sizeRange);
		}
		if(alphaTime<alphaRange){
			alphaTime+=rate;
			alpha=alpha1+((alpha2-alpha1)*alphaTime/alphaRange);
		}
		if(colorTime<colorRange){
			colorTime+=rate;
			float ff=colorTime/colorRange;
			float f2=1f-ff;
			color=new Vec((color1.x*f2)+ff*color2.x,(color1.y*f2)+ff*color2.y,(color1.z*f2)+ff*color2.z);
		}
		
		age+=rate;
		
		subTime();
		return (age<lifetime);
	}
	public void subTime(){
		
	}
	public void render(){
		GL11.glPushMatrix();
		
		GL11.glColor4f(color.x, color.y, color.z, alpha);
		GL11.glTranslatef(x, y, z+0.5f);
		
		GL11.glRotatef(-Render.getYaw(), 0, 0, 1);
		GL11.glRotatef(-Render.getPitch(), 1, 0, 0);
		GL11.glBegin(GL11.GL_QUADS);
		
		GL11.glNormal3f(1, 0, 0);
		GL11.glVertex3f(-size,-size,0);
		GL11.glVertex3f(size,-size,0);
		GL11.glVertex3f(size,size,0);
		GL11.glVertex3f(-size,size,0);
		GL11.glEnd();
		
		GL11.glPopMatrix();


	}
	public boolean isRemain() {
		return remain;
	}
	public void setRemain(boolean remain) {
		this.remain = remain;
	}
	
}
