package connect4;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

/**
 *  Class that inherits Grid Class that contains game data, controls the game, and processes mouse event.
 *
 *  @author alexj0523
 *  @version May, 2025
 */
public class Game extends Grid implements MouseListener{

	private BoardPanel board;
	private Player[] players;
	private int cPlayer, nplay;
	private boolean bGameEnd;

	/**
	 * number of column of the board
	 */
	static final int WIDTH = 7;
	/**
	 * number of row of the board
	 */
	static final int HEIGHT = 6;

	private int[] moves;

	/**
	 * Constructor that initializes variables
	 */
	public Game() {
		super(WIDTH,HEIGHT);
		moves = new int[WIDTH*HEIGHT];
		nplay = 0;
        cPlayer = 0;
//		this.board = board;
//		this.players = players;
//		board.update(this);
//        players[0].init(this,'b');
//        players[1].init(this,'r');
//        cPlayer = 0;
        bGameEnd = true;
//        players[cPlayer].hasTurn();
	}

	/**
	 * Creates a new game when the play button is clicked in ControlPanel
	 */
	public void newGame() {

		clearGrid();
		cPlayer =0;
		nplay = 0;
        bGameEnd = false;
		board.update(this);
		players[cPlayer].hasTurn();

	}

	/**
	 * Sets the game for the player
	 * @param board boardpanel
	 */
	public void startGame(BoardPanel board) {

		this.board = board;
		this.players = new Player[2];
        players[0] = new HumanPlayer('b');
        players[1] = new CompPlayer('r');

        players[0].init(this);
        players[1].init(this);

        board.addMouseListener( this );
        newGame();

	}


	private void changeLevel(int num) {

		Player p = players[num];
		if (p instanceof CompPlayer) { // check if the player is computer player
			int lv = ((CompPlayer) p).getLevel();
			lv = (lv + 2) % 6;
			((CompPlayer) p).setLevel(lv);
		}
	}

	/**
	 * returns level of the AI player
	 * @param num number of the player (0 or 1)
	 * @return level of the AI player
	 */
	public int getPlayerLevel(int num) {
		int lv = ((CompPlayer) players[num]).getLevel();
		return lv;
	}

	/**
	 * Displays the result of the game and message "Please choose New Game or Quit"
	 * @param color color of the player that just placed a chip
	 */
	public void endGame(char color) {

		bGameEnd = true;
		String msg;
		if (color == ' ')
			msg = "Draw!\n";
		else
			msg = "Players"+(cPlayer+1)+" has won!\n";
		msg += "Please choose New Game or Quit";
		JOptionPane.showMessageDialog(board, msg , "Notification", JOptionPane.INFORMATION_MESSAGE);
	}


    /**
     * Method called by CompPlayer and HumanPlayer. Drops the chip in the given column,
     * updates the board, and displays the latest move of the player in boardPanel.
     * @param posX x coordinate of the position
     */
    public void makeMove( int posX )
    {
    	int posY = drop(players[cPlayer].getColor(),posX);
        board.update( this );

        board.labelLoc[cPlayer].setText("Player"+(cPlayer+1)+": "+(posX+1)+","+(posY+1));


        if ( posY == -1) {
        	board.labelLoc[cPlayer].setText("<html>The column is full!<BR>\n Make another move</html>");
        	players[cPlayer].hasTurn(); //lets the player to make a valid move
        }

        else {
            moves[nplay] = posX;

            if (isWin(posX, posY)== players[cPlayer].getColor()) { //if the player won
            	board.labelLoc[cPlayer].setText("Player"+(cPlayer+1)+" won!!");
            	endGame(players[cPlayer].getColor());
            }
            else if (isFull()) //if the grid is full
            	endGame(' ');
            else { //next player gets to make a move
                nplay++;
                hasMoved();
            }

        }
    }

    private void hasMoved()
    {
        cPlayer = ( cPlayer + 1 ) % 2;
        Player p = players[cPlayer];
        // System.out.println (cPlayer); for debugging
        p.hasTurn();

    }

    /**
     * Undoing the latest move(s)
     */
    public void moveBack()
    {
    	if ( nplay < 1)
    		return;
    	if (players[(cPlayer+1)%2] instanceof CompPlayer) { //if next player is AI player
    		nplay--;
    		undoDrop(moves[nplay]);
    	}
    	nplay--; //if next player is AI, undo twice, if next player is human, undo once
    	undoDrop(moves[nplay]);
    	board.update( this );

    }

    /**
     * returns the player of the given number
     * @param num number 0 or 1
     * @return player within the players array
     */
    public Player getPlayer(int num) {
    	return players[num];
    }

    /**
     * Changes player type. Human - Comp Level 1 - Comp Level 3 - Comp Level 5 in a loop.
     * @param num player number(0 or 1)
     */
    public void changePlayerType(int num) {
    	//System.out.println ("call");

    	if ( players[num] instanceof HumanPlayer )
    		players[num] = new CompPlayer(players[num].getColor(), 1);
    	else {
    		int lv = ((CompPlayer) players[num]).getLevel();
    		if ( lv == 5)
    			players[num] = new HumanPlayer(players[num].getColor());
    		else
    			changeLevel(num);
    	}

        players[num].init(this);



    }
    /**
     * changes color of the chip when the chip button is clicked.
     */
    public void changeColor() {
    	char temp = players[0].getColor();
    	players[0].setColor(players[1].getColor());
    	players[1].setColor(temp);

    }

    /**
     * returns whether or not the game has ended
     * @return true if game ended, false if not
     */
    public boolean isGameEnd () {
    	return bGameEnd;
    }

    /**
     * returns the number of the player
     * @return 0 or 1
     */
    public int getCurrentPlayer () {

    	return cPlayer;
    }

    public int getPlayNum() {

    	return nplay;
    }

    public void mouseReleased( MouseEvent e ) {

    }

    public void mouseClicked( MouseEvent e )
    {
        if ( !(players[cPlayer] instanceof HumanPlayer) || bGameEnd )
            return;


        Location pos = board.getPos( e.getX(), e.getY() );
        int x = pos.getRow();
        int y = pos.getCol();
        if (x<=6 && x>=0 && y>=0 && y <= 5) {
            board.labelLoc[cPlayer].setText("Previous move was"+x+","+y);
            players[cPlayer].move(x);
        }

    }

    public void mousePressed( MouseEvent e )
    {
    }

    public void mouseEntered( MouseEvent e )
    {
    }

    public void mouseExited( MouseEvent e )
    {
    }

}
