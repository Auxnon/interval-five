package intervalGui;

import interval.Texture;
import org.lwjgl.opengl.GL11;


public class GIicon extends GItoggle {

	GIicon(String a, int[] i, float x, float y) {
		super(a, a, i, x, y);
		sx=FACTOR;
		sy=FACTOR;
	}
	public boolean check(float i,float j){
		mx=i;my=j;
		return (i>x && i<x+sx  && j>y-(sy) && j<y);
	}
	public void render(float dx,float dy){
		if(!visible){
			return;
		}
		float Y=y+dy;
		float X=x+dx;
		GL11.glColor4f(selection.x,selection.y,selection.z,selection.vx);
		Texture.bind("buttons");
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(tx, 1);
		GL11.glVertex3f(X,Y-sy,depth);
		GL11.glTexCoord2f(tx, 0);
		GL11.glVertex3f(X,Y,depth);
		GL11.glTexCoord2f(tx2, 0);
		GL11.glVertex3f(X+sx,Y,depth);
		GL11.glTexCoord2f(tx2, 1);
		GL11.glVertex3f(X+sx,Y-sy,depth);
		GL11.glEnd();
	}
	public String click(){
		mode++;
		if(mode>=modes.length)
			mode=0;
		setIcon(modes[mode]);
		return sub+modes[mode];
	}
	
	public String clickR(){
		mode--;
		if(mode<0)
			mode=modes.length-1;
		setIcon(modes[mode]);
		return sub+mode;
	}
}
