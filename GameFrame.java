import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.awt.geom.Line2D.Double;

public class GameFrame
{
    public static void main(String[] args)
    {
        Frame frame = new Mechanics();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
            	System.exit(0);
            }
        });
    }
}

@SuppressWarnings("serial")
class Mechanics extends Frame implements MouseListener, MouseMotionListener
{
	protected int mouseX, mouseY;
	protected int appletWidth,appletHeight;
	protected int height,elevation,score;
	protected int platformX,platformY,prevX,prevY,prevPlatY;
	protected int numClicks,level;
	protected final int difficulty,mod;
	protected static double velocity;
	protected boolean gravity,above,lose;
	protected Image virtualMem;
	protected Graphics gBuffer;
	protected static ArrayList<Platform> platforms;
	protected Random generate;
	protected Font font;
	protected DecimalFormat format;

	public Mechanics()
	{
		super("To the Clouds");
		setSize(1010,900);
		setVisible(true);
		addMouseListener(this);
		addMouseMotionListener(this);
		GameGraphics.init();
		velocity = 23;
		mouseX = 0;
		mouseY = 725;
		prevX = 0;
		prevY = 0;
		height = 0;
		elevation = 0;
		difficulty = 7;
		mod = 2;
		numClicks = 0;
		gravity = true;
		above = false;
		lose = false;
		generate = new Random();
		format = new DecimalFormat("00000");


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

		platforms = new ArrayList<Platform>();
		createPlatforms();
	}

	public void paint(Graphics g)
	{
		if(numClicks == 0) { GameGraphics.title(gBuffer,height);   lose = false; }
		else if(numClicks >= 1 && !lose)
		{	
			GameGraphics.background(gBuffer,height);
			GameGraphics.sprite(gBuffer,mouseX,elevation);
			drawPlatforms(gBuffer,height);
			checkBounce(mouseX,elevation,prevX,prevY);
			setVelocity();
			moveScreen();
			getScore(gBuffer);
			delay(1000/80);
		}
		else if(numClicks >= 1 && lose) 
		{ 
			GameGraphics.lose(gBuffer,height); 
			elevation = 0;
			height = 0;
			velocity = 25;
			numClicks = -1;
			score = 0;
			createPlatforms();
		}
		g.drawImage(virtualMem,0,0,null);	
		repaint();
	}

	public void update(Graphics g)	{ paint(g); }
	
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

		if(velocity > -24.5)
			velocity -= .5;
		
		elevation += velocity;
	}

	public void drawPlatforms(Graphics g, int height)
	{
		try
		{
			for(int c = 0; c < platforms.size(); c++)
				platforms.get(c).drawPlatform(g,height);
		}
		catch (NullPointerException e) {}
	}

	public void checkBounce(int mouseX,double elevation,int prevX,double prevY)
	{
		try
		{
			for(int c = 0; c < platforms.size(); c++)
			{
				if(platforms.get(c) instanceof MovePlatform || platforms.get(c) instanceof FallPlatform)	
					platforms.get(c).move();
				if(checkPath(platforms.get(c),mouseX,elevation,prevX,prevY))
				{
					elevation = 700-platforms.get(c).tlY-48;
					velocity = 23.0;
					if(platforms.get(c) instanceof BreakPlatform)
						platforms.remove(c);
					return;
				}
			}
			if(elevation < -250)
				lose = true;
		}
		catch (NullPointerException e) {}
	}

	public boolean checkPath(Platform plat, int mouseX, double elevation, int prevX, double prevY)
	{
		Double line = new Double(mouseX,elevation,prevX,prevY);
		return line.intersects(plat.rect);
	}

	public void moveScreen()
	{
		height+=difficulty;
		elevation-=difficulty;
	}
	
	public void getScore(Graphics g)
	{
		try
		{
			if(elevation+height > score)
				score = elevation+height;
			g.setFont(new Font("Arial",Font.PLAIN,36));
			g.setColor(Color.red);
			g.drawString(("Score: "+format.format(score)),675,100);
//			g.drawString(String.valueOf(elevation),750,140);
		}
		catch(NullPointerException e) {}
	}
	
	public void createPlatforms()
	{
		if(platforms.size() > 0)
			for(int c = platforms.size()-1; c >= 0; c--)
				platforms.remove(c);
		int platformX = 0;
		int platformY = 0;
		for(int c = 0; c < 140; c++)
		{
			platformX = generate.nextInt(880)+10;
			platformY = generate.nextInt(500)-c*200+200;
			if(platformY - prevPlatY < -300)
				platformY = prevPlatY - 150;
			int select = generate.nextInt(10);
			if(select == 0)
				platforms.add(new MovePlatform(platformX,platformY));
			else if(select == 1)
				platforms.add(new BreakPlatform(platformX,platformY));
			else if(select == 2)
				platforms.add(new FallPlatform(platformX,platformY));
			else
				platforms.add(new Platform(platformX,platformY));
			prevPlatY = platformY;
		}
	}

	public void mouseEntered(MouseEvent e)  {}
	public void mouseExited(MouseEvent e)	{}
	public void mouseReleased(MouseEvent e) {}
	public void mousePressed(MouseEvent e)  {}
	public void mouseMoved(MouseEvent e)	{ prevX = mouseX; mouseX = e.getX(); }	
	public void mouseClicked(MouseEvent e)  { prevX = mouseX; mouseX = e.getX(); numClicks++; }
	public void mouseDragged(MouseEvent e)  { prevX = mouseX; mouseX = e.getX(); numClicks++; }
}