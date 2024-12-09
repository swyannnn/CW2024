package com.example.demo.state;

import com.example.demo.screen.LoseScreen;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


/**
 * The LoseState class implements the GameState interface and represents the state
 * of the game when the player has lost. It manages the display of the lose screen
 * and handles user input to transition to other states or exit the game.
 * 
 * @see <a href="https://github.com/swyannnn/CW2024/blob/master/src/main/java/com/example/demo/state/LoseState.java">Github Source Code</a>
 * @see StateTransitioner
 * @see GameState
 * @see LoseScreen
 */
public class LoseState implements GameState {
    private final Stage stage;
    private final StateTransitioner stateTransitioner;
    private LoseScreen loseScreen;
    private Scene scene;

    /**
     * Constructs a new LoseState with the specified stage and state transitioner.
     *
     * @param stage the stage associated with this state
     * @param stateTransitioner the state transitioner to manage state transitions
     */
    public LoseState(Stage stage, StateTransitioner stateTransitioner) {
        this.stage = stage;
        this.stateTransitioner = stateTransitioner;
    }

    /**
     * Initializes the LoseState by setting up the LoseScreen and its scene.
     * This method creates a new instance of LoseScreen, retrieves its scene,
     * sets the scene on the stage, and then displays the stage.
     */
    @Override
    public void initialize() {
        System.out.println("Initializing Lose State");
        loseScreen = new LoseScreen(stage, stateTransitioner);
        this.scene = loseScreen.getLoseScreenScene();
        stage.setScene(this.scene);
        stage.show();
    }

    /**
     * Retrieves the current scene associated with the lose state.
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
     * lose state, no update logic is needed as the lose screen is static.
     *
     * @param now The current timestamp in nanoseconds.
     */
    @Override
    public void update(long now) {
        // No update logic needed for static lose screen
    }

    /**
     * Handles keyboard input events when the game is in the "Lose" state.
     * 
     * @param event the KeyEvent that triggered this handler
     * 
     * If the ENTER key is pressed, the game transitions to the main menu.
     * If the ESCAPE key is pressed, the game window is closed.
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
     * Cleans up resources or state when the lose screen is no longer needed.
     * In this implementation, no cleanup is required for the static lose screen.
     */
    @Override
    public void cleanup() {
        // No cleanup needed for static lose screen
    }
}
