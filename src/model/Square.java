package model;

/**
 * This class represents the square color and the state of the colors of the player in the game.
 * Both players will be assigned the discs according to the Othello rules.
 * The game has a white and black discs.
 * If not disc is placed on the board, then empty is placed to signify that space is available for the player to place their discs based on the condition their move is valid.
 */
public enum Square {
    BLACK, WHITE, EMPTY;

    /**
     * This method gets opposing players square color.
     * @return the square color of the other player, else returns empty square.
     * Players cannot have empty square, they must be either white or black.
     */
    public Square getOtherPlayerSquare() {
        if (this == Square.BLACK) {
            return Square.WHITE;
        } else if (this == Square.WHITE) {
            return Square.BLACK;
        }
        return Square.EMPTY;
    }
}