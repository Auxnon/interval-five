package intervalEntity;

import interval.IFont;
import interval.Main;
import interval.Message;
import interval.Render;
import interval.Timeline;
import interval.UserData;
import intervalParticle.PFloater;
import intervalParticle.PParadoxCore;
import intervalParticle.PTeleCore;


public class PlayerPast extends PlayerBase {
	//boolean paradox=false;
	private int version=0;

	//must override, past player will never need a different annual!
	@Override
	public void annual(){

	}
	PFloater label;
	public PlayerPast(int PIN,long st,long en,ScheduleAdvanced s,int ver){
		super();
		setPIN(PIN);
		schedule=s;
		startExistance=st;
		endExistance=en;
		setVersion(ver);
		label = new PFloater("V"+ver,this,2f,IFont.PARCHMENT);
	}
	/*public void paradox(){
		paradox=true;
	}*/

	public void mov(){
		//if(!aniStick){
		//	setAniMode(1);
			//delay=2;
		//}
	}
	public void command(String s,int c){
		command2(s,c);
		
	}
	public void command2(String s,int c){
		float sp=1;
		float ss=1;
		setMoveType(Short.parseShort(s.substring(0,1)));
		int com=Short.parseShort(s.substring(1,2));
		s=s.substring(2,s.length());
		switch(getMoveType()){
		case 1: ss=UserData.getSSideSpeed();sp=UserData.getSSpeed();break;
		case 2: ss=UserData.getSneakSideSpeed();sp=UserData.getSneakSpeed();break;
		default: ss=UserData.getSideSpeed();sp=UserData.getSpeed();break;
		}
		if(s!=""){
			if(s.contains("use")){
				com--;
				use();if(com<1)return;
			} 
			if(s.contains("tool")){
				com--;
				
				
					tool();
				
				if(com<1)return;
			}
			if(s.contains("aim")){
				//delay=30;
				//setMode(4);
				com--;
				int u=s.indexOf("aim:");
				int u2=s.indexOf("<aim>");
				r=Float.parseFloat(s.substring(u+4, u2));
			}
			if(s.contains("punch")){
				com--;
			//	delay=10;
				//setMode(2);
				//action=true;
				attack();if(com<1)return;
			} 
			if(s.contains("jump")){
				com--;
				jump();if(com<1)return;
			} 
			if(s.contains("check")){
				com--;
				s=s.substring(5);
				int ii=s.indexOf(",");
				String s1=s.substring(0, ii);
				String s2=s.substring(ii+1, s.length());
				float xx=Float.parseFloat(s1)-dx;
				float yy=Float.parseFloat(s2)-dy;
				double figure=Math.sqrt(xx*xx+yy*yy);
				boolean okay=figure>UserData.getSpeed()*4;
				Message.m(this,"paradoxical position is "+(Math.round(figure*10000)/10000.0)+" units off;"+((okay)?" NOT":"")+" within tolerance");
				Render.warp(x,y,z);
				if(okay){
					//paradox();
					if(!Main.player.inside)
						Main.world.addP(new PParadoxCore(false,x,y,z));
				}else{
					if(!Main.player.inside)
						Main.world.addP(new PTeleCore(false,x,y,z));
				}
				if(com<1)return;
			} 
			if(s.contains("pos")){
				com--;
				//s=s.substring(3);
				//int ii=s.indexOf(",");
				//String s1=s.substring(0, ii);
				//String s2=s.substring(ii+1, s.length());
				//place(Float.parseFloat(s1),Float.parseFloat(s2));
				if(com<1)return;
			} 
			if(s.contains("make")){
				com--;
				//Main.world.addS(new Tree(gx,gy,z));
				if(com<1)return;
			} 
			if(s.contains("hill")){
				com--;
				//Main.world.land.Hill(gx, gy, Land.dirt, 0);
				if(com<1)return;
			} 
			if(s.contains("done")){
				com--;
				//paradox();
				if(com<1)return;
			} 
			if(s.contains("init")){
				com--;
				renewP(schedule.getOrigin());
				Main.world.addP(new PTeleCore(true,x,y,z));
				if(com<1)return;
			} 
			boolean moveStick=getAniMode()==3;
			if(s.contains("ueft")){mov();
			com--;
			if(!moveStick){
				dx-=ss;
				dy+=ss;}
			r=45;if(com<1)return;
			} 
			if(s.contains("uight")){mov();
			com--;
			if(!moveStick){
				dx+=ss;
				dy+=ss;}
			r=315;if(com<1)return;
			} 
			if(s.contains("up")){mov();
			com--;
			if(!moveStick){dy+=sp;}
			r=0;if(com<1)return;
			} if(s.contains("doweft")){mov();
			com--;if(!moveStick){
				dx-=ss;
				dy-=ss;}
			r=135;if(com<1)return;
			} if(s.contains("dowight")){mov();
			com--;if(!moveStick){
				dx+=ss;
				dy-=ss;}
			r=225;if(com<1)return;
			} if(s.contains("down")){mov();com--;
			if(!moveStick){dy-=sp;}
			r=180;if(com<1)return;
			} if(s.contains("left")){mov();com--;
			if(!moveStick){dx-=sp;}
			r=90;if(com<1)return;
			} if(s.contains("right")){mov();com--;
			if(!moveStick){dx+=sp;}
			r=270;if(com<1)return;
			}



		}
		return;
	}

	int color=0;

	@Override
	public void time(Timeline t){
		schedule.run(this);
		super.time(t);
	}

	@Override
	public void render(){
		if(UserData.isPastParser()){
			label.age=0;
			if(!Main.world.particles.contains(label)){
				Main.world.addP(label);
			}
		}
		super.render();
	}
	
	public boolean active(){
		long t=schedule.T.getTick();
		return t>=startExistance && t<=endExistance;
	}

	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public void jump(){
		super.jump();
		vz=UserData.getJumpSpeed();
	}
}
