package com.example.demo.screen;

import com.example.demo.manager.ButtonManager;
import com.example.demo.state.StateTransitioner;
import com.example.demo.util.GameConstant;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The LoseScreen class represents the screen displayed when the player loses the game.
 * It provides options to restart the game, go back to the main menu, or exit the application.
 */
public class LoseScreen {
    private final Stage stage;
    private final StateTransitioner stateTransitioner;
    private static final int iconHeight = GameConstant.GameOver.IMAGE_HEIGHT;
	private static final int iconWidth = GameConstant.GameOver.IMAGE_WIDTH;
    private static final String imageName = GameConstant.GameOver.IMAGE_PATH;

    /**
     * Constructs a new LoseScreen.
     *
     * @param stage the stage to be used for displaying the screen
     * @param stateTransitioner the state transitioner to handle state changes
     */
    public LoseScreen(Stage stage, StateTransitioner stateTransitioner) {
        this.stage = stage;
        this.stateTransitioner = stateTransitioner;
    }

    /**
     * Creates and returns the scene for the lose screen.
     * The lose screen displays a game over image, and provides buttons to restart the game,
     * go back to the main menu, or exit the game.
     *
     * @return the constructed Scene object for the lose screen
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
        Button restartButton = ButtonManager.createButton("Restart Game", 250, 50, 20);
        restartButton.setOnAction(e -> stateTransitioner.goToLevel(1));

        Button backToMenuButton = ButtonManager.createButton("Back To Main Menu", 250, 50, 20);
        backToMenuButton.setOnAction(e -> stateTransitioner.goToMainMenu());

        // Exit Button
        Button exitButton = ButtonManager.createButton("Exit", 250, 50, 20);
        exitButton.setOnAction(e -> exitGame());

        // Add elements to the layout
        loseLayout.getChildren().addAll(gameoverImage, restartButton, backToMenuButton, exitButton);

        // Return the constructed scene
        return new Scene(loseLayout, GameConstant.GameSettings.SCREEN_WIDTH, GameConstant.GameSettings.SCREEN_HEIGHT);
    }

    /**
     * Closes the application by closing the current stage.
     */
    private void exitGame() {
        stage.close(); // Close the application
    }
}
