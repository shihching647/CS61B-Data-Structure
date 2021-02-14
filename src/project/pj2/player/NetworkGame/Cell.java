package project.pj2.player.NetworkGame;

import java.util.Objects;

public class Cell {

    Chip content;
    int row, col;
    boolean whiteAvailability, blackAvailability; //用來檢查white, black是否可以放

    public Cell() { }

    public Cell(Chip content, int row, int col) {
        this.content = content;
        this.row = row;
        this.col = col;
        initAvailability(row, col);
    }

    boolean isAvailable (int side) {
        if(side == Chip.BLACK) return blackAvailability;
        else return whiteAvailability;
    }

    void updateAvailability(int side, boolean value) {
        if (side == Chip.BLACK) blackAvailability = value;
        else whiteAvailability = value;
    }

    boolean isStart(int side) {
        if (side == Chip.BLACK) {
            return row == 0;
        } else {
            return col == 0;
        }
    }

    boolean isEnd(int side) {
        if (side == Chip.BLACK) {
            return row == 7;
        } else {
            return col == 7;
        }
    }

    private void initAvailability(int row, int col) {
        if(row == 0 || row == 7) {
            if(col == 0 || col == 7) {
                blackAvailability = false;
                whiteAvailability = false;
            } else {
                whiteAvailability = false;
                blackAvailability = true;
            }
        } else if (col == 0 || col == 7) {
            whiteAvailability = true;
            blackAvailability = false;
        } else {
            blackAvailability = true;
            whiteAvailability = true;
        }
    }

    public String toString() {
        if(content == null) return "E";
        else if(content.side == Chip.BLACK) return "B";
        else return "W";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return row == cell.row &&
                col == cell.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
