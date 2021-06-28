package intervalGui;

import interval.Calender;
import interval.IFont;
import interval.Land;
import interval.Main;
import interval.Message;
import interval.Stats;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GuiGame extends Gui{
	GIcapture cap;
	GI tab;
	GI date1;
	GI date2;
	GImeter healthBar;
	GItimeline timel;

	public void initer() {
		setGame();
		keyAble=false;
		//guis.add(new GuiItem("derp",0,Main.height-16));
		//guis.add(new GuiItem("grass",84,Main.height-100));
		//guis.add(new GuiItem("brick",252,Main.height-100));
		//guis.add(new GuiItem("stone",420,Main.height-100));
		//guis.add(new GuiItem("sand",392,Main.height-100));
		//guis.add(new GuiItem("pic",456,Main.height-100));
		//guis.add(new GuiItem("load",520,Main.height-100));
		timel=new GItimeline();
		add(timel);
		tab = new GItab(32,4,false).centerX();
		cap = new GIcapture(this);

		add(tab);
		add(cap);

		date1=new GI("19:15 February 27, 2010",32,56,0.66f,0.66f,IFont.BLACK).centerRight();
		date2=new GI(Calender.predictCalender(Main.level.getStats(),Main.player.getDestination()),32,56,0.66f,0.66f,IFont.BLACK).centerLeft();
		date1.ambientOpaque();
		date2.ambientOpaque();
		
		healthBar=new GImeter(0,0);
		add(date1);
		add(date2);
		add(healthBar);
		//guis.add(new GuiCapture());
		//gb= new GuiBorder();
		//money=new BufferedImage(256,16,BufferedImage.TYPE_INT_ARGB);
		//g2=money.createGraphics();
		//g2.clearRect(0, 0,16,16);
		//g2.drawString("der", 0, 0);
		//mula = new Tex(money);

	}

	public void command(String s,GI g){
		if(s.startsWith("time")){

			//int out=Integer.parseInt(s.substring(4));
			//System.out.println("out is: "+out);
			Stats S=Main.level.getStats();

			//int e=Timeline.computeEnt(S, out);
			//System.out.println("frame: "+Timeline.invertEnt(S, out));

			//System.out.println("dest: "+Main.player.destination);
			String str=Calender.predictCalender(S,Main.player.getDestination());

			if(!date2.action.contains(str)){
				date2.redo(str, IFont.BLACK);
				date2.centerLeft();
			}

			//Main.player.timeTravel(out+1);
		}else if(s.contains("tabbed")){
			if(!cap.visible)
				cap.Show();
			tab.hide();
			delay=0;
		}else if(s=="grass"){
			Main.player.setBlockPick(Land.grass1);
		}else if(s=="brick"){
			Main.player.setBlockPick(Land.brick1);
		}else if(s=="stone"){
			Main.player.setBlockPick(Land.stone);
		}else if(s=="sand"){
			Main.player.setBlockPick(Land.sand);
		}else if(s=="pic"){
			//Main.world.land.savePic();
			//Main.world.land.saveHeightMap();
		}else if(s=="load"){
			File f=new File("./land.bmp");
			File f2=new File("./height.bmp");
			BufferedImage im;
			BufferedImage im2;
			try {
				im = ImageIO.read(f);
				im2 = ImageIO.read(f2);
				Main.world.land.loadLand(im,im2);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}else if (s=="herp"){

			//Message.addPass(Message.insult());
		}else{
			Message.pointerMessage(s);
		}
	}

	short delay=0;
	public void run(){
		String string=Main.world.timeline.stringCalender();
		if(!date1.action.contains(string)){
			date1.redo(string, IFont.BLACK);
			date1.centerRight();
		}


		
		if(cap.visible){
			delay++;
			if(delay>3){
				delay=0;
				cap.Hide();
				tab.unhide();
			}
		}
		
		
		
		

		mouser();
		guisRun();
		
		if(timel.effect){
			date1.y=56;
			date2.y=56;
			//if(timel.xi<64-date1.sx)
			//date1.x=timel.xi;
			
			//date2.x=timel.x2;
		}else{
			date1.y=60;
			date2.y=60;
			//date2.x=0;
			//date1.x=64-date1.sx;
		}
		
		

		//	GL11.glColor4f(1f,0,0,1f);

		/*

		mula.g2.clearRect(0,0,256,16);
		mula.g2.drawString("$:"+PlayerManager.money, 0, 10);
		mula.renew();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2f(0, 0);
		GL11.glTexCoord2f(1, 1);

		GL11.glVertex2f(400, 0);
		GL11.glTexCoord2f(1,0);

		GL11.glVertex2f(400, 60);
		GL11.glTexCoord2f(0, 0);

		GL11.glVertex2f(0, 60);



		GL11.glEnd();*/

		//gb.render();
		
		if(Main.control.getKey().equals("0")){
			healthBar.redo(7, 0, false);
		}else if(Main.control.getKey().equals("1")){
			healthBar.redo(7, 1, false);
		}else if(Main.control.getKey().equals("2")){
			healthBar.redo(7, 2, false);
		}else if(Main.control.getKey().equals("3")){
			healthBar.redo(40, 23, false);
		}else if(Main.control.getKey().equals("4")){
			healthBar.redo(30, 27, true);
		}else if(Main.control.getKey().equals("5")){
			healthBar.redo(7, 5, false);
		}else if(Main.control.getKey().equals("6")){
			healthBar.redo(7, 6, false);
		}else if(Main.control.getKey().equals("7")){
			healthBar.redo(7, 7, false);
		}else if(Main.control.getKey().equals("8")){
			healthBar.redo(7, 0, false);
		}else if(Main.control.getKey().equals("9")){
			healthBar.redo(7, 0, false);
		} 
	}

}
