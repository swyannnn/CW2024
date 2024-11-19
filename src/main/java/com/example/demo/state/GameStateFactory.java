package com.example.demo.state;

import com.example.demo.controller.Controller;
import com.example.demo.level.LevelFactory;
import com.example.demo.level.LevelParent;
import com.example.demo.manager.ActorManager;
import com.example.demo.manager.GameStateManager;
import com.example.demo.manager.AudioManager;
import com.example.demo.manager.ImageManager;
import javafx.stage.Stage;

/**
 * GameStateFactory is responsible for creating instances of game states.
 */
public class GameStateFactory {
    private final Stage stage;
    private Controller controller;
    private final GameStateManager gameStateManager;
    private final AudioManager audioManager;
    private final ImageManager imageManager;
    private ActorManager actorManager;

    /**
     * Constructor initializes the GameStateFactory with necessary dependencies.
     *
     * @param stage The main Stage object.
     * @param controller The Controller instance.
     * @param gameStateManager The GameStateManager instance.
     */
    public GameStateFactory(Stage stage, Controller controller, GameStateManager gameStateManager) {
        this.stage = stage;
        this.controller = controller;
        this.gameStateManager = gameStateManager;
        this.audioManager = gameStateManager.getAudioManager();
        this.imageManager = gameStateManager.getImageManager();
    }

    /**
     * Sets the updated Controller.
     *
     * @param controller The new Controller instance.
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Sets the ActorManager.
     *
     * @param actorManager The ActorManager instance.
     */
    public void setActorManager(ActorManager actorManager) {
        this.actorManager = actorManager;
    }

    /**
     * Creates an instance of MainMenuState.
     *
     * @return A new MainMenuState.
     */
    public GameState createMainMenuState() {
        return new MainMenuState(stage, controller);
    }

    /**
     * Creates an instance of LevelState for the given level number.
     *
     * @param levelNumber The level number.
     * @return A new LevelState, or null if the level creation fails.
     */
    public GameState createLevelState(int levelNumber) {
        // Create the level using LevelFactory
        LevelParent level = LevelFactory.createLevel(levelNumber, controller);
        if (level != null) {
            ActorManager actorManager = ActorManager.getInstance(null);
            return new LevelState(stage, controller, level, actorManager, gameStateManager, audioManager, imageManager);
        }
        return null;
    }

        /**
     * Creates an instance of WinState.
     *
     * @return A new WinState.
     */
    public GameState createWinState() {
        return new WinState(stage, gameStateManager);
    }

    /**
     * Creates an instance of LoseState.
     *
     * @return A new LoseState.
     */
    public GameState createLoseState() {
        return new LoseState(stage, gameStateManager);
    }
}