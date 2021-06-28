package intervalStructure;

import interval.Main;
import interval.Spin;
import interval.VGO;
import intervalEntity.Entity;

import org.lwjgl.opengl.GL11;

public class Structure {

	public int x;
	public int y;
	public float z;
	public float height=1;
	public float sx=1f;
	public float sy=1f;
	int startYear=0;
	long startTick=0;
	int age=1;
	int lifetime=2000;
	private float size=1f;
	
	private float r;
	private float  nrx,nry;
	
	private boolean present=false;
	private boolean dependant=false;
	boolean preParticle=false;
	
	boolean inner=false;
	
	public Structure(){
		startYear=Main.world.timeline.getTerra();
		startTick=Main.world.timeline.getTick();
		size(getModel());
	}
	VGO vgo;
	public VGO getModel(){
		return vgo;
	}
	public void rot(float r){
		this.r=(int)r;
		float ff[]=Spin.get(r);
		nrx=ff[0]*size;
		nry=ff[1]*size;
	}
	public void size(VGO vgo2){
		size=vgo2.getSize();
		sx=vgo2.getSize();
		sy=vgo2.getSize();
	}
	
	public boolean claimed(float xx,float yy,float ssx,float ssy){
		if(isDependant() ||(!isDependant() && isPresent())){
		if(xx+ssx > this.x-sx && xx-ssx<this.x+sx){
			if(yy+ssy > this.y-sy && yy-ssy<this.y+sy){
				return true;
			}
		}}
		return false;
	}
	
	public float getR(){
		return r;
	}
	public float getNRX(){
		return nrx;
	}
	public float getNRY(){
		return nry;
	}
	public void render(){
		if(isPresent()){
			GL11.glPushMatrix();
			GL11.glTranslatef(x, y, z);
			Render();
			GL11.glPopMatrix();
		}
	}
	public void time(){
		age=Main.world.timeline.getTerra()-startYear;
		setPresent(((Main.world.timeline.getTerra()>startYear) || (Main.world.timeline.getTerra()==startYear&&Main.world.timeline.getTick()>startTick))&& age<=lifetime);
	}
	
	public void setTime(long i){
		//age=startYear+i;
		renew();
	}
	
	public void logic(){
		
	}
	public void renew(){
		age=1;
	}
	
	public void Render(){
		
	}

	public void place(int x,int y){
		this.x=x;
		this.y=y;
		this.z=Main.world.land.getHigh(Main.world.timeline.getTerra(),x, y);
	}
	
	public void place(int x,int y,float z){
		this.x=x;
		this.y=y;
		this.z=z;
	}
	public void contactSide(Entity e){
		
	}
	public void contactTop(Entity e){
		
	}
	public boolean isPresent() {
		return present;
	}
	public void setPresent(boolean present) {
		this.present = present;
	}
	public boolean isDependant() {
		return dependant;
	}
	public void setDependant(boolean dependant) {
		this.dependant = dependant;
	}

}
