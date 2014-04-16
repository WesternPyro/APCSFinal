package game;
import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

public class Cloud
{
	private int tlX,tlY;
	private Image cloud;

	public Cloud(int tlX, int tlY)
	{
		this.tlX = tlX;
		this.tlY = tlY;
		try { cloud = ImageIO.read(new File("cloud.png")); }
		catch (IOException e) {}
	}

	public void drawCloud(Graphics g, int height)	{ g.drawImage(cloud,tlX,tlY+height,null); }
}