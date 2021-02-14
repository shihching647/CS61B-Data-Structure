package project.pj2.player.NetworkGame;

import project.pj2.player.Move;
import project.pj2.player.NetworkIdentifier.NetworkIdentifier;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private Cell[][] cells;
    private List<Chip> blackChips, whiteChips;
    private NetworkIdentifier networkIdentifier;

    public Board() {
        cells = new Cell[8][8];
        for(int i = 0; i < cells.length; i++) {
            for(int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell(null, i, j);
            }
        }
        blackChips = new ArrayList<>();
        whiteChips = new ArrayList<>();
    }

    public List<Chip> getBlackChips() {
        return blackChips;
    }

    public List<Chip> getWhiteChips() {
        return whiteChips;
    }

    public void updateOneMove(int side, Move move) {
        if(move.moveKind == Move.ADD && isValidMove(side, move)) {
            int row = move.y1;
            int col = move.x1;
            Cell cell = cells[row][col];
            cell.content = new Chip(side, row, col, this, cell.isStart(side), cell.isEnd(side));
            if(side == Chip.BLACK) {
                cell.blackAvailability = false;
                blackChips.add(cell.content);
            } else {
                cell.whiteAvailability = false;
                whiteChips.add(cell.content);
            }
            updateAvailability(side, cell);
            cell.content.updateConnections();
        } else if (move.moveKind == Move.STEP) {
            //移除舊的chip位置
            Cell oldCell = cells[move.y2][move.x2];
            if(side == Chip.BLACK) {
                oldCell.blackAvailability = true;
            } else {
                oldCell.whiteAvailability = true;
            }
            Chip chip = oldCell.content;
            oldCell.content = null;
            updateAvailability(side, oldCell);

            //移動chip到新的位置
            chip.row = move.y1;
            chip.col = move.x1;
            Cell newCell = cells[move.y1][move.x1];
            if(side == Chip.BLACK) {
                newCell.blackAvailability = false;
            } else {
                newCell.whiteAvailability = false;
            }
            newCell.content = chip;
            updateAvailability(side, newCell);
            newCell.content.updateConnections();
            newCell.content.isStart = newCell.isStart(side);
            newCell.content.isEnd = newCell.isEnd(side);
        }
    }

    private void updateAvailability(int side, Cell cell) {
        int row = cell.row;
        int col = cell.col;

        for(int i = row -2; i <= row + 2; i++) {
            for(int j = col - 2; j <= col + 2; j++) {
                try {
                    Cell neighbor = cells[i][j];
                    if(neighbor.content == null || neighbor == cell) continue;
                    if(isAdjacent(cell, neighbor)) {
                        updateAdjacent(side, cell, neighbor);
                    } else {
                        updateNonAdjacent(side, cell, neighbor);
                    }
                } catch (ArrayIndexOutOfBoundsException ex) {
                    continue;
                }
            }
        }
    }

    private void updateAdjacent(int side, Cell cell, Cell neighbor) {
        int startRow = Math.min(cell.row - 1, neighbor.row - 1);
        int endRow = Math.max(cell.row + 1, neighbor.row + 1);
        int startCol = Math.min(cell.col - 1, neighbor.col - 1);
        int endCol = Math.max(cell.col + 1, neighbor.col + 1);
        for(int i = startRow; i <= endRow; i++) {
            for (int j = startCol; j <= endCol; j++) {
                try {
                    Cell c = cells[i][j];
                    if(isAdjacent(c, cell) || isAdjacent(c, neighbor))
                        c.updateAvailability(side, cell.content == null);
                } catch (ArrayIndexOutOfBoundsException ex) {
                    continue;
                }
            }
        }
    }

    private void updateNonAdjacent(int side, Cell cell, Cell neighbor) {
        int x = cell.col;
        int y = cell.row;
        double xDiff = (neighbor.col - cell.col) / 2.0;
        double yDiff = (neighbor.row - cell.row) / 2.0;
        if(Math.abs(xDiff) == 1 && Math.abs(yDiff) == 1) {
            cells[y+(int)yDiff][x+(int)xDiff].updateAvailability(side, cell.content == null);
        } else if (Math.abs(xDiff) == 0.5) {
            cells[y+(int)yDiff][x+(int)Math.floor(xDiff)].updateAvailability(side, cell.content == null);
            cells[y+(int)yDiff][x+(int)Math.ceil(xDiff)].updateAvailability(side, cell.content == null);
        } else if (Math.abs(yDiff) == 0.5) {
            cells[y+(int)Math.floor(yDiff)][x+(int)xDiff].updateAvailability(side, cell.content == null);
            cells[y+(int)Math.ceil(yDiff)][x+(int)xDiff].updateAvailability(side, cell.content == null);
        } else if(xDiff == 0) {
            cells[y+(int)yDiff][x - 1].updateAvailability(side, cell.content == null);
            cells[y+(int)yDiff][x].updateAvailability(side, cell.content == null);
            cells[y+(int)yDiff][x + 1].updateAvailability(side, cell.content == null);
        } else if(yDiff == 0) {
            cells[y - 1][x+(int)xDiff].updateAvailability(side, cell.content == null);
            cells[y][x+(int)xDiff].updateAvailability(side, cell.content == null);
            cells[y+1][x+(int)xDiff].updateAvailability(side, cell.content == null);
        }
    }

    private boolean isAdjacent(Cell cell1, Cell cell2) {
        return (Math.abs(cell1.row - cell2.row)<= 1) && (Math.abs(cell1.col - cell2.col)<= 1);
    }

    public boolean isValidMove(int side, Move move) {
        Cell cell = cells[move.y1][move.x1];
        return (cell.content == null && cell.isAvailable(side));
    }

    public List<Move> getAllLegalMoves(int side) {
        List<Move> list = new ArrayList<>();
        for(int i = 0; i < cells.length; i++) {
            for(int j = 0; j < cells[i].length; j++) {
                Move move = new Move(i, j);
                if(isValidMove(side, move)) list.add(move);
            }
        }
        return list;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Cell:           \tBlack:            \tWhite:\n");
        for(int i = 0; i < cells.length; i++) {
            for(int j = 0; j < cells[i].length; j++) {
                sb.append(cells[i][j]).append(" ");
            }
            sb.append("\t");
            for(int j = 0; j < cells[i].length; j++) {
                sb.append(cells[i][j].isAvailable(Chip.BLACK) ? 'T' : 'F').append(" ");
            }
            sb.append("\t");
            for(int j = 0; j < cells[i].length; j++) {
                sb.append(cells[i][j].isAvailable(Chip.WHITE) ? 'T' : 'F').append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }


    public boolean hasWin(int side) {
        List<Chip> chipList;
        boolean result = false;
        if (side == Chip.BLACK)
            chipList = blackChips;
        else
            chipList = whiteChips;

        for (Chip chip : chipList) {
            if (chip.isStart) {
                //
            }
        }
        return result;
    }



    public static void main(String[] args) {
        Board board = new Board();
        System.out.println(board);

        System.out.println("----測試add move-----");
        System.out.println("put on (2, 1)");
        board.updateOneMove(Chip.BLACK, new Move(2, 1));
        System.out.println("put on (3, 0)");
        board.updateOneMove(Chip.BLACK, new Move(3, 0));
        System.out.println("put on (1, 6)");
        board.updateOneMove(Chip.BLACK, new Move(1, 6));
        System.out.println("put on (2, 4)");
        board.updateOneMove(Chip.BLACK, new Move(2, 4));
        System.out.println("put on (4, 7)");
        board.updateOneMove(Chip.BLACK, new Move(4, 7));
        System.out.println("put on (6, 5)");
        board.updateOneMove(Chip.BLACK, new Move(6, 5));
        System.out.println("put on (5, 2)");
        board.updateOneMove(Chip.BLACK, new Move(5, 2));
        System.out.println("put on (5, 3)");
        board.updateOneMove(Chip.BLACK, new Move(5, 3));
        System.out.println(board);

        System.out.println("----測試step move-----");
        System.out.println("change (1, 6) -> (2, 6)");
        board.updateOneMove(Chip.BLACK, new Move(2, 6,1,6));
        System.out.println(board);
        System.out.println("change (2, 6) -> (1, 6)");
        board.updateOneMove(Chip.BLACK, new Move(1, 6,2,6));
        System.out.println(board);
        System.out.println(board.cells[6][1].content.getAllConnection());
        System.out.println(board.getAllLegalMoves(Chip.BLACK));
        System.out.println(board.cells[0][3].content.isStart);
        System.out.println(board.cells[0][3].content.isEnd);
    }
}
