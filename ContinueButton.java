import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class ContinueButton extends Button {

	private int[] xPoints;
	private int[] yPoints;

	public ContinueButton(int x, int y, int l) {
		super(x, y, l, l, "Start Game", bgColor);
		int base = l*2/3;
		int s = (l-(int)(base*Math.sqrt(3)/2))/2;
		int t = l/6;
		int[] xPoints = {x+s,x+s+(int)(base*Math.sqrt(3)/2),x+s};
		int[] yPoints = {y+t,y+t+base/2,y+t+base};
		this.xPoints = xPoints;
		this.yPoints = yPoints;
	}

	public void click() {
		menu.startGame();
	}
	
	public void draw(Graphics g) {
		if(hover) g.setColor(color.brighter());
		else g.setColor(color.darker());
		g.fillRect(x-1,y,1,height+1);
		g.fillRect(x,y+1,1,height);
		g.fillRect(x-1,y+height-1,width,1);
		g.fillRect(x-1,y+height,width+1,1);

		if(hover) g.setColor(color.darker());
		else g.setColor(color.brighter());
		g.fillRect(x,y-1,width,1);
		g.fillRect(x+1,y,width,1);
		g.fillRect(x+width-1,y-1,1,height);
		g.fillRect(x+width,y-1,1,height+1);

		g.setColor(defaultColor);
		g.fillPolygon(xPoints,yPoints,3);

		g.setColor(Color.BLACK);
		g.setFont(new Font("TimesNewRoman",Font.PLAIN,30));
		g.drawString(string,(int)(x+width*(1+.125)),(int)(y+height*(1-.25)));
	}

}
