package com.example.demo.state;

import com.example.demo.MainMenu;
import com.example.demo.controller.Controller;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * MainMenuState class manages the main menu state of the game.
 */
public class MainMenuState implements GameState {
    private final Stage primaryStage;
    private final Controller controller;
    private MainMenu mainMenu;

    /**
     * Constructor for MainMenuState.
     *
     * @param primaryStage The primary stage of the application.
     * @param controller   The game controller.
     */
    public MainMenuState(Stage primaryStage, Controller controller) {
        this.primaryStage = primaryStage;
        this.controller = controller;
    }

    @Override
    public void initialize() {
        // Initialize the HomeMenu and set the scene
        mainMenu = new MainMenu(primaryStage, controller);
        primaryStage.setScene(mainMenu.getHomeMenuScene());
        primaryStage.show();
    }

    @Override
    public void update() {
        // No update logic needed for the static main menu
    }

    @Override
    public void render() {
        // Rendering is handled by JavaFX's scene graph, so no custom rendering needed
    }

    @Override
    public void handleInput(KeyEvent event) {
        switch (event.getCode()) {
            case ENTER:
                controller.startGame(); 
                break;
            case ESCAPE:
                exitGame(); 
                break;
            default:
                break;
        }
    }

    @Override
    public void cleanup() {
        if (controller.getAudioManager() != null) {
            controller.getAudioManager().stopMusic();
        }
    }

    /**
     * Handles the action of exiting the game.
     */
    private void exitGame() {
        primaryStage.close();
    }
}
