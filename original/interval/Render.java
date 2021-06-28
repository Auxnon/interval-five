package interval;

import intervalGui.Gui;
import intervalGui.GuiEmpty;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class Render {
	private static Gui gui;
	public static Shader shader;
	static FloatBuffer lightPosition;
	static FloatBuffer lightDirection;
	static FloatBuffer lightAmbient;
	public static boolean inventory3D=true;
	static float WX;
	static float WY;
	static float WZ;
	private static float Tz=0;
	private static float Tz2=0;
	static float pTz=0;
	static float pTp=0;
	static float tz1=0;
	static float tz2=0;
	static float Tp=-45;
	static float Tp2=-0.785f;
	static float tp1=-45;
	static float tp2=-45;
	static boolean tzb=true;

	static Camera mainCamera;
	public static void resize(DisplayMode dm){
		try {
			Display.setDisplayMode(dm);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	public static void esizes(DisplayMode dm){
		try {
			Display.setDisplayMode(dm);//new DisplayMode(Main.width,Main.height));//dd[0]);
			Display.setTitle("Interval");
			Display.setResizable(true);
			//Display.setIcon(TextureCache.loadIcon(FileManager.dir()+"playo.png"));
			//Display.create();
			//Main.mainFrame.parent();
		} catch (LWJGLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GLU.gluPerspective(45.0f, (float) MainFrame.getW()/(float)MainFrame.getH(), 0.1f, 100.0f);
		GL11.glLoadIdentity();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_POLYGON_SMOOTH);

		GL11.glLightModeli(GL11.GL_LIGHT_MODEL_LOCAL_VIEWER, GL11.GL_TRUE);
		GL11.glEnable(GL11.GL_LIGHTING);

		lightAmbient = (FloatBuffer) BufferUtils.createFloatBuffer(4).put(new float[] {.4f, .4f, .4f, 1.0f }).flip();
		FloatBuffer lightDiffuse = (FloatBuffer) BufferUtils.createFloatBuffer(4).put(new float[] {0.9f,0.9f,0.9f,0.9f }).flip();
		FloatBuffer lightSpec = (FloatBuffer) BufferUtils.createFloatBuffer(4).put(new float[] {1f, 0.1f, 1.0f, 1f }).flip();
		lightPosition = (FloatBuffer) BufferUtils.createFloatBuffer(4).put(new float[] {26f, 26f,10f, 1f }).flip();
		lightDirection = (FloatBuffer) BufferUtils.createFloatBuffer(4).put(new float[] {1.0f, 1.0f, 0.0f,1f}).flip();

		GL11.glLight(GL11.GL_LIGHT1, GL11.GL_AMBIENT, lightAmbient);              
		GL11.glLight(GL11.GL_LIGHT1, GL11.GL_SPECULAR, lightSpec);   
		GL11.glLight(GL11.GL_LIGHT1, GL11.GL_DIFFUSE, lightDiffuse);   
		GL11.glLight(GL11.GL_LIGHT1, GL11.GL_POSITION,lightPosition);

		GL11.glLightf (GL11.GL_LIGHT1, GL11.GL_SPOT_EXPONENT, 0.0f);
		GL11.glLightf (GL11.GL_LIGHT1, GL11.GL_SPOT_CUTOFF, 180.0f);
		GL11.glEnable ( GL11.GL_COLOR_MATERIAL ); 

		shader=new Shader();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc (GL11.GL_SRC_ALPHA, GL11.GL_DST_ALPHA); //GL11.GL_ONE_MINUS_SRC_ALPHA
		
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1f);
	}
	public static void init(){
		try {
			Display.setDisplayMode(new DisplayMode(MainFrame.width,MainFrame.height));
			Display.setTitle("Interval");
			//Display.setIcon(TextureCache.loadIcon("playo"));
			Display.setResizable(true);

			Display.create();

		} catch (LWJGLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Mouse.create();
			Mouse.setGrabbed(false);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_POLYGON_SMOOTH);
		GL11.glLightModeli(GL11.GL_LIGHT_MODEL_LOCAL_VIEWER, GL11.GL_TRUE);
		GL11.glEnable(GL11.GL_LIGHTING);
		lightAmbient = (FloatBuffer) BufferUtils.createFloatBuffer(4).put(new float[] {.4f, .4f, .4f, 1.0f }).flip();
		FloatBuffer lightDiffuse = (FloatBuffer) BufferUtils.createFloatBuffer(4).put(new float[] {0.9f,0.9f,0.9f,0.9f }).flip();
		FloatBuffer lightSpec = (FloatBuffer) BufferUtils.createFloatBuffer(4).put(new float[] {1f, 0.1f, 1.0f, 1f }).flip();
		lightPosition = (FloatBuffer) BufferUtils.createFloatBuffer(4).put(new float[] {26f, 26f,10f, 1f }).flip();
		lightDirection = (FloatBuffer) BufferUtils.createFloatBuffer(4).put(new float[] {1.0f, 1.0f, 0.0f,1f}).flip();

		GL11.glLight(GL11.GL_LIGHT1, GL11.GL_AMBIENT, lightAmbient);              
		GL11.glLight(GL11.GL_LIGHT1, GL11.GL_SPECULAR, lightSpec);   
		GL11.glLight(GL11.GL_LIGHT1, GL11.GL_DIFFUSE, lightDiffuse);   
		GL11.glLight(GL11.GL_LIGHT1, GL11.GL_POSITION,lightPosition);
		GL11.glLightf(GL11.GL_LIGHT1, GL11.GL_SPOT_EXPONENT, 0.0f);
		GL11.glLightf(GL11.GL_LIGHT1, GL11.GL_SPOT_CUTOFF, 180.0f);
		GL11.glEnable(GL11.GL_LIGHT1);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable ( GL11.GL_COLOR_MATERIAL ); 

		Message.init();
		shader=new Shader();
		gui=new GuiEmpty();
		que = new ArrayList<InvVGO>();
		//quex= new ArrayList<Float>();
		//quey=new ArrayList<Float>();
		//ques=new ArrayList<Float>();
		mainCamera=new Camera();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc (GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1f);
	}

	public static void backColor(float r,float g,float b){
		bakr=r;
		bakg=g;
		bakb=b;
	}
	private static float bakr,bakg,bakb;
	public static void preRender(){
		if(gui.isGame() && !Main.player.inside ){
			double ho=Main.world.timeline.hour;
			boolean twilight=ho>15 && ho<22;

			float F=Main.world.timeline.getDaylight();
			float B=0;
			if(twilight)
				B=1f-(float) Math.abs((18.5-ho)/3.5f);
			GL11.glClearColor(0.134f+F*0.6f+B*0.6f, F+B*0.3f, 0.134f+F*0.82f, 1);
		}else{
			GL11.glClearColor(bakr,bakg,bakb,1);
		}
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT |GL11.GL_DEPTH_BUFFER_BIT);


	}

	public static void postRender(){
	}

	public static float underView=0;

	public static void applyCamera(){
		mainCamera.set(Main.Wx,Main.Wy, Main.Wz, Tp, getYaw(), Main.zoom);
	}
	public static void render2D(){
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glLoadIdentity();
		GL11.glOrtho(MainFrame.screenLeft(), MainFrame.screenRight(), 0,64, 0, 1);
		Texture.bind(0);
		
		
		
		gui.run();
		Message.run();
		
		if(borderOn)
			border(bm,bi,be,bt);
		GL11.glEnable(GL11.GL_LIGHTING);
	}
	public static boolean underTop(){
		return underView>0;
	}

	public static float squish=0;
	public static boolean warpLock=false;
	public static float wx,wy,wz;
	public static void warp(float x,float y,float z ){
		warpLock=true;
		squish=0.5f;
		wx=x;wy=y;wz=z;
	}
	public static float SUN=0;
	public static boolean twilight;
	public static void time(double d){
		if(gui.isEditor()&&!warpLock&&!gui.within&&Mouse.isButtonDown(0)){
			squish=0.5f;
		}else if(squish>0){
			squish-=0.04f*d;
		}else{
			squish=0;
			warpLock=false;
		}
	}

	static boolean borderOn=false;
	static float bi;
	static float be;
	static float bt;
	static int bm;
	
	static int bk;
	
	public static void border(int m,float e){
		border(m,0,e,1);
	}
	public static void border(int m,float i,float e){
		border(m,i,e,1);
	}
	public static void border(int m,float i,float e,float t){
		if(m==-1){
			borderOn=false;
			return;
		}
		Texture.bind(0);
		bm=m;
		bi=i;
		be=e;
		bt=t;
		borderOn=true;
		bk++;
		int bti=(int) Math.floor(bt*60);
		if(bk>bti)
			bk=0;
		
		
		float r=0,g=0,b=0;
		switch(m){
		case 1: b=1;break;
		case 2: g=1;break;
		case 3: g=1;r=0.2f;break;
		default: r=1;
		}

		GL11.glBegin(GL11.GL_QUADS);

		float v=Math.abs(1-((float)bk/(float)bti)*2);
		float H=i+e*v;
		
		float s2=MainFrame.screenLeft();
		float mox=MainFrame.screenRight();//Main.width;
		float moy=64;//Main.height;

		float f1=0.7f;
		//float f2=0.2f;
		GL11.glColor4f(r,g,b, f1);
		//GL11.glColor3f(1,1,1);
		GL11.glNormal3f(0,0,1);
		
		GL11.glVertex2f(s2, 0f);
		GL11.glVertex2f(mox, 0f);
		GL11.glVertex2f(mox-H, H);
		GL11.glVertex2f(H+s2, H);

		
		GL11.glVertex2f(H+s2, moy-H);
		GL11.glVertex2f(H+s2, H);
		GL11.glVertex2f(s2, 0f);
		GL11.glVertex2f(s2, moy);
		

		GL11.glVertex2f(mox, 0f);
		GL11.glVertex2f(mox, moy);
		GL11.glVertex2f(mox-H,moy-H);
		GL11.glVertex2f(mox-H, H);

		GL11.glVertex2f(mox-H, moy-H);
		GL11.glVertex2f(s2+H,moy-H);
		GL11.glVertex2f(s2, moy);
		GL11.glVertex2f(mox, moy);
		GL11.glEnd();
	}
	public static void render3D(){


		GL11.glLoadIdentity ();
		GL11.glViewport(0, 0, MainFrame.getW(), MainFrame.getH());
		GLU.gluPerspective(45.0f, (float) MainFrame.getW()/(float)MainFrame.getH(), 1f, 1000.0f);
		GL11.glDepthRange (0.1f, 1.0);

		if(gui.isRender()){
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		
		
		if(Mouse.isButtonDown(1) &&(gui.isGame() || (gui.isEditor()))){
			if(tzb){
				tzb=false;
				tz1=Mouse.getX();
				tp1=Mouse.getY();
				pTz=getYaw();
				pTp=Tp;
			}
			tz2=Mouse.getX();
			tp2=Mouse.getY();
			setYaw(pTz+((tz1-tz2)/2f));
			Tp=pTp+((tp1-tp2)/2f);

			underView=0;
			if(Tp>-33){
				Tp=-33;

			}else if(Tp<-85){
				if(Tp<-179){
					Tp=-179;
				}
				underView=-(Tp+85);
			}
			setYawRad((float) Math.toRadians(getYaw()));
			Tp2=(float) Math.toRadians(Tp);
		}else{
			tzb=true;
		}
		//float dd=ddf*((Main.zoom/2f)-0.5f);
		//WX=(float) (((Math.sin(Tz2)*dd) ) +Main.Wx);//+Main.Wx-1f); +0.5f -  Math.sin(Tp2)*4)
		//WY= (float) (((Math.cos(Tz2)*dd) )+ Main.Wy);// + Main.Wy+Main.zoom*1.5f);
		//WZ=(float) (Main.Wz+2f +(1-Main.zoom)*Math.cos(-Tp2)*3.14f);
		WX=Main.Wx;
		WY=Main.Wy;
		WZ=Main.Wz-0.1f;
		underView=0;
		float tZoom=Main.zoom/(1+underView/20f);//+(underTop()?-underView/5f:0);

		//if(inventoryLook){
		//	mainCamera.lock();
		//}else{
		mainCamera.upkeep(3, WX,WY,WZ, Tp, getYaw(), tZoom);
		//}
		mainCamera.run();

		//double hour= Main.world.timeline.hour;
		//if(hour)
		SUN=Main.world.timeline.getSun()*180;

		GL11.glColor4f(1,1,1,1);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glPushMatrix();

		double ho=Main.world.timeline.hour;

		GL11.glRotatef(-90+mainCamera.pitch(), 1, 0, 0);
		GL11.glRotatef(-mainCamera.yaw(), 0, 1, 0);

		GL11.glPushMatrix();
		GL11.glRotatef(SUN, 1, 0, 0);
		GL11.glTranslatef(0,0,200+mainCamera.zoom());
		VGO v=ModelManager.getModel("sun");
		float ss=30f;
		GL11.glScalef(ss,ss,ss);
		GL11.glTranslated(-v.mx, -v.my, -v.mz);



		if(SUN<200){
			float bo=1;
			if(twilight)
				bo=1f-(float) (ho-15)/3f;
			if(bo<0)
				bo=0;
			GL11.glColor4f(0.8f+bo*0.2f,0.2f+ bo*0.8f, bo, 1);
			v.render();
		}
		GL11.glPopMatrix();


		GL11.glPushMatrix();
		GL11.glRotatef(SUN-180, 1, 0, 0);
		GL11.glTranslatef(0,0,200+mainCamera.zoom());
		v=ModelManager.getModel("moon");
		ss=40f;
		GL11.glScalef(ss,ss,ss);
		GL11.glTranslated(-v.mx, -v.my, -v.mz);
		v.render();
		GL11.glPopMatrix();


		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glTranslatef(0,1f,-mainCamera.zoom());

		GL11.glRotatef(mainCamera.pitch(), 1, 0, 0);
		GL11.glRotatef(mainCamera.yaw(), 0, 0, 1);
		GL11.glPushMatrix();
		GL11.glTranslatef(mainCamera.x(),mainCamera.y(),mainCamera.z());

		Texture.bind(0);
		GL11.glPushMatrix();
		float day;
		if(Main.player.inside){
			day=0.6f;
		}else{
			day=Main.world.timeline.getDaylight();
		}
		lightAmbient = (FloatBuffer) BufferUtils.createFloatBuffer(4).put(new float[] {day,day,day, 1.0f }).flip();
		GL11.glLight(GL11.GL_LIGHT1, GL11.GL_AMBIENT,lightAmbient); 
		if(UserData.shadersOn()){
			//shader.start();
			if(UserData.shaderLight())
				shader.lightOn();
			else
				shader.lightOff();
		}
		float xxx=Main.mouseSelector.x;
		float yyy=Main.mouseSelector.y;
		float zzz=Main.mouseSelector.z+1;

		if(!warpLock)
			shader.warp(xxx,yyy,zzz,squish,1);
		else
			shader.warp(wx,wy,wz,squish,1);

		lightPos(xxx,yyy,zzz);
		xxx=Main.player.x;
		yyy=Main.player.y;
		zzz=Main.player.z;


		
		
		shader.startLight();
		Texture.bind(0);
		Main.world.render();
		//shader.testPrim();
		//shader.end();
		
		if(gui.isEditor())
			Main.mouseSelector.render();
		GL11.glDisable(GL11.GL_LIGHTING);
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		
		Main.world.renderAllP();
		
		
		
		GL11.glPopMatrix();
		GL11.glPopMatrix();
		}else
			GL11.glDisable(GL11.GL_LIGHTING);
		if(UserData.isGui()&&inventory3D){
			inv3D();
		}
		GL11.glEnable(GL11.GL_LIGHTING);
	}
	static float  rotto=0;
	public static ArrayList<InvVGO> que;

	public static void invModel(String s,float x,float y){
		invModel(s,x,y,1);
	}
	public static void invModel(String s,float x,float y,float scale){
		InvVGO inv=new InvVGO(s,
				(((x/64f) -0.5f)*4f),
				(((y/64f) -0.5f)*4f)-0.25f,
				scale,
				0);
		que.add(inv);
	}
	//public static boolean inventoryLook=false;

	public static Shader getShader(){
		return shader;
	}
	public static void inventoryDive(){
		if(!mainCamera.isLocked()){
			UserData.paused=true;
			float rrr=180-Main.player.r;
			double rr=Math.toRadians(rrr);
			double sx=-.95f;

			double rrx=-Math.sin(rr)*sx;
			double rry=Math.cos(rr)*sx;
			//ffx-ffy*1.75f,ffy+ffx*1.75f
			mainCamera.go(10, (float) (-rrx-Main.player.x),(float) (rry-Main.player.y),-Main.player.z,-15,rrr,2.5f);
			mainCamera.lock();
		}else{
			UserData.paused=false;
			mainCamera.unlock();
		}
	}
	public static void inv3D(){
		GL11.glLoadIdentity ();
		GL11.glDepthRange (0.0f, 0.1f);
		GL11.glColor4f(1,1,1,1);
		GLU.gluPerspective(45.0f, (float) MainFrame.getW()/(float)MainFrame.getH(), 0.1f, 6.0f);
		int derpy=que.size();
		if(derpy>0){
			rotto++;
			if(rotto>360){
				rotto=0;
			}
			float ffo=0.33f;
			for(int v=0;v<derpy;v++){
				GL11.glPushMatrix();
				InvVGO inv=que.get(v);
				VGO vgo=inv.getModel();
				float ffi=ffo;//vgo.getSize();
				float sz=vgo.getSize();
				if(sz<1)
					sz=1;
				ffi*=inv.scale/sz;
				GL11.glTranslatef(inv.x, inv.y, -5);
				GL11.glRotatef(90, 1,0,0);
				GL11.glRotatef(180, 0,1,0);
				GL11.glScalef(ffi,ffi,ffi);
				if(inv.mode==0){
					vgo.orient(rotto);
					vgo.render();
					if(inv.plat)
						vgo.plat();
				}else{
					GL11.glRotatef(inv.r2, 1, 0, 0);
					GL11.glRotatef(inv.r, 0,0, 1);
					
					GL11.glTranslatef(0, 0, -inv.raiseZ);
					if(inv.plat)
						vgo.plat();
					vgo.orient();
					GL11.glColor3f(1,1,1);
					if(inv.mode==2)
						vgo.render(inv.special);
					else
						vgo.render();
					
				}
				GL11.glPopMatrix();
			}
			//que.clear();
		}

		//GL11.glEnable(GL11.GL_DEPTH_TEST);
		//GL11.glMatrixMode(GL11.GL_PROJECTION);

		//GL11.glDepthRange (0.1, 1.0); /* Draw underlying geometry */ 
		//GL11.glDepthRange (0.0, 0.9); /* Draw overlying geometry */
	}
	public static void cycle(){
		preRender();
		
			render3D();

		if(UserData.isGui())
			render2D();

		postRender();

	}
/*	public static void lightC(){
		lightPosition.clear();
	}
	
	public static void lightX(float xx){
		lightPosition.put(xx);
	}
	public static void lightY(float yy){
		lightPosition.put(yy);
	}
	public static void lightZ(float zz){
		lightPosition.put(zz);
	}
	public static void lightParse(){
		//lightPosition.flip();
		GL11.glLight(GL11.GL_LIGHT1, GL11.GL_POSITION,lightPosition); 
	}*/
	public static void lightPos(float xxx,float yyy,float zzz){
		//xxx-=0.5f;
		//yyy-=0.5f;
		//zzz-=0.5f;
		lightPosition = (FloatBuffer) BufferUtils.createFloatBuffer(4).put(new float[] {xxx,yyy,zzz,1f }).flip();
		//lightPosition.p
		GL11.glLight(GL11.GL_LIGHT1, GL11.GL_POSITION,lightPosition); 
	}
	public static void clean(){
		que.clear();
		mainCamera.unlock();
		border(-1,0);
		backColor(0,0,0);
	}
	public static void setGui(Gui g){
		gui.setScraped(true);
		gui=g;
		clean();
		if(gui.isScraped())
			gui.setScraped(false);
		else
			gui.initer();
	}
	public static Gui getGui(){
		return gui;
	}
	public static double getRealYawRad() {
		return Math.toRadians(mainCamera.yaw());
	}
	public static float getYawRad() {
		return Tz2;
	}
	public static void setYawRad(float tz2) {
		Tz2 = tz2;
	}
	public static float getYaw() {
		return Tz;
	}
	public static void setYaw(float tz) {
		Tz = tz;
	}

	public static float getPitch() {
		return Tp;
	}

	/*public static void color(float x,float y,float z,float w){
		GL11.glColor4f(x, y, z,w);
	}
	public static void rotate(float a,float x,float y,float z){
		GL11.glRotatef(a, x, y, z);
	}
	public static void push(){
		GL11.glPushMatrix();
	}
	public static void pop(){
		GL11.glPopMatrix();
	}
	public static void quadS(){
		GL11.glBegin(GL11.GL_QUADS);
	}
	public static void vert(float x,float y,float z){
		GL11.glVertex3f(x, y, z);
	}
	public static void quadE(){
		GL11.glEnd();
	}*/

}
