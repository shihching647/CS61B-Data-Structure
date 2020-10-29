package TicTacToe;

import java.util.Scanner;

public class GameMain {
    protected Board board;
    protected GameState currentState;
    protected Seed currentPlayer;

    protected static Scanner scanner = new Scanner(System.in);  // input Scanner

    public GameMain() {
        board = new Board();
        initGame();
    }

    public void startGame() {
        do{
            playerMove(currentPlayer);
            updateGame(currentPlayer);
            board.paint();
            //Change player
            if(currentState == GameState.PLAYING)
                currentPlayer = (currentPlayer == Seed.CROSS ? Seed.NOUGHT : Seed.CROSS);
        } while (currentState == GameState.PLAYING);
        showResult();
    }

    /** Initialize the game-board contents and the current states */
    public void initGame() {
        board.init();  // clear the board contents
        currentPlayer = Seed.CROSS;       // CROSS plays first
        currentState = GameState.PLAYING; // ready to play
    }

    protected void playerMove(Seed seed) {
        boolean validInput = false;
        do {
            System.out.print("Player '" + (seed == Seed.CROSS ? "X" : "O") + "', enter your move(row[1-3] column[1-3]): ");
            int row = scanner.nextInt() - 1;
            int col = scanner.nextInt() - 1;
            if (row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLS && board.cells[row][col].content == Seed.EMPTY) {
                board.cells[row][col].content = seed;
                board.enteredRow = row;
                board.enteredCol = col;
                // updating the pattern after each move
                updateBitPattern(seed, row, col);
                validInput = true;
            } else {
                System.err.println("This move at (" + (row + 1) + "," + (col + 1) +") is not valid. Try again...");
            }
        } while(!validInput);
    }

    protected void updateBitPattern(Seed seed, int row, int col) {
        int bitPosition = (row) * Board.ROWS + (col); //row 0 1 2表第1 2 3列, col 0 1 2表第1 2 3行
        if(seed == Seed.CROSS) {
            board.crossPattern = board.crossPattern | 0x1 << bitPosition;
        } else {
            board.noughtPattern = board.noughtPattern | 0x1 << bitPosition;
        }
    }

    protected void updateGame(Seed seed) {
        if(board.hasWinByBitMasks(seed)) {
            currentState = (currentPlayer == Seed.CROSS ? GameState.CROSS_WIN : GameState.NOUGHT_WIN);
        } else if(board.isDraw()){
            currentState = GameState.DRAW;
        }
    }

    protected void showResult() {
        if(currentState != GameState.DRAW) {
            System.out.println("Player '" + (currentPlayer == Seed.CROSS ? "X" : "O") + "' won!");
        } else {
            System.out.println("DRAW!!!");
        }
    }

    public static void main(String[] args) {
        new GameMain().startGame();
    }
}
