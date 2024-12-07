package com.example.demo.state;

import com.example.demo.manager.AudioManager;
import com.example.demo.ui.MainMenu;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * MainMenuState class manages the main menu state of the game.
 */
public class MainMenuState implements GameState {
    private final Stage stage;
    private final StateTransitioner stateTransitioner;
    private final AudioManager audioManager;
    private MainMenu mainMenu;
    private Scene scene;

    /**
     * Constructor for MainMenuState.
     *
     * @param primaryStage The primary stage of the application.
     * @param controller   The game controller.
     */
    public MainMenuState(Stage stage, StateTransitioner stateTransitioner, AudioManager audioManager) {
        this.stage = stage;
        this.stateTransitioner = stateTransitioner;
        this.audioManager = audioManager;
    }

    @Override
    public void initialize() {
        // Initialize the HomeMenu and set the scene
        System.out.println("Initializing Main Menu State");
        mainMenu = new MainMenu(stage, stateTransitioner, audioManager);
        this.scene = mainMenu.getHomeMenuScene();
        stage.setScene(this.scene);
        stage.show();
    }

    @Override
    public void update(long now) {
        // No update logic needed for the static main menu
    }


    @Override
    public void handleInput(KeyEvent event) {
        switch (event.getCode()) {
            case SPACE:
                System.out.println("Space pressed DETECTED IN MAIN MENU STATE");
                stateTransitioner.goToLevel(1); 
                break;
            case ESCAPE:
                exitGame(); 
                break;
            default:
                break;
        }
    }

    @Override
    public Scene getScene() {
        return scene;
    }

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
