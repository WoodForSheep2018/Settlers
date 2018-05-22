
public class Location {
	private int row;
	private int col;
	private Player player;
	private Building building;
	
	public Location(int r, int c) {
		row = r;
		col = c;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public boolean hasSettlement(){
		return building.isASettlement();		
	}
	
	public boolean hasCity(){
		return building.isACity();
	}
	
	public void makeCity() {
		building.upgrade();
	}
	
	public void makeSettlement(Player p) {
		building = new Building(p);
		
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Building getBuilding() {
		return building;
	}
}
