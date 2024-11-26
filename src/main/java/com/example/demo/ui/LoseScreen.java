package com.example.demo.ui;

import com.example.demo.manager.ButtonManager;
import com.example.demo.manager.GameStateManager;
import com.example.demo.util.GameConstant;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * LoseScreen class represents the lose screen UI of the game.
 */
public class LoseScreen {
    private final Stage primaryStage;
    private final GameStateManager gameStateManager;

    /**
     * Constructor initializes the LoseScreen with the primary stage and game state manager.
     *
     * @param stage The primary stage of the application.
     * @param gameStateManager The GameStateManager instance.
     */
    public LoseScreen(Stage stage, GameStateManager gameStateManager) {
        this.primaryStage = stage;
        this.gameStateManager = gameStateManager;

        if (gameStateManager.getAudioManager() != null) {
            gameStateManager.getAudioManager().playMusic("losebgm.mp3");
        }
    }

    /**
     * Constructs and returns the lose screen scene.
     *
     * @return The lose screen scene.
     */
    public Scene getLoseScreenScene() {
        // Layout for the lose screen
        VBox loseLayout = new VBox(30); // Spacing of 30 pixels between elements
        loseLayout.setAlignment(Pos.CENTER);
        loseLayout.setStyle("-fx-background-color: #8B0000;"); // Background color for the lose screen

        // Lose Text
        Text loseText = createTitle("You Lose!");

        // Restart Button
        Button restartButton = ButtonManager.createButton("Restart Game", 200, 50, 20);
        restartButton.setOnAction(e -> gameStateManager.goToLevel(1));

        // Exit Button
        Button exitButton = ButtonManager.createButton("Exit", 200, 50, 20);
        exitButton.setOnAction(e -> exitGame());

        // Add elements to the layout
        loseLayout.getChildren().addAll(loseText, restartButton, exitButton);

        // Return the constructed scene
        return new Scene(loseLayout, GameConstant.GameSettings.SCREEN_WIDTH, GameConstant.GameSettings.SCREEN_HEIGHT);
    }

    /**
     * Creates and returns the title text for the lose screen.
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
        if (gameStateManager.getAudioManager() != null) {
            gameStateManager.getAudioManager().stopMusic();
        }
        primaryStage.close(); // Close the application
    }
}
