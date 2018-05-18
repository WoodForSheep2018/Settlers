import java.awt.Color;
import java.awt.Graphics;

public class SliderStep extends Button {

	private int release1,release2;

	public SliderStep(int x, int y, int w, int h, int rx, int rw) {
		super(x-w/2, y, w, h);
		release1 = rx;
		release2 = rw+rx;
	}

	public void click() { /*no implementation*/ }
	
	public void draw(Graphics g) {
		if(hover) g.setColor(highlightColor);
		else g.setColor(Color.BLACK);
		g.fillRect(x+width/2,y+height/4,2,height/2);
	}

	public boolean check(int x) {
		return x >= release1 && x <= release2;
	}

}
