package interval;

public class InvVGO {
	public String model;
	public float x;
	public float y;
	public int mode;
	public float scale;
	public boolean plat=false;
	public float r;
	public float r2;
	public int special;
	public float raiseZ;
	
	public InvVGO(String name,float xx,float yy){
		this(name,xx,yy,1,0);
	}
	public InvVGO(String name,float xx,float yy,float scale,int mode){
		model=name;
		x=xx;
		y=yy;
		this.scale=scale;
		this.mode=mode;
	}
	public VGO getModel(){
			return ModelManager.getModel(model);
	}
}
