package intervalGui;

import interval.Changer;
import interval.IFont;
import interval.Main;
import interval.Message;
import interval.Render;
import interval.UserData;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;


public class GuiOptions extends Gui {
	//GI view;
	GIlist gl;
	
	public void initer() {
		//super();
		this.menuPriority=true;
		GI so =new GI("Sound??",0,60,IFont.EMBOSS);
		so.displayOnly=true;
		so.canKey=false;
		add(so);
		int ia[]=UserData.isVoxelRefine()?new int[]{14,15}:new int[]{15,14};

		int ii[];
		switch(UserData.getShaderMode()){
		case 1: ii=new int[]{11,12,13,10};break;
		case 2: ii=new int[]{12,13,10,11};break;
		case 3: ii=new int[]{13,10,11,12};break;
		default:  ii=new int[]{10,11,12,13};
		}

		//view=new GI("View Distance: "+UserData.getLandView(),0,48,IFont.BLACK);
		//view.ambient();
		add(new GItoggle("Refined Voxels","voxel",ia,0,56));

		GIscale view =new GIscale("View Distance:","VScale",0,52,UserData.getLandView(),16,10);
		add(view);

		
		GItoggle sha=new GItoggle("Shader Mode","shade",ii,0,48);
		if(!UserData.canShader())
			sha.ambient();
		add(sha);
		
		
		
		//add(new GI("Fatness",0,42));
		add(new GI("Controls ->",0,44));
		add(new GI("Fullscreen",0,40));
		add(new GI("<- Back",0,6).centerLeft());
		
		DisplayMode dm[];
		try {
			dm = Display.getAvailableDisplayModes();
			String ss[]= new String[dm.length];
			for(int u=0;u<dm.length;u++){
				ss[u]=u+" :"+dm[u].getWidth()+"x"+dm[u].getHeight()+" -"+dm[u].getFrequency()+"hz "+dm[u].getBitsPerPixel()+"bpp";
			}
			gl=new GIlist("Resolution",ss,-1,0,36,IFont.PILLAR);
			add(gl);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		Render.border(3, 1,0);
	}

	public void command(String s,GI g){
		if(s.contains("voxel")){
			UserData.setVoxelRefine(s.endsWith("14"));
			//cb.value=UserData.isVoxelRefine();
			//ActorManager.modelDO();
			Changer.gc();
		}else if(s.contains("shade")){
			if(s.endsWith("3")){
				UserData.setShaderMode(3);Message.addPass("Shader Mode: Off (limited special effects ;__; )");
			}else if(s.endsWith("2")){
				UserData.setShaderMode(2);Message.addPass("Shader Mode: No-Lights (neglible performance gain)");
			}else if(s.endsWith("1")){
				UserData.setShaderMode(1);Message.addPass("Shader Mode: Vertex-Lighting (easier on widescreen)");
			}else {
				UserData.setShaderMode(0);Message.addPass("Shader Mode: Normal"); 
			}
		}else if(s=="Fullscreen"){
			Changer.fullscreen();
		}else if(s=="Fatness"){
			if(UserData.getFatness()==0){
				UserData.setFatness(0.7f);
			}else{
				UserData.setFatness(0);
			}
		}else if(s.contains("Resolution")){
			if(gl.select!=null){
				GI ii=gl.select;
				String sss=ii.action;
				UserData.resolution=Integer.parseInt(sss.substring(0, sss.indexOf(":")).trim());
				if(!UserData.isFullscreen()){
					try {
						DisplayMode dp = Display.getAvailableDisplayModes()[UserData.resolution];
						Main.mainFrame.setSize(dp.getWidth(), dp.getHeight());
					} catch (LWJGLException e) {
					}
				}
			}
		}else if(s=="<- Back"){
				Changer.menuUp();
		}else if(s=="Controls ->"){
			Changer.menuDown(new GuiControls());
		}else if(s.contains("VScale")){
			UserData.setLandView(Integer.parseInt(s.substring(6,s.length())));
			switch(UserData.getLandView()){
			case 0:UserData.setLandModels(2);break;
			case 1:UserData.setLandModels(9);break;
			case 2:UserData.setLandModels(25);break;
			case 3:UserData.setLandModels(49);break;
			case 4:UserData.setLandModels(81);break;
			case 5:UserData.setLandModels(121);break;
			case 6:UserData.setLandModels(169);break;
			case 7:UserData.setLandModels(225);break;
			case 8:UserData.setLandModels(289);break;
			case 9:UserData.setLandModels(361);break;
			}
			/*String herk="View Distance: "+UserData.getLandView();
			if(!view.action.contains(herk)){
				view.redo(herk, IFont.BLACK);
				//view.centerLeft();
			}*/

		}
	}
}
