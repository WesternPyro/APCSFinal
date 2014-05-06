package game;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class DifficultyMenu extends JFrame implements ActionListener
{
	public JFrame diffMenu = new JFrame();
	boolean display = false;
	final JButton one;
	final JButton two;
	final JButton three;
	final JButton four;
	final JButton newGame;
	
	public DifficultyMenu()
	{
		one = new JButton("Level I");
		two = new JButton("Level II");
		three = new JButton("Level III");
		four = new JButton("Level IV");
		newGame = new JButton("New Game");
		
		diffMenu.setSize(100, 170);
		diffMenu.setLocationRelativeTo(null);
		diffMenu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		diffMenu.setFocusable(true);
		diffMenu.setResizable(true);
		diffMenu.setLayout(new BoxLayout(diffMenu, BoxLayout.Y_AXIS));
		addComponentsToFrame(diffMenu);
		diffMenu.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				diffMenu.setVisible(false);
				diffMenu.dispose();
				Mechanics.gamePlay = true;
		}});
		buttonListeners();
		
		diffMenu.add(one);
		diffMenu.add(two);
		diffMenu.add(three);
		diffMenu.add(four);
		diffMenu.add(newGame);
	}
	public void actionPerformed(ActionEvent e) 	{}
	
	public void buttonListeners()
	{
		one.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Mechanics.difficulty = 5;
				if(!Mechanics.tracking) {
					Mechanics.lose = true;
					Mechanics.reform = true;
				}
				diffMenu.dispose();
				Mechanics.gamePlay = true;
		}});
		one.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		two.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Mechanics.difficulty = 7;
				if(!Mechanics.tracking) {
					Mechanics.lose = true;
					Mechanics.reform = true;
				}
				diffMenu.dispose();
				Mechanics.gamePlay = true;
		}});
		two.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		three.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Mechanics.difficulty = 9;
				if(!Mechanics.tracking) {
					Mechanics.lose = true;
					Mechanics.reform = true;
				}
				diffMenu.dispose();
				Mechanics.gamePlay = true;
		}});
		three.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		four.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Mechanics.difficulty = 11;
				if(!Mechanics.tracking) {
					Mechanics.lose = true;
					Mechanics.reform = true;
				}
				diffMenu.dispose();
				Mechanics.gamePlay = true;
		}});
		four.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		newGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Mechanics.lose = true;
				Mechanics.reform = true;
				diffMenu.dispose();
				Mechanics.gamePlay = true;
		}});
		newGame.setAlignmentX(Component.CENTER_ALIGNMENT);
	}

	public void show() 
	{
			if (!diffMenu.isVisible())
				diffMenu.setVisible(true);
			else
				diffMenu.dispose();
	}

	public static void addComponentsToFrame(Container pane) {
		pane.setLayout(new BoxLayout(((JFrame)pane).getContentPane(),
				BoxLayout.Y_AXIS));
	}
}

