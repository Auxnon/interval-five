package interval;

import java.awt.image.BufferedImage;
import org.lwjgl.opengl.GL11;

public class Title {

	static Tex tit;
	Tex tie;
	
	public VPoint p1;
	public VPoint p2;
	public VPoint p3;
	public VPoint p4;
	public VPoint p5;

	VStick s1;
	VStick s2;
	VStick s3;
	VStick s4;
	
	BufferedImage tem;
	
	public Title(){
		tit=new Tex(FileManager.dir()+"title2.png");
		tie = new Tex(FileManager.dir()+"tie1.png");
		tem = Texture.base(tit.img.getWidth(),tit.img.getHeight());
		tem.createGraphics().drawImage(tit.img, 0, 0,null);
		
	
		
		p1=new VPoint(30,60);
		p2=new VPoint(30,56);
		p3=new VPoint(30,52);
		p4=new VPoint(30,48);
		p5=new VPoint(30,44);

		s1=new VStick(p1,p2);
		s2=new VStick(p2,p3);
		s3=new VStick(p3,p4);
		s4=new VStick(p4,p5);

		
		p5.x=64;
		
	}
	
	public void scan(){
		int mx=tem.getWidth();
		int my=tem.getHeight();
		//float D=(float) (Math.exp(((float)Main.height/Mouse.getY()))-1f)*1000000;
		
		float fx=64f/(float)mx;
		float fy=(64f/(float)my);
		fy/=4f;
		
		float sx= p5.x;//Mouse.getX();
		float sy= p5.y;//Mouse.getY();
		int De=-255*255*255*255 ;
		//System.out.println(D);
		for(int x=0;x<mx;x++){
			for(int y=0;y<my;y++){
				int i =tem.getRGB(x, y);
				//System.out.println(i);
				
				if(i==-1 || i==-16777216){
					float dx=(x*fx)-sx;
					float dy=(y*fy)-(64-sy);
					float h = (float) Math.sqrt(dx*dx +dy*dy);
					int D=De;//-255*255*245*255 ;
					if(h<40f){
						if(i==-1){
						D=-1;
						}else{
							D=-9595392;
						}
					}
					tit.img.setRGB(x, y, D);
				}
			}
		}
		//tit.img=tem;
		
	}
	/*public void scale(){
		int M=128;
		sx1=Main.width*iint.wide/M;
		sx2=Main.width*er.wide/M;
		sx3=Main.width*val.wide/M;
		sx4=Main.width*I.wide/M;
		
		sy1=Main.width*iint.high/M;
		sy2=Main.width*er.high/M;
		sy3=Main.width*val.high/M;
		sy4=Main.width*I.high/M;
	}*/
	
	public void render(){
		GL11.glColor4f(1, 1, 1, 1);

		scan();
		
		tit.renew();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex3f(0, 64,-0.05f);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex3f(64,64,-0.05f);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex3f(64,48,-0.05f);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex3f(0, 48,-0.05f);
		GL11.glEnd();
		
		
		tie.bind();
		
		
		p1.refresh();
		p2.refresh();
		p3.refresh();
		p4.refresh();
		p5.refresh();

		
		p2.y-=0.05f;
		p3.y-=0.05f;
		p4.y-=0.05f;
		p5.y-=0.05f;
		

		s1.contract();
		s2.contract();
		s3.contract();
		s4.contract();
		
		p1.setPos(32,60);
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex3f(p1.x,p1.y,-0.04f);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex3f(p1.x+4,p1.y,-0.04f);
		///x2
		GL11.glTexCoord2f(1, 0.25f);
		GL11.glVertex3f(p2.x+4,p2.y,-0.04f);
		GL11.glTexCoord2f(0, 0.25f);
		GL11.glVertex3f(p2.x,p2.y,-0.04f);
		GL11.glEnd();
		
		
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(1, 0.25f);
		GL11.glVertex3f(p2.x+4,p2.y,-0.03f);
		GL11.glTexCoord2f(0, 0.25f);
		GL11.glVertex3f(p2.x,p2.y,-0.03f);

		GL11.glTexCoord2f(0, 0.5f);
		GL11.glVertex3f(p3.x,p3.y,-0.03f);
		GL11.glTexCoord2f(1, 0.5f);
		GL11.glVertex3f(p3.x+4,p3.y,-0.03f);
		GL11.glEnd();
		
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 0.5f);
		GL11.glVertex3f(p3.x,p3.y,-0.02f);
		GL11.glTexCoord2f(1, 0.5f);
		GL11.glVertex3f(p3.x+4,p3.y,-0.02f);

		GL11.glTexCoord2f(1, 0.75f);
		GL11.glVertex3f(p4.x+4,p4.y,-0.02f);
		GL11.glTexCoord2f(0, 0.75f);
		GL11.glVertex3f(p4.x,p4.y,-0.02f);
		GL11.glEnd();
		
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(1, 0.75f);
		GL11.glVertex3f(p4.x+4,p4.y,-0.01f);
		GL11.glTexCoord2f(0, 0.75f);
		GL11.glVertex3f(p4.x,p4.y,-0.01f);

		GL11.glTexCoord2f(0, 1f);
		GL11.glVertex3f(p5.x,p5.y,-0.01f);
		GL11.glTexCoord2f(1, 1f);
		GL11.glVertex3f(p5.x+4,p5.y,-0.01f);
		
		GL11.glEnd();
		
		
		
	}
}
