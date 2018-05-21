import java.util.ArrayList;

public class DevelopmentCard extends Card {
	
	public enum DevCardTypes{
		Knight, VictoryPoint, RoadBuilding, Monopoly, YearOfPlenty
	}
	private DevCardTypes type;
	private Board board;
	
	public DevelopmentCard(DevCardTypes t, Board b) {
		type=t;
		board=b;
	}
	
	public void play(Player player) {
		if(type.equals(DevCardTypes.Knight)) {
			
		}
		
		else if(type.equals(DevCardTypes.VictoryPoint)) {
			player.addVp();
		}
		
		else if(type.equals(DevCardTypes.RoadBuilding)) {
			player.buildRoad(loc);
			player.buildRoad(loc);
		}
		
		else if(type.equals(DevCardTypes.Monopoly)) {
			ArrayList<ResourceCard> cards = new ArrayList<ResourceCard>();
			ArrayList<Player> players = board.getPlayers();
			TerrainHex.Resource res = declared();
			for(Player p:players) {
				ArrayList<ResourceCard> give = p.removeAllCardsOfType(res);
				for(ResourceCard r:give) {
					cards.add(r);
				}
			}
			player.addCards(cards);
		}
		
		else if(type.equals(DevCardTypes.YearOfPlenty)) {
			player.addCard(res);
			player.addCard(res);
		}

	}
	
	public DevCardTypes getType() {
		return type;
	}
}
