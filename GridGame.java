import javax.swing.*;
import java.awt.*;
import java.applet.*;
import java.awt.event.*;

public abstract class GridGame extends Applet implements ActionListener, MouseListener {

	private int[] dimensions = { 1200, 600 };
	protected String logoName = "logo.jpg";
	protected CardLayout cdLayout = new CardLayout();// to progress through the
	// screens
	protected Panel program;// to hold the entire program

	protected int instructionsPage;
	private int difficulty = 0;

	protected int pgrid[][] = new int[10][10]; // Grid that player interacts
												// with

	public GridGame() {
		program = new Panel(); // to hold entire program, using cdLayout
		program.setLayout(cdLayout);
		program.setBackground(Color.black);

		program.add("menu", createAndGetMenu());
		program.add("diff", createAndGetDiff());
		program.add("inst", createAndGetInst());
		program.add("game", createAndGetGame());
		add(program);
	}

	public void init() {
		resize(dimensions[0], dimensions[1]);
		setBackground(Color.black);
	}

	protected Panel createAndGetMenu() {
		Panel menu = new Panel();
		menu.setLayout(new BoxLayout(menu, BoxLayout.PAGE_AXIS));

		JLabel title = new JLabel(createImageIcon(logoName));
		title.setAlignmentX(Component.CENTER_ALIGNMENT);

		menu.add(title);

		JButton menuButton[] = new JButton[3];
		menuButton[0] = new JButton("Start"); // to start game
		menuButton[1] = new JButton("Instructions"); // to go to instruction
														// screen /
		menuButton[2] = new JButton("Exit"); // to exit

		for (int i = 0; i < 3; i++) {
			menuButton[i].addActionListener(this);
		}

		menuButton[0].setActionCommand("n0");
		menuButton[1].setActionCommand("n1");
		menuButton[2].setActionCommand("n2");

		for (int i = 0; i < 3; i++) {
			menuButton[i].setBackground(Color.darkGray);
			menuButton[i].setForeground(Color.white);
			menuButton[i].setFont(new Font("Impact", Font.PLAIN, 40));
			menuButton[i].setAlignmentX(Component.CENTER_ALIGNMENT);
			menu.add(menuButton[i]);
		}
		return menu;
	}

	private Panel createAndGetDiff() {
		Panel difficultyChoice = new Panel();
		difficultyChoice.setLayout(new BoxLayout(difficultyChoice, BoxLayout.PAGE_AXIS));

		JLabel difficultyChoiceIns = new JLabel("                  Please choose a difficulty:               ");
		difficultyChoiceIns.setFont(new Font("Arial", Font.BOLD, 50));
		difficultyChoiceIns.setForeground(Color.white);
		difficultyChoiceIns.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel difficultyChoiceSpace = new JLabel("                           ");
		difficultyChoiceSpace.setFont(new Font("Arial", Font.BOLD, 150));
		difficultyChoiceSpace.setAlignmentX(Component.CENTER_ALIGNMENT);

		difficultyChoice.add(difficultyChoiceIns);
		difficultyChoice.add(difficultyChoiceSpace);

		JButton buttons[] = new JButton[3];
		buttons[0] = new JButton("Easy");
		buttons[1] = new JButton("Medium");
		buttons[2] = new JButton("Hard");

		for (int i = 0; i < 3; i++) {
			buttons[i].addActionListener(this);
		}

		buttons[0].setActionCommand("m0");
		buttons[1].setActionCommand("m1");
		buttons[2].setActionCommand("m2");

		for (int i = 0; i < 3; i++) {
			buttons[i].setBackground(Color.darkGray);
			buttons[i].setForeground(Color.white);
			buttons[i].setFont(new Font("Impact", Font.PLAIN, 40));
			buttons[i].setAlignmentX(Component.CENTER_ALIGNMENT);
			difficultyChoice.add(buttons[i]);
		}

		return difficultyChoice;
	}

	abstract Panel createAndGetInst();

	abstract Panel createAndGetGame();

	public static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = GridGame.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// To Process commands from main menu
		if (e.getActionCommand().charAt(0) == 'n') {
			int menuCommand = Integer.parseInt("" + e.getActionCommand().charAt(1));
			switch (menuCommand) {
			case 0: { // Start game by going to difficulty seleciton.
				cdLayout.show(program, "diff");
				break;
			}
			case 1: { // GOTO Instructions
				instructionsPage = 0;
				cdLayout.show(program, "inst");
				break;
			}
			case 2: { // Exit game
				System.exit(0);
				break;
			}
			}
		} else if (e.getActionCommand().charAt(0) == 'm') { // To process
															// difficulty
															// choices
			// difficulty, after
			// main menu
			difficulty = Integer.parseInt("" + e.getActionCommand().charAt(1));
			cdLayout.show(program, "game");
		}

	}

	protected int getDifficulty() {
		return difficulty;
	}
}
