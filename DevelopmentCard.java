import java.util.ArrayList;

public class DevelopmentCard extends Card {
	
	public enum DevCardTypes{
		Knight, VictoryPoint, RoadBuilding, Monopoly, YearOfPlenty
	}
	private DevCardTypes type;
	
	public DevelopmentCard(DevCardTypes t) {
		type=t;
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
