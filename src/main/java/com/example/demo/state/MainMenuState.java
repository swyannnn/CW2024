package com.example.demo.state;

import com.example.demo.controller.Controller;
import com.example.demo.manager.GameStateManager;
import com.example.demo.ui.MainMenu;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * MainMenuState class manages the main menu state of the game.
 */
public class MainMenuState implements GameState {
    private final Stage stage;
    private final Controller controller;
    private MainMenu mainMenu;
    GameStateManager gameStateManager;
    private Scene scene;

    /**
     * Constructor for MainMenuState.
     *
     * @param primaryStage The primary stage of the application.
     * @param controller   The game controller.
     */
    public MainMenuState(Stage stage, Controller controller) {
        this.stage = stage;
        this.controller = controller;
        this.gameStateManager = GameStateManager.getInstance(stage, controller);
    }

    @Override
    public void initialize() {
        // Initialize the HomeMenu and set the scene
        System.out.println("Initializing Main Menu State");
        mainMenu = new MainMenu(stage, controller);
        this.scene = mainMenu.getHomeMenuScene();
        stage.setScene(this.scene);
        stage.show();
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
                System.out.println("pressed enter");
                gameStateManager.startGame(); 
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
        if (gameStateManager.getAudioManager() != null) {
            gameStateManager.getAudioManager().stopMusic();
        }
    }

    /**
     * Handles the action of exiting the game.
     */
    private void exitGame() {
        stage.close();
    }
}
