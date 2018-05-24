//Alan version

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Board {
	private Hex[][] hexArr = new Hex[7][];
	private int boardWidth;
	private int boardHeight;
	private int boardXLoc;
	private int hexWidth;
	private int hexHeight;
	private int xGap;
	private int yGap;
	private ArrayList<OceanHex.Port> availablePorts = new ArrayList<OceanHex.Port>();
	private ArrayList<TerrainHex.Resource> availableTiles = new ArrayList<TerrainHex.Resource>();
	private ArrayList<Integer> availableNumbers = new ArrayList<Integer>();
	private ArrayList<Location> spaces = new ArrayList<Location>();
	private boolean isDesert = false;

	public Board(int pw, int ph) {
		hexArr[0] = new Hex[4];
		hexArr[1] = new Hex[5];
		hexArr[2] = new Hex[6];
		hexArr[3] = new Hex[7];
		hexArr[4] = new Hex[6];
		hexArr[5] = new Hex[5];
		hexArr[6] = new Hex[4];

		boardWidth = pw/2;
		boardHeight = (4*ph)/5;
		boardXLoc = pw/4;
		hexWidth = boardWidth/7;
		hexHeight = (int)(2*(hexWidth/Math.sqrt(3)));
		xGap = boardWidth - 7*hexWidth;
		yGap = boardHeight - (11*hexHeight)/2;

		availablePorts.add(OceanHex.Port.Wood);
		availablePorts.add(OceanHex.Port.Brick);
		availablePorts.add(OceanHex.Port.Wheat);
		availablePorts.add(OceanHex.Port.Sheep);
		availablePorts.add(OceanHex.Port.Rock);
		availablePorts.add(OceanHex.Port.ThreeOne);
		availablePorts.add(OceanHex.Port.ThreeOne);
		availablePorts.add(OceanHex.Port.ThreeOne);
		availablePorts.add(OceanHex.Port.ThreeOne);

		availableTiles.add(TerrainHex.Resource.Wood);
		availableTiles.add(TerrainHex.Resource.Wood);
		availableTiles.add(TerrainHex.Resource.Wood);
		availableTiles.add(TerrainHex.Resource.Wood);
		availableTiles.add(TerrainHex.Resource.Brick);
		availableTiles.add(TerrainHex.Resource.Brick);
		availableTiles.add(TerrainHex.Resource.Brick);
		availableTiles.add(TerrainHex.Resource.Sheep);
		availableTiles.add(TerrainHex.Resource.Sheep);
		availableTiles.add(TerrainHex.Resource.Sheep);
		availableTiles.add(TerrainHex.Resource.Sheep);
		availableTiles.add(TerrainHex.Resource.Wheat);
		availableTiles.add(TerrainHex.Resource.Wheat);
		availableTiles.add(TerrainHex.Resource.Wheat);
		availableTiles.add(TerrainHex.Resource.Wheat);
		availableTiles.add(TerrainHex.Resource.Rock);
		availableTiles.add(TerrainHex.Resource.Rock);
		availableTiles.add(TerrainHex.Resource.Rock);
		availableTiles.add(TerrainHex.Resource.Desert);

		availableNumbers.add(2);
		availableNumbers.add(3);
		availableNumbers.add(3);
		availableNumbers.add(4);
		availableNumbers.add(4);
		availableNumbers.add(5);
		availableNumbers.add(5);
		availableNumbers.add(6);
		availableNumbers.add(6);
		availableNumbers.add(8);
		availableNumbers.add(8);
		availableNumbers.add(9);
		availableNumbers.add(9);
		availableNumbers.add(10);
		availableNumbers.add(10);
		availableNumbers.add(11);
		availableNumbers.add(11);
		availableNumbers.add(12);

		setTileLocs();
		setUpPorts();
		setCoastalLocs();
		setUpOrDown();
	}

	public void setTileLocs() {
		int y = yGap/2 + hexHeight/2;
		int x = boardXLoc;
		for(int r = 0; r < hexArr.length; r++) {
			if(hexArr[r].length == 4) {
				x = boardXLoc + xGap/2 + 2*hexWidth;
			}
			else if(hexArr[r].length == 5) {
				x = boardXLoc + xGap/2 + (3*hexWidth)/2;
			}
			else if(hexArr[r].length == 6) {
				x = boardXLoc + xGap/2 + hexWidth;
			}
			else if(hexArr[r].length == 7) {
				x = boardXLoc + xGap/2 + hexWidth/2;
			}
			for(int c = 0; c < hexArr[r].length; c++) {
				if(r == 0 || r == hexArr.length - 1 || c == 0 || c == hexArr[r].length - 1) {
					hexArr[r][c] = new OceanHex(OceanHex.Port.Ocean, x, y, hexWidth);
				}
				else {
					TerrainHex.Resource randTile = randomTile();
					int randNum = randomNumber();
					hexArr[r][c] = new TerrainHex(randTile, x, y, hexWidth, randNum);
					if(randTile == TerrainHex.Resource.Desert) {
						hexArr[r][c].makeRobber(true);
					}
					for(int i = 0; i < hexArr[r][c].getVertices().size(); i++) {
						if(!isSameLoc(hexArr[r][c].getVertices().get(i))) {
							spaces.add(hexArr[r][c].getVertices().get(i));
							spaces.get(spaces.size() - 1).assign(randTile, randNum);
							spaces.get(spaces.size() - 1).assignHex(hexArr[r][c]);
						}
						else {
							int index = findSameLoc(hexArr[r][c].getVertices().get(i));
							spaces.get(index).assign(randTile, randNum);
							spaces.get(index).assignHex(hexArr[r][c]);
						}
					}
				}
				x += hexWidth;
			}
			y += (3*hexHeight)/4;
		}
	}

	public void setUpPorts() {
		OceanHex.Port currentPort;
		int randIndex;

		randIndex = (int)(Math.random()*availablePorts.size());
		currentPort = availablePorts.get(randIndex);
		availablePorts.remove(randIndex);
		hexArr[0][0] = new OceanHex(currentPort, hexArr[0][0].getXLoc(), hexArr[0][0].getYLoc(), hexWidth);
		portLocations(hexArr[0][0], currentPort);
		randIndex = (int)(Math.random()*availablePorts.size());
		currentPort = availablePorts.get(randIndex);
		availablePorts.remove(randIndex);
		hexArr[0][2] = new OceanHex(currentPort, hexArr[0][2].getXLoc(), hexArr[0][2].getYLoc(), hexWidth);
		portLocations(hexArr[0][2], currentPort);
		randIndex = (int)(Math.random()*availablePorts.size());
		currentPort = availablePorts.get(randIndex);
		availablePorts.remove(randIndex);
		hexArr[1][4] = new OceanHex(currentPort, hexArr[1][4].getXLoc(), hexArr[1][4].getYLoc(), hexWidth);
		portLocations(hexArr[1][4], currentPort);
		randIndex = (int)(Math.random()*availablePorts.size());
		currentPort = availablePorts.get(randIndex);
		availablePorts.remove(randIndex);
		hexArr[2][0] = new OceanHex(currentPort, hexArr[2][0].getXLoc(), hexArr[2][0].getYLoc(), hexWidth);
		portLocations(hexArr[2][0], currentPort);
		randIndex = (int)(Math.random()*availablePorts.size());
		currentPort = availablePorts.get(randIndex);
		availablePorts.remove(randIndex);
		hexArr[3][6] = new OceanHex(currentPort, hexArr[3][6].getXLoc(), hexArr[3][6].getYLoc(), hexWidth);
		portLocations(hexArr[3][6], currentPort);
		randIndex = (int)(Math.random()*availablePorts.size());
		currentPort = availablePorts.get(randIndex);
		availablePorts.remove(randIndex);
		hexArr[4][0] = new OceanHex(currentPort, hexArr[4][0].getXLoc(), hexArr[4][0].getYLoc(), hexWidth);
		portLocations(hexArr[4][0], currentPort);
		randIndex = (int)(Math.random()*availablePorts.size());
		currentPort = availablePorts.get(randIndex);
		availablePorts.remove(randIndex);
		hexArr[5][4] = new OceanHex(currentPort, hexArr[5][4].getXLoc(), hexArr[5][4].getYLoc(), hexWidth);
		portLocations(hexArr[5][4], currentPort);
		randIndex = (int)(Math.random()*availablePorts.size());
		currentPort = availablePorts.get(randIndex);
		availablePorts.remove(randIndex);
		hexArr[6][0] = new OceanHex(currentPort, hexArr[6][0].getXLoc(), hexArr[6][0].getYLoc(), hexWidth);
		portLocations(hexArr[6][0], currentPort);
		randIndex = (int)(Math.random()*availablePorts.size());
		currentPort = availablePorts.get(randIndex);
		availablePorts.remove(randIndex);
		hexArr[6][2] = new OceanHex(currentPort, hexArr[6][2].getXLoc(), hexArr[6][2].getYLoc(), hexWidth);
		portLocations(hexArr[6][2], currentPort);
	}
	
	public void portLocations(Hex h, OceanHex.Port p) {
		int samePorts = 0;
		for(int i = 0; i < spaces.size(); i++) {
			for(int j = 0; j < h.getVertices().size(); j++) {
				if(Math.abs(spaces.get(i).getXLoc() - h.getVertices().get(j).getXLoc()) < 3) {
					if(Math.abs(spaces.get(i).getYLoc() - h.getVertices().get(j).getYLoc()) < 3) {
						if(samePorts < 2)
						spaces.get(i).makePort(p);
						samePorts++;
					}
				}
			}
		}
	}

	public TerrainHex.Resource randomTile() {
		int randIndex = (int)(Math.random()*availableTiles.size());
		TerrainHex.Resource temp = availableTiles.get(randIndex);
		if(temp == TerrainHex.Resource.Desert) {
			isDesert = true;
		}
		availableTiles.remove(randIndex);
		return temp;
	}

	public int randomNumber() {
		if(isDesert == true) {
			isDesert = false;
			return 0;
		}
		else {
			int randIndex = (int)(Math.random()*availableNumbers.size());
			int temp = availableNumbers.get(randIndex);
			availableNumbers.remove(randIndex);
			return temp;
		}
	}
	
	public boolean isSameLoc(Location loc) {
		for(Location temp : spaces) {
			if(Math.abs(loc.getXLoc() - temp.getXLoc()) < 3) {
				if(Math.abs(loc.getYLoc() - temp.getYLoc()) < 3) {
					return true;
				}
			}
		}
		return false;
	}
	
	public int findSameLoc(Location loc) {
		for(int i = 0; i < spaces.size(); i++) {
			if(Math.abs(loc.getXLoc() - spaces.get(i).getXLoc()) < 3) {
				if(Math.abs(loc.getYLoc() - spaces.get(i).getYLoc()) < 3) {
					return i;
				}
			}
		}
		return -1;
	}
	
	public void draw(Graphics g) {
		g.setColor(new Color(153,255,255));
		g.fillRect(boardXLoc, 0, boardWidth, boardHeight);
		for(int r = 0; r < hexArr.length; r++) {
			for(int c = 0; c < hexArr[r].length; c++) {
				hexArr[r][c].draw(g);
			}
		}
		for(Location loc : spaces) {
			loc.draw(g);
		}
	}
	
	public boolean checkAdjacentLocs(Location loc) {
		for(int i = 0; i < spaces.size(); i++) {
			if((Math.sqrt(Math.pow((loc.getXLoc() - spaces.get(i).getXLoc()), 2) + Math.pow((loc.getYLoc() - spaces.get(i).getYLoc()), 2))) < hexHeight/2) {
				if(spaces.get(i).hasSettlement() || spaces.get(i).hasCity()) {
					return false;
				}
			}
		}
		return true;
	}

	public Location closestLoc(int x, int y) {
		for(int i = 0; i < spaces.size(); i++) {
			if(Math.abs(spaces.get(i).getXLoc() - x) < 20) {
				if(Math.abs(spaces.get(i).getYLoc() - y) < 20) {
					return spaces.get(i);
				}
			}
		}
		return null;
	}
	
	public void setUpOrDown() {
		for(Location loc:spaces) {
			Location up = closestLoc(loc.getXLoc(),loc.getYLoc()-40);
			Location down = closestLoc(loc.getXLoc(),loc.getYLoc()+40);
			Location left = closestLoc(loc.getXLoc()-20,loc.getYLoc());
			Location right = closestLoc(loc.getXLoc()+20,loc.getYLoc());

			if(up==null)
				loc.up(false);
			else if(down==null)
				loc.up(true);
		}
	}
		
	public void setCoastalLocs() {
		for(Location loc:spaces) {
			if(loc.isUpLoc()) {
				Location up = closestLoc(loc.getXLoc(),loc.getYLoc()-40);
				Location left = closestLoc(loc.getXLoc()-20,loc.getYLoc());
				Location right = closestLoc(loc.getXLoc()+20,loc.getYLoc());
				
				if(up==null || left==null || right==null) {
					loc.onCoast();
				}
			}
			else if(!loc.isUpLoc()) {
				Location down = closestLoc(loc.getXLoc(),loc.getYLoc()+40);
				Location left = closestLoc(loc.getXLoc()-20,loc.getYLoc());
				Location right = closestLoc(loc.getXLoc()+20,loc.getYLoc());
				
				if(down==null || left==null || right==null) {
					loc.onCoast();
				}
			}
		}
	}
}





// not Evan version

/*

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Board {
	private ArrayList<Player> players = new ArrayList<Player>();
	private Hex[][] hexArr = new Hex[7][];
	private int boardWidth;
	private int boardHeight;
	private int boardXLoc;
	private int hexWidth;
	private int hexHeight;
	private int xGap;
	private ArrayList<Location> buildingSpots = new ArrayList<Location>();
	private ArrayList<OceanHex.Port> availablePorts = new ArrayList<OceanHex.Port>();
	private ArrayList<TerrainHex.Resource> availableTiles = new ArrayList<TerrainHex.Resource>();
	private ArrayList<Integer> availableNumbers = new ArrayList<Integer>();
	private boolean isDesert = false;
	private ArrayList<DevelopmentCard> devCardDeck = new ArrayList<DevelopmentCard>();

	public Board(int pw, int ph) {
		Player player1 = new Player(this,"Ev",1);
		Player player2 = new Player(this,"Devon",2);
		Player player3 = new Player(this,"Payton",3);
		Player player4 = new Player(this,"Maz",4);
		players.add(player1);
		players.add(player2);
		players.add(player3);
		players.add(player4);

		hexArr[0] = new Hex[4];
		hexArr[1] = new Hex[5];
		hexArr[2] = new Hex[6];
		hexArr[3] = new Hex[7];
		hexArr[4] = new Hex[6];
		hexArr[5] = new Hex[5];
		hexArr[6] = new Hex[4];

		boardWidth = pw/2;
		boardHeight = (3*ph)/4;
		boardXLoc = pw/4;
		hexWidth = boardWidth/7;
		hexHeight = (int)(2*(hexWidth/Math.sqrt(3)));
		xGap = boardWidth - 7*hexWidth;

		availablePorts.add(OceanHex.Port.Wood);
		availablePorts.add(OceanHex.Port.Brick);
		availablePorts.add(OceanHex.Port.Wheat);
		availablePorts.add(OceanHex.Port.Sheep);
		availablePorts.add(OceanHex.Port.Rock);
		availablePorts.add(OceanHex.Port.ThreeOne);
		availablePorts.add(OceanHex.Port.ThreeOne);
		availablePorts.add(OceanHex.Port.ThreeOne);
		availablePorts.add(OceanHex.Port.ThreeOne);

		for(int i = 0; i < 4; i ++) {
			availableTiles.add(TerrainHex.Resource.Wood);
			availableTiles.add(TerrainHex.Resource.Sheep);
			availableTiles.add(TerrainHex.Resource.Wheat);
		}
		
		for(int i = 0; i < 3; i++) {
			availableTiles.add(TerrainHex.Resource.Brick);
			availableTiles.add(TerrainHex.Resource.Rock);
		}
		
		availableTiles.add(TerrainHex.Resource.Desert);

		availableNumbers.add(2);
		availableNumbers.add(3);
		availableNumbers.add(3);
		availableNumbers.add(4);
		availableNumbers.add(4);
		availableNumbers.add(5);
		availableNumbers.add(5);
		availableNumbers.add(6);
		availableNumbers.add(6);
		availableNumbers.add(8);
		availableNumbers.add(8);
		availableNumbers.add(9);
		availableNumbers.add(9);
		availableNumbers.add(10);
		availableNumbers.add(10);
		availableNumbers.add(11);
		availableNumbers.add(11);
		availableNumbers.add(12);

		setTileLocs();
		setUpPorts();
		setRobber();
		makeDevCardDeck();
		setBuildingLocs();
	}

	public ArrayList<Player> getPlayers(){
		return players;
	}
	
	public void distributeResources(int diceRoll, Player p) {
		for(int r = 0; r < hexArr.length; r++) {
			for(int c = 0; c < hexArr[r].length;c++) {
				if(hexArr[r][c].getType().equals(Hex.Type.Terrain)) {
					int num = hexArr[r][c].getNumber();
					if(num == diceRoll) {
						ArrayList<Location> vertices = hexArr[r][c].getVertices();
						for(Location v : vertices) {
							if(v.hasBuilding()) {
								v.getBuilding().give(hexArr[r][c].getResource());
							}
						}
					}
				}
			}
		}
	}
	
	public void setRobber() {
		for(int r = 0; r < hexArr.length; r++) {
			for(int c = 0; c < hexArr[r].length;c++) {
				if(hexArr[r][c].getType().equals(Hex.Type.Terrain)) {
					hexArr[r][c].makeRobber();
				}
			}
		}
	}
	
	public void setBuildingLocs() {
		
	}
	
	public void setTileLocs() {
		int y = hexHeight/2;
		int x = boardXLoc;
		for(int r = 0; r < hexArr.length; r++) {
			if(hexArr[r].length == 4) {
				x = boardXLoc + xGap/2 + 2*hexWidth;
			}
			else if(hexArr[r].length == 5) {
				x = boardXLoc + xGap/2 + (3*hexWidth)/2;
			}
			else if(hexArr[r].length == 6) {
				x = boardXLoc + xGap/2 + hexWidth;
			}
			else if(hexArr[r].length == 7) {
				x = boardXLoc + xGap/2 + hexWidth/2;
			}
			for(int c = 0; c < hexArr[r].length; c++) {
				if(r == 0 || r == hexArr.length - 1 || c == 0 || c == hexArr[r].length - 1) {
					hexArr[r][c] = new OceanHex(OceanHex.Port.Ocean, x, y, hexWidth);
				}
				else {
					hexArr[r][c] = new TerrainHex(randomTile(), x, y, hexWidth, randomNumber());
				}
				x += hexWidth;
			}
			y += (3*hexHeight)/4;
		}
	}

	public void setUpPorts() {
		OceanHex.Port currentPort;
		int randIndex;

		randIndex = (int)(Math.random()*availablePorts.size());
		currentPort = availablePorts.get(randIndex);
		availablePorts.remove(randIndex);
		hexArr[0][0] = new OceanHex(currentPort, hexArr[0][0].getXLoc(), hexArr[0][0].getYLoc(), hexWidth);
		randIndex = (int)(Math.random()*availablePorts.size());
		currentPort = availablePorts.get(randIndex);
		availablePorts.remove(randIndex);
		hexArr[0][2] = new OceanHex(currentPort, hexArr[0][2].getXLoc(), hexArr[0][2].getYLoc(), hexWidth);
		randIndex = (int)(Math.random()*availablePorts.size());
		currentPort = availablePorts.get(randIndex);
		availablePorts.remove(randIndex);
		hexArr[1][4] = new OceanHex(currentPort, hexArr[1][4].getXLoc(), hexArr[1][4].getYLoc(), hexWidth);
		randIndex = (int)(Math.random()*availablePorts.size());
		currentPort = availablePorts.get(randIndex);
		availablePorts.remove(randIndex);
		hexArr[2][0] = new OceanHex(currentPort, hexArr[2][0].getXLoc(), hexArr[2][0].getYLoc(), hexWidth);
		randIndex = (int)(Math.random()*availablePorts.size());
		currentPort = availablePorts.get(randIndex);
		availablePorts.remove(randIndex);
		hexArr[3][6] = new OceanHex(currentPort, hexArr[3][6].getXLoc(), hexArr[3][6].getYLoc(), hexWidth);
		randIndex = (int)(Math.random()*availablePorts.size());
		currentPort = availablePorts.get(randIndex);
		availablePorts.remove(randIndex);
		hexArr[4][0] = new OceanHex(currentPort, hexArr[4][0].getXLoc(), hexArr[4][0].getYLoc(), hexWidth);
		randIndex = (int)(Math.random()*availablePorts.size());
		currentPort = availablePorts.get(randIndex);
		availablePorts.remove(randIndex);
		hexArr[5][4] = new OceanHex(currentPort, hexArr[5][4].getXLoc(), hexArr[5][4].getYLoc(), hexWidth);
		randIndex = (int)(Math.random()*availablePorts.size());
		currentPort = availablePorts.get(randIndex);
		availablePorts.remove(randIndex);
		hexArr[6][0] = new OceanHex(currentPort, hexArr[6][0].getXLoc(), hexArr[6][0].getYLoc(), hexWidth);
		randIndex = (int)(Math.random()*availablePorts.size());
		currentPort = availablePorts.get(randIndex);
		availablePorts.remove(randIndex);
		hexArr[6][2] = new OceanHex(currentPort, hexArr[6][2].getXLoc(), hexArr[6][2].getYLoc(), hexWidth);
	}

	public TerrainHex.Resource randomTile() {
		int randIndex = (int)(Math.random()*availableTiles.size());
		TerrainHex.Resource temp = availableTiles.get(randIndex);
		if(temp == TerrainHex.Resource.Desert) {
			isDesert = true;
		}
		availableTiles.remove(randIndex);
		return temp;
	}

	public int randomNumber() {
		if(isDesert == true) {
			isDesert = false;
			return 0;
		}
		else {
			int randIndex = (int)(Math.random()*availableNumbers.size());
			int temp = availableNumbers.get(randIndex);
			availableNumbers.remove(randIndex);
			return temp;
		}
	}

	public void draw(Graphics g) {
		g.setColor(new Color(153,255,255));
		g.fillRect(boardXLoc, 0, boardWidth, boardHeight);

		for(int r = 0; r < hexArr.length; r++) {
			for(int c = 0; c < hexArr[r].length; c++) {
				hexArr[r][c].draw(g);
			}
		}
	}

	public void makeDevCardDeck() {
		for (int k = 0; k < 28; k++) {
			devCardDeck.add(new DevelopmentCard(DevelopmentCard.DevCardTypes.Knight,this));
		}
		for (int vp = 0; vp < 10; vp++) {
			devCardDeck.add(new DevelopmentCard(DevelopmentCard.DevCardTypes.VictoryPoint,this));
		}
		for (int rb = 0; rb < 4; rb++) {
			devCardDeck.add(new DevelopmentCard(DevelopmentCard.DevCardTypes.RoadBuilding,this));
		}
		for (int m = 0; m < 4; m++) {
			devCardDeck.add(new DevelopmentCard(DevelopmentCard.DevCardTypes.Monopoly,this));
		}
		for (int yop = 0; yop < 4; yop++) {
			devCardDeck.add(new DevelopmentCard(DevelopmentCard.DevCardTypes.YearOfPlenty,this));
		}

		ArrayList<DevelopmentCard> temp = new ArrayList<DevelopmentCard>();
		for (int i = 0; i < 50; i++) {
			int rand = (int) (Math.random() * 50);
			while (temp.contains(devCardDeck.get(rand))) {
				rand = (int) (Math.random() * 50);
			}
			temp.add(devCardDeck.get(rand));
		}
		devCardDeck=temp;
	}
	
	public DevelopmentCard takeDevCard() {
		DevelopmentCard dc = devCardDeck.get(0);
		devCardDeck.remove(0);
		return dc;
	}
}

*/

// Devon Version

// import java.awt.Color;
// import java.awt.Graphics;
// import java.util.ArrayList;

// public class Board {
// 	private Hex[][] hexArr = new Hex[7][];
// 	private int boardWidth,boardHeight,boardXLoc;
// 	private int hexWidth;
// 	private int hexHeight;
// 	private int xGap;
// 	private int yGap;
// 	private ArrayList<TerrainHex.Resource> availablePorts = new ArrayList<TerrainHex.Resource>();
// 	private ArrayList<TerrainHex.Resource> availableTiles = new ArrayList<TerrainHex.Resource>();
// 	private ArrayList<Integer> availableNumbers = new ArrayList<Integer>();
// 	private ArrayList<Location> spaces = new ArrayList<Location>();
// 	private boolean isDesert = false;
// 	private ArrayList<DevelopmentCard> devCards = new ArrayList<DevelopmentCard>();

// 	public Board(int pw, int ph) {
// 		for(int i = 0;i < hexArr.length/2+1;i++) {
// 			hexArr[i] = new Hex[i+4];
// 			hexArr[6-i] = new Hex[i+4];
// 		}
// 		for(int i = 0;i < hexArr.length;i++) {
// 			for(int j = 0;j < hexArr[i].length;j++) {
// 				System.out.print("H");
// 			}
// 			System.out.println();
// 		}

// 		boardWidth = pw/2;
// 		boardHeight = (3*ph)/4;
// 		boardXLoc = pw/4;
// 		hexWidth = boardWidth/7;
// 		hexHeight = (int)(2*(hexWidth/Math.sqrt(3)));
// 		xGap = boardWidth - 7*hexWidth;
// 		yGap = boardHeight - (11*hexHeight)/2;

// 		for(int i = 0;i < 5;i++) {
// 			availablePorts.add(TerrainHex.Resource.values()[i]);
// 		}
// 		for(int i = 0;i < 4;i++) {
// 			availablePorts.add(null);

// 			availableTiles.add(TerrainHex.Resource.Wood);
// 			availableTiles.add(TerrainHex.Resource.Sheep);
// 			availableTiles.add(TerrainHex.Resource.Wheat);
// 		}
// 		for(int i = 0;i < 3;i++) {
// 			availableTiles.add(TerrainHex.Resource.Brick);
// 			availableTiles.add(TerrainHex.Resource.Rock);
// 		}
// 		availableTiles.add(TerrainHex.Resource.Desert);

// 		availableNumbers.add(2);
// 		for(int i = 0;i < 4;i++) {
// 			for(int n = 0;n < 2;n++) {
// 				availableNumbers.add(i+3);
// 				availableNumbers.add(11-i);
// 			}
// 		}
// 		availableNumbers.add(12);
// 		System.out.println("Numbers: "+availableNumbers);
// 		System.out.println("Tiles: "+availableTiles);
// 		System.out.println("Ports: "+availablePorts);

// 		setTileLocs();
// 		setUpPorts();
// 	}

// 	public void setTileLocs() {
// 		int y = yGap/2 + hexHeight/2;
// 		int x = boardXLoc;
// 		for(int r = 0; r < hexArr.length; r++) {
// 			if(hexArr[r].length == 4) {
// 				x = boardXLoc + xGap/2 + 2*hexWidth;
// 			}
// 			else if(hexArr[r].length == 5) {
// 				x = boardXLoc + xGap/2 + (3*hexWidth)/2;
// 			}
// 			else if(hexArr[r].length == 6) {
// 				x = boardXLoc + xGap/2 + hexWidth;
// 			}
// 			else if(hexArr[r].length == 7) {
// 				x = boardXLoc + xGap/2 + hexWidth/2;
// 			}
// 			for(int c = 0; c < hexArr[r].length; c++) {
// 				if(r == 0 || r == hexArr.length - 1 || c == 0 || c == hexArr[r].length - 1) {
// 					hexArr[r][c] = new OceanHex(x, y, hexWidth);
// 				}
// 				else {
// 					hexArr[r][c] = new TerrainHex(randomTile(), x, y, hexWidth, randomNumber());
// 					for(int i = 0; i < hexArr[r][c].getVertices().size(); i++) {
// 						if(!spaces.contains(hexArr[r][c].getVertices().get(i))) {
// 							spaces.add(hexArr[r][c].getVertices().get(i));
// 						}
// 					}
// 				}
// 				x += hexWidth;
// 			}
// 			y += (3*hexHeight)/4;
// 		}
// 	}

// 	public void setUpPorts() {
// 		System.out.println("PORTS");
// 		TerrainHex.Resource currentPort;
// 		int randIndex;

// 		int[][] megaArr = {
// 				{0,0}, {0,2}, {1,4}, {2,0}, {3,6},
// 				{4,0}, {5,4}, {6,0}, {6,2}
// 		};

// 		for(int i = 0;i < megaArr.length;i++) {
// 			System.out.println(i);
// 			randIndex = (int)(Math.random()*availablePorts.size());
// 			currentPort = availablePorts.get(randIndex);
// 			availablePorts.remove(randIndex);
// 			hexArr[megaArr[i][0]][megaArr[i][1]]= new OceanHex(
// 							currentPort,
// 							hexArr[megaArr[i][0]][megaArr[i][1]].getXLoc(),
// 							hexArr[megaArr[i][0]][megaArr[i][1]].getYLoc(),
// 							hexWidth
// 							);
// 		}
// 	}

// 	public TerrainHex.Resource randomTile() {
// 		int randIndex = (int)(Math.random()*availableTiles.size());
// 		TerrainHex.Resource temp = availableTiles.get(randIndex);
// 		if(temp == TerrainHex.Resource.Desert) {
// 			isDesert = true;
// 		}
// 		availableTiles.remove(randIndex);
// 		return temp;
// 	}

// 	public int randomNumber() {
// 		System.out.println(availableTiles.size()+". Tiles: "+availableTiles);
// 		if(isDesert == true) {
// 			isDesert = false;
// 			return 0;
// 		} else {
// 			int randIndex = (int)(Math.random()*availableNumbers.size());
// 			int temp = availableNumbers.get(randIndex);
// 			availableNumbers.remove(randIndex);
// 			return temp;
// 		}
// 	}

// 	public void draw(Graphics g) {
// 		System.out.println("Draw");
// 		g.setColor(new Color(153,255,255));
// 		g.fillRect(boardXLoc, 0, boardWidth, boardHeight);

// 		for(int r = 0; r < hexArr.length; r++) {
// 			for(int c = 0; c < hexArr[r].length; c++) {
// 				hexArr[r][c].draw(g);
// 			}
// 		}
// 	}
	
// 	public void makeDevCardDeck() {
// 		for (int k = 0; k < 28; k++) {
// 			devCards.add(new DevelopmentCard(DevelopmentCard.DevCardTypes.Knight));
// 		}
// 		for (int vp = 0; vp < 10; vp++) {
// 			devCards.add(new DevelopmentCard(DevelopmentCard.DevCardTypes.VictoryPoint));
// 		}
// 		for (int rb = 0; rb < 4; rb++) {
// 			devCards.add(new DevelopmentCard(DevelopmentCard.DevCardTypes.RoadBuilding));
// 		}
// 		for (int m = 0; m < 4; m++) {
// 			devCards.add(new DevelopmentCard(DevelopmentCard.DevCardTypes.Monopoly));
// 		}
// 		for (int yop = 0; yop < 4; yop++) {
// 			devCards.add(new DevelopmentCard(DevelopmentCard.DevCardTypes.YearOfPlenty));
// 		}

// 		ArrayList<DevelopmentCard> temp = new ArrayList<DevelopmentCard>();
// 		for (int i = 0; i < 50; i++) {
// 			int rand = (int) (Math.random() * 50);
// 			while (temp.contains(devCards.get(rand))) {
// 				rand = (int) (Math.random() * 50);
// 			}
// 			temp.add(devCards.get(rand));
// 		}
// 		devCards=temp;
// 	}

// 	public DevelopmentCard takeDevCard() {
// 		DevelopmentCard dc = devCards.get(0);
// 		devCards.remove(0);
// 		return dc;
// 	}

// }

