
public class Location {
	private int row;
	private int col;
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
	
	public void addBuilding(Building b) {
		building = b;
	}
	
	public boolean hasBuilding() {
		return (building != null);
	}
	
	public Building getBuilding() {
		return building;
	}
	
}