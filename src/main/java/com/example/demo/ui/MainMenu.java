package com.example.demo.ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import com.example.demo.listeners.StateTransitioner;
import com.example.demo.manager.ButtonManager;
import com.example.demo.manager.AudioManager;
import com.example.demo.util.GameConstant;

/**
 * MainMenu class represents the main menu of the game.
 */
public class MainMenu {
    private final Stage primaryStage;
    private final StateTransitioner stateTransitioner;
    private static final String BACKGROUND_MUSIC_NAME = GameConstant.MainMenu.BACKGROUND_MUSIC;

    /**
     * Constructor initializes the MainMenu with the primary stage and controller.
     *
     * @param stage      The primary stage of the application.
     * @param controller The Controller for managing game actions.
     */
    public MainMenu(Stage stage, StateTransitioner stateTransitioner, AudioManager audioManager) {
        this.primaryStage = stage;
        this.stateTransitioner = stateTransitioner;
        // Play background music
        audioManager.playMusic(BACKGROUND_MUSIC_NAME);
    }

    /**
     * Constructs and returns the main menu scene.
     *
     * @return The main menu scene.
     */
    public Scene getHomeMenuScene() {
        VBox menuLayout = new VBox(30);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.setStyle("-fx-background-color: #1E1E1E;");

        Text title = createTitle("Sky Battle");

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
     * Creates and returns the title text for the main menu.
     *
     * @param titleText The text to display as the title.
     * @return A Text object styled as the title.
     */
    private Text createTitle(String titleText) {
        Text title = new Text(titleText);
        title.setFont(Font.font("Arial", 60)); // Font size 60
        title.setStyle("-fx-fill: white;"); // White text color
        return title;
    }

    /**
     * Handles the action of exiting the game.
     */
    private void exitGame() {
        // gameStateManager.getAudioManager().stopMusic(); 
        primaryStage.close(); // Close the application
    }
}
