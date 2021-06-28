package intervalEntity;

import interval.Main;
import interval.Task;
import interval.VecP;
import java.util.ArrayList;
import java.util.HashMap;

public class ScheduleAdvanced extends ScheduleSimple {
	//will change with player or AI but not past versions
	public ArrayList<Task> task;
	//ArrayList<Damage> damage; //??
	
	
	
	//Offsets
	public class Offset{
		float x,y,z;
		int damage;
		public Offset(float x,float y,float z,int damage){
			this.x=x;this.y=y;this.z=z;this.damage=damage;
		}
	}
	
	ArrayList<Offset> offsets; //this schedules offsets!
	Offset finalOffset; //resulting offset
	HashMap<ScheduleAdvanced,Offset> offsetChildren; //the offsets this schedule's parent has caused! confusing isnt it???
	
	public void offsetTarget(ScheduleAdvanced target,float x,float y,float z,int damage){
		//target.offsets
	}
	
	public void offsetThis(Offset o){
		
	}
	

	int index=0;
	int tickShift=0;
	boolean completion=false;
	public void renew(){
		tickShift=0;
		completion=false;
		loop=0;
	}
	public int getIndex(){
		return index;
	}
	public ScheduleAdvanced(){
		task=new ArrayList<Task>();
		annual=new HashMap<Integer,VecP>();
		//damage=new ArrayList<Damage>();
		index=0;
	}

	/*public void addDamage(int to,int from,int amount){
		Damage d=new Damage(to,from,amount,T.getTick());
		damage.add(d);
		Main.player.addDamage(d);
	}
	public void addPureDamage(int to,int from,int amount){
		damage.add(new Damage(to,from,amount,T.getPureTick()));
	}*/
	public void addTask(String action){
		addTask(action,0);
	}
	public Task addPureTask(String action,int c){
		return addTask(T.getEnt(),T.getPureTick(),action,c);
	}
	public Task addTask(String action,int c){
		return addTask(T.getEnt(),T.getTick(),action,c);
	}
	
	public VecP setYear(long tick,int ento){
		VecP v=annual.get(ento);
		renew();
		index=0;
		if(v!=null){
			if(v.index!=-1 && v.index<task.size()){
				Task tt=task.get(v.index);
				tickShift=(int) (tick-tt.tick);
				index=v.index;
			}
			return v;
		}
		return getOrigin();
	}
	public void addTask(int year,long tick,String action){
		addTask(year,tick,action,0);
	}
	public Task addTask(int year,long tick,String action,int c){
		if(!T.isActive() || !Main.level.getStats().canTimeTravel()){
			return null;
		}
		Task tt=null;
		int top=task.size()-1;
		if(top!=-1){
			Task t=task.get(top);
			if(t.action.contentEquals(action)&&t.c==c && t.tick+t.length+1>=tick){
				t.length++;
				task.set(top, t);
				tt=t;
			}else{
				tt=new Task(year,tick,0,action,c);
				task.add(tt);
			}
		}else{
			tt=new Task(year,tick,0,action,c);
			task.add(tt);
		}
		return tt;
	}
	public boolean slice(int i,int e){
		int c=0;
		while(i<task.size()){
			task.remove(i);
			c++;
		}
		for(int u=e;u<eEnt;u++){
			annual.remove(u);
		}
		eEnt=e;
		return c>0;
	}
	/*public boolean cutDamage(long t){
		for(int u=0;u<damage.size();u++){
			if(damage.get(u).occurence>=t){
				Main.player.cleanDamage(damage.remove(u));
				u--;
			}
		}
		return false;
	}*/
	public boolean run(EntityPlanned e){
		int sizeT=task.size();
		int enta=T.getEnt();
		if(index>=sizeT){
			if(!completion){
				completion=true;
				//Message.addPass("looped "+loop);
			}
			return false;
		}
		Task t=task.get(index);
		return processTask(e,t,enta)!=2;
	}
	public int processTask(EntityPlanned e,Task t,int ento){
		long tick=T.getTick();
		boolean notEnded=( (tick<=(t.tick+t.length)));  //(year<=t.year) &&
		boolean started= (  (tick>=t.tick)); //(year>=t.year) && 
		if(started && notEnded){
			e.command(t.action,t.c);//return that tasks action as it's on time!
			tickShift++;
			if(tickShift>t.length){
				tickShift=0;
				index++;
				return 1;
			}
			return 0;
		}else if(!notEnded){
			index++;
			tickShift=0;
			run(e);
			loop++;
		}
		return 2; //otherwise we're either at a task before this, by a tick or a whole year, or we're after it
	}
	int loop=0;
	@SuppressWarnings("unchecked")
	public ScheduleAdvanced clone(){
		ScheduleAdvanced s=new ScheduleAdvanced();
		s.sEnt=sEnt;
		s.annual=(HashMap<Integer, VecP>) annual.clone();
		s.task=(ArrayList<Task>) task.clone();
		return s;
	}
	public void printTasks(){
		System.out.println();
		for(int i=0;i<task.size();i++){
			Task t= task.get(i);
			System.out.print(t.action+" "+t.tick+","+t.length+": ");
		}
		System.out.println();
	}
	
}
