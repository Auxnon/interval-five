package interval;

import intervalEntity.Player;
import intervalEntity.EntityWander;
import intervalEntity.MouseSelector;
import intervalStructure.Building;
import intervalGui.*;
import java.io.IOException;
import java.util.Random;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

//TODO SKEPTIC GAME ENGINE?

/*yo dawg, i herd u like time so we got you 
 * this time so you could go back in time then 
 * forward in time and then watch yourself 
 * in real time and backwards time and timey wimey time dawg*/

//major 0 minor 01 build 2 release pa

/**SPECS
 * @author Aninon
 *
 * max sanity size 4096 x 4096  
 * 
 * max aging size 1024 x 1024
 *
 *method pattern:
 * logic() every tick
 * time() every time-tick (can vary from game tick rate)
 * render() every rendered tick
 * annual() called each time the timeline passes to a new cell
 * 
 * setTime() called to all instances when the timeline is moved
 * predict() method called to estimate the chain of actions taken over a specified time span
 * activate() called to activate the object
 * Destroy() called upon termination of an object 
 *
 */

public class Main {

	public static Sound sound;
	public static boolean RUN=true;
	public static int FPS=60;
	public static float zoom;
	public static MouseSelector mouseSelector;
	public static float Wx=-32f;
	public static float Wy=-32f;
	public static float Wz=0f;
	public static float panSpeed=0f;
	public static Player player;
	public static Player reservePlayer;
	public static World world;
	static int timer=0;
	static boolean escapeLock=false;
	public static PlayerPastManager past;
	public static Level level;
	public static Random rand;
	public static Controls control;
	//public static ModelTerraBuffer technicalModel;
	public static  MainFrame mainFrame=new MainFrame();
	
	public static Loader loader;
	public static Sync sync;
	
	public static void main(String[] arg) throws IOException{
		//System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir") + "\\bin");
		sound=new Sound();
		
		lastFPS = getTime();
		mainFrame.init();
		Render.init();
		
		ModelManager.init();
		loader = new Loader();
		sync=new Sync();
		sync.init();
		loader.start();
		//technicalModel = Model.hoopla();
		TextureCache.init();
		control = new Controls();
		world = new World();
		mouseSelector = new MouseSelector();
		zoom=10f;
		rand=new Random();
		LevelManager.init();
		level=new Level(0);
		past=new PlayerPastManager();
		player=new Player();
		player.renewSchedule();
		world.init();
		float zoomSpeed=0f;
		FileManager.init();
		Changer.initialGui();
		
		
		
		//loader.start();
		//Message.m(Main.class, "loader independent?");

		int sdffh=0;
		int n=3;
		MainFrame.screenAutoScale();

		boolean tHook=false;
		//Message.pop("oh hai");//,"derpy derp derp derp derp derp derp derp derpy derp derp derp derp derp derp derp derpy derp derp derp derp derp derp derp derpy derp derp derp derp derp derp derp");
		//Changer.level(1);
		boolean nLock=false;
		boolean f3Lock=false;
		while(RUN){
			control.run();
			panSpeed=-0.1f;

			sync.run();
			//TODO debug keys
/*
			if(control.getKey().equals("0")){
				UserData.setTimeRate(1);
			}else if(control.getKey().equals("1")){
				UserData.setTimeRate(1/256.0);
			}else if(control.getKey().equals("2")){
				UserData.setTimeRate(1/128.0);
			}else if(control.getKey().equals("3")){
				UserData.setTimeRate(1/64.0);
			}else if(control.getKey().equals("4")){
				UserData.setTimeRate(1/32.0);
			}else if(control.getKey().equals("5")){
				UserData.setTimeRate(1/16.0);
			}else if(control.getKey().equals("6")){
				UserData.setTimeRate(.125);
			}else if(control.getKey().equals("7")){
				UserData.setTimeRate(.25);
			}else if(control.getKey().equals("8")){
				UserData.setTimeRate(.5);
			}else if(control.getKey().equals("9")){
				UserData.setTimeRate(.75);
			} */

			if(Render.getGui() instanceof GuiTitle){
				if(sdffh>0){
					sdffh--;
				}else if(Keyboard.isKeyDown(Keyboard.KEY_P)){
					//Main.world.wipeP();
					sdffh=30;
					//sound.runInstance(sound.ocar1);
					//Render.inventoryDive();
					sound.runInstance(sound.coin);
				}else if(Keyboard.isKeyDown(Keyboard.KEY_L)){
					sdffh=8;
					//sound.runInstance(sound.bwom);
					Music.test.renew();
					world.addS(new Building(mouseSelector.x,mouseSelector.y,0));
				}else{
					if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
						n++;
						if(n>7){
							n=0;
						}
						//Message.addPass("N "+n);
						sdffh=20;
					}
					if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)){
						n--;
						if(n<0){
							n=7;
						}
						//Message.addPass("N "+n);
						sdffh=20;
					}
				}
				

				if(Keyboard.isKeyDown(Keyboard.KEY_1)){
					//sound.playNote("C#"+n,10);
					//sound.boop();
				}
				if(Keyboard.isKeyDown(Keyboard.KEY_2)){
					sound.playNote("D"+n,10);
				}
				if(Keyboard.isKeyDown(Keyboard.KEY_3)){
					sound.playNote("D#"+n,10);
				}
				if(Keyboard.isKeyDown(Keyboard.KEY_4)){
					sound.playNote("E"+n,10);
				}
				if(Keyboard.isKeyDown(Keyboard.KEY_5)){
					sound.playNote("F"+n,10);
				}
				if(Keyboard.isKeyDown(Keyboard.KEY_6)){
					sound.playNote("F#"+n,10);
				}
				if(Keyboard.isKeyDown(Keyboard.KEY_7)){
					sound.playNote("G"+n,10);
				}
				
				if(Keyboard.isKeyDown(Keyboard.KEY_8)){
					sound.playNote("G#"+n,10);
				}
				if(Keyboard.isKeyDown(Keyboard.KEY_9)){
					sound.playNote("A"+n,10);
				}
				
				
				
				if(Keyboard.isKeyDown(Keyboard.KEY_M)){
					if(!tHook){
						SoundFX.piff.play();
					}
					tHook=true;
				}else
					tHook=false;

			}
			if(Keyboard.isKeyDown(Keyboard.KEY_F3)){
				if(!f3Lock)
				if(Render.getGui().isHidden())Render.getGui().unhide();else Render.getGui().hide();
				f3Lock=true;
			}else f3Lock=false;
				
				
			
			if(Keyboard.isKeyDown(Keyboard.KEY_N)){
				if(!nLock){
				Main.world.land.sloped=!Main.world.land.sloped;
				Main.world.land.renewSegments();}
				nLock=true;
			}else{
				nLock=false;
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_H))
				UserData.setTimeRate(0.1);
			
			if(Keyboard.isKeyDown(Keyboard.KEY_G))
				UserData.setTimeRate(1);

			sound.run();
			if(Music.test!=null)
				Music.test.run();

			if(!Render.getGui().isMenu()){
				if(Keyboard.isKeyDown(Keyboard.KEY_ADD)){ //
					zoom-=0.05f;
				}
				if(Keyboard.isKeyDown(Keyboard.KEY_SUBTRACT)){ //
					zoom+=0.05f;
				}
				if(control.sprint){
					zoomSpeed+=Mouse.getDWheel()/100f;
				}else{
					zoomSpeed+=Mouse.getDWheel()/1000f;
				}
				/*if(Keyboard.isKeyDown(Keyboard.KEY_N)){ 
					EntityWander w= new EntityWander();
					w.setPos(mouseSelector.x,mouseSelector.y,mouseSelector.z);
					world.addE(w);
				}*/
				
			}
			timer++;

			if(Math.abs(zoomSpeed)>0.01f){
				zoomSpeed/=1.1f;
				zoom+=zoomSpeed;
			}else{
				zoomSpeed=0f;
			}
			if(zoom<2f){
				zoom=2f;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_H)){
				Changer.gc();
			}
			/*if(Keyboard.isKeyDown(Keyboard.KEY_B)){
				Changer.blank();
			}*/
			if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) ){
				if(!escapeLock){
					Changer.togglePause();
					escapeLock=true;
				}
			}else {
				escapeLock=false;
			}
			if (Display.isCloseRequested()) {
				Changer.destroy();
			}

			if(!UserData.paused){
				if(ModelManager.updated)
					world.modelUpdate();
				world.logic();
				player.logic();
				if(world.timeline.isActive()){
					if(!UserData.timeFreeze){
						world.time();
					}
					player.time(world.timeline);
				}
			}
			if(!Render.getGui().isMenu()){
				Wx=-player.x;
				Wy=-player.y;
				Wz=-player.z-2f;
			}
			if(Render.getGui().isGame())
				level.test();
			
			Render.cycle();
			updateFPS();
			Display.update();
			Display.sync(Main.FPS);
		}

	}
	public static void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			MainFrame.set("Interval     FPS: " + fps);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}
	static int fps;
	static long lastFPS;

	public static long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

}
