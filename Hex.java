import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

public abstract class Hex {
	public enum Type {Terrain, Ocean}
	private int xLoc;
	private int yLoc;
	private int[] xPoints;
	private int[] yPoints;
	private ArrayList<Location> vertices = new ArrayList<Location>();
	private int width;
	private int height;
	public boolean isRobber;

	public Hex(int x, int y, int w) {
		xLoc = x;
		yLoc = y;
		width = w;
		height = (int)(2*(width/Math.sqrt(3)));
		xPoints = new int[] {xLoc, xLoc + width/2, xLoc + width/2, xLoc, xLoc - width/2, xLoc - width/2};
		yPoints = new int[] {yLoc + height/2, yLoc + height/4, yLoc - height/4, yLoc - height/2, yLoc - height/4, yLoc + height/4};
		
		for(int i = 0; i < xPoints.length; i++) {
			vertices.add(new Location(xPoints[i], yPoints[i], width));
		}
		isRobber = false;
	}

	public abstract void draw(Graphics g);
	
	public int[] getXPoints() {
		return xPoints;
	}
	
	public int[] getYPoints() {
		return yPoints;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getXLoc() {
		return xLoc;
	}
	
	public int getYLoc() {
		return yLoc;
	}
	
	public ArrayList<Location> getVertices() {
		return vertices;
	}
	
	public abstract Type getType();

	public void makeRobber(boolean b) {
		isRobber = b;
	}
	
	public TerrainHex.Resource getResource(){
		return null;
	}
}