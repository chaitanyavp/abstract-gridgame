import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JLabel;

public class Battleship extends GridGame{
	
	int egrid[][] = new int[10][10]; // Grid that enemy interacts with
    // 0 means the tile is empty, 1 means there's a ship, 2 means that tile has
    // already been hit
	
	JButton playerBoard[][]; // buttons representing pgrid
	JLabel enemyBoard[][]; // labels representing egrid
	
    JLabel status; // shows status of game
    JLabel score; // shows score
	
	public Battleship() {
		// TODO Auto-generated constructor stub
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
                        playerBoard[j][i].setActionCommand(j + "" + i);
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

        JLabel img = new JLabel("    "); //for spacing
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
