package interval;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Used as a means to create a popup the user can copy text from. Until proper copy-pasteable dialog boxes are made ingame.
 * @author Aninon
 *
 */
public class MsgWindow extends JDialog implements WindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2997396447224135472L;

	Font font=new Font("sansserif", Font.BOLD, 14);
	boolean out;
	public MsgWindow(String title,String message){
		this(title,message,false);
	}
	
	public MsgWindow(String title,boolean edit){
		this(title,"",edit);
	}
	JTextArea l;
	public MsgWindow(String title,String message,boolean edit){
		this.setTitle(title);
		setSize(250,60);
		l=new JTextArea(message);
		l.setFont(font);
		l.setLineWrap(true);
		l.setWrapStyleWord(true);
		l.setEditable(edit);
		out=edit;
		JPanel p=(JPanel) this.getContentPane();
		p.setLayout(new BorderLayout());
		p.add(l, BorderLayout.CENTER);
		setLocationRelativeTo(Main.mainFrame);
		setVisible(true);
		this.setAlwaysOnTop(true);
		pack();
		
		this.addWindowListener(this);
	}
	
	public MsgWindow(String s){
		this.setTitle("Message");
		JLabel l=new JLabel(s);
		l.setFont(font);
		add(l);
		setSize(200,60);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
	
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		if(out)
			Render.getGui().command("POP"+l.getText(), null);
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
