package intervalGui;

import java.awt.image.BufferedImage;

import org.lwjgl.opengl.GL11;

import interval.FileManager;
import interval.IFont;
import interval.MainFrame;
import interval.Message;
import interval.Render;
import interval.Tex;
import interval.Texture;
import interval.VecF;

public class GIwindow extends GI {

	int wID=0;
	int fontType=0;
	boolean TYPE=false;
	float tx=0;
	float tx2=1;
	int m=0;
	
	private int id=0;
	boolean l=false;
	boolean r=false;
	boolean u=false;
	boolean d=false;

	boolean whole=false;
	float mmx=0;
	float mmy=0;

	boolean drag=false;
	float ratio;
	float aspect;
	
	String category="empty";
	
	
	static GIlist list;
	static{
		list=new GIlist("List",FileManager.getTextures(),-1,2,64,IFont.BLACK);
		list.rightClickType=true;
	}
	
	
	public GIwindow(int wID,float x,float y,float sx,float sy){
		this.x=x;
		this.y=y;
		this.sx=sx;
		this.sy=sy;
		selection=colorNormal;
		colorOver=new VecF(0.9f,0.8f,0.6f,1f);
		this.wID=wID;
		tex=new Tex(new BufferedImage(8,8,BufferedImage.TYPE_INT_ARGB));
	}
	

	public String click(){
		if(tx==0 && tx2==1){
		l=(mx>x && mx<x+1);
		r=(mx>x+sx-1 && mx<x+sx);
		d=(my<y-sy+1 && my>y-sy);
		u=(my<y && my>y-1);
		}
		if(!(l || r|| d|| u)){
			whole=true;
			mmx=mx-x;
			mmy=my-y;
		}
		selection=colorDown;
		drag=true;

		return "";
	}
	public String down(){
		return "";
	}
	public void run(){
		if(TYPE){
			TYPE=typer(fontType,"");
			id=this.tex.getID();
			return;
		}else{
			if(whole){
				x=mx-mmx;
				y=my-mmy;
			}else{
				if(l){
					sx+=x-mx+0.5f;
					x=mx-0.5f;
				}else if(r){
					sx=mx-x+0.5f;
				}
				if(u){
					sy-=y-my-0.5f;
					y=my+0.5f;
				}else if(d){
					sy=y-my+0.5f;
				}
			}
			if(sx<3)
				sx=3;
			if(sy<3)
				sy=3;
		}
	}

	public void done(){
		l=false;r=false;
		u=false;d=false;
		whole=false;
		drag=false;
		selection=colorNormal;

		if(x<MainFrame.screenLeft()){
			float xx=x;
			x=MainFrame.screenLeft();
			sx+=xx-x;
		}else if(x>MainFrame.screenRight())
			x=MainFrame.screenRight();
		else if(x+sx>MainFrame.screenRight())
			sx=MainFrame.screenRight()-x;

		if(y<0)
			y=0;
		else if(y>64){
			float yy=y;
			y=64;
			sy-=yy-y;
		}else if(y-sy<0)
			sy=y;
	}
	public void unselect(){
		if(TYPE)
			TYPE=false;
		done();
	}
	public String clickR(){
		if(TYPE)
			return "";
		openW(new String[]{
				">load texture",
				">set text",
				">fit",
				">add window",
				">process",
		">remove window"});
		return "wclk";
	}
	public void out(){
		if(!drag)
			selection=colorNormal;
	}
		
	public String release(){
		done();
		if(TYPE)
			TYPE=false;
		return "winRelease";
	}
	boolean ov=false;
	public String over(){
		ov=true;
		if(!drag)
			selection=colorOver;
		return "";
	}

	@Override
	public void render(float dx,float dy){
		if(!visible){
			return;
		}
		float Y=dy+y;
		float X=dx+x;
		
		if(ov){
			GL11.glColor4f(1,0,0,1);// 0.7f, 0.7f, 1f);
			float dd=depth+0.0001f;
			GL11.glBegin(GL11.GL_QUADS);
			
			

			GL11.glVertex3f(X,Y-sy,dd);
			GL11.glVertex3f(X+sx,Y-sy,dd);
			GL11.glVertex3f(X+sx,1+Y-sy,dd);
			GL11.glVertex3f(X,1+Y-sy,dd);

			GL11.glVertex3f(X,Y,dd);
			GL11.glVertex3f(X+sx,Y,dd);
			GL11.glVertex3f(X+sx,Y-1,dd);
			GL11.glVertex3f(X,Y-1,dd);

			GL11.glVertex3f(X+1,Y,dd);
			GL11.glVertex3f(X+1,Y-sy,dd);
			GL11.glVertex3f(X,Y-sy,dd);
			GL11.glVertex3f(X,Y,dd);

			GL11.glVertex3f(X+sx-1,Y,dd);
			GL11.glVertex3f(X+sx-1,Y-sy,dd);
			GL11.glVertex3f(X+sx,Y-sy,dd);
			GL11.glVertex3f(X+sx,Y,dd);

			GL11.glEnd();
			if(!drag)
				ov=false;
		}

		GL11.glPushMatrix();
		GL11.glColor4f(selection.x,selection.y,selection.z,selection.vx);

		Texture.bind(id);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(tx, 1);
		GL11.glVertex3f(X,Y-sy,depth);
		GL11.glTexCoord2f(tx, 0);
		GL11.glVertex3f(X,Y,depth);
		GL11.glTexCoord2f(tx2, 0);
		GL11.glVertex3f(X+sx,Y,depth);
		GL11.glTexCoord2f(tx2, 1);
		GL11.glVertex3f(X+sx,Y-sy,depth);
		GL11.glEnd();
		GL11.glPopMatrix();
	}
	public boolean call(String s){
		if(s.startsWith(">")){
			if(s.equals(">load texture")){
			
				openW2(FileManager.getTextures());
				category="image";
				return true;
			}else if( s.equals(">fit")){
				if(tx!=0 && tx2!=1){
					iconIt();
					GIwindow.list.off();
					return true;
				}
				String[] sta;
				if(ratio==0)
					sta=new String[]{">icon fit",">toggle auto-fit","","",""};
				else
					sta=new String[]{
						">perspective fit",
						">vertical fit",
						">horizontal fit",
						">icon fit",
				">toggle auto-fit"};

				openW2(sta);

				return true;
			}else if(s.equals(">perspective fit")){
				sx=aspect/4f;
				sy=sx*ratio;
			}else if(s.equals(">vertical fit")){
				sy=sx*ratio;
			}else if(s.equals(">horizontal fit")){
				sx=sy/ratio;
			}else if(s.equals(">icon fit")){
				iconIt();
			}else if(s.equals(">set text")){
				tx=0;tx2=1;
				redo(action, fontType);
				TYPE=true;
				category="label";
			}else{
				Render.getGui().command(s,this);
			}
		}else if(s.endsWith(".png")){
			BufferedImage b=FileManager.loadImage(s);
			set(Texture.get(s),b.getWidth(),b.getHeight());
		}
		GIwindow.list.off();
		return false;
	}
	public void iconIt(){
		category="icon";
		sx=FACTOR;
		set(Texture.get("buttons"),(int)FACTOR,(int)FACTOR);
		float f[]=icon(m);
		tx=f[0];tx2=f[1];
		m++;
		if(m>=16)
			m=0;
	}
	public void set(BufferedImage bim){
		set(Texture.make(bim),bim.getWidth(),bim.getHeight());
	}

	public void set(int ii,int w,int h){
		tx=0;tx2=1;
		id=ii;
		ratio=(float)h/(float)w;
		aspect=w;
		sy=sx*ratio;
	}

	public void openW(String[] st){
		GIwindow.list.setContents(st,-1);
		openW();
	}
	public void openW(){
		if(!Render.getGui().has(GIwindow.list))
			Render.getGui().add(GIwindow.list);

		GIwindow.list.set(mx,my);
		GIwindow.list.boxIn();
		GIwindow.list.unhide();
		GIwindow.list.on();
		GIwindow.list.idParam=this;

		Render.getGui().pick(GIwindow.list);
	}

	public void openW2(String[] st){
		GIwindow.list.setContents(st,-1);
		openW2();
	}

	public void openW2(){
		if(!Render.getGui().has(GIwindow.list))
			Render.getGui().add(GIwindow.list);

		//GIwindow.list.set(mx,my);
		GIwindow.list.unhide();
		GIwindow.list.on();
		GIwindow.list.idParam=this;
		//Render.getGui().pick(GIwindow.list);
	}
	public String toString(){
		return category+";(action,x,y,sx,sy): "+name+","+x+","+y+","+sx+","+sy;
	}
}
