package intervalGui;

public class GItoggle extends GIbutton {

	int mode;
	int[] modes;
	boolean dual=false;

	/**
	 * 
	 * @param action name
	 * @param sub name
	 * @param icons
	 * @param x
	 * @param y
	 */
	GItoggle(String a,String s,int[] icons, float x, float y) {
		super(a,s,icons[0], x, y);
		modes=icons;
	}
	public GItoggle dualOn(){
		dual=true;
		return this;
	}
	public int getMode(){
		return mode;
	}

	public String click(){
		if(!dual || !regular){
			mode++;
			if(mode>=modes.length)
				mode=0;
			setIcon(modes[mode]);
			return sub+modes[mode];
		}
		return name;
	}
	public String release(){
		return "";
	}
	public String clickR(){
		if(!dual || !regular){
			mode--;
			if(mode<0)
				mode=modes.length-1;
			setIcon(modes[mode]);
			return sub+mode;
		}
		return name;
	}
}
