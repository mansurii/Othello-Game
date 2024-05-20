package controller;

import model.Game;
import model.Move;
import model.Player;
import model.Square;

import java.util.List;
import java.util.Random;

/**
 * This class represents the computer player in the game.
 * This class stores the information about the  computer player namely their name and their square color.
 */
public class ComputerPlayer extends Player {

    /**
     * This method constructs a new computer player based on the specified arguments the name and the square color.
     * This class extends the player class and inherits the attributes from the player class such as the name and the square color.
     * @param name the name of the computer player.
     * @param color the square color of the computer player.
     */
    public ComputerPlayer(String name, Square color) {
        super(name, color);
    }

    /**
     * This method defines the logic for performing moves of a computer player in the game based on the current state of the board in the game.
     * If a computer player cannot make any more valid moves they should pass their turn so that the other player can take their turn.
     * It gets all the valid moves of the computer player and selects a random move.
     * @param game the current game being played.
     * @return the current move of a computer player in the game.
     */
    @Override
    public Move makeMove(Game game) {
        if  (!game.getTurn().getColor().equals(getColor())){
            return null;
        }
        List<Integer> validMoves = game.getAllValidMoves(getColor());
        if(validMoves.isEmpty()){
            System.out.println("Should pass");
            return new Move(-1, -1, getColor());
        } else {
            Random random = new Random();
            int index = random.nextInt(validMoves.size());

            int value = validMoves.get(index);
            int[] getRowsAndColsOfIndex = game.returnBoard().getRowAndColumnBasedOnIndex(value);

            return new Move(getRowsAndColsOfIndex[0],getRowsAndColsOfIndex[1],getColor());
        }
    }
}
