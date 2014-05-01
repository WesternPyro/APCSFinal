package game;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class StartMenu extends JFrame implements ActionListener
{
	public JFrame startMenu = new JFrame();
	boolean display = false;
	final JButton staticMove;
	final JButton trackingMove;
	
	public StartMenu()
	{
		staticMove = new JButton("Scrolling");
		trackingMove = new JButton("Free Play");
		
		startMenu.setSize(120, 90);
		startMenu.setLocationRelativeTo(null);
		startMenu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		startMenu.setFocusable(true);
		startMenu.setResizable(true);
		startMenu.setLayout(new BoxLayout(startMenu, BoxLayout.Y_AXIS));
		addComponentsToFrame(startMenu);
		startMenu.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				startMenu.setVisible(false);
				startMenu.dispose();
				Mechanics.gamePlay = true;
		}});
		buttonListeners();
		
		startMenu.add(staticMove);
		startMenu.add(trackingMove);
	}
	public void actionPerformed(ActionEvent e) 	{}
	
	public void buttonListeners()
	{
		staticMove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Mechanics.tracking = false;
				Mechanics.reform = true;
				startMenu.dispose();
				Mechanics.gamePlay = true;
		}});
		staticMove.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		trackingMove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Mechanics.tracking = true;
				startMenu.dispose();
				Mechanics.gamePlay = true;
		}});
		trackingMove.setAlignmentX(Component.CENTER_ALIGNMENT);
	}

	public void show() 
	{
		if (!startMenu.isVisible())
			startMenu.setVisible(true);
		else
			startMenu.dispose();
	}

	public static void addComponentsToFrame(Container pane) {
		pane.setLayout(new BoxLayout(((JFrame)pane).getContentPane(),
				BoxLayout.Y_AXIS));
	}
}
