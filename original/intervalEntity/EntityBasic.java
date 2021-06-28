package intervalEntity;

import interval.Main;
import interval.VecP;

/**
 * @author Aninon
 * @usage all entities that depend on time, at least in position and physics
 */
public class EntityBasic extends Entity {

	public ScheduleSimple schedule=new ScheduleSimple();

	public void annual(){
		schedule.annual(getVecP());
	}
	public VecP getVecP(){
		return new VecP(x,y,z,vx,vy,vz,r,(short)0,(short) 0,0,1f,dx,dy,0);
	}

	public void setTime(long tick,int ento){
		VecP v=schedule.setYear(tick,ento);
		if(v!=null){
			renewP(v);
		}else{
			refresh();
		}
	}
	public void presetTime(long tick,int ento){
		schedule.lastCheck(getVecP(), ento+1);
	}

	public void setVecP(VecP v){
		x=v.x;
		y=v.y;
		z=v.z;
		vx=v.vx;
		vy=v.vy;
		vz=v.vz;
		r=v.r;
		dx=v.tx;
		dy=v.ty;
	}

	public void renewP(VecP v){
		setVecP(v);
		refresh();
	}
	@Override
	public void possess(TimeBeacon timeb){
		super.possess(timeb);
		schedule.set(timeb.getTimeline());
	}
	@Override
	public void unpossess(){
		super.unpossess();
		schedule.set(Main.world.timeline);
	}
}
