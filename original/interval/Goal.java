package interval;

public class Goal {

	String name;
	boolean success;
	float completion;
	int length;
	int startTick;
	Coord startPos;
	Coord endPos;
	Coord destPos;
	float speed;
	float distance;
	
	public static Goal destination(int L,float speed,float x,float y,float dx,float dy){
		Goal g=new Goal();
		g.startTick=L;
		g.startPos=new Coord(x,y);
		g.destPos=new Coord(dx,dy);
		g.speed=speed;
		g.estimate();
		return g;
	}
	
	public Coord getPos(int c){
		return new Coord(0,0);
	}
	
	public void estimate(){
		//int sx=(int) Math.floor(startPos.x);
		//int sy=(int) Math.floor(startPos.y);
		//int ex=(int) Math.floor(destPos.x);
		//int ey=(int) Math.floor(destPos.y);
		
		float fx=startPos.x -destPos.x;
		float fy=startPos.y -destPos.y;
		distance=(float) Math.sqrt(fx*fx + fy*fy);
		length=(int) Math.ceil(distance/speed);
		
		///int wide=ex-sx;
		//int tall=ey-sy;
		//int ix,iy;
		
		//Main.world.land.getHeight(0, startPos.x,startPos.y);
		//Main.world.getLot(x, y)
	}
	
}
