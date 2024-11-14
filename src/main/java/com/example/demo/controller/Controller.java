package com.example.demo.controller;

import com.example.demo.GameControl;
import com.example.demo.GameOverImage;
import com.example.demo.HomeMenu;
import com.example.demo.WinImage;
import com.example.demo.level.LevelParent;
import com.example.demo.level.LevelView;
import com.example.demo.manager.AudioManager;
import com.example.demo.manager.GameStateManager;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.stage.Stage;

public class Controller implements GameControl {
    private final Stage stage;
    private final AudioManager audioManager;
    private final GameStateManager gameStateManager;
    private HomeMenu homeMenu;
    private AnimationTimer gameLoop;
    private LevelParent currentLevel;
    private GameOverImage gameOverImage;
    private WinImage winImage;

    public Controller(Stage stage) {
        this.stage = stage;
        this.audioManager = new AudioManager();
        this.gameStateManager = new GameStateManager(stage);
        initialize();
    }

    private void initialize() {
        // Set up property change listeners for level changes
        gameStateManager.addPropertyChangeListener(event -> {
            if ("level".equals(event.getPropertyName())) {
                int nextLevelNumber = (int) event.getNewValue();
                gameStateManager.loadLevel(nextLevelNumber, this);
                currentLevel = gameStateManager.getCurrentLevel(); // Set currentLevel
                startGameLoop();
            }
        });

        // Launch the home menu at the beginning
        launchHomeMenu();
    }

    /**
     * Launches the home menu scene.
     */
    public void launchHomeMenu() {
        stage.show();
        homeMenu = new HomeMenu(stage, this);
        stage.setScene(homeMenu.getHomeMenuScene());
    }

    /**
     * Handles the transition from the home menu to the first level.
     * Called when the player clicks "Start Game" in the main menu.
     */
    public void startGame() {
        audioManager.stopMusic();
        gameStateManager.loadLevel(1, this); // Start at level 1
        currentLevel = gameStateManager.getCurrentLevel(); // Set currentLevel
        startGameLoop();
    }

    /**
     * Starts or resets the game loop.
     */
    private void startGameLoop() {
        if (gameLoop != null) {
            gameLoop.stop();
        }

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (currentLevel != null) {
                    currentLevel.update(); // Update game logic
                    currentLevel.render(); // Render game elements
                }
            }
        };
        gameLoop.start();
    }

    // Method to stop the game loop and call loseGame
    public void stopGameLoopAndLose() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
        if (currentLevel != null) {
            LevelView levelView = currentLevel.getLevelView();
            if (levelView != null) {
                levelView.showGameOverImage();
            } else {
                System.err.println("Error: LevelView is null in stopGameLoopAndLose.");
            }
        } else {
            System.err.println("Error: currentLevel is null in stopGameLoopAndLose.");
        }
    }

    // Method to stop the game loop and call winGame
    public void stopGameLoopAndWin() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
        if (currentLevel != null) {
            currentLevel.winGame();
            LevelView levelView = currentLevel.getLevelView();
            if (levelView != null) {
                levelView.showWinImage();
            } else {
                System.err.println("Error: LevelView is null in stopGameLoopAndWin.");
            }
        } else {
            System.err.println("Error: currentLevel is null in stopGameLoopAndWin.");
        }
    }

    public AudioManager getAudioManager() {
        return audioManager;
    }
    // /**
    //  * Creates a fade-out transition and performs the specified action afterwards.
    //  * @param onFinish The action to perform after the transition.
    //  */
    // private void performFadeTransition(Runnable onFinish) {
    //     FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), stage.getScene().getRoot());
    //     fadeTransition.setFromValue(1.0);
    //     fadeTransition.setToValue(0.0);
    //     fadeTransition.setOnFinished(event -> onFinish.run());
    //     fadeTransition.play();
    // }
}
