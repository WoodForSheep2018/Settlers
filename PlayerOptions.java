import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

public class PlayerOptions extends Button {

	private ArrayList<Button> profiles = new ArrayList<Button>();
	private Image profile;

	private ArrayList<Button> colors = new ArrayList<Button>();
	private Color color;

	private StringEditor namer;
	private String name;
	
	public static int numPlayer = 0;

	public PlayerOptions(int x, int y, int w, int h) {
		super(x, y, w, h);
		int length = w/13;

		for(int r = 0;r < 2;r++) {
			for(int c = 0;c < 4;c++) {
				profiles.add(new ProfileOption(x+length*(3*c+1),y+length*(3*r+1),length*2,length*2));
				colors.add(new ColorOption(x+length*(3*c+1),y+length*(3*r+8),length*2,length*2));
			}
		}
		for(int i = 0;i < profiles.size();i++) {
			((ProfileOption) profiles.get(i)).setPlayer(this);
			((ColorOption) colors.get(i)).setPlayer(this);
		}
		
		numPlayer++;
		namer = new StringEditor(x+length,y+length*14,w-length*3,length*2,"Player "+numPlayer,Color.BLACK);
		name = "";
		
		namer.setPlayer(this);

		color = bgColor;
	}

	public void removeButtons() {
		Button.menu.removeButtons(profiles);
		Button.menu.removeButtons(colors);
		Button.menu.removeButton(namer);
	}
	public void addButtons() {
		Button.menu.addButtons(profiles);
		Button.menu.addButtons(colors);
		Button.menu.addButton(namer);
	}
	public void addProfiles() {
		Button.menu.addButtons(profiles);
	}
	public void addColors() {
		Button.menu.addButtons(colors);
	}
	public void addStringEditor() {
		Button.menu.addButton(namer);
	}

	public void click() {
		System.out.println("Player");
	}
	public void changeImage(Image img) {
		profile = img;
	}
	public void changeColor(Color clr) {
		System.out.println(clr);
		color = clr;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Image getProfile() {
		return profile;
	}
	public Color getColor() {
		return color;
	}
	public String getName() {
		return name;
	}

	public void draw(Graphics g) {
		Color clr = bgColor;
		if(hover) { clr = color; }

		for(Button b:profiles) {
			((ProfileOption) b).setColor(clr);
		}

		g.setColor(clr);
		g.fillRect(x,y,width,height);

		g.setColor(clr.darker());
		g.fillRect(x-1,y,1,height+1);
		g.fillRect(x,y+1,1,height);
		g.fillRect(x-1,y+height-1,width,1);
		g.fillRect(x-1,y+height,width+1,1);

		g.setColor(clr.brighter());
		g.fillRect(x,y-1,width,1);
		g.fillRect(x+1,y,width,1);
		g.fillRect(x+width-1,y-1,1,height);
		g.fillRect(x+width,y-1,1,height+1);
	}

}