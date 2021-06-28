package intervalGui;

import interval.IFont;
import interval.Message;
import interval.Tex;
import interval.VecF;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class GIdialog extends GI {

	int backType;
	BufferedImage clear;
	BufferedImage backpane;
	String message="";
	public GIdialog(int m,int type){
		colorOver=new VecF(0.9f,0.8f,0.6f,1f);
		selection=colorNormal;
		//int ssx=(int) (IFont.length(s));
		BufferedImage bim =new BufferedImage(256,48,BufferedImage.TYPE_INT_ARGB);
		clear=new BufferedImage(256,48,BufferedImage.TYPE_INT_ARGB);
		backpane=new BufferedImage(256,48,BufferedImage.TYPE_INT_ARGB);
		sx=64;
		sy=12;
		x=0;
		switch(m){
		case 1:y=38;break;
		case 2:y=64;break;
		default:y=12;
		}
		name="GIdialog";
		action="dialo";
		Graphics2D g=bim.createGraphics();
		IFont.backer(type, g, 0, 0, 3, 256);
		backpane.setData(bim.getData());
		backType=type;
		tex=new Tex(bim);
	}
	public boolean set(int type){
		if(backType!=type){
			backType=type;
			tex.img.setData(clear.getData());
			IFont.backer(type, tex.g2, 0, 0, 3, 256);
			backpane.setData(tex.img.getData());
			tex.renew();
			return true;
		}
		return false;
	}
	public void message(String m,int t){
		boolean bo=set(t);
		if(bo || !message.equals(m)){
			
			if(!bo &&  !m.startsWith(message)){
				tex.img.setData(backpane.getData());
			} 
			int s =IFont.length(m);
			int d=(int) Math.ceil(s/256.0);
			//int
			String s1=m.substring(0, d);
			int countr=0;
			while(IFont.length(s1)>256){
				d--;countr++;
				s1=m.substring(0,d);
				Message.m(this, "so many "+countr);
			}
			Message.m(this,"derf"+s);
			IFont.write(tex.g2, s1, 4, 4);
			tex.renew();
			
		}
		message=m;
	}
}
