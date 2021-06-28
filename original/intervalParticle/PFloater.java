package intervalParticle;

import interval.IFont;
import interval.Message;
import interval.Render;
import interval.Tex;
import interval.Texture;
import intervalEntity.Entity;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.lwjgl.opengl.GL11;

public class PFloater extends Particle {

	Entity E;
	float height;
	Tex tex;
	String text1="1";
	String multi[];
	boolean renew=false;
	float sizx;
	int style;
	boolean type=false;
	public PFloater(String label,Entity e,float high){
		this(label,e,high,IFont.PILLAR);
	}
	public PFloater(String label,Entity e,float high,int style){
		this.style=style;
		height=high;
		size=0.5f;
		E=e;
		int ssx=(int) (IFont.length(label));
		sizx=size*ssx/16;
		BufferedImage bim =new BufferedImage(ssx,(int) (16),BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2=bim.createGraphics();
		IFont.message(g2, label, 0, 0,style);
		tex=new Tex(bim);
		type=true;
		lifetime=2;
	}

	public void setText(int st,String... param){
		if(param.length>1)
			text1=param[0];
		
		figure(st,param.length);
		if(multi==null){renew=true;
			multi=param;
		}
		renew=param.length!=multi.length;
		if(!renew){
			for(int h=0;h<param.length;h++)
				if(!param[h].matches(multi[h]))
					renew=true;
		}
		multi=param;
		
	}
	public void setText(String s,int st){
		renew=!s.equals(text1);
		text1=s;
		figure(st,1);
	}
	
	private int SZ=256;
	private void figure(int sty,int sz){
		if(sty!=style){
			back=new BufferedImage(SZ,64,BufferedImage.TYPE_INT_ARGB);
			if(style!=-1)
				IFont.backer(sty,back.createGraphics(),0,0,3,SZ);

		}
		style=sty;
	}
	public PFloater(Entity e,float high){
		height=high;
		size=0.5f;
		E=e;
		sizx=size*SZ/64;
		BufferedImage bim = new BufferedImage(SZ,64,BufferedImage.TYPE_INT_ARGB);
		back = new BufferedImage(SZ,64,BufferedImage.TYPE_INT_ARGB);
		//Graphics2D g2=bim.createGraphics();
		tex=new Tex(bim);
	}
	BufferedImage  back;

	public boolean time(){
		age++;
		return  age<lifetime;
	}
	public void render(){
		if(!type && renew){
			if(multi!=null){ 
				int Y=0;
				tex.img.setData(back.getData());
				Graphics2D g2=tex.img.createGraphics();
				//Message.addPass("renew!!");
				for(String str:multi)
					IFont.write(g2,str,4,4+(Y++)*16);
				

			}else
				IFont.message(tex.img.createGraphics(), text1, 0, 0,style);
			
			tex.renew();
			renew=false;
		}else{
			tex.bind();
		}

		GL11.glPushMatrix();


		GL11.glColor4f(color.x, color.y,color.z, 1);
		GL11.glTranslatef(E.x, E.y, E.z+height);

		GL11.glRotatef(-Render.getYaw(), 0, 0, 1);
		GL11.glRotatef(-Render.getPitch(), 1, 0, 0);
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
