package intervalEntity;

import interval.ModelManager;
import interval.Main;
import interval.Timeline;
import interval.VGO;


public class Flower extends LinearEntity{

	
	public VGO getModel(){
		return ModelManager.getModel("flower");
	}
	
	public Flower(float x,float y){
		super(0, 2);
	//	schedule.setEndTick(schedule.getStartTick());
		annual();
		place(x,y);
	}
	float sp=0.1f;

	@Override
	public void time(Timeline t){
		/*long t=Main.world.timeline.getTick();
		if(schedule.getEndTick()<t){
			long interval=t-schedule.getEndTick();
			if((interval)>1){
				predict(t,interval);
			}
			schedule.setEndTick(t);
		}
		function(choice(4),1f);*/
	}
	
	public void function(int c,float multi){
		switch(c){
		case 1: dx+=sp*multi;break;
		case 2:dx-=sp*multi;break;
		case 3: dy+=sp*multi;break;
		default: dy-=sp*multi;break;
		}
	}
	
}
