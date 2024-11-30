package com.example.demo.ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import com.example.demo.controller.Controller;
import com.example.demo.manager.ButtonManager;
import com.example.demo.manager.GameStateManager;
import com.example.demo.manager.AudioManager;
import com.example.demo.util.GameConstant;

/**
 * MainMenu class represents the main menu of the game.
 */
public class MainMenu {
    private final Stage primaryStage;
    private final GameStateManager gameStateManager;
    private final AudioManager audioManager;
    private static final String BACKGROUND_MUSIC_NAME = GameConstant.MainMenu.BACKGROUND_MUSIC;

    /**
     * Constructor initializes the MainMenu with the primary stage and controller.
     *
     * @param stage      The primary stage of the application.
     * @param controller The Controller for managing game actions.
     */
    public MainMenu(Stage stage, Controller controller) {
        this.primaryStage = stage;

        gameStateManager = controller.getGameStateManager();
        audioManager = gameStateManager.getAudioManager();
        System.out.println("audiomanager in main menu:" + audioManager);
        if (gameStateManager.getAudioManager() != null) {
            gameStateManager.getAudioManager().playMusic(BACKGROUND_MUSIC_NAME);
        }
    }

    /**
     * Constructs and returns the main menu scene.
     *
     * @return The main menu scene.
     */
    public Scene getHomeMenuScene() {
        // Layout for the menu
        VBox menuLayout = new VBox(30); // Spacing of 30 pixels between elements
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.setStyle("-fx-background-color: #1E1E1E;"); // Background color

        // Title text
        Text title = createTitle("Sky Battle");

        // Buttons using ButtonManager
        Button startButton = ButtonManager.createButton("Start Game", 200, 50, 20);
        startButton.setOnAction(e -> gameStateManager.startGame());

        Button exitButton = ButtonManager.createButton("Exit", 200, 50, 20);
        exitButton.setOnAction(e -> exitGame());

        // Add elements to the layout
        menuLayout.getChildren().addAll(title, startButton, exitButton);

        // Return the constructed scene
        return new Scene(menuLayout, GameConstant.GameSettings.SCREEN_WIDTH, GameConstant.GameSettings.SCREEN_HEIGHT);
    }

    /**
     * Creates and returns the title text for the main menu.
     *
     * @param titleText The text to display as the title.
     * @return A Text object styled as the title.
     */
    private Text createTitle(String titleText) {
        Text title = new Text(titleText);
        title.setFont(Font.font("Arial", 60)); // Font size 60
        title.setStyle("-fx-fill: white;"); // White text color
        return title;
    }

    /**
     * Handles the action of exiting the game.
     */
    private void exitGame() {
        // gameStateManager.getAudioManager().stopMusic(); 
        primaryStage.close(); // Close the application
    }
}
