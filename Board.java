import java.awt.Color;
import java.awt.Graphics;

public class Board {
	private Hex[][] hexArr = new Hex[7][];
	private int boardWidth,boardHeight,boardXLoc,hexWidth,hexHeight,gap;

	public Board(int pw, int ph) {
		for(int i = 0;i < hexArr.length;i++) {
			hexArr[i] = new Hex[i+3];
			hexArr[6-i] = new Hex[i+3];
		}
		for(int r = 0; r < hexArr.length; r++) {
			for(int c = 0; c < hexArr[r].length; c++) {
				hexArr[r][c] = new Hex();
			}
		}
		
		boardWidth = pw/2;
		boardHeight = (3*ph)/4;
		boardXLoc = pw/4;
		hexWidth = boardWidth/7;
		hexHeight = (int)(2*(hexWidth/Math.sqrt(3)));
		gap = boardWidth - 7*hexWidth;
		
		setTileLocs();
	}
		
	public void setTileLocs() {
		int y = hexHeight/2;
		int x = boardXLoc;
		for(int r = 0; r < hexArr.length; r++) {
			if(hexArr[r].length == 4) {
				x = boardXLoc + gap/2 + 2*hexWidth;
			}
			else if(hexArr[r].length == 5) {
				x = boardXLoc + gap/2 + (3*hexWidth)/2;
			}
			else if(hexArr[r].length == 6) {
				x = boardXLoc + gap/2 + hexWidth;
			}
			else if(hexArr[r].length == 7) {
				x = boardXLoc + gap/2 + hexWidth/2;
			}
			for(int c = 0; c < hexArr[r].length; c++) {
				if(r == 0 || r == hexArr.length - 1 || c == 0 || c == hexArr[r].length - 1) {
					hexArr[r][c] = new OceanHex(OceanHex.Port.Sheep, x, y, hexWidth);
				}
				else {
					hexArr[r][c] = new TerrainHex(TerrainHex.Resource.Wheat, x, y, hexWidth, 5);
				}
				x += hexWidth;
			}
			y += (3*hexHeight)/4;
		}
	}
	
	public void draw(Graphics g) {
		g.setColor(new Color(153,255,255));
		g.fillRect(boardXLoc, 0, boardWidth, boardHeight);
		
		
		for(int r = 0; r < hexArr.length; r++) {
			for(int c = 0; c < hexArr[r].length; c++) {
				hexArr[r][c].draw(g);
			}
		}
	}

}

