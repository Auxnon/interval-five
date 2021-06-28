package interval;

import java.util.HashMap;

import org.lwjgl.input.Keyboard;

public class Controls {

	public boolean use;
	public boolean jump;
	public boolean left;
	public boolean right;
	public boolean up;
	public boolean down;
	public boolean sprint;
	public boolean melee;
	public boolean tool;
	public boolean TIME;

	public int keyTIME1=Keyboard.KEY_T;
	public int keyTIME2=Keyboard.KEY_RETURN;
	boolean timeLock=false;

	public int keyUse1=Keyboard.KEY_E;
	public int keyUse2=keyUse1;
	public int keyJump1=Keyboard.KEY_SPACE;
	public int keyJump2=keyJump1;
	public int keySprint1=Keyboard.KEY_LSHIFT;
	public int keySprint2=Keyboard.KEY_RSHIFT;
	public int keyLeft1=Keyboard.KEY_LEFT;
	public int keyLeft2=Keyboard.KEY_A;
	public int keyRight1=Keyboard.KEY_RIGHT;
	public int keyRight2=Keyboard.KEY_D;
	public int keyUp1=Keyboard.KEY_UP;
	public int keyUp2=Keyboard.KEY_W;
	public int keyDown1=Keyboard.KEY_DOWN;
	public int keyDown2=Keyboard.KEY_S;
	public int keyMelee1=Keyboard.KEY_Q;
	public int keyMelee2=keyMelee1;
	public int keyTool1=Keyboard.KEY_R;
	public int keyTool2=keyTool1;

	public boolean hold=false;

	boolean once=true;
	public  void run(){
		keyListener();
		if( !Render.getGui().isRender()||hold){
			return;
		}
		use=Keyboard.isKeyDown(keyUse1) || Keyboard.isKeyDown(keyUse2);
		jump=Keyboard.isKeyDown(keyJump1) || Keyboard.isKeyDown(keyJump2);
		left=Keyboard.isKeyDown(keyLeft1) || Keyboard.isKeyDown(keyLeft2);
		right=Keyboard.isKeyDown(keyRight1) || Keyboard.isKeyDown(keyRight2);
		up=Keyboard.isKeyDown(keyUp1) || Keyboard.isKeyDown(keyUp2);
		down=Keyboard.isKeyDown(keyDown1) || Keyboard.isKeyDown(keyDown2);
		melee=Keyboard.isKeyDown(keyMelee1) || Keyboard.isKeyDown(keyMelee2);
		tool=Keyboard.isKeyDown(keyTool1) || Keyboard.isKeyDown(keyTool2);
		sprint=Keyboard.isKeyDown(keySprint1) || Keyboard.isKeyDown(keySprint2);
		if(Keyboard.isKeyDown(keyTIME1) || Keyboard.isKeyDown(keyTIME2)){
			if(timeLock &&Render.getGui().isGame() && !UserData.paused && Main.level.getStats().canTimeTravel() &&!Main.player.inside){
				timeLock=false;
				Main.player.timeTravel();
			}
		}else{
			timeLock=true;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_F1)){
			if(once)Changer.toggleGui();once=false;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_F2)){
			if(once)Main.world.wipeP();once=false;
		}else{
			once=true;
		}
	}
	public void defaulter(){
		keyUse1=Keyboard.KEY_E;
		keyUse2=keyUse1;
		keyJump1=Keyboard.KEY_SPACE;
		keyJump2=keyJump1;
		keySprint1=Keyboard.KEY_LSHIFT;
		keySprint2=Keyboard.KEY_RSHIFT;
		keyLeft1=Keyboard.KEY_LEFT;
		keyLeft2=Keyboard.KEY_A;
		keyRight1=Keyboard.KEY_RIGHT;
		keyRight2=Keyboard.KEY_D;
		keyUp1=Keyboard.KEY_UP;
		keyUp2=Keyboard.KEY_W;
		keyDown1=Keyboard.KEY_DOWN;
		keyDown2=Keyboard.KEY_S;
		keyMelee1=Keyboard.KEY_Q;
		keyMelee2=keyMelee1;
		keyTool1=Keyboard.KEY_R;
		keyTool2=keyTool1;

	}

	public void set(String st,int key){
		if(st=="kuse1"){
			keyUse1=key;
		}else if(st=="kuse2"){
			keyUse2=key;
		}else if(st=="kjump1"){
			keyJump1=key;
		}else if(st=="kJump2"){
			keyJump2=key;
		}else if(st=="kleft1"){
			keyLeft1=key;
		}else if(st=="kleft2"){
			keyLeft2=key;
		}else if(st=="kright1"){
			keyRight1=key;
		}else if(st=="kright2"){
			keyRight2=key;
		}else if(st=="kup1"){
			keyUp1=key;
		}else if(st=="kup2"){
			keyUp2=key;
		}else if(st=="kdown1"){
			keyDown1=key;
		}else if(st=="kdown2"){
			keyDown2=key;
		}else if(st=="kmelee1"){
			keyMelee1=key;
		}else if(st=="kmelee2"){
			keyMelee2=key;
		}else if(st=="kitem1"){
			keyTool1=key;
		}else if(st=="kitem2"){
			keyTool2=key;
		}else if(st=="ksprint1"){
			keySprint1=key;
		}else if(st=="ksprint2"){
			keySprint2=key;
		}
	}
	private String keyString="";
	public int keyBind=0;
	public void keyListener(){
		if(Keyboard.next() && Keyboard.getNumKeyboardEvents()==0){
			int c=Keyboard.getEventKey();
			if( Keyboard.getEventKeyState() &&c!=Keyboard.CHAR_NONE){
				
				keyString=Keyboard.getKeyName(c);
				String S=norms.get(keyString);
				if(S!=null)
					keyString=S;
				keyBind=c;
				//Message.addPass(keyBind+"  "+keyString);
				return;
			}
		}
		keyString="";
	}
	
	public String getKey(){
		return keyString.toUpperCase();
	}
	
	public String getPureKey(){
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)){
			String st=shifts.get(keyString);
			if(st!=null)
				return st;
			
			return keyString.toUpperCase();
		}
		return keyString.toLowerCase();
	}
	static HashMap<String,String> shifts;
	static HashMap<String,String> norms;
	static{
		shifts=new HashMap<String,String>();
		norms=new HashMap<String,String>();
		
		shifts.put("1", "!");
		shifts.put("2", "@");
		shifts.put("3", "#");
		shifts.put("4", "$");
		shifts.put("5", "%");
		shifts.put("6", "^");
		shifts.put("7", "&");
		shifts.put("8", "*");
		shifts.put("9", "(");
		shifts.put("0", ")");
		shifts.put("-", "_");
		shifts.put("=", "+");
		shifts.put("[", "{");
		shifts.put("]", "}");
		shifts.put(";", ":");
		shifts.put("/", "?");
		shifts.put(",", "<");
		shifts.put(".", ">");
		shifts.put("`", "~");
		
		norms.put("LBRACKET", "[");
		norms.put("RBRACKET", "]");
		norms.put("SEMICOLON", ";");
		norms.put("APOSTROPHE", "'");
		norms.put("COMMA", ",");
		norms.put("PERIOD", ".");
		norms.put("SLASH", "/");
		norms.put("BACKSLASH", "\\");
		norms.put("EQUALS", "=");
		norms.put("MINUS", "-");
	}
}
