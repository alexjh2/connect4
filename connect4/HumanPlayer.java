package connect4;


/**
 *  Human player class that is a subclass of the player class.
 *
 *  @author alexj0523
 *  @version May, 2025
 */
public class HumanPlayer extends Player
{


    /**
     * Constructor that sets the human player using the superclass's constructor
     * @param c color
     */
    public HumanPlayer(char c)
    {
    	super("Human", c);

    }

    public void hasTurn()
    {
        myTurn = true;
    }

    // Called automatically when the mouse button is released
    public void move(int x) {
    	game.makeMove(x);
        myTurn = false;
    }


}
