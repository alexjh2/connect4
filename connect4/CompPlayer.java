package connect4;

/**
 *  Computer player that acts like a human player but with strategy algorithms rather than inputs.
 *
 *  @author alexj0523
 *  @version May, 2025
 */
public class CompPlayer extends Player {

	/**
	 * level of the game that the player has selected
	 */
	public int gameLevel;

    /**
     * Constructor that initializes the computer player with the superclass's constructor and the given color.
     * If the player does not change the level, it is level 5.
     * @param c color of the computer player's chip
     */
    public CompPlayer(char c)
    {
    	super("Comp", c);
    	this.gameLevel = 5;

    }

    /**
     * Constructor that initializes the computer player with the superclass's constructor and the given color and level
     * @param c color of the computer player's chip
     * @param lv level of the computer player
     */
    public CompPlayer(char c, int lv)
    {
    	super("Comp", c);
    	this.gameLevel = lv;

    }

    public void hasTurn()
    {
        myTurn = true;
        Strategy ai = new Strategy(game, color, game.getCurrentPlayer());
//        System.out.println(ai.evaluateBoard());
        int posX = ai.bestMoveForComputer(gameLevel);

        move(posX);

    }

    public void move(int x) {

    	game.makeMove(x);
        myTurn = false;
    }

    /**
     * Sets the computer player's level to the given level
     * @param level selected level
     */
    public void setLevel(int level) {

    	gameLevel = level;
    }

    /**
     * returns the current level of the computer player
     * @return level of the computer player.
     */
    public int getLevel() {

    	return gameLevel;
    }



}
