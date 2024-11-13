package com.example.demo;

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
    private MediaPlayer menuMediaPlayer;
    private Stage primaryStage;
    private Controller gameController;
    
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
        initializeMedia();
    }

    /**
     * Initializes audio clips and background music.
     */
    private void initializeMedia() {
        // Initialize AudioClips for sound effects
        sfx = new AudioClip[]{
            createAudioClip("player_death.wav"),
            createAudioClip("enemy_destroy.wav"),
            createAudioClip("titlescreen_transition.wav"),
            createAudioClip("player_shoot.mp3")
        };
        if (sfx[3] != null) {
            sfx[3].setVolume(0.20);
        }

        // Initialize Media for background music
        titleBackgroundMusic = createMedia("titlebackground.mp3");
    }

    /**
     * Creates an AudioClip from a resource file.
     *
     * @param filename The name of the audio file.
     * @return An instance of AudioClip or null if not found.
     */
    private AudioClip createAudioClip(String filename) {
        String resourcePath = AUDIO_LOCATION + filename;
        try {
            String uri = getClass().getResource(resourcePath).toExternalForm();
            return new AudioClip(uri);
        } catch (NullPointerException e) {
            System.err.println("Audio file not found: " + resourcePath);
            e.printStackTrace();
            return null;
        }
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
        startButton.setOnAction(e -> startGame());

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
    private void startGame() {
        // Stop menu music
        stopMenuMusic();

        // Initialize and launch the game controller
        gameController = new Controller(primaryStage);
        try {
            gameController.launchGame();
        } catch (Exception ex) {
            System.err.println("Error launching game.");
            ex.printStackTrace();
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
