package com.example.demo.ui;

import com.example.demo.WinImage;
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
 * WinScreen class represents the win screen UI of the game.
 */
public class WinScreen {
    private final Stage primaryStage;
    private final GameStateManager gameStateManager;
    // private static final int WIN_IMAGE_X_POSITION = 355;
	// private static final int WIN_IMAGE_Y_POSITION = 175;
    private static final int WIN_IMAGE_X_POSITION = 0;
	private static final int WIN_IMAGE_Y_POSITION = 0;

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
        WinImage winImage = new WinImage(WIN_IMAGE_X_POSITION, WIN_IMAGE_Y_POSITION);
        // Layout for the win screen
        VBox winLayout = new VBox(30); // Spacing of 30 pixels between elements
        winLayout.setAlignment(Pos.CENTER);
        winLayout.setStyle("-fx-background-color: #2E8B57;"); // Background color for the win screen

        // Win Text
        Text winText = createTitle("You Win!");

        // Restart Button
        Button restartButton = ButtonManager.createButton("Restart Game", 200, 50, 20);
        restartButton.setOnAction(e -> gameStateManager.goToLevel(1));

        // Exit Button
        Button exitButton = ButtonManager.createButton("Exit", 200, 50, 20);
        exitButton.setOnAction(e -> exitGame());

        // Add elements to the layout
        winLayout.getChildren().addAll(winImage, winText, restartButton, exitButton);

        // Return the constructed scene
        return new Scene(winLayout, GameConstant.GameSettings.SCREEN_WIDTH, GameConstant.GameSettings.SCREEN_HEIGHT);
    }

    /**
     * Creates and returns the title text for the win screen.
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
