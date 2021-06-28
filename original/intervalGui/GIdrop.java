package intervalGui;

import interval.IFont;
import interval.Message;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GIdrop extends GI {
	Color bakt = null;
	public ArrayList<GI> drops;
	GIdrop mother;
	boolean shrunk = true;
	float tab = 4;
	BufferedImage txtt = null;
	public int weighted;

	public GIdrop(String a, float xi, float yi, GI... g) {
		this(a, xi, yi, 0, g);
	}

	public GIdrop(String a, float xi, float yi, int t, BufferedImage txt,
			Color bak, GI... g) {
		super(a + " >", xi, yi, t, txt, bak);
		txtt = txt;
		bakt = bak;
		action = a;
		name=a;
		drops = new ArrayList<GI>();
		for (GI gi : g) {
			add(gi);
		}
	}

	public GIdrop(String a, float xi, float yi, int t, GI... g) {
		super(a + " >", xi, yi, t);
		action = a;
		name=a;
		drops = new ArrayList<GI>();
		for (GI gi : g) {
			add(gi);
		}
	}

	public void add(GI g) {
		g.hide();
		drops.add(g);
		if (g instanceof GIdrop)
			((GIdrop) g).mother = this;
	}

	public void childCall() {
		if (mother == null)
			order();
		else
			mother.childCall();
	}

	public String click() {
		Message.m(this, "l?");
		if (shrunk)
			open();
		else
			close();

		return name;
	}

	public void close() {
		shrunk = true;
		redo2(action + "~<", textType, false, txtt, bakt);
		for (GI g : drops) {
			g.hide();
		}

		if (mother != null)
			mother.childCall();
	}

	@Override
	public void hide() {
		super.hide();
		close();
	}

	public void open() {
		shrunk = false;
		redo2(action + "~v", textType, false, txtt, bakt);
		for (GI g : drops) {
			g.unhide();
		}
		if (mother == null)
			order();
		else
			mother.childCall();
	}

	public float order() {
		if (shrunk)
			return y - sy;
		float last = y - sy;
		//Message.m(this, "o: " + drops.size());
		for (GI g : drops) {
			g.x = x + tab;
			g.y = last;
			//Message.m(this, "l: " + last + " " + action);
			if (g instanceof GIdrop) {
				last = ((GIdrop) g).order();
			} else {
				last = g.y - g.sy;
			}
		}
		return last;
	}
	
	public void dropFigure(int T){
		
		int To=0;
		for(GI g:drops){
			if(g instanceof GIdrop){
				GIdrop gd=((GIdrop)g);
				gd.dropFigure(T);
				To+=gd.weighted;
			}
		}
		weighted+=To;
		bakt=IFont.getC(weighted,T);
		Message.m(this,"weight: "+action+" "+weighted);
	}

}
