import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class MenuButton {
	private int xMin;
	private int xMax;
	private int yMin;
	private int yMax;
	private int width;
	private int height;
	private int boxWidth;
	private int boxHeight;
	private int xSpacing;
	private int ySpacing;
	
	private int devCardX;
	private int roadX;
	private int settlementX;
	private int cityX;
	private int buyDevCardX;
	private int endTurnX;
	
	public MenuButton(Board b, int ph) {
		xMin = b.getBoardXLoc();
		xMax = b.getBoardXLoc() + b.getBoardWidth();
		yMin = b.getBoardHeight();
		yMax = ph;
		width = xMax - xMin;
		height = yMax - yMin;
		boxWidth = width/6;
		boxHeight = height/6;
		xSpacing = (width - 4*boxWidth)/5;
		ySpacing = (height - 2*boxHeight)/3;
		
		devCardX = xMin + xSpacing;
		roadX = devCardX + boxWidth + xSpacing;
		settlementX = roadX + boxWidth + xSpacing;
		cityX = settlementX + boxWidth + xSpacing;
		buyDevCardX = roadX;
		endTurnX = settlementX;
	}

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
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public boolean devCardBox(int x, int y) {
		if(x > devCardX && x < devCardX + boxWidth) {
			if(y > yMin + ySpacing && y < yMin + ySpacing + boxHeight) {
				return true;
			}
		}
		return false;
	}
	
	public boolean roadBox(int x, int y) {
		if(x > roadX && x < roadX + boxWidth) {
			if(y > yMin + ySpacing && y < yMin + ySpacing + boxHeight) {
				return true;
			}
		}
		return false;
	}
	
	public boolean settlementBox(int x, int y) {
		if(x > settlementX && x < settlementX + boxWidth) {
			if(y > yMin + ySpacing && y < yMin + ySpacing + boxHeight) {
				return true;
			}
		}
		return false;
	}

	public boolean cityBox(int x, int y) {
		if(x > cityX && x < cityX + boxWidth) {
			if(y > yMin + ySpacing && y < yMin + ySpacing + boxHeight) {
				return true;
			}
		}
		return false;
	}

	public boolean endTurnBox(int x, int y) {
		if(x > endTurnX && x < endTurnX + boxWidth) {
			if(y > yMin + 2*ySpacing + boxHeight && y < yMin + 2*ySpacing + 2*boxHeight) {
				return true;
			}
		}
		return false;
	}
	
	public boolean buyDevCardX(int x, int y) {
		if(x > buyDevCardX && x < buyDevCardX + boxWidth) {
			if(y > yMin + 2*ySpacing + boxHeight && y < yMin + 2*ySpacing + 2*boxHeight) {
				return true;
			}
		}
		return false;
	}

	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(xMin, yMin, width, height);
		
		g.setColor(Color.MAGENTA);
		g.fillRect(devCardX, yMin + ySpacing, boxWidth, boxHeight);
		g.fillRect(roadX, yMin + ySpacing, boxWidth, boxHeight);
		g.fillRect(settlementX, yMin + ySpacing, boxWidth, boxHeight);
		g.fillRect(cityX, yMin + ySpacing, boxWidth, boxHeight);
		
		g.setColor(Color.ORANGE);
		g.fillRect(endTurnX, yMin + 2*ySpacing + boxHeight, boxWidth, boxHeight);
		g.fillRect(buyDevCardX, yMin + 2*ySpacing + boxHeight, boxWidth, boxHeight);

		g.setColor(Color.BLACK);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 14));
		int textHeight = g.getFontMetrics().getAscent();
		g.drawString("Play Card", devCardX, yMin + ySpacing + textHeight);
		g.drawString("Build Road", roadX, yMin + ySpacing + textHeight);
		g.drawString("Build Settlement", settlementX, yMin + ySpacing + textHeight);
		g.drawString("Build City", cityX, yMin + ySpacing + textHeight);
		g.drawString("End Turn", endTurnX, yMin + 2*ySpacing + boxHeight + textHeight);
		g.drawString("Buy Card", buyDevCardX, yMin + 2*ySpacing + boxHeight + textHeight);
	}
}
