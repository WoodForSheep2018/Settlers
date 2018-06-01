import java.awt.Color;
import java.util.ArrayList;

public class Player {
	private int setsLeft = 5;
	private int citiesLeft = 4;
	private int roadsLeft = 15;
	private int points = 0;
	private String name;
	private int playerNum;
	public static int numPlayers = 0;
	private ArrayList<ResourceCard> resourceCards = new ArrayList<ResourceCard>();
	private ArrayList<DevelopmentCard> devCards = new ArrayList<DevelopmentCard>();
	private ArrayList<Location> settlementLoc = new ArrayList<Location>();
	private ArrayList<Location> cityLoc = new ArrayList<Location>();
	private ArrayList<Location> roadLoc = new ArrayList<Location>();
	private ArrayList<OceanHex.Port> ports = new ArrayList<OceanHex.Port>();
	private Board board;
	private boolean longestRoad;
	private boolean largestArmy;
	private boolean isTurn;
	private Color playerColor;

	public Player(Board b, String str, Color c) {
		board = b;
		name = str;
		numPlayers++;
		playerNum = numPlayers;
		playerColor = c;
	}
	
	public void giveUpHalf() {
		int num = resourceCards.size()/2;
		while(num>0) {
			int rand = (int) (Math.random()*resourceCards.size());
			resourceCards.remove(rand);
			num--;
		}
	}
	
	public ResourceCard removeRandomCard() {
		int rand = (int) (Math.random()*resourceCards.size());
		ResourceCard rc = resourceCards.get(rand);
		resourceCards.remove(rand);
		return rc;
	}
	
	public void addPort(OceanHex.Port p) {
		ports.add(p);
	}
	
	public boolean hasPortOfType(OceanHex.Port type) {
		for(OceanHex.Port p:ports) {
			if(p.equals(type)) {
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<OceanHex.Port> getPorts(){
		return ports;
	}

	public void addCard(TerrainHex.Resource r) {
		resourceCards.add(new ResourceCard(r));
	}

	public void addCard(ResourceCard rc) {
		resourceCards.add(rc);
	}
	
	public ArrayList<DevelopmentCard> getDevCards(){
		return devCards;
	}
	
	public boolean containsDevCard(DevelopmentCard.DevCardTypes type) {
		for(DevelopmentCard dc:devCards) {
			if(dc.getType().equals(type))
				return true;
		}
		return false;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public void addVp() {
		points++;
	}
	public void removeVp() {
		points--;
	}

	public void giveLongestRoad() {
		longestRoad = true;
		addVp();
		addVp();
	}

	public void revokeLongestRoad() {
		longestRoad = false;
		removeVp();
		removeVp();
	}

	public void giveLargestArmy() {
		largestArmy = true;
		addVp();
		addVp();
	}

	public void revokeLargestArmy() {
		largestArmy = false;
		removeVp();
		removeVp();
	}

	public boolean hasLongestRoad() {
		return longestRoad;
	}
	
	public boolean hasLargestArmy() {
		return largestArmy;
	}
	
	public void endTurn() {

	}

	public void trade(Player trader, TerrainHex.Resource[] giveResources, TerrainHex.Resource[] receiveResources) {
		// make it so that other person can accept or reject

		ArrayList<ResourceCard> give = new ArrayList<ResourceCard>();
		ArrayList<ResourceCard> receive = new ArrayList<ResourceCard>();

		int giveB = 0;
		int giveWo = 0;
		int giveWh = 0;
		int giveS = 0;
		int giveR = 0;
		for (int g = 0; g < giveResources.length; g++) {
			if (giveResources[g].equals(TerrainHex.Resource.Brick))
				giveB++;
			else if (giveResources[g].equals(TerrainHex.Resource.Wood))
				giveWo++;
			else if (giveResources[g].equals(TerrainHex.Resource.Wheat))
				giveWh++;
			else if (giveResources[g].equals(TerrainHex.Resource.Sheep))
				giveS++;
			else if (giveResources[g].equals(TerrainHex.Resource.Rock))
				giveR++;
		}
		if (fulfills(giveB, giveWo, giveWh, giveS, giveR)) {
			ArrayList<ResourceCard> b = removeCardsOfType(TerrainHex.Resource.Brick, giveB);
			ArrayList<ResourceCard> wo = removeCardsOfType(TerrainHex.Resource.Wood, giveWo);
			ArrayList<ResourceCard> wh = removeCardsOfType(TerrainHex.Resource.Wheat, giveWh);
			ArrayList<ResourceCard> s = removeCardsOfType(TerrainHex.Resource.Sheep, giveS);
			ArrayList<ResourceCard> r = removeCardsOfType(TerrainHex.Resource.Rock, giveR);

			for (int i = 0; i < b.size(); i++) {
				give.add(b.get(i));
			}
			for (int i = 0; i < wo.size(); i++) {
				give.add(wo.get(i));
			}
			for (int i = 0; i < wh.size(); i++) {
				give.add(wh.get(i));
			}
			for (int i = 0; i < s.size(); i++) {
				give.add(s.get(i));
			}
			for (int i = 0; i < r.size(); i++) {
				give.add(r.get(i));
			}
		}

		int recB = 0;
		int recWo = 0;
		int recWh = 0;
		int recS = 0;
		int recR = 0;
		for (int g = 0; g < receiveResources.length; g++) {
			if (receiveResources[g].equals(TerrainHex.Resource.Brick))
				recB++;
			else if (receiveResources[g].equals(TerrainHex.Resource.Wood))
				recWo++;
			else if (receiveResources[g].equals(TerrainHex.Resource.Wheat))
				recWh++;
			else if (receiveResources[g].equals(TerrainHex.Resource.Sheep))
				recS++;
			else if (receiveResources[g].equals(TerrainHex.Resource.Rock))
				recR++;
		}
		if (trader.fulfills(recB, recWo, recWh, recS, recR)) {
			ArrayList<ResourceCard> b = trader.removeCardsOfType(TerrainHex.Resource.Brick, recB);
			ArrayList<ResourceCard> wo = trader.removeCardsOfType(TerrainHex.Resource.Wood, recWo);
			ArrayList<ResourceCard> wh = trader.removeCardsOfType(TerrainHex.Resource.Wheat, recWh);
			ArrayList<ResourceCard> s = trader.removeCardsOfType(TerrainHex.Resource.Sheep, recS);
			ArrayList<ResourceCard> r = trader.removeCardsOfType(TerrainHex.Resource.Rock, recR);

			for (int i = 0; i < b.size(); i++) {
				receive.add(b.get(i));
			}
			for (int i = 0; i < wo.size(); i++) {
				receive.add(wo.get(i));
			}
			for (int i = 0; i < wh.size(); i++) {
				receive.add(wh.get(i));
			}
			for (int i = 0; i < s.size(); i++) {
				receive.add(s.get(i));
			}
			for (int i = 0; i < r.size(); i++) {
				receive.add(r.get(i));
			}
		}

		for (int i = 0; i < receive.size(); i++) {
			addCard(receive.get(i));
		}
		for (int i = 0; i < give.size(); i++) {
			trader.addCard(give.get(i));
		}

	}

	public boolean buildRoad() {
		if (canBuildRoad()) {
			removeCardsOfType(TerrainHex.Resource.Brick, 1);
			removeCardsOfType(TerrainHex.Resource.Wood, 1);
			roadsLeft--;
			return true;
		}
		return false;
	}

	public boolean buildSettlement() {
		if (canBuildSettlement()) {
			removeCardsOfType(TerrainHex.Resource.Brick, 1);
			removeCardsOfType(TerrainHex.Resource.Wood, 1);
			removeCardsOfType(TerrainHex.Resource.Wheat, 1);
			removeCardsOfType(TerrainHex.Resource.Sheep, 1);
			setsLeft--;
			points++;
			return true;
		}
		return false;
	}

	public boolean buildCity() {
		if (canBuildCity()) {
			removeCardsOfType(TerrainHex.Resource.Rock, 3);
			removeCardsOfType(TerrainHex.Resource.Wheat, 2);
			citiesLeft--;
			points++;
			return true;
		}
		return false;
	}

	public void buyDevCard() {
		if (canBuyDevCard()) {
			removeCardsOfType(TerrainHex.Resource.Rock, 1);
			removeCardsOfType(TerrainHex.Resource.Wheat, 1);
			removeCardsOfType(TerrainHex.Resource.Sheep, 1);
			devCards.add(board.takeDevCard());
		}
	}

	public void playDevCard(DevelopmentCard.DevCardTypes type) {
		for (int i = 0; i < devCards.size(); i++) {
			if (devCards.get(i).getType().equals(type)) {
				devCards.get(i).play(this);
				devCards.remove(i);
				return;
			}
		}
	}

	public ArrayList<ResourceCard> removeCardsOfType(TerrainHex.Resource r, int num) {
		ArrayList<ResourceCard> cards = new ArrayList<ResourceCard>();
		for (int i = 0; i < resourceCards.size(); i++) {
			if (num > 0 && resourceCards.get(i).getResource().equals(r)) {
				cards.add(resourceCards.get(i));
				resourceCards.remove(i);
				i--;
				num--;
			}
		}
		return cards;
	}

	private boolean fulfills(int... r) {
		// brick, wood, wheat, sheep, rock

		int[] resources = new int[r.length];
		for (int i = 0; i < r.length; i++) {
			resources[i] = r[i];
		}
		if (!(findNumOfCardsOfType(TerrainHex.Resource.Brick) >= resources[0])) {
			return false;
		}
		if (!(findNumOfCardsOfType(TerrainHex.Resource.Wood) >= resources[1])) {
			return false;
		}
		if (!(findNumOfCardsOfType(TerrainHex.Resource.Wheat) >= resources[2])) {
			return false;
		}
		if (!(findNumOfCardsOfType(TerrainHex.Resource.Sheep) >= resources[3])) {
			return false;
		}
		if (!(findNumOfCardsOfType(TerrainHex.Resource.Rock) >= resources[4])) {
			return false;
		}
		return true;
	}

	public int findNumOfCardsOfType(TerrainHex.Resource r) {
		int num = 0;
		for(ResourceCard rc : resourceCards) {
			if(rc.getResource().equals(r)) {
				num++;
			}
		}
		return num;
	}

	public int getPlayerNumber() {
		return playerNum;
	}

	public String getName() {
		return name;
	}

	public int getPoints() {
		return points;
	}

	public ArrayList<ResourceCard> getRCards() {
		return resourceCards;
	}

	public ArrayList<DevelopmentCard> getDCards() {
		return devCards;
	}

	public boolean canBuildRoad() {
		return (fulfills(1, 1, 0, 0, 0) && roadsLeft>=1);
	}

	public boolean canBuildSettlement() {
		return (fulfills(1, 1, 1, 1, 0) && setsLeft>=1);
	}

	public boolean canBuildCity() {
		return (fulfills(0, 0, 2, 0, 3) && citiesLeft>=1);
	}

	public boolean canBuyDevCard() {
		return (fulfills(0, 0, 1, 1, 1));
	}

	public Color getColor() {
		return playerColor;
	}

	public void setTurn(boolean b) {
		isTurn = b;
	}

	public void changeSets(int n) {
		setsLeft += n;
	}

	public void changeRoads(int n) {
		roadsLeft += n;
	}

	public void changeCities(int n) {
		citiesLeft += n;
	}
	
	public boolean getTurn() {
		return isTurn;
	}
	
	public void incrementPoints() {
		points++;
	}

}
