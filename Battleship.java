import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JLabel;

public class Battleship extends GridGame {

	private int egrid[][] = new int[10][10]; // Grid that enemy interacts with
	// 0 means the tile is empty, 1 means there's a ship, 2 means that tile has
	// already been hit

	private JButton playerBoard[][]; // buttons representing pgrid
	private JLabel enemyBoard[][]; // labels representing egrid

	private JLabel status; // shows status of game
	private JLabel score; // shows score

	private int shipsToPlace = 3; // For player's first three moves to be ship
									// placement
	private int phits = 10; // number of hits required for player to win
	private int ehits = 10; // number of hits required for enemy to win

	public Battleship() {
		// TODO Auto-generated constructor stub
	}

	public void init() {
		super.init();
		placeEnemyShips();
		addPowerUps();
		addEnemyPowerUps();
	}

	@Override
	Panel createAndGetInst() {
		// TODO Auto-generated method stub
		return new Panel();
	}

	@Override
	Panel createAndGetGame() {
		// Player Grid
		Panel playerBoardPanel = new Panel();
		playerBoardPanel.setLayout(new GridLayout(10, 10));

		playerBoard = new JButton[10][10];

		char letter = 'A';
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				playerBoard[j][i] = new JButton("" + letter + (j + 1));
				playerBoard[j][i].setPreferredSize(new Dimension(55, 50));
				playerBoard[j][i].setFont(new Font("Arial", Font.PLAIN, 10));
				playerBoard[j][i].addActionListener(this);
				playerBoard[j][i].setActionCommand("p" + j + "" + i);
				playerBoard[j][i].setForeground(Color.lightGray);
				playerBoard[j][i].setBackground(Color.darkGray);
				playerBoard[j][i].addMouseListener(this);
				playerBoard[j][i].setName(j + "" + i + "t");
				playerBoardPanel.add(playerBoard[j][i]);
			}
			letter++;
		}
		// Enemy grid

		Panel enemyBoardPanel = new Panel();
		enemyBoardPanel.setLayout(new GridLayout(10, 10));

		enemyBoard = new JLabel[10][10];

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				// Label1[j][i] = new JLabel(""+letter1+(j+1));
				enemyBoard[j][i] = new JLabel(createImageIcon("tile.jpg"));
				enemyBoard[j][i].setPreferredSize(new Dimension(50, 50));
				enemyBoard[j][i].setFont(new Font("Arial", Font.PLAIN, 14));
				enemyBoard[j][i].setForeground(new Color(130, 130, 130));
				enemyBoardPanel.add(enemyBoard[j][i]);
			}
		}

		// Creates screen for game

		Panel game = new Panel(); // panel to hold game itself
		game.setLayout(new BorderLayout());
		game.setBackground(Color.black);
		setBackground(Color.black);

		JLabel img = new JLabel("    "); // for spacing
		img.setForeground(Color.white);

		status = new JLabel("             Click to place the first ship");
		status.setForeground(Color.white);
		status.setFont(new Font("Arial", Font.BOLD, 25));

		score = new JLabel(
				"                   You have 10 hits left.                                                   Enemy has 10 hits left.");
		score.setForeground(Color.white);
		score.setFont(new Font("Arial", Font.BOLD, 25));

		game.add(status, BorderLayout.NORTH);
		game.add(playerBoardPanel, BorderLayout.WEST);
		game.add(img, BorderLayout.CENTER);
		game.add(enemyBoardPanel, BorderLayout.EAST);
		game.add(score, BorderLayout.SOUTH);

		return game;
	}

	private void placePlayerShip(int x, int y) { // place the player's ship at
													// (x,y). Places ships in
													// descending order of size.
		shipsToPlace--;
	}

	private void placeEnemyShips() { // places ships in the enemy's behalf

		// Places the ship of length 5
		int s51 = (int) (Math.random() * 6); // mostly random numbers
		int s52 = (int) (Math.random() * 10);
		for (int i = 0; i < 5; i++)
			pgrid[s51 + i][s52] = 1;

		// Places the ship of length 3
		int s31 = (int) (Math.random() * 10);
		int s32 = (int) (Math.random() * 8);
		while (pgrid[s31][s32] == 1 || pgrid[s31][s32 + 1] == 1 // prevents
																// overlaps
				|| pgrid[s31][s32 + 2] == 1) {
			s31 = (int) (Math.random() * 10);
			s32 = (int) (Math.random() * 8);
		}
		pgrid[s31][s32] = 1;
		pgrid[s31][s32 + 1] = 1;
		pgrid[s31][s32 + 2] = 1;

		// Places the ship of length 1
		int s21 = (int) (Math.random() * 10);
		int s22 = (int) (Math.random() * 9);
		while (pgrid[s21][s22] == 1 || pgrid[s21][s22 + 1] == 1) { // prevents
																	// overlaps
			s21 = (int) (Math.random() * 10);
			s22 = (int) (Math.random() * 9);
		}
		pgrid[s21][s22] = 1;
		pgrid[s21][s22 + 1] = 1;

	}

	private void addPowerUps() { // adds the powerUps on the user's grid
		for (int i = 0; i < 4; i++) {

			int x = (int) (Math.random() * 10);
			int y = (int) (Math.random() * 10);

			while (pgrid[x][y] != 0) { // prevents overlaps
				x = (int) (Math.random() * 10);
				y = (int) (Math.random() * 10);
			}

			pgrid[x][y] = 2;
		}
	}

	private void addEnemyPowerUps() {// adds powerups on enemy grid
		for (int i = 0; i < 4; i++) {
			int x = (int) (Math.random() * 10);
			int y = (int) (Math.random() * 10);

			while (egrid[x][y] != 0) { // prevents overlaps
				x = (int) (Math.random() * 10);
				y = (int) (Math.random() * 10);
			}

			egrid[x][y] = 2;
		}
	}

	private void enemyMove() {// enemy makes a move

	}

	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);

		if (e.getActionCommand().charAt(0) == 'p') { // To process player moves.
			int x = Integer.parseInt("" + e.getActionCommand().charAt(1));
			int y = Integer.parseInt("" + e.getActionCommand().charAt(2));

			System.out.println(x + ", " + y);

			if (shipsToPlace > 0) {
				placePlayerShip(x, y);
			} else {
				boolean goAgain = false;

				if (pgrid[x][y] == 1) { // If player lands a hit
					playerBoard[x][y].setBackground(Color.red);
					status.setText("             You got a hit");
					phits--;
				} else if (pgrid[x][y] == 2) { // If player hits a powerup
					playerBoard[x][y].setBackground(Color.magenta);
					goAgain = true;
				} else { // If player misses
					status.setText("             You missed");
					playerBoard[x][y].setBackground(Color.lightGray);
				}

				playerBoard[x][y].setEnabled(false);

				if (!goAgain) // If the player did get a powerup
					enemyMove();

				score.setText(
						"You have " + phits + " hits left.                                                   Enemy has "
								+ ehits + " hits left.");

			}
		}
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
