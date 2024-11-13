package com.example.demo.controller;

import com.example.demo.LevelParent;
import com.example.demo.AudioManager;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import com.example.demo.AudioManager;
import com.example.demo.HomeMenu;

/**
 * Controller class manages the game scene and its associated media.
 */
public class Controller implements Observer{
    private Stage primaryStage;
    private AudioManager audioManager;

    /**
     * Constructor initializes the Controller with the primary stage.
     *
     * @param stage The primary stage of the application.
     */
    public Controller(Stage stage) {
        this.primaryStage = stage;
        this.audioManager = new AudioManager(); // Initialize MusicManager
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
        audioManager.playBackgroundMusic("titlebackground.mp3"); // Play background music
        HomeMenu homeMenu = new HomeMenu(primaryStage);
        primaryStage.setScene(homeMenu.getHomeMenuScene());
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
}
