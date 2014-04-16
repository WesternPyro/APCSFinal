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
		setRect(this.tlX,this.tlY,height);
	}
	
	public void setRect(int tlX,int tlY,int height)	{ rect = new Rectangle(tlX,700-tlY-height,148,24); }
	public void move() {}
}