import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public class Minesweeper extends GridGame {

	private int mineNum;
	private int labelNum = 0;
	private JButton mineButton[][];

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
				mineButton[j][i].setName("unflagged");
				buttonGrid.add(mineButton[j][i]);
			}
		}
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

	private boolean isGameWon() {
		boolean win = true;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (pgrid[j][i] == 1 && !mineButton[j][i].getName().equals("flagged")) {
					win = false;
					break;
				}
			}
		}
		return win;
	}
	
	private void checkWin(){
		if(isGameWon()){
			JOptionPane.showMessageDialog(null, "You Win");
			System.exit(0);
		}
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
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	    if (arg0.getButton() == MouseEvent.BUTTON3 && arg0.getComponent() instanceof JButton) {  
	        String actionCommand = ((JButton) arg0.getComponent()).getActionCommand();
	        
			int x = Integer.parseInt("" + actionCommand.charAt(1));
			int y = Integer.parseInt("" + actionCommand.charAt(2));
			if (mineButton[x][y].getName().equals("unflagged") && labelNum < mineNum) {
				mineButton[x][y].setBackground(Color.green);
				mineButton[x][y].setName("flagged");
				labelNum++;
			} else if (mineButton[x][y].getName().equals("flagged")) {
				mineButton[x][y].setBackground(Color.lightGray);
				mineButton[x][y].setName("unflagged");
				labelNum--;
			}
	    }
	    checkWin();
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