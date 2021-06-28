package interval;


import org.lwjgl.opengl.GL11;

public class VGO {

	/**
	 * middle values
	 */
	float mx,my,mz;
	public float top;
	private float size;
	private float sizeX;
	private float sizeY;
	private float volume;
	
	String texture;
	Vox[] v;
	String Name="";
	String num[];
	int num2[];
	boolean loaded=false;

	public VGO(){
		v=new Vox[0];
	}
	public String getName(){
		return Name;
	}

	public VGO(String name,float mx,float my,float mz,float top,float wideX,float wideY,String tex,Vox[] V){
		Name=name;
		v=V;
		this.mx=mx;
		this.my=my;
		this.mz=mz;
		this.texture=tex;
		this.top=top;
		this.setSize(wideX,wideY);
	}

	public void set(String s[],int i[]){
		num=s;
		num2=i;
	}

	public void render(){
		render(texture);
	}
	public void render(String s){
		render(Texture.get(s));
	}
	
	public void render(int t){
		Texture.bind(t);
		GL11.glBegin(GL11.GL_QUADS);
		if(num!=null){
			int ti=0;
			int cap=num2[ti];
			for( int i=0;i<v.length;i++){
				if(i>=cap){
					GL11.glEnd();
					Texture.bind(num[ti]);
					ti++;
					if(ti>=num.length){
						cap=v.length;
					}else
						cap=num2[ti];
					GL11.glBegin(GL11.GL_QUADS);
				}
				v[i].apply();
			}
		}else{
			for(Vox vv:v){
				vv.apply();
			}
		}
		GL11.glEnd();
	}


	public void plat(){
		GL11.glPushMatrix();
		GL11.glTranslatef(-0.5f, -0.5f, -mz);
		Texture.bind(0);
		GL11.glColor4f(0.1f, 0.1f, 0.2f, 1);
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glVertex3f(0,0,0);
		GL11.glVertex3f(1,0,0);
		GL11.glVertex3f(1,1,0);
		GL11.glVertex3f(0,1,0);

		GL11.glVertex3f(0,0,-1);
		GL11.glVertex3f(1,0,-1);
		GL11.glVertex3f(1,1,-1);
		GL11.glVertex3f(0,1,-1);

		GL11.glColor4f(0.1f, 0.2f, 0.1f, 1);

		GL11.glVertex3f(0,0,-1);
		GL11.glVertex3f(1,0,-1);
		GL11.glVertex3f(1,0,0);
		GL11.glVertex3f(0,0,0);

		GL11.glVertex3f(0,1,-1);
		GL11.glVertex3f(1,1,-1);
		GL11.glVertex3f(1,1,0);
		GL11.glVertex3f(0,1,0);

		GL11.glColor4f(0.2f, 0.1f, 0.1f, 1);
		GL11.glVertex3f(1,0,-1);
		GL11.glVertex3f(1,1,-1);
		GL11.glVertex3f(1,1,0);
		GL11.glVertex3f(1,0,0);

		GL11.glVertex3f(0,0,-1);
		GL11.glVertex3f(0,1,-1);
		GL11.glVertex3f(0,1,0);
		GL11.glVertex3f(0,0,0);

		GL11.glEnd();

		GL11.glPopMatrix();
	}
	public String toString(){
		return Name;
	}
	public float getSizeX() {
		return sizeX;
	}
	public float getSizeY() {
		return sizeY;
	}
	public float getSize() {
		return size;
	}
	public float getVolume() {
		return volume;
	}
	public void setSize(float wideX, float wideY) {
		this.sizeX = wideX;
		this.sizeY = wideY;
		newSize();
	}
	public void newSize(){
		this.size=Math.max(sizeX,sizeY)*2;
		float sizeZ=(top-mz);
		volume=4*sizeX*sizeY*sizeZ;
	}
	public float getTop() {
		return top;
	}
	public float[] getM(){
		return new float[]{-mx,-my,-mz};
	}
	public void orient(float r){
		GL11.glRotatef(r, 0,0, 1);
		orient();
	}
	public void orientPlain(float r){
		GL11.glRotatef(r, 0,0, 1);
	}
	public void orient(float[] m){
		GL11.glTranslatef(m[0],m[1],m[2]);
	}
	public void orient(){
		GL11.glTranslatef(-mx,-my,-mz);
	}

	public void render(float hidden,float affected,float vel,float Height){
		Texture.bind(texture);
		int A=(int)((1-affected)*v.length);
		int S=(int) ((1-hidden)*v.length);
		for(int u=0;u<S;u++){
			if(u>A)
				v[u].applyD(vel,Height);
			else
				v[u].apply();
		}
	}
	public void render(float vel,float Height){
		Texture.bind(texture);
		for( Vox o:v){
			o.applyD(vel,Height);
		}
	}
	
	
	

}
