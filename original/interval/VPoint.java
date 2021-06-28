package interval;

public class VPoint {
	public float x;
	public float y;
	float px;
	float py;
	
	public VPoint(float x,float y){
		setPos(x,y);
	}
	public void setPos(float x,float y){
		this.x=px=x;
		this.y=py=y;
	}
	public void refresh(){
		float tempx=x;
		float tempy=y;
		x+=x-px;
		y+=y-py;
		px=tempx;
		py=tempy;
		
	}
	
}
