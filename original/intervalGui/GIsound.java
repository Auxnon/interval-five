package intervalGui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import interval.Message;
import interval.Tex;

public class GIsound extends GI {

	float[] points;
	int sel=-1;
	BufferedImage back;
	final int WIDE=256;
	final int HIGH=128;
	public GIsound(float x,float y){
		//super(str,32,32,0.66f,0.66f,-0.001f,IFont.BLACK);
		this.x=x;
		this.y=y;
		sx=64;
		sy=32;
		BufferedImage im=new BufferedImage(256,128,BufferedImage.TYPE_INT_RGB);
		tex=new Tex(im);
		points=new float[2];
		points[0]=0.5f;
		points[1]=0.5f;
	}
	public void setPoints(float[] f){
		points=f;
	}
	public float[] getPoints(){
		return points;
	}
	public String over(){
		test(mx,my-32);
		return "";
	}
	public String clickR(){
		if(points.length>2)
			plot(false,mx,my-32);
		return "";
	}
	public String release(){
		plot(!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT),mx,my-32);
		return super.release();
	}
	public void test(float xo,float yo){
		float xi=(xo*4);
		float f=256f/(points.length-1);
		float half=32f/(points.length-1);
		if(half<1)
			half=1;
		boolean done=false;
		boolean none=true;
		for(int j=0;j<points.length;j++){
			if(xi>((j)*f)-half && xi<((j)*f)+half){
				if(sel!=j){
					redP(j,j*f, (points[j]*128));
					done=true;
				}
				none=false;
			}
		}

		if(none && sel!=-1){
			redP(-1,-100,0);
			done=true;
		}
		if(done)
			tex.renew();
	}

	public void redraw(){
		lx=-1;
		redraw1();
		redraw2();
	}
	private void redraw1(){
		if(back==null){
			tex.g2.setColor(Color.black);
			tex.g2.fillRect(0, 0, 256, 128);
		}else{
			tex.g2.drawImage(back,0,0,null);
		}
	}
	private void redraw2(){
		tex.g2.setColor(Color.white);
		float f=256f/(points.length-1);
		for(int j=0;j<points.length;j++){
			p(j*f,(points[j]*128f));
		}
		tex.renew();
	}
	public void plot(boolean clk,float xo,float yo){
		float xi=(xo*4);
		redraw1();
		lx=-1;
		float f=256f/(points.length-1);
		if(sel!=-1){
			if(clk){
				points[sel]=yo/32f;
			}else{
				ue(sel,yo/32f);
			}
		}else if(clk){
			for(int j=0;j<points.length;j++){
				if(xi>(j)*f && xi<(j+1)*f){
					re(j+1,yo/32f);
				}
			}
		}
		redraw2();
		sel=-1;
	}
	public void re(int k,float d){
		float p[]=new float[points.length+1];
		for(int u=0;u<k;u++){
			p[u]=points[u];
		}
		p[k]=d;
		for(int u=k+1;u<p.length;u++){
			p[u]=points[u-1];
		}
		points=p;
	}
	public void ue(int k,float d){
		float p[]=new float[points.length-1];
		for(int u=0;u<k;u++){
			p[u]=points[u];
		}
		for(int u=k;u<p.length;u++){
			p[u]=points[u+1];
		}
		//H(points);
		//H(p);
		points=p;
	}
	private void H(float[] f){
		String s="";
		for(int u=0;u<f.length;u++)
			s+=f[u]+",";

		Message.m(this, "array: "+s);
	}
	int lx=-1,ly;
	public void p(float xi,float yi){
		int xu=toInt(xi);
		int yu=toInt(yi);
		tex.g2.fillOval(xu-5, yu-5, 10, 10);
		if(lx==-1){
			lx=xu;ly=yu;
		}
		tex.g2.drawLine(lx, ly, xu,yu);
		lx=xu;
		ly=yu;
	}
	private int toInt(float t){
		return (int) Math.floor(t);
	}
	public void redP(int sl,float xi,float yi){
		tex.g2.setColor(Color.red);
		int xu=toInt(xi);
		int yu=toInt(yi);
		tex.g2.fillOval(xu-5, yu-5, 10, 10);
		tex.g2.drawLine(xu, 0, xu, 128);

		//Message.m(this, "a: "+xi+","+yi);
		if(sel!=-1){
			xi= (sel*256/(points.length-1));
			yi= (points[sel]*128);
			xu=toInt(xi);
			yu=toInt(yi);
			tex.g2.setColor(Color.black);
			tex.g2.drawLine(xu, 0, xu, 128);

			tex.g2.setColor(Color.white);
			tex.g2.fillOval(xu-5, yu-5, 10, 10);


		}
		sel=sl;
	}

	public void setBack(ByteBuffer by){
		
		back=new BufferedImage(WIDE,HIGH,BufferedImage.TYPE_INT_RGB);
		byte bb[]=new byte[by.capacity()];
		by.get(bb);
		int W=bb.length;//WIDE;
		
		final int clump=bb.length/W;
		int cluster[]=new int[W];
		int c=0;
		int F=0;
		int I=0;
		int highest=-9000;

		Graphics2D gg=back.createGraphics();
		gg.setColor(Color.black);
		gg.fillRect(0, 0, WIDE,HIGH);
		gg.setColor(Color.lightGray);
		int half=HIGH/2;
		
		
		for(byte b:bb){
			c++;
			F+=b;
			if(c>=clump){
				c=0;
				cluster[I]=F;
				if(Math.abs(F)>highest){
					highest=Math.abs(F);
				}
				I++;
				if(I>=W)
					I--;
				F=0;
			}
		}

		float factr=(float)highest*2.5f/(float)HIGH;
		
		int llx=0;
		int lly=0;
		for(int j=0;j<W;j++){
			int Y=half+(int) ((float)cluster[j]/factr);
			int J=WIDE*j/W;
			
			gg.drawLine(llx, lly, J, Y);
			llx=J;
			lly=Y;
		}
		
	}

	public void render(float dx,float dy){
		if(!visible){
			return;
		}
		GL11.glPushMatrix();
		GL11.glColor4f(selection.x,selection.y,selection.z,selection.vx);
		if(tex!=null)
			tex.bind();

		float X=x+dx;
		float Y=y+dy;

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex3f(X,Y-sy,depth);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex3f(X,Y,depth);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex3f(X+sx,Y,depth);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex3f(X+sx,Y-sy,depth);
		GL11.glEnd();
		GL11.glPopMatrix();
		//unHold();

	}
}
