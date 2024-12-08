package com.example.demo.ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import com.example.demo.manager.ButtonManager;
import com.example.demo.state.StateTransitioner;
import com.example.demo.manager.AudioManager;
import com.example.demo.util.GameConstant;

/**
 * The MainMenu class represents the main menu of the game application.
 * It initializes the main menu with the primary stage and handles the transition
 * to different game states based on user interaction.
 * 
 * The main menu includes options for starting a single-player or two-player game,
 * as well as an option to exit the game. It also plays background music when the
 * menu is displayed.
 * 
 * The class provides methods to construct the main menu scene, create the title text,
 * and handle the exit game action.
 * 
 */
public class MainMenuScreen {
    private final Stage primaryStage;
    private final StateTransitioner stateTransitioner;
    private static final String backgroundMusicName = GameConstant.MainMenu.BACKGROUND_MUSIC;

    /**
     * Constructs a MainMenu instance.
     *
     * @param stage the primary stage of the application
     * @param stateTransitioner the state transitioner to manage state changes
     * @param audioManager the audio manager to handle audio playback
     */
    public MainMenuScreen(Stage stage, StateTransitioner stateTransitioner, AudioManager audioManager) {
        this.primaryStage = stage;
        this.stateTransitioner = stateTransitioner;
        // Play background music
        audioManager.playMusic(backgroundMusicName);
    }

    /**
     * Creates and returns the home menu scene for the game.
     * The home menu includes a title and buttons for selecting the number of players (1 or 2) and exiting the game.
     * 
     * @return the Scene object representing the home menu.
     */
    public Scene getHomeMenuScene() {
        VBox menuLayout = new VBox(30);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.setStyle("-fx-background-color: #1E1E1E;");

        Text title = new Text("Sky Battle");
        title.setFont(Font.font("Arial", 60)); // Font size 60
        title.setStyle("-fx-fill: white;"); // White text color

        Button onePlayerButton = ButtonManager.createButton("1 Player", 200, 50, 20);
        onePlayerButton.setOnAction(e -> {
            stateTransitioner.setNumberOfPlayers(1);
            stateTransitioner.goToLevel(1);
        });

        Button twoPlayerButton = ButtonManager.createButton("2 Players", 200, 50, 20);
        twoPlayerButton.setOnAction(e -> {
            stateTransitioner.setNumberOfPlayers(2);
            stateTransitioner.goToLevel(1);
        });

        Button exitButton = ButtonManager.createButton("Exit", 200, 50, 20);
        exitButton.setOnAction(e -> exitGame());

        menuLayout.getChildren().addAll(title, onePlayerButton, twoPlayerButton, exitButton);

        return new Scene(menuLayout, GameConstant.GameSettings.SCREEN_WIDTH, GameConstant.GameSettings.SCREEN_HEIGHT);
    }

    /**
     * Closes the primary stage and exits the game.
     */
    private void exitGame() {
        primaryStage.close();
    }
}
