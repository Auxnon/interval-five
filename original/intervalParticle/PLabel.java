package intervalParticle;

import interval.IFont;
import interval.Render;
import interval.Tex;
import interval.Texture;
import intervalEntity.Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.lwjgl.opengl.GL11;

public class PLabel extends Particle {

	float height;
	Tex tex;
	String text1="1";

	float sizx;
	short style;
	boolean type=false;
	public PLabel(String label,float x,float y,float z,float high){
		this(label,x,y,z,high,IFont.PILLAR);
	}
	public PLabel(String label,float x,float y,float z,float high,short style){
		this.style=style;
		setRemain(true);
		height=high;
		size=0.04f;
		this.x=x;
		this.y=y;
		this.z=z;
		int h=6*(label.length()+2);
		sizx=size*h/16;
		BufferedImage bim = new BufferedImage(h,16,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2=bim.createGraphics();
		IFont.message(g2, label, 0, 0,style);
		tex=new Tex(bim);
		type=true;
		lifetime=10000;
		//color=new Vec(0,1,1);
	}
	public PLabel(Entity e,float high){
		height=high;
		size=0.5f;
		sizx=size;
		BufferedImage bim = new BufferedImage(64,64,BufferedImage.TYPE_INT_ARGB);
		//Graphics2D g2=bim.createGraphics();
		tex=new Tex(bim);
	}
	
	public boolean time(){
		age++;
		return  age<lifetime;
	}
	public void render(){
		if(!type){
		Graphics2D g2=tex.img.createGraphics();
		//g2.setColor(Color.red);
		//g2.fillRect(0, 0, 64, 64);
		//g2.setColor(Color.BLUE);
		//g2.drawString(text1, 0, 20);
		//g2.drawString(text2, 0, 30);
		//g2.drawString(text3, 0, 40);
		
		IFont.message(g2, text1, 0, 0,style);
		tex.renew();
		}else{
			tex.bind();
		}
		
		GL11.glPushMatrix();
		
		
		GL11.glColor4f(color.x, color.y,color.z, 1);
		GL11.glTranslatef(x, y, z+height);
		
		GL11.glRotatef(-Render.getYaw(), 0, 0, 1);
		GL11.glRotatef(45, 1, 0, 0);
		GL11.glBegin(GL11.GL_QUADS);
		
		GL11.glNormal3f(1, 0, 0);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex3f(-sizx,-size,0);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex3f(sizx,-size,0);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex3f(sizx,size,0);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex3f(-sizx,size,0);
		GL11.glEnd();
		
		GL11.glPopMatrix();
		Texture.bind(0);
	}
}
