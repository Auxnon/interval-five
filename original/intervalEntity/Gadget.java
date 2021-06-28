package intervalEntity;

import interval.Main;
import interval.Texture;
import interval.Timeline;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

public class Gadget extends EntityBasic {

	//Gadget input;

	/*public Gadget(){
		animate=false;
	}*/
	Gadget input;

	final static short NORMAL=0, //state = input state
			TOGGLE=1, //new input toggles state, if input ON: ON=!ON
			COUNT=2,//on method occurs only once, every time input changes
			INVERTED_COUNT=3, //off method occurs only once, every time input changes
			INVERTED_NORMAL=4; //state = !input state

	short mode=0;

	boolean ON=false;
	boolean outState=false;
	//boolean inputted=false;
	//boolean newState=false;

	public void activate(){
		annual();
	}


	public Matrix4f getMat(){
		return Main.world.getViewMat();
	}

	public void gadgetOn(){
		Color.setV(0, 1, 0);
		outState=true;
	}
	public void gadgetOff(){
		Color.setV(1, 0, 0);
		outState=false;
		//brandNew=true;
	}
	public void setOutput(Entity e){
		if(e instanceof Gadget){
			((Gadget) e).setInput(this);
		}
	}

	public void setInput(Gadget e){
		input=e;
	}
	public void setInput(Entity e){
		if(e instanceof Gadget){
			setInput((Gadget) e);
		}
	}
	//boolean once=true;
	boolean inputState=false;

	public void gadgetDefine(String name){
		if(name.startsWith("%")){
			int ii=name.indexOf("$");
			mode=Short.parseShort(name.substring(1,ii));
			name=name.substring(ii+1);
			//ON=true;
		}
		properName=name;
	}
	@Override
	public void time(Timeline t){
		boolean newOne=false;
		boolean doit=true;
		if(input!=null){
			newOne=input.outState!=inputState;
			inputState=input.outState;
			if(newOne)
				switch(mode){
				case NORMAL:ON=inputState;break;
				case TOGGLE: if(inputState)ON=!ON;break;
				case COUNT: ON=false;doit=false; gadgetOn();break;
				case INVERTED_NORMAL: ON=!inputState;break;
				case INVERTED_COUNT:ON=true;doit=false; gadgetOff();break;
				default:ON=inputState;
				}

		}

		if(doit)
			if(ON)
				gadgetOn();
			else
				gadgetOff();


	}

	public boolean isON(){
		return ON;
	}

	public void render(){

		float sx=x-getHalfSize();
		float ex=x+getHalfSize();

		float sy=y-getHalfSize();
		float ey=y+getHalfSize();

		float t= z+0.2f;
		float t2=t+height;

		Texture.bind(0);

		GL11.glColor4f(Color.x,Color.y,Color.z,Color.vx);
		GL11.glBegin(GL11.GL_QUADS);

		
		GL11.glNormal3f(0, 0, 1);
		GL11.glVertex3f(sx,sy,t);
		GL11.glVertex3f(ex, sy,t);
		GL11.glVertex3f(ex,ey, t);
		GL11.glVertex3f(sx, ey,t);

		GL11.glVertex3f(sx,sy,t2);
		GL11.glVertex3f(ex, sy,t2);
		GL11.glVertex3f(ex,ey, t2);
		GL11.glVertex3f(sx, ey,t2);
		
		
		GL11.glNormal3f(1, 0,0);
		GL11.glVertex3f(sx,sy,t);
		GL11.glVertex3f(sx, sy,t2);
		GL11.glVertex3f(sx,ey, t2);
		GL11.glVertex3f(sx, ey,t);

		GL11.glNormal3f(0, 1,0);
		GL11.glVertex3f(sx,sy,t2);
		GL11.glVertex3f(ex, sy,t2);
		GL11.glVertex3f(ex,sy, t);
		GL11.glVertex3f(sx, sy,t);

		GL11.glNormal3f(0, -1,0);
		GL11.glVertex3f(sx,ey,t2);
		GL11.glVertex3f(ex, ey,t2);
		GL11.glVertex3f(ex,ey, t);
		GL11.glVertex3f(sx, ey,t);

		GL11.glNormal3f(-1, 0,0);
		GL11.glVertex3f(ex,sy,t);
		GL11.glVertex3f(ex, sy,t2);
		GL11.glVertex3f(ex,ey, t2);
		GL11.glVertex3f(ex, ey,t);


		GL11.glEnd();
	}

	public String[] infer(String param){
		String st[]=super.infer(param);
		ON=st[inferIndex].contains("true");
		inferIndex++;
		return st;
	}
	public String toString(){
		return super.toString()+","+ON;
	}


}
