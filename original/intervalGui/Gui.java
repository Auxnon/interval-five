package intervalGui;

import interval.MainFrame;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public abstract class Gui {
	public boolean canPress = false;
	public boolean click = false;
	public boolean clicked = false;
	public boolean clickR = false;
	int d = -1;
	public boolean down = false;
	public boolean downRight = false;
	private short gameMode = 0;// 0= menu 1=game 2=editor 3=stick
	private ArrayList<GI> guis = new ArrayList<GI>();
	boolean hide = false;
	boolean keyAble = true;
	boolean keyPause = true;
	int lastGui = -1;

	public boolean menuPriority = false;
	boolean mouseLock = true;
	public float mx;
	public float my;
	private boolean render = true;
	private boolean scraped = false;
	//public boolean scroll = false;

	short sel = 0;

	public boolean shouldRelease = false;
	public boolean within = false;

	public Gui() {

	}

	public void add(GI gi) {
		if (gi instanceof GIdrop) {
			GIdrop gid = (GIdrop) gi;
			for (GI g : gid.drops) {
				add(g);
			}
		}
		gi.depth = -0.2f + guis.size() / 1000f;
		guis.add(gi);
	}

	protected void com(String s,GI g ) {
		if(s!="")
			command(s,g);
	}
	public void command(String s,GI g ) {

	}

	public void dafuq(boolean a) {
		for (GI g : guis) {
			g.y += a ? 1024 : -1024;
		}
	}

	public GI get(int u) {
		return guis.get(u);
	}
	float smy,rmy,smx,rmx;
	
	protected void sub(){
		 smy = my;
		 rmy = 0;
		 smx = mx;
		 rmx = 0;
	}
	protected void sub2(){
		
	}
	public void guisRun() {
		float highest = -99f;
		down = Mouse.isButtonDown(0);
		downRight = Mouse.isButtonDown(1);
		within = false;

		if (hide)
			return;

		sub2();
		sub();

		for (int i=0; i < guis.size(); i++) {
			
			GI G = guis.get(i);
			G.run();
			if (!G.hidden && (G.sticky?G.check(mx,my):G.check(smx,smy) ) ) {
				within = true;
				if (!G.displayOnly && G.depth > highest) {
					highest = G.depth;
					d = i;
					sel = (short) i;
				}
			}
		}

		boolean arrange = false;
		if (!within)
			shouldRelease = false;

		for(int i=0; i < guis.size() && !isScraped(); i++) {
			GI G = guis.get(i);
			if (!within && clicked) {
				d = -1;
				if (lastGui != -1) {
					guis.get(lastGui).unselect();
					lastGui = -1;
				}
			}
			if (i == d) {
				com(G.over(),G);
				if (down) {
					com(G.down(),G);
					if (click) {
						shouldRelease = true;
						com(G.click(),G);
						if (lastGui != -1 && i != lastGui) {
							guis.get(lastGui).unselect();
						}
						if (i < guis.size())
							lastGui = i;
						else
							lastGui = -1;

						arrange = true;
					}
				} else if (shouldRelease) {
					shouldRelease = false;
					com(G.release(),G);
				}
				if (downRight) {
					com(G.downR(),G);
					if (clickR) {
						com(G.clickR(),G);
					}
				}
				G.focused = true;
			} else {
				G.out();
				G.focused = false;
			}

			String las = G.last();
			if (las != "") {
				com(las,G);
			}
		}

		for (GI g : guis) {
			if(g.sticky)g.render(0,0);else g.render(-rmx,-rmy);
		}

		if (lastGui != -1) {
			if (menuPriority && arrange) {
				toFront();
				order();
			}
			if (!isScraped()) {
				guis.get(lastGui).lastSelected();
			}
		}
		if (d != -1) {
			d = -1;
		}
		clicked = click && down;
		click = !down;
		clickR = !downRight;
	}

	public boolean has(Object o) {
		return guis.contains(o);
	}

	abstract public void initer();

	public boolean isEditor() {
		return gameMode == 2;
	}

	public boolean isGame() {
		return gameMode == 1;
	}

	public boolean isMenu() {
		return gameMode == 0;
	}

	public boolean isRender() {
		return render;
	}

	public boolean isScraped() {
		return scraped;
	}

	public boolean isStuck() {
		return gameMode == 3;
	}

	void key() {
		keyPause = true;
		mouseLock = true;
		GI g = guis.get(sel);
		mx = g.x + 1;
		my = g.y + 1;
	}

	void keydown() {
		if (sel < 0) {
			sel = (short) (guis.size() - 1);
		}
	}

	void keyup() {
		if (sel >= guis.size()) {
			sel = 0;
		}
	}

	public void mous() {
		mx = ((MainFrame.screenSize()) * Mouse.getX() / MainFrame.getW())
				+ MainFrame.screenLeft();
		my = 64f * Mouse.getY() / MainFrame.getH();
	}

	public void mouser() {
		mous();
	}

	public void order() {
		float sz = guis.size();
		for (int u = 0; u < sz; u++) {
			guis.get(u).depth = -GI.stackSize * (u + 1);
		}
	}

	public void pick(GI gi) {
		if (guis.contains(gi)) {
			int u = 0;
			for (GI gg : guis) {
				if (gg.equals(gi)) {
					this.lastGui = u;
					break;
				}
				u++;
			}
		}
		toFront();
		order();
	}

	boolean preKey() {
		return !guis.get(sel).canKey;
	}

	public void refreshs() {
		for (int u = 0; u < guis.size(); u++) {
			guis.get(u).refresh();
		}
	}

	public void remove(int u) {
		guis.remove(u);
		if (lastGui == u)
			lastGui = -1;
	}
	public void remove(GI g) {
		int u=guis.indexOf(g);
		guis.remove(g);
		if (lastGui == u)
			lastGui = -1;
	}

	public void run() {
		mouser();
		guisRun();
	}

	public void segment(float x, float y, float z, float sx, float sy, float r,
			float g, float b, float a) {
		GL11.glColor4f(r, g, b, a);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex3f(x, y, z);
		GL11.glVertex3f(x + sx, y, z);
		GL11.glVertex3f(x + sx, y + sy, z);
		GL11.glVertex3f(x, y + sy, z);
		GL11.glEnd();
	}

	public void setEditor() {
		gameMode = 2;
	}

	public void setGame() {
		gameMode = 1;
	}

	public void setMenu() {
		gameMode = 0;
	}

	public void setRender(boolean render) {
		this.render = render;
	}

	public void setScraped(boolean scraped) {
		this.scraped = scraped;
	}

	public void setStuck() {
		gameMode = 3;
	}

	public int size() {
		return guis.size();
	}
	public ArrayList<GI> getG() {
		return guis;
	}

	public void startAnimation() {
		for (GI gi : guis) {
			gi.startAnimation();
		}
	}

	public void toFront() {
		GI g = guis.set(0, guis.remove(lastGui));
		guis.add(g);
		lastGui = 0;
	}
	public void hide(){
		hide=true;
	}
	public void unhide(){
		hide=false;
	}
	public boolean isHidden(){
		return hide;
	}

}
