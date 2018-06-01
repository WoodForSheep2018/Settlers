import java.awt.Color;
import java.awt.Graphics;

public class Building {

	private int level;
	private Player owner;
	private int xLoc;
	private int yLoc;
	private int size;

	public Building(int x, int y, int hexWidth) {
		level = 0;
		xLoc = x;
		yLoc = y;
		size = hexWidth/6;
	}

	public void buildSettlement() {
		level = 1;
	}

	public void buildCity() {
		level = 2;
	}

	public boolean isASettlement() { 
		return level == 1; 
	}

	public boolean isACity() { 
		return level == 2; 
	}

	public Player getOwner() { 
		return owner; 
	}
	
	public boolean hasOwner() {
		return owner != null;
	}
	
	public void setOwner(Player p) {
		owner = p;
	}
	
	public int getLevel() {
		return level;
	}

	public void draw(Graphics g) {
		if(owner != null) {
			g.setColor(owner.getColor());
			if(level == 1) {
				g.fillRect(xLoc - size/2, yLoc - size/2, size, size);
				g.fillPolygon(new int[]{xLoc-size/2, xLoc, xLoc+size/2}, new int[]{yLoc-size/2, yLoc-size, yLoc-size/2}, 3);
			}
			if(level == 2) {
				g.fillRect(xLoc, yLoc, size, size);
				g.fillRect(xLoc - size, yLoc - size/2, size, (3*size)/2);
				g.fillPolygon(new int[]{xLoc-size, xLoc-size/2, xLoc}, new int[]{yLoc-size/2, yLoc-size, yLoc-size/2}, 3);
			}
		}
	}

}
