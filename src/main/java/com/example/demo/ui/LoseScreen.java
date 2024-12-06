package com.example.demo.ui;

import com.example.demo.listeners.StateTransitioner;
import com.example.demo.manager.ButtonManager;
import com.example.demo.util.GameConstant;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * LoseScreen class represents the lose screen UI of the game.
 */
public class LoseScreen {
    private final Stage stage;
    private final StateTransitioner stateTransitioner;
    private static final int iconHeight = GameConstant.GameOver.IMAGE_HEIGHT;
	private static final int iconWidth = GameConstant.GameOver.IMAGE_WIDTH;
    private static final String imageName = GameConstant.GameOver.IMAGE_PATH;

    /**
     * Constructor initializes the LoseScreen with the primary stage and game state manager.
     *
     * @param stage The primary stage of the application.
     * @param gameStateManager The GameStateManager instance.
     */
    public LoseScreen(Stage stage, StateTransitioner stateTransitioner) {
        this.stage = stage;
        this.stateTransitioner = stateTransitioner;
    }

    /**
     * Constructs and returns the lose screen scene.
     *
     * @return The lose screen scene.
     */
    public Scene getLoseScreenScene() {
        ImageView gameoverImage = new ImageView(new Image(getClass().getResource(imageName).toExternalForm()));
		gameoverImage.setFitHeight(iconHeight);
		gameoverImage.setFitWidth(iconWidth);

        // Layout for the lose screen
        VBox loseLayout = new VBox(30); // Spacing of 30 pixels between elements
        loseLayout.setAlignment(Pos.CENTER);
        loseLayout.setStyle("-fx-background-color: #8B0000;"); // Background color for the lose screen

        // Restart Button
        Button restartButton = ButtonManager.createButton("Restart Game", 200, 50, 20);
        restartButton.setOnAction(e -> stateTransitioner.goToLevel(1));

        // Exit Button
        Button exitButton = ButtonManager.createButton("Exit", 200, 50, 20);
        exitButton.setOnAction(e -> exitGame());

        // Add elements to the layout
        loseLayout.getChildren().addAll(gameoverImage, restartButton, exitButton);

        // Return the constructed scene
        return new Scene(loseLayout, GameConstant.GameSettings.SCREEN_WIDTH, GameConstant.GameSettings.SCREEN_HEIGHT);
    }

    /**
     * Handles the action of exiting the game.
     */
    private void exitGame() {
        stage.close(); // Close the application
    }
}
