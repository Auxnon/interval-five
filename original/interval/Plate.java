package interval;

import org.lwjgl.opengl.GL11;

public class Plate {

	VecF pp[];

	//public Texture texture;
	//public float width,height,length=0.01f;
	public float sx,sy,ex,ey;
	
	public Plate(VecF ppi[]){
		pp= ppi;
	}
	public Plate(VecF p1,VecF p2,VecF p3,VecF p4){
		VecF ppi[]= {p1,p2,p3,p4};
		pp=ppi;
	}
	
	public Plate(VecF p1,VecF p2,VecF p3,VecF p4,int di){
		//D=di;
		VecF ppi[]= {p1,p2,p3,p4};
		pp=ppi;
	}
	
	int gh(){
		return ((int) Math.round(Math.random()));
	}
	public void computeTexCoord(boolean b,int px,int py,int wx,int wy){
		int MM=VGOraw.D;
		float mm=(float) MM*1f;
		float buffer=0.001f;
		float F;
		if(b){
			F=(float)MM*6f;
		}else{
			F=(float)MM*4f;
		}
		ex=(px/F);
		ex-=buffer;
		ey=(py/mm);
		ey-=buffer;
		
		sx=ex-(wx/F);
		
		sx+=buffer;
		sy=ey-(wy/mm);
		sy+=buffer;
	}

	/**
	 * Render Plate
	 */
	public void apply(){
		GL11.glNormal3f(pp[0].vx,pp[0].vy,pp[0].vz);
		
		GL11.glTexCoord2f(sx, sy);
		GL11.glVertex3f(pp[0].x,pp[0].y,pp[0].z);
		
		GL11.glTexCoord2f(ex, sy);
		GL11.glVertex3f(pp[1].x,pp[1].y,pp[1].z);
		
		GL11.glTexCoord2f(ex, ey);
		GL11.glVertex3f(pp[2].x,pp[2].y,pp[2].z);

		GL11.glTexCoord2f(sx, ey);
		GL11.glVertex3f(pp[3].x,pp[3].y,pp[3].z);
	}
	
	/*public void apply(){
		GL11.glNormal3f(pp[0].dx,pp[0].dy,pp[0].vz);
		
		GL11.glTexCoord2f(sy, sx);
		GL11.glVertex3f(pp[0].x,pp[0].y,pp[0].z);
		
		GL11.glTexCoord2f(sy, ex);
		GL11.glVertex3f(pp[1].x,pp[1].y,pp[1].z);
		
		GL11.glTexCoord2f(ey, ex);
		GL11.glVertex3f(pp[2].x,pp[2].y,pp[2].z);
		
		GL11.glTexCoord2f(ey, sy);
		GL11.glVertex3f(pp[3].x,pp[3].y,pp[3].z);
	}*/
	/**
	 * Render model fatter/rounder??
	 */
	public void applyBloat(float D,float Po,float x,float y,float z){
		GL11.glNormal3f(pp[0].vx,pp[0].vy,pp[0].vz);
		
		GL11.glTexCoord2f(sx, sy);
		P(0,D,Po,x,y,z);
		
		GL11.glTexCoord2f(ex, sy);
		P(1,D,Po,x,y,z);
		
		GL11.glTexCoord2f(ex, ey);
		P(2,D,Po,x,y,z);

		GL11.glTexCoord2f(sx, ey);
		P(3,D,Po,x,y,z);
	}
	public void translate(float x,float y,float z){
		for(int u=0;u<pp.length;u++){
			pp[u].add(x, y, z);
		}
	}
	
	public void P(int u,float D,float Po,float x,float y,float z){
		float xx=pp[u].x-x;
		float yy=pp[u].y-y;
		float  zz=pp[u].z-z;
		float rr=(float) Math.sqrt(xx*xx + yy*yy + zz*zz);
		float dr=(1-Po)+(Po*D/rr);
		GL11.glVertex3f(x+xx*dr,y+yy*dr,z+zz*dr);
	}
	
	public void splitTexture(float size,int indexX,int indexY){
		float ssz=1f/(6f*size);
		
		sx/=size;
		ex/=size;
		
		sy/=size;
		ey/=size;
		
		float half=1f/size;
		
		sy+=half*indexY;
		ey+=half*indexY;
		
		sx+=ssz*(indexX);
		ex+=ssz*(indexX);
	}
	public void reverse(){
		float tx=sx;
		//float ty=sy;
		sx=ex;//sy=ey;
		ex=tx;//ey=ty;
	}
	public Plate clone(){
		Plate p=new Plate(this.pp.clone());
		p.ex=ex;p.ey=ey;p.sx=sx;p.sy=sy;
		return p;
	}
	/*public void slideTexture(float size,int indexX,int indexY,int rot){
		float half=1f/size;
		float ssz=1f/(6f*size);
		sy+=half*indexY;
		ey+=half*indexY;
		
		sx+=ssz*(indexX);
		ex+=ssz*(indexX);
		
		switch(rot){
		case 1:  float ssy=sy;sy=ey;  ey=ssy;break; //ex=ssx;  float ssx=sx; sx=ex;
		case 2: break;
		case 3: break;
		}
	}*/
	
}
