package intervalGui;

import interval.InvVGO;
import interval.Render;

import org.lwjgl.opengl.GL11;

public class GIvox extends GI{

	private String vox;
	private float scale;
	
	InvVGO inv;
	public GIvox(float scale,String v,float xx,float yy){
		super("",xx,yy);
		this.scale=scale;
		vox=v;
		visible=true;
		depth=-0.08f;
		
		
//		colorNormal=new VecF(1,1,1,1);//new Vec(0.2f,0.6f,0.7f,1f);
	//	colorOver=new VecF(0.9f,0.8f,0.6f,1f);
		//colorDown=new VecF(1,1,1,0.5f);
		//selection=colorNormal;
		//action="herp";
		
		make();
	}
	public void scale(float s){
		scale=s;
		inv.scale=s;
	}
	public void addScale(float s){
		scale+=s;
		inv.scale+=s;
	}
	public void make(){
		inv=new InvVGO(vox,
				(((x/64f) -0.5f)*4f),
				convY(y),
				scale,
				1);
	}
	public float convX(float Y){
		return (((Y/64f) -0.5f)*4f)-0.25f;
	}
	public float convY(float Y){
		return convX(Y)-0.25f;
	}
	
	public boolean check(float i,float j){
		if(visible){
			return  super.check(i, j);
		}
		return false;
	}
	public void change(String s){
		inv.model=s;
	}
	
	@Override
	public void render(float dx,float dy){
		if(!visible){
			return;
		}
		//Message.m("butt");
		if(dy!=0){
			inv.y=convY(y+dy);
		}
		if(dx!=0){
			inv.x=convX(x+dx);
		}
		GL11.glColor4f(1,1,1,1);
		
		if(!Render.que.contains(inv)){
			Render.que.add(inv);
		}
	}
	
	public void clean(){
		visible=false;
		Render.que.remove(inv);
	}
	@Override
	public void finalize(){
		clean();
	}

}
