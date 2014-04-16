package game;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Color;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GameGraphics
{
	private static Random generate;
	private static ArrayList<Cloud> clouds;
	private static Image sprite;

	public static void init()
	{
		generate = new Random();

		clouds = new ArrayList<Cloud>();
		for(int c = 0; c < 100; c++)
		{
			int x = generate.nextInt(950);
			int y = generate.nextInt(23000)-22500;
			clouds.add(new Cloud(x,y));
		}
		
		try	{ sprite = ImageIO.read(new File("rocketship.png")); }
		catch (IOException e) {}
	}

	public static void title(Graphics g, int height)
	{
		try
		{
			background(g,height);
			g.setColor(Color.black);
			g.setFont(new Font("Arial",Font.PLAIN,72));
			g.drawString("To the Clouds", 290, 250);
			g.setFont(new Font("Arial",Font.PLAIN,36));
			g.drawString("Press the space bar to pause the game.",190,330);
			g.drawString("Press 'M' to open up the screen movement menu.",120,370);
			g.drawString("Click the left mouse button to continue.",205,410);
		}
		catch (NullPointerException e) {}
	}
	
	public static void lose(Graphics g, int height)
	{
		background(g,height);
		g.setColor(Color.black);
		g.setFont(new Font("Arial",Font.PLAIN,72));
		g.drawString("GAME OVER",300,300);
		g.setFont(new Font("Arial",Font.PLAIN,36));
		g.drawString("Click once to return to the title screen.",225,375);
	}
	
	public static void win(Graphics g, int height)
	{
		background(g,height);
		g.setColor(Color.black);
		g.setFont(new Font("Arial",Font.PLAIN,72));
		g.drawString("YOU WIN!",340,300);
		g.setFont(new Font("Arial",Font.PLAIN,36));
		g.drawString("Click once to return to the title screen.",215,375);
	}

	public static void background(Graphics g, int height)
	{
		try
		{
			g.setColor(new Color(138,205,255));
			g.fillRect(0,0,1000,900);
			ground(g,height);
			for(int c = 0; c < clouds.size(); c++)
				clouds.get(c).drawCloud(g,height);
		}
		catch (NullPointerException e) {}
	}

	public static void ground(Graphics g, int height)
	{
		g.setColor(new Color(25,75,25));
		g.fillRect(0,750+height,1000,150);
	}

	public static void sprite(Graphics g, int mouseX, double elevation) { g.drawImage(sprite,mouseX-21,700-(int)elevation-32,null); }
}