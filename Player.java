import java.util.ArrayList;

public class Player {
	private int setsLeft = 5;
	private int citiesLeft = 4;
	private int roadsLeft = 15;
	private int points;
	private ArrayList<ResourceCard> resourceCards;
	private ArrayList<DevelopmentCard> devCards;
	private ArrayList<Location> settlementLoc;
	private ArrayList<Location> cityLoc;
	private ArrayList<Location> roadLoc;
	private Board board;
	private boolean longestRoad;
	private boolean largestArmy;

	public Player(Board b) {
		board=b;
	}

	public void addCard(TerrainHex.Resource r) {
		resourceCards.add(new ResourceCard(r));
	}
	
	public void addVp() {
		points++;
	}
	
	public void giveLongestRoad() {
		longestRoad = true;
	}
	public void takeLongestRoad() {
		longestRoad = false;
	}

	public void giveLargestArmy() {
		largestArmy = true;
	}
	public void takeLargestArmy() {
		largestArmy = false;
	}
	
	public void buildRoad() {
		if (canBuildRoad()) {
			removeCardOfType(TerrainHex.Resource.Brick);
			removeCardOfType(TerrainHex.Resource.Wood);
			roadsLeft--;
			putDownRoad(Location);
		}
	}

	public void buildSettlement() {
		if (canBuildSettlement()) {
			removeCardOfType(TerrainHex.Resource.Brick);
			removeCardOfType(TerrainHex.Resource.Wood);
			removeCardOfType(TerrainHex.Resource.Wheat);
			removeCardOfType(TerrainHex.Resource.Sheep);
			setsLeft--;
			putDownSettlement(Location);
			points++;
		}
	}

	public void buildCity() {
		if (canBuildCity()) {
			removeCardOfType(TerrainHex.Resource.Rock);
			removeCardOfType(TerrainHex.Resource.Rock);
			removeCardOfType(TerrainHex.Resource.Rock);
			removeCardOfType(TerrainHex.Resource.Wheat);
			removeCardOfType(TerrainHex.Resource.Wheat);
			citiesLeft--;
			putDownCity(Location);
			points++;
		}
	}

	public void buyDevCard() {
		if (canBuyDevCard()) {
			removeCardOfType(TerrainHex.Resource.Rock);
			removeCardOfType(TerrainHex.Resource.Wheat);
			removeCardOfType(TerrainHex.Resource.Sheep);
			devCards.add(board.takeDevCard());
		}
	}
	
	public void playDevCard(DevelopmentCard.DevCardTypes type) {
		for(int i=0; i<devCards.size(); i++) {
			if(devCards.get(i).getType().equals(type)) {
				devCards.get(i).play(this);
				devCards.remove(i);
				return;
			}
		}
	}

	
	public boolean canBuildRoad() {
		return (findNumOfCardsOfType(TerrainHex.Resource.Brick) == 1
				&& findNumOfCardsOfType(TerrainHex.Resource.Wood) == 1);
	}

	public boolean canBuildSettlement() {
		return (findNumOfCardsOfType(TerrainHex.Resource.Brick) == 1
				&& findNumOfCardsOfType(TerrainHex.Resource.Wood) == 1
				&& findNumOfCardsOfType(TerrainHex.Resource.Wheat) == 1
				&& findNumOfCardsOfType(TerrainHex.Resource.Sheep) == 1);
	}

	public boolean canBuildCity() {
		return (findNumOfCardsOfType(TerrainHex.Resource.Rock) == 3
				&& findNumOfCardsOfType(TerrainHex.Resource.Wheat) == 2);
	}

	public boolean canBuyDevCard() {
		return (findNumOfCardsOfType(TerrainHex.Resource.Rock) == 1
				&& findNumOfCardsOfType(TerrainHex.Resource.Wheat) == 1
				&& findNumOfCardsOfType(TerrainHex.Resource.Sheep) == 1);
	}

	
	private int findNumOfCardsOfType(TerrainHex.Resource r) {
		int num = 0;
		for (ResourceCard rc : resourceCards) {
			if (rc.getResource().equals(r)) {
				num++;
			}
		}
		return num;
	}

	private void removeCardOfType(TerrainHex.Resource r) {
		for (int i = 0; i < resourceCards.size(); i++) {
			if (resourceCards.get(i).getResource().equals(r)) {
				resourceCards.remove(i);
				return;
			}
		}
	}
}


//public class Player {
//
//	ArrayList<Card> cards;
//	private int points;
//
//	public Player() {
//		cards = new ArrayList<Card>();
//	}
//
//	public void add(Hex.Resource r) {
//		cards.add(new ResourceCard(r));
//	}
//	
//	private boolean pay(int... resources) {
//		if(fulfils(resources)) {
//			remove(resources);
//			return true;
//		}
//		return false;
//	}
//	private boolean fulfils(int... resources) {
//		int[] r = new int[resources.length];
//		for(int i = 0;i < r.length;i++) r[i] = 0;
//		for(Card rc:cards) {
//			if(((ResourceCard) rc).isAResource()) {
//				for(int i = 0;i < r.length;i++) {
//					if(((ResourceCard) rc).isResource(i)) r[i]++;
//				}
//			}
//		}
//		for(int i = 0;i < r.length;i++) if(r[i] < resources[i]) return false;
//		return true;
//	}
//	private void remove(int... r) {
//		for(int i = cards.size()-1;i >= 0;i--) {
//			if(((ResourceCard) rc).isAResource()) {
//				for(int j = 0;j < r.length;j++) {
//					if(r[j] > 0 && ((ResourceCard) rc).isResource(j)) {
//						cards.remove(i);
//						r[j]--;
//					}
//				}
//			}
//		}
//	}
//	
//	public boolean createBuilding() {
//		return pay(1,1,0,1,1);
//	}
//	public boolean upgradeBuilding() {
//		return pay(0,0,3,2,0);
//	}
//	public boolean createRoad() {
//		return pay(1,1,0,0,0);
//	}
//	public boolean getDevelopment() {
//		return pay(0,0,1,1,1);
//	}
//	
//}
