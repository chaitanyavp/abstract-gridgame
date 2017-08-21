import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Minesweeper extends GridGame {

	private int mineNum;
	private int labelNum = 0;
	private JButton mineButtons[][];
	private Panel buttonPanel;
	private Panel game;

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
		inst.setLayout(new BoxLayout(inst, BoxLayout.PAGE_AXIS));
		
		JLabel space1 = new JLabel("                           ");
		space1.setFont(new Font("Arial", Font.BOLD, 150));
		space1.setAlignmentX(Component.CENTER_ALIGNMENT);
		inst.add(space1);
		
		JLabel text[] = {new JLabel("Click on a tile to reveal what's underneath it."), 
				new JLabel("If you see a number, that is how many bombs are adjacent to it. If you click on a bomb, you lose."),
				new JLabel("You can right click a tile to flag it. Win by flagging all the bombs.")};
		for(JLabel sentence:text){
			sentence.setFont(new Font("Arial", Font.PLAIN, 25));
			sentence.setAlignmentX(Component.CENTER_ALIGNMENT);
			sentence.setForeground(Color.white);
			inst.add(sentence);
		}
		
		JLabel space2 = new JLabel("                           ");
		space2.setFont(new Font("Arial", Font.BOLD, 150));
		space2.setAlignmentX(Component.CENTER_ALIGNMENT);
		inst.add(space2);
		
        JButton back = new JButton("<- Back");
        back.addActionListener(this);
        back.setActionCommand("cb");
        back.setForeground(Color.white);
        back.setBackground(Color.darkGray);
        back.setFont(new Font("Impact", Font.PLAIN, 40));
        back.setAlignmentX(Component.CENTER_ALIGNMENT);
        inst.add(back);
		
		return inst;
	}

	@Override
	Panel createAndGetGame() {
		game = new Panel();
		game.setLayout(new FlowLayout());
		
		buttonPanel = new Panel();

		buttonPanel.setLayout(new GridLayout(10, 10));

		mineButtons = new JButton[10][10];

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				mineButtons[j][i] = new JButton(" ");
				mineButtons[j][i].setFont(new Font("Arial", Font.BOLD, 12));
				mineButtons[j][i].setPreferredSize(new Dimension(55, 55));
				mineButtons[j][i].addActionListener(this);
				mineButtons[j][i].addMouseListener(this);
				mineButtons[j][i].setActionCommand("h" + j + "" + i);
				mineButtons[j][i].setBackground(Color.darkGray);
				mineButtons[j][i].setName("unflagged");
				buttonPanel.add(mineButtons[j][i]);
			}
		}
		game.add(buttonPanel);
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
				if (pgrid[j][i] == 1 && !mineButtons[j][i].getName().equals("flagged")) {
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
		mineButtons[x][y].setEnabled(false);
		pgrid[x][y] = 5;

		mineButtons[x][y].setBackground(Color.white);

		if (check != 0) { // base case
			mineButtons[x][y].setText("" + check);
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
		
		if (e.getActionCommand().charAt(0) == 'm'){
			if (getDifficulty() == 0) {
				mineNum = 8;
			} else if (getDifficulty() == 1) {
				mineNum = 14;
			} else {
				mineNum = 18;
			}
			placeMine();
		}
		
		if (("" + e.getActionCommand()).charAt(0) == 'h') {
			int x = Integer.parseInt("" + ("" + e.getActionCommand()).charAt(1));
			int y = Integer.parseInt("" + ("" + e.getActionCommand()).charAt(2));
			
			if (pgrid[x][y] == 1) {
				System.out.println("mine at " + x + "," + y);
				mineButtons[x][y].setEnabled(false);
				mineButtons[x][y].setBackground(Color.red);
				JOptionPane.showMessageDialog(null, "You Lose");
				System.exit(0);
			} else {
				flood(x, y);
			}
		}
		else if(("" + e.getActionCommand()).equals("cb")){
			cdLayout.show(program, "menu");
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	    if (arg0.getButton() == MouseEvent.BUTTON3 && arg0.getComponent() instanceof JButton) {  
	        String actionCommand = ((JButton) arg0.getComponent()).getActionCommand();
	        
			int x = Integer.parseInt("" + actionCommand.charAt(1));
			int y = Integer.parseInt("" + actionCommand.charAt(2));
			if (mineButtons[x][y].getName().equals("unflagged") && labelNum < mineNum) {
				mineButtons[x][y].setBackground(Color.green);
				mineButtons[x][y].setName("flagged");
				labelNum++;
			} else if (mineButtons[x][y].getName().equals("flagged")) {
				mineButtons[x][y].setBackground(Color.darkGray);
				mineButtons[x][y].setName("unflagged");
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