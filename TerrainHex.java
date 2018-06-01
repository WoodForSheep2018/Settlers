import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.Observer;

import javax.imageio.ImageIO;

public class TerrainHex extends Hex {
	private Type t;
	public enum Resource{Wood, Brick, Sheep, Wheat, Rock, Desert}
	private Resource type;
	private int number;

	public TerrainHex(Resource r, int x, int y, int w, int n) {
		super(x,y,w);
		type = r;
		number = n;
		t = Type.Terrain;
	}

	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawPolygon(getXPoints(), getYPoints(), 6);
		if(type == Resource.Wood) {
			g.setColor(new Color(0,100,0));
		}
		else if(type == Resource.Brick) {
			g.setColor(new Color(204,0,0));
		}
		else if(type == Resource.Sheep) {
			g.setColor(new Color(0,255,0));
		}
		else if(type == Resource.Wheat) {
			g.setColor(new Color(204,204,0));
		}
		else if(type == Resource.Rock) {
			g.setColor(new Color(96,96,96));
		}
		else if(type == Resource.Desert) {
			g.setColor(new Color(255,255,168));
		}
		g.fillPolygon(getXPoints(), getYPoints(), 6);

		if(isRobber) {
			Image i = null;
			try {
				i = ImageIO.read(getClass().getResource("theHexV3.GIF"));
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(i, getXLoc() - getWidth()/4, getYLoc() - getHeight()/4, getWidth()/2, getHeight()/2, null);
		}
		else if(type != Resource.Desert) {
			g.setColor(Color.WHITE);
			g.fillOval(getXLoc() - getWidth()/6, getYLoc() - getWidth()/6 , (2*getWidth())/6, (2*getHeight()/6));
			g.setColor(Color.BLACK);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
			String numString = Integer.toString(number);
			int textWidth = g.getFontMetrics().stringWidth(numString);
			int textAscent = g.getFontMetrics().getAscent();
			g.drawString(numString, getXLoc() - textWidth/2, getYLoc() + textAscent/2);
		}
		
	}

	public int getNumber() {
		return number;
	}
	
	public Type getType() {
		return t;
	}
	
	public TerrainHex.Resource getResource(){
		if(!isRobber)
			return type;
		return null;
	}
}
