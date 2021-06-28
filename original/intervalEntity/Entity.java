package intervalEntity;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import interval.ModelManager;
import interval.Main;
import interval.Render;
import interval.SoundFX;
import interval.Timeline;
import interval.UserData;
import interval.VGO;
import interval.Vec;
import interval.VecF;
import intervalParticle.PGroundPiff;

public class Entity {
	VecF Color=new VecF(1,1,1,1);
	public float x,y,z;//pos
	private float size=1f; //wide
	private float size2=size/2;
	public float height=1.2f; //tall
	float dx=0,dy=0; //set this for goto position rather than x y directly to collision check
	float vz=0,vy=0,vx=0; //velocity
	int gx=0,gy=0; //terrain pos
	float px,py,pz; //previous x and y, used for computing true differences;
	private boolean destroyed=false; //when time is run, this entity will be removed in it's entirety (throughout time)
	boolean sideCollision=false;
	float weight=1;
	float stepHeight=0.2f;
	public float r;
	protected Container contained=null;
	int PIN=0; //ID
	public String properName;
	boolean useable=false;
	boolean grounded=false; //connected to solid ground/object
	private boolean moving=false;
	float top; //dumb physics var
	private float topObjects;
	//private Matrix4f mat=new Matrix4f();
	float[] base;
	TimeBeacon possesion; //for unique timeline
	final static float pit=0.5f; //the additional distance non-traversible from edge of map
	static int inferIndex;

	
	
	
	/////////////////////
	
	
	
	/**
	 * renders entity, independent of time loop, so still visible 
	 */
	public void render(){ 
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, z);
		GL11.glColor4f(Color.x, Color.y, Color.z, (float)(Math.random())); //Color.vx
		VGO v=getModel();
		v.orientPlain(r);
		v.orient(base);
		
		tFling+=0.01f;
		if(tFling>5){
			tHide=tActive;
			tActive+=0.1f;
			if(tHide>=1){
				tHide=0f;
				tActive=0.1f;
			}
			tFling=0f;
		}
		
		v.render();//tHide,tActive,tFling,1);
		GL11.glPopMatrix();
	}
	float tHide=0f;
	float tFling=0f;
	float tActive=0.1f;
	
	/**
	 *  for figuring things out not necessarily dependant on time, stuff here will work even if time is frozen
	 */
	public void logic(){

	}

	public void time(Timeline t){

	}
	public void timeFactored(Timeline t,double F){
		physics(F);
	}
	/**
	 * set entity's time schedule to the specific timeframe
	 */
	public void setTime(long time,int ento){ 
	}
	/**
	 * called before setting world time, generally last minute time travel precautions
	 */
	public void presetTime(long tick,int ento){

	}
	/**
	 * allows this entity to start functioning
	 */
	public void activate(){

	}
	
	public void contactTop(Entity e){

	}
	
	public void contactSide(Entity e){
		if(sideCollision){
			side(e);
		}
	}
	
	public void annual(){

	}
	
	public void fallDamage(){

	}
	public void checkContainer(){
		if(getContained().isDestroyed()){
			uncontain();
		}
	}
	public void contain(Container c){
		this.contained = c;
	}
	public void uncontain(){

	}
	public void use(){

	}
	public void cantUse(Entity e){

	}
	public void used(Entity e){

	}
	public void attack(){

	}
	public void attacked(Entity e,float num){

	}
	
	public void Destroy() {
		this.destroyed = true;
	}

	public void hear(Entity e,String m){
	}
	
	public void possess(TimeBeacon timeb){
		possesion=timeb;
	}
	public void unpossess(){
		possesion=null;
	}
	
	public boolean active(){
		return true;
	}
	public int getPIN(){
		return PIN;
	}
	public Container getContained() {
		return contained;
	}
	public boolean isMoving(){
		return moving;
	}
	public boolean isDestroyed() {
		return destroyed;
	}
	public boolean isUseable(){
		return useable;
	}
	
	/////////////////
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void side(Entity e){
		if(height <=e.stepHeight){
			e.z=(z+height);
			return;
		}
		float xe=e.x;
		float xo=x;
		float ye=e.y;
		float yo=y;

		float x1;
		float x2;
		float y1;
		float y2;
		float total=e.weight+weight;
		if(xo>xe){
			x1=xe+e.getHalfSize();
			x2=xo-getHalfSize();
		}else{
			x1=xe-e.getHalfSize();
			x2=xo+getHalfSize();
		}

		if(yo>ye){
			y1=ye+e.getHalfSize();
			y2=yo-getHalfSize();
		}else{
			y1=ye-e.getHalfSize();
			y2=yo+getHalfSize();
		}

		float amountX=(x1-x2)/(total*2);
		float amountY=(y1-y2)/(total*2);
		if(Math.abs(xo-xe)>Math.abs(yo-ye)){
			dx+=amountX*weight;
			e.dx-=amountX*e.weight;
		}else{
			dy+=amountY*weight;
			e.dy-=amountY*e.weight;
		}
	}
	public boolean submerged(){
		return false;//z<Main.world.land.getWaterHigh(gx,gy);
	}
	public void physics(double rate){ //general physics, not called by default in most cases, add to time() to fix this
		float nextTop=0;
		if(getContained()==null){
			int year=Main.world.timeline.getTerra();

			gx=(int)Math.floor(x);
			gy=(int)Math.floor(y);
			if(gx<0){
				gx=0;
			}
			if(gy<0){
				gy=0;
			}
			Main.world.contacts(this);
			if(getTopObjects()>0){
				top=getTopObjects();
			}else{
				top=(Main.world.land.getHigh(year,gx,gy));
			}

			if(dx<pit){
				dx=pit;
			}
			if(dy<pit){
				dy=pit;
			}
			float mm=Main.level.getStats().getSize()-pit;
			if(dx>mm ){
				dx=mm;
			}
			if(dy>mm ){
				dy=mm;
			}

			nextTop=Main.world.land.getHigh(year,(int)Math.floor(dx), (int)Math.floor(dy));
		}else{
			float smx=getContained().boundsx;
			float smy=getContained().boundsy;
			float emx=getContained().boundex;
			float emy=getContained().boundey;

			if(dx<smx){
				dx=smx;
			}else if(dx>emx){
				dx=emx;
			}

			if(dy<smy){
				dy=smy;
			}else if(dy>emy){
				dy=emy;
			}
			getContained().innerTouch(this);
		}

		int m=Main.level.getStats().getSize()-1;
		pz=z;
		z+=vz*rate;
		float hz=z-top;
		if(z<-2){
			z=-2;
			fallDamage();
		}
		if(hz>0){
			if(submerged()){
				vz-=UserData.getWaterGravity()*rate;
				if(vz<-1){
					vz=-1;
				}
				float zu=Main.world.land.getWaterHigh(gx,gy);
				float z1=zu;
				float z2=zu;
				float z3=zu;
				float z4=zu;

				if(gx>0){
					z1=Main.world.land.getWaterHigh(gx-1,gy);
				}
				if(gx<m){
					z2=Main.world.land.getWaterHigh(gx+1,gy);
				}
				if(gy>0){
					z3=Main.world.land.getWaterHigh(gx,gy-1);
				}
				if(gy<m){
					z4=Main.world.land.getWaterHigh(gx,gy+1);
				}
				if(z1>zu){
					vx+=rate;
				}
				if(z2>zu){
					vx-=rate;
				}

				if(z3>zu){
					vy+=rate;
				}
				if(z4>zu){
					vy-=rate;
				}
			}else{
				vz-=UserData.getGravity()*rate;
			}
			grounded=false;//vz>=-0.02;
		}else{
			if(!grounded){
				grounded=true;
				land();

			}
			vz=0;
			z=top;
		}

		dx+=vx*rate;
		dy+=vy*rate;

		float sz=nextTop-z;
		float sx=(float) (rate*(dx-x)/5f);
		float sy=(float) (rate*(dy-y)/5f);

		if(sz<=stepHeight && Math.abs(vx)<0.01f && Math.abs(vy)<0.01f){
			px=x;
			py=y;
			x+=sx;
			y+=sy;
		}else{
			dx=x;
			dy=y;
		}
		if(Math.abs(sx)<0.006 &&Math.abs(sy)<0.006){
			moving=false;
		}else{
			moving=true;
		}
	}
	public void land(){
		if(vz<-0.38f){
			fallDamage(); //damage
		}
		if(vz<-0.05f){
			for(int u=0;u<5;u++)
				Main.world.addP(new PGroundPiff(x,y,z,vz));
			SoundFX.piff.play();
		}
	}
	public void place(){ //put entity on mouse selector
		place((float) Math.floor(Main.mouseSelector.x)+0.5f,(float) Math.floor(Main.mouseSelector.y)+0.5f);
	}
	public void place(float x,float y){ //put entity on coord, overrides dx dy smoothing stuff
		z=0.2f+Main.world.land.getHigh(Main.world.timeline.getTerra(),(int)x, (int)y);
		Main.world.contacts(this);
		z+=getTopObjects();
		place(x,y,z);
	}
	public void place(float x,float y,float z){ //the hell with proper height on terrain!
		this.x=x;
		this.y=y;
		this.z=z;
		dx=x;
		dy=y;
		vz=0;
		vx=0;
		vy=0;
	}

	public void refresh(){
		grounded=false;
		gx=0;
		gy=0;
	}
	public Entity(boolean t){
		renewDimensions();
	}
	public Entity(){
		keyFinder();
		renewDimensions();
	}
	protected void keyFinder(){
		setPIN(Main.world.allocateID());
	}

	public void renewDimensions(){
		VGO v=getModel();
		size=v.getSize();
		height=v.top;
		base=v.getM();
		size2=size/2f;
	}
	public void setDimensions(float w,float h){
		size=w;
		size2=size/2f;
		height=h;
	}
	public VGO getModel(){
		return ModelManager.getModel("coin");
	}
	public Vec getVec(){
		return new VecF(x,y,z,vx,vy,vz,r);
	}
	public void setVec(VecF v){
		x=v.x;dx=x;
		y=v.y;dy=y;
		z=v.z;
		vx=v.vx;
		vy=v.vy;
		vz=v.vz;
		r=v.r;
	}
	public void setVec(Vec v){
		x=v.x;dx=x;
		y=v.y;dy=y;
		z=v.z;
	}
	public void setPos(float x,float y,float z){
		this.x=x;this.dx=x;
		this.y=y;this.dy=y;
		this.z=z;
	}

	
	
	public  static Entity find(int pin){
		return Main.world.findE(pin);
	}
	
	
	public float getTopObjects() {
		return topObjects;
	}
	public void setTopObjects(float topObjects) {
		this.topObjects = topObjects;
	}
	
	
	public void parseModel(){
		FloatBuffer fb =  BufferUtils.createFloatBuffer(16);
		GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, fb);
		Matrix4f m2=new Matrix4f();
		Matrix4f mat=new Matrix4f();
		m2.load(fb);
		Matrix4f.mul(Main.world.getViewMat(),m2, mat);
		
		Render.shader.coordinateEntity(mat,r);
	}
	public void setPIN(int i) {
		PIN=i;
	}
	
	
	
	public float getSize(){
		return size;
	}
	public float getHalfSize(){
		return size2;
	}
	
	public void Useable(){
		useable=true;
	}
	
	public String[] infer(String param){
		String st[]=param.split(",");
		inferIndex=0;
		setPos(Float.parseFloat(st[0]),
				Float.parseFloat(st[1]),
				Float.parseFloat(st[2]) );

		inferIndex+=3;
		return st;
	}


	/*public Matrix4f getMat(){
		return mat;
	}*/
	
	public String toString(){
		return this.getClass().getSimpleName()+":"+x+","+y+","+z;
	}
	public void extra(String s){

	}
	public void considerTime(){
		Timeline t;
		if(possesion!=null && possesion.active()){
			t=possesion.getTimeline();
		}else{
			if(possesion!=null)
				unpossess();
			
			t=Main.world.timeline;
		}
		if(!t.shouldSkip())
			time(t);

		timeFactored(t,t.getTimeFactor());
	}
	


	public float groundHeight(){
		return z-top;
	}
}
