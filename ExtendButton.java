import java.awt.Color;
import java.awt.Graphics;

public class ExtendButton extends Button {

	private int[] xPoints = {0,(int) (15*Math.sqrt(3)/2),0};
	private int[] yPoints = {0,8,15};
	private int[] xs;
	private int[] ys;
	
	public ExtendButton(int x, int y, int w, int h) {
		super(x, y, w, h);
		xs = new int[3];
		ys = new int[3];
		for(int i = 0;i < 3;i++) {
			this.xs[i] = xPoints[i] + x;
			this.ys[i] = yPoints[i] + y;
		}
	}

	public void click() {
		menu.expandSetUpList();
		for(int i = 0;i < 3;i++) {
			xPoints[i] = ((int) (15*Math.sqrt(3)/2) - xPoints[i]);
			xs[i] = x + xPoints[i];
		}
	}
	
	public void draw(Graphics g) {
		if(hover) g.setColor(highlightColor);
		else g.setColor(defaultColor);
		g.fillPolygon(xs,ys,3);
	}

}
