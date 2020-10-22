package TicTacToe;

import java.util.Scanner;
/**
 * Tic-Tac-Toe: Two-player console, non-graphics, non-OO version.
 * All variables/methods are declared as static (belong to the class)
 *  in the non-OO version.
 */
public class TTTConsoleNonOO2P {
    // Name-constants to represent the seeds and cell contents
    public static final int EMPTY = 0;
    public static final int CROSS = 1;
    public static final int NOUGHT = 2;

    // Name-constants to represent the various states of the game
    public static final int BOARD_STATE_PLAYING = 0;
    public static final int BOARD_STATE_DRAW = 1;
    public static final int BOARD_STATE_CROSS_WIN = 2;
    public static final int BOARD_STATE_NOUGHT_WIN = 3;

    public static final int ROWS = 3, COLS = 3;
    public static int[][] board = new int[ROWS][COLS];
    public static int currentBoardState;
    public static int currentPlayer;
    public static int enteredRow, enteredCol;

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initGame();
        do{
            playerMove();
            updateBoard();
            printCurrentBoard();
        } while(currentBoardState == BOARD_STATE_PLAYING);
        showResult();
    }
    /** Initialize the game-board contents and the current states */
    public static void initGame() {
        for(int i = 0; i < ROWS; i++) {
            for(int j = 0; j < COLS; j++) {
                board[i][j] = EMPTY;
            }
        }
        currentPlayer = CROSS;
        currentBoardState = BOARD_STATE_PLAYING;
    }
    /** Player with the makes one move, with input validation.
     Update global variables "enteredRow" and "enteredCol". */
    public static void playerMove() {
        boolean validInput = false;
        do {
            System.out.print("Player '" + (currentPlayer == CROSS ? "X" : "O") + "', enter your move(row[1-3] column[1-3]): ");
            enteredRow = scanner.nextInt() - 1;
            enteredCol = scanner.nextInt() - 1;
            if (enteredRow >= 0 && enteredRow < ROWS && enteredCol >= 0 && enteredCol < COLS && board[enteredRow][enteredCol] == EMPTY) {
                board[enteredRow][enteredCol] = currentPlayer;
                validInput = true;
            } else {
                System.err.println("This move at (" + (enteredRow + 1) + "," + (enteredCol + 1) +") is not valid. Try again...");
            }
        } while(!validInput);
    }
    /** Update the "currentState" after one move. */
    public static void updateBoard() {
        if(hasWon()) {
            currentBoardState = (currentPlayer == CROSS ? BOARD_STATE_CROSS_WIN : BOARD_STATE_NOUGHT_WIN);
        } else if(isDraw()){
            currentBoardState = BOARD_STATE_DRAW;
        } else {
            currentPlayer = (currentPlayer == CROSS ? NOUGHT : CROSS );
        }
    }

    public static boolean hasWon() {
        //任一條為一樣,且不能為EMPTY
        return(board[0][0] == board[0][1] && board[0][1] == board[0][2] && board[0][0] != EMPTY
                || board[1][0] == board[1][1] && board[1][1] == board[1][2] && board[1][0] != EMPTY
                || board[2][0] == board[2][1] && board[2][1] == board[2][2] && board[2][0] != EMPTY
                || board[0][0] == board[1][0] && board[1][0] == board[2][0] && board[0][0] != EMPTY
                || board[0][1] == board[1][1] && board[1][1] == board[2][1] && board[0][1] != EMPTY
                || board[0][2] == board[1][2] && board[1][2] == board[2][2] && board[0][2] != EMPTY
                || board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != EMPTY
                || board[2][0] == board[1][1] && board[1][1] == board[0][2] && board[2][0] != EMPTY
        );
    }
    /** Return true if it is a draw (no more empty cell). */
    public static boolean isDraw() {
        for(int i = 0; i < ROWS; i++) {
            for(int j = 0; j < COLS; j++) {
                if(board[i][j] == EMPTY)
                    return false;
            }
        }
        return true;
    }
    /** Return true if the player has won after one move. */
    public static void printCurrentBoard() {
        String result = "";
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                switch(board[i][j]){
                    case CROSS: result += "X"; break;
                    case NOUGHT: result += "O"; break;
                    case EMPTY: result += " ";
                }
                result += (j == (board[i].length - 1) ? "\n" : "|");
            }
            if(i != board.length - 1) result += "-----\n";
        }
        System.out.println(result);
    }

    public static void showResult() {
        if(currentBoardState != BOARD_STATE_DRAW) {
            System.out.println("Player '" + (currentPlayer == CROSS ? "X" : "O") + "' won!");
        } else {
            System.out.println("DRAW!!!");
        }
    }
}
