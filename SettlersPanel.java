import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
	private int startX = 0;
	private int startY = 0;
	private int endX;
	private int endY;
	private int roadClickNumber = 0;
	private boolean settlementClicking = false;
	private boolean cityClicking = false;

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

	public void rollDice() {
		if (!diceRolled) {
			if (x >= diceButton.getXMin() && x <= diceButton.getXMax() && y >= diceButton.getYMin()
					&& y <= diceButton.getYMax()) {
				curRoll = diceButton.roll();
				b.giveResources(curRoll);
				if(curRoll==7) {
					sevenRolled();
				}
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
					if(b.closestLoc(x,y).isPort()) {
						currentPlayer.addPort(b.closestLoc(x,y).getPort());
					}
					currentPlayer.changeSets(-1);
					currentPlayer.addVp();
					if (turn > playerList.size()) {
						ArrayList<Hex> hexes = b.closestLoc(x, y).getHexes();
						for (Hex hex : hexes) {
							if (hex.getResource() != null)
								b.giveResources(currentPlayer, hex.getResource());
						}
					}
					repaint();
					roadAfterSettlement = true;
				}
			}
		} else if (roadAfterSettlement == true) {
			if (b.closestLoc(x, y) != null) {
				int roadFinalX = b.closestLoc(x, y).getXLoc();
				int roadFinalY = b.closestLoc(x, y).getYLoc();
				if (b.isAdjacent(settlementX, settlementY, roadFinalX, roadFinalY)) {
					if (!(settlementX == roadFinalX && settlementY == roadFinalY)) {
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
				if (roadClickNumber == 1 && b.closestLoc(x, y) != null) {
					startX = b.closestLoc(x, y).getXLoc();
					startY = b.closestLoc(x, y).getYLoc();
					roadClickNumber = 2;
				} else if (roadClickNumber == 2 && b.closestLoc(x, y) != null) {
					endX = b.closestLoc(x, y).getXLoc();
					endY = b.closestLoc(x, y).getYLoc();
					if (!b.roadInBetween(b.closestLoc(startX, startY), b.closestLoc(endX, endY))
							&& b.isCorrectRoadLength(startX, startY, endX, endY)) {
						currentPlayer.buildRoad();
						b.addRoad(new Road(startX, startY, endX, endY, currentPlayer));
						//giveLongestRoad;
					}
					roadClickNumber = 0;
					repaint();
				}

				if (settlementClicking) {
					if (b.closestLoc(x, y) != null) {
						if (b.checkAdjacentLocs(b.closestLoc(x, y))) {
							currentPlayer.buildSettlement();
							b.closestLoc(x, y).makeSettlement(currentPlayer);
							if(b.closestLoc(x,y).isPort()) {
								currentPlayer.addPort(b.closestLoc(x,y).getPort());
							}
							repaint();
						}
					}
					settlementClicking = false;
				}
				
				if (cityClicking) {
					if(b.closestLoc(x,y) != null) {
						if (b.closestLoc(x,y).hasSettlement()) {
							currentPlayer.buildCity();
							b.closestLoc(x,y).makeCity();
							currentPlayer.changeSets(1);
							repaint();
						}
					}
					cityClicking = false;
				}

				if (turn <= playerList.size() * 2) {
					pickStartingSettlements();
				} else {
					rollDice();
					if (diceRolled) {
						if (menuButton.playDevCardBox(x, y)) {
							
						} 
						
						else if (menuButton.buyDevCardBox(x, y)) {
							if(currentPlayer.canBuyDevCard()) {
								currentPlayer.buyDevCard();
							}
						} 
						
						else if (menuButton.roadBox(x, y)) {
							if (currentPlayer.canBuildRoad()) {
								roadClickNumber = 1;
							}
						} 
						
						else if (menuButton.settlementBox(x, y)) {
							if (currentPlayer.canBuildSettlement()) {
								settlementClicking = true;
							}
						} 
						
						else if (menuButton.cityBox(x, y)) {
							if (currentPlayer.canBuildCity()) {
								cityClicking = true;
							}

						} 
						
						else if (menuButton.endTurnBox(x, y)) {
							nextTurn();
						}
						
						else if (menuButton.tradeWBankBox(x,y)) {
							tradeWBank();
						}
						
						else if (menuButton.tradeWOtherBox(x,y)) {
							
						}
						repaint();

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
		for (int i = 1; i <= playerList.size(); i++) {
			int xLoc;
			int yLoc;
			if (i == 1 || i == 4)
				xLoc = 0;
			else
				xLoc = (3 * PANEL_WIDTH) / 4;
			if (i == 1 || i == 2)
				yLoc = 0;
			else
				yLoc = PANEL_HEIGHT / 2;
			playerBoxList.add(new PlayerBox(PANEL_WIDTH, PANEL_HEIGHT, xLoc, yLoc, playerList.get(i - 1)));
		}
		diceButton = new DiceButton(b);
		menuButton = new MenuButton(b, PANEL_HEIGHT);
	}

	public void setUpGameObjects(ArrayList<PlayerOptions> players) {
		b = new Board(PANEL_WIDTH, PANEL_HEIGHT);
		for (int n = 0; n < players.size(); n++) {
			for (int i = 0; i < n; i++) {
				if (players.get(n).getColor().equals(players.get(i).getColor())) {
					players.get(n)
							.changeColor(new Color((int) (players.get(n).getColor().getRed() * .9),
									(int) (players.get(n).getColor().getGreen() * .9),
									(int) (players.get(n).getColor().getBlue() * .9)));
				}
			}
			playerList.add(new Player(b, players.get(n).getName(), players.get(n).getColor()));
		}
		for (int i = 1; i <= playerList.size(); i++) {
			int xLoc;
			int yLoc;
			if (i == 1 || i == 4)
				xLoc = 0;
			else
				xLoc = (3 * PANEL_WIDTH) / 4;
			if (i == 1 || i == 2)
				yLoc = 0;
			else
				yLoc = PANEL_HEIGHT / 2;
			playerBoxList.add(new PlayerBox(PANEL_WIDTH, PANEL_HEIGHT, xLoc, yLoc, playerList.get(i - 1)));
		}
		diceButton = new DiceButton(b);
		menuButton = new MenuButton(b, PANEL_HEIGHT);
	}

	public void startGame() {
		turn = 1;
		pickStartingPlayer();
		pickStartingSettlements();
		setUpMouseListener();
//		for(int i=0; i<10; i++) {
//			currentPlayer.addVp();
//		}
	}

	public void pickStartingPlayer() {
		int start = (int) (Math.random() * playerList.size() + 1);
		for (Player p : playerList) {
			if (p.getPlayerNumber() == start) {
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
		if (current < playerList.size()) {
			current++;
		} else {
			current = 1;
		}
		for (Player p : playerList) {
			if (p.getPlayerNumber() == current) {
				currentPlayer = p;
				currentPlayer.setTurn(true);
			}
		}
		repaint();
	}
	
	private void tradeWBank() {
		String res = JOptionPane.showInputDialog("Enter as follows (part1 is give; part2 is receive): Resource,number;Resource");
		String giveRes = res.substring(0, res.indexOf(","));
		String giveNum = res.substring(res.indexOf(",")+1, res.indexOf(";"));
		String recRes = res.substring(res.indexOf(";")+1);
		TerrainHex.Resource give = TerrainHex.Resource.Desert;
		TerrainHex.Resource rec = TerrainHex.Resource.Desert;
		int num = Integer.parseInt(giveNum);
		
		if(giveRes.equals("Brick")) {
			give = TerrainHex.Resource.Brick;
		}
		else if(giveRes.equals("Wood")) {
			give = TerrainHex.Resource.Wood;
		}
		else if(giveRes.equals("Wheat")) {
			give = TerrainHex.Resource.Wheat;
		}
		else if(giveRes.equals("Sheep")) {
			give = TerrainHex.Resource.Sheep;
		}
		else if(giveRes.equals("Rock")) {
			give = TerrainHex.Resource.Rock;
		}
		
		if(recRes.equals("Brick")) {
			rec = TerrainHex.Resource.Brick;
		}
		else if(recRes.equals("Wood")) {
			rec = TerrainHex.Resource.Wood;
		}
		else if(recRes.equals("Wheat")) {
			rec = TerrainHex.Resource.Wheat;
		}
		else if(recRes.equals("Sheep")) {
			rec = TerrainHex.Resource.Sheep;
		}
		else if(recRes.equals("Rock")) {
			rec = TerrainHex.Resource.Rock;
		}
		
		if(currentPlayer.hasPortOfType(OceanHex.Port.ThreeOne) && num>=3) {
			int calc = num/3;
			int g = 3*calc;
			currentPlayer.removeCardsOfType(give, g);
			for(int i=0; i<calc; i++) {
				currentPlayer.addCard(rec);
			}
		}
		else if(currentPlayer.hasPortOfType(OceanHex.Port.Brick) && give.equals(TerrainHex.Resource.Brick) && num>=2) {
			int calc = num/2;
			int g = 2*calc;
			currentPlayer.removeCardsOfType(give, g);
			for(int i=0; i<calc; i++) {
				currentPlayer.addCard(rec);
			}
		}
		else if(currentPlayer.hasPortOfType(OceanHex.Port.Wood) && give.equals(TerrainHex.Resource.Wood) && num>=2) {
			int calc = num/2;
			int g = 2*calc;
			currentPlayer.removeCardsOfType(give, g);
			for(int i=0; i<calc; i++) {
				currentPlayer.addCard(rec);
			}
		}
		else if(currentPlayer.hasPortOfType(OceanHex.Port.Wheat) && give.equals(TerrainHex.Resource.Wheat) && num>=2) {
			int calc = num/2;
			int g = 2*calc;
			currentPlayer.removeCardsOfType(give, g);
			for(int i=0; i<calc; i++) {
				currentPlayer.addCard(rec);
			}
		}
		else if(currentPlayer.hasPortOfType(OceanHex.Port.Sheep) && give.equals(TerrainHex.Resource.Sheep) && num>=2) {
			int calc = num/2;
			int g = 2*calc;
			currentPlayer.removeCardsOfType(give, g);
			for(int i=0; i<calc; i++) {
				currentPlayer.addCard(rec);
			}
		}
		else if(currentPlayer.hasPortOfType(OceanHex.Port.Rock) && give.equals(TerrainHex.Resource.Rock) && num>=2) {
			int calc = num/2;
			int g = 2*calc;
			currentPlayer.removeCardsOfType(give, g);
			for(int i=0; i<calc; i++) {
				currentPlayer.addCard(rec);
			}
		}
		else {
			if(num>=4) {
				int calc = num/4;
				int g = 4*calc;
				currentPlayer.removeCardsOfType(give, g);
				for(int i=0; i<calc; i++) {
					currentPlayer.addCard(rec);
				}
			}
		}
	}
	
	private void sevenRolled() {
		for(Player p:playerList) {
			if(p.getRCards().size()>7)
				p.giveUpHalf();
		}
		int r = (int) (Math.random()*5)+1;
		int c = 0;
		if(r==1 || r==5) {
			c = (int) (Math.random()*3)+1;
		}
		else if(r==2 || r==4) {
			c = (int) (Math.random()*4)+1;
		}
		else if(r==3) {
			c = (int) (Math.random()*5)+1;
		}
		b.setRobber(r,c);
		Hex hex = b.getHex(r,c);
		ArrayList<Location> locs = hex.getVertices();
		ArrayList<Player> builders = new ArrayList<Player>();
		for(Location l:locs) {
			if(l.hasSettlement() || l.hasCity()) {
				builders.add(l.getBuilding().getOwner());
			}
		}
		if(builders.size()>0) {
			int rand = (int) (Math.random()*builders.size());
			if(builders.get(rand).getRCards().size()>0) {
				currentPlayer.addCard(builders.get(rand).removeRandomCard());
			}
		}
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
		if(!gameOver()) {
			super.paintComponent(g);
			b.draw(g);
			for (PlayerBox pb : playerBoxList) {
				pb.draw(g);
			}
			diceButton.draw(g);
			menuButton.draw(g);	
		}
		else {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Times New Roman",Font.BOLD, 30));
			g.drawString("Game over: winner is Ev", PANEL_WIDTH/3, PANEL_HEIGHT/2);
		}
	}

}
