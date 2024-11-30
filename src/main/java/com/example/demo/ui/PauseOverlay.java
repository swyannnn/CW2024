package com.example.demo.ui;

import com.example.demo.manager.GameStateManager;
import com.example.demo.util.GameConstant;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * PauseOverlay represents the UI overlay displayed when the game is paused.
 */
public class PauseOverlay {
    private final VBox overlay;
    private final GameStateManager gameStateManager;

    /**
     * Constructor for PauseOverlay.
     *
     * @param gameStateManager The GameStateManager to handle game state transitions.
     */
    public PauseOverlay(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        this.overlay = createPauseOverlay();
    }

    /**
     * Creates the pause overlay UI components.
     *
     * @return A VBox containing the pause overlay elements.
     */
    private VBox createPauseOverlay() {
        VBox pauseBox = new VBox(20);
        pauseBox.setAlignment(Pos.CENTER);
        pauseBox.setPrefSize(GameConstant.GameSettings.SCREEN_WIDTH, GameConstant.GameSettings.SCREEN_HEIGHT);
        pauseBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);"); // Semi-transparent background

        // Pause Label
        Text pauseLabel = new Text("Game Paused");
        pauseLabel.setFont(Font.font("Arial", 36));
        pauseLabel.setFill(Color.WHITE);

        // Resume Button
        Button resumeButton = new Button("Resume");
        resumeButton.setPrefSize(200, 50);
        resumeButton.setFont(Font.font("Arial", 18));
        resumeButton.setOnAction(e -> gameStateManager.resumeGame());

        // Exit to Main Menu Button
        Button exitButton = new Button("Exit to Main Menu");
        exitButton.setPrefSize(200, 50);
        exitButton.setFont(Font.font("Arial", 18));
        exitButton.setOnAction(e -> gameStateManager.goToMainMenu());

        // Add all components to the VBox
        pauseBox.getChildren().addAll(pauseLabel, resumeButton, exitButton);

        return pauseBox;
    }

    /**
     * Returns the overlay UI component.
     *
     * @return The VBox representing the pause overlay.
     */
    public VBox getOverlay() {
        return overlay;
    }
}
