package model;

import java.util.ArrayList;
import java.util.List;

/**
 *  This class represents the board, the players, the turns and the logic
 *  to check all valid movies, flip opposing player's discs, adds all the valid moves,
 *  checks all the eight directions of the board for valid movies, calculates
 *  the player score, check who won, checks both players  to see  if it's draw
 *  or checks to see who is the winner, and It also returns the board.
 */
public class Game {
    private final Board board;
    private final Player player1;
    private final Player player2;
    private Player playersTurn;

    /**
     *This is the constructor of the game class. It creates a new board for the game,
     * add the players to the game, and gives the turn to the
     * first player (with the black square).
     * @param player1 the first player of the game (black square).
     * @param player2 the second player of the game (white square).
    */
    public Game(Player player1, Player player2) {
        this.board = new Board(8,8);
        this.player1 = player1;
        this.player2 = player2;
        this.playersTurn = player1;
    }

    /**
     * This method will flip all the discs that are passed in as a parameter.
     * It will be used when a move is made, and the opponents disks on the board have
     * to be flipped (change colour).
     * @param discsToFlip index fields on the board to be flipped to the other colour.
     */
    public void flipPlayerDiscs(ArrayList<Integer> discsToFlip) {
        for(int disc : discsToFlip) {
            int [] rowAndCol = board.getRowAndColumnBasedOnIndex(disc);
            Square square = board.getContent(rowAndCol[0], rowAndCol[1]);
            if (square.equals(Square.BLACK)) {
                board.removeSquareFromBoard(rowAndCol[0], rowAndCol[1]);
                board.setSquareOnBoard(rowAndCol[0], rowAndCol[1], Square.WHITE);
            } else if (square.equals(Square.WHITE)) {
                board.removeSquareFromBoard(rowAndCol[0], rowAndCol[1]);
                board.setSquareOnBoard(rowAndCol[0], rowAndCol[1], Square.BLACK);
            }
        }
    }

    /**
     * This method has three checks. First it checks if the move is in bounds.
     * Then it checks if the field that the move wants to play to is empty.
     * Then it checks if the move flip at least one of the opponents square.
     * @param move the move that the player wants to do on the board
     * @return true if the conditions above are met, false otherwise.
     */
    public boolean isValidMove(Move move) {
        if(board.isInBounds(move.getRow(),move.getCol())) {
            if(board.getContent(move.getRow(),move.getCol()).equals(Square.EMPTY)) {
                if (getTurn().getColor().equals(move.getSquare())) {
                    return !(checkRightHorizontally(move).isEmpty() && checkLeftHorizontally(move).isEmpty()
                            && checkUpVertically(move).isEmpty() && checkDownVertically(move).isEmpty() &&
                            checkTopLeftDiagonally(move).isEmpty() && checkTopRightDiagonally(move).isEmpty() &&
                            checkBottomLeftDiagonally(move).isEmpty() && checkBottomRightDiagonally(move).isEmpty());
                }
            }
        }
        return false;
    }

    /**
     * This method generates moves, checks if there are valid for the specific square
     * and then adds them to list.
     * @param color takes the current square of the player of which we want to calculate the valid moves.
     * @return all the valid moves from the list.
     */
    public List<Integer> getAllValidMoves(Square color) {
        List<Integer> allTheValidMoves = new ArrayList<>();
        for(int i = 0; i< board.getRows(); i++) {
            for(int j = 0; j<board.getCols(); j++) {
                Move move = new Move(i,j,color);
                if(isValidMove(move)) {
                  int indexPosition = board.getIndexPosition(i,j);
                  allTheValidMoves.add(indexPosition);
                }
            }
        }
        return allTheValidMoves;
    }

    /**
     * This method gets the player by the square color.
     * @param squareColor takes the square color to be checked.
     * @return returns player 1 if its equal to black, else returns player 2.
     */
    public Player getPlayerBySquare(Square squareColor) {
        if (squareColor.equals(Square.BLACK)) {
            return player1;
        } else {
            return player2;
        }
    }

    /**
     * This method executes the move of the player and calls the method to flip the opponents discs
     * @param move takes the current move of the player
     * @param flag indicates if this method is used in the BestMove class, so the move doesn't actually have to be played.
     */
    public int doMove(Move move, boolean flag) {
        //check if the move is -1 which represents the passing move.
        if (move.getRow() == -1  && move.getCol() == -1 ) {
            if  (!getAllValidMoves(move.getSquare()).isEmpty()) {
                System.out.println("You cannot pass turn because there are valid moves to be played");
            } else {
                return 0;
            }
        }
        if (!isValidMove(move)) {
            return 0;
        }
        ArrayList<Integer> movesThatWillFlip = new ArrayList<>();

        movesThatWillFlip.addAll(checkRightHorizontally(move));
        movesThatWillFlip.addAll(checkLeftHorizontally(move));
        movesThatWillFlip.addAll(checkUpVertically(move));
        movesThatWillFlip.addAll(checkDownVertically(move));
        movesThatWillFlip.addAll(checkTopRightDiagonally(move));
        movesThatWillFlip.addAll(checkTopLeftDiagonally(move));
        movesThatWillFlip.addAll(checkBottomRightDiagonally(move));

        if (flag) {
            flipPlayerDiscs(movesThatWillFlip);
            board.setSquareOnBoard(move.getRow(), move.getCol(), move.getSquare());
            System.out.println("Player " + getPlayerBySquare(move.getSquare()).getName() + " played: " + move.getIndex() + ".");
        }
        return movesThatWillFlip.size();
    }

    /**
     * This method checks the direction of the board based on the direction of the method called.
     * @param move current move of the player
     * @param rowIncrement increments the row by 1 if we are going down, -1 if we are going up.
     * @param columnIncrement increments the  columns by 1 if we are going right ,-1 if we are going up.
     * @return the indexes of the squares to be flipped.
     */
    public ArrayList<Integer> checkDirection(Move move, int rowIncrement, int columnIncrement) {
        ArrayList<Integer> moveThatCanFlip = new ArrayList<>();

        for (int row = move.getRow() + rowIncrement, col = move.getCol() + columnIncrement;
             row >= 0 && row < board.getRows() && col >= 0 && col < board.getCols();
             row += rowIncrement, col += columnIncrement) {

            if (board.getContent(row, col).equals(Square.EMPTY)) {
                break;
            } else if (board.getContent(row, col).equals(move.getSquare().getOtherPlayerSquare())) {
                moveThatCanFlip.add(board.getIndexPosition(row, col));
            } else if (board.getContent(row, col).equals(move.getSquare())) {
                if (!moveThatCanFlip.isEmpty()) {
                    return new ArrayList<>(moveThatCanFlip);
                } else {
                    break;
                }
            }
        }
        return new ArrayList<>();
    }

    /**
     * This method checks Right Horizontally which means that the row has to remain the same
     * and the columns must be incremented by 0 at every iteration of the checkDirection() method.
     * @param move the move that the user played.
     * @return the list indexes that are flipped by this move from the move towards right horizontal.
     */
    public ArrayList<Integer> checkRightHorizontally(Move move) {
        return checkDirection(move, 0,1);
    }

    /**
     * This method checks Left Horizontally which means that the row has to remain the same
     * and the columns must be decremented by 1 at every iteration of the checkDirection() method.
     * @param move the move that the user played.
     * @return the list indexes that are flipped by this move from the move towards left horizontal.
     */
    public ArrayList<Integer> checkLeftHorizontally(Move move) {
        return checkDirection(move, 0,-1);
    }

    /**
     * This method checks Check Up Vertically which means that the row has to be decremented by 1
     * the column has to remain the same at every iteration of the checkDirection() method..
     * @param move the move that the user played.
     * @return the list indexes that are flipped by this move from the move towards up vertically.
     */
    public ArrayList<Integer> checkUpVertically(Move move) {
        return checkDirection(move,-1,0);
    }
    /**
     * This method checks Check Down Vertically which means that the row has to be incremented by 1
     * the column has to remain the same at every iteration of the checkDirection() method.
     * @param move the move that the user played.
     * @return the list indexes that are flipped by this move from the move towards down vertically.
     */
    public ArrayList<Integer> checkDownVertically(Move move) {
        return checkDirection(move, 1,0);
    }

    /**
     * This method checks Top Right Diagonally  which means that the row has to be decremented by 1
     * the column must be incremented by 1 at every iteration of the checkDirection() method.
     * @param move the move that the user played.
     * @return the list indexes that are flipped by this move from the move towards top right diagonally.
     */
    public ArrayList<Integer> checkTopRightDiagonally(Move move) {
        return checkDirection(move,-1,1);
    }

    /**
     * This method checks Top Left Diagonally  which means that the row has to be decremented by 1
     * the column must be decremented by 1 at every iteration of the checkDirection() method.
     * @param move the move that the user played.
     * @return the list indexes that are flipped by this move from the move towards top left diagonally.
     */
    public ArrayList<Integer> checkTopLeftDiagonally(Move move) {
        return checkDirection(move,-1,-1);
    }

    /**
     * This method checks Bottom Right Diagonally  which means that the row has to be incremented by 1
     * the column must be incremented by 1 at every iteration of the checkDirection() method.
     * @param move the move that the user played.
     * @return the list indexes that are flipped by this move from the move towards bottom right diagonally.
     */
    public ArrayList<Integer> checkBottomRightDiagonally(Move move) {
        return checkDirection(move,1,1);
    }

    /**
     * This method checks Bottom Left Diagonally  which means that the row has to be incremented by 1
     * the column must be decremented  by 1 at every iteration of the checkDirection() method.
     * @param move the move that the user played.
     * @return the list indexes that are flipped by this move from the move towards bottom left diagonally.
     */
    public ArrayList<Integer> checkBottomLeftDiagonally(Move move) {
        return checkDirection(move,1,-1);
    }

    /**
     * This method checks to see if the board is full.
     * @return returns true if the board is full, otherwise false.
     */
    public boolean isGameOver() {
        return board.isBoardFull() || bothPlayersPass();
    }

    /**
     * This method checks if the game is over and both players scores are equal.
     * This means that the game ended in draw.
     * @return true if game is over and if both scores are equal, false otherwise.
     */
    public boolean isDraw() {
        return isGameOver() && (board.calculateTheScore(Square.BLACK) == board.calculateTheScore(Square.WHITE));
    }

    /**
     * This method checks to see if both players have not valid moves left to play.
     * @return true if both white and black square is empty from the getAllValidMoves method, false otherwise.
     */
    public boolean bothPlayersPass() {
        return getAllValidMoves(Square.WHITE).isEmpty() && getAllValidMoves(Square.BLACK).isEmpty();
    }

    /**
     * This method gets the player turn.
     * @return return the Player who turn it is.
     */
    public Player getTurn() {
        return playersTurn;
    }

    /**
     * This method changes the players turn.
     */
    public void changeTurns() {
        if (playersTurn.equals(player1)) {
            playersTurn = player2;
        } else {
            playersTurn = player1;
        }
    }
    /**
     * This method calculates the score of the player based on the disc color.
     * @param player the player who score will be calculated.
     * @return returns the score of that player.
     */
    public int calculatePlayerScore(Player player) {
        return board.calculateTheScore(player.getColor());
    }

    /**
     * This method returns the winner based on who scored the highest in the game.
     * @return returns player1 if the score is higher than player2.
     * returns player2 If  the score is higher than player 1.
     * If the game is a tie, null is returned;
     */
    public Player getWinner() {
        if (calculatePlayerScore(player1) > calculatePlayerScore(player2)) {
            return player1;
        } else if (calculatePlayerScore(player2) > calculatePlayerScore(player1)) {
            return player2;
        } else {
            return null;
        }
    }

    /**
     * This method returns the current state of the board in the game.
     * @return returns the board.
     */
    public Board returnBoard() {
        return board;
    }


}