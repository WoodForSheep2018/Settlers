import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class MenuButton {
	private int xMin = 300;
	private int xMax = 900;
	private int yMin = 640;
	private int yMax = 800;

	public int getXMin() {
		return xMin;
	}

	public int getXMax() {
		return xMax;
	}

	public int getYMin() {
		return yMin;
	}

	public int getYMax() {
		return yMax;
	}

	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(xMin, 640, 600, 160);

		g.setColor(Color.BLACK);
		g.drawRect(xMin, 640, 150, 120);
		g.drawRect(450, 640, 150, 120);
		g.drawRect(600, 640, 150, 120);
		g.drawRect(750, 640, 150, 120);
		g.drawRect(xMin, 760, 600, 39);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
		g.drawString("Play development card", xMin, 720);
		g.drawString("Build road", 500, 720);//first click is startLoc, second is endLoc
		g.drawString("Build settlement", 630, 720);
		g.drawString("Build city", 790, 720);
		g.drawString("End turn", 550, 780);

	}
}
