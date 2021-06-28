package intervalGui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import interval.Message;
import interval.Tex;

public class GImusic extends GI {

	final int DEGREE=10;

	float[] points;
	int[] lengths;
	int sel=-1;
	BufferedImage back;
	final int WIDE=1024;
	final int HIGH=512;
	
	public int defaultLength=0;
	public GImusic(float x,float y){
		//super(str,32,32,0.66f,0.66f,-0.001f,IFont.BLACK);
		this.x=x;
		this.y=y;
		sx=64;
		sy=32;
		BufferedImage im=new BufferedImage(WIDE,HIGH,BufferedImage.TYPE_INT_RGB);
		tex=new Tex(im);
		points=new float[1];
		lengths=new int[1];
		points[0]=0f;
		lengths[0]=0;
		//points[1]=0.5f;
	}
	public void reset(){
		lengths=new int[1];
		points=new float[1];
		points[0]=0f;
		lengths[0]=0;
		redraw();
	}
	private float clip(float f){
		return Math.round(f*DEGREE)/(float)DEGREE;
	}
	private int getL(int u){
		return lengths[u]+1;
	}
	private int atL(int u){
		int t=0;
		for(int k=0;k<u;k++){
			t+=lengths[k]+1;
		}
		return t;
	}	
	private int total(){
		int t=0;
		for(int j:lengths){
			t+=j+1;
		}
		return t;
	}
	public void setPoints(float[] f){
		points=f;
	}
	public float[] getPoints(){
		return points;
	}
	public int[] getDPoints(){
		int[] u =new int[points.length];
		for(int j=0;j<points.length;j++){
			u[j]=Math.round(points[j]*DEGREE);
		}
		return u;
	}
	public int[] getLengths(){
		return lengths;
	}
	public String over(){
		test(mx,32-(my-32));
		return "";
	}
	public String clickR(){
		if(points.length>1)
			plot(false,mx,32-(my-32));
		return "";
	}
	public String release(){
		plot(!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT),mx,32-(my-32));
		return super.release();
	}
	public void test(float xo,float yo){
		float xi=(xo*4);
		float T=total();
		float f=256f/T;
		float F=WIDE/T;
		float half=32f/(T);
		float half2=half*2;
		if(half<1)
			half=1;
		boolean done=false;
		boolean none=true;
		int t=0;
		boolean ctrl=Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);

		for(int j=0;j<points.length;j++){
			float tf=t*f;
			if(xi>(tf)-half && xi<(tf)+half2){
				boolean who=!ctrl &&(xi<(tf)+half);
				if(Who!=who)
					sel=-1;
				if(sel!=j){
					redP( who  ,j,t*F, (points[j]*HIGH));
					done=true;
				}
				none=false;
			}

			t+=getL(j);
		}

		if(none && sel!=-1){
			redP(true,-1,-100,0);
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
			tex.g2.fillRect(0, 0, WIDE, HIGH);
		}else{
			tex.g2.drawImage(back,0,0,null);
		}
	}
	private void redraw2(){
		int T=total();
		tex.g2.setColor(Color.white);
		float f=WIDE/(T);
		int t=0;
		for(int j=0;j<points.length;j++){

			p(j,T,t*f,(points[j]*HIGH));
			t+=getL(j);
		}
		tex.renew();
	}
	public void plot(boolean clk,float xo,float yo){
		float xi=(xo*4);
		redraw1();
		lx=-1;
		float f=WIDE/(total());
		if(sel!=-1){
			if(clk){
				if(Who){
					points[sel]=clip(yo/32f);
				}else
					lengths[sel]++;
			}else{
				if(Who)
					ue(sel);
				else
					if(lengths[sel]>0)
						lengths[sel]--;
			}
		}else if(clk){
			int t=0;
			for(int j=0;j<points.length;j++){

				if(xi>(t)*f && xi<(t+getL(j))*f){
					re(j+1,clip(yo/32f));
				}
				t+=getL(j);
			}
		}
		redraw2();
		sel=-1;
	}
	public void re(int k,float d){
		float p[]=new float[points.length+1];
		int l[]=new int[lengths.length+1];
		for(int u=0;u<k;u++){
			p[u]=points[u];
			l[u]=lengths[u];
		}
		p[k]=d;
		l[k]=((defaultLength>0)?defaultLength-1:0);
		for(int u=k+1;u<p.length;u++){
			p[u]=points[u-1];
			l[u]=lengths[u-1];
		}
		points=p;
		lengths=l;
	}
	public void ue(int k){
		float p[]=new float[points.length-1];
		int l[]=new int[lengths.length-1];
		for(int u=0;u<k;u++){
			p[u]=points[u];
			l[u]=lengths[u];
		}
		for(int u=k;u<p.length;u++){
			p[u]=points[u+1];
			l[u]=lengths[u+1];
		}
		//H(points);
		//H(p);
		points=p;
		lengths=l;
	}
	private void H(float[] f){
		String s="";
		for(int u=0;u<f.length;u++)
			s+=f[u]+",";

		Message.m(this, "array: "+s);
	}
	int lx=-1,ly;
	public void p(int I,int total,float xi,float yi){
		int xu=toInt(xi);
		int yu=toInt(yi);
		tex.g2.fillOval(xu-5, yu-5, 10, 10);
		if(lx==-1){
			lx=xu;ly=yu;
		}
		int H=(int)(WIDE*getL(I)/(float)total);
		
		tex.g2.drawLine(xu+1, yu, xu+H,yu);
		tex.g2.drawLine(xu-1, yu, xu-1,ly);
		tex.g2.drawString(""+getL(I),xu+H/2, yu+4);
		lx=xu;
		ly=yu;
	}
	private int toInt(float t){
		return (int) Math.floor(t);
	}
	boolean Who=false;
	public void redP(boolean who,int sl,float xi,float yi){
		Who=who;

		int xu=toInt(xi);
		int yu=toInt(yi);

		if(who){
			tex.g2.setColor(Color.red);
			tex.g2.fillOval(xu-5, yu-5, 10, 10);
			tex.g2.drawLine(xu, 0, xu, HIGH);
		}else{
			tex.g2.setColor(Color.white);
			tex.g2.fillOval(xu-5, yu-5, 10, 10);

			tex.g2.setColor(Color.green);
			tex.g2.fillPolygon(new int[]{xu,xu,xu+5}, new int[]{yu-5,yu+5,yu}, 3);

			//tex.g2.fillOval(xu-5, yu-5, 10, 10);
			tex.g2.drawLine(xu, 0, xu, HIGH);
		}
		//Message.m(this, "a: "+xi+","+yi);
		if(sel!=-1){
			xi= WIDE*((float)atL(sel)/(float)total());
			yi= (points[sel]*HIGH);
			xu=toInt(xi);
			yu=toInt(yi);
			tex.g2.setColor(Color.black);
			tex.g2.drawLine(xu, 0, xu, HIGH);

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
}
