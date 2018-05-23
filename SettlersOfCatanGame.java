import javax.swing.JFrame;

public class SettlersOfCatanGame extends JFrame {

	private static MainMenu menu;
	private static SettlersPanel game;

	public SettlersOfCatanGame(String title) {
		super(title);
	}

	public static void main(String[] args) {
		SettlersOfCatanGame frame = new SettlersOfCatanGame("Settlers of Catan");
		menu = new MainMenu();
		MainMenu.frame = frame;
		frame.getContentPane().add(menu);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void startGame() {
		this.getContentPane().removeAll();
		game = new SettlersPanel(menu);
		SettlersPanel.frame = this;
		this.getContentPane().add(game);
		this.pack();
	}

}
