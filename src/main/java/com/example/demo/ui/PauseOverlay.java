package com.example.demo.ui;

import com.example.demo.listeners.StateTransitioner;
import com.example.demo.manager.ButtonManager;
import com.example.demo.manager.GameLoopManager;
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
    private final GameLoopManager gameLoopManager;
    private final StateTransitioner stateTransitioner;

    /**
     * Constructor for PauseOverlay.
     *
     * @param gameStateManager The GameStateManager to handle game state transitions.
     */
    public PauseOverlay(GameLoopManager gameLoopManager, StateTransitioner stateTransitioner) {
        this.overlay = createPauseOverlay();
        this.gameLoopManager = gameLoopManager;
        this.stateTransitioner = stateTransitioner;
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
        Button resumeButton = ButtonManager.createButton("Resume", 200, 50, 20);
        resumeButton.setOnAction(e -> gameLoopManager.resumeGame());

        // Exit to Main Menu Button
        Button exitButton =  ButtonManager.createButton("Exit to Main Menu", 280, 50, 20);
        exitButton.setOnAction(e -> stateTransitioner.goToMainMenu());

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
