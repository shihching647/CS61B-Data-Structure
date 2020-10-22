package TicTacToe;

/**
 *  There are two methods which can both determine whether there is a winner after each move.
 *  1.hasWin(Seed s): check every cells in the determine whether there is a winner.
 *  2.hasWinByBitMasks(Seed seed): use 9-bit binary number to present a player pattern and match with pre-defined winner pattern via bit operations.
 */
public class Board {
    public static final int ROWS = 3;
    public static final int COLS = 3;

    Cell[][] cells;
    int enteredRow, enteredCol;

    //winner pattern
    public static final int[] WINNING_PATTERN = {
            0b111000000, //row 2
            0b000111000, //row 1
            0b000000111, //row 0
            0b100100100, //col 2
            0b010010010, //col 1
            0b001001001, //col 0
            0b100010001, //diagonal
            0b001010100  //opposite diagonal
    };
    // keeping track each player's pattern
    int crossPattern, noughtPattern;

    /** Constructor to initialize the game board */
    public Board() {
        cells = new Cell[ROWS][COLS];
        for(int row = 0; row < ROWS; row++) {
            for(int col = 0; col < COLS; col++) {
                cells[row][col] = new Cell(row, col);
            }
        }
    }

    /** Initialize (or re-initialize) the contents of the game board */
    public void init() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col].clear();  // clear the cell content
            }
        }
    }

    /** Return true if it is a draw (i.e., no more EMPTY cell) */
    public boolean isDraw() {
        for(int i = 0; i < ROWS; i++) {
            for(int j = 0; j < COLS; j++) {
                if(cells[i][j].content == Seed.EMPTY)
                    return false;
            }
        }
        return true;
    }

    public boolean hasWin(Seed s) {
        return(cells[enteredRow][0].content == s && cells[enteredRow][1].content == s && cells[enteredRow][2].content == s
            || cells[0][enteredCol].content == s && cells[1][enteredCol].content == s && cells[2][enteredCol].content == s
            || enteredRow == enteredCol && cells[0][0].content == s && cells[1][1].content == s && cells[2][2].content == s
            || enteredRow + enteredCol == 2 && cells[2][0].content == s && cells[1][1].content == s && cells[0][2].content == s);
    }

    //Matching of Winning Patterns with Bit-Masks
    public boolean hasWinByBitMasks(Seed seed) {
        int playerPattern = (seed == Seed.CROSS ? crossPattern : noughtPattern);
        for(int pattern : WINNING_PATTERN) {
            if((pattern & playerPattern)  == pattern)
                return true;
        }
        return false;
    }

    public void paint() {
        String result = "";
        for(int i = 0; i < cells.length; i++){
            for(int j = 0; j < cells[i].length; j++){
                result += cells[i][j].paint();
                result += (j == (cells[i].length - 1) ? "\n" : "|");
            }
            if(i != cells.length - 1) result += "-----\n";
        }
        System.out.println(result);
    }
}
