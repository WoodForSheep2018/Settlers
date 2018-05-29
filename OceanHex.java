import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class OceanHex extends Hex {
	
	public enum Port{Wood, Brick, Sheep, Wheat, Rock, ThreeOne, Ocean}
	private Port type;
	
	public OceanHex(Port p, int x, int y, int w) {
		super(x,y,w);
		type = p;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawPolygon(getXPoints(), getYPoints(), 6);
		g.setColor(new Color(113,203,237));
		g.fillPolygon(getXPoints(), getYPoints(), 6);
		
		if(type == Port.Wood) {
			g.setColor(Color.WHITE);
			g.drawOval(getXLoc() - getWidth()/4, getYLoc() - getHeight()/4, getWidth()/2, getHeight()/2);
			g.setColor(new Color(0,100,0));
			g.fillOval(getXLoc() - getWidth()/4, getYLoc() - getHeight()/4, getWidth()/2, getHeight()/2);
			g.setColor(Color.WHITE);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
			String str = "2:1";
			int textWidth = g.getFontMetrics().stringWidth(str);
			int textAscent = g.getFontMetrics().getAscent();
			g.drawString(str, getXLoc() - textWidth/2, getYLoc() + textAscent/2);
		}
		else if(type == Port.Brick) {
			g.setColor(Color.WHITE);
			g.drawOval(getXLoc() - getWidth()/4, getYLoc() - getHeight()/4, getWidth()/2, getHeight()/2);
			g.setColor(new Color(204,0,0));
			g.fillOval(getXLoc() - getWidth()/4, getYLoc() - getHeight()/4, getWidth()/2, getHeight()/2);
			g.setColor(Color.WHITE);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
			String str = "2:1";
			int textWidth = g.getFontMetrics().stringWidth(str);
			int textAscent = g.getFontMetrics().getAscent();
			g.drawString(str, getXLoc() - textWidth/2, getYLoc() + textAscent/2);
		}
		else if(type == Port.Sheep) {
			g.setColor(Color.WHITE);
			g.drawOval(getXLoc() - getWidth()/4, getYLoc() - getHeight()/4, getWidth()/2, getHeight()/2);
			g.setColor(new Color(0,255,0));
			g.fillOval(getXLoc() - getWidth()/4, getYLoc() - getHeight()/4, getWidth()/2, getHeight()/2);
			g.setColor(Color.BLACK);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
			String str = "2:1";
			int textWidth = g.getFontMetrics().stringWidth(str);
			int textAscent = g.getFontMetrics().getAscent();
			g.drawString(str, getXLoc() - textWidth/2, getYLoc() + textAscent/2);
		}
		else if(type == Port.Wheat) {
			g.setColor(Color.WHITE);
			g.drawOval(getXLoc() - getWidth()/4, getYLoc() - getHeight()/4, getWidth()/2, getHeight()/2);
			g.setColor(new Color(204,204,0));
			g.fillOval(getXLoc() - getWidth()/4, getYLoc() - getHeight()/4, getWidth()/2, getHeight()/2);
			g.setColor(Color.BLACK);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
			String str = "2:1";
			int textWidth = g.getFontMetrics().stringWidth(str);
			int textAscent = g.getFontMetrics().getAscent();
			g.drawString(str, getXLoc() - textWidth/2, getYLoc() + textAscent/2);
		}
		else if(type == Port.Rock) {
			g.setColor(Color.WHITE);
			g.drawOval(getXLoc() - getWidth()/4, getYLoc() - getHeight()/4, getWidth()/2, getHeight()/2);
			g.setColor(new Color(96,96,96));
			g.fillOval(getXLoc() - getWidth()/4, getYLoc() - getHeight()/4, getWidth()/2, getHeight()/2);
			g.setColor(Color.WHITE);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
			String str = "2:1";
			int textWidth = g.getFontMetrics().stringWidth(str);
			int textAscent = g.getFontMetrics().getAscent();
			g.drawString(str, getXLoc() - textWidth/2, getYLoc() + textAscent/2);
		}
		else if(type == Port.ThreeOne) {
			g.setColor(Color.WHITE);
			g.drawOval(getXLoc() - getWidth()/4, getYLoc() - getHeight()/4, getWidth()/2, getHeight()/2);
			g.fillOval(getXLoc() - getWidth()/4, getYLoc() - getHeight()/4, getWidth()/2, getHeight()/2);
			g.setColor(Color.BLACK);
			g.setColor(Color.BLACK);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 14));
			String str = "3:1";
			int textWidth = g.getFontMetrics().stringWidth(str);
			int textAscent = g.getFontMetrics().getAscent();
			g.drawString(str, getXLoc() - textWidth/2, getYLoc() + textAscent/2);
		}
	}

	public TerrainHex.Resource getResource() {
		return null;
	}

	public Type getType() {
		return null;
	}
	
}