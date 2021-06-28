package intervalGui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import interval.Calender;
import interval.FileManager;
import interval.Main;
import interval.MainFrame;
import interval.Tex;
import interval.Texture;
import interval.Timeline;
import interval.VecF;

import org.lwjgl.opengl.GL11;

public class GItimeline extends GI {

	int count;
	Tex plain;
	Tex bitty;
	Tex ends;
	public GItimeline(){
		refresh();
		tex=new Tex(FileManager.dir()+"timey.png");
		depth=-0.008f;
		//super(s,xi,yi,sxi,syi);
		colorNormal=new VecF(1,1,1,1);//new Vec(0.2f,0.6f,0.7f,1f);
		colorOver=new VecF(0.9f,0.8f,0.6f,1f);
		colorDown=new VecF(1,1,1,0.5f);
		selection=colorNormal;
		x2=-MainFrame.screenStretch;

		count=Main.level.getStats().getCapEntSnaps();
		BufferedImage boo=tex.img.getSubimage(8, 0, 16, 16);
		BufferedImage bim=Texture.base(count*8, 8);
		Graphics2D g2=bim.createGraphics();
		for(int u=0;u<count;u++)
			g2.drawImage(boo,u*8,-8,null);

		BufferedImage bim3=Texture.base(8, 16);
		bim3.createGraphics().drawImage(tex.img,0,0,8,16,24,0,32,16,null);
		plain=new Tex(bim);
		bitty=new Tex(bim3);

		BufferedImage bim4=Texture.base(8, 16);
		bim4.createGraphics().drawImage(tex.img,0,0,null);
		ends=new Tex(bim4);
		bit=1f/count;
		part=sx*bit;
	}
	public void refresh(){
		x=MainFrame.screenLeft();
		y=64;
		sy=8;
		sx=MainFrame.screenSize();
		bit=1f/count;
		part=sx*bit;
	}
	float x2;
	float xi;
	float bit;
	float part;

	public void render(float dx,float dy){
		if(!visible){
			return;
		}
		GL11.glPushMatrix();
		GL11.glColor4f(1,1,1,1);
		
		float yy=y+dy;
		float xx=x+dx;
		
		if(effect)
			tex.bind();
		else
			plain.bind();

		GL11.glBegin(GL11.GL_QUADS);

		if(effect){

			//float p=0;
			float Y=0;
			boolean bb;
			for(float u=0;u<sx;u+=part){
				//p+=bit;
				Y= (float) (5f*Math.exp(-Math.abs((x+u+part/2f)-mx)/3));
				bb=Y>3;
			GL11.glTexCoord2f(bb?0.5f:0.25f, 1);
			GL11.glVertex3f(xx+u,-Y+yy-sy,depth);
			GL11.glTexCoord2f(bb?0.5f:0.25f, 0);
			GL11.glVertex3f(xx+u,yy,depth);
			GL11.glTexCoord2f(bb?0.75f:0.5f, 0);
			GL11.glVertex3f(xx+u+part,yy,depth);
			GL11.glTexCoord2f(bb?0.75f:0.5f, 1);
			GL11.glVertex3f(xx+u+part,-Y+yy-sy,depth);

			}
		}else{
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex3f(xx,yy-sy/2,depth);
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex3f(xx,yy,depth);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex3f(xx+sx,yy,depth);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex3f(xx+sx,yy-sy/2,depth);
		}

		GL11.glEnd();

		GL11.glPopMatrix();
		//float mm=Main.screenStretch;

		xi=(float) ((MainFrame.screenSize())*((double)Main.world.timeline.getTick()/(double)Main.level.getStats().getCapTicks())+MainFrame.screenLeft());
		bitty.bind();

		float F=effect?0:2;
		GL11.glColor4f(1f,0.8f,0.2f,1f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex3f(xi, 64+F,-0.008f);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex3f(xi+part, 64+F,-0.008f);
		GL11.glTexCoord2f(1,1);
		GL11.glVertex3f(xi+part, 56+F,-0.008f);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex3f(xi, 56+F,-0.008f);
		//GL11.glEnd();

		GL11.glColor4f(0.2f,0.4f,1f,1f);
		//GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex3f(x2, 64+F,-0.007f);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex3f(x2+part, 64+F,-0.007f);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex3f(x2+part, 56+F,-0.007f);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex3f(x2, 56+F,-0.007f);
		GL11.glEnd();





		if(animating){
			animate();

		}
		//else
			//effect=false;

		
	}

	boolean effect=false;
	public String over(){
		effect=true;
		return overString;
	}
	String overString="press T to time travel";
	public String click(){
		float f=((float)mx-x)/MainFrame.screenSize();
		//System.out.println(f);
		int I=(int) Math.floor(Main.level.getStats().getCapEntSnaps()*f);
		//System.out.println(f);
		//int R=I/Main.world.stats.entRate;
		//System.out.println("R: "+(I/Main.world.stats.terraRate));
		//System.out.println("I: "+I);
		//System.out.println(Main.world.stats.terraRate);


		Main.player.setDestination(Timeline.invertEnt(Main.level.getStats(), I));
		x2=(float)((MainFrame.screenSize()*(Main.player.getDestination()))/(Main.level.getStats().getCapTicks()))+MainFrame.screenLeft();

		overString=Calender.predictCalender(Main.level.getStats(),Main.player.getDestination());


		return "time"+I;
	}
	public void out(){
		effect=animating;
	}
	boolean animating=false;
	public void animate(){
		
		effect=true;
		x-=0.2f;
		sx+=0.4f;
		y+=0.15f;
		sy=8;
		if(sx>=MainFrame.screenSize()){
			animating=false;
			refresh();
		}

		ends.bind();

		GL11.glColor4f(1,1,1,1);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex3f(-2+x, y,-0.007f);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex3f(-2+x+part, y,-0.007f);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex3f(-2+x+part, y-8,-0.007f);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex3f(-2+x, y-8,-0.007f);


		GL11.glTexCoord2f(0, 0);
		GL11.glVertex3f(2+x+sx, y,-0.006f);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex3f(2+sx+x-part, y,-0.006f);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex3f(2+sx+x-part, y-8,-0.006f);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex3f(2+sx+x, y-8,-0.006f);

		GL11.glEnd();

	}
	public void startAnimation(){
		sx=0;
		x=32;
		y=32;
		animating=true;
	}
}
