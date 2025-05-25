package connect4;

/**
 *  Parent class of CompPlayer and HumanPlayer, defines common variables and methods
 *
 *  @author alexj0523
 *  @version May, 2025
 */
public class Player {

	/**
	 * Player's name
	 */
	public String name;
	/**
	 * Player's chip's color
	 */
	public char color;
	/**
	 * true if it's the player's turn false if not
	 */
	public boolean myTurn;
	/**
	 * current game
	 */
	public Game game;

	/**
	 * Constructor that initializes variables
	 * @param name player's name
	 * @param c player's chip color
	 */
	public Player(String name, char c) {
		this.name = name;
		this.myTurn = false;
		this.color = c;
	}

	/**
	 * passes the game data
	 * @param g current game
	 */
	public void init(Game g) {
    	this.game = g;
	}


	/**
	 * stub method used by the players
	 */
	public void hasTurn()
	{

	}

	/**
	 * Place a description of your method here.
	 */
	public void makeMove() {

	}

    /**
     * Method made to change the myTurn variable when the player has made their move
     * @param col the column the player placed its chip
     */
    public void move(int col) {
    	myTurn = false;
    }

	/**
	 * returns the name of the player
	 * @return name of the player
	 */
	public String getName() {
		return name;
	}



    /**
     * sets the color of the chip to the given color
     * @param c color of the chip
     */
    public void setColor(char c) {
    	this.color = c;
    }

	/**
	 * returns the color of the player's chip
	 * @return color of the chip
	 */
	public char getColor() {
		return color;
	}



}
