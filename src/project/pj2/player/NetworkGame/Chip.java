package project.pj2.player.NetworkGame;

import java.util.*;

public class Chip {

    public static final int BLACK = 0;
    public static final int WHITE = 1;

    int side;
    int row, col;
    Board board;
    boolean isStart, isEnd;
    private Map<Chip,Connection> connectionsMap = new HashMap<>(); //存與this chip形成connection的chip與connection的物件

    public Chip(int side, Board board) {
        this.side = side;
    }

    public Chip(int side, int row, int col, Board board, boolean isStart, boolean isEnd) {
        this.side = side;
        this.row = row;
        this.col = col;
        this.board = board;
        this.isStart = isStart;
        this.isEnd = isEnd;
    }

    void updateConnections() {
        List<Chip> chipList;
        if (side == BLACK) {
            chipList = board.getBlackChips();
        } else {
            chipList = board.getWhiteChips();
        }

        for (Chip chip : chipList) {
            if (chip != this) {
                connectionsMap.put(chip, Connection.newConnection(this, chip));
//                System.out.println(connectionsMap.get(chip));
            }
        }
//        System.out.println("-END-");
    }

    public List<Connection> getAllConnection() {
        List<Connection> list = new ArrayList<>();
        for (Connection c : connectionsMap.values()) {
            if (c != null)
                list.add(c);
        }
        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chip chip = (Chip) o;
        return side == chip.side &&
                row == chip.row &&
                col == chip.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(side, row, col);
    }

    public String toString() {
        return "(" + col + "," + row + ")";
    }
}
