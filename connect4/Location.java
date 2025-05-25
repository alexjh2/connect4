package connect4;
/**
 *  Location class is a class that contains info about the chip's location.
 *
 *  @author alexj0523
 *  @version May, 2025
 */
public class Location
{
    private int x, y;

    /**
     * Constructor that initializes the variables to the given coordinates
     * @param x x coordinate of the location
     * @param y y coordinate of the location
     */
    public Location( int x, int y )
    {
        this.x = x;
        this.y = y;
    }

    /**
     * returns x coordinate of the location
     * @return x coordinate of the location
     */
    public int getRow()
    {
        return x;
    }

    /**
     * returns y coordinate of the location
     * @return y coordinate of the location
     */
    public int getCol()
    {
        return y;
    }
}
