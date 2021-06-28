package intervalGui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.lwjgl.opengl.GL11;

import interval.FileManager;
import interval.Tex;
import interval.Texture;

public class GImeter extends GI {

	BufferedImage bim;
	BufferedImage bim2;

	BufferedImage cap1;
	BufferedImage cap2;
	BufferedImage mid;
	BufferedImage bit;

	BufferedImage capp1;
	BufferedImage capp2;
	BufferedImage midp;
	BufferedImage blank;

	public GImeter(float xx,float yy){
		bim=FileManager.loadImage("health.png");
		blank=Texture.base(128,16);
		bim2=Texture.base(128,16);
		bim2.setData(blank.getData());
		x=xx;
		y=yy;
		sx=64;
		sy=8;
		cap1=bim.getSubimage(0, 0, 5, 16);
		mid=bim.getSubimage(5, 0, 3, 16);
		cap2=bim.getSubimage(10, 0, 5, 16);
		bit=bim.getSubimage(15, 0, 1, 16);

		capp1=bim.getSubimage(0, 16, 5, 16);
		midp=bim.getSubimage(5, 16, 3, 16);
		capp2=bim.getSubimage(10, 16, 5, 16);


		Graphics2D g2=bim2.createGraphics();
		g2.drawImage(cap1,0,0,null);
		int i=0;

		for(i=0;i<5;i++)
			g2.drawImage(mid,5+i*3,0,null);

		g2.drawImage(cap2,5+i*3,0,null);
		tex=new Tex(bim2);
	}

	public void redo(int n,int s,boolean buff){
		tex.img.setData(blank.getData());
		tex.g2.drawImage(cap1,0,0,null);
		int i=0;
		for(i=0;i<n-2;i++)
			tex.g2.drawImage(mid,5+i*3,0,null);

		tex.g2.drawImage(cap2,4+i*3,0,null);
		

		if(s>=1){
			tex.g2.drawImage(capp1,0,0,null);
				//s-=1;
				
				if(s==n){
					tex.g2.drawImage(capp2,-2+s*3,0,null);
					if(buff)tex.g2.drawImage(bit,-2+s*3,0,null);
				}
				
				for(i=0;i<s-1 && i<n-2;i++){
					tex.g2.drawImage(midp,5+i*3,0,null);
					if(buff)tex.g2.drawImage(bit,4+i*3,0,null);
				}

		}

		tex.renew();

	}

	public void render(float dx,float dy){
		if(!visible){
			return;
		}
		GL11.glPushMatrix();
		GL11.glColor4f(1,1,1,1);
		if(tex!=null)
			tex.bind();

		float Y=y+dy;
		float X=x+dx;
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex3f(X,Y+sy,depth);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex3f(X,Y,depth);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex3f(X+sx,Y,depth);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex3f(X+sx,Y+sy,depth);
		GL11.glEnd();
		GL11.glPopMatrix();
	}
}
