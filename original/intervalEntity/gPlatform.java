package intervalEntity;

import interval.Main;

public class gPlatform extends Gadget {

	float Top=0;
	float Bottom=0;
	float rate=0.01f;
	public gPlatform(float top,float bottom){
		Top=top;
		Bottom=bottom;
		this.sideCollision=true;
	}
	public gPlatform(){
		this(1f,0.1f);
	}
	public void contactTop(Entity e){
	}
	public void gadgetOn(){
		//super.gadgetOn();
		if(height<Top){
			height+=rate;
		}
	}
	public void gadgetOff(){
		//super.gadgetOff();
		if(height>Bottom){
			height-=rate;
		}
	}

	public String[] infer(String param){
		String st[]=super.infer(param);
		Top=Float.parseFloat(st[inferIndex]);
		Bottom=Float.parseFloat(st[inferIndex+1]);
		inferIndex+=2;
		return st;
	}
	public String toString(){
		return super.toString()+","+Top+","+Bottom;
	}
	public void extra(String s){
		Entity e=Main.world.findE(s);
		if(e!=null)
			this.setInput(e);
	}
}
