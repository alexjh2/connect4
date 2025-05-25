package connect4;


/**
 *  Stores game data and defines game operations that happen on the game board(not board panel)
 *
 *  @author alexj0523
 *  @version May, 2025
 */
public class Grid {

	/**
	 * 2d array grid (x,y components)
	 */
	public char[][] grid;

	/**
	 * Constructor that creates 2d array grid with the size x x y
	 * @param x width(number of columns)
	 * @param y height(number of rows)
	 */
	public Grid(int x, int y) {
		grid = new char[x][y];
		clearGrid();
	}

	private void fill(char c) {
		for (int i = 0 ; i < grid.length ; i++)
			for (int j = 0 ; j < grid[0].length ; j++)
				grid[i][j] = c;
	}

	/**
	 * Method that finds the lowest position of given column and fills in that position with c(color).
	 * @param c color of the chip
	 * @param posX x coordinate of the position (Column)
	 * @return y coordinate of the valid position (the lowest remaining). If full, return -1.
	 */
	public int drop(char c, int posX) {
		if (posX != -1) {
			for (int posY = grid[0].length-1 ; posY >= 0 ; posY--) {
				if (grid[posX][posY] == ' ') {
					grid[posX][posY] = c;
					return posY;
				}
			}
		}
		return -1; //if the given column is full
	}

	/**
	 * Resets grid
	 */
	public void clearGrid() {

		fill(' ');

	}

	/**
	 * returns whether or not the entire grid is full
	 * @return true if the grid is full, false if there's empty space(s)
	 */
	public boolean isFull() {
		for (int posX = 0 ; posX< grid.length ; posX++) {
			if ( grid[posX][0] == ' ')
				return false;

		}
		return true;
	}

    /**
     * Returns if the player can place a chip in the given column
     * @param posX given column
     * @return true if there's empty space in the column, false if not
     */
    public boolean isLegal(int posX) {
    	return posX >= 0 &&
    			posX < grid.length &&
    			grid [posX][ 0 ] == ' ';
    }

    /**
     * Removing the chip in the highest position  that is occupied of the given column
     * @param posX given column
     */
    public void undoDrop(int posX) {
    	int posY = 0;
    	while (posY < grid[0].length-1 && grid[posX][posY] == ' ')
    		posY++;

    	grid[posX][posY] = ' ';

    }


	/**
	 * Method that determines if there's a winner or not by checking all 8 directions and returns the color of the winner
	 * @param posX current chip's x component
	 * @param posY current chip's y component
	 * @return color of the winner or blank if there's no winner
	 */
	public char isWin(int posX, int posY) {

		char c = grid[posX][posY];
		boolean win = false;

		if ( (checkConnect(c, posX,posY-1, 0, -1) + checkConnect(c, posX,posY+1, 0, 1)) >= 3 ) //checks up&down
			win = true;
		else if ( (checkConnect(c, posX+1,posY, 1, 0) + checkConnect (c, posX-1,posY, -1, 0)) >= 3 ) //checks left&right
			win = true;
		else if ( (checkConnect (c,posX+1,posY-1, 1, -1) + checkConnect(c,posX-1,posY+1, -1, 1)) >= 3 ) //checks top right & bottom left
			win = true;
		else if ( (checkConnect (c,posX+1,posY+1,1,1) + checkConnect(c,posX-1,posY-1,-1,-1)) >= 3 ) //checks top left & bottom right
			win = true;

		if (win == true)
			return c;
		return ' ';

	}

	private int checkConnect (char c, int x, int y, int xOffset, int yOffset) {

		if (x < 0 || x > 6 || y < 0 || y > 5)
			return 0;
		else if ( grid[x][y] != c)
			return 0;

		return 1+ checkConnect(c, x+xOffset, y+yOffset, xOffset, yOffset);
	}
}
