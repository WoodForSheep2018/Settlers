import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class ProfileOption extends Button {

	private PlayerOptions po;

	public static int option = -1;
	private Image img;
	private final Image[] profiles = new Image[8];

	private Color color;

	public ProfileOption(int x, int y, int w, int h) {
		super(x, y, w, h);
		if(option >= 7) option = 0;
		else option++;
		img = profiles[option];

		color = bgColor;
	}

	public void setPlayer(PlayerOptions player) {
		po = player;
	}

	public void click() {
		po.changeImage(img);
	}

	public void setColor(Color clr) {
		color = clr;
	}

	public PlayerOptions getPO() {
		return po;
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
		//g.drawImage(img,x,y,null);
	}

}
