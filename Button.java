import java.awt.Color;
import java.awt.Graphics;

public abstract class Button {

	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected String string;
	protected Color color;
	protected final static Color bgColor = new Color(200,120,0);
	protected final static Color defaultColor = new Color(140,50,0);
	protected final static Color highlightColor = new Color(160,100,0);
	protected boolean hover;
	
	public static MainMenu menu;

	public Button(int x,int y,int w,int h,String str,Color c) {
		this.x = x;
		this.y = y;
		width = w;
		height = h;
		string = str;
		color = c;
	}
	public Button(int x,int y,int w,int h) {
		this(x,y,w,h,"",bgColor);
	}
	
	public boolean check(int x,int y) {
		return x >= this.x && x <= this.x+width
				&& y >= this.y && y <= this.y+height;
	}
	
	public abstract void click();

	public void hover() {
		hover = true;
	}
	public void stopHover() {
		hover = false;
	}

	public void draw(Graphics g) {
		g.fillRect(x,y,width,height);
	}
	
	public String toString() {
		return "Button: "
				+((string.length()>0)?"\""+string+"\"":"")
				+"{("+x+"-"+(x+width)+"), ("+y+"-"+(y+height)+")}";
	}

}
