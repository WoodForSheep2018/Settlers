import java.util.ArrayList;

public class DevelopmentCard extends Card {
	
	public enum DevCardTypes{
		Knight, VictoryPoint, RoadBuilding, Monopoly, YearOfPlenty
	}
	private DevCardTypes type;
	
	public DevelopmentCard(DevCardTypes t) {
		type=t;
	}
	
	public void play() {
		if(type.equals(DevCardTypes.Knight)) {
			
		}
		else if(type.equals(DevCardTypes.VictoryPoint)) {
			
		}
		else if(type.equals(DevCardTypes.RoadBuilding)) {
			
		}
		else if(type.equals(DevCardTypes.Monopoly)) {
	
		}
		else if(type.equals(DevCardTypes.YearOfPlenty)) {
			
		}

	}
	
	public DevCardTypes getType() {
		return type;
	}
}
