import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class MainMenu extends javax.swing.JPanel {

	public static SettlersOfCatanGame frame;

	private ArrayList<Button> buttons = new ArrayList<Button>();

	private ArrayList<SetUpOption> setup = new ArrayList<SetUpOption>();
	private boolean expanded;
	private SetUpOption.Layout layout;

	private Slider slider;
	private ArrayList<PlayerOptions> players = new ArrayList<PlayerOptions>();
	private ArrayList<PlayerOptions> playingPlayers = new ArrayList<PlayerOptions>();
	private Button entered;

	public final int LENGTH = 20;

	private boolean sliding;
	private int sliderLoc = 0;

	public MainMenu() {
		Button.menu = this;
		this.setPreferredSize(new Dimension(LENGTH*31,LENGTH*25));
		buttons.add(new ExtendButton(130,72,(int)(15*Math.sqrt(3)/2),15));
		this.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				for(Button b:buttons) {
					if(b.check(e.getX(),e.getY())) {
						b.click();
						System.out.println("You just clicked: "+b);
					}
				}
				repaint();
			}

			public void mouseEntered(MouseEvent e) {
				System.out.println("You just entered!! "+e);
			}

			public void mouseExited(MouseEvent e) {
				System.out.println("You just exited!! "+e);
			}

			public void mousePressed(MouseEvent e) {
				System.out.println("Pressed");
				if(slider.check(e.getX(),e.getY())) {
					sliding = true;
				}
			}

			public void mouseReleased(MouseEvent e) {
				if(sliding) {
					System.out.println("Released slider! "+e);
					sliding = false;
					slider.release(e.getX());
					slider.drag();
				}
			}

		});

		this.addMouseMotionListener(new MouseMotionListener() {

			public void mouseDragged(MouseEvent e) {
				if(sliding) {
					slider.drag(e.getX());
				}
				repaint();
			}

			public void mouseMoved(MouseEvent e) {
				showPlayerOptions();
				Button b = getCheckedButton(e);
				if(b!=null && b!=entered) {
					System.out.println(b);
					b.hover();
				}
				for(Button butt:buttons) {
					if(butt != b)
						butt.stopHover();
				}
				entered = b;

				int index = buttons.indexOf(playingPlayers.get(playingPlayers.size()-1))+1;
				for(int i = 0;i < 8*playingPlayers.size();i++) {
					if(b == buttons.get(index+i)) {
						((ProfileOption) b).getPO().hover();
					}
				}
				for(int i = 8*playingPlayers.size();i < 16*playingPlayers.size();i++) {
					if(b == buttons.get(index+i)) {
						((ColorOption) b).getPO().hover();
					}
				}
				for(int i = 16*playingPlayers.size();i < 17*playingPlayers.size();i++) {
					if(b == buttons.get(index+i)) {
						((StringEditor) b).getPO().hover();
					}
				}

				if(slider.check(e.getX(),e.getY())) {
					slider.hover();
				} else { slider.stopHover(); }

				repaint();
			}

		});

		for(int n = 0;n < 3;n++) {
			setup.add(new SetUpOption(150+130*n-4*n*n,65,120-8*n,30,
					SetUpOption.Layout.values()[n].name(),
					Button.defaultColor,
					SetUpOption.Layout.values()[n]));
		}
		layout = SetUpOption.Layout.Random;

		slider = new Slider(20,115,455,LENGTH,4);
		for(int n = 0;n < 4;n++) {
			players.add(new PlayerOptions(15+140*n,130,130,230));
		}

		buttons.add(players.get(0));
		players.get(0).addButtons();
		playingPlayers.add(players.get(0));

		buttons.add(new ContinueButton(400,370,LENGTH*2));
	}

	public void addButton(Button button) {
		this.buttons.add(button);
	}
	public void addButtons(ArrayList<Button> buttons) {
		this.buttons.addAll(buttons);
	}
	public void removeButton(Button button) {
		this.buttons.remove(button);
	}
	public void removeButtons(ArrayList<Button> buttons) {
		this.buttons.removeAll(buttons);
	}

	private Button getCheckedButton(MouseEvent e) {
		for(int i = buttons.size()-1;i >= 0;i--) {
			if(buttons.get(i).check(e.getX(),e.getY())) {
				return buttons.get(i);
			}
		}
		return null;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(new Color(200,120,0));
		g.fillRect(0,0,610,515);
		g.setColor(Color.BLACK);
		g.setFont(new Font("TimesNewRoman",Font.BOLD,45));
		g.drawString("Settings",220,50);
		g.setFont(new Font("TimesNewRoman",Font.PLAIN,30));
		g.drawString("Set-Up",20,90);
		for(Button b:buttons) {
			b.draw(g);
		}
		slider.draw(g);
	}
	private void outline(Graphics g) {
		g.setColor(new Color(100,60,180));
		g.fillRect(220,10,200,60);
		g.fillRect(15,65,110,30);
		g.fillRect(130,72,(int) (15*Math.sqrt(3)/2),15);
		for(int n = 0;n < 3;n++) {
			g.fillRect(150+130*n-4*n*n,65,120-8*n,30);
		}
		g.fillRect(15,115,110,30);
		g.fillRect(83,150,455,15/2);
		for(int n = 0;n < 4;n++) {
			g.fillRect(20+150*n,163,130,230);
		}
	}


	public void expandSetUpList() {
		if(expanded) {
			buttons.removeAll(setup);
		} else {
			System.out.println("EXPAND");
			buttons.addAll(0,setup);
		}
		expanded = !expanded;
		repaint();
	}

	public void selectSetUp(SetUpOption.Layout layout) {
		this.layout = layout;
	}

	public void setPlayerSlider(int sliderLoc) {
		this.sliderLoc = sliderLoc;
		showPlayerOptions();
	}
	public void showPlayerOptions() {
		playingPlayers.removeAll(players);
		playingPlayers.add(players.get(0));
		for(int n = 0;n < sliderLoc;n++) {
			playingPlayers.add(players.get(n+1));
		}

		buttons.removeAll(players);
		for(PlayerOptions po:players) {
			po.removeButtons();
		}

		buttons.addAll(playingPlayers);
		for(PlayerOptions po:playingPlayers) {
			po.addProfiles();
		}
		for(PlayerOptions po:playingPlayers) {
			po.addColors();
		}
		for(PlayerOptions po:playingPlayers) {
			po.addStringEditor();
		}
		repaint();
	}

	public void startGame() {
		frame.startGame();
	}
	public SetUpOption.Layout getSetUp() {
		return layout;
	}
	public ArrayList<PlayerOptions> getPlayers() {
		return players;
	}

}
