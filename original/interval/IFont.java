package interval;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class IFont {

	static HashMap<String,Integer> X;
	static HashMap<String,Integer> size;
	public static BufferedImage im;
	public static BufferedImage back;
	public static final short BLANK=-1;
	public static final short CLASSIC=1;
	public static final short PARCHMENT=4;
	public static final short PILLAR=0;
	public static final short PURPLE=3;
	public static final short EMBOSS=5;
	public static final short BLACK=6;

	static{
		X= new HashMap<String,Integer>();
		size= new HashMap<String,Integer>();
		im=FileManager.loadImage("letter4.png");	
		back=FileManager.loadImage("back.png");	
		String ss="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890!@#$%^&*()-+_=~`{}|[]\\:;\"'<>?,./";

		for(int i=0;i<ss.length();i++){
			String st=ss.substring(i, i+1);
			X.put(st, i);
		}
		for(int i=0;i<26;i++){
			size.put(ss.substring(i, i+1),7);
		}
		/*for(int i=26;i<78;i++){
			size.put(ss.substring(i, i+1),6);
		}*/
		//size.put("E", 6);
		//size.put("F", 6);
		//size.put("I", 6);
		//size.put("L", 6);
		size.put("c", 5);
		size.put("i", 2);
		size.put("j", 5);
		size.put("k", 5);
		size.put("l", 2);
		size.put("t", 4);
		size.put("1", 4);
		size.put("0", 7);
		
		size.put("!", 3);
		size.put("@", 8);
		size.put("#", 7);
		size.put("$", 7);
		size.put("%", 7);
		size.put("&", 7);
		size.put("*", 4);
		size.put("(", 4);
		size.put(")", 4);
		size.put("-", 4);
		size.put("+", 4);
		size.put("_", 7);
		size.put("=", 4);
		size.put("`", 4);
		size.put("}", 5);
		size.put("{", 5);
		size.put("|", 2);
		size.put("]", 4);
		size.put("[", 4);
		size.put("\\", 5);
		size.put(":", 2);
		size.put(";", 3);
		size.put("\"", 5);
		size.put("'", 3);
		size.put("<", 5);
		size.put(">", 5);
		size.put(",", 3);
		size.put(".", 2);
		size.put("/", 5);
		
		X.put("~c",104); size.put("~c", 8);
		X.put("~t",105); size.put("~t", 7);
		X.put("~s",106); size.put("~s", 8);
		X.put("~a",107); size.put("~a", 8);
		
		X.put("~k",109); size.put("~k", 15);
		X.put("~l",111); size.put("~l", 5);
		X.put("~r",112); size.put("~r", 5);
		X.put("~m",113); size.put("~m", 5);
		X.put("~/",114); size.put("~/", 8);
		X.put("~^",115); size.put("~^", 8);
		X.put("~v",116); size.put("~v", 8);
		X.put("~<",117); size.put("~<", 7);
		X.put("~>",118); size.put("~>", 7);
	}

	public static int length(String s){
		int i=0;
		for(int u=0;u<s.length();u++){
			String rt=s.substring(u, u+1);
			if(rt.contains("~")){
				rt=s.substring(u, u+2);
				u++;
			}
			Integer k=size.get(rt);
			int h=6;
			if(k!=null)
				h=k;

			i+=h;
		}

		return (int) ((Math.ceil((i+8)/8.0))*8);
	}

	/*public static BufferedImage write(BufferedImage b,String s,int x,int y){

		Graphics2D g2= b.createGraphics();
		letter("a",g2,0,10);
		return b;
	}*/
	public static void message(Graphics2D g2,String s,int x,int y,int j){
		message( g2, s,length(s), x, y, j);
	}
	public static void message(Graphics2D g2,String s,int size,int x,int y,int j){
		if(j!=BLANK){
			backer(j,g2,x,y,size);
		}
		write(g2,s,x+4,y+4);
	}
	public static void message2(Graphics2D g2,String s,int x,int y,int j){
		if(j!=BLANK){
			backer(j,g2,x,y,s.length()+1);
		}
		write2(g2,s,x+6,y+4);
	}
	public static int getPara(String s,int line){
		int n=s.length()+1;
		int h=(int) Math.ceil((float)n/(float)line); //vertical lines of text
		return h;
	}
	
	public static void message(Graphics2D g2, String s, int size, int x, int y,
			int j, BufferedImage text, Color backr) {
		if(j!=BLANK){
			backer(j,g2,x,y,size,backr);
		}
		write(g2,s,x+4,y+4,text);
	}

	public static void message(Graphics2D g2,String s,int x,int y,int j, int ssx,int ssy){
		//backer(j,g2,x,y,s.length()+1);
		//int n=s.length()+1;
		//int h=(int) Math.ceil((float)n/(float)line);
		para(j,g2,x,y,ssx+1,ssy);
		for(int i=0;i<ssy;i++){
			int ss=(i+1)*ssx;
			if(s.length()<ss){
				ss=s.length();
			}
			write(g2,s.substring(i*ssx,ss) ,x+6,(i*6)+y+4);
			//c++;
		}
		//write(g2,s,x+6,y+4);
	}
	/*public static void write(Graphics2D g2,String s,int x,int y,Color txt){
		if(txt==null){
			write(g2,s,x,y,im);
		}else{
			write(g2,s,x,y,newIm(txt.getRGB(),im,-1));
		}
	}*/
	public static void write(Graphics2D g2,String s,int x,int y){
		write(g2,s,x,y,im);
	}
	public static void write(Graphics2D g2,String s,int x,int y,BufferedImage imma){
		int ind=0;
		for(int i=0;i<s.length();i++){
			String letter=s.substring(i, i+1);
			if(letter.contains("~")){
				letter=s.substring(i, i+2);
				i++;
			}
			Integer k=size.get(letter);
			int u=6;
			if(k!=null){
				u=k;
			}
			letter(letter,g2,x+ind,y,u,imma);
			ind+=u;
		}
	}
	public static void write2(Graphics2D g2,String s,int x,int y){
		for(int i=0;i<s.length();i++){
			letter2(s.substring(i, i+1),g2,x+i*6,y);
		}
	}
	public static void letter(String letter,Graphics2D g2,int x,int y,int s){
		letter(letter,g2,x,y,s,im);
	}
	public static void letter(String letter,Graphics2D g2,int x,int y,int s,BufferedImage imm){
		if(X.containsKey(letter)){
			int fro=X.get(letter);
			int xx=fro%26;
			int yy=fro/26;
			g2.drawImage(imm, x, y, x+s, y+8, xx*8, yy*8, (xx*8)+s, yy*8+8, null); //Color.blue,
			//g2.drawImage(im, x, y, x+6, y+6, xx*6, yy*6, xx*6+6, yy*6+6, null);
		}
	}
	public static void letter2(String letter,Graphics2D g2,int x,int y){
		if(X.containsKey(letter)){
			int fro=X.get(letter);
			int xx=fro%26;
			int yy=fro/26;
			g2.drawImage(im, x, y, x+6, y+6, xx*6, yy*6, xx*6+6, yy*6+6, null); //Color.blue,
			//g2.drawImage(im, x, y, x+6, y+6, xx*6, yy*6, xx*6+6, yy*6+6, null);
		}
	}

	public static BufferedImage newIm(int n,BufferedImage original,int c){
		int w=original.getWidth();
		int h=original.getHeight();
		BufferedImage b2=new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
		b2.setData(original.getData());
				//original.getSubimage(0, 0, w, h);
		for(int xx=0;xx<w;xx++){
			for(int yy=0;yy<h;yy++){
				if(b2.getRGB(xx, yy)==c){
					b2.setRGB(xx, yy, n);
				}
			}
		}
		return b2;
	}

	public static void backer(int j,Graphics2D g2,int x,int y,int size,Color c){
		g2.setColor(c);
		g2.fillRect(x, y, size, 16);
		g2.setColor(Color.gray);
		g2.drawRect(x, y, size-1, 15);
	}
	public static void backer(int j,Graphics2D g2,int x,int y,int size){
		size/=8;
		size-=1;
		j++;
		short xe=(short) ((j%2)*48);
		short ye=(short) (16*Math.floor(j/3));

		g2.drawImage(back, x, y, x+8, y+16, xe, ye, xe+8, ye+16, null);
		for(int i=1;i<size;i++){
			g2.drawImage(back, x+i*8, y, ((i+1)*8)+x, y+16, xe+16, ye, xe+24, ye+16, null);
		}
		g2.drawImage(back, x+size*8, y, ((size+1)*8)+x, y+16, xe+8, ye, xe+16, ye+16, null);
	}
	public static void backer(int j,Graphics2D g2,int x,int y,int lines,int size){
		size/=8;
		size-=1;
		j++;
		short xe=(short) ((j%2)*48);
		short ye=(short) (16*Math.floor(j/3));
		g2.drawImage(back, x, y, x+8, y+8, xe, ye, xe+8, ye+8, null);
		g2.drawImage(back, x, y+8, x+8, y+16, xe+32, ye, xe+40, ye+8, null);
		int n;
		for(n=0;n<lines;n++){

			for(int i=1;i<size;i++){
				if(n==0){
					g2.drawImage(back, x+i*8, y+n*16, ((i+1)*8)+x, y+n*16+8, xe+16, ye, xe+24, ye+8, null);
					g2.drawImage(back, x+i*8, y+n*16+8, ((i+1)*8)+x, y+n*16+16, xe+24, ye+8, xe+32, ye+16, null);
				}else if(n==lines-1){
					g2.drawImage(back, x+i*8, y+n*16+8, ((i+1)*8)+x, y+n*16+16, xe+16, ye+8, xe+24, ye+16, null);
					g2.drawImage(back, x+i*8, y+n*16, ((i+1)*8)+x, y+n*16+8, xe+24, ye, xe+32, ye+8, null);
				}else{
					g2.drawImage(back, x+i*8, y+n*16, ((i+1)*8)+x, y+n*16+16, xe+24, ye, xe+32, ye+16, null);
				}
			}
			if(n>0){
				g2.drawImage(back, x, n*16+y, x+8, n*16+y+16, xe+32, ye, xe+40, ye+16, null);
				g2.drawImage(back, x+size*8, n*16+y, ((size+1)*8)+x, n*16+y+16, xe+40, ye, xe+48, ye+16, null);
			}
		}

		g2.drawImage(back, x+size*8, y, ((size+1)*8)+x, y+8, xe+8, ye, xe+16, ye+8, null);
		g2.drawImage(back, x+size*8, y+8, ((size+1)*8)+x, y+16, xe+40, ye+8, xe+48, ye+16, null);

		int lin=lines-1;
		g2.drawImage(back, x, lin*16+y, x+8, lin*16+y+8, xe+32, ye, xe+40, ye+8, null);
		g2.drawImage(back, x, lin*16+y+8, x+8, lin*16+y+16, xe, ye+8, xe+8, ye+16, null);

		g2.drawImage(back, x+size*8, lin*16+y, ((size+1)*8)+x, lin*16+y+8, xe+40, ye, xe+48, ye+8, null);
		g2.drawImage(back, x+size*8, lin*16+y+8,((size+1)*8)+x, lin*16+y+16, xe+8, ye+8, xe+16, ye+16, null);
	}
	public static void backer2(int j,Graphics2D g2,int x,int y,int size){
		j++;
		short xe=(short) ((j%2)*48);
		short ye=(short) (16*Math.floor(j/3));

		g2.drawImage(back, x, y, x+6, y+16, xe, ye, xe+6, ye+16, null);
		for(int i=1;i<size;i++){
			g2.drawImage(back, x+i*6, y, (i*6)+x+6, y+16, xe+16, ye, xe+22, ye+16, null);
		}
		g2.drawImage(back, x+size*6, y, (size*6)+x+6, y+16, xe+10, ye, xe+16, ye+16, null);
	}
	public static void para(int j,Graphics2D g2,int x,int y,int sx,int sy){
		j++;
		short xe=(short) ((j%2)*48);
		short ye=(short) (16*Math.floor(j/3));
		int sxx=sx -6;
		int syy=sy-6;
		int sx2=sx/6;
		int sy2=sy/6;
		for(int xi=1;xi<sx2;xi++){
			for(int yi=1;yi<sy2;yi++){
				g2.drawImage(back, x+xi*6, y+yi*6, (xi*6)+x+6, (yi*6)+y+6, xe+5, ye+5, xe+11, ye+11, null);
			}
		}
		for(int xi=1;xi<sx2;xi++){
			g2.drawImage(back, x+xi*6, y+syy, (xi*6)+x+6, syy+y+6, xe+16, ye+10, xe+22, ye+16, null);
		}
		for(int xi=1;xi<sx2;xi++){
			g2.drawImage(back, x+xi*6, y, (xi*6)+x+6, y+6, xe+16, ye, xe+22, ye+6, null);
		}
		for(int yi=1;yi<sy2;yi++){
			g2.drawImage(back, x, y+yi*6, x+6, (yi*6)+y+6, xe+32, ye, xe+38, ye+6, null);
		}
		for(int yi=1;yi<sy2;yi++){
			g2.drawImage(back, x+sxx, y+yi*6, sxx+x+6, (yi*6)+y+6, xe+42, ye, xe+48, ye+6, null);
		}
		g2.drawImage(back, x, y, x+6, y+6, xe, ye, xe+6, ye+6, null);
		g2.drawImage(back, x, y+syy, x+6, (syy)+y+6, xe, ye+10, xe+6, ye+16, null);
		g2.drawImage(back, x+sxx, y, x+6+sxx, y+6, xe+10, ye, xe+16, ye+6, null);
		g2.drawImage(back, x+sxx, y+syy, x+6+sxx,(syy)+y+6, xe+10, ye+10, xe+16, ye+16, null);
	}
	
	public static Color getC(int w,int max) {
		Color c;
		float M=0.5f+(6*(float)w/(float)max);
		if(M>1){
			if(M>2){
				if(M>3){
					if(M>4){
						if(M>5){
							float h=M-5.5f;
							c=new Color(1,h,h);
						}else
							c=new Color(1,5-M,0);
					}else
						c=new Color(M-3,1,0);
				}else
					c=new Color(0,1,3-M);
			}else
				c=new Color(0,M-1,1);
		}else
			c=new Color(0,0,M);
		
		//0 0 0
		//b+
		//g+
		//b-
		//r+
		//g-
		//g+ b+
		return c;
	}


}
