package com.example.demo.controller;

import com.example.demo.LevelParent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

/**
 * Controller class manages the game scene and its associated media.
 */
public class Controller implements Observer{
    private Stage primaryStage;
    private MediaPlayer gameMediaPlayer;

    // Media files for the game
    private static final String AUDIO_LOCATION = "/com/example/demo/audios/";
    private Media bgmEasy, bgmMedium, bgmHard;

    // Level class name
    private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.LevelOne";

    /**
     * Constructor initializes the Controller with the primary stage.
     *
     * @param stage The primary stage of the application.
     */
    public Controller(Stage stage) {
        this.primaryStage = stage;
        initializeMedia();
    }
	    /**
     * Initializes game-specific media.
     */
    private void initializeMedia() {
        bgmEasy = createMedia("bgm_easy.wav");
        bgmMedium = createMedia("bgm_medium.mp3");
        bgmHard = createMedia("bgm_hard.mp3");
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
     * Creates the game scene by loading LevelOne.
     */
    @Override
	public void update(Observable arg0, Object arg1) {
        try {
			goToLevel((String) arg1);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText(e.getClass().toString());
			alert.show();
		}
    }
	

    /**
     * Launches the game by setting the game scene and starting background music.
     */
    public void launchGame() {
        primaryStage.show();
        // Background music is handled by the LevelParent
    }

    /**
     * Stops and disposes of the game media player.
     */
    public void stopGameMusic() {
        if (gameMediaPlayer != null) {
            gameMediaPlayer.stop();
            gameMediaPlayer.dispose();
        }
    }

    /**
     * Loads and transitions to the specified level.
     *
     * @param className The fully qualified class name of the level to load.
     */
	private void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			Class<?> myClass = Class.forName(className);
			Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
			LevelParent myLevel = (LevelParent) constructor.newInstance(primaryStage.getHeight(), primaryStage.getWidth());
			myLevel.addObserver(this);
			Scene scene = myLevel.initializeScene();
			primaryStage.setScene(scene);
			myLevel.startGame();

	}

    /**
     * Initializes and plays the game background music.
     *
     * @param media The Media object representing the background music.
     */
    private void playGameMusic(Media media) {
        if (media == null) {
            System.err.println("Game background music not initialized.");
            return;
        }
        try {
            gameMediaPlayer = new MediaPlayer(media);
            gameMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop indefinitely
            gameMediaPlayer.setVolume(0.5); // Set volume to 50%
            gameMediaPlayer.play();
        } catch (Exception e) {
            System.err.println("Error initializing MediaPlayer for game music.");
            e.printStackTrace();
        }
    }
}
