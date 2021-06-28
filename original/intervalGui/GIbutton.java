package intervalGui;

import interval.Texture;

import org.lwjgl.opengl.GL11;

public class GIbutton extends GI {

	float subSx;
	//float subSy;
	boolean subbed=false;
	boolean regular=true;
	String sub;
	int type;
	float tx;
	float tx2;

	public GIbutton(){
		
	}
	public GIbutton(String a,String s,int type,float x,float y){
		super(a,x,y);
		addSub(s,type);
	}
	public GIbutton(String a,float x,float y){
		super(a,x,y);
	}

	public void setIcon(int i){
		float f[]=icon(i);
		tx=f[0];
		tx2=f[1];
	}
	public void addSub(String sub,int type){
		subbed=true;
		this.sub=sub;
		this.type=type;
		setIcon(type);
		subSx=FACTOR;
		//subSy=4;
	}
	public boolean check(float i,float j){
		mx=i;my=j;
		boolean boo=(i>x && i<x+sx  && j>y-sy && j<y);
		if(subbed){
			regular=boo;
			return (i>x && i<x+sx+subSx  && j>y-(sy) && j<y);
		}
		return boo;
	}
	public void render(float dx,float dy){
		if(!visible){
			return;
		}
		GL11.glPushMatrix();
		if(regular)
			GL11.glColor4f(selection.x,selection.y,selection.z,selection.vx);
		else
			GL11.glColor4f(colorNormal.x,colorNormal.y,colorNormal.z,colorNormal.vx);

		if(tex!=null)
			tex.bind();
		
		float Y=y+dy;
		float X=x+dx;

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

		if(subbed){
			if(!regular)
				GL11.glColor4f(selection.x,selection.y,selection.z,selection.vx);
			else
				GL11.glColor4f(colorNormal.x,colorNormal.y,colorNormal.z,colorNormal.vx);

			segment(dx,dy);
		}
		GL11.glPopMatrix();
	}
	
	public void segment(float dx,float dy){
		float Y=y+dy;
		float X=x+dx+sx;
		
		Texture.bind("buttons");
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(tx, 1);
		GL11.glVertex3f(X,Y-sy,depth);
		GL11.glTexCoord2f(tx, 0);
		GL11.glVertex3f(X,Y,depth);
		GL11.glTexCoord2f(tx2, 0);
		GL11.glVertex3f(X+subSx,Y,depth);
		GL11.glTexCoord2f(tx2, 1);
		GL11.glVertex3f(X+subSx,Y-sy,depth);
		GL11.glEnd();
	}
}
