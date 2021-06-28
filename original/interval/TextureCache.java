package interval;

import intervalHistory.Gene;
import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import org.lwjgl.opengl.GL11;

public class TextureCache {
	static ArrayList<Integer> ids = new ArrayList<Integer>();
	static ArrayList<Integer> freeIds = new ArrayList<Integer>();
	static int WERG=1;
	public static Tex check;
	public static Tex empty;
	public static BufferedImage buttons;
	public static BufferedImage level;
	public static int badge;
	
	static int hair=0;
	static int cloth=0;
	static int skin=0;
	static int skin2=0;
	static int skin3=0;
	
	public static void init(){
		check = new Tex("check");
		empty = new Tex(0);
		buttons=FileManager.loadImage("buttons");
		level=FileManager.loadImage("level");
		Texture.get("buttons");
		
		badge=Texture.make(level.getSubimage(64, 0, 32, 16));
		
		
		BufferedImage bim =new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB);
		bim.setRGB(0, 0, 0xffffffff);
		Texture.make(bim,0);
		
		
		BufferedImage conf= FileManager.loadImage("config");
		hair=conf.getRGB(0, 0);
		skin=conf.getRGB(0, 1);skin2=conf.getRGB(1, 1);skin3=conf.getRGB(2, 1);
		cloth=conf.getRGB(0, 2);

		int mx=256;
		
		BufferedImage out=new BufferedImage(mx,mx,BufferedImage.TYPE_INT_RGB);
		BufferedImage out2=new BufferedImage(mx,mx,BufferedImage.TYPE_INT_RGB);
		
		for(int x=0;x<mx;x++){
			for(int y=0;y<mx;y++){
				int[] s=Skin(x,y,mx);
				int[] h=Hair(x,y,mx);
				out.setRGB(x, y, h[0]<<16 | h[1]<<8 | h[2]);
				out2.setRGB(x, y, s[0]<<16 | s[1]<<8 | s[2]);
			}
		}
		FileManager.saveImage("PaletteHair",out);
		FileManager.saveImage("PaletteSkin",out2);
	}
	private static int[] Hair(int L,int R,int max){
		L=256*L/max;
		R=256*R/max;
		int r,g,b;
		
		if(L<128){
			b=(L*2)/5;
			r=10*L/9;
			g=L*8/10;
		}else{
			if(L<200)
				b=(128*2/5) -(L-128)*2/5;
			else
				b= ((200-128)*2/5 )+ (L-200)*3 ;
			
			g=(128*8/10)+(L-128)*10/9;
			r=(128*10/9) +(L-128)*9/10;
		}
		float RR=(R/256f);
		float R2=RR*RR*RR;
		r+=R2*60;
		g-=R2*60;
		b-=R2*40;
		if(r>255)
			r=255;
		if(g<0)
			g=0;
		if(b<0)
			b=0;
		return new int[]{r,g,b};
	}
	
	private static int[] Skin(int S,int L,int max){
		float s=(float)S/(float)max;
		float l=(float)L/(float)max;
		if(s>1)
			s=1;
		if(s<0)
			s=0;
		int ss=(int) (s*180);
		int r=70+ss;
		int g= (int) (30+30*l*(0.5+s/2)+ss);
		int b=ss;
		return new int[]{r,g,b};
	}
	private static int integator(int c[]){
		return integator(c[0],c[1],c[2]);
	}
	private static int integator(int r,int g,int b){
		return r<<16 | g<<8 |b;
	}
	public static Tex genetics(Gene gene,int r,int g,int b,String original){
		return genetics(gene,integator(r,g,b),original);
	}
	public static Tex genetics(Gene gene,int shirt,String original){
		BufferedImage bim = FileManager.loadImage(original); 
		if(bim==null)
			return empty;
		int hairColor=integator(Hair(gene.Light(),gene.Red(),256));
		int skinColor=integator(Skin(gene.Skin(),gene.Light(),256));
		int skinColor2=integator(Skin(gene.Skin()+12,gene.Light(),256));
		int skinColor3=integator(Skin(gene.Skin()-12,gene.Light(),256));
		
		for(int x=0;x<bim.getWidth();x++){
			for(int y=0;y<bim.getHeight();y++){
				int in=bim.getRGB(x, y);
				
				if(in==hair){
					bim.setRGB(x, y, hairColor);
				}else if(in==cloth){
					bim.setRGB(x, y, shirt);
				}else if(in==skin){
					bim.setRGB(x, y, skinColor);
				}else if(in==skin2){
					bim.setRGB(x, y, skinColor2);
				}else if(in==skin3){
					bim.setRGB(x, y, skinColor3);
				}
			}
		}
	
		return new Tex(bim);
	}
	
	static ColorModel glAlphaColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB),
			new int[] {8,8,8,8},
			true,
			false,
			ComponentColorModel.TRANSLUCENT,
			DataBuffer.TYPE_BYTE);

	static ColorModel glColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB),
			new int[] {8,8,8,0},
			false,
			false,
			ComponentColorModel.OPAQUE,
			DataBuffer.TYPE_BYTE);


	
	static int  createTextureID() 
	{ 
		int iii=1;
		if(freeIds.size()>0){
			iii=freeIds.remove(0);
		}else{
			while(ids.contains(WERG)){
				WERG++;
			}
			iii=WERG;
		}
		ids.add(iii);
		return iii;
	} 
	public static void removeTexture(int i){
		//WERG=1;
		//System.out.println("removal "+i);
		freeIds.add(i);
		ids.remove(ids.indexOf(i));
		GL11.glDeleteTextures(i);
	}
	
	public static IntBuffer createIntBuffer(int size) {
		ByteBuffer temp = ByteBuffer.allocateDirect(4 * size);
		temp.order(ByteOrder.nativeOrder());
		return temp.asIntBuffer();
	}  
	
	public static BufferedImage loadImage(String ref)
	{ 
		BufferedImage bufferedImage = null;
		try {
			File ff=new File(ref);
			bufferedImage = ImageIO.read(ff);
			
		} catch (IOException e) {
			System.out.println("Texture unavailable @: "+ref);
		}
		return bufferedImage;
	}
	
/*	public static ByteBuffer convertImageData(BufferedImage bufferedImage) { 
		ByteBuffer imageBuffer = null; 
		WritableRaster raster;
		BufferedImage texImage;

		int texWidth = bufferedImage.getWidth();
		int texHeight = bufferedImage.getHeight();

		if (bufferedImage.getColorModel().hasAlpha()) {
			raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,texWidth,texHeight,4,null);
			texImage = new BufferedImage(glAlphaColorModel,raster,false,new Hashtable());
		} else {
			raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,texWidth,texHeight,3,null);
			texImage = new BufferedImage(glColorModel,raster,false,new Hashtable());
		}
		Graphics g = texImage.getGraphics();
		g.setColor(new Color(0f,0f,0f,0f));
		g.fillRect(0,0,texWidth,texHeight);
		g.drawImage(bufferedImage,0,0,null);


		byte[] data = ((DataBufferByte) texImage.getRaster().getDataBuffer()).getData(); 

		imageBuffer = ByteBuffer.allocateDirect(data.length); 
		imageBuffer.order(ByteOrder.nativeOrder()); 
		imageBuffer.put(data, 0, data.length); 
		imageBuffer.flip();

		return imageBuffer; 
	} */
	
	public static ByteBuffer[] loadIcon(String str){
	
		str=FileManager.perfectTexture(str);
		BufferedImage image = null;
		try{
			image = ImageIO.read(new File(str));
		}catch (IOException e){
			e.printStackTrace();
		}
		ByteBuffer[] buffers = null;
		String OS = System.getProperty("os.name").toUpperCase();
		if(OS.contains("WIN"))
		{
			buffers = new ByteBuffer[2];
			buffers[0] = loadInstance(image, 16);
			buffers[1] = loadInstance(image, 32);
		}
		else if(OS.contains("MAC")){
			buffers = new ByteBuffer[1];
			buffers[0] = loadInstance(image, 128);
		}
		else
		{
			buffers = new ByteBuffer[1];
			buffers[0] = loadInstance(image, 32);
		}
		return buffers;
	}
	
	private static ByteBuffer loadInstance(BufferedImage image, int dimension)
	{
		BufferedImage scaledIcon = new BufferedImage(dimension, dimension,
				BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g = scaledIcon.createGraphics();
		//double ratio = 1;//getIconRatio(image, scaledIcon);
		double width = image.getWidth() ;
		double height = image.getHeight() ;
		g.drawImage(image, (int) ((scaledIcon.getWidth() - width) / 2),
				(int) ((scaledIcon.getHeight() - height) / 2), (int) (width),
				(int) (height), null);
		g.dispose();

		return convertToByteBuffer(scaledIcon);
	}

	public static ByteBuffer convertToByteBuffer(BufferedImage image)
	{
		byte[] buffer = new byte[image.getWidth() * image.getHeight() * 4];
		int counter = 0;
		for (int i = 0; i < image.getHeight(); i++)
			for (int j = 0; j < image.getWidth(); j++)
			{
				int colorSpace = image.getRGB(j, i);
				buffer[counter + 0] = (byte) ((colorSpace << 8) >> 24);
				buffer[counter + 1] = (byte) ((colorSpace << 16) >> 24);
				buffer[counter + 2] = (byte) ((colorSpace << 24) >> 24);
				buffer[counter + 3] = (byte) (colorSpace >> 24);
				counter += 4;
			}
		return ByteBuffer.wrap(buffer);
	}
	
}
