import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

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
	private int[] longShipCoords;

	private int phits = 10; // number of hits required for player to win
	private int ehits = 10; // number of hits required for enemy to win

	private boolean enemyLastShotHit = false; // If the enemy's last move was a
												// hit.
	private int enemyLastHitX; // coordinates of enemy's last hit
	private int enemyLastHitY;
	private int checkDirection = 5;// Tells enemy which direction to check once
									// a hit is made.
	private int conflictX = 0; // If enemy lands a hit in the middle of a ship
	private int conflictY = 0;

	private JLabel insPic;
	private JLabel insText;
	private int insCont = -1;

	public Battleship() {
		// TODO Auto-generated constructor stub
	}

	public void init() {
		super.init();
		placeEnemyShips();
		addPowerUps();
		addEnemyPowerUps();
		disableRestrictedTiles(0, 0);
	}

	@Override
	Panel createAndGetInst() {

		Panel ins = new Panel();
		ins.setLayout(new BoxLayout(ins, BoxLayout.PAGE_AXIS));
		insPic = new JLabel(createImageIcon("ship1.jpg"));
		insPic.setAlignmentX(Component.CENTER_ALIGNMENT);

		insText = new JLabel("When you first start the game, you must choose where to place your ships.");
		insText.setFont(new Font("Arial", Font.PLAIN, 30));
		insText.setForeground(Color.white);
		insText.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel spc1 = new JLabel("                           ");
		spc1.setFont(new Font("Arial", Font.BOLD, 50));
		spc1.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel spc2 = new JLabel("                           ");
		spc2.setFont(new Font("Arial", Font.BOLD, 50));
		spc2.setAlignmentX(Component.CENTER_ALIGNMENT);

		JButton cont = new JButton("Next ->");
		cont.addActionListener(this);
		cont.setActionCommand("cu");
		cont.setForeground(Color.white);
		cont.setBackground(Color.darkGray);
		cont.setFont(new Font("Impact", Font.PLAIN, 40));
		cont.setAlignmentX(Component.CENTER_ALIGNMENT);

		JButton back = new JButton("<- Back");
		back.addActionListener(this);
		back.setActionCommand("cb");
		back.setForeground(Color.white);
		back.setBackground(Color.darkGray);
		back.setFont(new Font("Impact", Font.PLAIN, 40));
		back.setAlignmentX(Component.CENTER_ALIGNMENT);

		ins.add(insText);
		ins.add(spc1);
		ins.add(insPic);
		ins.add(spc2);
		ins.add(cont);
		ins.add(back);

		return ins;
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
				// enemyBoard[j][i] = new JLabel(""+letter1+(j+1));
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
		if (shipsToPlace == 3) {
			longShipCoords = new int[2];
			longShipCoords[0] = x;
			longShipCoords[1] = y;

			// creates long ship at x,y on egrid
			for (int i = 0; i < 5; i++)
				egrid[x + i][y] = 1;

			// makes ship tiles blue on enemy's board
			for (int counter = 0; counter < 5; counter++)
				enemyBoard[x + counter][y].setIcon(createImageIcon("tileship.jpg"));

		}

		else if (shipsToPlace == 2) {
			// create medium ship at x,y on egrid

			egrid[x][y] = 1;
			egrid[x][y + 1] = 1;
			egrid[x][y + 2] = 1;

			// make ship tiles blue on enemy's board
			enemyBoard[x][y].setIcon(createImageIcon("tileship.jpg"));
			enemyBoard[x][y + 1].setIcon(createImageIcon("tileship.jpg"));
			enemyBoard[x][y + 2].setIcon(createImageIcon("tileship.jpg"));
		}

		else if (shipsToPlace == 1) {
			// create small ship at x,y on egrid
			egrid[x][y] = 1;
			egrid[x][y + 1] = 1;

			// make ship tiles blue on enemy's board
			enemyBoard[x][y].setIcon(createImageIcon("tileship.jpg"));
			enemyBoard[x][y + 1].setIcon(createImageIcon("tileship.jpg"));

			status.setText("             Click to attack");
		}
		shipsToPlace--;
		enableAllTiles();
		disableRestrictedTiles(x, y);
	}

	private void disableRestrictedTiles(int x, int y) { // Prevents player from
														// placing ships out of
														// bounds.
		if (shipsToPlace == 3) {
			for (int i = 6; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					playerBoard[i][j].setEnabled(false);
					playerBoard[i][j].setBackground(Color.lightGray);
					playerBoard[i][j].setName((j) + "" + (i) + "f");
				}

			}
		} else if (shipsToPlace == 2) {
			// disables long ship tiles
			for (int i = 0; i < 3; i++) {
				if (y - i >= 0) {
					for (int j = 0; j < 5; j++) {
						playerBoard[x + j][y - i].setEnabled(false);
						playerBoard[x + j][y - i].setBackground(Color.lightGray);
						playerBoard[x + j][y - i].setName((x + j) + "" + (y - i) + "f");
					}
				}
			}

			// disables restricted tiles
			for (int i = 8; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					playerBoard[j][i].setEnabled(false);
					playerBoard[j][i].setBackground(Color.lightGray);
					playerBoard[j][i].setName(j + "" + i + "f");
				}
			}

			status.setText("             Click to place the second ship");
		} else if (shipsToPlace == 1) {
			// disable medium ship tiles
			if (y - 1 >= 0) {
				playerBoard[x][y - 1].setEnabled(false);
				playerBoard[x][y - 1].setBackground(Color.lightGray);
				playerBoard[x][y - 1].setName(x + "" + (y - 1) + "f");
			}
			playerBoard[x][y].setEnabled(false);
			playerBoard[x][y + 1].setEnabled(false);
			playerBoard[x][y + 2].setEnabled(false);

			playerBoard[x][y].setBackground(Color.lightGray);
			playerBoard[x][y + 1].setBackground(Color.lightGray);
			playerBoard[x][y + 2].setBackground(Color.lightGray);

			playerBoard[x][y].setName(x + "" + y + "f");
			playerBoard[x][y + 1].setName(x + "" + (y + 1) + "f");
			playerBoard[x][y + 2].setName(x + "" + (y + 2) + "f");

			// disables long ship tiles
			for (int counter1 = 0; counter1 < 2; counter1++) {
				if (longShipCoords[1] - counter1 >= 0) {

					for (int longcount = 0; longcount < 5; longcount++) {

						playerBoard[longShipCoords[0] + longcount][longShipCoords[1] - counter1].setEnabled(false);
						playerBoard[longShipCoords[0] + longcount][longShipCoords[1] - counter1]
								.setBackground(Color.lightGray);
						playerBoard[longShipCoords[0] + longcount][longShipCoords[1] - counter1]
								.setName((longShipCoords[0] + longcount) + "" + (longShipCoords[1] - counter1) + "f");
					}
				}
			}

			// disable restricted tiles
			for (int i = 9; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					playerBoard[j][i].setEnabled(false);
					playerBoard[j][i].setBackground(Color.lightGray);
					playerBoard[j][i].setName(j + "" + i + "f");
				}
			}

			status.setText("             Click to place the third ship");
		} else {
			enableAllTiles();
		}
	}

	private void enableAllTiles() { // Enables all buttons on the player's board
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				playerBoard[j][i].setEnabled(true);
				playerBoard[j][i].setName(j + "" + i + "t");
				playerBoard[j][i].setForeground(Color.lightGray);
				playerBoard[j][i].setBackground(Color.darkGray);
			}
		}
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

		boolean goAgain = false;

		int x = (int) (Math.random() * 10);
		int y = (int) (Math.random() * 10);

		// Make sure the enemy hits in a checkerboard pattern.
		if (x % 2 == 0) { // if x is even, y should be odd
			if (y % 2 == 0) {
				if (y + 1 < 10)
					y++;
				else
					y--;
			}
		} else { // if x is odd, y should be even
			if (y % 2 == 1)
				if (y + 1 < 10)
					y++;
				else
					y--;
		}

		if (enemyLastShotHit && getDifficulty() > 0) { // /if last shot was a
														// hit, enemy
			// will try to
			// check around it only run if difficulty is
			// normal or above
			x = enemyLastHitX;
			y = enemyLastHitY;

			if (getDifficulty() != 2) {

				if (conflictX == 4) {
					conflictX = 0;
				} else if (conflictY == 4) {
					conflictY = 0;
					// System.out.println("conflict has been reset ");
				}

				if (conflictX != 0) {
					if (conflictX == 1)
						x++;
					else {
						x--;
					}
					enemyLastHitX = x;
					enemyLastHitY = y;
					conflictX = 4;
				} else if (conflictY == 1) {
					// System.out.println("checking down");
					conflictY = 4;
					y++;
				}

				if (x + 1 < 10 && x - 1 >= 0) {// branch for multiple hits
					if (egrid[x + 1][y] == 1 && egrid[x - 1][y] == 1) {
						// System.out.println("conflict at "+ex+","+ey);
						enemyLastHitX = x;
						enemyLastHitY = y;
						if (x + 2 < 10) {
							if (egrid[x + 2][y] == 1) {
								conflictX = 1;
								x--;
							} else {
								conflictX = 2;
								x++;
							}
						} else {
							conflictX = 2;
							x++;
						}

					}
				}

				if (y + 1 < 10 && y - 1 >= 0) {// branch for multiple vertical
												// hits
					if (egrid[x][y + 1] == 1 && egrid[x][y - 1] == 1) {
						// System.out.println("conflict at "+ex+","+ey);
						enemyLastHitX = x;
						enemyLastHitY = y;
						conflictY = 1;
						y--;
					}
				}

				if (conflictX == 0 && conflictY == 0) {

					if (checkDirection == 0) // checks in each direction
						x++;
					else if (checkDirection == 1)
						x--;
					else if (checkDirection == 2)
						y++;
					else if (checkDirection == 3) {
						y--;
						enemyLastShotHit = false;
					}

					if (x < 0) // making sure coordinates stay within bounds
						x = 0;
					else if (x > 9)
						x = 9;
					if (y < 0)
						y = 0;
					else if (y > 9)
						y = 9;
				}
			} // medium diff

			else if (getDifficulty() == 2) {// hard difficulty
				// gives enemy unfair advantage

				if (conflictX == 4)
					conflictX = 0;
				if (conflictY == 4)
					conflictY = 0;

				if (conflictX != 0) {
					if (conflictX == 1)
						x++;
					else {
						x--;
					}
					enemyLastHitX = x;
					enemyLastHitY = y;
					conflictX = 4;
				} else if (conflictY == 1) {
					conflictY = 4;
					y++;
				}

				if (x + 1 < 10 && x - 1 >= 0) {// branch for multiple hits
					if (egrid[x + 1][y] == 1 && egrid[x - 1][y] == 1) {
						// System.out.println("conflict at "+ex+","+ey);
						enemyLastHitX = x;
						enemyLastHitY = y;
						if (x + 2 < 10) {
							if (egrid[x + 2][y] == 1) {
								conflictX = 1;
								x--;
							} else {
								conflictX = 2;
								x++;
							}
						} else {
							conflictX = 2;
							x++;
						}

					}
				}

				if (y + 1 < 10 && y - 1 >= 0) {// branch for multiple vertical
												// hits
					if (egrid[x][y + 1] == 1 && egrid[x][y - 1] == 1) {
						// System.out.println("conflict at "+ex+","+ey);
						enemyLastHitX = x;
						enemyLastHitY = y;
						conflictY = 1;
						y--;
					}
				}

				if (conflictX == 0 && conflictY == 0) {
					if (y + 1 < 10) {
						if (egrid[x][y + 1] == 1)// if y+1 is a hit and it is
													// within bounds, it will go
													// there
							y++;
					}

					if (y - 1 >= 0) {
						if (egrid[x][y - 1] == 1)
							y--;
					}
					if (x + 1 < 10) {
						if (egrid[x + 1][y] == 1)
							x++;
					}
					if (x - 1 >= 0) {
						if (egrid[x - 1][y] == 1) {
							x--;
							enemyLastShotHit = false;
						}
					}
				}

			} // hard diff
			checkDirection++;
		} // lasthit

		int tryCount = 0;
		while (egrid[x][y] == 3) { // makes sure enemy doesn't hit same spot
									// twice
			x = (int) (Math.random() * 10);
			y = (int) (Math.random() * 10);
			if (tryCount < 20 && getDifficulty() > 0) {
				if (x % 2 == 0) { // if x is even, y should be odd
					if (y % 2 == 0) { // for checkerboard pattern
						if (y + 1 < 10)
							y++;
						else
							y--;
					}
				} else { // if x is odd, y should be even
					if (y % 2 == 1)
						if (y + 1 < 10)
							y++;
						else
							y--;
				}
			}
			tryCount++;
		}

		if (egrid[x][y] == 1) { // hit branch
			enemyBoard[x][y].setIcon(createImageIcon("tilehit.jpg"));
			status.setText("             Enemy got a hit");
			enemyLastShotHit = true; // starts ai
			checkDirection = 0;
			if (conflictX == 0 && conflictY == 0) {
				enemyLastHitX = x; // stores x and y values
				enemyLastHitY = y;
			}
			ehits--; // keeps score
		}

		else if (egrid[x][y] == 2) { // poweup branch
			enemyBoard[x][y].setIcon(createImageIcon("tilepower.jpg"));
			status.setText("             Enemy got a PowerUp");
			goAgain = true;
		}

		else
			// miss branch
			enemyBoard[x][y].setIcon(createImageIcon("tilemiss.jpg"));

		egrid[x][y] = 3;

		checkEnemyWin();

		if (goAgain)
			enemyMove();

	}

	private void checkPlayerWin() {
		if (phits == 0) {
			JOptionPane.showMessageDialog(null, "You Win");
			reset();
		}
	}

	private void checkEnemyWin() {
		if (ehits == 0) {
			JOptionPane.showMessageDialog(null, "Enemy Wins");
			reset();
		}
	}

	private void reset() {
		System.exit(0);
	}

	private void insProgression() {// changes what is displayed in the
		// instructions screen
		switch (insCont) {
		case 0: {
			insText.setText("When you first start the game, you must choose where to place your ships.");
			break;
		}
		case 1: {
			insText.setText("These are the ships:");
			break;
		}
		case 2: {
			insText.setText("Some areas are disabled to prevent overlaps and to ensure the ships stay on the board.");
			// "<html><div align=\"center\">Some areas are disabled to prevent
			// overlaps <br/> and to make sure the ships don't go off the
			// board.</div></html>"
			break;
		}
		case 3: {
			insText.setText("When placed, ships will appear on the grid on the right side.");
			break;
		}
		case 4: {
			insText.setText("The enemy will try to attack these ships.");
			break;
		}
		case 5: {
			insText.setText("You must attack the Enemy's ships, which will be on the left grid after ship selection.");
			break;
		}
		case 6: {
			insText.setText("PowerUps allow you to go again. The enemy also can also get PowerUps");
			break;
		}
		default: {
			insText.setText("When you first start the game, you must choose where to place your ships.");
			insCont = 0;

			cdLayout.show(program, "menu");
		}
		}
		insPic.setIcon(createImageIcon("ship" + (insCont + 1) + ".jpg"));
	}

	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);

		if (("" + e.getActionCommand()).charAt(0) == 'c') { // for instructions
			if (("" + e.getActionCommand()).charAt(1) == 'u')
				insCont++;
			else
				insCont--;
			insProgression();
		}

		else if (e.getActionCommand().charAt(0) == 'p') { // To process player
															// moves.
			int x = Integer.parseInt("" + e.getActionCommand().charAt(1));
			int y = Integer.parseInt("" + e.getActionCommand().charAt(2));

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

				checkPlayerWin();

				if (!goAgain) // If the player did get a powerup
					enemyMove();

				score.setText(
						"You have " + phits + " hits left.                                                   Enemy has "
								+ ehits + " hits left.");

			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) { // turns tiles yellow during ship
		// selection
		if (shipsToPlace > 0) {
			char isTileEnabled = ("" + e.getComponent()).charAt(22);

			if (isTileEnabled == 't') {

				int x = Integer.parseInt(("" + e.getComponent()).charAt(20) + "");
				int y = Integer.parseInt(("" + e.getComponent()).charAt(21) + "");
				playerBoard[x][y].setBackground(Color.yellow);

				if (shipsToPlace == 3) {
					for (int i = 1; i < 5; i++) {
						if ((x + i) < 10)
							playerBoard[x + i][y].setBackground(Color.yellow);
					}
				} else if (shipsToPlace == 2) {
					if (y + 1 < 10)
						playerBoard[x][y + 1].setBackground(Color.yellow);
					if (y + 2 < 10)
						playerBoard[x][y + 2].setBackground(Color.yellow);
				} else if (shipsToPlace == 1) {
					if (y + 1 < 10)
						playerBoard[x][y + 1].setBackground(Color.yellow);
				}
			}
		}
	}

	public void mouseExited(MouseEvent e) {// changes tiles back to normal after
		// being turned yellow

		if (shipsToPlace > 0) {
			int x = Integer.parseInt(("" + e.getComponent()).charAt(20) + "");
			int y = Integer.parseInt(("" + e.getComponent()).charAt(21) + "");

			char isTileEnabled = ("" + e.getComponent()).charAt(22);

			if (isTileEnabled == 't')
				playerBoard[x][y].setBackground(Color.darkGray);

			if (shipsToPlace == 3) {

				for (int i = 1; i < 5; i++) {

					if ((x + i) < 10) {

						isTileEnabled = playerBoard[x + i][y].getName().charAt(2);

						if (isTileEnabled == 't')
							playerBoard[x + i][y].setBackground(Color.darkGray);
						else
							playerBoard[x + i][y].setBackground(Color.lightGray);

					}
				}

			} else if (shipsToPlace == 2) {
				if (y + 1 < 10) {// first tile

					isTileEnabled = playerBoard[x][y + 1].getName().charAt(2);

					if (isTileEnabled == 't')
						playerBoard[x][y + 1].setBackground(Color.darkGray);
					else
						playerBoard[x][y + 1].setBackground(Color.lightGray);

				}

				if (y + 2 < 10) {// second tile
					isTileEnabled = playerBoard[x][y + 2].getName().charAt(2);

					if (isTileEnabled == 't')
						playerBoard[x][y + 2].setBackground(Color.darkGray);
					else
						playerBoard[x][y + 2].setBackground(Color.lightGray);
				}

			} else if (shipsToPlace == 1) {

				if (y + 1 < 10) {// first tile

					isTileEnabled = playerBoard[x][y + 1].getName().charAt(2);

					if (isTileEnabled == 't')
						playerBoard[x][y + 1].setBackground(Color.darkGray);
					else
						playerBoard[x][y + 1].setBackground(Color.lightGray);

				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
