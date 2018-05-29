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
		g.fillRect(xMin, 640, 150, 120);
		g.fillRect(450, 640, 150, 120);
		g.fillRect(600, 640, 150, 120);
		g.fillRect(750, 640, 150, 120);
		g.fillRect(xMin, 760, 600, 40);

		g.setColor(Color.BLACK);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
		g.drawString("Play development card", xMin, 720);
		g.drawString("Build road", 470, 720);
		g.drawString("Build settlement", 600, 720);
		g.drawString("Build city", 750, 720);
		g.drawString("End turn", 550, 770);

	}
}
