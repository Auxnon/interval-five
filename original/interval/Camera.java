package interval;

import java.util.ArrayList;

public class Camera {

	int time=0;
	int capTime=0;
	ArrayList<VecF> chain;
	VecF origin;
	VecF current;
	VecF destination;
	boolean finished=true;
	private boolean locked=false;
	public Camera(){
		origin=new VecF();
		current=new VecF();
		destination=new VecF();
		chain=new ArrayList<VecF>();
	}
	public void go(int cap,float x,float y,float z){
		go(cap,x,y,z,current.vx,current.vy,current.vz);
	}
	public void que(int cap,float x,float y,float z){
		que(cap,x,y,z,current.vx,current.vy,current.vz);
	}
	public void upkeep(int cap,float x,float y,float z,float r,float r2,float r3){
		if(finished &&!locked){
			go(cap,x,y,z,r,r2,r3);
		}
	}
	public void go(int cap,float x,float y,float z,float r,float r2,float r3){
			origin=current.clone();
			destination.set(x, y, z, r, r2,r3);
			capTime=cap;
			this.time=0;
			finished=false;
		
	}
	public void que(int cap,float x,float y,float z,float r,float r2,float r3){
		chain.add(new VecF(x,y,z,r,r2,r3,cap));
	}
	public void set(float x,float y,float z,float r,float r2,float r3){
		current.set(x, y, z, r, r2,r3);
		destination.set(x, y, z, r, r2,r3);
		finished=true;
	}
	public void set(float x,float y,float z){
		current.set(x, y, z, current.vx, current.vy,current.vz);
		finished=true;
	}
	public void run(){
		if(time<capTime){
			time++;
					finished=time>=capTime;
		}
		if(!locked && finished && chain.size()>0){
			VecF vf=chain.remove(0);
			go((int) vf.r,vf.x,vf.y,vf.z,vf.vx,vf.vy,vf.vz);
		}
		float v=(float)time/(float)capTime;
		float x=origin.x+(destination.x -origin.x)*v;
		float y=origin.y+(destination.y -origin.y)*v;
		float z=origin.z+(destination.z -origin.z)*v;
		float vx=origin.vx+(destination.vx -origin.vx)*v;
		float vy=origin.vy+(destination.vy -origin.vy)*v;
		float vz=origin.vz+(destination.vz -origin.vz)*v;
		//double rr=Math.toRadians(vx);
		//double SIGN=Math.cos(rr);
		
		//double dz=(1+Math.sin(rr))*(SIGN/Math.abs(SIGN));
		//double dzz=z+dz*vz;
		//Message.m(this, "z "+(dzz));
		
		//if(dzz<-3){
		//	double fff=-3-dzz;
			//z+=(fff*fff);
		//	vx+=fff*fff;
		//}
		
		current.set(x, y, z, vx, vy, vz);
		
		//Message.m(this, "locked "+locked+" finished "+finished+" time "+time +" cap "+capTime);
	}
	public float x(){
		return current.x;
	}
	public float y(){
		return current.y;
	}
	public float z(){
		return current.z;
	}
	public float yaw(){
		return current.vy;
	}
	public float pitch(){
		return current.vx;
	}
	public float zoom(){
		return current.vz;
	}
	public void lock(){
		locked=true;
	}
	public void unlock(){
		locked=false;
		//finished=true;
		//time=capTime;
	}
	public boolean isLocked(){
		return locked;
	}
}
