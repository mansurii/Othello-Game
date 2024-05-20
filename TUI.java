package view;

import controller.ComputerPlayer;
import controller.HumanPlayer;
import model.*;

import java.util.Scanner;

/**
 * This class represents the CLI of an othello game. Both states of the current players is displayed.
 * This class creates a game, interacts with all the other classes to form the game so that two players can play the game.
 * It also takes input from the user and display all current valid moves a player can make
 * and shows all the current moves placed on the board for both players.
 */
public class TUI {
    /**
     * This main method to run the TUI class.
     * @param args ignored
     */
    public static void main(String[] args) {
        System.out.println("Welcome to the othello game");
        new TUI().playGame();
    }

    /**
     *  This method initialise two players, requires them to enter their chosen name and select whether they would like to play with human player or computer player.
     *  Sets the positions of both players in the game.
     *  It displays the current name and scores of both players and all the valid moves each player can make.
     *  If a player has taken their turn, then the next player will be prompted to enter their move. If neither player can make a valid move they must enter -1 which indicates a pass.
     *  It checks to see if both players have a draw or a player has won the game.
     *  Once the game is the finished it displays game over and displays the current score the game finished at for both players.
     *  To display the winner after the game is finished, the opposing player's score must be higher than other player's score.
     *  The user will be prompted to start a new game. If they wish to play a new game they must enter y otherwise n.
     */
    public void playGame(){
        Scanner stdin = new Scanner(System.in);
        Player player1 = null;
        Player player2 = null;
        for (int i = 1; i<=2; i++) {
            System.out.println("Enter Player " +  i + "'s name:");
            String playerName = stdin.nextLine();
            System.out.println("Please choose 1 if you would like to play with human player or " +
                    "2 if you would like to play with the computer player");

            String choice = stdin.nextLine();
            while(!(choice.equals("1") || choice.equals("2"))){
                System.out.println("Invalid input. Please enter 1 for a human player or 2 for a computer player.");
                choice = stdin.nextLine();
            }

            if (i == 1 ){
                if(choice.equals("1")){
                    player1 = new HumanPlayer(playerName, Square.BLACK);
                } else {
                    player1 = new ComputerPlayer(playerName, Square.BLACK);
                }
            } else {
                if(choice.equals("1")){
                    player2 = new HumanPlayer(playerName, Square.WHITE);
                } else {
                    player2 = new ComputerPlayer(playerName, Square.WHITE);
                }
            }
        }

        Game game = new Game(player1,player2);
        while (!game.isGameOver()) {
            System.out.println(game.returnBoard());

            System.out.println(
                    "Current score is: \n" +
                            player1.getName() + " (BLACK): " + game.calculatePlayerScore(player1) + "\n" +
                            player2.getName() + " (WHITE): " + game.calculatePlayerScore(player2)
            );

            Player currentPlayer = game.getTurn();

            if (game.getAllValidMoves(currentPlayer.getColor()).isEmpty()) {
                System.out.println("There are no valid moves for you to play. Type -1 to pass your turn.");
            } else {
                System.out.println(currentPlayer.getName() + ", enter number for your move" + "(e.g." + game.getAllValidMoves(currentPlayer.getColor()) + "):");
            }
            game.doMove(currentPlayer.makeMove(game), true);
            game.changeTurns();
        }

        if (game.getWinner() == null && game.isDraw()) {
            System.out.println("Game was draw");
        } else {
            System.out.println(game.returnBoard());
            System.out.println("Game over. Player : " + game.getWinner().getName() +  " (" + game.getWinner().getColor() + ")" + " won."); System.out.println(
                    "Score of the game finished at: \n" +
                            player1.getName() + " (BLACK): " + game.calculatePlayerScore(player1) + "\n" +
                            player2.getName() + " (WHITE): " + game.calculatePlayerScore(player2)
            );
        }

        System.out.println("Would you like to play a new game (Y/N).");
        String playerInput = stdin.nextLine().toLowerCase();

        while(!(playerInput.equals("y") || playerInput.equals("n"))){
            System.out.println("Please enter y/n");
            playerInput = stdin.nextLine().toLowerCase();
        }

        if (playerInput.equals("y")) {
           playGame();
        } else {
            System.out.println("Thanks for playing, goodbye!");
        }
    }
}
