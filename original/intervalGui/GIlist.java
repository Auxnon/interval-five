package intervalGui;

import interval.IFont;
import interval.Main;
import interval.MainFrame;
import interval.Message;
import interval.Render;
import interval.Texture;
import interval.VecF;

import java.util.ArrayList;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GIlist extends GI {
	ArrayList<GI> items;
	GI first;
	GI select;
	float offset;
	boolean compact=true;
	
	int type;
	float fa;
	float tam;
	int maintain=0;
	int selectionn;
	float yo;
	int delay;
	private String search;
	int searchCount=1;
	boolean rightClickType=false;
	boolean firstClick=false;
	int facto;
	VecF v1=new VecF(1,1,1,1);
	VecF v2=new VecF(0,1,1,1);
	public GI idParam;
	String append="";

	final public float FACTOR2=4f;
	
	int tall;
	int thick;
	
	boolean clip=false;
	float clipped=0f;
	
	public void clip(float i){
		clipped=i;
		clip=true;
		if(i<tall)
			tall=(int) i;
	}
	public GI centerX(){
		x-=sx/2;
		set(x,y);
		return this;
	}
	public GI centerRight(){
		x=(MainFrame.screenRight())-sx;
		orient=2;
		set(x,y);
		return this;
	}
	public GI centerLeft(){
		x=MainFrame.screenLeft();
		orient=1;
		set(x,y);
		return this;
	}
	
	public void set(float xx,float yy){
		int u=0;
		x=xx;
		y=yy;
		fa=0;
		yo=yy;
		for(GI gi:items){
			gi.x=xx;
			gi.y=yy-u*FACTOR2;
			u++;
		}
	}
	public GIlist(String a,String st[],int firstSelection,int x,int y,int i){
		super(a,x,y,i);
		type=i;
		items=new ArrayList<GI>();
		setContents(st,firstSelection);
		boxIn();
		off();
		//action=a;
		idParam=this;
	}
	public void setTall(int i){
		tall=i;
		if(clip && tall>clipped){
			tall=(int) clipped;
		}
		boxIn();
	}
	public void boxIn(){
		if(tall>y){
			tall=(int) Math.floor(y);
		}
	}
	public int getIndex(){
		return selectionn;
	}
	/*public String getString(){
		return select!=null?select.action:"N/A";
	}*/
	public void setContents(String[] st){
		setContents(st,-1);
	}
	public void setContents(String[] st,int S){
		items.clear();
		
		for(int u=0;u<st.length;u++){
			GI gi=new GI(st[u],x,y-u*FACTOR2,IFont.BLANK);
			gi.depth=depth+0.001F;
			int d=(int) (gi.sx);
			if(d>thick)
				thick=d;
			items.add(gi);
			if(u==0){
				first=gi;
			}
		}
		setTall((int) (st.length*FACTOR2));
		//boxIn();
		offset=0;
		if(S==-1){
			selectionn=-1;
			select=null;
		}else{
			selectionn=S;
			select=items.get(S);
		}
		
		on();
	}
	
	public void lastSelected(){
		String sst=Main.control.getKey();
		if(sst!=""){
			int C=0;
			if(sst.equals(search)){
				searchCount++;
			}else{
				searchCount=1;
			}
			search=sst;
			for(int u=0;u<items.size();u++){
				GI G=items.get(u);
				if(G.name.toUpperCase().startsWith(sst)){
					C++;
					if(C>=searchCount){
						selectionn=u;
						Message.m(this, "found "+G.name);
						confirm();
						break;
					}
				}
			}
			if(C<searchCount){
				searchCount=0;
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
			upward(1);
		}else if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
			downward(1);
		}else if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
			off();
		}
	}
	public boolean check(float xx,float yy){
		boolean bb=super.check(xx,yy);
		if(!focused){
			return bb;
		}
		which(xx,yy);
		int md=Mouse.getDWheel()/20;
		if(bb&&maintain==1){
			firstClick=false;
		}else if(md!=0){
			if(md>0){
				upward(4);
			}else{
				downward(4);
			}
		}else{
			fa=y-yy;
		}
		yo=yy;
		return bb;
	}

	public String down(){
		if(!focused)
			return "";
		if(firstClick){
			slide((int) Math.floor(tam-fa));
			maintain=3;
		}
		return super.down();
	}
	public void slide(int u){
		int facto2=u;
		if(facto2!=facto){
			if(facto2-facto>0){
				upward(1);
			}else{
				downward(1);
			}
		}
		facto=facto2;
	}
	public void off(){
		if(rightClickType){
			hide();
		}
		//String a=action;
		this.redo(select!=null?select.name:first.name, type);
		//action=a;
		compact=true;
	}
	public void on(){
		//Message.addPass("thick: "+thick);
		this.makePane(IFont.BLACK, 0,0,thick,tall);
		compact=false;
	}
	public void upward(float f){
		//items.add(items.remove(0));
		offset-=f;
		if(offset<0){
			offset=0;
		}
	}
	public void downward(float f){
		//items.add(0, items.remove(items.size()-1));
		offset+=f;
		int o= (int) (((1+items.size())*FACTOR2)-sy);
		if(offset>o){
			offset=o;
		}
	}
	public String click(){

		if(!compact){
			tam=fa;
			facto=0;
			firstClick=true;
			confirm();
			//off();
			return "";
		}else{
			on();
		}
		return "";
	}

	public void confirm(){
		if(select!=null){
			select.selection=v1;
		}
		
		GI gg=items.get(selectionn);
		if(gg==null){
			return;
		}
		if(select!=gg){
			
			Render.getGui().command(append+gg.action,this);
		}
		
		Message.addPass(gg.action);
		gg.selection=v2;
		select=gg;
		offset=(selectionn*FACTOR2);//-sy/2;
	}
	@Override
	public String release(){
		return "";
	}

	public void which(float xx,float yy){
		for(int u=0;u<items.size();u++){
			if(items.get(u).check(xx, yy)){
				selectionn=u;
			}
		}
	}
	public void unselect(){
		off();
		
	}
	@Override
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
		
		tex.bind();
		
		if(maintain>0){
			maintain--;
		}
		if(compact){
			/*if(select!=null){
				select.y=this.y;
				select.render();
			}else{
				first.y=this.y;
				first.render();
			}*/
		}else{

			float iy=Y-sy*(offset/(FACTOR2*items.size()));
			Texture.bind(0);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex3f(X,iy-2,depth);
			GL11.glVertex3f(X,iy,depth);
			GL11.glVertex3f(X-1,iy,depth);
			GL11.glVertex3f(X-1,iy-2,depth);
			GL11.glEnd();
			
			float ff=FACTOR2/2f;
			float stack=GI.subStackSize/items.size();
			
			for(int u=0;u<items.size();u++){
				GI gi =items.get(u);
				gi.depth=depth+stack*(u+1);
				gi.y=offset+this.y-u*FACTOR2;
				if(gi.y-ff>(y-sy) && gi.y-ff<y)
					gi.render(dx,dy);
			}
		}
	}
	public void setAppend(String s){
		append=s;
	}

	
//	String param="";
/*	public String last(){
		String t=param;
		param="";
		return t;
	}*/
}
