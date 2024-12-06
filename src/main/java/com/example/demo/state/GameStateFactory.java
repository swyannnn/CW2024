package com.example.demo.state;

import java.util.Optional;

import com.example.demo.level.LevelConfig;
import com.example.demo.level.LevelFactory;
import com.example.demo.level.LevelParent;
import com.example.demo.listeners.StateTransitioner;
import com.example.demo.manager.ActorManager;
import com.example.demo.manager.CollisionManager;
import com.example.demo.manager.GameLoopManager;
import com.example.demo.manager.AudioManager;
import javafx.stage.Stage;

/**
 * GameStateFactory is responsible for creating instances of game states.
 */
public class GameStateFactory {
    private final Stage stage;
    private final ActorManager actorManager;
    private final CollisionManager collisionManager;
    private final GameLoopManager gameLoopManager;
    private final StateTransitioner stateTransitioner;
    private final AudioManager audioManager;
    /**
     * Constructor initializes the GameStateFactory with necessary dependencies.
     *
     * @param stage             The main Stage object.
     * @param actorManager      The ActorManager instance.
     * @param collisionManager  The CollisionManager instance.
     * @param pauseManager      The PauseManager instance.
     * @param stateTransitioner The StateTransitioner instance.
     * @param audioManager      The AudioManager instance.
     * @param numberOfPlayers   The number of players.
     */
    public GameStateFactory(
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
    public IGameState createMainMenuState() {
        gameLoopManager.resumeGame();
        return new MainMenuState(stage, stateTransitioner, audioManager);
    }

    /**
     * Creates an instance of LevelState for the given level number.
     *
     * @param levelNumber The level number.
     * @return A new LevelState, WinState if the level does not exist.
     */
    public IGameState createLevelState(int levelNumber) {
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
    public IGameState createWinState() {
        return new WinState(stage, stateTransitioner);
    }

    /**
     * Creates an instance of LoseState.
     *
     * @return A new LoseState.
     */
    public IGameState createLoseState() {
        return new LoseState(stage, stateTransitioner);
    }
}
