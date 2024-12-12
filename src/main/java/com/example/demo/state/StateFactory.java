package com.example.demo.state;

import java.util.Optional;

import com.example.demo.level.LevelConfig;
import com.example.demo.level.LevelFactory;
import com.example.demo.level.LevelParent;
import com.example.demo.manager.ActorManager;
import com.example.demo.manager.CollisionManager;
import com.example.demo.manager.GameLoopManager;
import com.example.demo.manager.AudioManager;
import javafx.stage.Stage;

/**
 * GameStateFactory is responsible for creating instances of game states.
 * 
 * @see <a href="https://github.com/swyannnn/CW2024/blob/master/src/main/java/com/example/demo/state/StateFactory.java">Github Source Code</a>
 */
public class StateFactory {
    private final Stage stage;
    private final ActorManager actorManager;
    private final CollisionManager collisionManager;
    private final GameLoopManager gameLoopManager;
    private final StateTransitioner stateTransitioner;
    private final AudioManager audioManager;

    /**
     * Constructs a new StateFactory with the specified managers and stage.
     *
     * @param stage the stage where the game is rendered
     * @param actorManager the manager responsible for handling game actors
     * @param collisionManager the manager responsible for handling collisions
     * @param gameLoopManager the manager responsible for handling the game loop
     * @param stateTransitioner the manager responsible for handling state transitions
     * @param audioManager the manager responsible for handling audio
     */
    public StateFactory(
        Stage stage,
        ActorManager actorManager,
        CollisionManager collisionManager,
        GameLoopManager gameLoopManager,
        StateTransitioner stateTransitioner,
        AudioManager audioManager
    ) {
        this.stage = stage;
        this.actorManager = actorManager;
        this.collisionManager = collisionManager;
        this.gameLoopManager = gameLoopManager;
        this.stateTransitioner = stateTransitioner;
        this.audioManager = audioManager;
    }

    /**
     * Creates an instance of MainMenuState.
     *
     * @return A new MainMenuState.
     */
    public GameState createMainMenuState() {
        gameLoopManager.resumeGame();
        return new MainMenuState(stage, stateTransitioner, audioManager);
    }

    /**
     * Creates an instance of LevelState for the given level number.
     *
     * @param levelNumber The level number.
     * @return A new LevelState, WinState if the level does not exist.
     */
    public GameState createLevelState(int levelNumber) {
        LevelConfig config = new LevelConfig(
            stateTransitioner.getNumberOfPlayers(),
            actorManager,
            audioManager,
            gameLoopManager
        );
        Optional<LevelParent> optionalLevel = LevelFactory.createLevel(levelNumber, config);
        if (optionalLevel.isPresent()) {
            LevelParent level = optionalLevel.get();
            return new LevelState(
                    stage,
                    level,
                    actorManager,
                    collisionManager,
                    gameLoopManager,
                    stateTransitioner
            );
        } else {
            return createWinState();
        }
    }

    /**
     * Creates an instance of WinState.
     *
     * @return A new WinState.
     */
    public GameState createWinState() {
        return new WinState(stage, stateTransitioner);
    }

    /**
     * Creates an instance of LoseState.
     *
     * @return A new LoseState.
     */
    public GameState createLoseState() {
        return new LoseState(stage, stateTransitioner);
    }
}
