package TicTacToe;

import java.util.ArrayList;
import java.util.List;

public class AIPlayerMinMax extends AIPlayer {

    public AIPlayerMinMax(Board board) {
        super(board);
    }

    @Override
    int[] move() {
//        int[] result= minimax(2, mySeed);
        int[] result= minimaxWithAlphaBetaPurning(2, mySeed, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return new int[] {result[1], result[2]};
    }

    /** Recursive minimax at level of depth for either maximizing or minimizing player.
     Return int[3] of {score, row, col}  */
    private int[] minimax(int depth, Seed player) {

        List<int[]> nextMoves = generateNextMoves();

        int bestScore = player == mySeed ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int currentScore;
        int bestRow = -1, bestCol = -1;

        if(nextMoves.isEmpty() || depth == 0) {
            bestScore = evaluate();
        } else {
            for(int[] nextMove : nextMoves) {
                cells[nextMove[0]][nextMove[1]].content = player;
                if(player == mySeed) {
                    currentScore = minimax(depth - 1, oppSeed)[0];
                    if(currentScore > bestScore) {
                        bestScore = currentScore;
                        bestRow = nextMove[0];
                        bestCol = nextMove[1];
                    }
                } else {
                    currentScore = minimax(depth - 1, mySeed)[0];
                    if(currentScore < bestScore) {
                        bestScore = currentScore;
                        bestRow = nextMove[0];
                        bestCol = nextMove[1];
                    }
                }
                // Undo move
                cells[nextMove[0]][nextMove[1]].content = Seed.EMPTY;
            }
        }
        return new int[] {bestScore, bestCol, bestRow};
    }

    /** Minimax (recursive) at level of depth for maximizing or minimizing player
     with alpha-beta cut-off. Return int[3] of {score, row, col}  */
    private int[] minimaxWithAlphaBetaPurning(int depth, Seed player, int alpha, int beta) {

        List<int[]> nextMoves = generateNextMoves();

        int score;
        int bestRow = -1, bestCol = -1;

        if(nextMoves.isEmpty() || depth == 0) {
            score = evaluate();
            return new int[] {score, alpha, beta};
        } else {
            for(int[] nextMove : nextMoves) {
                cells[nextMove[0]][nextMove[1]].content = player;
                if(player == mySeed) {
                    score = minimaxWithAlphaBetaPurning(depth - 1, oppSeed, alpha, beta)[0];
                    if(score > alpha) {
                        alpha = score;
                        bestRow = nextMove[0];
                        bestCol = nextMove[1];
                    }
                } else {
                    score = minimaxWithAlphaBetaPurning(depth - 1, mySeed, alpha, beta)[0];
                    if(score < beta) {
                        beta = score;
                        bestRow = nextMove[0];
                        bestCol = nextMove[1];
                    }
                }
                // Undo move
                cells[nextMove[0]][nextMove[1]].content = Seed.EMPTY;
                // cut-off
                if (alpha >= beta) break;
            }
        }
        return new int[] {(player == mySeed) ? alpha : beta, bestRow, bestCol};
    }

    /** Find all valid next moves.
     Return List of moves in int[2] of {row, col} or empty list if gameover */
    private List<int[]> generateNextMoves() {
        List<int[]> nextMoves = new ArrayList<>();

        if(hasWon(mySeed) || hasWon(oppSeed)) {
            return nextMoves;
        }

        for(int row = 0; row < ROWS; row++) {
            for(int col = 0; col < COLS; col++) {
                if(cells[row][col].content == Seed.EMPTY) {
                    nextMoves.add(new int[] {row, col});
                }
            }
        }
        return nextMoves;
    }

    private boolean hasWon(Seed player) {
        int pattern = 0b000000000;
        for(int row = 0; row < ROWS; row++) {
            for(int col = 0; col < COLS; col++) {
                if(cells[row][col].content == player)
                    pattern |= (1 << (row * COLS + col));
            }
        }
        for(int winningPattern : Board.WINNING_PATTERN) {
            if((winningPattern & pattern)  == winningPattern)
                return true;
        }
        return false;
    }

    /**
     * The heuristic evaluation function for the current board
     * @return +100, +10, +1 for EACH 3-, 2-, 1-in-a-line for computer.
     *         -100, -10, -1 for EACH 3-, 2-, 1-in-a-line for opponent.
     *         0 otherwise
     */
    private int evaluate() {
        int score = 0;
        // Evaluate score for each of the 8 lines (3 rows, 3 columns, 2 diagonals)
        score += evaluateLine(0, 0, 0, 1, 0, 2);  // row 0
        score += evaluateLine(1, 0, 1, 1, 1, 2);  // row 1
        score += evaluateLine(2, 0, 2, 1, 2, 2);  // row 2
        score += evaluateLine(0, 0, 1, 0, 2, 0);  // col 0
        score += evaluateLine(0, 1, 1, 1, 2, 1);  // col 1
        score += evaluateLine(0, 2, 1, 2, 2, 2);  // col 2
        score += evaluateLine(0, 0, 1, 1, 2, 2);  // diagonal
        score += evaluateLine(0, 2, 1, 1, 2, 0);  // alternate diagonal
        return score;
    }

    private int evaluateLine(int row1, int col1, int row2, int col2, int row3, int col3) {
        int score = 0;

        if(cells[row1][col1].content == mySeed) {
            score = 1;
        } else if(cells[row1][col1].content == oppSeed) {
            score = -1;
        }

        if(cells[row2][col2].content == mySeed) {
            if(score == 1) {
                score += 10;
            } else if(score == -1) {
                return 0;
            } else {
                score = 1;
            }
        } else if (cells[row2][col2].content == oppSeed) {
            if(score == 1) {
                return 0;
            } else if(score == -1) {
                score = -10;
            } else {
                score = -1;
            }
        }

        if(cells[row3][col3].content == mySeed) {
            if(score > 0) {
                score *= 10;
            } else if(score < 0){
                return 0;
            } else {
                score = 1;
            }
        } else if(cells[row3][col3].content == oppSeed) {
            if(score < 0) {
                score *= 10;
            } else if(score > 0){
                return 0;
            } else {
                score = -1;
            }
        }

        return score;
    }


}
