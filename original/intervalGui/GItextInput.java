package intervalGui;

import interval.Message;
import interval.VecF;



public class GItextInput extends GI {
	VecF colorP=new VecF(1,1,1,1);
	
	boolean TYPE=false;
	boolean clearOnSelect=false;
	boolean filterNumbers=false;
	String Prefix="";
	public GItextInput(String name,float x,float y){
		super("   ",x,y);
	}
	public GItextInput(String name,float x,float y,boolean prefixText,boolean clearOnSelect,boolean filterNumbers){
		super(name,x,y);
		action="";
		if(prefixText)
			Prefix=name+": ";
		redo2(Prefix,0);
		this.clearOnSelect=clearOnSelect;
		this.filterNumbers=filterNumbers;
	}
	@Override
	public void unselect(){
		super.unselect();
		TYPE=false;
		endTyper();
	}
	@Override
	public String release(){
		super.release();
		toggle();
		return "";
	}
	public void toggle(){
		TYPE=!TYPE;
		if(!TYPE)
			endTyper();
		else if(clearOnSelect){
			action=""; redo2(Prefix,0);}
	}
	public void run(){
		if(TYPE){
			TYPE=typer(0,Prefix);
			if(!TYPE)
				endTyper();
		}
	}
	private void endTyper(){
		if(filterNumbers){
			action=action.replaceAll("[^0-9.]+","");
			redo2(Prefix+action,0);
		}
		command(name);
	}
	boolean u=false;
	float t=1;
	
	@Override
	public void render(float dx,float dy){
		if(TYPE){
			t+=u?0.05f:-0.05f;
			if(t<0){
				t=0;u=true;
			}else if(t>1){
				t=1;u=false;
			}
			colorP.setV(1,t,t);
			selection=colorP;
		}
		super.render(dx,dy);
	}
	public int getInt(){
		return (int) getFloat();
	}
	public float getFloat(){
		try{
			return Float.parseFloat(action);
		}catch(NumberFormatException exc){
			return 0;
		}
	}
	
}
