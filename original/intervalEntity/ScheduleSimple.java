package intervalEntity;

import interval.Main;
import interval.Timeline;
import interval.VecP;

import java.util.HashMap;

public class ScheduleSimple {
	public int sEnt;
	public int eEnt;
	HashMap<Integer,VecP> annual;
	Timeline T;

	public ScheduleSimple(){
		annual=new HashMap<Integer,VecP>();
		T=Main.world.timeline;
	}

	public void activate(){
		sEnt=T.getEnt();
		eEnt=sEnt;
		renew();
	}

	public void renew(){
		//completion=false;
	}

	public void annual(VecP vecP){
		int e=T.getEnt();
		if(e>eEnt){
			eEnt=e;
		}
		annual.put(e,vecP);
	}
	public void lastCheck(VecP vecP, int ent){
		if(ent>eEnt){
			eEnt=ent;
		}
		if(annual.get(ent)==null){
			annual.put(ent, vecP);
		}
	}

	public VecP setYear(long tick,int ento){
		VecP v=annual.get(ento);
		renew();
		if(v!=null){
			return v;
		}
		return getOrigin();
	}
	public boolean slice(int i,int e){
		int u=0;
		for(u=e;u<eEnt;u++){
			annual.remove(u);
		}
		eEnt=e;
		return u>0;
	}

	public VecP getOrigin(){
		return annual.get(sEnt);
	}
	public void set(Timeline t){
		T=t;
	}
}
