package intervalGui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import interval.IFont;
import interval.MainFrame;
import interval.Tex;
import interval.VecF;
import org.lwjgl.opengl.GL11;

public class GIass extends GI {

	public float counter=0;
	float sf=1;
	public GIass(String str){
		this.centerX();
		selection=new VecF(1,1,1,1);
		ambient();
		BufferedImage bim =new BufferedImage(10,(int) (10),BufferedImage.TYPE_INT_ARGB);
		Graphics2D g=bim.createGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0,10,10);
		IFont.letter(str, g, 2, 1, 8);
		tex=new Tex(bim);
	}
	public void redo(String s){
		tex.g2.setColor(Color.black);
		tex.g2.fillRect(0, 0,10,10);
		IFont.letter(s, tex.g2, 2, 1, 8);
		tex.renew();
	}

	public void render(float dx,float dy){
		if(!visible){
			return;
		}
	
		GL11.glColor4f(1,1,1,1);//selection.x,selection.y,selection.z,ff);
		tex.bind();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex3f(MainFrame.screenLeft(),0,depth);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex3f(MainFrame.screenLeft(),64,depth);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex3f(MainFrame.screenRight(),64,depth);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex3f(MainFrame.screenRight(),0,depth);
		GL11.glEnd();

	}
}
