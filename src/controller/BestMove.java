package controller;

import model.Game;
import model.Move;
import model.Player;

import java.util.List;

/**
 * This class represents the best move decision in the othello game.
 * This class includes the logic to determine the best possible move for a player based on the current state of the game.
 * It calculates the player current score based on the current state of the game. It will check to find the highest  move to be flipped count
 * and return the highest number of moves that can be flipped for each player depending on the valid moves left.
 */
public class BestMove {
    private final Game game;
    private final Player player;

    /**
     * This method constructs a new best move based on the specified arguments the game and the player.
     * @param game the current state of the game
     * @param player the player for which the best move is being calculated for.
     */
    public BestMove(Game game, Player player) {
       this.game = game;
       this.player = player;
    }

    /**
     * This method checks all the valid moves left for each player in the game. It will check the valid moves
     * to see if it contains any of the edges values.
     * If one of the edge values is found, then a new move will be returned with the row and column of the index position on the board and square
     * color of player trying to flip the opponents  discs.
     * If  only one valid move is present, then that moved will be returned in a new move object.
     * If the above conditions are not satisfied, then this algorithm will be executed to check to find the best move in the game.
     * This algorithm loop over all the valid moves and evaluates each move to compare
     * and find the best possible move the player can move that player can make to flip the most discs on the opponents side of the board.
     * @return the current best move for this player.
     */

    public Move getBestMove() {
        List<Integer> validMoves = game.getAllValidMoves(player.getColor());
        if  (validMoves.isEmpty()) {
            System.out.println("Your best move is to pass your turn! So -1!");
            return new Move(-1,-1,player.getColor());
        }

        int [] edge = {0,7,56,63};
        for (int edgeNumber : edge) {
            if (validMoves.contains(edgeNumber)){
                int [] array = game.returnBoard().getRowAndColumnBasedOnIndex(edgeNumber);
                return new Move(array[0],array[1],player.getColor());
            }
        }

        if (validMoves.size() == 1){
            int [] tempArray = game.returnBoard().getRowAndColumnBasedOnIndex(validMoves.get(0));
            return new Move(tempArray[0], tempArray[1], player.getColor());
        }

        Move currentBestMove = null;
        int bestMovesDiscs = 0;
        for (Integer moveIndex : validMoves) {
            int[] tempArray = game.returnBoard().getRowAndColumnBasedOnIndex(moveIndex);
            Move tempMove = new Move(tempArray[0], tempArray[1], player.getColor());
            int tempDiscs = game.doMove(tempMove, false);

            if (tempDiscs >= bestMovesDiscs) {
                bestMovesDiscs = tempDiscs;
                currentBestMove = tempMove;
            }
        }
        return currentBestMove;
    }
}
