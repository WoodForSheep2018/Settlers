import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Slider extends Button {

	ArrayList<SliderStep> steps = new ArrayList<SliderStep>();
	private int sliderLoc;

	private boolean dragging;
	private int dragX;

	public Slider(int x, int y, int w, int h, int s) {
		super(x, y, w, h);
		int length = w/(s-1);
		steps.add(new SliderStep(x,y,menu.LENGTH,height,0,x+length/2));
		for(int n = 1;n < s-1;n++) {
			int d = x+length*n;
			steps.add(new SliderStep(d,y,menu.LENGTH,height,d-length/2,length));
		}
		steps.add(new SliderStep(x+length*(s-1),y,menu.LENGTH,height,x+length*(s-1)-length/2,1000));
	}

	public boolean check(int x,int y) {
		return steps.get(sliderLoc).check(x, y);
	}
	public void release(int x) {
		for(SliderStep ss:steps) {
			if(ss.check(x)) {
				sliderLoc = steps.lastIndexOf(ss);
			}
		}
		menu.setPlayerSlider(sliderLoc);
	}

	public void click() {
		System.out.println("Nada nada");
	}

	public void drag(int x) {
		for(SliderStep ss:steps) {
			if(ss.check(x)) {
				ss.hover();
			} else {
				ss.stopHover();
			}
		}
		dragging = true;
		dragX = (x >= this.x && x <= this.x+width) ? x - this.x :
			(x < this.x) ? 0 : width;
	}
	public void drag() { dragging = false; }

	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(x-6,y+height/2,width+11,2);
		for(SliderStep ss:steps) {
			ss.draw(g);
		}
		if(hover) g.setColor(highlightColor);
		else g.setColor(defaultColor);

		int[] xPoints = {(x-7),(x+6),(x+6),x,(x-7)};
		if(dragging) {
			for(int i = 0;i < xPoints.length;i++) {
				xPoints[i] += dragX;
			}
		} else {
			for(int i = 0;i < xPoints.length;i++) {
				xPoints[i] += width/(steps.size()-1)*sliderLoc;
			}
		}
		int[] yPoints = {y+2,y+2,(y+12),(int)(y+12+7),(y+12)};

		g.fillPolygon(xPoints, yPoints, 5);
	}

}
