package com.example.demo.screen;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
     * The menu includes the game title and buttons for:
     * - Starting a single-player game
     * - Starting a two-player game
     * - Viewing instructions on how to play
     * - Exiting the game
     *
     * @return the home menu scene
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

        // Create the "How to Play" button
        Button howToPlayButton = ButtonManager.createButton("How to Play", 200, 50, 20);
        howToPlayButton.setOnAction(e -> showInstructions());

        Button exitButton = ButtonManager.createButton("Exit", 200, 50, 20);
        exitButton.setOnAction(e -> exitGame());

        menuLayout.getChildren().addAll(title, onePlayerButton, twoPlayerButton, howToPlayButton, exitButton);

        return new Scene(menuLayout, GameConstant.GameSettings.SCREEN_WIDTH, GameConstant.GameSettings.SCREEN_HEIGHT);
    }

    /**
     * Displays the game instructions in a popup dialog.
     */
    private void showInstructions() {
        Alert instructionsAlert = new Alert(AlertType.INFORMATION);
        instructionsAlert.setTitle("How to Play");
        instructionsAlert.setHeaderText("Game Instructions");
        instructionsAlert.setContentText(getInstructionsText());

        instructionsAlert.getButtonTypes().setAll(ButtonType.OK);

        instructionsAlert.showAndWait();
    }

    /**
     * Returns the instructions text to be displayed.
     *
     * @return a String containing the game instructions.
     */
    private String getInstructionsText() {
        return "Welcome to Sky Battle!\n\n" +
               "Objective:\n" +
               "Defeat all enemy planes and protect your base.\n\n" +
               "Controls:\n\n" +
               "PLAYER 1:\n" +
                "- Move Up: Up Arrow Key\n" +
                "- Move Down: Down Arrow Key\n" +
               "- Move Left: Left Arrow Key\n" +
               "- Move Right: Right Arrow Key\n\n" +
                "PLAYER 2:\n" +
                "- Move Up: W Key\n" +
                "- Move Down: S Key\n" +
                "- Move Left: A Key\n" +
                "- Move Right: D Key\n\n" +
                "- Pause Game: Space Bar\n\n" +
               "Tips:\n" +
               "- Don't worry, shooting is automatic!\n" +
               "- Dodge enemy bullets to survive longer.\n" +
               "- Focus on eliminating enemies systematically.\n\n" +
               "Good luck and have fun!";
    }

    /**
     * Closes the primary stage and exits the game.
     */
    private void exitGame() {
        primaryStage.close();
    }
}
