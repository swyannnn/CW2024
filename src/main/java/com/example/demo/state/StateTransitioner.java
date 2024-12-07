package com.example.demo.state;

/**
 * The StateTransitioner interface defines the methods required for transitioning 
 * between different states in a game or application. Implementations of this 
 * interface will handle the logic for moving between states such as levels, 
 * main menu, win state, and lose state.
 */
public interface StateTransitioner {
    /**
     * Sets the number of players for the game.
     *
     * @param numberOfPlayers the number of players to be set
     */
    void setNumberOfPlayers(int numberOfPlayers);

    /**
     * Retrieves the number of players.
     *
     * @return the number of players
     */
    int getNumberOfPlayers();

    /**
     * Transitions to the specified level.
     *
     * @param levelNumber the number of the level to transition to
     */
    void goToLevel(int levelNumber);

    /**
     * Transitions the application to the main menu state.
     */
    void goToMainMenu();
    
    /**
     * Transitions the current state to the win state.
     */
    void goToWinState();

    /**
     * Transitions the current state to the "Lose" state.
     */
    void goToLoseState();
}
