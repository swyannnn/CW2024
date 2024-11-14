package com.example.demo.controller;

import javafx.application.Application;
import com.example.demo.util.ScreenConstants;
import javafx.stage.Stage;

/**
 * Main class serves as the entry point for the JavaFX application.
 * It initializes and displays the HomeMenu.
 */
public class Main extends Application {
    private static final String TITLE = "Sky Battle";
    private Controller myController;

    @Override
    public void start(Stage stage) {
        // Set stage properties
        stage.setTitle(TITLE);
        stage.setResizable(false);
        stage.setWidth(ScreenConstants.SCREEN_WIDTH);
        stage.setHeight(ScreenConstants.SCREEN_HEIGHT);

        // Initialize and launch the controller to show the HomeMenu
        myController = new Controller(stage);
    }

    @Override
    public void stop() {
        // Perform any necessary cleanup here
    }

    public static void main(String[] args) {
        launch();
    }
}
