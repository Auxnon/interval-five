package interval;

import intervalEntity.PlayerEditor;
import intervalGui.Gui;
import intervalGui.GuiEditorLevel;
import intervalGui.GuiEndTime;
import intervalGui.GuiGame;
import intervalGui.GuiPause;
import intervalGui.GuiPreGame;
import intervalGui.GuiTitle;
import intervalGui.GuiWin;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Changer {

	public static ArrayList<Gui> menuHiearchy=new ArrayList<Gui>();
	
	public static void menuNext(Gui g){
		menuHiearchy.clear();
		Render.setGui(g);
	}
	public static void menuTop(){
		if(menuHiearchy.size()>0){
		Gui g=menuHiearchy.remove(0);
		menuHiearchy.clear();
		if(g==null)
			title();
		else
			Render.setGui(g);
		}else
			title();
	}
	
	public static void menuUp(){
		Gui g=menuHiearchy.remove(menuHiearchy.size()-1);
		if(g==null)
			title();
		else
			Render.setGui(g);
	}
	public static void menuDown(Gui g){
		menuHiearchy.add(Render.getGui());
		Render.setGui(g);
	}
	
	public static void initialGui(){
		menuNext(new GuiPreGame());
	}
	
	
	public static void pause(){
		if(Render.getGui().isGame()){
			menuDown(new GuiPause());
			UserData.paused=true;
		}
	}
	public static void unpause(){
		if(UserData.paused){
			UserData.paused=false;
			menuTop();
		}
	}
	public static void togglePause(){
		if(UserData.paused){
			unpause();
		}else{
			pause();
		}
	}
	public static void togglePauseNoChange(){
		UserData.paused=!UserData.paused;
	}
	public static void toggleGui(){
		Main.world.wipeMostP();
		if(UserData.isGui()){
			UserData.guiOff();
		}else{
			UserData.guiOn();
		}
	}
	public static void endTime(){
		menuNext(new GuiEndTime());
	}
	public static void win() {
		if(Render.getGui().isGame()){
			menuNext(new GuiWin());
		}
	}
	
	/*public static void blank(){
		if(UserData.isGame() && !UserData.paused){
			Render.setGui(new GuiEmpty());
		}
	}*/
	

/*
	public static void gui(Class<? extends Gui> c){
		try {
			Render.setGui((Gui) c.newInstance());
		} catch (InstantiationException e) {
			Message.er(e);
		} catch (IllegalAccessException e) {
			Message.er(e);
		}
	}*/
	/*public static void gui(Class<? extends Gui> c,boolean render){
		UserData.render=render;
		gui(c);
	}*/
	
	public static void refreshGui(){
		if(Render.getGui()!=null){
			Render.getGui().refreshs();
		}
	}

	public static void editorLevel(int sz){

		menuNext(new GuiEditorLevel());
		Main.reservePlayer=Main.player;
		Main.player=new PlayerEditor();

		Stats s = new Stats();
		s.setSize(sz);
		s.setTimeTravel(false);
		s.setCapEntSnaps(1);
		s.lockLight(0.6f);
		s.light(50f, 0.5f);
		Main.level=new Level(0,s);
		Main.world=new World();
		Main.world.init();

		Main.Wx=-16f ;//+(0.5f*Main.player.size);
		Main.Wy=-16f ;//+(0.5f*Main.player.size);
		Main.Wz=0;
		Main.zoom=4f;

		Render.setYaw(0);
		Render.setYawRad(0);
		Render.Tp=-45;
		Render.Tp2=-0.7854f;
		UserData.setSelectionSize(4);
		Main.world.timeline.setActive(false);

	}

	public static void titleSimple(){
		gc();
		Message.reset();
		UserData.paused=false;
		Main.player.r=180;
		menuNext(new GuiTitle());
	}
	public static void title(){
		titleSimple();
		Main.level=LevelManager.getMenu();
		Main.world=new World();
		
		Main.world.init(FileManager.loadMap(LevelManager.getMenu().getMap()));
		Main.player.advancedSchedule();

		Main.player.place(16f, 14f);
		Main.player.r=180;
		Main.Wx=-16f ;//+(0.5f*Main.player.size);
		Main.Wy=-16f ;//+(0.5f*Main.player.size);
		Main.Wz=0;
		Main.zoom=8f;

		Render.setYaw(0);
		Render.setYawRad(0);
		Render.Tp=-45;
		Render.Tp2=-0.7854f;
		Render.clean();

	}
	public static void restart(){
		//render=true;
		level(Main.level.getLevelName());
		UserData.paused=false;
		//unpause();
	}
	public static void level(String s){
		Level L=LevelManager.getLevel(s);
		if(L==null)
			return;
		levelPart2(L);
	}
	public static void level(int i){
		levelPart2(LevelManager.getLevel(i));
	}
	private static void levelPart2(Level L){
		
		Message.reset();
		Main.world=null;
		Main.level=L;
		Main.player.setDestination(Main.level.getStartTime());
		Main.world=new World();

		if(!Main.level.Default){
			//Message.m("map here");
			Main.world.init(FileManager.loadMap(Main.level.getMap()));
		}else{
			//Message.m(Main.level.levelName +" is "+Main.level.Default);
			Main.world.init();
		}
		if(Main.world.land.corrupted){
			title();
			Message.addPass("not enough memory to open map :(",1);
			return;
		}

		Main.player.renewSchedule();

		Render.Tp=-70;
		Render.Tp2=(float) Math.toRadians(-70);
		Main.zoom=4.5f;
		Main.Wx=-Main.player.x+0.5f;
		Main.Wy=-Main.player.y+0.5f;
		Render.applyCamera();

		Message.addPass(Render.mainCamera.time +" to "+Render.mainCamera.capTime);
		
		Main.level.prime();
		Main.player.timeTravel(Main.level.getStartTime());
		Main.past.reset();

		menuNext(new GuiGame());
		
		Render.getGui().startAnimation();
		Main.world.activateAllE();
		
		//EntityManager.createMap("testMe");
	}
	public static void destroy(){
		Main.RUN = false;
		Main.loader.RUN=false;
		Message.m(Changer.class,"loop ended");
		Main.sound.end();
		Message.m(Changer.class,"sounds closed");
		Display.destroy();
		Message.m(Changer.class,"display destroyed");
		//if(UserData.fullScreen){
		//	
		//}
		//Main.mainFrame.dispose();
		System.exit(0);
		
		
	}

	public static void gc() {
		Object obj = new Object();
		WeakReference<Object> ref = new WeakReference<Object>(obj);
		obj = null;
		while(ref.get() != null) {
			System.gc();
		}
	}
	
	
	public static void fullscreen() {
		boolean b=!UserData.fullScreen;
		UserData.fullScreen=b;
		try {
			if(b){
				Display.setParent(null);
				DisplayMode dm=Display.getAvailableDisplayModes()[UserData.resolution];
				Message.addPass(UserData.resolution+":"+dm.getWidth()+"x"+dm.getHeight()+" -"+dm.getFrequency()+"hz "+dm.getBitsPerPixel()+"bpp");
				Display.setDisplayMode(Display.getAvailableDisplayModes()[UserData.resolution]);
				
				//resize(dm.getWidth(),dm.getHeight());
			
			}else{
				Display.setParent(MainFrame.canvas);
				//resize(Main.mainFrame.canvas.getWidth(),Main.mainFrame.canvas.getHeight());
			}
			Display.setFullscreen(b);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
}
