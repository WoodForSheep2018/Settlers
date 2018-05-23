import java.awt.Graphics;
import java.util.ArrayList;

public class Location {
	private int row;
	private int col;
	private ArrayList<TerrainHex.Resource> surroundingResources;
	private ArrayList<Integer> surroundingNums;
	private Building building;

	public Location(int r, int c, int hexSize) {
		row = r;
		col = c;
		surroundingResources = new ArrayList<TerrainHex.Resource>();
		surroundingNums = new ArrayList<Integer>();
		building = new Building(r, c, hexSize);
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public void assign(TerrainHex.Resource r, int i) {
		surroundingResources.add(r);
		surroundingNums.add(i);
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

	public void makeSettlement() {
		building.upgrade();
	}
	
	public void draw(Graphics g) {
		building.draw(g);
	}
	
}
