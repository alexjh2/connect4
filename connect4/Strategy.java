package connect4;



/**
 *  Strategy class made with minimax algorithm that is used by the computer player.
 *
 *  @author alexj0523
 *  @version May, 2025
 */
public class Strategy {

    private Grid board;
    /**
     * board width
     */
    static final int WIDTH = 7;
    /**
     * board height
     */
    static final int HEIGHT = 6;
    private char myColor;
    private char oppColor;
    private int algo;

    /**
     * Constructor that initializes the variables
     * @param board the game board
     * @param c color of the chip
     */
    public Strategy(Game board, char c, int algo) {

        this.board = new Grid(WIDTH,HEIGHT);
        this.board.grid = board.grid;
        myColor = c;
        oppColor = (c=='b') ? 'r' : 'b';
        this.algo = algo;
    }

    private double calculatepower(double n) {
        return Math.pow(4, n);
    }

    private boolean validBounds(int x, int y) {
        if (x >= WIDTH || x < 0) {
            return false;
        }
        if (y >= HEIGHT || y < 0) {
            return false;
        }
        if (board.grid[x][y] != ' ') {
            return false;
        }
        return true;
    }


    private double calcScore(int x, int y, int xOffset, int yOffset) {

        double result = 0;
        char color = board.grid[x][y];
        int nFree = 0;  //

        for (int i = 0; i < 3; i++) {
            int posX = x + i * xOffset;
            int posY = y+ i * yOffset;

            if (posX < 0 || posX >= WIDTH || posY < 0 || posY >= HEIGHT ) {

                color = ' ';
            	result = 0;
                break;
            }
            else if (board.grid[posX][posY] != color && board.grid[posX][posY] != ' ') {
                
            	color = ' ';
            	result = 0;
                break;
            }
            else if ( validBounds(posX + xOffset, posY + yOffset) ) { // one in a x

                if (color == myColor) {
                    result = calculatepower(i+1-nFree);
                } else if (color == oppColor) {
                    result = calculatepower(i+1-nFree)*(-1);
                }
                nFree +=1;
            }

        }
        return result;

    }
    private double calcScore1(int x, int y, int xOffset, int yOffset) {

        double result = 0;
        char color = board.grid[x][y];
        int nFree = 0;  //

        for (int i = 0; i < 3; i++) {
            int posX = x + i * xOffset;
            int posY = y+ i * yOffset;

            if (posX < 0 || posX >= WIDTH || posY < 0 || posY >= HEIGHT ) {

                color = ' ';
            	result = 0;
                break;
            }
            else if (board.grid[posX][posY] != color && board.grid[posX][posY] != ' ') {
                
            	color = ' ';
            	result = 0;
                break;
            }
            else if ( validBounds(posX + xOffset, posY + yOffset) ) { // one in a x

                if (color == myColor) {
                    result = calculatepower(i+1-nFree);
                } else if (color == oppColor) {
                    result = calculatepower(i+1-nFree)*(-1);
                }
                nFree +=1;
            }
            else if (i == 2 && posX+xOffset >= 0 && posX+xOffset < WIDTH && posY+yOffset >= 0 && posY+yOffset < HEIGHT) {
                if (board.grid[posX + xOffset][posY + yOffset] == color){
                	if (color == myColor) {
                        result = calculatepower(i+1-nFree);
                    } else if (color == oppColor) {
                        result = calculatepower(i+1-nFree)*(-1);
                    }
                }

            }
          
        }
        return result;

    }
    /**
     * Calculating the entire board's score by adding up the score according to the calcScore method.
     * The higher the returned value, the better move it is for the comp player.
     * @return the score of the predicted board
     */
    public double evaluateBoard() {

        double result = 0;

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
            	
            	switch (algo) {
            	
            	case 0:
                    result += calcScore(x, y, 1, 0);
                    result += calcScore(x, y, 0, -1); // up
                    result += calcScore(x, y, 1, -1); // up diagonal
                    result += calcScore(x, y, 1, 1); // down diagonal
                    break;
            	case 1:
                    result += calcScore1(x, y, 1, 0);
                    result += calcScore1(x, y, 0, -1); // up
                    result += calcScore1(x, y, 1, -1); // up diagonal
                    result += calcScore1(x, y, 1, 1); // down diagonal
                    break;
            	
            	}
            		

                result += calcScore(x, y, 1, 0);

                result += calcScore(x, y, 0, -1); // up

                result += calcScore(x, y, 1, -1); // up diagonal

                result += calcScore(x, y, 1, 1); // down diagonal

            }
        }

        return result;

    }


    /**
     * returns the column(x) that the gives the best chance for the comp player to win
     * @param maxDepth how far the estimation should go for. This depends on the level.
     * @return column that the comp player should place its chip (best move)
     */
    public int bestMoveForComputer(int maxDepth) {

        double alpha = -100000; //initialize alpha and beta values for pruning
        double beta = 1000000;

        int bestCol = -1;
        double result, bestResult = Double.NEGATIVE_INFINITY;
        int y;
        char winner;


        for (int x = 0; x < WIDTH; x++) {
            if (board.isLegal(x)) {

                y = board.drop(myColor, x);
                winner = board.isWin(x, y); // checks if placing the chip at this position wins the game

                if (winner == myColor) {
                    // result = Double.POSITIVE_INFINITY;
                    board.undoDrop(x);
                    return x;
                }
                result = min(x, y, maxDepth, 0, alpha, beta);
                System.out.print(result+", ");
                
                board.undoDrop(x);
                if (result > bestResult) {

                    bestResult = result;
                    bestCol = x;

                }

                if (result > alpha) { //alpha-beta pruning
                    alpha = result; //constantly updates the alpha since min is called many times and bestResult could change accordingly
                }

            }

        }
        System.out.print("\n");

        if (bestCol == -1) //if there's no best move available (comp player will lose wherever it places its chip)
            for (int x=0 ; x<WIDTH ; x++) {
                if (board.isLegal(x)) {
                    if (Math.abs(x-3)<Math.abs(bestCol-3))
                        bestCol = x;
                }

            }
        return bestCol;
    }

    /**
     * Calculating and returning the max score of the predicted board
     * @param posX x coordinate of the current point
     * @param posY y coordinate of the current point
     * @param maxDepth max depth(height) of the minimax tree
     * @param depth current depth
     * @param alpha highest value
     * @param beta lowest value
     * @return max value
     */
    private double max(int posX, int posY, int maxDepth, int depth, double alpha, double beta) {

        char winner = board.isWin(posX, posY);
        if (winner == myColor) {
            return Double.POSITIVE_INFINITY;
        } else if (winner == oppColor) {
            return Double.NEGATIVE_INFINITY;
        }



        if (board.isFull() || (depth == maxDepth)) {
            double r = evaluateBoard();
            return r;
        }
        double bestResult = Double.NEGATIVE_INFINITY;
        double result;
        int y;

        for (int x = 0; x < WIDTH; x++) {
            if (board.isLegal(x)) {

                y = board.drop(myColor, x);
                result = min(x,y,maxDepth, depth + 1, alpha, beta);
                board.undoDrop(x);
                if (result > bestResult) {

                    bestResult = result;

                }

                if (result > alpha) {
                    alpha = result;
                }

                if (alpha >= beta) {
                    return alpha;
                }
            }

        }

        return bestResult;

    }

    /**
     * Calculating and returning the min score of the predicted board
     * @param posX x coordinate of the current point
     * @param posY y coordinate of the current point
     * @param maxDepth max depth(height) of the minimax tree
     * @param depth current depth
     * @param alpha highest value
     * @param beta lowest value
     * @return min value
     */
    private double min(int posX, int posY, int maxDepth, int depth, double alpha, double beta) {

        char winner = board.isWin(posX, posY);

        if (winner == myColor) {

            return Double.POSITIVE_INFINITY;

        } else if (winner == oppColor) {

            return Double.NEGATIVE_INFINITY;
        }


        if (board.isFull() || (depth == maxDepth)) {

            double r = evaluateBoard();
            return r;

        }
        double bestResult = Double.POSITIVE_INFINITY;
        double result;
        int y;

        for (int x = 0; x < WIDTH; x++) {
            if (board.isLegal(x)) {

                y = board.drop(oppColor, x);
                result = max(x, y, maxDepth, depth + 1, alpha, beta);
                board.undoDrop(x);

                if (result <= bestResult) {
                    bestResult = result;

                }

                if (result < beta) {
                    beta = result;
                }

                if (alpha >= beta) {
                    return beta;
                }
            }

        }

        return bestResult;
    }
}

