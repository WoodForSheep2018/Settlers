import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class SetUpOption extends Button {

	public enum Layout { Beginner, Random, Custom }
	private Layout layout;

	public SetUpOption(int x, int y, int w, int h, String str, Color c, Layout l) {
		super(x, y, w, h, str, c);
		layout = l;
	}

	public void click() {
		menu.selectSetUp(layout);
	}
	
	public void draw(Graphics g) {
		if(hover) g.setColor(new Color(160,100,0));
		else g.setColor(color);
		super.draw(g);
		g.setColor((color.getRed()+color.getBlue()+color.getGreen() > 255*3/2) ?
				Color.BLACK : Color.WHITE);
		g.setFont(new Font("TimesNewRoman",Font.PLAIN,25));
		g.drawString(string,x+5,y+height-8);
	}

}
