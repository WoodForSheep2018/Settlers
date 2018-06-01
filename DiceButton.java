import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DiceButton extends JPanel{
	private int xMin;
	private int xMax;
	private int yMin;
	private int yMax;
	private int roll;
	
	public DiceButton(Board b) {
		xMin = b.getBoardXLoc();
		xMax = b.getBoardXLoc() + b.getBoardWidth()/6;
		yMin = 0;
		yMax = b.getBoardHeight()/8;
	}
	
	public int roll() {
		int one = (int)(Math.random()*6) + 1;
		int two = (int)(Math.random()*6) + 1;
		roll = one + two;
		return roll;
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

	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(xMin, yMin, xMax - xMin, yMax - yMin);
		g.setColor(Color.BLACK);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		int textWidth = g.getFontMetrics().stringWidth("Click to roll");
		int textHeight = g.getFontMetrics().getAscent();
		g.drawString("Click to roll", (xMin + xMax)/2 - textWidth/2, yMin + textHeight);
		g.setFont(new Font("TimesRoman", Font.BOLD, 30));
		if(roll > 0) {
			int numWidth = g.getFontMetrics().stringWidth("" + roll);
			int numHeight = g.getFontMetrics().getAscent();
			g.drawString("" + roll, (xMin + xMax)/2 - numWidth/2, yMax - numHeight/2);
		}
	}
	
}