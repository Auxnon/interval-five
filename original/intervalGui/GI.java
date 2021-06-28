package intervalGui;
import interval.IFont;
import interval.Main;
import interval.MainFrame;
import interval.Message;
import interval.Render;
import interval.Tex;
import interval.Texture;
import interval.TextureCache;
import interval.VecF;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.Hashtable;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GI {
	public static final float stackSize=0.01f;
	public static final float subStackSize=0.005f;
	public float x=0;
	public float y=0;
	public float sx=0;
	public float sy=0;
	public float mx,my;
	VecF colorNormal=new VecF(1,1,1,1);
	VecF colorDown=new VecF(0.6f,0.6f,0.6f,1);
	VecF colorOver=new VecF(1,1,1,1);
	VecF selection=colorNormal;
	int orient=0;
	Tex tex=TextureCache.empty;
	public String name="default"; //used for 'onPress' callbacks, similar to action but should never change
	public String action="";
	boolean visible=true;
	//boolean scrollItem=false;
	boolean canKey=true;
	boolean displayOnly=false;
	boolean hidden=false;
	boolean focused=false;
	float depth=0f;
	float px=1f;
	float py=1f;
	int textType;
	
	boolean sticky=false;
	
	public static final float FACTOR=4f;
	
	public GI(){
	}
	public GI(String s,float xi,float yi){
		this(s,xi,yi,1,1,-0.02f,IFont.PILLAR);
	}
	public GI(String s,float xi,float yi,int type){
		this(s,xi,yi,1,1,-0.02f,type);
	}
	public GI(String s,float xi,float yi,int type,BufferedImage txt,Color bak){
		this(s,xi,yi,1,1,-0.02f,type,txt,bak);
	}
	public GI(String s,float xi,float yi,float px,float py,int type){
		this(s,xi,yi,px,py,-0.02f,type);
	}

	/**
	 * called on screen resize
	 */
	public void refresh(){
		switch(orient){
		case 1: centerLeft();break;
		case 2: centerRight();break;
		}
	}
	/**
	 * centers onto it's x position
	 */
	public GI leftAlign(){
		x-=sx;
		return this;
	}

	public GI centerX(){
		x-=sx/2;
		return this;
	}
	public GI half(){
		sx/=2f;
		sy/=2f;
		return this;
	}
	/**centers far right, refreshed on screen resize*/
	public GI centerRight(){
		x=(MainFrame.screenRight())-sx;
		orient=2;
		return this;
	}
	/**centers far left, refreshed on screen resize*/
	public GI centerLeft(){
		x=MainFrame.screenLeft();
		orient=1;
		return this;
	}
	

	public GI(String s,float xi,float yi,float px,float py,float depth,int type){
		this(s,xi,yi,px,py,depth,type,null,null);
	}
	public GI(String s,float xi,float yi,float px,float py,float depth,int type,BufferedImage txt,Color bak){
		this.depth=depth;
		this.px=px;
		this.py=py;
		colorOver=new VecF(0.9f,0.8f,0.6f,1f);
		selection=colorNormal;
		int ssx=(int) (IFont.length(s));
		BufferedImage bim =new BufferedImage(ssx,(int) (16),BufferedImage.TYPE_INT_ARGB);
		if(ssx*px>MainFrame.getW()){
			sx=ssx*px/8;
		}else{
			sx=ssx*px/4;
		}
		sy=4;
		x=xi;
		y=yi;
		name=s;
		action=s;
		Graphics2D g=bim.createGraphics();
		if(txt==null || bak==null)
			IFont.message(g, s,ssx, 0, 0,type);
		else
			IFont.message(g, s,ssx, 0, 0,type,txt,bak);
		textType=type;
		tex=new Tex(bim);
	}
	public void redo(String ss,int nn){
		action=ss;
		redo2(ss,nn,true,null,null);
	}
	public void redo(String ss){
		redo(ss,textType);
	}
	/**
	 * redo without remaking background
	 */
	public void redoHard(String ss){
		action=ss;
		redo2(ss,textType,false,null,null);
	}
	/**
	 * redo without changing action
	 */
	public void redo2(String ss,int nn){
		redo2(ss,nn,true,null,null);
	}
	public void redoColored(String ss,int nn){
		
	}
	public void redo2(String ss,int nn,boolean b,BufferedImage text,Color back){
		if(b){
			WritableRaster raster;
			int ssx=(int) (IFont.length(ss));
			raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,ssx,16,4,null);
			tex.img =new BufferedImage(Texture.glAlphaColorModel,raster,false,new Hashtable());
			if(ssx*px>MainFrame.getW()){
				sx=ssx*px/8;
			}else{
				sx=ssx*px/4;
			}
			sy=4;
			tex.g2=tex.img.createGraphics();
			tex.setWide(ssx);
		}
		if(text==null || back==null){
			IFont.message(tex.g2, ss,(int)sx*4, 0, 0,nn);
		}else{
			IFont.message(tex.g2, ss,(int)sx*4, 0, 0,nn,text,back);
		}
		tex.setHigh(16);
		tex.renew2();
	}
	public void makePane(int type,int x,int y,int sx,int sy){
		this.sx=sx;
		this.sy=sy;
		tex.img=Texture.base(sx, sy);
		tex.setWide(sx);
		tex.setHigh(sy);
		tex.setType(GL11.GL_RGBA);
		tex.g2=tex.img.createGraphics();
		IFont.para(type, tex.g2, x, y, sx, sy);
		tex.renew2();
	}

	public void makeStrip(int type,int x,int y,int sx,int size){
		this.sx=sx;
		this.sy=FACTOR;
		tex.img=Texture.base( sx*4, 16);
		tex.setWide((int) (sx*4));
		tex.setHigh(16);
		tex.setType(GL11.GL_RGBA);
		tex.g2=tex.img.createGraphics();
		IFont.backer(type, tex.g2, 0, 0, sx*4); //(int)(sx/1.75)
		tex.renew2();
	}
	/**verifies mouse within item*/
	public boolean check(float i,float j){
		mx=i;my=j;
		return (i>x && i<x+sx  && j>y-sy && j<y);
	}
	
	public boolean render(){
		render(0,0);
		return false;
	}
	public boolean render(float dx){
		return false;
	}
	public void render(float dx,float dy){
		if(!visible){
			return;
		}
		
		GL11.glPushMatrix();
		GL11.glColor4f(selection.x,selection.y,selection.z,selection.vx);
		if(tex!=null)
			tex.bind();

		float Y=y+dy;
		float X=x+dx;

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex3f(X,Y-sy,depth);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex3f(X,Y,depth);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex3f(X+sx,Y,depth);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex3f(X+sx,Y-sy,depth);
		GL11.glEnd();
		GL11.glPopMatrix();
		//unHold();

	}
	public void run(){

	}
	/**called while mouse is pressed down (continuous)*/
	public String down(){
		selection=colorDown;
		return "";
	}

	public String downR(){
		return "";
	}
	public String clickR(){
		return "";
	}
	public String release(){
		selection=colorNormal;
		return action; //TODO swap from action to name? what's the problem with it?
	}

	/**called when this item is no longer focused on*/
	public void unselect(){
		selection=colorNormal;
	}

	/**called when mouse is over*/
	public String over(){
		if(selection==colorNormal){
			//Main.sound.runInstance(Main.sound.menu);
			selection=colorOver;
		}
		return "";
	}
	public void out(){
		selection=colorNormal;
	}
	/**called when mouse is pressed down (once)*/
	public String click(){
		return "";
	}
	/**makes item unclickable and slightly transparent*/
	public GI ambient(){
		displayOnly=true;
		canKey=false;
		colorNormal.factor(0.4f);
		return this;
	}
	public GI ambientOpaque(){
		displayOnly=true;
		canKey=false;
		return this;
	}
	/**reverse ambient*/
	public GI unambient(){
		displayOnly=false;
		canKey=true;
		colorNormal.factor(2.5f);
		return this;
	}
	/**unhides item and makes interactable*/
	public void unhide(){
		visible=true;
		hidden=false;
	}
	/**hides item and makes uninteractable*/
	public void hide(){
		visible=false;
		hidden=true;
	}

	public String last(){
		return "";
	}
	/*protected void finalize(){
		if(tid!=0){
			TextureCache.removeTexture(tid);
		}
	}*/
	/**called while item is still selected (continuous)*/
	public void lastSelected() {

	}
	int typerDelay=0;
	String lastKey="";
	public boolean typer(int font,String prefix){
		if(Keyboard.isKeyDown(Keyboard.KEY_RETURN)){
			return false;
		}
		String s=Main.control.getPureKey();
		
		if(s!="" || Keyboard.isRepeatEvent()){
			if(!Keyboard.isRepeatEvent())
				lastKey=s;
			else
				s=lastKey;
			
			if(s.length()==1){
				action+=s;this.redo2(prefix+action, font);
			}else if(s.toLowerCase().equals("space")){
				action+=" ";this.redo2(prefix+action, font);
			}else if(action.length()>0&&s.toLowerCase().equals("back")){
				action=action.substring(0,action.length()-1);this.redo2(prefix+action, font);
			}
		}
		return true;
	}

	public float[] icon(int i){
		float h=TextureCache.buttons.getHeight();
		float W=TextureCache.buttons.getWidth();
		float w=i*h;
		float w2=(i+1)*h;
		return new float[]{w/W,w2/W};
	}
	public void startAnimation() {

	}
	public void setDepth(float f) {
		depth=f;
	}
	public void command(String s){
		Render.getGui().command(s,this);
	}
}
