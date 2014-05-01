package game;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;

class Platform
{
	int tlX,tlY;
	Rectangle rect;

	public Platform(int tlX, int tlY)
	{
		this.tlX = tlX;
		this.tlY = tlY;
		rect = new Rectangle(0,0,1,1);
	}

	public void drawPlatform(Graphics g, int height)
	{
		g.setColor(Color.yellow);
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
	
	public void setRect(int tlX,int tlY,int height)	{ rect = new Rectangle(tlX-21,700-tlY-height,142,32); }
	public String toString() { return ("["+tlX+", "+tlY+"]"); }
	public void move() {}
	public void reset() {}
}