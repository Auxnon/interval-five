package intervalGui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import interval.IFont;
import interval.MainFrame;
import interval.Tex;
import interval.Texture;
import interval.TextureCache;
import org.lwjgl.opengl.GL11;



public class GIlvl extends GI {

	float sk;
	boolean red;
	public GIlvl(String str,int num,float x,float y,int type,int score,int col){
		action=str;
		name=str;
		this.x=x;
		this.y=y;
		sx=8;
		sy=10;
		BufferedImage bim=Texture.baseA(16, 20);
		Graphics2D g2=bim.createGraphics();
		g2.drawImage(TextureCache.level, 0,0,16,20, 0,12,16,32,null);
		g2.setColor(new Color(col));
		g2.fillRect(1, 1, 13, 17);

		int tt=type*16;
		g2.drawImage(TextureCache.level, 3,7,16,17, tt +3,0,tt+16,10,null);
		IFont.write(g2, ""+num, 0, 0);
		tex=new Tex(bim);

		if(score==0){
			sk=-1;
		}else{
			sk=((score-1)*7)/32f;
		}
		red=type>=3;
	}
	public void redo(String s){
		tex.g2.setColor(Color.black);
		tex.g2.fillRect(0, 0,10,10);
		IFont.letter(s, tex.g2, 2, 1, 8);
		tex.renew();
	}

	public void render(float dx,float dy){
		super.render(dx,dy);


		//0.21875
		if(sk!=-1){
			Texture.bind(TextureCache.badge);
			GL11.glBegin(GL11.GL_QUADS);
			float di=depth+GI.subStackSize;
			float xi=x+dx+5;
			float xi2=xi+3.5f;
			float yi=(y+dy)-7;
			float yi2=yi-(red?6:3f);
			GL11.glTexCoord2f(sk, 0);
			GL11.glVertex3f(xi,yi,di);
			GL11.glTexCoord2f(sk,red?.75f:0.375f);
			GL11.glVertex3f(xi,yi2,di);
			GL11.glTexCoord2f(sk+.21875f, red?.75f:0.375f);
			GL11.glVertex3f(xi2,yi2,di);
			GL11.glTexCoord2f(sk+.21875f, 0);
			GL11.glVertex3f(xi2,yi,di);
			GL11.glEnd();

		}
	}
}
