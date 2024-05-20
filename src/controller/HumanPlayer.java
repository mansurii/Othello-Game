package controller;

import model.Game;
import model.Move;
import model.Player;
import model.Square;

import java.util.Scanner;

/**
 * This class represents the human player in the game.
 * It takes input from the human player and checks to see if they can perform their valid move in the game.
 * This class stores the information about the  human player namely their name and their square color.
 */
public class HumanPlayer extends Player {
    private final Scanner scanner;

    /**
     * This method constructs a new human player based on the specified arguments the name and the square color.
     * This class extends the player class and inherits the attributes from the player class such as the name and the square color.
     * It also initializes a new scanner object.
     * @param name the name of the computer player.
     * @param color the square color of the computer player.
     */
    public HumanPlayer(String name, Square color) {
        super(name, color);
        scanner = new Scanner(System.in);
    }

    /**
     * This method defines the logic for performing moves of a human player in the game based on the current state of the board in the game.
     * If a human player cannot make any more valid moves they should pass their turn so that the other player can take their turn.
     * The human player must enter valid integer within the range of (0-63) to perform a valid move and to avoid invalid index message.
     * It gets all the valid moves of the human player to select from.
     * @param game the current game being played.
     * @return the current move of a human player in the game.
     */
    @Override
    public Move makeMove(Game game) {
        Move makeMove;

        while (true) {
         System.out.println("Type best if you want to get the best available move");
         String input = scanner.nextLine();

          if (input.equals("best")) {
             BestMove bestMove = new BestMove(game, this);
             System.out.println("Your best move is: " + bestMove.getBestMove().getIndex());
          }

           int enterNum;

           try {
               enterNum = Integer.parseInt(input);
           } catch (NumberFormatException e) {
               System.out.println("Please enter a valid input or type best for the best move!");
               continue;
           }

            if (game.getAllValidMoves(getColor()).isEmpty() && enterNum == -1) {
                return new Move(-1, -1, getColor());
            } else if (!game.getAllValidMoves(getColor()).isEmpty() && enterNum == -1){
                System.out.println("There are valid moves to play. -1 represents a pass turn.");
                continue;
            }

            if (enterNum >= 0 && enterNum <= 64) {
                int[] move = game.returnBoard().getRowAndColumnBasedOnIndex(enterNum);
                makeMove = new Move(move[0], move[1], getColor());
                if (game.isValidMove(makeMove)) {
                    break;
                } else {
                    System.out.println("Move is not valid!");
                }
            } else {
                System.out.println("Invalid index, please try again!");
            }
        }
        return makeMove;
    }
}
