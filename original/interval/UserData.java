package interval;

public class UserData {

	public static int TEM=0;
	static float health;
	static float energy;
	static int paradoxes;
	private static final float speed=0.10f;
	private static final float sideSpeed=getSpeed()*0.707f;
	
	static double timeRate=1;
	private static final float sspeed=0.25f;
	private static final float ssideSpeed=getSSpeed()*0.707f;
	
	private static final float sneakSpeed=0.05f;
	private static final float sneakSideSpeed=getSneakSpeed()*0.707f;
	static int invSlots=3;
	
	static private boolean terminal=false;
	static public boolean fullScreen=false;
	static public int resolution=0;
	
	private static final float jumpSpeed=0.1f;
	private static float fatness=0f;
	private static boolean VoxelRefine=true;
	private static int money;
	private static int landModels=16;//64;//32; //16
	private static int landView=1;//3;//2; //1
	private static int selectionSize=4;
	public static boolean selectorLock=false;
	
	static boolean fancyGrass=false;
	
	static boolean timeFreeze=false;
	private static boolean pastParser=false;
	
	//public static short gameMode=0;//0= menu 1=game 2=editor 3=stick
	
	private static String tempModel="playerIdle";
	
	public static boolean noGui=false;
	private static int shaderMode=0;
	private static boolean shaderSuccess=true;
	private static float gravity=0.01f;
	private static float wGravity=0.002f;
	
	public static float getGravity(){
		return gravity;
	}
	public static void setGravity(float g){
		gravity=g;
		wGravity=g/5f;
	}
	
	public static float getWaterGravity(){
	return wGravity;
	}
	
	public static boolean shadersOn(){
		return shaderMode<3;
	}
	public static boolean shaderLight(){
		return shaderMode<2;
	}
	public static int getShaderMode(){
		return shaderMode;
	}
	public static void setShaderMode(int u){
		if(shaderSuccess)
		shaderMode=u;
	}
	public static void shaderFail(){
		shaderSuccess=false;
		shaderMode=3;
	}
	public static boolean canShader(){
		return shaderSuccess;
	}
	
	public static boolean isGui(){
		return !noGui;
	}
	public static void guiOff(){
		noGui=true;
	}
	public static void guiOn(){
		noGui=false;
	}
	/*
	public static boolean isGame(){
		return gameMode==1;
	}
	public static boolean isMenu(){
		return gameMode==0;
	}
	public static boolean isEditor(){
		return gameMode==2;
	}
	public static boolean isStuck(){
		return gameMode==3;
	}
	public static void setGame(){
		gameMode=1;
	}
	public static void setMenu(){
		gameMode=0;
	}
	public static void setEditor(){
		gameMode=2;
	}
	public static void setStuck(){
		gameMode=3;
	}*/
	public static boolean paused=false;
	//private static boolean render=true;

	public static boolean errorMessage() {
		return terminal;
	}
	public static boolean isFullscreen(){
		return fullScreen;
	}
	public static double timeRate(){
		return timeRate;
	}
	public static void setTimeRate(double d){
		
		//int dd=(int) (1/d);
	//	Message.addPass("rate set to 1/"+(dd));
		timeRate=d;
		Main.world.timeline.setTimeFactor(d);
	}
	public static float getSSideSpeed() {
		return ssideSpeed;
	}
	public static float getSneakSideSpeed() {
		return sneakSideSpeed;
	}
	public static float getSneakSpeed() {
		return sneakSpeed;
	}
	public static float getSSpeed() {
		return sspeed;
	}
	public static float getSpeed() {
		return speed;
	}
	public static float getSideSpeed() {
		return sideSpeed;
	}
	public static float getJumpSpeed() {
		return jumpSpeed;
	}
	public static int getMoney() {
		return money;
	}
	public static void setMoney(int money) {
		UserData.money = money;
	}
	public static void addMoney(int value) {
		money+=value;
	}
	public static int getSelectionSize() {
		return selectionSize;
	}
	public static void setSelectionSize(int selectionSize) {
		UserData.selectionSize = selectionSize;
	}
	public static boolean isPastParser() {
		return pastParser;
	}
	public static void setPastParser(boolean pastParser) {
		UserData.pastParser = pastParser;
	}
	public static int getLandView() {
		return landView;
	}
	public static void setLandView(int landView) {
		if(landView<1)
			landView=1;
		UserData.landView = landView;
	}
	public static boolean isVoxelRefine() {
		return VoxelRefine;
	}
	public static void setVoxelRefine(boolean voxelRefine) {
		Message.addPass("Refined Voxels "+(voxelRefine?"Enabled":"Disabled"));
		VoxelRefine = voxelRefine;
	}
	public static float getFatness() {
		return fatness;
	}
	public static void setFatness(float fatness) {
		UserData.fatness = fatness;
	}
	public static int getLandModels() {
		return landModels;
	}
	public static void setLandModels(int landModels) {
		UserData.landModels = landModels;
	}
	public static String getTempModel() {
		return tempModel;
	}
	public static void setTempModel(String tempModel) {
		//Main.player.renewDimensions();
		//UserData.tempModel = tempModel;
	}
	

	
}
