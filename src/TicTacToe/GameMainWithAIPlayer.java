package TicTacToe;

public class GameMainWithAIPlayer extends GameMain {
    AIPlayer aiPlayer;
    private Seed player, ai;

    public GameMainWithAIPlayer(Seed player) {
        this.player = player;
    }

    public void startGame() {
        do{
            if(currentPlayer == player) {
                playerMove(currentPlayer);
            } else {
                aiPlayerMove(currentPlayer);
            }
            updateGame(currentPlayer);
            board.paint();
            //Change player
            if(currentState == GameState.PLAYING)
                currentPlayer = (currentPlayer == Seed.CROSS ? Seed.NOUGHT : Seed.CROSS);
        } while (currentState == GameState.PLAYING);
        showResult();
    }

    private void aiPlayerMove(Seed seed) {
        aiPlayer = new AIPlayerMinMax(board);
        aiPlayer.setSeed(seed);
        int[] move = aiPlayer.move();
        int row = move[0];
        int col = move[1];
        System.out.println("-------AI choose : " + (row + 1) + " " + (col + 1));
        //update board
        board.cells[row][col].content = seed;
        board.enteredRow = row;
        board.enteredCol = col;
        // updating the pattern after each move
        updateBitPattern(seed, row, col);
    }

    public static void main(String[] args) {
        new GameMainWithAIPlayer(Seed.NOUGHT).startGame();
    }
}
