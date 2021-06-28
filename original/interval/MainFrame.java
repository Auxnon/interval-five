package interval;
import java.awt.Canvas;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

public class MainFrame extends JFrame implements ComponentListener, WindowListener  {
	private static final long serialVersionUID = 1679372649878590108L;
	static Canvas canvas;
	//JPanel panel;
	//WIDTH;
	
	public static int width =768;
	public static int height=600;
	
	public static float screenStretch=0f;
	public static JFrame instance;
	
	public static int getW(){
		return canvas.getWidth();
	}
	public static int getH(){
		return canvas.getHeight();
	}
	public MainFrame(){
		super("Interval");
	}
	
	public void init(){

		this.setSize(width,height);
		this.setVisible(true);
		canvas =new Canvas();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.add(canvas);
		
		this.addComponentListener(this);
		this.setVisible(true);
		this.addWindowListener(this);

	
		this.setIconImage(FileManager.loadImage("playo2"));
		
		//this.setMaximumSize(this.getToolkit().getScreenSize());
		parent();
		
		instance=this;
	}
	public static JFrame get(){
		return instance;
	}
	public static void set(String s){
		instance.setTitle(s);
	}
	@Override
	public void componentHidden(ComponentEvent arg0) {
	}
	@Override
	public void componentMoved(ComponentEvent arg0) {
	}
	@Override
	public void componentResized(ComponentEvent e) {
		
		fit();
		Changer.refreshGui();
		
		//Message.m(this,"uh "+	this.getLayeredPane().
		
	}
	boolean maxed=false;
	public void fit(){
		fit(getWidth(),getHeight()-32);
	}
	public void fit(int w,int h){
		
		int ww=Display.getDesktopDisplayMode().getWidth();
		int hh=Display.getDesktopDisplayMode().getHeight()-35;
		if(w>ww && h>hh){
			setExtendedState(getExtendedState()|JFrame.MAXIMIZED_BOTH);
			width=ww;
			height=hh;
		}else{
			if(w>ww){
				w=ww;this.setLocation(0,this.getLocation().y);
			}
			if(h>hh){
				h=hh;this.setLocation(this.getLocation().x, 0);
			}
			//super.setSize(w, h);
			width=w;
			height=h;
		}
		MainFrame.screenAutoScale();
	}
	public void parent(){
		try {
			Display.setParent(canvas);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void componentShown(ComponentEvent arg0) {
	}
	public static void screenAutoScale(){
		//canvas.
		float w=(float)canvas.getWidth();
		float h=(float)canvas.getHeight();
		screenStretch=32f*(w-h)/h;
		sr=64f+screenStretch;
		ss=64f+screenStretch*2;
		sl=-screenStretch;
		Message.m(MainFrame.class,"autoScale "+sr);
	}
	private static float sr;
	private static float ss;
	private static float sl;
	
	public static float screenSize(){
		return ss;
	}
	public static float screenRight(){
		return sr;
	}
	public static float screenLeft(){
		return sl;
	}
	public static boolean isLong(){
		return ss>64;
	}


	@Override
	public void windowClosing(WindowEvent e) {
		Main.sound.end();
		Message.m(MainFrame.class,"sounds closed");
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	
}
