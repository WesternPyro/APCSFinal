package game;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.awt.geom.Line2D.Double;

import javax.swing.JFrame;

public class GameFrame
{
    public static void main(String[] args)
    {
    	Mechanics tester = new Mechanics();
		tester.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tester.setSize(1010,900);
		tester.setVisible(true);
    }
}

@SuppressWarnings("serial")
class Mechanics extends JFrame implements MouseListener, MouseMotionListener, KeyListener
{
	protected int mouseX;
	protected int appletWidth,appletHeight;
	protected int height,elevation,score;
	protected int platformX,platformY,prevX,prevY,prevPlatY;
	protected int numClicks;
	protected int count,top;
	protected int fallCount;
	protected static int difficulty,level;
	protected static String dif;
	protected static double velocity;
	protected static boolean lose,win,finalWin,tracking,reform;
	protected static boolean gamePlay,levelCompleted;
	protected static ArrayList<Platform> platforms,removedPlatforms;
	protected Image virtualMem;
	protected Graphics gBuffer;
	protected Random generate;
	protected DecimalFormat format;
	protected StartMenu start;
	protected DifficultyMenu diff;

	public Mechanics()
	{
		super("To the Clouds");
		setSize(1010,900);
		setVisible(true);
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		GameGraphics.init();
		menuInit();
		velocity = 23;
		mouseX = 0;
		platformX = platformY = 0;
		prevX = 0;
		prevY = 0;
		height = 0;
		elevation = 0;
		count = 0;
		top = 0;
		fallCount = 0;
		difficulty = 7;
		level = 1;
		numClicks = 0;
		lose = false;
		win = false;
		finalWin = false;
		tracking = true;
		reform = false;
		gamePlay = true;
		generate = new Random();
		format = new DecimalFormat("00000");
		platforms = new ArrayList<Platform>();
		removedPlatforms = new ArrayList<Platform>();
		createPlatforms();
		
		try
		{
			appletWidth = getWidth();
			appletHeight = getHeight();
			virtualMem = new BufferedImage(appletWidth,appletHeight,BufferedImage.TYPE_INT_RGB);
			gBuffer = virtualMem.getGraphics();
		}
		catch(NullPointerException e) {}

		Graphics2D g2d = (Graphics2D) gBuffer;
		g2d.translate(8,30);
	}

	public void paint(Graphics g)
	{
		if(gamePlay)
		{
			if(numClicks == 0) 
			{ 
				GameGraphics.title(gBuffer,height);
				score = 0;
				elevation = 0;
				height = 0;
				velocity = 23;
				lose = false;
				win = false;
			}
			else if(!(lose||win)) 			{ runGame(); }
			else if(lose && tracking) 		{ trackingLose(); }
			else if(lose && !tracking) 		{ staticLose(); }
			else if(win && !tracking) 		{ levelWin(); }
			else if(finalWin && !tracking) 	{ finalWin(); }
			
			g.drawImage(virtualMem,0,0,null);
		}
		repaint();
	}

	public void update(Graphics g)	{ paint(g); }
	
	public void runGame()
	{
		GameGraphics.background(gBuffer,height);
		GameGraphics.sprite(gBuffer,mouseX,elevation);
		drawPlatforms(gBuffer,height);
		checkBounce(mouseX,elevation,prevX,prevY);
		setVelocity();
		moveScreen();
		getScore(gBuffer);
		delay(1000/65);
	}
	
	public void trackingLose()
	{
		GameGraphics.lose(gBuffer,height); 
		numClicks = -1;
		gBuffer.drawString("Your score was "+score,350,360);
		reform = true;
		resetPlatforms();
	}
	
	public void staticLose()
	{
		GameGraphics.lose(gBuffer,height);
		numClicks = -1;
		level = 1;
		resetPlatforms();
	}
	
	public void levelWin()
	{
		GameGraphics.win(gBuffer,height);
		numClicks = -1;
		if(levelCompleted) {
			if(level < 5)
				level++;
			reform = true;
			resetPlatforms();
		}
		levelCompleted = false;
	}
	
	public void finalWin()
	{
		GameGraphics.finalWin(gBuffer,height);
		numClicks = -1;
		level = 1;
		reform = true;
		resetPlatforms();
	}
	
	public void delay(int n)
	{
		long startDelay = System.currentTimeMillis();
		long endDelay = 0;
		while (endDelay - startDelay < n)
			endDelay = System.currentTimeMillis();
	}

	public void setVelocity()
	{
		prevY = elevation;

		if(velocity > -22.5)
			velocity -= .5;
		
		elevation += velocity;
	}

	public void drawPlatforms(Graphics g, int height)
	{
		try {
			for(int c = 0; c < platforms.size(); c++)
				platforms.get(c).drawPlatform(g,height);
		}
		catch (NullPointerException e) {}
	}

	public void checkBounce(int mouseX,double elevation,int prevX,double prevY)
	{
		try {
			for(int c = 0; c < platforms.size(); c++)
			{
				if(platforms.get(c) instanceof MovePlatform || platforms.get(c) instanceof FallPlatform)	
					platforms.get(c).move();
				if(checkPath(platforms.get(c),mouseX,elevation,prevX,prevY))
				{
					elevation = 700-platforms.get(c).tlY-32;
					velocity = 23.0;
					if(platforms.get(c) instanceof BreakPlatform) {
						removedPlatforms.add(platforms.get(c));
						platforms.remove(c);
					}
					return;
				}
			}
			if(elevation < -300)
				lose = true;
			if(elevation+height > -prevPlatY+1200 && !tracking) {
				win = true;		
				levelCompleted = true;
			}
			else if(elevation+height > -prevPlatY+1200)
				finalWin = true;
		}
		catch (NullPointerException e) {}
	}

	private boolean checkPath(Platform plat, int mouseX, double elevation, int prevX, double prevY)
	{
		Double line = new Double(mouseX,elevation,prevX,prevY);
		return line.intersects(plat.rect);
	}

	public void moveScreen()
	{
		if(tracking)
		{	
			if(700-elevation < 250)	
			{
				int diff=250-(700-elevation);
				height+=diff;
				elevation-=diff;
				if(elevation+height > -(prevPlatY+1000))
					addPlat();
			}
		}
		else
		{
			height+=difficulty+(level-1);
			elevation-=difficulty+(level-1);
		}
	}
	
	public void getScore(Graphics g)
	{
		try
		{
			if(elevation+height > score)
				score = elevation+height;
			g.setFont(new Font("Arial",Font.PLAIN,36));
			g.setColor(Color.black);
			g.drawString(("Score: "+format.format(score)),675,100);
			if(!tracking) {
				g.drawString("Difficulty: "+getDifficulty(),100,100);
				g.drawString("Level: "+level,100,140);
			}
		}
		catch(NullPointerException e) {}
	}
	
	public void createPlatforms()
	{
		while(count < 140)
		{
			platformX = generate.nextInt(880)+10;
			platformY = generate.nextInt(500)-(count-1)*200;
			if(platformY - prevPlatY < -300)
				platformY = prevPlatY - 150;
			int select;
			if(fallCount>1)
				select = generate.nextInt(9)+1;
			else
				select = generate.nextInt(10);
			if(select == 0)
				platforms.add(new FallPlatform(platformX,platformY));
			else if(select == 1)
				platforms.add(new BreakPlatform(platformX,platformY));
			else if(select == 2)
				platforms.add(new MovePlatform(platformX,platformY));
			else
				platforms.add(new Platform(platformX,platformY));
			if(platforms.get(platforms.size()-1) instanceof FallPlatform) 
				fallCount++;
			else
				fallCount = 0;
			prevPlatY = platformY;
			count++;
		}
		top = prevPlatY+1000;
	}

	
	public void keyPressed(KeyEvent e)
	{
		int keyCode = e.getKeyCode();		
		if(keyCode == KeyEvent.VK_M)
			if(gamePlay)
			{
				gamePlay = false;
				start.show();
			}
			else
			{
				gamePlay = true;
				start.show();
				repaint();
			}
		else if(keyCode == KeyEvent.VK_SPACE)
			if(gamePlay)
			{
				gamePlay = false;
				diff.show();
			}
			else
			{
				gamePlay = true;
				diff.show();
				repaint();
			}
	}
	
	public String getDifficulty()
	{
		if(difficulty == 5)
			return "I";
		else if(difficulty == 7)
			return "II";
		else if(difficulty == 9)
			return "III";
		else if(difficulty == 11)
			return "IV";
		else
			return "Invalid";
	}
	
	public void resetPlatforms()
	{
		if(removedPlatforms.size() > 0) {
			for(int c = 0; c < removedPlatforms.size(); c++)
				platforms.add(removedPlatforms.get(c));
			for(int c = removedPlatforms.size()-1; c >= 0; c--)
				removedPlatforms.remove(c);
		}
		if(reform)
		{	
			for(int c = platforms.size()-1; c >= 0; c--)
				platforms.remove(c);
			count = 0;
			createPlatforms();
			reform = false;
		}
		for(int c = 0; c < platforms.size(); c++)
			if(platforms.get(c) instanceof FallPlatform)
				platforms.get(c).reset();
	}
	
	public void menuInit()
	{
		start = new StartMenu();
		diff = new DifficultyMenu();
	}
	
	public void addPlat()
	{
		for(int c = 0; c < 5; c++) {
			platformX = generate.nextInt(880)+10;
			platformY = generate.nextInt(500)-(count-1)*200;
			if(platformY - prevPlatY < -300)
				platformY = prevPlatY - 150;
			int select;
			if(fallCount>1)
				select = generate.nextInt(9)+1;
			else
				select = generate.nextInt(10);
			if(select == 0)
				platforms.add(new FallPlatform(platformX,platformY));
			else if(select == 1)
				platforms.add(new BreakPlatform(platformX,platformY));
			else if(select == 2)
				platforms.add(new MovePlatform(platformX,platformY));
			else
				platforms.add(new Platform(platformX,platformY));
			if(platforms.get(platforms.size()-1) instanceof FallPlatform) 
				fallCount++;
			else
				fallCount = 0;
			prevPlatY = platformY;
			count++;
			platforms.remove(0);
		}
		top = prevPlatY+1200;
	}
	
	public void mouseEntered(MouseEvent e)  {}
	public void mouseExited(MouseEvent e)	{}
	public void mouseReleased(MouseEvent e) {}
	public void mousePressed(MouseEvent e)  {}
	public void mouseMoved(MouseEvent e)	{ prevX = mouseX; mouseX = e.getX(); }	
	public void mouseClicked(MouseEvent e)  { prevX = mouseX; mouseX = e.getX(); numClicks++; }
	public void mouseDragged(MouseEvent e)  { prevX = mouseX; mouseX = e.getX(); numClicks++; }
	public void keyReleased(KeyEvent arg0) 	{}
	public void keyTyped(KeyEvent arg0) 	{}
}