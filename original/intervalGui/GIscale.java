package intervalGui;

import interval.Texture;
import org.lwjgl.opengl.GL11;

public class GIscale extends GIbutton {
	float subRange;
	float sliderX;
	float sliderSize;
	boolean coloned=false;
	int index;

	public GIscale(String action,String sub,float x,float y,float subX,float size,int scale){
		this(action,sub,x,y,0,subX,size,scale);
	}
	public GIscale(String action,String sub,float x,float y,float size,int scale){
		this(action,sub,x,y,0,0,size,scale);
	}
	public GIscale(String action,String sub,float x,float y,int t,float subX,float size,int scale){
		super(action,sub,t,x,y);
		this.subRange=scale;
		depth=-0.02f;
		sliderX=x;
		subSx=size;
		this.sliderSize=subSx/scale;
		
		if(action.contains(":")){
			name=action.split(":")[0];
			coloned=true;
/*			int scl=(""+scale).length();
			String blank="";
			while(scl>0){
				scl--;blank+=" ";
			}
			
			redo(action+blank);*/
		}
		setSlider(subX);
		redoTag();
	}
	public void setSlider(float ind ){
		sliderX=x+sx+(sliderSize*ind);
		index=(int) ind;
	}
	public int getIndex(){
		return index;
	}
	public String down(){
		sliderX=mx;
		float f1=x+sx;
		float f=f1+(subSx-sliderSize);
		if(sliderX>f)
			sliderX=f;

		if(sliderX<f1)
			sliderX=f1;

		index=(int) (subRange* (sliderX-(x +sx))/subSx);


		redoTag();

		return "";
	}
	private void redoTag(){
		if(coloned){
			String test=name+": "+index;
			if(!action.equals(test)){
				redoHard(test);
			}
		}
	}
	public String release(){
		//int t=(int) (subRange* (mx-(x +sx))/subSx);
		setSlider(index);
		return sub+index;
	}

	@Override
	public void segment(float dx,float dy){
		GL11.glColor4f(1,1,1,1);
		Texture.bind(0);
		
		float Y=y+dy;
		float X=x+dx;
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex3f(X+sx,Y-sy,depth);
		GL11.glVertex3f(X+sx,Y,depth);
		GL11.glVertex3f(X+sx+subSx,Y,depth);
		GL11.glVertex3f(X+sx+subSx,Y-sy,depth);
		GL11.glEnd();

		float ss=sliderSize;
		if(ss<0.4)
			ss=0.4f;
		
		float ssx=sliderX+dx;
		GL11.glColor4f(1,0,0,1);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex3f(ssx,Y-sy,depth+0.001f);
		GL11.glVertex3f(ssx+ss,Y-sy,depth+0.001f);
		GL11.glVertex3f(ssx+ss,Y,depth+0.001f);
		GL11.glVertex3f(ssx,Y,depth+0.001f);
		GL11.glEnd();
	}
}
