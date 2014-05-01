package game;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class MovePlatform extends Platform 
{
	int tlX,tlY,mod;
	Rectangle rect;
	
	public MovePlatform(int tlX, int tlY)
	{
		super(tlX,tlY);
		this.tlX = tlX;
		this.tlY = tlY;
		rect = new Rectangle(0,0,1,1);
		mod = 2;
	}
	
	public void drawPlatform(Graphics g, int height)
	{
		g.setColor(Color.green);
		g.fillArc(tlX,tlY+height,6,25,0,360);
		g.fillRect(tlX+3,tlY+height,100,25);
		g.fillArc(tlX+100,tlY+height,6,25,0,360);
		g.setColor(Color.black);
		g.drawArc(tlX,tlY+height,6,25,90,180);
		g.drawArc(tlX+100,tlY+height,6,25,270,180);
		g.drawLine(tlX+3,tlY+height,tlX+103,tlY+height);
		g.drawLine(tlX+3,tlY+height+25,tlX+103,tlY+height+25);
		setRect(this.tlX,this.tlY,height);
	}
	
	public void move()
	{
		if(tlX > 890 || tlX < 10)
			mod *= -1;
		this.tlX += mod;
	}
}
