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
	private int roadLength;
	private int xGap;
	private int yGap;
	private ArrayList<OceanHex.Port> availablePorts = new ArrayList<OceanHex.Port>();
	private ArrayList<TerrainHex.Resource> availableTiles = new ArrayList<TerrainHex.Resource>();
	private ArrayList<Integer> availableNumbers = new ArrayList<Integer>();
	private ArrayList<Location> spaces = new ArrayList<Location>();
	private ArrayList<Road> roads = new ArrayList<Road>();
	private boolean isDesert = false;
	private ArrayList<DevelopmentCard> devCardDeck = new ArrayList<DevelopmentCard>();
	private Hex curRobber;

	public Board(int pw, int ph) {
		hexArr[0] = new Hex[4];
		hexArr[1] = new Hex[5];
		hexArr[2] = new Hex[6];
		hexArr[3] = new Hex[7];
		hexArr[4] = new Hex[6];
		hexArr[5] = new Hex[5];
		hexArr[6] = new Hex[4];

		boardWidth = pw / 2;
		boardHeight = (4 * ph) / 5;
		boardXLoc = pw / 4;
		hexWidth = boardWidth / 7;
		hexHeight = (int) (2 * (hexWidth / Math.sqrt(3)));
		xGap = boardWidth - 7 * hexWidth;
		yGap = boardHeight - (11 * hexHeight) / 2;
		roadLength = hexHeight/2;

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
		makeDevCardDeck();
	}

	public void giveResources(Player p, TerrainHex.Resource ... res) {
		for(int i=0; i<res.length; i++) {
			p.addCard(new ResourceCard(res[i]));
		}
	}
	
	public void giveResources(int roll) {
		ArrayList<Hex> hexes = new ArrayList<Hex>();
		for (Location l : spaces) {
			if (l.hasBuilding() && l.getBuilding().hasOwner() && l.getNums().contains(roll)) {
				hexes = l.getHexes();
				for (Hex h : hexes) {
					if (!h.isRobber && ((TerrainHex) h).getNumber() == roll) {
						for(int i=0; i<l.getBuilding().getLevel(); i++) {
							l.getBuilding().getOwner().addCard(((TerrainHex) h).getResource());
						}
					}
				}
			}
		}
	}
	
	public Hex getHex(int r, int c) {
		return hexArr[r][c];
	}
	public void setRobber(int r, int c) {
		curRobber.makeRobber(false);
		hexArr[r][c].makeRobber(true);
		curRobber = hexArr[r][c];
	}
	
	public void setTileLocs() {
		int y = yGap / 2 + hexHeight / 2;
		int x = boardXLoc;
		for (int r = 0; r < hexArr.length; r++) {
			if (hexArr[r].length == 4) {
				x = boardXLoc + xGap / 2 + 2 * hexWidth;
			} else if (hexArr[r].length == 5) {
				x = boardXLoc + xGap / 2 + (3 * hexWidth) / 2;
			} else if (hexArr[r].length == 6) {
				x = boardXLoc + xGap / 2 + hexWidth;
			} else if (hexArr[r].length == 7) {
				x = boardXLoc + xGap / 2 + hexWidth / 2;
			}
			for (int c = 0; c < hexArr[r].length; c++) {
				if (r == 0 || r == hexArr.length - 1 || c == 0 || c == hexArr[r].length - 1) {
					hexArr[r][c] = new OceanHex(OceanHex.Port.Ocean, x, y, hexWidth);
				} else {
					TerrainHex.Resource randTile = randomTile();
					int randNum = randomNumber();
					hexArr[r][c] = new TerrainHex(randTile, x, y, hexWidth, randNum);
					if (randTile == TerrainHex.Resource.Desert) {
						hexArr[r][c].makeRobber(true);
						curRobber = hexArr[r][c];
					}
					for (int i = 0; i < hexArr[r][c].getVertices().size(); i++) {
						if (!isSameLoc(hexArr[r][c].getVertices().get(i))) {
							spaces.add(hexArr[r][c].getVertices().get(i));
							spaces.get(spaces.size() - 1).assign(randTile, randNum);
							spaces.get(spaces.size() - 1).assignHex(hexArr[r][c]);
						} else {
							int index = findSameLoc(hexArr[r][c].getVertices().get(i));
							spaces.get(index).assign(randTile, randNum);
							spaces.get(index).assignHex(hexArr[r][c]);
						}
					}
				}
				x += hexWidth;
			}
			y += (3 * hexHeight) / 4;
		}
	}

	public void setUpPorts() {
		OceanHex.Port currentPort;
		int randIndex;

		randIndex = (int) (Math.random() * availablePorts.size());
		currentPort = availablePorts.get(randIndex);
		availablePorts.remove(randIndex);
		hexArr[0][0] = new OceanHex(currentPort, hexArr[0][0].getXLoc(), hexArr[0][0].getYLoc(), hexWidth);
		portLocations(hexArr[0][0], currentPort);
		randIndex = (int) (Math.random() * availablePorts.size());
		currentPort = availablePorts.get(randIndex);
		availablePorts.remove(randIndex);
		hexArr[0][2] = new OceanHex(currentPort, hexArr[0][2].getXLoc(), hexArr[0][2].getYLoc(), hexWidth);
		portLocations(hexArr[0][2], currentPort);
		randIndex = (int) (Math.random() * availablePorts.size());
		currentPort = availablePorts.get(randIndex);
		availablePorts.remove(randIndex);
		hexArr[1][4] = new OceanHex(currentPort, hexArr[1][4].getXLoc(), hexArr[1][4].getYLoc(), hexWidth);
		portLocations(hexArr[1][4], currentPort);
		randIndex = (int) (Math.random() * availablePorts.size());
		currentPort = availablePorts.get(randIndex);
		availablePorts.remove(randIndex);
		hexArr[2][0] = new OceanHex(currentPort, hexArr[2][0].getXLoc(), hexArr[2][0].getYLoc(), hexWidth);
		portLocations(hexArr[2][0], currentPort);
		randIndex = (int) (Math.random() * availablePorts.size());
		currentPort = availablePorts.get(randIndex);
		availablePorts.remove(randIndex);
		hexArr[3][6] = new OceanHex(currentPort, hexArr[3][6].getXLoc(), hexArr[3][6].getYLoc(), hexWidth);
		portLocations(hexArr[3][6], currentPort);
		randIndex = (int) (Math.random() * availablePorts.size());
		currentPort = availablePorts.get(randIndex);
		availablePorts.remove(randIndex);
		hexArr[4][0] = new OceanHex(currentPort, hexArr[4][0].getXLoc(), hexArr[4][0].getYLoc(), hexWidth);
		portLocations(hexArr[4][0], currentPort);
		randIndex = (int) (Math.random() * availablePorts.size());
		currentPort = availablePorts.get(randIndex);
		availablePorts.remove(randIndex);
		hexArr[5][4] = new OceanHex(currentPort, hexArr[5][4].getXLoc(), hexArr[5][4].getYLoc(), hexWidth);
		portLocations(hexArr[5][4], currentPort);
		randIndex = (int) (Math.random() * availablePorts.size());
		currentPort = availablePorts.get(randIndex);
		availablePorts.remove(randIndex);
		hexArr[6][0] = new OceanHex(currentPort, hexArr[6][0].getXLoc(), hexArr[6][0].getYLoc(), hexWidth);
		portLocations(hexArr[6][0], currentPort);
		randIndex = (int) (Math.random() * availablePorts.size());
		currentPort = availablePorts.get(randIndex);
		availablePorts.remove(randIndex);
		hexArr[6][2] = new OceanHex(currentPort, hexArr[6][2].getXLoc(), hexArr[6][2].getYLoc(), hexWidth);
		portLocations(hexArr[6][2], currentPort);
	}

	public void portLocations(Hex h, OceanHex.Port p) {
		int samePorts = 0;
		for (int i = 0; i < spaces.size(); i++) {
			for (int j = 0; j < h.getVertices().size(); j++) {
				if (Math.abs(spaces.get(i).getXLoc() - h.getVertices().get(j).getXLoc()) < 3) {
					if (Math.abs(spaces.get(i).getYLoc() - h.getVertices().get(j).getYLoc()) < 3) {
						if (samePorts < 2)
							spaces.get(i).makePort(p);
						samePorts++;
					}
				}
			}
		}
	}

	public TerrainHex.Resource randomTile() {
		int randIndex = (int) (Math.random() * availableTiles.size());
		TerrainHex.Resource temp = availableTiles.get(randIndex);
		if (temp == TerrainHex.Resource.Desert) {
			isDesert = true;
		}
		availableTiles.remove(randIndex);
		return temp;
	}

	public int randomNumber() {
		if (isDesert == true) {
			isDesert = false;
			return 0;
		} 
		else {
			int randIndex = (int) (Math.random() * availableNumbers.size());
			int temp = availableNumbers.get(randIndex);
			availableNumbers.remove(randIndex);
			return temp;
		}
	}

	public boolean isSameLoc(Location loc) {
		for (Location temp : spaces) {
			if (Math.abs(loc.getXLoc() - temp.getXLoc()) < 3) {
				if (Math.abs(loc.getYLoc() - temp.getYLoc()) < 3) {
					return true;
				}
			}
		}
		return false;
	}

	public int findSameLoc(Location loc) {
		for (int i = 0; i < spaces.size(); i++) {
			if (Math.abs(loc.getXLoc() - spaces.get(i).getXLoc()) < 3) {
				if (Math.abs(loc.getYLoc() - spaces.get(i).getYLoc()) < 3) {
					return i;
				}
			}
		}
		return -1;
	}

	public void draw(Graphics g) {
		g.setColor(new Color(153, 255, 255));
		g.fillRect(boardXLoc, 0, boardWidth, boardHeight);
		for (int r = 0; r < hexArr.length; r++) {
			for (int c = 0; c < hexArr[r].length; c++) {
				hexArr[r][c].draw(g);
			}
		}
		for (Location loc : spaces) {
			loc.draw(g);
		}
		for (Road r : roads) {
			r.draw(g);
		}
	}

	public boolean checkAdjacentLocs(Location loc) {
		for (int i = 0; i < spaces.size(); i++) {
			if ((Math.sqrt(Math.pow((loc.getXLoc() - spaces.get(i).getXLoc()), 2)
					+ Math.pow((loc.getYLoc() - spaces.get(i).getYLoc()), 2))) < hexHeight / 2 + 5) {
				if (spaces.get(i).hasSettlement() || spaces.get(i).hasCity()) {
					return false;
				}
			}
		}
		return true;
	}

	public Location closestLoc(int x, int y) {
		for (int i = 0; i < spaces.size(); i++) {
			if (Math.abs(spaces.get(i).getXLoc() - x) < 20) {
				if (Math.abs(spaces.get(i).getYLoc() - y) < 20) {
					return spaces.get(i);
				}
			}
		}
		return null;
	}
	
	public boolean isAdjacent(int x1, int y1, int x2, int y2) {
		if ((Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2))) < hexHeight / 2 + 5) {
			return true;
		}
		return false;
	}

	public boolean roadInBetween(Location one, Location two) {
		for(Road road:roads) {
			if(road.isAtLocation(one.getXLoc(), one.getYLoc()) && road.isAtLocation(two.getXLoc(), two.getYLoc())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isCorrectRoadLength(int x1, int y1, int x2, int y2) {
		int xDist = Math.abs(x2-x1);
		int yDist = Math.abs(y2-y1);
		
		int dist = (int) Math.sqrt((xDist*xDist)+(yDist*yDist));
		return (Math.abs(roadLength-dist)<5);
	}
	
	public void addRoad(Road r) {
		roads.add(r);
	}

	public void makeDevCardDeck() {
		for (int k = 0; k < 28; k++) {
			devCardDeck.add(new DevelopmentCard(DevelopmentCard.DevCardTypes.Knight, this));
		}
		for (int vp = 0; vp < 10; vp++) {
			devCardDeck.add(new DevelopmentCard(DevelopmentCard.DevCardTypes.VictoryPoint, this));
		}
		for (int rb = 0; rb < 4; rb++) {
			devCardDeck.add(new DevelopmentCard(DevelopmentCard.DevCardTypes.RoadBuilding, this));
		}
		for (int m = 0; m < 4; m++) {
			devCardDeck.add(new DevelopmentCard(DevelopmentCard.DevCardTypes.Monopoly, this));
		}
		for (int yop = 0; yop < 4; yop++) {
			devCardDeck.add(new DevelopmentCard(DevelopmentCard.DevCardTypes.YearOfPlenty, this));
		}

		ArrayList<DevelopmentCard> temp = new ArrayList<DevelopmentCard>();
		for (int i = 0; i < 50; i++) {
			int rand = (int) (Math.random() * 50);
			while (temp.contains(devCardDeck.get(rand))) {
				rand = (int) (Math.random() * 50);
			}
			temp.add(devCardDeck.get(rand));
		}
		devCardDeck = temp;
	}

	public DevelopmentCard takeDevCard() {
		DevelopmentCard dc = devCardDeck.get(0);
		devCardDeck.remove(0);
		return dc;
	}
	
	public int getBoardXLoc() {
		return boardXLoc;
	}
	
	public int getBoardWidth() {
		return boardWidth;
	}
	
	public int getBoardHeight() {
		return boardHeight;
	}
}