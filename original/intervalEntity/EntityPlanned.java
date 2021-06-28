package intervalEntity;

import interval.Main;
import interval.VecP;

/**
 * @author Aninon
 * @usage all entities that depend on time in both location, and also in actions it performs. More advanced than EntityBasic
 */
public class EntityPlanned extends EntityComplex {
	
	public ScheduleAdvanced schedule=new ScheduleAdvanced();
	long startExistance;
	long endExistance;

	//public EntityPlanned(){
		//animate=true;
	//}

	public void annual(){
		schedule.annual(getVecP());
	}
	public int getIndex(){
		return schedule.task.size()-1;
	}
	
	public void command(String s, int c){
		
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
