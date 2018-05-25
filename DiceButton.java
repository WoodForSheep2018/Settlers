import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DiceButton extends JPanel{
	private Dice dice;
	int xMin=300;
	int xMax=350;
	int yMin=640;
	int yMax=690;
	int roll = 0;
	
	public DiceButton(Dice d) {
		dice=d;
	}
	public void click() {
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {

				int x = e.getX();
				int y = e.getY();
				
				if(x>=xMin && x<=xMax && y>=yMin && y<=yMax) {
					roll = dice.roll();
					repaint();
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void drawPtTwo(Graphics g) {
		g.drawString(""+roll, 300, 640);
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(300, 640, 50, 50);
		g.setFont(new Font("TimesRoman" , Font.BOLD, 30));
		String str = "";
		str+=roll;
		int textWidth = g.getFontMetrics().stringWidth(str);
		int textAscent = g.getFontMetrics().getAscent();
		
		if(roll>0) {
			g.setColor(Color.WHITE);
			g.drawString(""+roll, 325, 665);
		}
	}
}
