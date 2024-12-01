package com.example.demo.ui;

import com.example.demo.manager.GameStateManager;
import com.example.demo.util.GameConstant;
import com.example.demo.util.GameConstant.GameSettings;

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
    private int currentLevel;

    /**
     * Constructor for PauseOverlay.
     *
     * @param gameStateManager The GameStateManager to handle game state transitions.
     */
    public PauseOverlay(GameStateManager gameStateManager, int currentLevel) {
        this.gameStateManager = gameStateManager;
        this.currentLevel = currentLevel;
        this.overlay = createPauseOverlay();
        System.out.println("Current Level in pauseoverlay constructor: " + currentLevel);
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

        // Instruction Text
        System.out.println("Current Level in pauseoverlay: " + currentLevel);
        Text instructionText = new Text(getInstructionsForLevel(currentLevel));
        instructionText.setFont(Font.font("Arial", 16));
        instructionText.setFill(Color.LIGHTGRAY);
        instructionText.setWrappingWidth(GameConstant.GameSettings.SCREEN_WIDTH * 0.8); // Adjust width as needed
        
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
        pauseBox.getChildren().addAll(instructionText, resumeButton, exitButton);

        return pauseBox;
    }

        /**
     * Retrieves instructions based on the current level number.
     *
     * @param level The current level number.
     * @return A string containing instructions for the level.
     */
    private String getInstructionsForLevel(int level) {
        switch (level) {
            case 1:
                return String.format("Level %d:\n-Kill %d enemies!.", level, GameConstant.Level001.KILLS_TO_ADVANCE);
            case 2:
                return String.format("Level %d:\n-Kill the boss!", level, GameConstant.Level001.KILLS_TO_ADVANCE);
            case 3:
                return String.format("Level %d:\n- Survive %d seconds!", level, GameConstant.Level003.SURVIVAL_TIME);
            case 4:
                return String.format("Level %d:\n- Kill the ULTIMATE BOSS!", level);
            default:
                return "Good luck on your adventure!";
        }
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
