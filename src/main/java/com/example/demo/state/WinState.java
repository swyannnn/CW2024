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
 * WinState class manages the win screen state of the game.
 */
public class WinState implements GameState {
    private final Stage primaryStage;
    private final GameStateManager gameStateManager;

    /**
     * Constructor for WinState.
     *
     * @param primaryStage     The primary stage of the application.
     * @param gameStateManager The GameStateManager instance.
     */
    public WinState(Stage primaryStage, GameStateManager gameStateManager) {
        this.primaryStage = primaryStage;
        this.gameStateManager = gameStateManager;
    }

    @Override
    public void initialize() {
        // Initialize the WinScreen and set the scene
        VBox winLayout = new VBox(30);
        winLayout.setAlignment(Pos.CENTER);
        winLayout.setStyle("-fx-background-color: #2E8B57;"); // Example background color

        // Win Text
        Text winText = new Text("You Win!");
        winText.setFont(Font.font("Arial", 60));
        winText.setStyle("-fx-fill: white;");

        // Restart Button
        Button restartButton = ButtonManager.createButton("Restart Game", 200, 50);
        restartButton.setOnAction(e -> gameStateManager.goToMainMenu());


        // Exit Button
        Button exitButton = ButtonManager.createButton("Exit", 200, 50);
        exitButton.setOnAction(e -> primaryStage.close());

        winLayout.getChildren().addAll(winText, restartButton, exitButton);
        primaryStage.setScene(new Scene(winLayout, GameConstant.SCREEN_WIDTH, GameConstant.SCREEN_HEIGHT));
        primaryStage.show();
    }

    @Override
    public void update() {
        // No update logic needed for static win screen
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
