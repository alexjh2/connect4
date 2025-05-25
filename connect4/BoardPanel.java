package connect4;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *  BoardPanel that contains needed graphics, buttons, and labels
 *
 *  @author alexj0523
 *  @version May, 2025
 */
public class BoardPanel extends JPanel
{
    private Game game;
    /**
     * JLabel component that reports current status of the players
     */
    public JLabel[] labelLoc;
    /**
     * JButton component that allows the player to choose level and type
     */
    public JButton[] buttonPlayer;
    /**
     * JButton component that allows the player to switch colors of the chip
     */
    public JButton[] buttonCoin;

    private final int WIDTH = 7, HEIGHT = 6; // board dimensions
    private final int CELLSIZE = 70;


    private Image backgroundImage = new ImageIcon("images/blueboard70.png").getImage();
    private ImageIcon[] coinImageIcon = new ImageIcon[2];
    private Image[]  coinImage = new Image[2];

    /**
     * Constructor that initializes GUI components in the board panel and starts the game.
     * @param game current game
     */
    public BoardPanel(Game game) {

        setPreferredSize( new Dimension( WIDTH * CELLSIZE, HEIGHT * CELLSIZE ) );
        setBackground( Color.WHITE );
        this.game = game;
        game.startGame(this);

        setLayout( null );

        labelLoc = new JLabel[2];
        buttonPlayer = new JButton[2];
        buttonCoin = new JButton[2];

        coinImageIcon[0] = new ImageIcon("images/yellow_coin.png");
        coinImageIcon[1] = new ImageIcon("images//red_coin.png");

        for (int i=0 ; i < 2; i++) {

            buttonCoin[i] = new JButton( coinImageIcon[i]);
            buttonCoin[i].setBounds(i*245,425,55,50);
            buttonCoin[i].setBorderPainted(false);
            buttonCoin[i].addActionListener(new MyListener());
            add(buttonCoin[i]);

            String temp = game.getPlayer(i).getName();
            if (temp.equals("Comp"))
                temp = temp + " Level: "+game.getPlayerLevel(i);
            buttonPlayer[i] = new JButton(temp);
            buttonPlayer[i].setHorizontalAlignment(JButton.CENTER);
            buttonPlayer[i].setFont(new Font("Arial", Font.PLAIN, 20));
            buttonPlayer[i].setBounds(i*245+50,430,195,50);
            buttonPlayer[i].addActionListener(new MyListener());
            add(buttonPlayer[i]);

            labelLoc[i] = new JLabel("Ready!");
            labelLoc[i].setHorizontalAlignment(JLabel.CENTER);
            labelLoc[i].setFont(new Font("Arial", Font.PLAIN, 20));
            labelLoc[i].setBounds(i*245,480,245,50);
            add(labelLoc[i]);

            coinImage[i] = coinImageIcon[i].getImage();

        }

    }

    /**
     * Updating the board panel with the current game status
     * @param game current game data
     */
    public void update( Game game )
    {
        this.game = game;
        repaint();
    }

    /**
     * Drawing each graphic components
     * @param g graphics
     */

    public void paintComponent( Graphics g )
    {
        super.paintComponent( g );
        for (int x=0 ; x < WIDTH ; x++)
            for (int y=0 ; y < HEIGHT ; y++)
                g.drawImage(backgroundImage, x*CELLSIZE , y*CELLSIZE , null); //drawing the squares of the grid

        for (int i = 0 ; i < HEIGHT; i++) {
            for (int j = 0 ; j < WIDTH ; j ++) {
                if (game.grid[j][i] == game.getPlayer(0).getColor())
                    g.drawImage(coinImage[0], 10+ j*CELLSIZE , 10+ i*CELLSIZE, null);
                else if (game.grid[j][i] == game.getPlayer(1).getColor())
                    g.drawImage(coinImage[1], 10+ j*CELLSIZE , 10+ i*CELLSIZE, null);
                //drawing the chips
            }
        }


    }

    /**
     * returns the location of the clicked position
     * @param x  x-component of the mouse
     * @param y y-component of the mouse
     * @return location of the mouse
     */
    public Location getPos( int x, int y )
    {
        return new Location( x / CELLSIZE, y / CELLSIZE );
    }

    /**
     *  class that processes each mouse event
     *
     *  @author alexj0523
     *  @version May, 2025
     */
    class MyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            for (int i = 0 ; i < 2 ; i++) {
                if(e.getSource() == buttonCoin[i] && ( game.isGameEnd () == true || game.getPlayNum() == 0)) {
                    game.changeColor();
                    coinImage[0] = coinImageIcon[1].getImage();
                    coinImage[1] = coinImageIcon[0].getImage();
                    ImageIcon temp = coinImageIcon[0];
                    coinImageIcon[0] = coinImageIcon[1];
                    coinImageIcon[1] = temp; // swapping colors of the chips of the players
                    buttonCoin[0].setIcon(coinImageIcon[0]);
                    buttonCoin[1].setIcon(coinImageIcon[1]);
                    repaint();
                }

                else if (e.getSource() == buttonPlayer[i]) {
                    game.changePlayerType(i);
                    String temp = game.getPlayer(i).getName();
                    if (temp.equals("Comp")) {
                        temp = temp + " Level: "+game.getPlayerLevel(i);
                    }
                    buttonPlayer[i].setText(temp); //changes player type from board panel

                    if (game.isGameEnd () == false ) {
                        game.getPlayer(game.getCurrentPlayer()).hasTurn();
                    }
                }
            }
        }
    }
}



