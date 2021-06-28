package intervalGui;

import interval.Texture;
import java.awt.image.BufferedImage;
import org.lwjgl.opengl.GL11;

public class GIpic extends GI {
	int id;
	public GIpic(int u,float xx,float yy,float sxx,float syy){
		id=u;
		x=xx;
		y=yy;
		sy=syy;
		sx=sxx;
		depth=-0.01f;
	}
	public GIpic(BufferedImage bim,float xx,float yy,float sxx,float syy){
		this(0,xx,yy,sxx,syy);
		set(bim);
	}
	
	
	public void render(float dx,float dy){
		if(!visible){
			return;
		}
		float Y=y+dy;
		float X=x+dx;
		Texture.bind(id);
		GL11.glColor3f(1, 1,1);
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
	}
	public void set(BufferedImage bim){
		//Message.m("w "+bim.getWidth());
		set(Texture.make(bim),bim.getWidth(),bim.getHeight());
	}
	public void set(int ii,int w,int h){
		id=ii;
		sy=sx*h/w;
	}
}
