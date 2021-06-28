package interval;

public class VStick {

	VPoint a;
	VPoint b;
	float hypo;
	
	public VStick(VPoint a1,VPoint b1){
		a=a1;
		b=b1;
		float dx=a.x-b.x;
		float dy=a.y-b.y;
		
		hypo=(float) Math.sqrt(dx*dx + dy*dy);
	}
	
	public void contract(){
		float dx=b.x-a.x;
		float dy=b.y-a.y;
		float h=(float) Math.sqrt(dx*dx + dy*dy);
		float diff= hypo-h;
		float N=0.5f*diff/h;
		float offx=N*dx;
		float offy=N*dy;
		
		a.x-=offx;
		a.y-=offy;
		b.x+=offx;
		b.y+=offy;
	}
}
