import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public class Minesweeper extends GridGame {

	private int mineNum;
	private int labelNum = 0;
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
		// game.setPreferredSize(new Dimension(500, 500));
		
		mineNum = 15;
		
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
				mineButton[j][i].addMouseListener(this);
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
			while (pgrid[x][y] == 1){
				x = (int) (Math.random() * 10);
				y = (int) (Math.random() * 10);
			}
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

	private void mineMode() {
		if (!mine) {
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					mineButton[j][i].setActionCommand("z" + j + "" + i);
				}
			}
		} else {
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					mineButton[j][i].setActionCommand("h" + j + "" + i);
				}
			}
		}
		mine = !mine;
	}

	private void flood(int x, int y) {
		int check = countMines(x, y);
		mineButton[x][y].setEnabled(false);
		pgrid[x][y] = 5;

		mineButton[x][y].setBackground(Color.white);

		if (check != 0) { // base case
			mineButton[x][y].setText("" + check);
			return;
		} else {
			if (x - 1 >= 0 && y - 1 >= 0 && pgrid[x - 1][y - 1] != 5) {
				flood(x - 1, y - 1);
			}

			if (x + 1 < 10 && y - 1 >= 0 && pgrid[x + 1][y - 1] != 5) {
				flood(x + 1, y - 1);
			}

			if (y - 1 >= 0 && pgrid[x][y - 1] != 5) {
				flood(x, y - 1);
			}

			if (x - 1 >= 0 && pgrid[x - 1][y] != 5) {
				flood(x - 1, y);
			}

			if (x + 1 < 10 && pgrid[x + 1][y] != 5) {
				flood(x + 1, y);
			}

			if (x - 1 >= 0 && y + 1 < 10 && pgrid[x - 1][y + 1] != 5) {
				flood(x - 1, y + 1);
			}

			if (y + 1 < 10 && pgrid[x][y + 1] != 5) {
				flood(x, y + 1);
			}

			if (x + 1 < 10 && y + 1 < 10 && pgrid[x + 1][y + 1] != 5) {
				flood(x + 1, y + 1);
			}

		}
	}

	private int countMines(int x, int y) {
		int num = 0;
		int count = 0;

		if (x - 1 >= 0 && y - 1 >= 0 && pgrid[x - 1][y - 1] == 1) {
			num++;
		}

		if (y - 1 >= 0 && pgrid[x][y - 1] == 1) {
			num++;
		}

		if (x + 1 < 10 && y - 1 >= 0 && pgrid[x + 1][y - 1] == 1) {
			num++;
		}

		if (x - 1 >= 0 && pgrid[x - 1][y] == 1) {
			num++;
		}

		if (x + 1 < 10 && pgrid[x + 1][y] == 1) {
			num++;
		}

		if (x - 1 >= 0 && y + 1 < 10 && pgrid[x - 1][y + 1] == 1) {
			num++;
		}

		if (y + 1 < 10 && pgrid[x][y + 1] == 1) {
			num++;
		}

		if (x + 1 < 10 && y + 1 < 10 && pgrid[x + 1][y + 1] == 1) {
			num++;
		}
		return num;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);

		if (("" + e.getActionCommand()).charAt(0) == 'h') {
			int x = Integer.parseInt("" + ("" + e.getActionCommand()).charAt(1));
			int y = Integer.parseInt("" + ("" + e.getActionCommand()).charAt(2));

			if (pgrid[x][y] == 1) {
				System.out.println("mine at " + x + "," + y);
				mineButton[x][y].setEnabled(false);
				mineButton[x][y].setBackground(Color.red);
				JOptionPane.showMessageDialog(null, "You Lose");
				System.exit(0);
			} else {
				flood(x, y);
			}
		} else if (("" + e.getActionCommand()).charAt(0) == 'z') {
			int x = Integer.parseInt("" + ("" + e.getActionCommand()).charAt(1));
			int y = Integer.parseInt("" + ("" + e.getActionCommand()).charAt(2));
			if (mineButton[x][y].getName().equals("a") && labelNum < mineNum) {
				mineButton[x][y].setBackground(Color.green);
				mineButton[x][y].setName("b");
				labelNum++;
			} else if (mineButton[x][y].getName().equals("b")) {
				mineButton[x][y].setBackground(Color.lightGray);
				mineButton[x][y].setName("a");
				labelNum--;
			}
		}

		else if (e.getActionCommand().equals("enableMode")) {
			if (!mine)
				mode.setBackground(Color.green);
			else
				mode.setBackground(Color.lightGray);
			mineMode();
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		

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