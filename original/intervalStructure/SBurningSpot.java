package intervalStructure;

import interval.Main;
import intervalParticle.PSmoke;


public class SBurningSpot extends Structure{
	public SBurningSpot(){
		preParticle=true;
		setDependant(true);
	}
	public SBurningSpot(int x,int y){
		this();
		place(x,y);
	}

	int delay;
	public void time(){
		delay++;
		if(delay>3){
			//System.out.println("dddgdg");
			delay=0;
			Main.world.addP(new PSmoke(x,y,z));
		}

		age=Main.world.timeline.getTerra()-startYear;
		setPresent(((Main.world.timeline.getTerra()>startYear) || (Main.world.timeline.getTerra()==startYear&&Main.world.timeline.getTick()>startTick))&& age<=lifetime);
	}

}
