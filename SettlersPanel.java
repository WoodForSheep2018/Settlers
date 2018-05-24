import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class SettlersPanel extends JPanel {
	private final int PANEL_WIDTH = 800;
	private final int PANEL_HEIGHT = 600;
	private Board board;
	private ArrayList<Player> players = new ArrayList<Player>();
	public static SettlersOfCatanGame frame;
	
	public SettlersPanel() {
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		board = new Board(PANEL_WIDTH, PANEL_HEIGHT);
	}
	
	public SettlersPanel(MainMenu settings) { //TODO
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		board = new Board(PANEL_WIDTH,PANEL_HEIGHT);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		board.draw(g);
	}

}
