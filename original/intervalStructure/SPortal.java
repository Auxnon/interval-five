package intervalStructure;

import interval.Main;
import intervalParticle.PBlood;

public class SPortal extends Structure {

	public SPortal(int x,int y){
		setDependant(true);
		place(x,y);
	}
	
	public void time(){
		for(int i=0;i<5;i++)
		//Main.world.addP(new PTeley(false,x,y,z));
			Main.world.addP(new PBlood(x,y,z));
	}
}
