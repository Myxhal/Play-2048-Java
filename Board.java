package game;

import java.util.ArrayList;

/**
 * 2048 Board
 * Methods to complete:
 * updateOpenSpaces(), addRandomTile(), swipeLeft(), mergeLeft(),
 * transpose(), flipRows(), makeMove(char letter)
 * 
 * @author Kal Pandit
 * @author Ishaan Ivaturi
 **/
public class Board {
    private int[][] gameBoard;               // the game board array
    private ArrayList<BoardSpot> openSpaces; // the ArrayList of open spots: board cells without numbers.

    /**
     * Zero-argument Constructor: initializes a 4x4 game board.
     **/
    public Board() {
        gameBoard = new int[4][4];
        openSpaces = new ArrayList<>();
    }

    /**
     * One-argument Constructor: initializes a game board based on a given array.
     * 
     * @param board the board array with values to be passed through
     **/
    public Board ( int[][] board ) {
        gameBoard = new int[board.length][board[0].length];
        for ( int r = 0; r < gameBoard.length; r++ ) {
            for ( int c = 0; c < gameBoard[r].length; c++ ) {
                gameBoard[r][c] = board[r][c];
            }
        }
        openSpaces = new ArrayList<>();
    }

    /**
     * 1. Initializes the instance variable openSpaces (open board spaces) with an empty array.
     * 2. Adds open spots to openSpaces (the ArrayList of open BoardSpots).
     * 
     * Note: A spot (i, j) is open when gameBoard[i][j] = 0.
     * 
     * Assume that gameBoard has been initialized.
     **/
    public void updateOpenSpaces() {
        // WRITE YOUR CODE HERE
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                if(gameBoard[i][j]==0){
                    BoardSpot bS = new BoardSpot(i,j);
                    openSpaces.add(bS);
                }
            }
        }

    }

    /**
     * Adds a random tile to an open spot with a 90% chance of a 2 value and a 10% chance of a 4 value.
     * Requires separate uses of StdRandom.uniform() to find a random open space and determine probability of a 4 or 2 tile.
     * 
     * 1. Select a tile t by picking a random open space from openSpaces
     * 2. Pick a value v by picking a double from 0 to 1 (not inclusive of 1); < .1 means the tile is a 4, otherwise 2
     * 3. Update the tile t on gameBoard with the value v
     * 
     * Note: On the driver updateOpenStapes() is called before this method to ensure that openSpaces is up to date.
     **/
    public void addRandomTile() {
        // WRITE YOUR CODE HERE
        int a = 0;
        double b = 1.0;

        // int randR = StdRandom.uniform(0,4);
        // int randC= StdRandom.uniform(0,4);
        // BoardSpot t = new BoardSpot(randR, randC);

        int randBSpace = (StdRandom.uniform(a,openSpaces.size()));
    
        BoardSpot t = openSpaces.get(randBSpace); //gets random tile on board
        openSpaces.remove(randBSpace);
        double rand = StdRandom.uniform(a,b);
        
        
        int v;

        if (rand < 0.1){
            v = 4;
            // System.out.println("flag1");
        }
        else{
            v = 2;
            // System.out.println("flag2");
        }
        // System.out.println(t.getRow());
        // System.out.println(t.getCol());
        // System.out.println(v);
        gameBoard[t.getRow()][t.getCol()] = v;
        
    }

    /**
     * Swipes the entire board left, shifting all nonzero tiles as far left as possible.
     * Maintains the same number and order of tiles. 
     * After swiping left, no zero tiles should be in between nonzero tiles. 
     * (ex: 0 4 0 4 becomes 4 4 0 0).
     **/
    public void swipeLeft() {
        // WRITE YOUR CODE HERE
        for (int i = 0; i < 4; i++)
        {

            for(int j = 0; j < 4; j++)
            {

                if (gameBoard [i][j] == 0)
                {

                    for (int newJ = j + 1; newJ < 4; newJ++)
                    {

                        if (gameBoard[i][newJ] != 0)
                        {

                            gameBoard [i][j] = gameBoard [i][newJ];
                            gameBoard [i][newJ] = 0;

                            break;
                        }
                    }
                }
            }
        }
        
    }

    /**
     * Find and merge all identical left pairs in the board. Ex: "2 2 2 2" will become "2 0 2 0".
     * The leftmost value takes on double its own value, and the rightmost empties and becomes 0.
     **/
    public void mergeLeft() {
        // WRITE YOUR CODE HERE
        for(int i = 0; i < 4; i++)
        {

            for (int j = 0; j < 3; j++)
            {

                if (gameBoard[i][j] == gameBoard[i][j + 1])
                {


                    gameBoard[i][j] = gameBoard[i][j] * 2;
                    gameBoard[i][j + 1] = 0;


                }
            }
        }
        swipeLeft();
    }

    /**
     * Rotates 90 degrees clockwise by taking the transpose of the board and then reversing rows. 
     * (complete transpose and flipRows).
     * Provided method. Do not edit.
     **/
    public void rotateBoard() {


        transpose();
        flipRows();
    }

    /**
     * Updates the instance variable gameBoard to be its transpose. 
     * Transposing flips the board along its main diagonal (top left to bottom right).
     * 
     * To transpose the gameBoard interchange rows and columns.
     * Col 1 becomes Row 1, Col 2 becomes Row 2, etc.
     * 
     **/
    public void transpose() {
        // WRITE YOUR CODE HERE
        int temporary;

        for(int i = 0; i < 4; i++)
        {		

            for(int j = 0; j < i; j++)
            {

              temporary = gameBoard[i][j];
              gameBoard[i][j] = gameBoard[j][i];
              gameBoard[j][i] = temporary;


            }
        }
    }

    /**
     * Updates the instance variable gameBoard to reverse its rows.
     * 
     * Reverses all rows. Columns 1, 2, 3, and 4 become 4, 3, 2, and 1.
     * 
     **/
    public void flipRows() {
        // WRITE YOUR CODE HERE
        int temporary;

        for(int i = 0; i < gameBoard.length; i++)
        {		

            for(int j = 0; j < gameBoard[i].length / 2; j++)
            {


              temporary = gameBoard[i][j];
              gameBoard[i][j] = gameBoard[i][gameBoard[i].length - j - 1];
              gameBoard[i][gameBoard[i].length - j - 1] = temporary;


            }
        }
    }

    /**
     * Calls previous methods to make right, left, up and down moves.
     * Swipe, merge neighbors, and swipe. Rotate to achieve this goal as needed.
     * 
     * @param letter the first letter of the action to take, either 'L' for left, 'U' for up, 'R' for right, or 'D' for down
     * NOTE: if "letter" is not one of the above characters, do nothing. 
     **/
    public void makeMove(char letter) {
        // WRITE YOUR CODE HERE
        char move = letter;

        if (move == 'L')
        {

            swipeLeft();
            mergeLeft();
            swipeLeft();
        }

        if (move == 'R')
        {

            rotateBoard();
            rotateBoard();
            swipeLeft();
            mergeLeft();
            swipeLeft();
            rotateBoard();
            rotateBoard();

        }

        if (move == 'U')
        {

            rotateBoard();
            rotateBoard();
            rotateBoard();
            swipeLeft();
            mergeLeft();
            swipeLeft();

            rotateBoard();
        }

        if (move == 'D')
        {

           rotateBoard();
           swipeLeft();
           mergeLeft();
           swipeLeft();
           
           rotateBoard();
           rotateBoard();
           rotateBoard();


        }

    }

    /**
     * Returns true when the game is lost and no empty spaces are available. Ignored
     * when testing methods in isolation.
     * 
     * @return the status of the game -- lost or not lost
     **/
    public boolean isGameLost() {
        return openSpaces.size() == 0;
    }

    /**
     * Shows a final score when the game is lost. Do not edit.
     **/
    public int showScore() {
        int score = 0;
        for ( int r = 0; r < gameBoard.length; r++ ) {
            for ( int c = 0; c < gameBoard[r].length; c++ ) {
                score += gameBoard[r][c];
            }
        }
        return score;
    }

    /**
     * Prints the board as integer values in the text window. Do not edit.
     **/
    public void print() {
        for ( int r = 0; r < gameBoard.length; r++ ) {
            for ( int c = 0; c < gameBoard[r].length; c++ ) {
                String g = Integer.toString(gameBoard[r][c]);
                StdOut.print((g.equals("0")) ? "-" : g);
                for ( int o = 0; o < (5 - g.length()); o++ ) {
                    StdOut.print(" ");
                }
            }
            StdOut.println();
        }
    }
    /**
     * Prints the board as integer values in the text window, with open spaces denoted by "**"". Used by TextDriver.
     **/
    public void printOpenSpaces() {
        for ( int r = 0; r < gameBoard.length; r++ ) {
            for ( int c = 0; c < gameBoard[r].length; c++ ) {
                String g = Integer.toString(gameBoard[r][c]);
                for ( BoardSpot bs : getOpenSpaces() ) {
                    if (r == bs.getRow() && c == bs.getCol()) {
                        g = "**";
                    }
                }
                StdOut.print((g.equals("0")) ? "-" : g);
                for ( int o = 0; o < (5 - g.length()); o++ ) {
                    StdOut.print(" ");
                }
            }
            StdOut.println();
        }
    }

    /**
     * Seed Constructor: Allows students to set seeds to debug random tile cases.
     * 
     * @param seed the long seed value
     **/
    public Board(long seed) {
        StdRandom.setSeed(seed);
        gameBoard = new int[4][4];
    }

    /**
     * Gets the open board spaces.
     * 
     * @return the ArrayList of BoardSpots containing open spaces
     **/
    public ArrayList<BoardSpot> getOpenSpaces() {
        return openSpaces;
    }

    /**
     * Gets the board 2D array values.
     * 
     * @return the 2D array game board
     **/
    public int[][] getBoard() {
        return gameBoard;
    }
}
