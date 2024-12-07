package com.example.demo.ui;

import com.example.demo.manager.ButtonManager;
import com.example.demo.manager.GameLoopManager;
import com.example.demo.state.StateTransitioner;
import com.example.demo.util.GameConstant;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * The PauseOverlay class represents a UI overlay that appears when the game is paused.
 * It provides options to resume the game or exit to the main menu.
 */
public class PauseOverlay {
    private final VBox overlay;
    private final GameLoopManager gameLoopManager;
    private final StateTransitioner stateTransitioner;

    /**
     * Constructs a PauseOverlay object.
     *
     * @param gameLoopManager the manager responsible for handling the game loop
     * @param stateTransitioner the object responsible for handling state transitions
     */
    public PauseOverlay(GameLoopManager gameLoopManager, StateTransitioner stateTransitioner) {
        this.overlay = createPauseOverlay();
        this.gameLoopManager = gameLoopManager;
        this.stateTransitioner = stateTransitioner;
    }

    /**
     * Creates a VBox containing the pause overlay UI components.
     * The overlay includes a semi-transparent background, a "Game Paused" label,
     * a "Resume" button to resume the game, and an "Exit to Main Menu" button to exit to the main menu.
     *
     * @return VBox containing the pause overlay UI components.
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
     * Returns the VBox overlay.
     *
     * @return the VBox overlay
     */
    public VBox getOverlay() {
        return overlay;
    }
}
