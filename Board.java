package model;

/**
 * This class represent the Board of the Othello game.
 */
public class Board  {
    private final int rows;
    private final int cols;
    private final Square[][] board;

    private static final String SPACE = "     ";
    private static final String ROW_LINE = "---+---+---+---+---+---+---+---+";
    private static final String[] NUMBERED_BOARD = {" 00 | 01| 02| 03| 04| 05| 06| 07", "---+---+---+---+---+---+---+---+", " 08 | 09| 10| 11| 12| 13| 14| 15", "---+---+---+---+---+---+---+---+", " 16 | 17| 18| 19| 20| 21| 22| 23 ", "---+---+---+---+---+---+---+---+", " 24 | 25| 26| 27| 28| 29| 30| 31 ", "---+---+---+---+---+---+---+---+", " 32 | 33| 34| 35| 36| 37| 38| 39 ", "---+---+---+---+---+---+---+---+", " 40 | 41| 42| 43| 44| 45| 46| 47 ", "---+---+---+---+---+---+---+---+", " 48 | 49| 50| 51| 52| 53| 54| 55 ", "---+---+---+---+---+---+---+---+"," 56 | 57| 58| 59| 60| 61| 62| 63 "};

    /**
     * Gets the number of rows.
     * @return returns the number of rows.
     */
    public int getRows() {
        return rows;
    }

    /**
     *  Gets the number of columns.
     * @return returns the number of columns.
     */
    public int getCols() {
        return cols;
    }

    /**
     * This is the constructor of the Board class. It initializes the starting positions of
     * Othello game.
     * @param rows the rows of the Board.
     * @param cols the columns of the Board.
     */
    public Board (final int rows, final int cols) {
        this.rows = rows;
        this.cols = cols;
        this.board = new Square[rows][cols];
        for(int i = 0; i<rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.board[i][j] = Square.EMPTY;
            }
        }
        setSquareOnBoard(3,3,Square.WHITE);
        setSquareOnBoard(3,4,Square.BLACK);
        setSquareOnBoard(4,3, Square.BLACK);
        setSquareOnBoard(4,4,Square.WHITE);
    }

    /**
     * This sets the disc color on the board by row and column. It specifies the position on where the disc
     * will be placed on the board of the game.
     * @param row the row on which the square will be placed.
     * @param col the column on which the square will be placed.
     * @param colour the colour of the square that we would like to place.
     */
    public void setSquareOnBoard(int row, int col, Square colour){
        if (getContent(row, col).equals(Square.EMPTY)) {
            board[row][col] = colour;
        }
    }

    /**
     *  This method removes a square and sets the content to empty.
     * @param row the row on which the square will be removed.
     * @param col the column on which the square will be removed.
     */
    public void removeSquareFromBoard(int row, int col) {
        board[row][col] = Square.EMPTY;
    }

    /**
     * This returns the contents of the board based on the rows and the columns.
     * @param row the row on which we would like to get the content of.
     * @param col the column on which we would like to get the content of.
     * @return the board content of this row and column.
     */
    public Square getContent(int row, int col) {
        return board[row][col];
    }

    /**
     * This method checks to see if the board is full.
     * @return true if board is completely filled else false if the board is not full.
     */
    public boolean isBoardFull(){
        for(int i = 0; i<rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == Square.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Creates the board representation to printed in the terminal.
     * @return the board with the squares, and the numbers.
     */
    @Override
    public String toString() {
        StringBuilder boardString = new StringBuilder();
        for (int row = 0; row < rows; row++) {
            boardString.append(buildRowString(row));
            boardString.append(SPACE).append(NUMBERED_BOARD[row * 2]);

            if (row < rows - 1) {
                boardString.append("\n").append(ROW_LINE).append(SPACE).append(NUMBERED_BOARD[row * 2 + 1]).append("\n");
            }
        }
        return boardString.toString();
    }

    /**
     * Makes the row with the squares.
     * @param row current row.
     * @return String representation of a row.
     */
    private String buildRowString(int row) {
        StringBuilder rowString = new StringBuilder();
        for (int col = 0; col < getCols(); col++) {
            rowString.append(buildCellString(getContent(row, col)));

            if (col < getCols() - 1) {
                rowString.append("|");
            }
        }
        return rowString.toString();
    }
    /**
     * Makes the columns with the squares.
     * @param square the square color
     * @return String representation of the cell.
     */
    private String buildCellString(Square square) {
        if (square.equals(Square.BLACK)) {
            return " " + square.toString().charAt(0) + " ";
        } else if (square.equals(Square.WHITE)) {
            return " " + square.toString().charAt(0) + " ";
        } else {
            return "   ";
        }
    }

    /**
     * This method calculates the score for the player disc color.
     * @param colour takes the input of the colour of the disc
     * @return returns the score of the disc color.
     */
    public int calculateTheScore(Square colour){
        int score = 0;
        for(int i = 0; i<rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (this.board[i][j] == colour) {
                    score++;
                }
            }
        }
        return score;
    }

    /**
     * This method checks the rows and columns are inside the bounds of the board.
     * @param row the row on which the square in bound will be checked.
     * @param col the column on which the square in bound will be checked.
     * @return returns true if the board is in bounds and else if its not.
     */
    public boolean isInBounds(int row, int col){
        return row>=0 && row<rows && col>=0 && col<cols;
    }

    /**
     * This method calculates the index position using rows and columns and returns single integer index of that position.
     * @param column the column of the index position of the board
     * @return returns the index position.
     */
    public int getIndexPosition(int row, int column) {
        return row * 8 + column;
    }

    /**
     * This method converts the index into row and column coordinates.
     * @param index the index position to be converted.
     * @return returns the row and column of the converted index in the form of an array.
     */
    public int[] getRowAndColumnBasedOnIndex(int index) {
        int row = (index / 8) ;
        int column = (index % 8);

        return new int []{row,column};
    }
}
