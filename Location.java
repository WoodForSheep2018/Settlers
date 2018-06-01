import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Location {
	private int xLoc;
	private int yLoc;
	private ArrayList<TerrainHex.Resource> surroundingResources;
	private ArrayList<Integer> surroundingNums;
	private ArrayList<Hex> hexes;
	private Building building;
	private boolean isPort = false;
	private OceanHex.Port port;
	private int portSize;
	private Player player;


	public Location(int x, int y, int hexWidth) {
		xLoc = x;
		yLoc = y;
		portSize = hexWidth/6;
		surroundingResources = new ArrayList<TerrainHex.Resource>();
		surroundingNums = new ArrayList<Integer>();
		hexes = new ArrayList<Hex>();
		building = new Building(xLoc, yLoc, hexWidth);
	}

	public int getXLoc() {
		return xLoc;
	}

	public int getYLoc() {
		return yLoc;
	}
	
	public ArrayList<Hex> getHexes(){
		return hexes;
	}
	
	public ArrayList<Integer> getNums(){
		return surroundingNums;
	}

	public void assign(TerrainHex.Resource r, int i) {
		surroundingResources.add(r);
		surroundingNums.add(i);
	}

	public void assignHex(Hex h) {
		hexes.add(h);
	}

	public boolean hasSettlement(){
		return building.isASettlement();		
	}

	public boolean hasCity(){
		return building.isACity();
	}

	public void makeCity() {
		building.buildCity();
	}

	public void makeSettlement(Player p) {
		player = p;
		building.setOwner(player);
		building.buildSettlement();
	}

	public void makePort(OceanHex.Port p) {
		port = p;
		isPort = true;
		if(player != null)
			player.addPort(port);
	}
	
	public boolean isPort() {
		return isPort;
	}
	
	public OceanHex.Port getPort(){
		return port;
	}

	public void draw(Graphics g) {
		if(isPort) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillOval(xLoc - portSize/2, yLoc - portSize/2, portSize, portSize);
		}
		building.draw(g);
	}
	
	public Building getBuilding() {
		return building;
	}
	
	public boolean hasBuilding() {
		return building!=null;
	}
}
