package com.example.demo.state;

import com.example.demo.controller.Controller;
import com.example.demo.level.LevelFactory;
import com.example.demo.level.LevelParent;
import com.example.demo.manager.ActorManager;
import com.example.demo.manager.GameStateManager;

import javafx.stage.Stage;

/**
 * GameStateFactory is responsible for creating instances of game states.
 */
public class GameStateFactory {
    private final Stage stage;
    private Controller controller;
    private final GameStateManager gameStateManager;

    public GameStateFactory(Stage stage, Controller controller, GameStateManager gameStateManager) {
        this.stage = stage;
        this.controller = controller;
        this.gameStateManager = gameStateManager;
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
     * Creates an instance of MainMenuState.
     *
     * @return A new MainMenuState.
     */
    public GameState createMainMenuState() {
        return new MainMenuState(stage, controller); // Uses the updated Controller
    }

    /**
     * Creates an instance of LevelState for the given level number.
     *
     * @param levelNumber The level number.
     * @return A new LevelState.
     */
    public GameState createLevelState(int levelNumber) {
        // LevelFactory creates a LevelParent object for the specified level
        LevelParent level = LevelFactory.createLevel(levelNumber, controller);
        if (level != null) {
            ActorManager actorManager = level.getActorManager();
            return new LevelState(level, actorManager, stage, gameStateManager); 
        }
        return null;
    }
}
