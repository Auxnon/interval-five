package intervalEntity;

import interval.ModelManager;
import interval.Main;
import interval.Texture;
import interval.Timeline;
import interval.VGO;
import interval.VecP;
import org.lwjgl.opengl.GL11;

public class Vehicle extends Container{

	float speed=0.5f;
	float rspeed=2f;
	float destr;
	float destx;
	float desty;
	

	@Override
	public void time(Timeline t){
			int m=ents.size();
			if(m>0){
				///destx=x;
				//desty=y;
				for(int i=0;i<m;i++){
					Entity e=ents.get(i);
					destx+=(e.dx-e.x)*3;
					desty+=(e.dy-e.y)*3;
					e.place(x, y, z);
				}
				float siz=Main.level.getStats().getSize()-0.01f;
				if(destx>siz){
					destx=siz;
				}
				if(desty>siz){
					desty=siz;
				}
				if(destx<0){
					destx=0;
				}
				if(desty<0){
					desty=0;
				}

				float rx=destx-x;
				float ry=desty-y;

				if(rx>1){
					rx=1;
				}
				if(ry>1){
					ry=1;
				}
				if(rx<-1){
					rx=-1;
				}
				if(ry<-1){
					ry=-1;
				}


				rot(rx,ry);
				float rrr= rr+360;

				while(rrr>=360){
					rrr-=360;
				}
				r=rrr;

				double ro=Math.toRadians(r);
				double sped=Math.sqrt((rx*rx) + (ry*ry));


				sped-=0.5f;
				if(sped>speed){
					sped=speed;
				}else if(sped<0){
					sped=0;
				}

				dx+=Math.cos(ro)*sped;
				dy+=Math.sin(ro)*sped;
		}
	}
	public VGO getModel(){
		return ModelManager.getModel("car");
	}

	public boolean playerDriving(){
		return ents.contains(Main.player);
	}



	float o=0;
	float rr=0;

	public void rot(float xi,float yi){


		float m=(float) -((Math.toDegrees((Math.atan2(-yi,-xi))))-180);
		float n=-(rr);
		float mn=m-n;

		if(mn>180){
			mn-=180;
			mn*=-1;

		}
		if(mn<-180){

			mn+=180;
			mn*=-1;

		} 

		if(mn>0){
			if(mn<180){
				o+=rspeed;
				//System.out.println("poo");
			}
		}
		if(mn<0){
			if(mn>-180){
				o-=rspeed;

			}
		}
		float mna=Math.abs(mn);
		if(mna<3 || mna >177){
			o=m;
			//notTurning=true;
		} else{
			//notTurning=false;
		}

		rr=-o;
	}


	public void render(){ //renders entity, independent of time loop, so still visible 

		if(active()){

		if(playerDriving()){
			GL11.glPushMatrix();
			int xx=(int) destx;
			int yy=(int) desty;
			Texture.bind(0);
			GL11.glTranslatef(xx, yy, 0.5f+Main.world.land.getHigh(xx, yy));
			GL11.glColor4f(0, 1, 0, 1);
			GL11.glBegin(GL11.GL_QUADS);
			float szz=0.5f;
			GL11.glVertex2f(-szz,-szz);
			GL11.glVertex2f(szz,-szz);
			GL11.glVertex2f(szz,szz);
			GL11.glVertex2f(-szz,szz);
			GL11.glEnd();

			GL11.glPopMatrix();
		}
		GL11.glPushMatrix();

		GL11.glTranslatef(x, y, z);
		GL11.glColor4f(Color.x, Color.y, Color.z, Color.vx);

		VGO v=getModel();
		v.orient(r-90);
		v.render();
		GL11.glPopMatrix();
		}
	}
	
	
	public boolean active(){
		return  super.active();
	}
	
	@Override
	public void setTime(long t,int ento){
		VecP v=schedule.setYear(t,ento);
		if(v!=null){
			renewP(v);
			rr=o=r;
		}else{
			refresh();
		}
	}
}
