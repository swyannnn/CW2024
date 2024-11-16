package com.example.demo;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import com.example.demo.controller.Controller;
import com.example.demo.manager.ButtonManager;

/**
 * HomeMenu class represents the home menu of the game.
 */
public class MainMenu {
    private final Stage primaryStage;
    private final Controller controller;

    /**
     * Constructor initializes the HomeMenu with the primary stage and controller.
     *
     * @param stage The primary stage of the application.
     * @param controller The Controller for managing game actions.
     */
    public MainMenu(Stage stage, Controller controller) {
        this.primaryStage = stage;
        this.controller = controller;
        controller.getAudioManager().playMusic("titlebackground.mp3");
    }

    /**
     * Constructs and returns the home menu scene.
     *
     * @return The home menu scene.
     */
    public Scene getHomeMenuScene() {
        VBox menuLayout = new VBox(30); // 30 pixels of spacing
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.setStyle("-fx-background-color: #1E1E1E;"); // Example background color

        // Title Text
        Text title = new Text("Sky Battle");
        title.setFont(Font.font("Arial", 60));
        title.setStyle("-fx-fill: white;"); // White text color

        // Use ButtonManager to create buttons
        Button startButton = ButtonManager.createButton("Start Game", 200, 50);
        startButton.setOnAction(e -> controller.startGame());

        Button exitButton = ButtonManager.createButton("Exit", 200, 50);
        exitButton.setOnAction(e -> exitGame());

        menuLayout.getChildren().addAll(title, startButton, exitButton);
        return new Scene(menuLayout, 1300, 750);
    }

    /**
     * Handles the action of exiting the game.
     */
    private void exitGame() {
        controller.getAudioManager().stopMusic(); // Use Controller to stop music
        primaryStage.close();
    }
}
