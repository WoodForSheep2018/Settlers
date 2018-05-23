import java.awt.Color;
import java.awt.Graphics;

public class ColorOption extends Button {

	private PlayerOptions po;

	public static int option = -1;
	private Color color;
	private final Color[] colors = {
			new Color(190,20,10), new Color(20,20,170), new Color(20,180,10), new Color(180,180,10),
			new Color(220,130,10), new Color(140,10,130), new Color(120,100,100), new Color(200,200,200)
	};

	public ColorOption(int x, int y, int w, int h) {
		super(x, y, w, h);
		if(option >= 7) option = 0;
		else option++;
		color = colors[option];
	}

	public void setPlayer(PlayerOptions player) {
		po = player;
	}
	public PlayerOptions getPO() {
		return po;
	}

	public void click() {
		po.changeColor(color);
	}

	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(x,y,width,height);

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
	}

}
