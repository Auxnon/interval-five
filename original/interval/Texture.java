package interval;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Hashtable;
import org.lwjgl.opengl.GL11;

public class Texture {

	static HashMap<String,Integer> cache=new HashMap<String,Integer>();
	
	public static void redo(String s){
		s=FileManager.cleanestFileName(s);
		Integer u=cache.get(s);
		if(u!=null){
			int ii=Texture.make(s,u);
			cache.put(s, ii);
		}
	}
	public static int get(String s){
		s=FileManager.cleanestFileName(s);
		Integer u=cache.get(s);
		if(u==null){
			int ii=Texture.make(s);
			cache.put(s, ii);
			return ii;
		}
		return u;
	}
	public static int limitedGet(String s){
		Integer u=cache.get(s);
		if(u==null){
			return -1;
		}
		return u;
	}
	public static void specify(String s,int i){
			cache.put(s, i);
	}
	public static void bind(String s){
		bind(get(s));
	}
	
	final public static ColorModel glAlphaColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB),
			new int[] {8,8,8,8},
			true,
			false,
			ComponentColorModel.TRANSLUCENT,
			DataBuffer.TYPE_BYTE);

	final public static ColorModel glColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB),
			new int[] {8,8,8,0},
			false,
			false,
			ComponentColorModel.OPAQUE,
			DataBuffer.TYPE_BYTE);
	
	
	public static IntBuffer createIntBuffer(int size) {
		ByteBuffer temp = ByteBuffer.allocateDirect(4 * size);
		temp.order(ByteOrder.nativeOrder());
		return temp.asIntBuffer();
	}  
	public static int make(String file){
		return make(file,TextureCache.createTextureID());
	}
	public static int make(String file,int iid){
		BufferedImage bim = FileManager.loadImage(file); 
		return make(bim,iid);
	}
	public static int make(BufferedImage bim){
		return make(bim,TextureCache.createTextureID());
	}
	public static int make(BufferedImage bim,int iid){
		int target=GL11.GL_TEXTURE_2D;
		int textureID = iid;
		GL11.glBindTexture(target, textureID); 
		if(bim==null){
			bim = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
		}
		int srcPixelFormat;
		if (bim.getColorModel().hasAlpha()) {
			 srcPixelFormat = GL11.GL_RGBA;
		} else {
			srcPixelFormat = GL11.GL_RGB;
		}
		ByteBuffer textureBuffer = convertImageData(bim); 
		GL11.glTexParameteri(target, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST); 
		GL11.glTexParameteri(target, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR); 
		int width=bim.getWidth();
		int height=bim.getHeight();
		GL11.glTexImage2D(target, 
				0, 
				GL11.GL_RGBA, 
				width, 
				height, 
				0, 
				srcPixelFormat, 
				GL11.GL_UNSIGNED_BYTE, 
				textureBuffer ); 
		return textureID;
	}
	
	void makeTexture(String file){
		int target=GL11.GL_TEXTURE_2D;
		int textureID = TextureCache.createTextureID();
		GL11.glBindTexture(target, textureID); 
		BufferedImage bim = FileManager.loadImage(file); 
		if(bim==null){
			bim = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
		}
		
		int srcPixelFormat;
		if (bim.getColorModel().hasAlpha()) {
			srcPixelFormat = GL11.GL_RGBA;
		} else {
			srcPixelFormat = GL11.GL_RGB;
		}

		ByteBuffer textureBuffer = convertImageData(bim); 


		GL11.glTexParameteri(target, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST); 
		GL11.glTexParameteri(target, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR); 


		int width=bim.getWidth();
		int height=bim.getHeight();

		GL11.glTexImage2D(target, 
				0, 
				GL11.GL_RGBA, 
				width, 
				height, 
				0, 
				srcPixelFormat, 
				GL11.GL_UNSIGNED_BYTE, 
				textureBuffer ); 
	}

	/** make a call to Texture.bind(Core.<b><i>textureID</i></b>) and  the next object to be rendered will use this texture **/
	public static void bind(int textureId) {	
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId); 
	}


	public  static void renew(BufferedImage img,int id){
		int srcPixelFormat1;
		ByteBuffer textureBuffer1;
		int target1=GL11.GL_TEXTURE_2D;
		GL11.glBindTexture(target1, id); 

		if (img.getColorModel().hasAlpha()) {
			srcPixelFormat1 = GL11.GL_RGBA;
		} else {
			srcPixelFormat1 = GL11.GL_RGB;
		}
		textureBuffer1 = convertImageData(img); 
		int wid=img.getWidth();
		int hei=img.getHeight();
		GL11.glTexImage2D(target1, 
				0, 
				GL11.GL_RGBA, 
				wid, 
				hei, 
				0, 
				srcPixelFormat1, 
				GL11.GL_UNSIGNED_BYTE, 
				textureBuffer1 ); 
	}

	public static ByteBuffer convertImageData(BufferedImage bufferedImage) { 
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
	} 
	
	public static BufferedImage base(int w,int h){
		WritableRaster raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,w,h,4,null);
		return new BufferedImage(Texture.glAlphaColorModel,raster,false,new Hashtable());
	}
	
	public static BufferedImage baseA(int w,int h){
		WritableRaster raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,w,h,4,null);
		return new BufferedImage(Texture.glAlphaColorModel,raster,false,new Hashtable());
	}
	/*public void finalize() {
		TextureCache.removeTexture(textureID);
	}*/
}
