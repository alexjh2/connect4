package connect4;

import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 *  GamePanel  that makes control panel and board panel
 *
 *  @author alexj0523
 *  @version May, 2025
 */
public class GamePanel extends JPanel {
	private ControlPanel cPanel;
	private BoardPanel bPanel;

	/**
	 * Constructor that makes board panel and control panel and also passes game status to them
	 * @param game this game
	 */
	public GamePanel(Game game) {
        setPreferredSize( new Dimension( 595,420 ) );

		this.bPanel = new BoardPanel(game);
		this.cPanel = new ControlPanel(game);

		setLayout( new BorderLayout());
		this.add(cPanel, BorderLayout.WEST);
		this.add(bPanel, BorderLayout.CENTER);
	}

	public void paintComponent(Graphics g) {
        super.paintComponent( g );
	}
}
