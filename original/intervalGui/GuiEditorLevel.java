package intervalGui;

import interval.Changer;
import interval.FileManager;
import interval.IFont;
import interval.Land;
import interval.Main;
import interval.Message;
import interval.UserData;

public class GuiEditorLevel extends Gui {

	GI selSize;
	GI selSpeed;
	GI selHeight;
	
	public void initer() {
		setEditor();
		
		add(new GI("height",0,57,0.5f,0.5f,IFont.BLACK));
		add(new GI("pit",6,57,0.5f,0.5f,IFont.BLACK));
		add(new GI("smooth",12,57,0.5f,0.5f,IFont.BLACK));
		add(new GI("age",18,57,0.5f,0.5f,IFont.BLACK));
		add(new GI("water",24,57,0.5f,0.5f,IFont.BLACK));
		add(new GI("lock",36,57,0.5f,0.5f,IFont.BLACK));

		add(new GI("grass",0,50,0.5f,0.5f,IFont.BLACK));
		add(new GI("dirt",5,50,0.5f,0.5f,IFont.BLACK));
		add(new GI("hard stone",10,50,0.5f,0.5f,IFont.BLACK));
		add(new GI("soft stone",19,50,0.5f,0.5f,IFont.BLACK));
		add(new GI("sand",28,50,0.5f,0.5f,IFont.BLACK));
		add(new GI("mud",33,50,0.5f,0.5f,IFont.BLACK));
		add(new GI("steel",38,50,0.5f,0.5f,IFont.BLACK));

		selSize =new GI("size: 4",0,62.5f,0.5f,0.5f,IFont.BLACK);
		selSpeed =new GI("delay: 5",16,62.5f,0.5f,0.5f,IFont.BLACK);
		selHeight =new GI("amount: 1",32,62.5f,0.5f,0.5f,IFont.BLACK);

		GI z1=new GI("+",7,64,0.5f,0.5f,IFont.BLACK);
		GI z2=new GI("-",7,61,0.5f,0.5f,IFont.BLACK);
		GI d1=new GI("+",24,64,0.5f,0.5f,IFont.BLACK);
		GI d2=new GI("-",24,61,0.5f,0.5f,IFont.BLACK);
		GI a1=new GI("+",40,64,0.5f,0.5f,IFont.BLACK);
		GI a2=new GI("-",40,61,0.5f,0.5f,IFont.BLACK);
		z1.action="z+";z2.action="z-";d1.action="d+";d2.action="d-";a1.action="a+";a2.action="a-";

		add(z1);
		add(z2);

		add(d1);
		add(d2);

		add(a1);
		add(a2);


		add(new GI("Quit",0,3,0.5f,0.5f,IFont.BLACK).centerLeft());

		add(new GI("Load",64,3,0.5f,0.5f,IFont.BLACK));
		add(new GI("Save",60,3,0.5f,0.5f,IFont.BLACK));
		
		add(selSize);
		add(selSpeed);
		add(selHeight);
	}
	public void redoZ(){
		String st="size: "+UserData.getSelectionSize();

		if(selSize.action!=st){
			selSize.redo(st, IFont.BLACK);
		}
	}
	public void redoD(){
		String st="delay: "+Main.player.getCommandCount();
		if(selSpeed.action!=st){
			selSpeed.redo(st, IFont.BLACK);
		}
	}
	public void redoA(){
		String st="amount: "+Main.player.getAniMode();
		if(selHeight.action!=st){
			selHeight.redo(st, IFont.BLACK);
		}
	}
	public void command(String s,GI g){
		Message.m(this, "ss " +g.name +" "+g.action);
		if(s=="grass"){
			Main.player.setBlockPick(Land.grass1);
		}else if(s=="dirt"){
			Main.player.setBlockPick(Land.dirt);
		}else if(s=="mud"){
			Main.player.setBlockPick(Land.mud);Main.player.setMoveType(0);
		}else if(s=="hard stone"){
			Main.player.setBlockPick(Land.hardStone);Main.player.setMoveType(0);
		}else if(s=="soft stone"){
			Main.player.setBlockPick(Land.softStone);Main.player.setMoveType(0);
		}else if(s=="sand"){
			Main.player.setBlockPick(Land.sand);Main.player.setMoveType(0);
		}else if(s=="steel"){
			Main.player.setBlockPick(Land.steel);Main.player.setMoveType(0);
			
		}else if(s=="height"){
			Main.player.setMoveType(0);
		}else if(s=="pit"){
			Main.player.setMoveType(3);
		}else if(s=="smooth"){
			Main.player.setMoveType(1);
		}else if(s=="water"){
			Main.player.setMoveType(4);
		}else if(s=="age"){
			Main.player.setMoveType(2);
			
		}else if(s=="lock"){
			UserData.selectorLock=!UserData.selectorLock;
		}else if(s=="z+"){
			
			if(UserData.getSelectionSize() < 16){
				UserData.setSelectionSize(UserData.getSelectionSize() + 1);
				redoZ();
			}

		}else if(s=="z-"){
			if(UserData.getSelectionSize() > 1){
				UserData.setSelectionSize(UserData.getSelectionSize() - 1);
				redoZ();
			}


		}else if(s=="d+"){
			if(Main.player.getCommandCount() < 60){
				Main.player.setCommandCount(Main.player
						.getCommandCount() + 1);
				redoD();
			}

		}else if(s=="d-"){
			if(Main.player.getCommandCount() > 0){
				Main.player.setCommandCount(Main.player
						.getCommandCount() - 1);
				redoD();
			}


		}else if(s=="a+"){
			if(Main.player.getAniMode() < 50){
				Main.player.setAniMode(Main.player.getAniMode() + 1);
				redoA();
			}

		}else if(s=="a-"){
			if(Main.player.getAniMode() > 1){
				Main.player.setAniMode(Main.player.getAniMode() - 1);
				redoA();
			}

		}else if(s=="Quit"){
			Main.player=Main.reservePlayer;
			Changer.title();
		}else if(s=="Save"){
			Main.world.land.saveHeightMap(FileManager.dir(), "savemap");
			Main.world.land.savePic(FileManager.dir(), "savemap");
			
		}else if(s=="Load"){
			Main.world.land.loadLand(FileManager.dir(), "savemap");
		}

	}
}
