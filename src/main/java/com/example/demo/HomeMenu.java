package com.example.demo;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.example.demo.controller.Controller;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.AudioClip;

/**
 * HomeMenu class represents the home menu of the game.
 * It provides options to start the game or exit the application.
 */
public class HomeMenu {
    private static final String AUDIO_LOCATION = "/com/example/demo/audios/";
    private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.LevelOne";
    private MediaPlayer menuMediaPlayer;
    private Stage primaryStage;
    
    // Media files
    private Media titleBackgroundMusic;
    private AudioClip[] sfx;

    /**
     * Constructor initializes the HomeMenu with the primary stage.
     *
     * @param stage The primary stage of the application.
     */
    public HomeMenu(Stage stage) {
        this.primaryStage = stage;
    }

    /**
     * Creates a Media instance from a resource file.
     *
     * @param filename The name of the media file.
     * @return An instance of Media or null if not found.
     */
    private Media createMedia(String filename) {
        String resourcePath = AUDIO_LOCATION + filename;
        try {
            String uri = getClass().getResource(resourcePath).toExternalForm();
            return new Media(uri);
        } catch (NullPointerException e) {
            System.err.println("Media file not found: " + resourcePath);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Starts playing the menu background music.
     */
    private void playMenuMusic() {
        if (titleBackgroundMusic == null) {
            System.err.println("Title background music not initialized.");
            return;
        }
        try {
            menuMediaPlayer = new MediaPlayer(titleBackgroundMusic);
            menuMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop indefinitely
            menuMediaPlayer.setVolume(0.5); // Set volume to 50%
            menuMediaPlayer.play();
        } catch (Exception e) {
            System.err.println("Error initializing MediaPlayer for menu music.");
            e.printStackTrace();
        }
    }

    /**
     * Stops and disposes of the menu background music.
     */
    private void stopMenuMusic() {
        if (menuMediaPlayer != null) {
            menuMediaPlayer.stop();
            menuMediaPlayer.dispose();
        }
    }

    /**
     * Constructs and returns the home menu scene.
     *
     * @return The home menu scene.
     */
    public Scene getHomeMenuScene() {
        // Create layout for Home Menu
        VBox menuLayout = new VBox(30); // 30 pixels of spacing
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.setStyle("-fx-background-color: #1E1E1E;"); // Example background color

        // Title Text
        Text title = new Text("Sky Battle");
        title.setFont(Font.font("Arial", 60));
        title.setStyle("-fx-fill: white;"); // White text color

        // Start Game Button
        Button startButton = new Button("Start Game");
        startButton.setPrefWidth(200);
        startButton.setPrefHeight(50);
        startButton.setFont(Font.font("Arial", 20));
        startButton.setOnAction(e -> {
            try {
                startGame();
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                System.err.println("Error starting game: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        // Exit Button
        Button exitButton = new Button("Exit");
        exitButton.setPrefWidth(200);
        exitButton.setPrefHeight(50);
        exitButton.setFont(Font.font("Arial", 20));
        exitButton.setOnAction(e -> exitGame());

        // Add elements to layout
        menuLayout.getChildren().addAll(title, startButton, exitButton);

        // Optional: Add background image
        // Uncomment and modify the following lines if you add a background image
        /*
        Image backgroundImage = new Image(getClass().getResource("/com/example/demo/images/home_background.jpg").toExternalForm());
        BackgroundImage myBI = new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1300, 750, false, false, false, false));
        menuLayout.setBackground(new Background(myBI));
        */

        // Create and return the scene
        Scene scene = new Scene(menuLayout, 1300, 750);
        playMenuMusic(); // Start playing menu music
        return scene;
    }

    /**
     * Handles the action of starting the game.
     * Stops menu music and launches the game controller.
     */
    private void startGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
    InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException  {
        try{
            // Stop menu music
            stopMenuMusic();
            goToLevel(LEVEL_ONE_CLASS_NAME);
        } catch (Exception e) {
            System.err.println("Error launching game.");
            e.printStackTrace();
        }
    }

    private void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
        InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        
        try {
            Class<?> myClass = Class.forName(className);
            Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
            LevelParent myLevel = (LevelParent) constructor.newInstance(primaryStage.getHeight(), primaryStage.getWidth());
            // myLevel.addObserver(this);
            Scene scene = myLevel.initializeScene();
            primaryStage.setScene(scene);
            myLevel.startGame();
        } catch (Exception e) {
            System.err.println("Error loading level: " + e.getMessage());
            e.printStackTrace();
        }

    }
    /**
     * Handles the action of exiting the game.
     * Stops menu music and closes the application.
     */
    private void exitGame() {
        // Stop menu music
        stopMenuMusic();

        // Exit the application
        primaryStage.close();
    }
}
