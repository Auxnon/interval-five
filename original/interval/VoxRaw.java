package interval;

import org.lwjgl.opengl.GL11;

public class VoxRaw {
	float x,y,z;
	float ss=1;

	public float sx,sy,sz,ex,ey,ez;
	public int xep,yep,zep;
	public int dimx=1,dimy=1,dimz=1;
	public boolean absorbed=false;

	Vec f1n = new Vec(0,0,1);
	Vec f2n = new Vec(-1,0,0); //?????????
	Vec f3n = new Vec(0,0,-1);

	Vec f4n = new Vec(1,0,0);
	Vec f5n = new Vec(0,-1,0);
	Vec f6n = new Vec(0,1,0);

	Vec ver;
	Plate f1,f2,f3,f4,f5,f6;

	public VoxRaw(){

	}

	public VoxRaw(float isx,float isy,float isz,float iex,float iey,float iez){
		Register(isx,isy,isz,iex,iey,iez);
	}

	public void plusX(float iex,int ixep){
		xep=ixep;
		dimx++;
		Register(sx,sy,sz,iex,ey,ez);
	}
	public void plusY(float iey,int iyep){
		yep=iyep;
		//ms("hoop");
		dimy++;
		Register(sx,sy,sz,ex,iey,ez);
	}
	/*public void minusY(float isy,int iyep){
		//yep=iyep;

		dimy++;
		Register(sx,isy,sz,ex,ey,ez);
	}*/

	public void plusZ(float iez,int izep){
		zep=izep;
		dimz++;
		Register(sx,sy,sz,ex,ey,iez);
	}

	/*public void minusZ(float isz,int izep){
		//zep=izep;
		//
		dimz++;
		Register(sx,sy,isz,ex,ey,ez);
	}*/

	public void Register(float isx,float isy,float isz,float iex,float iey,float iez){
		int D=VGOraw.D;
		sx=isx;sy=isy;sz=isz;ex=iex;ey=iey;ez=iez;
		f1 = new Plate(new VecF(sx,sy,ez,f1n.x,f1n.y,f1n.z),new VecF(ex,sy,ez,f1n.x,f1n.y,f1n.z),new VecF(ex,ey,ez,f1n.x,f1n.y,f1n.z),new VecF(sx,ey,ez,f1n.x,f1n.y,f1n.z),D);//down
		f2 = new Plate(new VecF(ex,sy,sz,f2n.x,f2n.y,f2n.z),new VecF(ex,ey,sz,f2n.x,f2n.y,f2n.z),new VecF(ex,ey,ez,f2n.x,f2n.y,f2n.z),new VecF(ex,sy,ez,f2n.x,f2n.y,f2n.z),D); //right
		f3 = new Plate(new VecF(sx,sy,sz,f3n.x,f3n.y,f3n.z),new VecF(ex,sy,sz,f3n.x,f3n.y,f3n.z),new VecF(ex,ey,sz,f3n.x,f3n.y,f3n.z),new VecF(sx,ey,sz,f3n.x,f3n.y,f3n.z),D);//up
		f4 = new Plate(new VecF(sx,sy,sz,f4n.x,f4n.y,f4n.z),new VecF(sx,ey,sz,f4n.x,f4n.y,f4n.z),new VecF(sx,ey,ez,f4n.x,f4n.y,f4n.z),new VecF(sx,sy,ez,f4n.x,f4n.y,f4n.z),D); //left
		f5 = new Plate(new VecF(sx,sy,sz,f5n.x,f5n.y,f5n.z),new VecF(ex,sy,sz,f5n.x,f5n.y,f5n.z),new VecF(ex,sy,ez,f5n.x,f5n.y,f5n.z),new VecF(sx,sy,ez,f5n.x,f5n.y,f5n.z),D);
		f6 = new Plate(new VecF(sx,ey,sz,f6n.x,f6n.y,f6n.z),new VecF(ex,ey,sz,f6n.x,f6n.y,f6n.z),new VecF(ex,ey,ez,f6n.x,f6n.y,f6n.z),new VecF(sx,ey,ez,f6n.x,f6n.y,f6n.z),D);
	}


	public void polish(){
		ver = new Vec((Math.random()*2)-1f,(Math.random()*2)-1f,(Math.random()*2)-0.4f);
	}


	/*public void Combo(float xi,float yi,float zi){
		//xi,yi,zi
		f1 = new Plate(new Vec(x,y,z),new Vec(x+d+xi,y,z),new Vec(x+d+xi,y+d+yi,z),new Vec(x,y+d+yi,z));//down
		f2 = new Plate(new Vec(x+d+xi,y,z),new Vec(x+d+xi,y+d+yi,z),new Vec(x+d+xi,y+d+yi,z+d+zi),new Vec(x+d+xi,y,z+d+zi)); //right
		f3 = new Plate(new Vec(x,y,z+d+zi),new Vec(x+d+xi,y,z+d+zi),new Vec(x+d+xi,y+d+yi,z+d+zi),new Vec(x,y+d+yi,z+d+zi));//up
		f4 = new Plate(new Vec(x,y,z),new Vec(x,y+d+yi,z),new Vec(x,y+d+yi,z+d+zi),new Vec(x,y,z+d+zi)); //left
		f5 = new Plate(new Vec(x,y,z),new Vec(x+d+xi,y,z),new Vec(x+d+xi,y,z+d+zi),new Vec(x,y,z+d+zi)); //forward
		f6 = new Plate(new Vec(x,y+d+yi,z),new Vec(x+d+xi,y+d+yi,z),new Vec(x+d+xi,y+d+yi,z+d+zi),new Vec(x,y+d+yi,z+d+zi)); //back
	}*/

	/**
	 * @ Calculate how the texture should be applied to appropriately simulate the original single voxels
	 * @param
	 */
	public void computeTex(boolean b){
		int D=VGOraw.D;
		if(b){
			int d2= D*2;
			int d3=D*3;
			xep+=1;
			yep+=1;
			f3.computeTexCoord(b,d2+xep,yep,dimx,dimy); //up
			f1.computeTexCoord(b,d2+xep+d3,yep,dimx,dimy); //down
			f2.computeTexCoord(b,D+yep+d3,zep,dimy,dimz); //right// D2-YEP+1
			f4.computeTexCoord(b,D+yep,zep,dimy,dimz); //left  
			f5.computeTexCoord(b,xep+d3,zep,dimx,dimz); //forward
			f6.computeTexCoord(b,xep,zep,dimx,dimz); //back
		}else{
			int d2= D*2;
			xep+=1;
			yep+=1;
			f3.computeTexCoord(b,d2+xep,yep,dimx,dimy); //up
			f1.computeTexCoord(b,d2+xep,yep,dimx,dimy); //down
			f2.computeTexCoord(b,D+yep,zep,dimy,dimz); //right// D2-YEP+1
			f4.computeTexCoord(b,D+yep,zep,dimy,dimz); //left  
			f5.computeTexCoord(b,xep,zep,dimx,dimz); //forward
			f6.computeTexCoord(b,xep,zep,dimx,dimz); //back
		}
	}
	public void apply(){
		f1.apply();
		f2.apply();
		f3.apply();
		f4.apply();
		f5.apply();
		f6.apply();
	}
	public void applyD(float v,float H){
		GL11.glPushMatrix();
		float vv=-0.9f*v*v/2f;

		float xx=ver.x*v;
		float yy=ver.y*v;
		float zz=vv+(ver.z+H)*v;

		GL11.glTranslatef(xx, yy, zz);
		GL11.glBegin(GL11.GL_QUADS);
		f1.apply();
		f2.apply();
		f3.apply();
		f4.apply();
		f5.apply();
		f6.apply();
		GL11.glEnd();
		GL11.glPopMatrix();
	}

	public void translate(float xx,float yy,float zz){
		f1.translate(xx, yy, zz);
		f2.translate(xx, yy, zz);
		f3.translate(xx, yy, zz);
		f4.translate(xx, yy, zz);
		f5.translate(xx, yy, zz);
		f6.translate(xx, yy, zz);
	}

	/*public void textureSide(int from,int to,int rot){
		//int indexY;
		switch(to){
			case  0: f5.slideTexture(ss, from, 0,rot);
						f5.slideTexture(ss, 3+from, 0,rot);break;
			case  1: f4.slideTexture(ss, from, 0,rot);
						f2.slideTexture(ss, 3+from, 0,rot);break;
			default: f3.slideTexture(ss, from, 0,rot);
					f1.slideTexture(ss, 3+from, 0,rot);
		}
	}*/

	public void applyBloat(float D,float Po,float x,float y,float z){
		GL11.glPushMatrix();
		GL11.glBegin(GL11.GL_QUADS);
		f1.applyBloat(D,Po,x,y,z);
		f2.applyBloat(D,Po,x,y,z);
		f3.applyBloat(D,Po,x,y,z);
		f4.applyBloat(D,Po,x,y,z);
		f5.applyBloat(D,Po,x,y,z);
		f6.applyBloat(D,Po,x,y,z);
		GL11.glEnd();
		GL11.glPopMatrix();
	}

	public void splitTexture(float size,int indexX,int indexY,int indexZ){
		int sr=(int) size;
		if(sr>1){
			sr--;
		}
		
		/*if(sr==2){
			sr=1;
		}else if(sr==4){
			sr=3;
		}else if(sr==8){
			sr=7;
		}*/
		
		f6.splitTexture(size,indexX,indexZ); 
		f4.splitTexture(size,indexY+sr,indexZ); //1  3
		f3.splitTexture(size,indexX+2*sr,indexY); // 2  6

		f5.splitTexture(size,indexX+3*sr,indexZ);// 3 9
		f2.splitTexture(size,indexY+4*sr,indexZ); //4 12
		f1.splitTexture(size,indexX+5*sr,indexY);  //6 3
		
		f1.reverse();
		/*f6.splitTexture(size,indexX,indexZ); 
		f4.splitTexture(size,indexY+3,indexZ); //1  3
		f3.splitTexture(size,indexX+6,indexY); // 2  6

		f5.splitTexture(size,indexX+3*sr,indexZ);// 3 9
		f2.splitTexture(size,indexY+4*sr,indexZ); //4 12
		f1.splitTexture(size,indexX+5,indexY);  //6 3*/

	}
	public VoxRaw clone(){
		VoxRaw v=new VoxRaw();
		v.sx=sx;v.ex=ex;v.xep=xep;v.dimx=dimx;
		v.sy=sy;v.ey=ey;v.yep=yep;v.dimy=dimy;
		v.sz=sz;v.ez=ez;v.zep=zep;v.dimz=dimz;
		v.absorbed=absorbed;
		if(ver!=null)
			v.ver=ver.clone();
		v.f1=f1.clone();
		v.f2=f2.clone();
		v.f3=f3.clone();
		v.f4=f4.clone();
		v.f5=f5.clone();
		v.f6=f6.clone();
		return v;
	}

}
