package intervalHistory;

import java.util.Random;

public class Gene {

	byte skin=0; //0 black, 1 white, 0.5 tan
	//short hair=0; //0 black,dark,brown,light
	byte light=0;//pheomelanin
	byte red=0; //euomelanin
	byte weight=0;
	
	public Gene(int s,int w,int l,int r){
		setSkin(s);
		weight=(byte) w;
		setHair(l,r);
	}
	public void setSkin(int s){
		skin=(byte) (s-127);
	}
	public void setHair(int l,int r){
		light=(byte) (l-127);red=(byte) (r-127);
	}
	public Gene(Random r,Gene g1,Gene g2){
		skin=(byte) (r.nextInt(10)+(g1.skin+g2.skin)/2.1);
		weight=(byte)(r.nextInt(10)+(g1.weight+g2.weight)/2.1);
		light=(byte)(r.nextInt(10)+(g1.light+g2.light)/2.1);
		red=(byte)(r.nextInt(10)+(g1.red+g2.red)/2.1);
	}
	public int Red(){
		return ((int)red)+127;
	}
	public int Light(){
		return ((int)light)+127;
	}
	public int Skin(){
		return ((int)skin)+127;
	}
}
