package com.example.demo.ui;

import com.example.demo.manager.ButtonManager;
import com.example.demo.manager.GameStateManager;
import com.example.demo.util.GameConstant;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * WinScreen class represents the win screen UI of the game.
 */
public class WinScreen {
    private final Stage primaryStage;
    private final GameStateManager gameStateManager;
    private static final int iconHeight = GameConstant.WinGame.IMAGE_HEIGHT;
	private static final int iconWidth = GameConstant.WinGame.IMAGE_WIDTH;
    private static final String imageName = GameConstant.WinGame.IMAGE_PATH;

    /**
     * Constructor initializes the WinScreen with the primary stage and game state manager.
     *
     * @param stage The primary stage of the application.
     * @param gameStateManager The GameStateManager instance.
     */
    public WinScreen(Stage stage, GameStateManager gameStateManager) {
        this.primaryStage = stage;
        this.gameStateManager = gameStateManager;

        // if (gameStateManager.getAudioManager() != null) {
        //     gameStateManager.getAudioManager().playMusic("winbgm.mp3");
        // }
    }

    /**
     * Constructs and returns the win screen scene.
     *
     * @return The win screen scene.
     */
    public Scene getWinScreenScene() {
        ImageView winImage = new ImageView(new Image(getClass().getResource(imageName).toExternalForm()));
		winImage.setFitHeight(iconHeight);
		winImage.setFitWidth(iconWidth);

        // Layout for the win screen
        VBox winLayout = new VBox(30); // Spacing of 30 pixels between elements
        winLayout.setAlignment(Pos.CENTER);
        winLayout.setStyle("-fx-background-color: #2E8B57;"); // Background color for the win screen

        // Restart Button
        Button restartButton = ButtonManager.createButton("Restart Game", 200, 50, 20);
        restartButton.setOnAction(e -> gameStateManager.goToLevel(1));

        // Exit Button
        Button exitButton = ButtonManager.createButton("Exit", 200, 50, 20);
        exitButton.setOnAction(e -> exitGame());

        // Add elements to the layout
        winLayout.getChildren().addAll(winImage, restartButton, exitButton);

        // Return the constructed scene
        return new Scene(winLayout, GameConstant.GameSettings.SCREEN_WIDTH, GameConstant.GameSettings.SCREEN_HEIGHT);
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
