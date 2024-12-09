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
 * The WinScreen class represents the screen displayed when the player wins the game.
 * It provides methods to construct and display the win screen scene, including buttons
 * for restarting the game, returning to the main menu, and exiting the game.
 * 
 * @see <a href="https://github.com/swyannnn/CW2024/blob/master/src/main/java/com/example/demo/screen/WinScreen.java">Github Source Code</a>
 */
public class WinScreen {
    private final Stage stage;
    private final StateTransitioner stateTransitioner;
    private static final int iconHeight = GameConstant.WinGame.IMAGE_HEIGHT;
	private static final int iconWidth = GameConstant.WinGame.IMAGE_WIDTH;
    private static final String imageName = GameConstant.WinGame.IMAGE_PATH;

    /**
     * Constructs a WinScreen object.
     *
     * @param stage the Stage object representing the window where the win screen will be displayed
     * @param stateTransitioner the StateTransitioner object used to manage state transitions within the application
     */
    public WinScreen(Stage stage, StateTransitioner stateTransitioner) {
        this.stage = stage;
        this.stateTransitioner = stateTransitioner;
    }

    /**
     * Creates and returns the scene for the win screen.
     * The win screen displays an image, a restart button, a back to menu button, and an exit button.
     * The layout is styled with a background color and elements are centered with spacing.
     *
     * @return the constructed Scene object for the win screen.
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
        Button restartButton = ButtonManager.createButton("Restart Game", 250, 50, 20);
        restartButton.setOnAction(e -> stateTransitioner.goToLevel(1));

        Button backToMenuButton = ButtonManager.createButton("Back To Main Menu", 250, 50, 20);
        backToMenuButton.setOnAction(e -> stateTransitioner.goToMainMenu());

        // Exit Button
        Button exitButton = ButtonManager.createButton("Exit", 250, 50, 20);
        exitButton.setOnAction(e -> exitGame());

        // Add elements to the layout
        winLayout.getChildren().addAll(winImage, restartButton, backToMenuButton, exitButton);

        // Return the constructed scene
        return new Scene(winLayout, GameConstant.GameSettings.SCREEN_WIDTH, GameConstant.GameSettings.SCREEN_HEIGHT);
    }

    /**
     * Closes the application by closing the current stage.
     */
    private void exitGame() {
        stage.close(); // Close the application
    }
}
