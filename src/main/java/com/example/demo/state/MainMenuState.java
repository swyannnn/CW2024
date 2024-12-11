package com.example.demo.state;

import com.example.demo.manager.AudioManager;
import com.example.demo.screen.MainMenuScreen;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * MainMenuState is a class that implements the GameState interface and represents
 * the main menu state of the game. It is responsible for initializing the main menu,
 * handling user input, and transitioning to other game states.
 * 
 * @see <a href="https://github.com/swyannnn/CW2024/blob/master/src/main/java/com/example/demo/state/MainMenuState.java">Github Source Code</a>
 * @see GameState
 * @see Stage
 * @see StateTransitioner
 * @see AudioManager
 */
public class MainMenuState implements GameState {
    private final Stage stage;
    private final StateTransitioner stateTransitioner;
    private final AudioManager audioManager;
    private MainMenuScreen mainMenu;
    private Scene scene;

    /**
     * Represents the main menu state of the application.
     * This state is responsible for displaying the main menu and handling user interactions.
     *
     * @param stage The stage on which the main menu is displayed.
     * @param stateTransitioner The object responsible for transitioning between states.
     * @param audioManager The object responsible for managing audio within the application.
     */
    public MainMenuState(Stage stage, StateTransitioner stateTransitioner, AudioManager audioManager) {
        this.stage = stage;
        this.stateTransitioner = stateTransitioner;
        this.audioManager = audioManager;
    }

    /**
     * Initializes the Main Menu State.
     * This method sets up the HomeMenu, assigns the scene to the stage, and displays it.
     */
    @Override
    public void initialize() {
        // Initialize the HomeMenu and set the scene
        mainMenu = new MainMenuScreen(stage, stateTransitioner, audioManager);
        this.scene = mainMenu.getHomeMenuScene();
        stage.setScene(this.scene);
        stage.show();
    }

    /**
     * Updates the state of the main menu.
     * <p>
     * This method is called to update the state of the main menu. However, since the main menu is static and does not require any updates, 
     * this method does not contain any update logic.
     *
     * @param now The current timestamp in nanoseconds.
     */
    @Override
    public void update(long now) {
        // No update logic needed for the static main menu
    }


    /**
     * Handles keyboard input events in the main menu state.
     *
     * @param event the KeyEvent that represents the user's keyboard input
     */
    @Override
    public void handleInput(KeyEvent event) {
        switch (event.getCode()) {
            case SPACE:
                stateTransitioner.goToLevel(1); 
                break;
            case ESCAPE:
                exitGame(); 
                break;
            default:
                break;
        }
    }

    /**
     * Retrieves the current scene associated with the main menu state.
     *
     * @return the current Scene object.
     */
    @Override
    public Scene getScene() {
        return scene;
    }

    /**
     * Cleans up resources or performs any necessary finalization tasks 
     * when the state is no longer needed.
     */
    @Override
    public void cleanup() {
    }

    /**
     * Handles the action of exiting the game.
     */
    private void exitGame() {
        stage.close();
    }
}
