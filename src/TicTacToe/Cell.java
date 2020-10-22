package TicTacToe;

public class Cell {
    Seed content;
    int row, col;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        clear();
    }

    public void clear() {
        this.content = Seed.EMPTY;
    }

    public String paint() {
        switch (content) {
            case CROSS:  return ("X");
            case NOUGHT: return ("O");
            case EMPTY:  return (" ");
        }
        return "";
    }
}
