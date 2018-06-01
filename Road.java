import java.awt.Graphics;

public class Road {
	private int startX;
	private int startY;
	private int finalX;
	private int finalY;
	private Player owner;
	private int[] xPoints;
	private int[] yPoints;

	public Road(int x1, int y1, int x2, int y2, Player p) {
		startX = x1;
		startY = y1;
		finalX = x2;
		finalY = y2;
		owner = p;

		if(Math.abs(startX - finalX) < 2) {
			xPoints = new int[] {startX - 3, startX + 3, finalX + 3, finalX - 3};
			yPoints = new int[] {startY, startY, finalY, finalY};
		}
		else {
			xPoints = new int[] {startX, finalX, finalX, startX};
			yPoints = new int[] {startY + 3, finalY + 3, finalY - 3, startY - 3};
		}
		
	}
	
	public boolean isAtLocation(int x, int y) {
		return ((startX==x && startY==y) || (finalX==x && finalY==y));
	}

	public int getStartX() {
		return startX;
	}
	public int getStartY() {
		return startY;
	}
	public int getEndX() {
		return finalX;
	}
	public int getEndY() {
		return finalY;
	}
	
	public void draw(Graphics g) {
		g.setColor(owner.getColor());
		g.fillPolygon(xPoints, yPoints, 4);
	}
}