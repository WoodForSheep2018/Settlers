import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DiceButton extends JPanel{
	int xMin=300;
	int xMax=400;
	int yMin=0;
	int yMax=100;
	int roll = 0;
	
	public DiceButton() {
	}
	
	public int roll() {
		int one = (int) (Math.random()*6) + 1;
		int two = (int) (Math.random()*6) + 1;
		roll = one+two;
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
		g.fillRect(xMin, yMin, 100, 100);
		g.setFont(new Font("TimesRoman", Font.ITALIC, 16));
		g.setColor(Color.BLACK);
		g.drawString("Click to roll", 300, 15);
		g.setFont(new Font("TimesRoman", Font.BOLD, 30));
		if(roll>0)
			g.drawString(""+roll, 340, 60);
	}
}
