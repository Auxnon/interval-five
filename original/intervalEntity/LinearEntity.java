package intervalEntity;

import interval.Main;
import interval.Stats;
import interval.Timeline;
import interval.Vec;

import java.util.Random;

public class LinearEntity extends EntityComplex {

	Random rand;
	int age;
	public ScheduleAdvanced schedule;

	public void annual(){
		schedule.annual(getVecP());
		//int in=schedule.task.size()-1;
		//schedule.annualCheck[Main.world.timeline.getEnt()-1]=in;// taskIndex//
	}

	public void setTime(long tick,int ento){

		Vec v=schedule.setYear(tick,ento);
		//System.out.println("time in: "+i+" start: "+schedule.startTick);
		/*if(schedule.getStartTick()>=i){
			setDead(true);
		}else if(i>schedule.getEndTick()){
			renew(schedule.spot.get(schedule.spot.size()-1));
			
			predict(schedule.getEndTick(),i);
		}else{
			if(v!=null){
				renew(v);
			}else{
				renew();
			}
		}*/

	}
	public LinearEntity(int L,int age){
		this.age=age;
		rand=new Random();
		//ID=L;
		//rand.setSeed(ID);
		schedule=new ScheduleAdvanced();
		//schedule.setEndTick(schedule.getStartTick());
	}
	/*public LinearEntity(){
		this(Main.world.allocateID(),0);
	}*/
	public void predict(long now, long next){
		Stats s=Main.level.getStats();
		int e1=Timeline.computeEnt(s, now);
		int e2=Timeline.computeEnt(s, next);
		//int eSpan=e2-e1;
		
		for(int i=e1;i<e2;i++){
			activity(i,s.getREnt());
		}
		//resultant();
	}

	public int choice(int m){
		return choice(Main.world.timeline.getEnt(),m);
	}
	public int choice(int L,int m){
		//rand.setSeed(ID+L);
		return rand.nextInt(m);
	}
	public void activity(int c,double divi){
		
	}
}
