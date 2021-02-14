package project.pj2.player.NetworkGame;

public class Connection {

    public static final int DIRECTION_VERTICAL = 0;
    public static final int DIRECTION_HORIZONTAL = 1;
    public static final int DIRECTION_SLASH = 2;
    public static final int DIRECTION_BACK_SLASH = 3;
    public Chip[] chips;
    public int direction;
    public int side;

    public Connection(Chip[] chips, int direction, int side) {
        this.chips = chips;
        this.direction = direction;
        this.side = side;
    }

    public static Connection newConnection(Chip src, Chip dest) {
        Chip[] chips = new Chip[]{src, dest};
        int side = src.side;
        if (src.col == dest.col) return new Connection(chips, DIRECTION_VERTICAL, side);
        else if (src.row == dest.row) return new Connection(chips, DIRECTION_HORIZONTAL, side);
        else if (src.row - dest.row == src.col - dest.col) return new Connection(chips, DIRECTION_SLASH, side);
        else if (src.row - dest.row == -(src.col - dest.col)) return new Connection(chips, DIRECTION_BACK_SLASH, side);
        else return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("開始位置 = " + chips[0]);
        sb.append("side = ").append(side == 0 ? "Black" : "White");
        sb.append(", direction = ");
        if (direction == DIRECTION_VERTICAL) sb.append("垂直");
        else if (direction == DIRECTION_HORIZONTAL) sb.append("水平");
        else if (direction == DIRECTION_SLASH) sb.append("右下");
        else sb.append("左下");
        sb.append(", 結束位置 = " + chips[1]);
        return sb.toString();
    }
}
