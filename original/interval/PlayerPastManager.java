package interval;

import intervalEntity.PlayerPast;
import intervalEntity.Entity;
import intervalEntity.ScheduleAdvanced;
import java.util.ArrayList;

public class PlayerPastManager {

	
	public int version=0;
	
	public void reset(){
		version=0;
	}
	public void createVersion(long st,long en,ScheduleAdvanced s){
		version++;
		s.printTasks();
		Main.world.addPastPlayer(new PlayerPast(Main.player.getPIN(),st,en,s,version));
	}
	
	public void removeVersion(int selection){
		World w=Main.world;
		int ss=w.sizeE();
		for(int i=0;i<ss;i++){
			Entity e=w.getE(i);
			if(e instanceof PlayerPast){
				PlayerPast p =( PlayerPast) e;
				if(p.getVersion()>=selection){
					if(p.getVersion()==selection){
						Main.player.setVecP((VecP)p.schedule.getOrigin());
						Main.player.forceTravel(p.schedule.sEnt);
					}
					w.removeE(i);
					ss--;
				}
			}
		}
		
		for(int j=0;j<Main.level.getStats().getCapTerra();j++){
			ArrayList<Action> a=Main.world.land.topography.actions.get(j);
			if(a!=null){
				int f=a.size();
				for(int k=0;k<f;k++){
					if(a.get(k).version==selection){
						a.remove(k);
						f--;
					}
				}
				if(a.size()==0){
					Main.world.land.topography.actions.remove(j);
				}else{
					Main.world.land.topography.actions.put(j, a);
				}
				
			}
		}
	}
}
