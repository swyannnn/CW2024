package com.example.demo;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * HomeMenu class represents the home menu of the game.
 * It provides options to start the game or exit the application.
 */
public class HomeMenu {
    private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.LevelOne";
    private Stage primaryStage;
    private AudioManager audioManager;

    /**
     * Constructor initializes the HomeMenu with the primary stage and audio manager.
     *
     * @param stage The primary stage of the application.
     */
    public HomeMenu(Stage stage) {
        this.primaryStage = stage;
        this.audioManager = new AudioManager(); // Initialize AudioManager
        audioManager.playBackgroundMusic("titlebackground.mp3"); // Play background music
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
        startButton.setOnAction(e -> {
            try {
                startGame();
            } catch (Exception ex) {
                System.err.println("Error starting game: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        Button exitButton = ButtonManager.createButton("Exit", 200, 50);
        exitButton.setOnAction(e -> exitGame());

        menuLayout.getChildren().addAll(title, startButton, exitButton);
        return new Scene(menuLayout, 1300, 750);
    }

    /**
     * Handles the action of starting the game.
     */
    private void startGame() throws Exception {
        audioManager.stopMusic(); // Stop menu music
        goToLevel(LEVEL_ONE_CLASS_NAME);
    }

    private void goToLevel(String className) throws Exception {
        Class<?> myClass = Class.forName(className);
        Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
        LevelParent myLevel = (LevelParent) constructor.newInstance(primaryStage.getHeight(), primaryStage.getWidth());
        Scene scene = myLevel.initializeScene();
        primaryStage.setScene(scene);
        myLevel.startGame();
    }

    /**
     * Handles the action of exiting the game.
     */
    private void exitGame() {
        audioManager.stopMusic(); // Stop menu music
        primaryStage.close();
    }
}
