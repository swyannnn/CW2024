package com.example.demo.state;

import com.example.demo.ui.WinScreen;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * The WinState class implements the GameState interface and represents the state
 * of the game when the player has won. It manages the display of the win screen
 * and handles user input to transition to other states or exit the game.
 */
public class WinState implements GameState {
    private final Stage stage;
    private final StateTransitioner stateTransitioner;
    private WinScreen winScreen;
    private Scene scene;

    /**
     * Constructs a new WinState with the specified stage and state transitioner.
     *
     * @param stage the stage associated with this state
     * @param stateTransitioner the state transitioner to manage state transitions
     */
    public WinState(Stage stage,  StateTransitioner stateTransitioner) {
        this.stage = stage;
        this.stateTransitioner = stateTransitioner;
    }

    /**
     * Initializes the WinState by setting up the WinScreen and its scene.
     * This method creates a new instance of WinScreen, retrieves its scene,
     * sets the scene on the stage, and then displays the stage.
     */
    @Override
    public void initialize() {
        // Initialize the WinScreen and set the scene
        System.out.println("Initializing Win State");
        winScreen = new WinScreen(stage, stateTransitioner);
        this.scene = winScreen.getWinScreenScene();
        stage.setScene(this.scene);
        stage.show();
    }

    /**
     * Retrieves the current scene associated with the win state.
     *
     * @return the current scene
     */
    @Override
    public Scene getScene() {
        return scene;
    }

    /**
     * Updates the state of the game. This method is called periodically to 
     * update the game's state based on the current time. In the case of the 
     * win state, no update logic is needed as the win screen is static.
     * 
     * @param now the current timestamp in nanoseconds
     */
    @Override
    public void update(long now) {
        // No update logic needed for static win screen
    }

    /**
     * Handles input events specific to the win state. This method processes
     * key events to transition to other states or exit the game.
     * 
     * @param event the KeyEvent to be processed
     * 
     * If the ENTER key is pressed, the game transitions to the main menu state.
     * If the ESCAPE key is pressed, the game exits.
     */
    @Override
    public void handleInput(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            KeyCode keyCode = event.getCode();
            switch (keyCode) {
                case ESCAPE:
                    stage.close(); // Exit the game
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Cleans up resources or performs any necessary finalization for the win state.
     * In this implementation, no cleanup is needed for the static win screen.
     */
    @Override
    public void cleanup() {
        // No cleanup needed for static win screen
    }
}
