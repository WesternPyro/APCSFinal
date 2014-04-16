import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;

public class FallPlatform extends Platform 
{
	int tlX,tlY,mod;
	Rectangle rect;
	
	public FallPlatform(int tlX, int tlY) 
	{
		super(tlX, tlY);
		this.tlX = tlX;
		this.tlY = tlY;
		rect = new Rectangle (0,0,1,1);
	}
	
	public void drawPlatform(Graphics g, int height)
	{
		g.setColor(new Color(250,124,12));
		g.fillArc(tlX,tlY+75+height,6,25,0,360);
		g.fillRect(tlX+3,tlY+75+height,100,25);
		g.fillArc(tlX+100,tlY+75+height,6,25,0,360);
		super.setRect(this.tlX,this.tlY,height);	
	}
	
	public void move()	{ this.tlY += 2; }
}
