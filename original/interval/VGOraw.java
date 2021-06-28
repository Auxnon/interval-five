package interval;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;
import org.lwjgl.opengl.GL11;

public class VGOraw extends VGO{
	public float sizex,sizey;
	VoxRaw pp[];

	float multiplier=1.0f;
	public static int D=16;
	float dd;
	//int combo=1;
	String param="";

	//int combo2=0;
	boolean failed=false;
	BufferedImage im3[];

	public VGOraw(float multiplier,String t1){
		Name=t1;
		this.multiplier=multiplier;
		texture=t1.substring(t1.lastIndexOf("/"));
		BufferedImage bb=FileManager.loadImage(t1);
		if(bb!=null){
			//FileManager.saveImage(t1,bb);
			carveInit(bb);
			failed=false;
		}else{
			failed=true;
		}
	}

	public void ground(){
		if(mz!=-0.1875f){ //-0.1875f
			translate(pp,0,0,-mz);
			top-=mz;
			mz=-0.1875f;
		}
	}
	public void combine(VGOraw vgo,short i,short j, short k,boolean smooth){
		if(smooth){
			ground();
		}

		float zz=((k>0)?(top-(smooth?vgo.mz:0)):(k<0)?(vgo.top-(smooth?mz:0)):(mz));
		float sX=sizex+vgo.sizex;
		float sY=sizey+vgo.sizey;
		translate(vgo.pp
				,(-(sX))*i
				,(-(sY))*j
				,(zz)
				);
		
		if(i!=0){
			sizex+=vgo.sizex*2;
			if(i<0)
				mx+=vgo.sizex*2;
			else
				mx-=vgo.sizex*2;
		}
		
		if(j!=0){
			sizey+=vgo.sizey*2;
			if(j<0)
				my-=sY/2f;
			else
				my+=sY/2f;
		}
		
		newSize();
		if(k>0)
			top+=vgo.top-(smooth?vgo.mz:0);
		else if(k<0)
			mz-=vgo.top-vgo.mz;
		combine(vgo);
	}
	public void combine(VGOraw vgo){
		//combo++;
		int s2=vgo.pp.length;
		//Message.m(this,"tex"+vgo.texture);
		if(!texture.equals(vgo.texture))
			param+=(pp.length+":"+vgo.texture+";");

		VoxRaw[] vv=new VoxRaw[pp.length+s2];
		for(int u=0;u<pp.length;u++){
			vv[u]=pp[u];
		}
		for(int h=0;h<s2;h++){
			vv[pp.length+h]=vgo.pp[h];
		}
		if(vgo.top>top)
			top=vgo.top;

		if(mz>vgo.mz)
			mz=vgo.mz;
		pp=vv;
	}
	public void save(String der){
		this.ground();
		FileManager.saveToVGO(this,der);
	}
	public VGOraw(float multiplier,String t1,String t2,String t3){
		Name=t1;
		this.multiplier=multiplier;
		BufferedImage b1= FileManager.loadImage(t1);
		BufferedImage b2=FileManager.loadImage(t2);
		BufferedImage b3=FileManager.loadImage(t3);
		int w=b1.getWidth();
		BufferedImage bb=new BufferedImage(w*4,b1.getHeight(),BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2=bb.createGraphics();
		g2.drawImage(b1,0,0,null);
		g2.drawImage(b2,w,0,null);
		g2.drawImage(b3,w*2,0,null);
		texture=FileManager.saveImage(t1,bb);
		carveInit(bb);
	}
	public VGOraw(float multiplier,String t1,String t2,String t3,String t4){
		this(multiplier,t1,t2,t3,t4,t2,t3);
	}
	public VGOraw(float multiplier,String t1,String t2,String t3,String t4,String t5){
		this(multiplier,t1,t2,t3,t4,t5,t3);
	}
	public VGOraw(float multiplier,String t1,String t2,String t3,String t4,String t5,String t6){
		Name=t1;
		this.multiplier=multiplier;
		BufferedImage b1=FileManager.loadImage(t1);
		BufferedImage b2=FileManager.loadImage(t2);
		BufferedImage b3=FileManager.loadImage(t3);
		BufferedImage b4;
		BufferedImage b5;
		BufferedImage b6;
		if(t4==t1){
			b4=b1;
		}else{
			b4=FileManager.loadImage(t4);
		}
		if(t5==t2){
			b5=b2;
		}else{
			b5=FileManager.loadImage(t5);
		}
		if(t6==t3){
			b6=b3;
		}else{
			b6=FileManager.loadImage(t6);
		}
		int w=b1.getWidth();
		BufferedImage bb=new BufferedImage(w*6,b1.getHeight(),BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2=bb.createGraphics();
		g2.drawImage(b1,0,0,null);
		g2.drawImage(b2,w,0,null);
		g2.drawImage(b3,w*2,0,null);
		g2.drawImage(b4,w*3,0,null);
		g2.drawImage(b5,w*4,0,null);
		g2.drawImage(b6,w*5,0,null);
		texture=FileManager.saveImage(t1,bb);
		carveInit(bb);//new BufferedImage[]{ b1,b2,b3,b4,b5,b6});
	}

	public void carveInit( BufferedImage ima){
		D=ima.getHeight();
		boolean b=ima.getWidth() !=D*4;
		int NN=3;
		if(b){
			NN=6;
		}
		if(D>16){
			System.out.println("derk "+D);
			subCarve(NN,D/16,ima);
			finish();
			return;
		}
		dd=multiplier/D;
		im3 = new BufferedImage [NN];
		for(int ii=0;ii<NN;ii++){
			im3[ii]=new BufferedImage(D, D, BufferedImage.TYPE_INT_ARGB_PRE);
			im3[ii].createGraphics().drawImage(ima, -ii*(D), 0, null);
		}
		carving(false);
		finish();
	}
	public void subCarve(int n,int u,BufferedImage ima){
		int doubled=u*u;
		int pbSize=n*doubled;
		D=16;
		dd=multiplier/D;
		im3 = new BufferedImage [n];
		BufferedImage picBuffer[] = new BufferedImage [pbSize];
		for(int ii=0;ii<n;ii++){
			im3[ii]=new BufferedImage(D, D, BufferedImage.TYPE_INT_ARGB_PRE);
			im3[ii].createGraphics().drawImage(ima, -ii*(D), 0, null);
			for(int jj=0;jj<u;jj++){
				for(int kk=0;kk<u;kk++){
					int f=(ii*doubled)+(jj*u) +kk;
					picBuffer[f]=new BufferedImage(D, D, BufferedImage.TYPE_INT_ARGB_PRE);
					picBuffer[f].createGraphics().drawImage(ima, -((ii*D*u)+kk*D ), -jj*D, null);
				}
			}
		}
		float factr=1f/(float)(u*u*u);
		float U=0;
		ArrayList<VoxRaw> voxs=new ArrayList<VoxRaw>();
		for(int bz=0;bz<u;bz++){
			for(int bx=0;bx<u;bx++){
				for(int by=0;by<u;by++){
					U++;
					Main.loader.status(U*factr);
					int wx=bx+(bz*u);
					int wy=(by)+(bz*u);
					int wz=bx+(by*u);

					im3=new BufferedImage[]{
							picBuffer[wx],
							picBuffer[wy+doubled],
							picBuffer[wz+doubled*2],

							picBuffer[wx+doubled*3], //bx+
							picBuffer[wy+doubled*4], //by2+
							picBuffer[wz+doubled*5]};//bz*by*u+

					carving(false);
					if(pp.length>0){
						translate(pp,-multiplier*bx,multiplier*by,-multiplier*bz);
						splitTexture(pp,u,bx,by,bz);
						voxs.addAll(Arrays.asList(pp));
					}
				}
			}
		}
		pp= voxs.toArray(pp);
		translate(pp,0,0,multiplier); //size/u,-size/u,size
		multiplier*=u;
		
		ms("final multi-chunk voxel count for "+Name+":"+pp.length);

	}

	public static void translate(VoxRaw[] voxx,float xx,float yy,float zz){
		for(int u=0;u<voxx.length;u++){
			voxx[u].translate(xx, yy, zz);
		}
	}

	public static void splitTexture(VoxRaw[] voxx,int l,int xx,int yy,int zz){
		for(int u=0;u<voxx.length;u++){
			voxx[u].splitTexture(l,xx, yy, zz);
		}
	}
	
	public void finish(){
		float leastX=9000;
		float mostX=-9000;
		float leastY=9000;
		float mostY=-9000;
		float leastZ=9000;
		float mostZ=-9000;
		for(VoxRaw v:pp){
			if(v.f2.pp[0].x<leastX){
				leastX=v.f2.pp[0].x;
			}
			if(v.f1.pp[0].x>mostX){
				mostX=v.f1.pp[0].x;
			}
			if(v.f1.pp[0].y<leastY){
				leastY=v.f1.pp[0].y;
			}
			if(v.f1.pp[2].y>mostY){
				mostY=v.f1.pp[2].y;
			}
			
			if(v.f1.pp[0].z<leastZ){
				leastZ=v.f1.pp[0].z;
			}
			if(v.f2.pp[0].z>mostZ){
				mostZ=v.f2.pp[0].z;
			}
		}
		//ms("size: "+leastX+" to "+mostX+" , "+leastZ+" to "+mostZ);
		sizex=(mostX-leastX)/2f;
		sizey=(mostY-leastY)/2f;

		mx=(leastX+(sizex));
		my=(mostY-(sizey));
		mz=leastZ;
		top=mostZ;
	
	}
	public void carveInit(BufferedImage im[]){
		D=im[0].getHeight();
		dd=multiplier/D;

		BufferedImage imi;
		boolean b=im.length>3;

		if(b){
			imi=new BufferedImage(D*6, D, BufferedImage.TYPE_INT_ARGB_PRE);
		}else{
			imi=new BufferedImage(D*4, D, BufferedImage.TYPE_INT_ARGB_PRE);
		}

		for(int bfy=0;bfy<D;bfy++){
			for(int bfx=0;bfx<D;bfx++){
				imi.setRGB(bfx, bfy, im[0].getRGB(bfx,bfy));
				imi.setRGB(bfx+D, bfy, im[1].getRGB(bfx,bfy));
				imi.setRGB(bfx+(D*2), bfy, im[2].getRGB(bfx,bfy));

				if(b){
					imi.setRGB(bfx+(D*3), bfy, im[3].getRGB(bfx,bfy));		
					imi.setRGB(bfx+(D*4), bfy, im[4].getRGB(bfx,bfy));
					imi.setRGB(bfx+(D*5), bfy, im[5].getRGB(bfx,bfy));

				}else{
					imi.setRGB(bfx-1+D*3, bfy, -16777216);
				}
			}
		}

		int yy=Name.indexOf("png")-2;
		try {
			ImageIO.write(imi, "png", new File(Name.substring(0, yy)+"Tex.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		im3 = new BufferedImage [im.length];
		for(int ii=0;ii<im.length;ii++){
			im3[ii]=im[ii];
		}
		carving(false);
		finish();
	}
	public void carving(boolean sub){

		boolean b=im3.length>3;

		int x,y,z;
		VoxRaw voxA[] = new VoxRaw[D*D*D];
		int N=0;

		float sx,sy,sz,ex,ey,ez;
		sx=sy=sz=999f;
		ex=ey=ez=-1f;
		for(int zi=0;zi<D;zi++){
			for(int yi=0;yi<D;yi++){
				for(int xi=0;xi<D;xi++){
					x=xi;y=yi;z=zi+1;

					//F front
					//U up
					//R right

					int F=im3[0].getRGB(xi, zi);
					int R=im3[1].getRGB(yi, zi);
					int U=im3[2].getRGB(xi,yi ); //D-yi-1

					int NUM=-1;//-16777216;
					if(F==NUM || R==NUM || U==NUM){
						//blank spot!
					}else{
						if(x<sx){
							sx=x;
						}
						if(y<sy){
							sy=y;
						}
						if(z<sz){
							sz=z;
						}

						if(x>ex){
							ex=x;
						}
						if(y>ey){
							ey=y;
						}
						if(z>ez){
							ez=z;
						}

						//fill me in!
						float xe=(D-x)*dd;
						float ye=y*dd;
						float ze=(D-z)*dd;

						voxA[N]=new VoxRaw(xe,ye,ze+dd,xe-dd,ye+dd,ze);

						
						voxA[N].xep=x;
						voxA[N].yep=(y);
						voxA[N].zep=z;

						N+=1;
					}
				}
			}
		}
		N--;

		refine(N,voxA,b,sub);
		if(pp.length>0){
			//ms("final voxel count for "+Name+":"+pp.length);
		}else{
			ms(Name+" is Empty");
		}
	}

	public static void ms(String s){
		Message.m(VGOraw.class,s);
	}
	public void refine(int N,VoxRaw[] voxA,boolean b,boolean sub){
		//boolean H=false;
		if(UserData.isVoxelRefine()&&!sub){
			// COMBINE X NEIGHBORS
			int nyeh=N;
			for(int i=0;i<(N+1);i++){
				if(!voxA[i].absorbed){
					for(int k=0;k<(N+1);k++){
						if(!voxA[k].absorbed && i!=k){
							if((voxA[k].sy == voxA[i].sy)&&(voxA[k].sz == voxA[i].sz) && Math.abs(voxA[k].xep -voxA[i].xep)==1 ){
								voxA[k].absorbed=true;
								nyeh--;
								voxA[i].plusX(voxA[k].ex,voxA[k].xep);
							}
						}
					}
				}
			}
			//------------
			// COMPRESS ARRAY, REMOVING ABSORBED (DEAD) VOXELS
			VoxRaw voxx[] = new VoxRaw[nyeh+1];
			int inn=0;
			for(int ppi=0;ppi<(N+1);ppi++){
				if(!voxA[ppi].absorbed){
					voxx[inn]=voxA[ppi];
					inn++;
				}
			}
			//---------
			//ms("after X:"+(nyeh+1));
			// COMBINE Y NEIGHBORS
			int yin=nyeh;
			for(int i=0;i<(nyeh+1);i++){
				if(!voxx[i].absorbed){
					for(int k=0;k<(nyeh+1);k++){
						if(!voxx[k].absorbed && i!=k){
							if((voxx[k].sx == voxx[i].sx)&&(voxx[k].sz == voxx[i].sz) && (voxx[k].dimx == voxx[i].dimx)&& Math.abs(voxx[k].yep -voxx[i].yep)==1 ){
								voxx[k].absorbed=true;
								yin--;

								voxx[i].plusY(voxx[k].ey,voxx[k].yep);

							}
						}
					}
				}
			}
			//-----------
			// COMPRESS ARRAY, REMOVING ABSORBED (DEAD) VOXELS 2nd time
			VoxRaw voxy[] = new VoxRaw[yin+1];
			int iny=0;
			for(int ppi=0;ppi<(nyeh+1);ppi++){
				if(!voxx[ppi].absorbed){
					voxy[iny]=voxx[ppi];
					iny++;
				}
			}
			//---------
			//ms("after Y:"+(yin+1));
			// COMBINE Z NEIGHBORS
			int zin=yin;
			for(int i=0;i<(yin+1);i++){
				if(!voxy[i].absorbed){
					for(int k=0;k<(yin+1);k++){
						if(!voxy[k].absorbed && i!=k){
							if((voxy[k].sx == voxy[i].sx)&&(voxy[k].sy == voxy[i].sy) && (voxy[k].dimx == voxy[i].dimx) && (voxy[k].dimy == voxy[i].dimy) && Math.abs(voxy[k].zep -voxy[i].zep)==1 ){
								voxy[k].absorbed=true;
								zin--;
								voxy[i].plusZ(voxy[k].ez,voxy[k].zep);
							}
						}
					}
				}
			}
			//-----------
			//ms("tempy: "+tempy);
			// COMPRESS ARRAY, REMOVING ABSORBED (DEAD) VOXELS final time
			pp = new VoxRaw[zin+1];
			int inz=0;
			for(int ppi=0;ppi<(yin+1);ppi++){
				if(!voxy[ppi].absorbed){
					pp[inz]=voxy[ppi];
					pp[inz].computeTex(b);
					inz++;
				}
			}
			//count=zin+1;
		}else {
			VoxRaw voxx[] = new VoxRaw[N+1];
			int inn=0;
			for(int ppi=0;ppi<(N+1);ppi++){
				if(!voxA[ppi].absorbed){
					voxx[inn]=voxA[ppi];
					voxx[inn].computeTex(b);
					inn++;
				}
			}
			pp= voxx;
		}
	}
	public void render(float r){
		Texture.bind(texture);
		renderBlank(r);
	}
	public void renderBloat(float r,float DD,float Po){
		renderBloat(r,DD,Po,mx,my,mz); //mz+midz
	}

	public void renderBloat(float r,float DD,float Po,float x,float y,float z){
		Texture.bind(texture);
		GL11.glRotatef(r, 0, 0, 1);
		GL11.glTranslatef(-mx,-my,-mz*DD);
		for( int i=0;i<pp.length;i++){
			pp[i].applyBloat(DD,Po,x,y,z);
		}
	}

	public void render(float r,float v,float H){
		Texture.bind(texture);
		GL11.glRotatef(r, 0, 0, 1);
		GL11.glTranslatef(-mx,-my,-mz);
		for( int i=0;i<pp.length;i++){
			pp[i].applyD(v,H);
		}
	}

	public void renderS(float F,float r){
		Texture.bind(texture);
		float fo=1f-F;
		GL11.glRotatef(r, 0, 0, 1);
		GL11.glTranslatef(mx*F,my*F,-mz);
		GL11.glScalef(fo, fo, 1+F*2);
		GL11.glBegin(GL11.GL_QUADS);
		for( int i=0;i<pp.length;i++){
			pp[i].apply();
		}
		GL11.glEnd();
	}
	public void renderF(boolean n,float r){
		if(!n){
			Texture.bind(0);
		}else{
			Texture.bind(texture);
		}
		renderBlank(r);
	}

	public void renderPre(float r){
		GL11.glRotatef(r, 0, 0, 1);
		GL11.glTranslatef(-mx,-my,-mz);
	}
	void renderBlank(float r){
		GL11.glBegin(GL11.GL_QUADS);
		for( int i=0;i<pp.length;i++){
			pp[i].apply();
		}	
		GL11.glEnd();
	}

}
