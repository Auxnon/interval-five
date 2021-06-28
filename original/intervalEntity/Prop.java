package intervalEntity;

import interval.ModelManager;
import interval.VGO;

public class Prop extends EntityBasic {


	String model="coin";
	public void activate(){
		annual();
	}

	public Prop(){
	this.sideCollision=true;
		model="coin";
		
	}

	/*public Matrix4f getMat(){
		return Main.world.getViewMat();
	}*/


	public VGO getModel(){
		return ModelManager.getModel(model);
	}

	public String[] infer(String param){
		String st[]=super.infer(param);
		model=st[inferIndex];
		renewDimensions();
		//Message.m(this,"model: "+st[inferIndex]);
		inferIndex++;
		return st;
	}
	public String toString(){
		return super.toString()+","+model;
	}


}
