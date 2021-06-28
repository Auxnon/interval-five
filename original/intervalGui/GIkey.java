package intervalGui;

import interval.Message;
import interval.Render;

import org.lwjgl.input.Keyboard;

public class GIkey extends GI {

	int key;
	boolean breaker=false;
	public GIkey(String s,int key,float x,float y){
		super(s,x,y);
		this.key=key;
	}
	
	@Override
	public void run(){
		if(Keyboard.isKeyDown(key)){
			if(!breaker){
				Render.getGui().com(release(),this);
				breaker=true;
			}else
				over();
		}else
			breaker=false;
	}
	@Override
	public void out(){
		if(!breaker)
		selection=colorNormal;
	}
}
