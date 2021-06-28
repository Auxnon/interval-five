package intervalGui;

import interval.FileManager;
import interval.MainFrame;
import interval.Message;
import interval.Render;
import interval.Tex;
import interval.VecF;

import org.lwjgl.opengl.GL11;

public class GIcapture extends GI{
	Gui parent;
	Tex t2;
	Square squares[];

	public GIcapture(Gui parenti){
		squares=new Square[4];
		squares[0]=new Square(6.4f,14.4f,4,12);
		squares[1]=new Square(20.8f,28.8f,4,12);
		squares[2]=new Square(35.2f,43.2f,4,12);
		squares[3]=new Square(49.6f,57.6f,4,12);
		visible=false;
		parent=parenti;
		x=MainFrame.screenLeft();
		y=16;
		sy=16;
		sx=MainFrame.screenRight();
		
		tex=new Tex(FileManager.dir()+"capBar.png");
		t2=new Tex(FileManager.dir()+"capBox.png");
		depth=-0.08f;
		//super(s,xi,yi,sxi,syi);
		colorNormal=new VecF(1,1,1,1);//new Vec(0.2f,0.6f,0.7f,1f);
		colorOver=new VecF(0.9f,0.8f,0.6f,1f);
		colorDown=new VecF(1,1,1,0.5f);
		selection=colorNormal;
		name="GIcapture";
		action="herp";	
	}
	public boolean check(float i,float j){
		if(visible){
			return  super.check(i, j);
		}
		return false;
	}
	public String over(){
		return "tabbed";
	}

	public void Show(){
		visible=true;
		int l=3;//UserData.invSlots;
		if(l>0){
			model(6.4f,"badger");
			if(l>1){
				model(20.8f,"playerIdle");
				if(l>2){
					model(35.2f,"BLUB");
					if(l>3){
						model(49.6f,"carr");
					}
				}
			}
		}
	}
	public void Hide(){
		Render.clean();
		visible=false;
	}
	public void render(float dx,float dy){
		if(!visible){
			return;
		}
		float Y=y+dy;
		float X=x+dx;
		GL11.glColor4f(1,1,1,1);
		t2.bind();
		int l=3;//UserData.invSlots;
		if(l>0){
			GL11.glBegin(GL11.GL_QUADS);
			square(6.4f);
			if(l>1){
				square(20.8f);
				if(l>2){
					square(35.2f);
					if(l>3){
						square(49.6f);
					}
				}
			}
			GL11.glEnd();
		}
		tex.bind();
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
		//GL11.glPopMatrix();		
	}
	public void model(float xx,String vox){
		Render.invModel(vox, xx+4, 8);
	}
	public void square(float xx){
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex3f(xx,12,-0.06f);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex3f(xx+8,12,-0.06f);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex3f(xx+8,4,-0.06f);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex3f(xx,4,-0.06f);
	}

	public String click(){
		float x=parent.mx;
		float y=parent.my;
		if(squares[0].check(x,y)){
			Message.addPass("badger");
		}else if(squares[1].check(x,y)){
			Message.addPass("human");
		}else if(squares[2].check(x,y)){
			Message.addPass("dinosaur");
		}else if(squares[3].check(x,y)){
			Message.addPass("truck");
		}
		return "";
	}
	
	public class Square {
		float x1,x2,y1,y2;
		
		public Square(float xx1,float xx2,float yy1,float yy2){
			x1=xx1;
			x2=xx2;
			y1=yy1;
			y2=yy2;
			
		}
		
		public boolean check(float x,float y){
			if(x>x1 && x<x2 && y>y1&& y<y2){
				return true;
			}
			return false;
		}
	}
}
