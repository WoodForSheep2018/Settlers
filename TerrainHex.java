import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class TerrainHex extends Hex {
	public enum Resource{Wood, Brick, Sheep, Wheat, Rock, Desert}
	private Resource type;
	private int number;

	public TerrainHex(Resource r, int x, int y, int w, int n) {
		super(x,y,w);
		type = r;
		number = n;
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
	}
	
	public int getNumber() {
		return number;
	}

}