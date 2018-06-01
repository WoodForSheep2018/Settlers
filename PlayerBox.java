import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class PlayerBox {
	private Player player;
	private int boxWidth;
	private int boxHeight;
	private int xLoc;
	private int yLoc;
	private int currentY;

	public PlayerBox(int panelWidth, int panelHeight, int x, int y, Player p) {
		player = p;
		boxWidth = panelWidth/4;
		boxHeight = panelHeight/2;
		xLoc = x;
		yLoc = y;
	}

	public void draw(Graphics g) {
		//box
		if(player.getTurn()) {
			g.setColor(player.getColor());
		}
		else {
			g.setColor(new Color(player.getColor().getRed(), player.getColor().getGreen(), player.getColor().getBlue(), 25));
		}
		g.fillRect(xLoc, yLoc, boxWidth, boxHeight);

		//name
		g.setColor(Color.BLACK);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		int nameWidth = g.getFontMetrics().stringWidth(player.getName());
		int nameAscent = g.getFontMetrics().getAscent();
		g.drawString(player.getName(), xLoc + boxWidth/2 - nameWidth/2, yLoc + nameAscent);

		//statsandcards
		g.setFont(new Font("TimesRoman", Font.PLAIN, 14));
		int textAscent = g.getFontMetrics().getAscent();
		g.drawString("Points: " + player.getPoints(), xLoc, yLoc + nameAscent + 2*textAscent);
		g.drawString("Resources: " + player.getRCards().size(), xLoc, yLoc + nameAscent + 4*textAscent);
		if(player.getTurn()) {
			g.drawString("Wood: " + player.findNumOfCardsOfType(TerrainHex.Resource.Wood), xLoc + boxWidth/4, yLoc + nameAscent + 6*textAscent);
			g.drawString("Brick: " + player.findNumOfCardsOfType(TerrainHex.Resource.Brick), xLoc + boxWidth/4, yLoc + nameAscent + 8*textAscent);
			g.drawString("Sheep: " + player.findNumOfCardsOfType(TerrainHex.Resource.Sheep), xLoc + boxWidth/4, yLoc + nameAscent + 10*textAscent);
			g.drawString("Wheat: " + player.findNumOfCardsOfType(TerrainHex.Resource.Wheat), xLoc + boxWidth/4, yLoc + nameAscent + 12*textAscent);
			g.drawString("Rock: " + player.findNumOfCardsOfType(TerrainHex.Resource.Rock), xLoc + boxWidth/4, yLoc + nameAscent + 14*textAscent);
		}
		g.drawString("Development Cards: " + player.getDCards().size(), xLoc, yLoc + nameAscent + 16*textAscent);
		if(player.getTurn()) {
			g.drawString("Knight: " + player.containsDevCard(DevelopmentCard.DevCardTypes.Knight), xLoc + boxWidth/4, yLoc + nameAscent + 18*textAscent);
			g.drawString("Road Building: " + player.containsDevCard(DevelopmentCard.DevCardTypes.RoadBuilding), xLoc + boxWidth/4, yLoc + nameAscent + 20*textAscent);
			g.drawString("Monopoly: " + player.containsDevCard(DevelopmentCard.DevCardTypes.Monopoly), xLoc + boxWidth/4, yLoc + nameAscent + 22*textAscent);
			g.drawString("Year of Plenty: " + player.containsDevCard(DevelopmentCard.DevCardTypes.YearOfPlenty), xLoc + boxWidth/4, yLoc + nameAscent + 24*textAscent);
			g.drawString("Victory Points: " + player.containsDevCard(DevelopmentCard.DevCardTypes.VictoryPoint), xLoc + boxWidth/4, yLoc + nameAscent + 26*textAscent);
			g.drawString("Longest Road: " + player.hasLongestRoad(), xLoc, yLoc + nameAscent + 28*textAscent);
			g.drawString("Largest Army: " + player.hasLargestArmy(), xLoc, yLoc + nameAscent + 30*textAscent);

		}

	}

}
