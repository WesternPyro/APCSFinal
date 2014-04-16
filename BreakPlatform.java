import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class BreakPlatform extends Platform 
{
	int tlX,tlY;
	Rectangle rect;
	
	public BreakPlatform(int tlX, int tlY)
	{
		super(tlX,tlY);
		this.tlX = tlX;
		this.tlY = tlY;
		rect = new Rectangle(0,0,1,1);
	}
	
	public void drawPlatform(Graphics g, int height)
	{
		g.setColor(Color.blue);
		g.fillArc(tlX,tlY+75+height,6,25,0,360);
		g.fillRect(tlX+3,tlY+75+height,100,25);
		g.fillArc(tlX+100,tlY+75+height,6,25,0,360);
		super.setRect(this.tlX,this.tlY,height);		
	}
}
