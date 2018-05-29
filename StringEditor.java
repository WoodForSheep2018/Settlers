import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;

public class StringEditor extends Button {

	private PlayerOptions po;

	private String name;

	public StringEditor(int x, int y, int w, int h, String str, Color c) {
		super(x, y, w, h, str, c);
		System.out.println("CREATE STRING EDITOR");
		name = "";
	}

	public void setPlayer(PlayerOptions player) {
		po = player;
	}
	public PlayerOptions getPO() {
		return po;
	}

	public void click() {
		name = JOptionPane.showInputDialog("Enter your name here");
		po.setName(name);
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(x,y+height,width,2);
		g.setFont(new Font("TimesNewRoman",Font.PLAIN,16));
		if(name.length() == 0) {
			g.setColor(new Color(0,0,0,100));
			g.drawString(string,x,y+height*3/4);
		} else {
			g.drawString(name,x,y+height*3/4);
		}
	}

}
