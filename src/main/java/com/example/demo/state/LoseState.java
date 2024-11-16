package com.example.demo.state;

import com.example.demo.controller.Controller;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import com.example.demo.manager.ButtonManager;
import com.example.demo.manager.GameStateManager;
import com.example.demo.util.GameConstant;

/**
 * LoseState class manages the lose screen state of the game.
 */
public class LoseState implements GameState {
    private final Stage primaryStage;
    private final GameStateManager gameStateManager;

    /**
     * Constructor for LoseState.
     *
     * @param primaryStage The primary stage of the application.
     * @param controller   The game controller.
     */
    public LoseState(Stage primaryStage, GameStateManager gameStateManager) {
        this.primaryStage = primaryStage;
        this.gameStateManager = gameStateManager;
    }

    @Override
    public void initialize() {
        // Initialize the LoseScreen and set the scene
        VBox loseLayout = new VBox(30);
        loseLayout.setAlignment(Pos.CENTER);
        loseLayout.setStyle("-fx-background-color: #8B0000;"); // Example background color

        // Lose Text
        Text loseText = new Text("You Lose!");
        loseText.setFont(Font.font("Arial", 60));
        loseText.setStyle("-fx-fill: white;");

        // Restart Button
        Button restartButton = ButtonManager.createButton("Restart Game", 200, 50);
        restartButton.setOnAction(e -> gameStateManager.goToMainMenu());

        // Exit Button
        Button exitButton = ButtonManager.createButton("Exit", 200, 50);
        exitButton.setOnAction(e -> primaryStage.close());

        loseLayout.getChildren().addAll(loseText, restartButton, exitButton);
        primaryStage.setScene(new Scene(loseLayout, GameConstant.SCREEN_WIDTH, GameConstant.SCREEN_HEIGHT));
        primaryStage.show();
    }

    @Override
    public void update() {
        // No update logic needed for static lose screen
    }

    @Override
    public void render() {
        // Rendering is handled by JavaFX's scene graph
    }

    @Override
    public void handleInput(KeyEvent event) {
        // Handle input if necessary
    }

    @Override
    public void cleanup() {
        // Cleanup resources if necessary
    }
}
