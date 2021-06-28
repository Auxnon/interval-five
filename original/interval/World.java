package interval;

import intervalEntity.Container;
import intervalEntity.Entity;
import intervalEntity.Player;
import intervalEntity.PlayerPast;
import intervalHistory.Society;
import intervalParticle.Particle;
import intervalStructure.Structure;
import java.awt.image.BufferedImage;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javax.swing.JOptionPane;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

public class World {
	private HashMap<Integer,Entity> entities;
	private ArrayList<Entity> ents2;
	public ArrayList<Structure> structures;
	public ArrayList<Particle> particles;
	public Timeline timeline=new Timeline();
	public Land land;
	public int[] defaultAnnual;
	public int ID=0;
	public Random rand;
	public Random pseudo;
	public HashMap<Integer,Integer> mortgage;
	
	public Society society;
	
	public int allocateID(){
		int iii=rand.nextInt()+1;
		int failsafe=0;
		while((findE(iii)!=null) && failsafe <30){
			iii=rand.nextInt()+1;
			failsafe++;
		}
		if(failsafe>=30){
			JOptionPane.showMessageDialog(null, "failed to generate unique entity id!", "Entity Collapse", JOptionPane.ERROR_MESSAGE);
		}
		return iii;
	}
	public Entity findE(String name){
		int m=sizeE();
		for(int i=0;i<m;i++){
			Entity e2 = getE(i);
			if(e2.properName!=null &&e2.properName.equalsIgnoreCase(name)){
				return e2;
			}
		}
		return null;
	}
	public Entity findE(int id){
		if(id==Main.player.getPIN())
			return Main.player;
		return entities.get(id);
	}

	public World(){
		timeline=new Timeline();
		entities=new HashMap<Integer,Entity>();
		ents2=new ArrayList<Entity>();
		structures=new ArrayList<Structure>();
		particles = new ArrayList<Particle>();
		defaultAnnual=new int[1];
		defaultAnnual[0]=-1;
		society = new Society();
		rand = new Random();
		rand.setSeed(ID);
	}
	public void initL(){
		if(defaultAnnual.length!=Main.level.getStats().getCapEntSnaps()){
			defaultAnnual=new int[Main.level.getStats().getCapEntSnaps()];
			for(int h=1;h<defaultAnnual.length;h++){
				defaultAnnual[h]=-1;
			}
		}
		
		pseudo=new Random();
		land = new Land();
		mortgage = new HashMap<Integer,Integer>();
	}
	public void init(){
		initL();
		if(!land.corrupted){
			land.defaultAllLand();
			land.time();
		}
	}
	public void init(BufferedImage[] i){
		initL();
		if(i!=null){
			land.loadLand(i[0], i[1]);
		}else{
			land.defaultAllLand();
		}
		land.time();
	}
	public void init(BufferedImage i,BufferedImage j){
		initL();
		land.loadLand(i, j);
		land.time();
	}
	public void useE(Entity ei,int n,float wide){
		int m=sizeE();
		float dd=ei.getHalfSize();
		float d[]=new float[4];
		d[0]=dd;
		d[1]=dd;
		d[2]=dd;
		d[3]=dd;
		d[n]+=wide;

		for(int i=0;i<m;i++){
			Entity e=getE(i);
			if(e!=ei && e.active()){
				if(e.x-e.getHalfSize()<(ei.x+d[3]) && e.x+e.getHalfSize()>(ei.x-d[1])){
					if(e.y-e.getHalfSize()<(ei.y+d[0]) && e.y+e.getHalfSize()>(ei.y-d[2])){
						float ze=(e.z+e.height);
						if(ei.z<ze && ei.z+0.5f>=e.z){

							if(e.isUseable())
								e.used(ei);
							else
								ei.cantUse(e);

						}
					}
				}
			}
		}
	}
	public void attackE(Entity ei,float damage,float distance,float wide){
		int m=sizeE();
		float d2=distance*2;
		for(int i=-1;i<m;i++){
			Entity e=getE2(i);
			if(e!=ei && e.active()){
				if(e.x<(ei.x+d2) && e.x>(ei.x-d2)){
					if(e.y<(ei.y+d2) && e.y>(ei.y-d2)){
						float ze=(e.z+e.height);
						if(ei.z<ze && ei.z+0.5f>=e.z){
							float xo=e.x-ei.x;
							float yo=e.y-ei.y;
							float or =(float) Math.toDegrees(Math.atan2(yo, xo));
							float dor=ei.r-or;
							if(dor>180){
								dor-=180;
							}
							if(true){
								float zo=e.z-ei.z;
								float ro=(float) Math.sqrt(xo*xo + yo*yo + zo*zo);
								if(ro<distance){
									e.attacked(ei, damage);
								}
							}	
						}
					}
				}
			}
		}
	}
	public void contacts(Entity ei){
		int m=sizeE();
		float highest=0;
		
		
		for(int i=-1;i<m;i++){
			
			Entity e=i==-1?Main.player:getE(i);
			if(e!=ei && e.active() && e.getContained()==null){
				if(e.x-e.getHalfSize()<(ei.x+ei.getHalfSize()) && e.x+e.getHalfSize()>(ei.x-ei.getHalfSize())){
					if(e.y-e.getHalfSize()<(ei.y+ei.getHalfSize()) && e.y+e.getHalfSize()>(ei.y-ei.getHalfSize())){
						float ze=(e.z+e.height);
						if(ei.z>=ze){
							if((ei.z -ze)==0){//<0.2f){
								e.contactTop(ei);
							}
							if(ze>highest){
								highest=ze;
							}
						}else if(ei.z+0.1f>=e.z){
							e.contactSide(ei);
						}
					}
				}
			}
		}
		m=sizeS();
		for(int k=0;k<m;k++){
			Structure s = getS(k);
			if(s.isPresent()){
				if(s.x-s.sx<(ei.x+ei.getHalfSize()) && s.x+s.sx>(ei.x-ei.getHalfSize())){
					if(s.y-s.sy<(ei.y+ei.getHalfSize()) && s.y+s.sy>(ei.y-ei.getHalfSize())){
						float ze=(s.z+s.height);
						if(ei.z>ze){
							if((ei.z -ze)<0.2f){
								s.contactTop(ei);
							}
							if(ze>highest){
								highest=ze;
							}
						}else{
							s.contactSide(ei);
						}
					}
				}
			}
		}
		ei.setTopObjects(highest);
	}
	
	public int getLot(int x,int y){
		return mortgage.get(x+(y<<land.bit));
	}
	public void buyLot(int H,int x,int y){
		mortgage.put(x+(y<<land.bit), H);
	}
	public void sellLot(int x,int y){
		mortgage.remove(x+(y<<land.bit));
	}
	public void communicate(Entity s,float range,String message){
		for(Entity e:this.ents2){
			float ex=s.x-e.x;
			float ey=s.y-e.y;
			float ez=s.z-e.z;
			float er=(float) Math.sqrt(ex*ex + ey*ey + ez*ez);
			if(er<=range){
				e.hear(s, message);
			}
		}
	}
	public Entity nearest(float min,Entity ee){
		String s=ee.getClass().getSimpleName();
		int m=sizeE();
		Entity out=null;
		for(int i=0;i<m;i++){
			Entity e=getE(i) ;
			if(e!= ee&& e.active()&&e.getClass().getSimpleName().contains(s)){
				Vec v1=e.getVec();
				Vec v2=ee.getVec();
				float er=v1.distance(v2);
				if(er<min){
					min=er;
					out=e;
				}
			}
		}
		return out;
	}
	public boolean contains(Entity e){
		return entities.containsValue(e);
	}
	public void removeE(Entity e){
		ents2.remove(e);
		entities.remove(e);
	}
	public void addE(Entity e){
		entities.put(e.getPIN(),e);
		ents2.add(e);
	}
	public void addPastPlayer(PlayerPast e){
		entities.put(e.getPIN(), e);
		ents2.add(e);
	}
	public void addS(Structure s){
		structures.add(s);
	}
	public void addP(Particle p){
		particles.add(p);
	}
	public void removeE(int i){
		getE(i).Destroy();
		Entity e=ents2.remove(i);
		entities.remove(e);
	}
	public void removeS(int i){
		structures.remove(i);
	}
	public void removeP(int i){
		particles.remove(i);
	}
	public Entity getE(int i){
		return ents2.get(i);
	}
	public Entity getE2(int i){
		if(i==-1)
			return Main.player;
		return ents2.get(i);
	}
	public Structure getS(int i){
		return structures.get(i);
	}
	public Particle getP(int i){
		return particles.get(i);
	}
	public int sizeE(){
		return ents2.size();
	}
	public int sizeS(){
		return structures.size();
	}
	public int sizeP(){
		return particles.size();
	}
	public void renderIndoorE(){
		int m=sizeE();
		Container cc=Main.player.getContained();
		for(int j=0;j<m;j++){
			Entity e=getE(j);
			if(e.getContained()==cc){
				e.render();
			}
		}
	}
	public void renderAllE(){
		int m=sizeE();
		for(int j=0;j<m;j++){
			Entity ee=getE(j);
			if(ee.getContained()==null){
				renderE(j);
			}
		}
	}
	public void renderAllS(){
		int m=sizeS();
		for(int j=0;j<m;j++){
			renderS(j);
		}
	}
	public void renderAllP(){
		Texture.bind(0);
		GL11.glColor4f(1f, 0f, 0f, 1f);
		int m=sizeP();
		for(int j=0;j<m;j++){
			renderP(j);
		}
	}
	public void logicAllE(){
		int m=sizeE();
		for(int j=0;j<m;j++){
			logicE(j);
		}
	}
	public void activateAllE(){
		int m=sizeE();
		for(int j=0;j<m;j++){
			activateE(j);
		}
	}
	
	public void logicAllS(){
		int m=sizeS();
		for(int j=0;j<m;j++){
			logicS(j);
		}
	}
	
	public void setTimeAllE(long t,int en){
		int m=sizeE();
		for(int j=0;j<m;j++){
			setTimeE(j,t,en);
		}
	}
	public void setTimeAllS(long i){
		int m=sizeS();
		for(int j=0;j<m;j++){
			setTimeS(j,i);
		}
	}
	public Player findPlayer(float x,float y){
		Player p=Main.player;
	
		float ro=999999999;

		for(Entity e:ents2){
			if(e instanceof Player){
				float xo=e.x-x;
				float yo=e.y-y;
				float rr=xo*xo+yo*yo;
				if(rr<ro)
					p=(Player) e;
			}
		}
		return p;
	}
	
	public void activateE(int i){
		getE(i).activate();
	}
	
	public void setTimeE(int i,long t,int en){
		getE(i).setTime(t,en);
	}
	public void setTimeS(int i,long j){
		getS(i).setTime(j);
	}
	public void logicE(int i){
		getE(i).logic();
	}
	public void logicS(int i){
		getS(i).logic();
	}
	public void renderE(int i){
		Entity E=getE(i);
		if(E.active()){
			E.render();
		}
	}
	public void renderS(int i){
		Structure S=getS(i);
		//Render.shader.coordinateEntity(true,S.x-S.sx, S.y -S.sy , S.z,Math.toRadians(S.getR()));
		S.render();
	}
	public void renderP(int i){
		getP(i).render();
	}
	public void wipeE(){
		entities.clear();
	}
	public void wipeS(){
		structures.clear();
	}
	public void wipeMostP(){
		for(int u=0;u<sizeP();u++){
			if(!getP(u).isRemain()){
				removeP(u);
				u--;
			}
		}
		//particles.clear();
	}
	public void wipeP(){
		particles.clear();
	}
	public void cleanS(){
		int m=sizeS();
		for(int i=0;i<m;i++){
			if(getS(i).isDependant()){
				removeS(i);
				i--;
				m--;
			}
		}
	}

	public boolean timeE(int i){
		Entity e = getE(i);
		e.considerTime();
		return e.isDestroyed();
	}
	public void timeS(int i){
		getS(i).time();
	}
	public boolean timeP(int i,double f){
		return getP(i).time(f);
	}
	public void timeAllE(){
		int m=sizeE();
		for(int j=0;j<m;j++){
			if(timeE(j)){
				removeE(j);
				j--;
				m--;
			}
		}
	}
	public void timeAllS(){
		int m=sizeS();
		for(int j=0;j<m;j++){
			timeS(j);

		}
	}
	public void timeAllP(double f){
		int m=sizeP();
		for(int j=0;j<m;j++){
			if(!timeP(j,f)){
				removeP(j);
				j--;
				m--;
			}
		}
	}
	public void time(){
		timeline.run();
		//pseudo.setSeed(timeline.tick);
		timeAllE();
		double factor=timeline.getTimeFactor();
		Render.time(factor);
		timeAllP(factor);
		if(timeline.skipTick)
			return;
		land.time();
		
		timeAllS();	
		society.time();
		
	}
	public int Rand(int seed,int range){
		pseudo.setSeed(timeline.tick+seed+ID);
		return pseudo.nextInt(range);
	}
	public void presetTimeE(){
		long tick=timeline.getTick();
		int ento=timeline.getEnt();
		if(ento+1<=Main.level.getStats().getCapEntSnaps())
		for(Entity e:ents2){
			e.presetTime(tick, ento);
		}
	}
	public void presetTime(){
		presetTimeE();
	}

	public void logic(){
		land.logic();
		logicAllE();
		logicAllS();
	}
	public Matrix4f getViewMat(){
		return viewMat;
	}
	
	Matrix4f viewMat;


	public static FloatBuffer mat2buff(Matrix4f m){
		FloatBuffer v =  BufferUtils.createFloatBuffer(16);
		v.put(0, m.m00);
		v.put(4, m.m10);
		v.put(8, m.m20);
		v.put(12, m.m30);

		v.put(1, m.m01);
		v.put(5, m.m11);
		v.put(9, m.m21);
		v.put(13, m.m31);

		v.put(2, m.m02);
		v.put(6, m.m12);
		v.put(10, m.m22);
		v.put(14, m.m32);

		v.put(3, m.m03);
		v.put(7, m.m13);
		v.put(11, m.m23);
		v.put(15, m.m33);
		
		
		return v;
	}
	public void matGen(){
		FloatBuffer f =  BufferUtils.createFloatBuffer(16);
		GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, f);
		Matrix4f m=new Matrix4f();
		m.load(f);
		m.invert();
		viewMat=m;
	}

	public void render(){
		matGen();

		if(Main.player.inside){
			//renderIndoorE();
			//Main.player.getContained().renderInner();
			//playerRender();
		}else{
			//Render.shader.normal();

			//plainLand();
			//Render.shader.autoNormOn();
			Render.getShader().start();
			Render.getShader().lightOn();
			Render.getShader().startLight();
			land.render();
			Render.getShader().end();
			//Render.shader.autoNormOff();
			
			
			Main.mouseSelector.think();	
			playerRender();

			renderAllE();
			//renderAllS();
			//land.renderWater();
			
			Render.getShader().end();
		}
	}
	private void playerRender(){
		
		Main.player.render();
	}
	

	public void annualE(){
		int m=sizeE();
		for(int j=0;j<m;j++){
			getE(j).annual();
		}
	}

	public void modelUpdate(){
		for(Entity e:ents2)
			e.renewDimensions();
	}
	public void setTime(long i){
		
		presetTime();
		timeline.setTime(i);

		int terr=timeline.getTerra();//Timeline.computeTerra(stats, i);
		//System.out.println("terr!! "+terr);
		if(terr>0){
			if(Land.current<terr){
				land.futureTo(Land.current, terr);
			}
		}
		int ento=timeline.getEnt();//Timeline.computeEnt(stats, i);

		land.time();
		land.loadWater(terr);
		Land.current=land.FRAME;
		land.renewSegments();
		//land.current=i;
		wipeMostP();
		setTimeAllE(i,ento);
		cleanS();
		setTimeAllS(terr);

		//land.current=i;
		//land.time();
		//land.logic();
	}
	public void reset(){
		timeline = new Timeline();
		wipeS();
		wipeE();
		wipeP();
		land.defaultAllLand();
	}
	double dd=0;
	double p2=Math.PI*2;
	public void plainLand(){
		int sz=Main.level.getStats().getSize();


		GL11.glNormal3f(0, 0, 1);
		GL11.glBegin(GL11.GL_TRIANGLE_STRIP);

		Render.shader.lightOff();
		GL11.glColor4f(0,0,0,1);
		
		GL11.glVertex3f(0f,0,.1f);
		GL11.glVertex3f(0, sz,.1f);
		GL11.glVertex3f(0, 0,-100);
		GL11.glVertex3f(0, sz,-100);

		GL11.glVertex3f(0f,0,.1f);
		GL11.glVertex3f(sz, 0,.1f);
		GL11.glVertex3f(0, 0,-100);
		GL11.glVertex3f(sz, 0,-100);
		
		GL11.glEnd();
		
		if(UserData.shaderLight())
			Render.shader.lightOn();
		
		GL11.glBegin(GL11.GL_TRIANGLE_STRIP);
		GL11.glColor4f(0.54f,0.82f,0.00f,1);
		//outer
		GL11.glVertex3f(0f,0,.1f);
		GL11.glVertex3f(-900,0,.1f);
		GL11.glVertex3f(0f,-900,.1f);
		GL11.glVertex3f(-900f,-900,.1f);
		
		GL11.glVertex3f(0f,0,.1f);
		GL11.glVertex3f(sz,0,.1f);
		GL11.glVertex3f(0f,-900,.1f);
		GL11.glVertex3f(sz,-900,.1f);
		
		GL11.glVertex3f(sz,0,.1f);
		GL11.glVertex3f(sz+900,0,.1f);
		GL11.glVertex3f(sz,-900,.1f);
		GL11.glVertex3f(sz+900,-900,.1f);
		
		GL11.glVertex3f(sz,0,.1f);
		GL11.glVertex3f(sz+900,0,.1f);
		GL11.glVertex3f(sz,sz,.1f);
		GL11.glVertex3f(sz+900,sz,.1f);

		GL11.glVertex3f(sz,sz,.1f);
		GL11.glVertex3f(sz+900,sz,.1f);
		GL11.glVertex3f(sz,sz+900,.1f);
		GL11.glVertex3f(sz+900,sz+900,.1f);
		
		GL11.glVertex3f(0,sz,.1f);
		GL11.glVertex3f(sz,sz,.1f);
		GL11.glVertex3f(0,sz+900,.1f);
		GL11.glVertex3f(sz,sz+900,.1f);
		
		GL11.glVertex3f(0,sz,.1f);
		GL11.glVertex3f(-900,sz,.1f);
		GL11.glVertex3f(0,sz+900,.1f);
		GL11.glVertex3f(-900,sz+900,.1f);
		
		GL11.glVertex3f(0,sz,.1f);
		GL11.glVertex3f(-900,sz,.1f);
		GL11.glVertex3f(0,0,.1f);
		GL11.glVertex3f(-900,0,.1f);
		GL11.glEnd();

	
	}

}
