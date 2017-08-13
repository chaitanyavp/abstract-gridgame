import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public class Minesweeper extends GridGame {

	private int mineNum = 15;;
	private int labelNum = 0;;
	private JButton mineButton[][];
	private boolean mine = false;;
	private JButton mode;

	public Minesweeper() {

	}

	public void init() {
		super.init();
	}

	protected Panel createAndGetMenu() {
		logoName = "minesweeper.jpg";
		return super.createAndGetMenu();
	}

	@Override
	Panel createAndGetInst() {
		Panel inst = new Panel();
		return inst;
	}

	@Override
	Panel createAndGetGame() {
		Panel game = new Panel();
		game.setLayout(new BoxLayout(game, BoxLayout.PAGE_AXIS));
		// resize (600, 600);
		placeMine();
		Panel buttonGrid = new Panel();
		mode = new JButton("Mine Mode");
		mode.addActionListener(this);
		mode.setActionCommand("enableMode");
		mode.setBackground(Color.lightGray);
		mode.setAlignmentX(Component.CENTER_ALIGNMENT);

		buttonGrid.setLayout(new GridLayout(10, 10));

		mineButton = new JButton[10][10];

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				mineButton[j][i] = new JButton(" ");
				mineButton[j][i].setFont(new Font("Arial", Font.BOLD, 12));
				mineButton[j][i].setPreferredSize(new Dimension(50, 50));
				mineButton[j][i].addActionListener(this);
				mineButton[j][i].setActionCommand("h" + j + "" + i);
				mineButton[j][i].setBackground(Color.lightGray);
				mineButton[j][i].setName("a");
				buttonGrid.add(mineButton[j][i]);
			}
		}
		game.add(mode);
		game.add(buttonGrid);
		return game;
	}

	private void placeMine() {
		for (int i = 0; i < mineNum; i++) {
			int x = (int) (Math.random() * 10);
			int y = (int) (Math.random() * 10);
			while (pgrid[x][y] == 1)
				x = (int) (Math.random() * 10);
			y = (int) (Math.random() * 10);
			pgrid[x][y] = 1;
		}
	}

	private boolean gameWon() {
		boolean win = true;

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (pgrid[j][i] == 1 && !mineButton[j][i].getName().equals("b")) {
					win = false;
					break;
				}
			}
		}
		return win;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}