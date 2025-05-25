package connect4;


import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JFrame;

/**
 *  Inherits JFrame and creates needed components of the game
 *
 *  @author alexj0523
 *  @version May, 2025
 */
public class Connect4 extends JFrame {

	private IntroPanel intro;
	private GamePanel gPanel;
	private Game game;



	/**
	 * Create GUI component: frame, IntroPanel, and GamePanel.
	 * Create new game
	 */
	public Connect4() {

		setTitle("Connect4");
		setVisible(true);
		setSize(670,550);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container c = getContentPane();

		game = new Game();
		intro = new IntroPanel(this);
		gPanel = new GamePanel(game);

		intro.setVisible(true);

		c.add(intro, BorderLayout.CENTER);

	}


	/**
	 * When pressed start in IntroPanel, switch to GamePanel
	 */
	public void changeToboard() {
		Container c = getContentPane(); //current screen info
		c.removeAll();
		c.add(gPanel, BorderLayout.CENTER );
        revalidate();
        repaint();

	}



	/**
	 * main method
	 * @param args args
	 */
	public static void main(String[] args) {

        Connect4 window = new Connect4();
        window.setVisible( true );
	}

}
