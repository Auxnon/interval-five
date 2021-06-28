package interval;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Random;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.EXTDrawInstanced;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.EXTBindableUniform;

public class Land {

	public static final short grass1=0;
	public static final short grass2=1;
	public static final short grass3=2;
	public static final short grass4=3;
	public static final short dirt=4;
	public static final short woodLeaves1=5;
	public static final short woodLeaves2=6;
	public static final short woodLeaves3=7;
	public static final short mud=8;
	public static final short ash=9;
	public static final short silt=10;
	public static final short sand=11;
	public static final short sandstone=12;
	public static final short grassyStone=13;
	public static final short softStone=14;
	public static final short stone=15;
	public static final short hardStone=16;
	public static final short granite=17;
	public static final short salt=18;
	public static final short death1=19;
	public static final short death2=20;
	public static final short ore=21;
	public static final short marble=22;
	public static final short pumice=23;
	public static final short concrete=24;
	public static final short steel=25;
	public static final short aluminum=26;
	public static final short rust=27;
	public static final short wood=28;
	public static final short rotWood=29;
	public static final short brick1=30;
	public static final short brick2=31;
	public static final short wornBrick1=32;
	public static final short wornBrick2=33;
	public static final short stoneBrick1=34;
	public static final short stoneBrick2=35;
	public static final short wornStoneBrick1=36;
	public static final short wornStoneBrick2=37;
	public static final short ultraMetal=38;
	public static final short crystal1=39;
	public static final short crystal2=40;
	public static final short permaRed=41;
	public static final short permaBlue=42;
	public static final short permaGray=43;

	public static final short time250=44;
	public static final short time500=45;
	public static final short time750=46;
	public static final short time1000=47;


	public static final short S1018=44; //low carbon steel
	public static final short S1045=44; // brittler
	public static final short S1095=44;

	static int squareRef[];
	static HashMap<Integer,Integer> squareBase;
	static{
		squareBase = new HashMap<Integer,Integer>(47);
		squareRef = new int[47];
		for(int i=0;i<47;i++){
			float ff[]=color(i);
			int r=(int)(0xff*ff[0]);
			int g=(int)(0xff*ff[1]);
			int b=(int)(0xff*ff[2]);
			int rgb=(((r<<8) +g)<<8) +b;
			squareRef[i]=rgb;
			int dd=(rgb-(0xffffff+1));
			squareBase.put(dd, i);
		}

	}

	public int typeByColor(int i){
		int h=0;
		
		Integer hh=squareBase.get(i);
		if(hh!=null){
			h=hh;
			//Message.m(this,"outter "+h);
		}
		
		
		return h;
	}

	public int colorOut(int i){
		switch(i){
		case -7745280:return grass1; //2
		case -11949568:return grass1; //3
		case -7552768:return grass1; //4
		case -9595392:return grass1;
		case -11260662:return dirt;
		case -2895418:return softStone;
		case -7566199:return hardStone;
		case -2902148:return sand;
		case -12967156:return mud;
		case -8616818:return steel;

		default:return grass1;
		}
	}



	public static float[] color(int i){
		switch(i){
		case grass1:return new float[]{0.43f,0.59f,0.042f};
		case grass2:return new float[]{0.54f,0.82f,0.042f};
		case grass3:return new float[]{0.29f,0.67f,0.042f};
		case grass4:return new float[]{0.55f,0.76f,0.042f};
		case dirt:return new float[]{0.33f,0.18f,0.04f};
		case woodLeaves1: return new float[]{0.77f,0.52f,0.27f};
		case woodLeaves2: return new float[]{0.65f,0.53f,0.16f};
		case woodLeaves3: return new float[]{0.82f,0.59f,0.20f};
		case mud: return new float[]{0.23f,0.14f,0.05f};
		case ash: return new float[]{0.25f,0.21f,0.22f};
		case silt: return new float[]{0.45f,0.40f,0.29f};

		case sand: return new float[]{0.83f,0.72f,0.49f};
		case sandstone: return new float[]{0.74f,0.62f,0.51f};
		case grassyStone: return new float[]{0.48f,0.53f,0.45f};
		case softStone: return new float[]{0.83f,0.82f,0.78f};
		case stone: return new float[]{0.65f,0.65f,0.64f};
		case hardStone: return new float[]{0.55f,0.55f,0.54f};

		case granite: return new float[]{0.18f,0.13f,0.15f};
		case salt: return new float[]{0.87f,0.88f,0.90f};

		case death1: return new float[]{0.04f,0.04f,0.22f};
		case death2: return new float[]{0.02f,0.02f,0.01f};
		case ore: return new float[]{0.55f,0.49f,0.38f};
		case marble: return new float[]{0.67f,0.67f,0.63f};
		case pumice: return new float[]{0.32f,0.26f,0.25f};
		case concrete: return new float[]{0.72f,0.67f,0.62f};

		case steel: return new float[]{0.49f,0.52f,0.56f};
		case aluminum: return new float[]{0.77f,0.80f,0.87f};
		case rust: return new float[]{0.84f,0.40f,0.22f};

		case wood: return new float[]{0.74f,0.62f,0.40f};
		case rotWood: return new float[]{0.45f,0.45f,0.34f};


		case brick1: return new float[]{0.64f,0.24f,0.15f};
		case brick2: return new float[]{0.54f,0.18f,0.10f};
		case wornBrick1: return new float[]{0.57f,0.36f,0.25f};
		case wornBrick2: return new float[]{0.48f,0.27f,0.15f};
		case stoneBrick1: return new float[]{0.43f,0.43f,0.46f};
		case stoneBrick2: return new float[]{0.41f,0.35f,0.30f};
		case wornStoneBrick1: return new float[]{0.34f,0.31f,0.30f};
		case wornStoneBrick2: return new float[]{0.35f,0.28f,0.22f};


		case ultraMetal: return new float[]{0.33f,0.27f,0.49f};
		case crystal1: return new float[]{0.58f,0.76f,0.75f};
		case crystal2: return new float[]{0.49f,0.91f,0.87f};
		case permaRed: return new float[]{0.75f,0.20f,0.22f};
		case permaBlue: return new float[]{0.35f,0.49f,0.63f};
		case permaGray: return new float[]{0.53f,0.49f,0.55f};

		default:return new float[]{.9f,.0f,.1f};
		}
	}


	public boolean hardness(int i,int ease){
		i=baseType(i);
		int F=1;
		switch(i){
		case grass1:F=5;break;
		case dirt:F=3;break;
		case hardStone:F=700;break;
		case stone:F=100;break;
		case softStone:F=30;break;
		case grassyStone:F=20;break;
		case silt:F=10;break;
		case brick1:F=400;break;
		case wornBrick1:F=40;break;
		case aluminum:F=150;break;
		case steel:F=600;break;
		case permaRed:return false;
		case time250:return false;
		case granite:F=140;break;
		case crystal1:F=700;break;
		default:return true;
		}

		return r.nextInt(1+(F>>ease))==0;
	}

	public int convert(int i){
		//i=baseType(i);
		switch(i){
		//case 1:return 0;
		case dirt:return subType(Main.level.getStats().getCurrentBlock());
		case grass1:return dirt;
		case grass2:return dirt;
		case grass3:return dirt;
		case grass4:return dirt;
		case ash:return dirt;
		case silt:return dirt;
		case salt:return sand;
		case sand:return silt;
		case sandstone:return sand;
		case woodLeaves1:return dirt;
		case woodLeaves2:return dirt;
		case woodLeaves3:return dirt;
		case mud:return dirt;
		case grassyStone:return grass1+r.nextInt(4);
		case hardStone:return stone;
		case softStone:return grassyStone;
		case stone:return softStone;
		case brick1:return wornBrick1;
		case brick2:return wornBrick2;
		case wornBrick1:return silt;
		case wornBrick2:return ash;
		case ore: return softStone;
		case steel:return rust;
		case aluminum:return rust;
		case rust:return dirt;
		case death1:return salt;
		case death2:return steel;
		case granite:return stone;
		case time1000:return time750;
		case time750:return time500;
		case time500:return time250;
		case time250:return dirt;
		case crystal1:return ore;
		default:return i;
		}
	}

	public int longevity(int i){
		i=baseType(i);
		switch(i){
		case steel:return ((r.nextInt(5)==0)?r.nextInt(10):0);
		case aluminum:return r.nextInt(5)==0?1:0;
		case dirt: return r.nextInt(4)<<4;
		case grass1: return r.nextInt(3)<<3;
		case mud: return r.nextInt(5)<<2;
		case ash: return r.nextInt(4)<<6;
		case rust: return r.nextInt(2)+2;
		case silt: return r.nextInt(5)<<5;
		case sand:return r.nextInt(4)==0?r.nextInt(4):0;
		case sandstone:return r.nextInt(4);
		case hardStone: return (r.nextInt(400)==0?25:0);
		case stone:return (r.nextInt(100)==0?r.nextInt(15)+15:0);
		case softStone :return r.nextInt(30)==0?r.nextInt(8)+5:0;
		case grassyStone :return r.nextInt(15)==0?r.nextInt(5)+3:0;
		case death1:return r.nextInt(6)==0?1:0;
		case salt:return r.nextInt(7)==0?1:0;
		case woodLeaves1: return r.nextInt(5)<<3;
		case brick1: return r.nextInt(3)==0?4:0;
		case wornBrick1: return r.nextInt(4)==0?2:0;
		case ore: return r.nextInt(30)==0?32:0;
		case granite: return r.nextInt(20)==0?16:0;
		case crystal1: return r.nextInt(400)==0?128:0;
		case time250:return 1;

		default:return -1;
		}
	}
	public int baseType(int i){
		if(i>=grass1 && i<=grass4){
			return grass1;
		}
		if(i>=woodLeaves1 && i<=woodLeaves3){
			return woodLeaves1;
		}
		if(i==death1 || i==death2){
			return death1;
		}
		if(i==brick1 || i==brick2){
			return brick1;
		}
		if(i==wornBrick1 || i==wornBrick2){
			return wornBrick1;
		}
		if(i==stoneBrick1 || i==stoneBrick2){
			return stoneBrick1;
		}
		if(i==wornStoneBrick1 || i==wornStoneBrick2){
			return wornStoneBrick1;
		}

		if(i==crystal1 || i==crystal2){
			return crystal1;
		}
		if(i>=permaRed && i<=permaGray){
			return permaRed;
		}
		if(i>=time250 && i<=time1000){
			return time250;
		}
		return i;
	}


	public int subType(int i){
		if(i>=grass1 && i<=grass4){
			return grass1+r.nextInt(4);
		}
		if(i>=woodLeaves1 && i<=woodLeaves3){
			return woodLeaves1+r.nextInt(3);
		}
		if(i==death1 || i==death2){
			return death1+r.nextInt(2);
		}
		if(i==brick1 || i==brick2){
			return brick1+r.nextInt(2);
		}
		if(i==wornBrick1 || i==wornBrick2){
			return wornBrick1+r.nextInt(2);
		}
		if(i==stoneBrick1 || i==stoneBrick2){
			return stoneBrick1+r.nextInt(2);
		}
		if(i==wornStoneBrick1 || i==wornStoneBrick2){
			return wornStoneBrick1+r.nextInt(2);
		}
		if(i==crystal1 || i==crystal2){
			return crystal1+r.nextInt(2);
		}
		return i;
	}

	public boolean isSame(int i,int j){
		return baseType(i)==baseType(j);
	}
	
	
	//TODO land variables

	byte ALL[][][];
	byte waterChunk[];
	float grasses[];

	public LandModel segment[][];
	public static ModelTerraReference modelRef[];
	public static ModelTerraBuffer terraBase;
	public ModelTerraBuffer grassModel;

	boolean changeHeight=false;

	boolean changeType=false;

	public Random r;
	
	boolean corrupted=false;

	int preFrame=0;
	int FRAME=0;

	int bit;
	int size;
	public static int current=0;
	public Topographic topography;

	int segSize;
	int viewModels;
	public Land(){
		corrupted=false;
		init();
		remakeModelCache();
	}

	public void remakeModelCache(){
		Message.addPass("fresh land model cache");
		int sss=segSize*segSize;
		if(sss<UserData.getLandModels()){
			viewModels=sss;
		}else{
			viewModels=UserData.getLandModels();
		}

		//terraBase=Model.createMap();
		modelRef=new ModelTerraReference[viewModels];
		for(int i=0;i<viewModels;i++){
			terraBase=Model.createMap();
			modelRef[i]=ModelTerraReference.dupe(terraBase);
			
			//model[i]=Model.createMap(i,i);
			//model[i].coordX=i;
			//model[i].coordY=i;
		}
	}
	public void renewModels(){
		//for(int i=0;i<viewModels;i++){
			//model[i].used=false;
		//}
	}
	public void init(){
		size=Main.level.getStats().getSize()*Main.level.getStats().getSize()*4;
		topography=new Topographic();
		if(Main.level.getStats().isNoTerrainAge() ||!Main.level.getStats().isInterval()){
			try{
			ALL=new byte[1][4][size];
			}catch(OutOfMemoryError error){
				
				corrupted=true;
				return;
			}
		}else{
			ALL=new byte[Main.level.getStats().getCapTerra()+1][4][size];
		}
		waterChunk= new byte[size];
		r= new Random();
		bit=(int) (Math.log10(Main.level.getStats().getSize())/Math.log10(2));

		segSize=Main.level.getStats().getSize()/32;
		segment = new LandModel[segSize][segSize];
		int sss=segSize*segSize;
		if(sss<UserData.getLandModels()){
			viewModels=sss;
		}else{
			viewModels=UserData.getLandModels();
		}

		for(int i=0;i<segSize;i++){
			for(int j=0;j<segSize;j++){
				segment[i][j]=new LandModel(i,j);//Model.createMap(i,j);
			}
		}

		if(UserData.fancyGrass){
			grassModel=Model.createGrassModel();
			grasses=new float[1600];
			defaultGeoBatch();
			refreshGeoBatch();
		}
	}

	public void defaultGeoBatch(){
		//	x=0;
		int i=0;
		for(int x=0;x<20;x++){
			for(int y=0;y<20;y++){
				grasses[i]=x;
				grasses[i+1]=y;
				grasses[i+2]=2;
				grasses[i+3]=0;
				i+=4;
			}
		}

	}

	public void refreshGeoBatch(){
		/*if(UserData.fancyGrass){
			//TODO geobatch
			grasses[0]=0;
			grasses[1]=0;
			grasses[2]=0;
			grasses[3]=0;
			FloatBuffer cenb= (FloatBuffer) BufferUtils.createFloatBuffer(grasses.length).put(grasses).flip();
			GL15.glBindBuffer(EXTBindableUniform.GL_UNIFORM_BUFFER_EXT, Main.technicalModel.centr);
			GL15.glBufferData(EXTBindableUniform.GL_UNIFORM_BUFFER_EXT, cenb,GL15.GL_STATIC_READ);
		}*/
	}
	////////
	/////////
	//0 type, 1 is height, 2 is age, 3 is elevation

	public void Set(int frame,int x,int y,int z,int type){
		ALL[frame][z][(((x<<bit)+y)) ]=(byte)(type-128); //<<2
		int xxx=x>>5;
		int yyy=y>>5;
			segment[xxx][yyy].equilibrium=false;
			segment[xxx][yyy].waterStill=false;
			segment[xxx][yyy].updatedC=true;
			if(z==0){

			}else{
				segment[xxx][yyy].updatedV=true;
			}
	}

	public int Get(int frame,int x,int y,int z){
		return ALL[frame][z][(((x<<bit)+y)) ]+128;
	}



	public float getHigh(int land,int x,int y){
		float ffo=(float) getHeight(land,x,y)/5f;
		//System.out.println(ffo);
		if(ffo<=0.2f){
			ffo=-100;
		}
		return ffo;
	}
	
	public float getWaterHigh(int x,int y){
		float ffo=(float) getWater(x,y)/5f;
		//System.out.println(ffo);
		if(ffo<=0.2f){
			//ffo=-100;
		}
		return ffo;
	}
	public float getHigh(int x,int y){
		return  getHigh(Main.world.timeline.getTerra(),x,y);
	}
	public int getHeight(int land,int x,int y){
		return Get(land,x,y,1);
	}
	public void setHeight(int land,int x,int y,int height){
		int e=getElevation(land,x,y);
		if(height<e){
			height=e;
		}
		Set(land,x,y,1,height);
		changeHeight=true;
		grassCheck(land,x,y);
	}



	public int getType(int land,int x,int y){
		return Get(land,x,y,0);
	}
	public void setType(int land,int x,int y,int type){
		Set(land,x,y,0,type);
		setAge(land,x,y,0);
		changeType=true;
		grassCheck(land,x,y,type);
	}
	public void grassCheck(int land,int x,int y){
		if(UserData.fancyGrass){

			if(Main.rand.nextInt(5)==0){

				if(baseType(getType(land,x,y))==grass1){
					int ind=Main.rand.nextInt(400);
					int inde=ind*4;
					grasses[inde]=x;
					grasses[inde+1]=y;
					grasses[inde+2]=getHigh(land,x,y);
					grasses[inde+3]=0;
				}
			}
		}
	}
	public void grassCheck(int land,int x,int y,int type){
		if(UserData.fancyGrass){
			if(baseType(type)==grass1){
				if(Main.rand.nextInt(5)==0){
					int ind=Main.rand.nextInt(400);
					int inde=ind*4;
					grasses[inde]=x;
					grasses[inde+1]=y;
					grasses[inde+2]=getHigh(land,x,y);
					grasses[inde+3]=0;
					//grasses[in]
				}
			}
		}
	}

	public int getAge(int land,int x,int y){
		return Get(land,x,y,2);
	}
	public void setAge(int land,int x,int y,int age){
		Set(land,x,y,2,age);
	}



	public boolean age(int land,int x,int y,int h){
		int TT=getType(land,x,y);
		if(baseType(TT)==Main.level.getStats().getCurrentBlock()){
			return false;
		}
		int AA=longevity(TT);

		if(AA>0){
			int II=getAge(land,x,y)+AA;

			if(II>=250){
				setType(land,x,y,convert(TT));
			}else{
				setAge(land,x,y,II);
			}
			return true;
		}
		return AA!=-1;
	}

	public int getPeakWater(int land,int x,int y){
		return Get(land,x,y,3);
	}
	public void setPeakWater(int land,int x,int y,int height){
		Set(land,x,y,3,height);
		//changeHeight=true;
	}
	public int getWater(int x,int y){

		return waterChunk[(((x<<bit)+y)) ]+128;

	}
	public void setWater(int x,int y,int height){


		waterChunk[(((x<<bit)+y)) ]=(byte)(height-128); //<<2
		int xxx=x>>5;
		int yyy=y>>5;

		segment[xxx][yyy].waterStill=false;

	}
	public void cycleWater(){
		if(!Main.level.getStats().isWater()){
			return;
		}
		int c=0;
		//int segs=0;
		int H=Main.level.getStats().getWaterHeight();
		int m=Main.level.getStats().getSize()-1;
		int land = Main.world.timeline.getTerra();
		//boolean booly=false;

		boolean b1=false;
		boolean b2=false;
		boolean b3=false;
		boolean b4=false;

		for(int xx=0;xx<segSize;xx++){
			for(int yy=0;yy<segSize;yy++){


				c=0;
				if(!segment[xx][yy].waterStill){//xo<getEx() && xo+32>getSx() && yo<getEy() && yo+32>getSy()){
					int xo=(xx<<5);
					int yo=(yy<<5);
					//segs++;

					for(int xi=0;xi<32;xi++){
						for(int yi=0;yi<32;yi++){
							int x=(xo)+xi;
							int y=(yo)+yi;

							//int ease=0;
							//int T=getType(land,x,y);
							//if(hardness(T,0)){
							int i=getWater(x,y);
							int g=getHeight(land,x,y);


							//booly=i<=H;
							int i1=H;
							int i2=H;
							int i3=H;
							int i4=H;


							int g1=g;
							int g2=g;
							int g3=g;
							int g4=g;

							b1=x>0;
							if(b1){
								i1=getWater(x-1,y);
								g1=getHeight(land,x-1,y);
								if(g1>i){
									//if(i1<i+1)
									i1=i;
								}else{
									//setWater(x-1,y,(i1+i)/2);
								}
							}

							b2=x<m;
							if(b2){
								i2=getWater(x+1,y);
								g2=getHeight(land,x+1,y);
								if(g2>i){
									//if(i2<i+1)
									i2=i;
								}else{
									//setWater(x+1,y,(i2+i)/2);
								}
							}
							b3=y>0 ;
							if(b3){
								i3=getWater(x,y-1);
								g3=getHeight(land,x,y-1);
								if(g3>i){
									//if(i3<i+1)
									i3=i;
								}else{
									//setWater(x,y-1,(i3+i)/2);
								}
							}

							b4=y<m ;
							if(b4){
								i4=getWater(x,y+1);
								g4=getHeight(land,x,y+1);
								if(g4>i){
									//if(i4<i+1)
									i4=i;
								}else{
									//setWater(x,y+1,(i4+i)/2);
								}
							}


							int I=0;
							I=(int) (((i*2)+i1+i2+i3+i4)/6);
							/*int I2=(int) (I-i);

							int ii1=i1+I2;
							int ii2=i2+I2;
							int ii3=i3+I2;
							int ii4=i4+I2;
							if(ii1<0){
								ii1=0;
							}else if(ii1>255){
								ii1=255;
							}
							if(ii2<0){
								ii2=0;
							}else if(ii2>255){
								ii2=255;
							}
							if(ii3<0){
								ii3=0;
							}else if(ii3>255){
								ii3=255;
							}
							if(ii4<0){
								ii4=0;
							}else if(ii4>255){
								ii4=255;
							}

							if(b1 &&g1<=ii1){
								//setWater(x-1,y,ii1);
							}

							if(b2 && g2<=ii2){
								//setWater(x+1,y,ii2);
							}

							if(b3 && g3<=ii3){
								//setWater(x,y-1,ii3);
							}
							if(b4 && g4<=ii4){
								//setWater(x,y+1,ii4);
							}

							if(I>g&&g1>I && g2>I && g3>I && g4>I){
							//if(I<g+1){
								//I=g+1;
								//I=4;
							//}
							}else{

							}*/

							if(getWater(x,y)!=I){
								setWater(x,y,I);
								c++;
							}
						}
					}
					if(c==0){
						segment[xx][yy].waterStill=true;
					}
				}
			}
		}
	}
	public void saveWater(int land){
		ALL[land][3]=waterChunk.clone();
	}
	public void loadWater(int land){
		waterChunk=ALL[land][3].clone();
		for(int xx=0;xx<segSize;xx++){
			for(int yy=0;yy<segSize;yy++){
				segment[xx][yy].waterStill=false;
			}
		}
	}


	public int getElevation(int land,int x,int y){
		return 0;//Get(land,x,y,3);
	}
	public void setElevation(int land,int x,int y,int height){
		//Set(land,x,y,3,height);
		//changeHeight=true;
	}
	public void Flood(int x,int y,int high,int wide){
		int F=wide;
		int m =Main.level.getStats().getSize();
		for(int xx=x-F;xx<x+F;xx++){
			for(int yy=y-F;yy<y+F;yy++){
				int xx1=xx;
				int yy1=yy;
				if(xx1<0 || yy1<0 || xx1>m-1 || yy1>m-1){

				}else{
					int  h=getWater(xx1,yy1)+high;
					if(h<255)
						setWater(xx1,yy1,h);

				}
			}
		}
	}
	public void Hill(int x,int y,int type,int high,int wide){
		Hill(Main.world.timeline.getTerra(),x,y,type,high,wide);
	}
	public void Hill(int land,int x,int y,int type,int high,int wide){
		int F=wide/2;
		int F2=F+((wide%2==1)?1:0);
		int m =Main.level.getStats().getSize();
		for(int xx=x-F;xx<x+F2;xx++){
			for(int yy=y-F;yy<y+F2;yy++){
				int xx1=xx;
				int yy1=yy;
				if(xx1<0 || yy1<0 || xx1>m-1 || yy1>m-1){

				}else{
					int  h=getHeight(land,xx1,yy1)+high;
					int T=getType(land,xx1,yy1);
					if(!isSame(T,type))
						setType(land,xx1,yy1,subType(type));

					if(h<255)
						setHeight(land,xx1,yy1,h);

				}
			}
		}
	}
	public void Hole(int x,int y,int type,int high,int size){
		Hole(Main.world.timeline.getTerra(),x,y,type,high,size);
	}

	public void Pit(int x,int y,int size){
		Pit(Main.world.timeline.getTerra(),x,y,size);
	}
	public void Pit(int land, int x,int y,int size){
		int F=size/2;
		int F2=F+((size%2==1)?1:0);
		int m =Main.level.getStats().getSize()-1;
		for(int xx=x-F;xx<x+F2;xx++){
			for(int yy=y-F;yy<y+F2;yy++){
				int xx1=xx;
				int yy1=yy;
				if(xx1<0 || yy1<0 || xx1>m || yy1>m){

				}else{
					setHeight(land,xx1,yy1,0);
				}
			}
		}
	}
	public void Hole(int land,int x,int y,int type,int high,int size){
		int m =Main.level.getStats().getSize()-1;
		int F=size/2;
		int F2=F+((size%2==1)?1:0);
		for(int xx=x-F;xx<x+F2;xx++){
			for(int yy=y-F;yy<y+F2;yy++){
				int xx1=xx;
				int yy1=yy;
				if(xx1<0 || yy1<0 || xx1>m || yy1>m){

				}else{
					int  h=getHeight(land,xx1,yy1)-high;
					int T=getType(land,xx1,yy1);
					if(!isSame(T,type))
						setType(land,xx1,yy1,subType(type));

					if(h<2){
						h=2;}


					setHeight(land,xx1,yy1,h);
				}
			}
		}
	}
	public void Smooth(int x,int y,int size){
		Smooth(Main.world.timeline.getTerra(),x,y,size);
	}
	public void Smooth(int land,int x,int y,int size){
		int m=Main.level.getStats().getSize()-1;
		int F=size/2;
		int F2=F+((size%2==1)?1:0);
		for(int xx=x-F;xx<x+F2;xx++){
			for(int yy=y-F;yy<y+F2;yy++){
				if(!(xx<0 || yy<0 || xx>m || yy>m)){
					int i=getHeight(land,xx,yy);
					int i1=i;
					int i2=i;
					int i3=i;
					int i4=i;

					if(xx>0)
						i1=getHeight(land,xx-1,yy)+(hardness(baseType(getType(land,xx-1,yy)),1)?1:0);
					if(xx<m)
						i2=getHeight(land,xx+1,yy)+(hardness(baseType(getType(land,xx+1,yy)),1)?1:0);

					if(yy>0)
						i3=getHeight(land,xx,yy-1)+(hardness(baseType(getType(land,xx,yy-1)),1)?1:0);
					if(yy<m)
						i4=getHeight(land,xx,yy+1)+(hardness(baseType(getType(land,xx,yy+1)),1)?1:0);

					int I=(int) ((i*2 +i1+i2+i3+i4)/6);
					setHeight(land,xx,yy,I);
				}
			}

		}
	}

	public void Smooth(int land){
		int m=Main.level.getStats().getSize()-1;
		for(int x=0;x<=m;x++){
			for(int y=0;y<=m;y++){

				int i=getHeight(land,x,y);
				int i1=i;
				int i2=i;
				int i3=i;
				int i4=i;

				if(x>0)
					i1=getHeight(land,x-1,y)+(hardness(baseType(getType(land,x-1,y)),1)?1:0);
				if(x<m)
					i2=getHeight(land,x+1,y)+(hardness(baseType(getType(land,x+1,y)),1)?1:0);

				if(y>0)
					i3=getHeight(land,x,y-1)+(hardness(baseType(getType(land,x,y-1)),1)?1:0);
				if(y<m)
					i4=getHeight(land,x,y+1)+(hardness(baseType(getType(land,x,y+1)),1)?1:0);

				int I=(int) ((i*2 +i1+i2+i3+i4)/6);
				setHeight(land,x,y,I);
			}

		}
	}
	public void Decay(){
		Decay(Main.world.timeline.getTerra()-1);
	}

	public void Decay(int land){
		current=land;
		r.setSeed(land+1);
		Decay3(land);
	}
	public void DecayN(){
		Decay3(0);
	}

	public void Decay3(int land){
		int c=0;
		//int segs=0;
		int H=Main.level.getStats().getLandHeight();
		int m=Main.level.getStats().getSize()-1;

		//boolean booly=false;
		for(int xx=0;xx<segSize;xx++){
			for(int yy=0;yy<segSize;yy++){


				c=0;
				if(!segment[xx][yy].equilibrium){//xo<getEx() && xo+32>getSx() && yo<getEy() && yo+32>getSy()){
					int xo=(xx<<5);
					int yo=(yy<<5);
					//segs++;

					for(int xi=0;xi<32;xi++){
						for(int yi=0;yi<32;yi++){
							int x=(xo)+xi;
							int y=(yo)+yi;

							int ease=0;
							int T=getType(land,x,y);
							if(hardness(T,0)){
								int i=getHeight(land,x,y);
								//booly=i<=H;
								int i1=i;
								int i2=i;
								int i3=i;
								int i4=i;
								if(x>0 ){

									if(hardness(getType(land,x-1,y),2)){
										i1=getHeight(land,x-1,y);
										ease+=1;
									}
								}

								if(x<m ){
									if(hardness(getType(land,x+1,y),2)){
										i2=getHeight(land,x+1,y);
										ease+=1;
									}
								}

								if(y>0 ){
									if(hardness(getType(land,x,y-1),2)){
										i3=getHeight(land,x,y-1);
										ease+=1;
									}
								}

								if(y<m ){
									if(hardness(getType(land,x,y+1),2)){
										i4=getHeight(land,x,y+1);
										ease+=1;
									}
								}

								ease+=1;
								int I=(int) ((i*2 +i1+i2+i3+i4)/6);
								if(I!=H){
									c++;
									if(i<H){
										I+=2;
									}
								}
								if(getHeight(land,x,y)!=I){
									setHeight(land,x,y,I);
									c++;
								}

							}
							if(age(land,x,y,ease/2)){
								c++;}else{
									if(getHeight(land,x,y)!=H){
										c++;
									}
								}	
						}
					}
					if(c==0){
						segment[xx][yy].equilibrium=true;
					}
				}
			}
		}
	}
	public void Decay(int x,int y,int sizee){
		Decay(Main.world.timeline.getTerra(),x,y,sizee);
	}
	public void Decay(int land,int x,int y,int sizee){
		int H=Main.level.getStats().getLandHeight();
		int m=Main.level.getStats().getSize()-1;
		int F=sizee/2;
		int F2=F+((sizee%2==1)?1:0);
		for(int xx=x-F;xx<x+F2;xx++){
			for(int yy=y-F;yy<y+F2;yy++){
				if(!(xx<0 || yy<0 || xx>m || yy>m)){



					int ease=0;
					int T=getType(land,xx,yy);
					if(hardness(T,0)){
						int i=getHeight(land,xx,yy);
						int i1=i;
						int i2=i;
						int i3=i;
						int i4=i;
						if(xx>0 ){

							if(hardness(getType(land,xx-1,yy),2)){
								i1=getHeight(land,xx-1,yy);
								ease+=1;
							}
						}

						if(xx<m ){
							if(hardness(getType(land,xx+1,yy),2)){
								i2=getHeight(land,xx+1,yy);
								ease+=1;
							}
						}

						if(yy>0 ){
							if(hardness(getType(land,xx,yy-1),2)){
								i3=getHeight(land,xx,yy-1);
								ease+=1;
							}
						}

						if(yy<m ){
							if(hardness(getType(land,xx,yy+1),2)){
								i4=getHeight(land,xx,yy+1);
								ease+=1;
							}
						}

						ease+=1;
						int I=(int) ((i*2 +i1+i2+i3+i4)/6);
						if(I!=H){

							if(i<H){
								I+=2;
							}
						}
						if(getHeight(land,xx,yy)!=I){
							setHeight(land,xx,yy,I);
						}

					}
					if(age(land,xx,yy,ease/2)){
					}else{
						if(getHeight(land,xx,yy)!=H){
						}
					}	
				}
			}
		}


	}

	public void cloneLand(int from,int to){
		ALL[to]=ALL[from].clone();
	}
	public void futureLand(int start){
		for(int m=start;m<Main.level.getStats().getCapTerra()-1;m++){
			cloneLand(m,m+1);
			Decay(m+1);
		}
	}

	public void futureTo(int start,int finish){
		System.out.println("start: "+start+" finish: "+finish);
		for(int m=start;m<finish;m++){
			cloneLand(m,m+1);

			topography.cause(m+1);
			Decay(m+1);

		}
		//current=finish-1;
	}

	public void future(int past){
		if(current>=past){
			return;
		}
		saveWater(past);
		cloneLand(past-1, past);
		Decay(past);
	}


	public void defaultAllLand(){
		int M=Main.level.getStats().getSize();
		int z=0;
		int e=Main.level.getStats().getLandHeight();

		for(int x=0;x<M;x++){
			for(int y=0;y<M;y++){		
				z=dirt;//r.nextInt(4);
				setPeakWater(0,x,y,e);
				setHeight(0,x,y,e+1);//Main.rand.nextInt(50)); //e+1
				setType(0,x,y,z);
			}
		}
		if(Main.level.getStats().isNoTerrainAge() || !Main.level.getStats().isInterval()){
			return;
		}
		for(int m=1;m<Main.level.getStats().getCapTerra();m++){
			cloneLand(0,m);
		}

		loadWater(0);
		setWater(0,0,20);
	}

	public void perlinLand(){
		int m=Main.level.getStats().getSize();
		int e2=(int) (Math.log10(m)/Math.log10(2));
		System.out.println("e: "+e2);
		int array[][]=new int[m][m];

		int F=0;
		for(int i=0;i<e2;i++){
			System.out.println("x ");
			F=1<<i;
			for(int x=0;x<m;x+=F){
				System.out.print(x+" ");
				for(int y=0;y<m;y+=F){
					int G=r.nextInt(200);
					for(int x1=x;x1<x+F;x1++){
						for(int y1=y;y1<y+F;y1++){
							array[x1][y1]+=G;
						}
					}

				}
			}

		}
		int z=0;
		for(int x=0;x<m;x++){
			for(int y=0;y<m;y++){
				z=r.nextInt(4);
				setElevation(0,x,y,0);
				setHeight(0,x,y,array[x][y]/e2);
				setType(0,x,y,z);
			}
		}
		futureLand(0);
		Message.m(this,"done");

	}

	public void allTypesTest(){
		int M=Main.level.getStats().getSize();
		for(int x=0;x<M;x++){
			for(int y=0;y<M;y++){		
				setHeight(0,x,y,0);
				setType(0,x,y,x);
			}
		}

		for(int m=1;m<Main.level.getStats().getCapTerra();m++){
			cloneLand(0,m);
		}
	}

	public void time(){
		if(Main.level.getStats().isNoTerrainAge()){
			FRAME=0;
		}else{
			FRAME=Main.world.timeline.getTerra();

			if(FRAME !=preFrame){
				refreshVectors();
				refreshColors();
			}
			preFrame=FRAME;
		}
	}
	public void logic(){

		if(changeHeight){
			changeHeight=false;
			refreshVectors();
			refreshGeoBatch();
			current=FRAME;
		}
		if(changeType){
			changeType=false;
			refreshGeoBatch();
			refreshColors();
			current=FRAME;
		}
		//TODO UGH
		int xo=(((int)Main.player.x)>>5);
		int yo=(((int)Main.player.y)>>5);
		int R=UserData.getLandView();
		for(int fx=0;fx<segSize;fx++){
			for(int fy=0;fy<segSize;fy++){
				int xi=segment[fx][fy].x;
				int yi=segment[fx][fy].y;

				if(xi>=xo-R && xi<=xo+R && yi>=yo-R && yi<=yo+R){
					segment[fx][fy].load();
					this.refreshVectors();
					this.refreshColors();
				}else{
					segment[fx][fy].unload();
				}
			}

		}

		cycleWater();
	}
	public void renewSegments(){
		for(int fx=0;fx<segSize;fx++){
			for(int fy=0;fy<segSize;fy++){
				//segment[fx][fy].visible=false;
				segment[fx][fy].updatedC=true;
				segment[fx][fy].updatedV=true;
				segment[fx][fy].equilibrium=false;
			}
		}
		changeHeight=true;
		changeType=true;
		this.refreshVectors();
		this.refreshColors();
	}
	public void render(){
		GL11.glPushMatrix();
		GL11.glColor4f(1f,1f,1f,1f);
		Texture.bind(0);
	//	GL11.glTranslatef(0.5f, 0.5f, 0);
		
		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
		GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
		
		
		
		
		
		for(int i=0;i<segSize;i++){
			for(int j=0;j<segSize;j++){
				//Render.shader.coordinate(i*32,j*32,0);
				renderLand(i,j);
			}
		}
		
		GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glDisableClientState(GL11.GL_NORMAL_ARRAY);
		GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
		//renderGeoBatch();
		GL11.glPopMatrix();
	}

	public void refreshColors(){
		int M=Main.level.getStats().getSize();
		int land=Main.world.timeline.getTerra();
		int i=0;
		for(int xx=0;xx<segSize;xx++){
			for(int yy=0;yy<segSize;yy++){
				i=0;
				int xo=(xx<<5);
				int yo=(yy<<5);
				int c=segment[xx][yy].getChoice();
				if(segment[xx][yy].updatedC && c!=-1){//if(xo<getEx() && xo+32>getSx() && yo<getEy() && yo+32>getSy()){
					segment[xx][yy].updatedC=false;

					ModelTerraBuffer m3=terraBase;//model[c];
					for(int xi=0;xi<32;xi++){
						for(int yi=0;yi<32;yi++){
							int x=(xo)+xi;
							int y=(yo)+yi;

							float f[];
							if(getHeight(land,x,y)<=1){
								f=new float[]{0.0f,0.1f,0.2f};
							}else{
								f=color(getType(land,x,y));
							}
							float fL[]=f;
							float fU[]=f;

							if(x<M-1){
								if(getHeight(land,x+1,y)<=1){
									fL=new float[]{0.0f,0.1f,0.2f};
								}else{
									fL=color(getType(land,x+1,y));
								}
							}
							if(y<M-1){
								if(getHeight(land,x,y+1)<=1){
									fU=new float[]{0.0f,0.1f,0.2f};
								}else{
									fU=color(getType(land,x,y+1));
								}
							}
							//i=(((x*M) +y)*36);

							for(int b=0;b<4;b++){
								m3.C[i]=f[0];i++;
								m3.C[i]=f[1];i++;
								m3.C[i]=f[2];i++;
							}

							//m3.C[i]=f[0];i++;
							//m3.C[i]=f[1];i++;
							//m3.C[i]=f[2];i++;

							//m3.C[i]=f[0];i++;
							//m3.C[i]=f[1];i++;
							//m3.C[i]=f[2];i++;

							m3.C[i]=fL[0];i++;
							m3.C[i]=fL[1];i++;
							m3.C[i]=fL[2];i++;

							m3.C[i]=fL[0];i++;
							m3.C[i]=fL[1];i++;
							m3.C[i]=fL[2];i++;

							//m3.C[i]=f[0];i++;
							//m3.C[i]=f[1];i++;
							//m3.C[i]=f[2];i++;

							//m3.C[i]=f[0];i++;
							//m3.C[i]=f[1];i++;
							//m3.C[i]=f[2];i++;

							m3.C[i]=fU[0];i++;
							m3.C[i]=fU[1];i++;
							m3.C[i]=fU[2];i++;

							m3.C[i]=fU[0];i++;
							m3.C[i]=fU[1];i++;
							m3.C[i]=fU[2];i++;
							
						}
					}
					//model[c]=m3;
					//bufferData(model[c].c,wrap(model[c].C));
					bufferData(modelRef[c].c,wrap(m3.C));
				}
			}
		}
	}
	
	public boolean sloped=true;
	public void refreshVectors(){
		int M=Main.level.getStats().getSize();
		int z=0;
		int zL=0;
		int zU=0;
		
		int zR=0;
		int zD=0;
		int i=2;

		int land=Main.world.timeline.getTerra();

		
		/*boolean redoM=false;
		boolean redoL=false;
		boolean redoU=false;*/
		//int C=0;
		float v1=0,v2=0,v3=0,v4=0;
		for(int xx=0;xx<segSize;xx++){
			for(int yy=0;yy<segSize;yy++){
				i=2;
				int xo=(xx<<5);
				int yo=(yy<<5);
				int c=segment[xx][yy].getChoice();
				if(segment[xx][yy].updatedV &&c!=-1){//xo<getEx() && xo+32>getSx() && yo<getEy() && yo+32>getSy()){
					segment[xx][yy].updatedV=false;

					ModelTerraBuffer m3=terraBase;//model[c];
					for(int xi=0;xi<32;xi++){
						for(int yi=0;yi<32;yi++){
							int x=(xo)+xi;
							int y=(yo)+yi;

							z=getHeight(land,x,y);

							if(x<M-1){
								zL=getHeight(land,x+1,y);
							}else{
								zL=0;
							}
							if(y<M-1){
								zU=getHeight(land,x,y+1);
							}else{
								zU=0;
							}
							
							if(x>0){
								zR=getHeight(land,x-1,y);
							}else{
								zR=0;
							}
							if(y>0){
								zD=getHeight(land,x,y-1);
							}else{
								zD=0;
							}
							/*redoM=((m3.V[i]*5f)-1)==z;
							redoL=((m3.V[i+18]*5f)-1)==zL;
							redoU=((m3.V[i+30]*5f)-1)==zU;*/

							if(z==1){
								z=-50;
							}
							if(zU==1){
								zU=-50;
							}
							if(zL==1){
								zL=-50;
							}
							
							v4=v3=v2=v1=(z+1)/5f;
							if(sloped && this.getType(land, x, y)<14){
							boolean bL=between(zL-z);
							boolean bR=between(zR-z);
							boolean bD=between(zD-z);
							boolean bU=between(zU-z);
							
							/*if(bL||bD)v1=(Math.max(zL, zD)+1)/5f;
							if(bR||bD)v2=(Math.max(zR, zD)+1)/5f;
							if(bR||bU)v3=(Math.max(zR, zU)+1)/5f;
							if(bL||bU)v4=(Math.max(zL, zU)+1)/5f;*/
							
							v1=(Math.max(bL?zL:z, bD?zD:z)+1)/5f;
							v2=(Math.max(bR?zR:z, bD?zD:z)+1)/5f;
							v3=(Math.max(bR?zR:z, bU?zU:z)+1)/5f;
							v4=(Math.max(bL?zL:z, bU?zU:z)+1)/5f;
							}
								
							
							
							m3.V[i]=v1;
							m3.V[i+3]=v2;
							m3.V[i+6]=v3;
							m3.V[i+9]=v4;

							i+=12;

							float nU=1f;
							float nL=1f;
							if(zU>z){
								nU=-nU;
							}
							if(zL>z){
								nL=-nL;
							}

							//m3.V[i]=(z+1)/5f;m3.N[i-2]=nL;
							//m3.V[i+3]=(z+1)/5f;m3.N[i+1]=nL;
							int n=1;
							m3.V[i]=(zL+1)/5f;m3.N[n+i-3]=nL;
							m3.V[i+3]=(zL+1)/5f;m3.N[n+i]=nL;

							//m3.V[i+12]=(z+1)/5f;m3.N[i+11]=nU;
							//m3.V[i+15]=(z+1)/5f;m3.N[i+14]=nU;
							m3.V[i+6]=(zU+1)/5f;m3.N[n+i+4]=nU;
							m3.V[i+9]=(zU+1)/5f;m3.N[n+i+7]=nU;
							i+=12;
						}
					}

					//model[c]=m3;
					//bufferData(model[c].v,wrap(model[c].V));
					//bufferData(model[c].n,wrap(model[c].N));
					
					bufferData(modelRef[c].v,wrap(m3.V));
					bufferData(modelRef[c].n,wrap(m3.N));
				}
			}
		}
	}
	public int CC=0;

	private boolean between(int c){
		return c>0 && c<5;
	}

	public void renderGeoBatch(){
/*
		if(!UserData.fancyGrass){
			return;
		}
		//GL11.glPushClientAttrib(GL11.GL_CLIENT_VERTEX_ARRAY_BIT);


		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB,grassModel.v);
		GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);


		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		//GL11.glDrawElements(GL11.GL_QUADS, grassModel.ib);//500, GL11.GL_UNSIGNED_SHORT, 0);
		EXTDrawInstanced.glDrawElementsInstancedEXT(GL11.GL_QUADS, grassModel.ib,  400);
		//GL11.glDrawArrays(GL11.GL_QUADS, 0, model[c].faces);

		GL11.glDisableClientState(GL11.GL_NORMAL_ARRAY);
		GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
		GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);*/
		//ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, 0);
	}
	
	public void renderLand(int xi,int yi){

		//GL11.glPushClientAttrib(GL11.GL_CLIENT_VERTEX_ARRAY_BIT);

		int c=segment[xi][yi].getChoice();
		if(c==-1){
			return;
		}
		float xd=(xi*32);
		float yd=(yi*32);
		GL11.glPushMatrix();
		GL11.glTranslatef(xd, yd, 0); //0.5f+ 0.5f+
	
		//Render.lightPosition.put(3,Render.lightPosition.get(3)-xd);
		//Render.lightPosition.put(2,Render.lightPosition.get(2)-yd);
		Render.lightPos(Main.mouseSelector.x-xd, Main.mouseSelector.y-yd, Main.mouseSelector.z+1);
		//Render.lightParse();
		//int i_v=0;
		//int i_c=1;
		//int i_n=2;
		//GL11.glEnableClientState( GL11.GL_INDEX_ARRAY );
		//GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		//glEnableVertexAttribArray(0);
		
		//GL20.glEnableVertexAttribArray(i_v);
		// GL20.glEnableVertexAttribArray(i_c);
		// GL20.glEnableVertexAttribArray(i_n);
		
		 
		//ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB,model[c].v);
		//GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);
		
		
		 
		//GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
        //GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, model[c].v);
       /* GL20.glVertexAttribPointer(
        		i_v,                  // attribute
                3,                  // size
                GL11.GL_FLOAT,           // type
                false,           // normalized?
                0,                  // stride
                0            // array buffer offset
        );
        
       // GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, model[c].c);
        GL20.glVertexAttribPointer(
        		i_n,                                // attribute
                3,                                // size
                GL11.GL_FLOAT,                         // type
                false,                         // normalized?
                0,                                // stride
                0                          // array buffer offset
        );
       
       // GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, model[c].n);
        GL20.glVertexAttribPointer(
        		i_n,                                // attribute
                3,                                // size
                GL11.GL_FLOAT,                         // type
                false,                         // normalized?
                0,                                // stride
                0                          // array buffer offset
        );*/

     // Index buffer
        //GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, model[c].v2);
        /*GL11.glDrawElements(
        		GL11.GL_QUADS,      // mode
        		model[c].ibb,    // count
                GL11.GL_UNSIGNED_SHORT,   // type
                model[c].vib           // element array buffer offset
        );*/

		//GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		//glEnableClientState(GL_COLOR_ARRAY);


		
		
		ARBVertexBufferObject.glBindBufferARB( ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, modelRef[c].v );//vertex buffer
		GL11.glVertexPointer( 3, GL11.GL_FLOAT, 0, 0 );
		
		
		ARBVertexBufferObject.glBindBufferARB( ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, modelRef[c].n );//normal buffer
		GL11.glNormalPointer( GL11.GL_FLOAT, 0,0 );
		
		
		
		ARBVertexBufferObject.glBindBufferARB( ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, modelRef[c].c );//color buffer
		GL11.glColorPointer( 3, GL11.GL_FLOAT, 0, 0 );
		
		ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB, modelRef[c].v2);//index buffer
		//GL11.glDrawElements(GL11.GL_QUADS,model[c].ibb,GL11.GL_UNSIGNED_SHORT,0); 
		
		GL12.glDrawRangeElements(GL11.GL_QUADS, 0, modelRef[c].ibb, modelRef[c].ibb,
				GL11.GL_UNSIGNED_SHORT, 0);
		
		
	
		//ARBVertexBufferObject.glBindBufferARB(
		//		ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB, model[c].v2);
		
       // GL11.glDrawElements(
      // 		GL11.GL_QUADS,      // mode
      //  		model[c].vib           // element array buffer offset
       // );

       // GL20.glDisableVertexAttribArray(i_v);
       // GL20.glDisableVertexAttribArray(i_c);
       // GL20.glDisableVertexAttribArray(i_n);
		
        //GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
		//GL11.glDrawElements(, model[c].vib);
		
		/*
		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB,model[c].v);
		GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);
		//GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,model[c].v);
		//GL20.glEnableVertexAttribArray(0);
		//GL20.glVertexAttribPointer(0, 1, GL11.GL_FLOAT, false, 0, 0);

		//GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
		ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, model[c].n);
		GL11.glNormalPointer(GL11.GL_FLOAT, 0, 0);

		//GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, 0);
		GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
		ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, model[c].c);
		GL11.glColorPointer(3, GL11.GL_FLOAT, 0, 0);

		//ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB,0);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL11.glDrawElements(GL11.GL_QUADS, Main.technicalModel.ib);//500, GL11.GL_UNSIGNED_SHORT, 0);
		//EXTDrawInstanced.glDrawElementsInstancedEXT(GL11.GL_QUADS, Main.hoopl.ib,  100);
		//GL11.glDrawArrays(GL11.GL_QUADS, 0, model[c].faces);

		GL11.glDisableClientState(GL11.GL_NORMAL_ARRAY);
		GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
		GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);*/
		//ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, 0);
		GL11.glPopMatrix();
	}

	public FloatBuffer wrap(float[] arr) {
		FloatBuffer buf = BufferUtils.createFloatBuffer(arr.length);
		buf.put(arr).flip();
		return buf;
	}

	public static void bufferData(int id, FloatBuffer buffer) {
		if (GLContext.getCapabilities().GL_ARB_vertex_buffer_object) {

			ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, id);
			ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, buffer, ARBVertexBufferObject.GL_STATIC_DRAW_ARB);
		}
	}
	public void savePic(String path,String name){
		int land=Main.world.timeline.getTerra();
		int m=Main.level.getStats().getSize();
		BufferedImage im = new BufferedImage(m,m,BufferedImage.TYPE_INT_RGB);
		for(int i=0;i<m;i++){
			for(int j=0;j<m;j++){
				//float f[] = color(getType(land,i,j));
				//int r=(int)(0xff*f[0]);
				//int g=(int)(0xff*f[1]);
				//int b=(int)(0xff*f[2]);
				//int rgb=(((r<<8) +g)<<8) +b;
				int rgb = squareRef[getType(land,i,j)];
				im.setRGB(i, j, rgb);
			}
		}
		try {
			File outputfile = new File(path+name+".png");
			ImageIO.write(im, "png", outputfile);
			Message.addPass("File Saved!");
		} catch (IOException e) {
			Message.addPass("Cannot Saved!");
		}
	}

	public void saveHeightMap(String path,String name){
		int land=Main.world.timeline.getTerra();
		int m=Main.level.getStats().getSize();
		BufferedImage im = new BufferedImage(m,m,BufferedImage.TYPE_INT_RGB);
		for(int i=0;i<m;i++){
			for(int j=0;j<m;j++){
				int N=getHeight(land,i,j);
				int W=getWater(i,j);
				int A=getAge(land,i,j);
				int rgb=(((N<<8) +W)<<8) +A;
				im.setRGB(i, j, rgb);
			}
		}
		try {
			File outputfile = new File(path+name+"_height.png");
			ImageIO.write(im, "png", outputfile);
			Message.addPass("File Saved!");
		} catch (IOException e) {
			Message.addPass("Cannot Saved!");
		}

	}
	public void loadLand(String path,String name){
		try {
			loadLand(ImageIO.read(new File(path+name+".png")),ImageIO.read(new File(path+name+"_height.png")));
		} catch (IOException e) {
			Message.addPass("No Saved Maps!");
		}
	}
	public void loadLand(BufferedImage types,BufferedImage heights){
		if(types==null || heights==null){
			defaultAllLand();
			return;
		}
		if(types.getWidth()==types.getHeight() && types.getHeight()==heights.getHeight()  && types.getWidth()==heights.getWidth()){
			double e= (Math.log10(types.getWidth())/Math.log10(2));
			if(e==Math.round(e)){
				int m=types.getWidth();
				Main.level.getStats().setSize(m);
				init();
				remakeModelCache();
				renewModels();
				for(int i=0;i<m;i++){
					for(int j=0;j<m;j++){
						//int N=getHeight(land,i,j);
						int rgb=types.getRGB(i, j);
						int data=heights.getRGB(i, j);
						//int h=(int)Math.pow(Math.abs(height),(1f/2f));
						int H=256+((data >>8)>>8);
						int W=256+((data | 0xff0000)>>8);
						int A=256+(data |0xffff00);

						if(A!=0)
							System.out.println("A "+A);
						//System.out.println("L2 "+((((2<<8) +2)<<8) +2));
						//int z=r.nextInt(4);
						//setElevation(0,i,j,0);
						setPeakWater(0,i,j,W);

						setHeight(0,i,j,H);//256+h2
						setType(0,i,j,subType(typeByColor(rgb)));
						setAge(0,i,j,A);
					}
				}

				//System.out.println("o hai "+m);

				Main.world.setTime(0);
				
				if(Main.level.getStats().isNoTerrainAge() || !Main.level.getStats().isInterval()){
				
				}else{
				futureLand(0);
				loadWater(0);
				}
				Message.addPass("Map Loaded!");
			}else{
				//NOT FACTOR OF 2!!!
			}
		}else{
			//NOT SAME SIZES
		}
	}
	public void renderWater(){
		GL11.glPushMatrix();
		GL11.glTranslatef(0.5f, 0.5f, 0);
		int m=Main.level.getStats().getSize();
		if(!Main.level.getStats().isWater()){
			return;
		}
		GL11.glColor4f(0,0,1,0.2f);
		GL11.glBegin(GL11.GL_QUADS);

		float ff=0.15f;
		for(int x=0;x<m-1;x++){
			for(int y=0;y<m-1;y++){

				float z1=getWater(x,y)/5f;
				float z2=getWater(x+1,y)/5f;
				float z3=getWater(x+1,y+1)/5f;
				float z4=getWater(x,y+1)/5f;
				GL11.glVertex3f(x, y, z1+ff);
				GL11.glVertex3f(x+1, y, z2+ff);
				GL11.glVertex3f(x+1, y+1, z3+ff);
				GL11.glVertex3f(x, y+1, z4+ff);

			}
		}
		GL11.glEnd();
		GL11.glPopMatrix();

	}



	//public int getHeight(int x, int y) {
	//System.out.println("check "+" : "+y);
	//	return getHeight(Main.world.timeline.getTerra(),x,y);
	//}

}
