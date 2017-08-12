import javax.swing.*;
import java.awt.*;
import java.applet.*;
import java.awt.event.*;

public class GridGame extends Applet implements ActionListener, MouseListener{
	
	private int[] dimensions = {1200, 600};
	
	protected CardLayout cdLayout = new CardLayout();// to progress through the screens
	protected Panel program;// to hold the entire program
	protected int pgrid[][] = new int[10][10]; // Grid that player interacts with
	
	public GridGame() {
		
		System.out.println("constructor");
		
		program = new Panel(); // to hold entire program, using cdLayout
		program.setLayout(cdLayout);
		
		// Main menu Panel
		Panel menu = new Panel();
		menu.setLayout(new BoxLayout(menu, BoxLayout.PAGE_AXIS));
		
		JLabel title = new JLabel(createImageIcon("logo.jpg"));
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		menu.add(title);
		
		JButton menuButton[] = new JButton[3];
		menuButton[0] = new JButton("Start"); //to start game
		menuButton[1] = new JButton("Instructions"); //to go to instruction screen
		menuButton[2] = new JButton("Exit"); //to exit
		
		for(int i = 0; i < 3; i++){
			menuButton[i].addActionListener(this);
		}
		
		menuButton[0].setActionCommand("n0");
		menuButton[1].setActionCommand("n1");
		menuButton[2].setActionCommand("n2");
		
		for(int i = 0; i < 3; i++){
			menuButton[i].setBackground(Color.darkGray);
			menuButton[i].setForeground(Color.white);
			menuButton[i].setFont(new Font("Impact", Font.PLAIN, 40));
			menuButton[i].setAlignmentX(Component.CENTER_ALIGNMENT);
			menu.add(menuButton[i]);
		}
		
		program.add("1", menu);
		add(program);
	}
	
	public void init(){
		resize(dimensions[0], dimensions[1]);
		
	}
	
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
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
