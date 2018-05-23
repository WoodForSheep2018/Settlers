import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Location {
	private int row;
	private int col;
	private ArrayList<TerrainHex.Resource> surroundingResources;
	private ArrayList<Integer> surroundingNums;
	private Building building;
	private boolean isPort = false;
	private OceanHex.Port port;
	private int portSize;

	public Location(int r, int c, int hexWidth) {
		row = r;
		col = c;
		portSize = hexWidth/6;
		surroundingResources = new ArrayList<TerrainHex.Resource>();
		surroundingNums = new ArrayList<Integer>();
		building = new Building(r, c, hexWidth);
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
		building.buildCity();;
	}

	public void makeSettlement() {
		building.buildSettlement();
	}
	
	public void makePort(OceanHex.Port p) {
		port = p;
		isPort = true;
	}
	
	public void draw(Graphics g) {
		if(isPort) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillOval(row - portSize/2, col - portSize/2, portSize, portSize);
		}
		building.draw(g);
	}
	
}
