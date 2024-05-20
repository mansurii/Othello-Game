package model;

/**
 * This Player Class represents  a player in a game.
 * This class stores the information about the player namely their name and their square color.
 */
public abstract class Player {
    private final String name;
    private final Square color;

    /**
     * This method constructs a new player based on the specified arguments the name and the square color.
     * @param name the name of the player.
     * @param color the square color of the player.
     */
    public Player(String name, Square color){
        this.name = name;
        this.color = color;
    }

    /**
     * This method returns the name of this player.
     * @return the name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * This method returns the square color of this player.
     * @return the square color of the player.
     */
    public Square getColor() {
        return color;
    }

    /**
     * This abstract method makes a move in the game. This method defines the logic for performing moves in the game based
     * on the current state of the board in the game.
     * @param game the current game being played.
     * @return the move of a player in the game.
     */
    public abstract Move makeMove(Game game);

    /**
     * This method prints the string representation of this player.
     * @return the name and the square color of this player.
     */
    @Override
    public String toString(){
        return "Name: " + getName() + "\nColor: " + getColor();
    }
}
