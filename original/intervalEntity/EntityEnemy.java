package intervalEntity;

import interval.Main;
import interval.Message;
import interval.ModelManager;
import interval.Timeline;
import interval.VGO;

public class EntityEnemy extends EntityPlanned {

	float speed=0.02f;
	int ano=10;
	int ano2=ano*2;

	boolean dynamic=false;
	public EntityEnemy(){
		dump2();
	}
	int ani=0;
	public VGO getModel(){

		if(isMoving()){
			if(ani>ano){
				return ModelManager.getModel("thingw2");
			}else{
				return ModelManager.getModel("thingw1");
			}
		}
		if(maul){
			if(ani>ano){

				return ModelManager.getModel("thinga1");
			}else{
				return ModelManager.getModel("thing");
			}
			//return ModelManager.getModel("thinga1");
		}

		return ModelManager.getModel("thing");
	}
	public void dump2(){
		schedule=new ScheduleAdvanced();
		schedule.activate();
	}
	public void dump(){
		if(schedule.slice(schedule.getIndex(),Main.world.timeline.getEnt())){
			//schedule.cutDamage(Main.world.timeline.getTick());
			Message.addPass("alternate timeline!",2);
		}
	}
	public void setTime(long tick,int ento){
		super.setTime(tick, ento);
		target=0;
		dynamic=false;
	}
	@Override
	public void time(Timeline t){
		ani++;
		if(ani>=ano2)
			ani=0;

		if(!schedule.run(this)){
			dynamic();
		}else{
			dynamic=false;
		}
	}
	public void change(){
		if(!dynamic){
			dump();
			dynamic=true;
		}
	}
	public void dynamic(){
		if(target!=0){
			//Message.m(this, "getem");
			seek(Main.world.findE(target));
			if(fr<1){
				if(ani==0){
					//schedule.addTask("harm",target);
					//schedule.addDamage(target, getPIN(), 10);
					((EntityComplex)Main.world.findE(target)).attacked(this, 10);
				}else{
					schedule.addTask("seek",target);
				}
			}else
			schedule.addTask("seek",target);
		}else{
			Player p=Main.world.findPlayer(x, y);
			maul=false;
			r+=(float) (Math.sin(Main.world.timeline.getTick()/30f));
			compute(p);
			if(fr<3){
				target=p.getPIN();
				change();
				//Message.m(this, "yes");
			}else{
				//Message.m(this, "no");
			}
		}

	}
	float fr;
	float fx;
	float fy;
	int target=0;
	boolean maul=false;

	public void seek(Entity e){
		compute(e);
		if(fr>0.98){
			dx+=fx*speed/fr;
			dy+=fy*speed/fr;
		}else{
			maul=true;
		}
		r=(float)(-90+180f* Math.atan2(fy,fx)/Math.PI);
	}

	public void compute(Entity e){
		fx=e.x-x;
		fy=e.y-y;
		fr=(float) Math.sqrt(fx*fx+fy*fy);
	}
	public void command(String s,int c){
		boolean b=s.contains("harm");
		if(s.contains("seek") ||b){
			//Integer h=Integer.parseInt(s.substring(4));
			//if(h!=null){
			Entity e=Main.world.findE(c);
			if(e!=null){
				//target=e;
				//Message.m(this, "fuck u");
				if(b)
					((EntityComplex)e).attacked(this, 10);
				seek(e);
				return;
			}
			//}
		}
	}

}
