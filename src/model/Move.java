package model;
/**
 * This class represents a move in the game.
 * The class stores the rows, columns and square color when a move is made.
 */
public class Move {
    private final int row;
    private final int col;
    private final Square square;

    /**
     * This is the constructor of the move class. It creates a new move in the Othello game.
     * @param row the row of the move.
     * @param col the column of the move.
     * @param square the square color  of the move.
     */
    public Move(int row, int col, Square square) {
        this.row = row;
        this.col = col;
        this.square = square;
    }

    /**
     * This method returns the row of this move.
     * @return the row of the move.
     */
    public int getRow() {
        return row;
    }

    /**
     * This method returns the column of this move.
     * @return the column of the move
     */
    public int getCol() {
        return col;
    }

    /**
     * This method returns the square color of this move.
     * @return the square color of the move.
     */
    public Square getSquare() {
        return square;
    }

    /**
     * This method calculates the index position of the row and column.
     * @return the index by multiplying the current row (of the move) by 8 and adding the column.
     */
    public int getIndex() {
        return getRow() * 8 + getCol();
    }

    /**
     * This method prints the string representation of this move.
     * @return the row, the column and the square color of this move.
     */
    @Override
    public String toString() {
        return "Move{" +
                "row=" + row +
                ", col=" + col +
                ", square=" + square +
                '}';
    }


}