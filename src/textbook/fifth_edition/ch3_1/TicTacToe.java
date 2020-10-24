package textbook.fifth_edition.ch3_1;

public class TicTacToe {
    protected static final int EMPTY = 0;
    protected static final int X = 1;
    protected static final int O = -1;
    protected int board[][] = new int[3][3];
    protected int player;

    public TicTacToe(){
        clearBoard();
    }

    public void clearBoard() {
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                board[i][j] = EMPTY;
            }
        }
        player = 1; //一開始是由X那位先攻
    }

    /** Puts an X or 0 mark at position i,j **/
    public void putMark(int i, int j) throws IllegalArgumentException{
        if(i < 0 || i > board.length || j < 0 || j > board[i].length){
            throw new IllegalArgumentException();
        }
        if(board[i][j] != EMPTY){
            throw new IllegalArgumentException();
        }
        board[i][j] = player;
        player = -player;
    }

    //Checks whether the board configuration is a win for the given player
    public boolean isWin(int mark){
        return (board[0][0] + board[0][1] + board[0][2] == mark * 3    //row 0
                || board[1][0] + board[1][1] + board[1][2] == mark * 3 //row 1
                || board[2][0] + board[2][1] + board[2][2] == mark * 3 //row 2
                || board[0][0] + board[1][0] + board[2][0] == mark * 3 //column 0
                || board[0][1] + board[1][1] + board[2][1] == mark * 3 //column 1
                || board[0][2] + board[1][2] + board[2][2] == mark * 3 //column 2
                || board[0][0] + board[1][1] + board[2][2] == mark * 3 //diagonal
                || board[0][2] + board[1][1] + board[2][0] == mark * 3); //diagonal
    }

    //Returns the winning player or 0 to indicate a tie
    public int winner(){
        if(isWin(X)){
            return X;
        }else if(isWin(O)){
            return O;
        }else{
            return 0;
        }
    }

    //Returns a simple character string showing the current board
    public String toString(){
        String result = "";
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                switch(board[i][j]){
                    case X: result += "X"; break;
                    case O: result += "O"; break;
                    case EMPTY: result += " ";
                }
                result += (j == (board[i].length - 1) ? "\n" : "|");
            }
            if(i != board.length - 1) result += "-----\n";
        }
        return result;
    }

    public static void main(String[] args){
        TicTacToe game = new TicTacToe();
        /* X moves: */                /* O moves: */
        game.putMark(1, 1);     game.putMark(0, 2);
        game.putMark(2, 2);     game.putMark(0, 0);
        game.putMark(0, 1);     game.putMark(2, 1);
        game.putMark(1, 2);     game.putMark(2, 0);
        game.putMark(1, 0);

        System.out.println(game);
        int winner = game.winner();
        if(winner != 0){
            System.out.println("Winner is " + (winner == X ? "X" : "O"));
        }else{
            System.out.println("Tie");
        }
    }
}
