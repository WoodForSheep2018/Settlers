import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

public class SettlersPanel extends JPanel {
	public static SettlersOfCatanGame frame;
	private final int PANEL_WIDTH = 1200;
	private final int PANEL_HEIGHT = 800;
	private Board b;
	private Player player1;
	private Player player2;
	private Player player3;
	private Player player4;
	private ArrayList<Player> playerList = new ArrayList<Player>();
	private ArrayList<PlayerBox> playerBoxList = new ArrayList<PlayerBox>();
	private Player currentPlayer;
	private int turn;
	private boolean diceRolled = false;
	private boolean roadAfterSettlement = false;
	private int settlementX;
	private int settlementY;
	private DiceButton diceButton;
	private MenuButton menuButton;
	private int curRoll = 0;
	private int x;
	private int y;
	private boolean buildRoad = false;
	private boolean buildSettlement = false;
	private boolean buildCity = false;
	private int startX;
	private int startY;
	private int endX;
	private int endY;
	private int roadClickNumber = -1;

	public static void main(String[] args) {
		JFrame frame = new JFrame("Catan");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new SettlersPanel());
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
	}

	public SettlersPanel() {
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		setUpGameObjects();
		startGame();
	}

	public SettlersPanel(MainMenu settings) {
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		setUpGameObjects(settings.getPlayers());
		startGame();
	}

	public void doPlayerActions() {
		if(menuButton.devCardBox(x,y)) {
			
		}
		else if(menuButton.roadBox(x,y)) {
			buildRoad = true;
		} 
		else if(menuButton.settlementBox(x,y)) {
			buildSettlement = true;
		} 
		else if(menuButton.cityBox(x,y)) {
			buildCity = true;
		} 
		else if(menuButton.buyDevCardBox(x,y)) {
			
		}
		else if(menuButton.endTurnBox(x,y)) {
			nextTurn();
		}
	}

	public void rollDice() {
		if(!diceRolled) {
			if (x >= diceButton.getXMin() && x <= diceButton.getXMax() && y >= diceButton.getYMin() && y <= diceButton.getYMax()) {
				curRoll = diceButton.roll();
				b.giveResources(curRoll);
				diceRolled = true;
				repaint();
			}
		}
	}

	public void pickStartingSettlements() {
		if (roadAfterSettlement == false) {
			if (b.closestLoc(x, y) != null) {
				settlementX = b.closestLoc(x, y).getXLoc();
				settlementY = b.closestLoc(x, y).getYLoc();
				if (b.checkAdjacentLocs(b.closestLoc(x, y))) {
					b.closestLoc(x, y).makeSettlement(currentPlayer);
					currentPlayer.changeSets(-1);
					if(turn>playerList.size()) {
						ArrayList<Hex> hexes = b.closestLoc(x,y).getHexes();
						for(Hex hex:hexes) {
							if(hex.getResource() != null)
								b.giveResources(currentPlayer, hex.getResource());
						}
					}
					repaint();
					roadAfterSettlement = true;
				}
			}
		} 
		else if (roadAfterSettlement == true) {
			if (b.closestLoc(x, y) != null) {
				int roadFinalX = b.closestLoc(x, y).getXLoc();
				int roadFinalY = b.closestLoc(x, y).getYLoc();
				if(b.isAdjacent(settlementX, settlementY, roadFinalX, roadFinalY)) {
					if(!(settlementX == roadFinalX && settlementY == roadFinalY)) {
						b.addRoad(new Road(settlementX, settlementY, roadFinalX, roadFinalY, currentPlayer));
						currentPlayer.changeRoads(-1);
						repaint();
						roadAfterSettlement = false;
						nextTurn();
					}
				}
			}
		}
	}
	

	public void setUpMouseListener() {
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				x = e.getX();
				y = e.getY();
				if(roadClickNumber == 0) {
					startX = x;
					startY = y;
					roadClickNumber++;
				}
				else if(roadClickNumber==1) {
					endX = x;
					endY = y;
					roadClickNumber = -1;
				}
				if(turn <= playerList.size()*2) {
					pickStartingSettlements();
				} 
				else {
					rollDice();
					if(diceRolled && x>=menuButton.getXMin() && x<=menuButton.getXMax() && y>=menuButton.getYMin() && y<=menuButton.getYMax()) {
						doPlayerActions();
					}
				}
			}
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
			}
		});
	}

	public void setUpGameObjects() {
		b = new Board(PANEL_WIDTH, PANEL_HEIGHT);
		player1 = new Player(b, "Ev", Color.BLUE);
		playerList.add(player1);
		player2 = new Player(b, "Devon", Color.YELLOW);
		playerList.add(player2);
		player3 = new Player(b, "Payton", Color.PINK);
		playerList.add(player3);
		player4 = new Player(b, "Al", Color.CYAN);
		playerList.add(player4);
		for(int i = 1; i <= playerList.size(); i++) {
			int xLoc;
			int yLoc;
			if(i == 1 || i == 4)
				xLoc = 0;
			else
				xLoc = (3*PANEL_WIDTH)/4;
			if(i == 1 || i == 2)
				yLoc = 0;
			else
				yLoc = PANEL_HEIGHT/2;
			playerBoxList.add(new PlayerBox(PANEL_WIDTH, PANEL_HEIGHT, xLoc, yLoc, playerList.get(i-1)));
		}
		diceButton = new DiceButton(b);
		menuButton = new MenuButton(b, PANEL_HEIGHT);
	}

	public void setUpGameObjects(ArrayList<PlayerOptions> players) {
		b = new Board(PANEL_WIDTH, PANEL_HEIGHT);
		for(int n = 0; n < players.size(); n++) {
			for(int i = 0; i < n; i++) {
				if(players.get(n).getColor().equals(players.get(i).getColor())) {
					players.get(n).changeColor(new Color((int) (players.get(n).getColor().getRed() * .9),
							(int)(players.get(n).getColor().getGreen() * .9),
							(int)(players.get(n).getColor().getBlue() * .9)));
				}
			}
			playerList.add(new Player(b, players.get(n).getName(), players.get(n).getColor()));
		}
		for(int i = 1; i <= playerList.size(); i++) {
			int xLoc;
			int yLoc;
			if(i == 1 || i == 4)
				xLoc = 0;
			else
				xLoc = (3*PANEL_WIDTH)/4;
			if(i == 1 || i == 2)
				yLoc = 0;
			else
				yLoc = PANEL_HEIGHT/2;
			playerBoxList.add(new PlayerBox(PANEL_WIDTH, PANEL_HEIGHT, xLoc, yLoc, playerList.get(i-1)));
		}
		diceButton = new DiceButton(b);
		menuButton = new MenuButton(b, PANEL_HEIGHT);
	}

	public void startGame() {
		turn = 1;
		pickStartingPlayer();
		pickStartingSettlements();
		setUpMouseListener();
	}

	public void pickStartingPlayer() {
		int start = (int) (Math.random()*playerList.size() + 1);
		for (Player p : playerList) {
			if(p.getPlayerNumber() == start) {
				currentPlayer = p;
				currentPlayer.setTurn(true);
			}
		}
	}

	public void nextTurn() {
		diceRolled = false;
		turn++;
		currentPlayer.setTurn(false);
		int current = currentPlayer.getPlayerNumber();
		if(current < playerList.size()) {
			current++;
		} 
		else {
			current = 1;
		}
		for(Player p : playerList) {
			if(p.getPlayerNumber() == current) {
				currentPlayer = p;
				currentPlayer.setTurn(true);
			}
		}
		repaint();
	}

	public boolean gameOver() {
		for (Player p : playerList) {
			if (p.getPoints() >= 10) {
				return true;
			}
		}
		return false;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		b.draw(g);
		for(PlayerBox pb : playerBoxList) {
			pb.draw(g);
		}
		diceButton.draw(g);
		menuButton.draw(g);
	}

}

