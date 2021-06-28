package interval;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Hashtable;
import org.lwjgl.opengl.GL11;

public class Tex {
	int ID;
	public BufferedImage img;
	ByteBuffer pixels;
	public Graphics2D g2;
	private int wide=768;
	private int high=600;
	private int type;
	public Tex(int i){
		ID=i;
	}
	public Tex(){
		setType(GL11.GL_RGBA);
		WritableRaster raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,getWide(),getHigh(),4,null);
		img = new BufferedImage(Texture.glAlphaColorModel,raster,false,new Hashtable());
		//GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST); 
		//GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR); 
		g2=img.createGraphics();
		
		BufferedImage text=new BufferedImage(60,30,BufferedImage.TYPE_INT_ARGB);
		Graphics2D gg=text.createGraphics();
		gg.setColor(Color.green);
		gg.drawString("DEFAULT", 2, 20);
		g2.drawImage(text, 0, 0,getWide(),getHigh(),0,0,60,30, null);
		step2();
	}
	public Tex(String texture){
		texture=FileManager.perfectTexture(texture);
		BufferedImage bim=TextureCache.loadImage(texture);
		setWide(bim.getWidth());
		setHigh(bim.getHeight());
		WritableRaster raster;
		if(bim.getColorModel().hasAlpha()){
			setType(GL11.GL_RGBA);
			raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,getWide(),getHigh(),4,null);
			img = new BufferedImage(Texture.glAlphaColorModel,raster,false,new Hashtable());
		}else{
			setType(GL11.GL_RGB);
			raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,getWide(),getHigh(),3,null);
			img = new BufferedImage(Texture.glColorModel,raster,false,new Hashtable());
		}
		g2=img.createGraphics();
		g2.drawImage(bim, 0,0,null);
		step2();
	}
	/*public static BufferedImage base(int w,int h){
		WritableRaster raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,w,h,3,null);
		return new BufferedImage(Texture.glColorModel,raster,false,new Hashtable());
	}*/

	
	public Tex(BufferedImage bim){
		setWide(bim.getWidth());
		setHigh(bim.getHeight());
		WritableRaster raster;
		if(bim.getColorModel().hasAlpha()){
			setType(GL11.GL_RGBA);
			raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,getWide(),getHigh(),4,null);
			img = new BufferedImage(Texture.glAlphaColorModel,raster,false,new Hashtable());
		}else{
			
			setType(GL11.GL_RGB);
			raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,getWide(),getHigh(),3,null);
			img = new BufferedImage(Texture.glColorModel,raster,false,new Hashtable());
		}
		g2=img.createGraphics();
		g2.drawImage(bim, 0,0,null);
	step2();
	}
	public void step2(){
		ID=TextureCache.createTextureID();
		
		byte[] data = ((DataBufferByte) img.getRaster().getDataBuffer()).getData(); 
		pixels = ByteBuffer.allocateDirect(data.length); 
		pixels.order(ByteOrder.nativeOrder());
		
		
		pixels.put(data, 0, data.length); 
		pixels.flip();
		//Message.m("ID "+ ID);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, ID);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST); 
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, getType(), getWide(), getHigh(), 0, getType(), GL11.GL_UNSIGNED_BYTE, pixels);
	
	}
	
	public int getID(){
		return ID;
	}
	
	 protected void finalize() {
		TextureCache.removeTexture(ID);
	 }
	
	public void renew(){
		
		byte[] data = ((DataBufferByte) img.getRaster().getDataBuffer()).getData(); 
		pixels.put(data, 0, data.length); 
		pixels.flip();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, ID);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, getType(), getWide(), getHigh(), 0, getType(), GL11.GL_UNSIGNED_BYTE, pixels);
	}
	
	public void renew2(){
		byte[] data = ((DataBufferByte) img.getRaster().getDataBuffer()).getData(); 
		pixels = ByteBuffer.allocateDirect(data.length); 
		pixels.order(ByteOrder.nativeOrder());
		pixels.put(data, 0, data.length); 
		pixels.flip();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, ID);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, getType(), getWide(), getHigh(), 0, getType(), GL11.GL_UNSIGNED_BYTE, pixels);
	}
	
	public void bind(){
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, ID);
	}
	public int getWide() {
		return wide;
	}
	public void setWide(int wide) {
		this.wide = wide;
	}
	public int getHigh() {
		return high;
	}
	public void setHigh(int high) {
		this.high = high;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

}
