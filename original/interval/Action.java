package interval;

public class Action {
	int x=0;
	int y=0;
	int version=0;
	String s="action";
	
	public Action(int xi,int yi,String si,int id){
		x=xi;
		y=yi;
		s=si;
		version =id;
	}

	public void run(int year){
		Main.world.land.Hill(year,x, y, 0, 0);
	}
}
