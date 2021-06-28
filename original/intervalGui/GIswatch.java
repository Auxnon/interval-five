package intervalGui;

import interval.FileManager;
import java.awt.image.BufferedImage;

public class GIswatch extends GIpic {
	
	public GIswatch(String a,String s, float xx, float yy, float sxx, float syy) {
		this(a,FileManager.loadImage(s),xx,yy,sxx,syy);
	}
	
	public GIswatch(String a,BufferedImage img, float xx, float yy, float sxx, float syy) {
		super(img, xx, yy, sxx, syy);
		name=a;
		action=a;
	}
	
	public String release(){
		int t=(int) (256* (mx-x)/sx);
		int k=(int) (256* (1-((my-y)/sy)));
		return name+t+";"+k;
	}

}
