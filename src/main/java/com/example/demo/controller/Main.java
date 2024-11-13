package com.example.demo.controller;

import com.example.demo.HomeMenu;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class serves as the entry point for the JavaFX application.
 * It initializes and displays the HomeMenu.
 */
public class Main extends Application {

    private static final int SCREEN_WIDTH = 1300;
    private static final int SCREEN_HEIGHT = 750;
    private static final String TITLE = "Sky Battle";
    private Controller myController;

    @Override
    public void start(Stage stage) throws Exception {
        // Set stage properties
        stage.setTitle(TITLE);
        stage.setResizable(false);
        stage.setWidth(SCREEN_WIDTH);
        stage.setHeight(SCREEN_HEIGHT);
        myController = new Controller(stage);
		myController.launchGame();
    }

    @Override
    public void stop() {
        // Perform any necessary cleanup here
    }

    public static void main(String[] args) {
        launch();
    }
}
