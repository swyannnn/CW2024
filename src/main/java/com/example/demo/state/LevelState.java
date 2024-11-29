package com.example.demo.state;

import java.util.List;

import com.example.demo.actor.plane.UserPlane;
import com.example.demo.controller.Controller;
import com.example.demo.level.LevelParent;
import com.example.demo.manager.ActorManager;
import com.example.demo.manager.ButtonManager;
import com.example.demo.manager.CollisionManager;
import com.example.demo.manager.GameStateManager;
import com.example.demo.util.GameConstant;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


/**
 * LevelState manages the game logic and rendering for a specific level.
 */
public class LevelState implements GameState {
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private final LevelParent level;
    private final Stage stage;
    private final GameStateManager gameStateManager;
    private UserPlane userPlane;
    private final ActorManager actorManager;
    private final CollisionManager collisionManager;
    private boolean levelCompleted;
    private VBox pauseOverlay;
    private Scene scene;

    /**
     * Constructor for LevelState.
     *
     * @param stage The main Stage object used for rendering scenes.
     * @param controller The Controller handling game logic.
     * @param level The LevelParent object representing the game level.
     * @param actorManager The ActorManager handling game actors.
     * @param gameStateManager The GameStateManager handling game state transitions.
     * @param audioManager The AudioManager handling game audio.
     * @param imageManager The ImageManager handling game images.
     */
    public LevelState(Stage stage, Controller controller, LevelParent level) {
        this.level = level;
        this.stage = stage;
        this.gameStateManager = controller.getGameStateManager();
        this.actorManager = gameStateManager.getActorManager();
        this.collisionManager = gameStateManager.getCollisionManager();
        this.levelCompleted = false;

        // Initialize userPlanes (handles multiple players)
        List<UserPlane> players = actorManager.getPlayers();
        if (players.isEmpty()) {
            System.err.println("LevelState: No UserPlane found in ActorManager.");
        } else {
            this.userPlane = players.get(0); // For single-player, just take the first player
            System.out.println("LevelState: UserPlane initialized with " + players.size() + " players.");
        }
    }

    @Override
    public void initialize() {
        this.scene = level.getScene();
        if (this.scene == null) {
            System.err.println("LevelState: Failed to initialize scene for level " + level.getCurrentLevelNumber());
            return;
        }

        setupScene(this.scene);
        stage.show();
        System.out.println("LevelState: Level " + level.getCurrentLevelNumber() + " initialized and displayed.");

        // Play background music for this level
        System.out.println("LevelState:" + gameStateManager);
        gameStateManager.getAudioManager().playMusic("menubgm.mp3");
        createPauseOverlay();
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    public void update(long now) {
        if (!levelCompleted) {
            level.spawnEnemyUnits();
            actorManager.updateAllActors(now);
            collisionManager.handleAllCollisions(actorManager);
            level.updateLevelView();
            checkLevelCompletion();
            actorManager.removeDestroyedActors();
            level.updateBackground();
        }
    }
    

    @Override
    public void render() {
        if (!levelCompleted) {
            level.getLevelView().updateView();
        }
    }

    @Override
    public void handleInput(KeyEvent event) {
        if (gameStateManager.isPaused()) {
            if (event.getEventType() == KeyEvent.KEY_PRESSED && event.getCode() == KeyCode.ESCAPE) {
                gameStateManager.resumeGame();
            }
        } else {
            // Process regular game input
            if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                handleKeyPressed(event);
            } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
                handleKeyReleased(event);
            }
        }
    }
    
    

    @Override
    public void cleanup() {
        if (actorManager != null) {
            actorManager.removeDestroyedActors();
        }
        // audioManager.stopMusic(); // Stop music when level is cleaned up
    }

    /**
     * Sets up the scene and registers input event handlers.
     *
     * @param scene The scene to set on the stage.
     */
    private void setupScene(Scene scene) {
        stage.setScene(scene);
        scene.getRoot().requestFocus();
        addPauseButton(scene);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }
    

    /**
     * Adds a pause button to the scene.
     *
     * @param scene The current scene where the button will be added.
     */
    private void addPauseButton(Scene scene) {
        // Create the pause button with an icon
        Button pauseButton = ButtonManager.createImageButton("setting.png", 35, 35); 

        // Set the action for the pause button
        pauseButton.setOnAction(e -> gameStateManager.pauseGame());
        pauseButton.setLayoutX(1250); // 10 pixels from the left
        pauseButton.setLayoutY(10); // 10 pixels from the top

        // Add the button to the scene
        Platform.runLater(() ->  actorManager.addUIElement(pauseButton));
    }

    /**
     * Creates the pause overlay UI.
     */
    private void createPauseOverlay() {
        pauseOverlay = new VBox(20);
        pauseOverlay.setAlignment(Pos.CENTER);
        pauseOverlay.setPrefSize(GameConstant.GameSettings.SCREEN_WIDTH, GameConstant.GameSettings.SCREEN_HEIGHT);
        pauseOverlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);"); // Semi-transparent background

        // Pause Label
        Text pauseLabel = new Text("Game Paused");
        pauseLabel.setFont(Font.font("Arial", 36));
        pauseLabel.setFill(Color.WHITE);

        // Resume Button
        Button resumeButton = new Button("Resume");
        resumeButton.setPrefSize(200, 50);
        resumeButton.setStyle("-fx-font-size: 18px;");
        resumeButton.setOnAction(e -> gameStateManager.resumeGame());

        // Exit to Main Menu Button
        Button exitButton = new Button("Exit to Main Menu");
        exitButton.setPrefSize(200, 50);
        exitButton.setStyle("-fx-font-size: 18px;");
        exitButton.setOnAction(e -> gameStateManager.goToMainMenu());

        // Add all components to the layout
        pauseOverlay.getChildren().addAll(pauseLabel, resumeButton, exitButton);
        pauseOverlay.setLayoutX(0);
        pauseOverlay.setLayoutY(0);

        pauseOverlay.setFocusTraversable(true);
        pauseOverlay.setOnKeyPressed(this::handleInput);
        pauseOverlay.setOnKeyReleased(this::handleInput);
    }

    /**
     * Displays the pause overlay.
     */
    @Override
    public void handlePause() {
        Platform.runLater(() -> {
            if (!level.getRoot().getChildren().contains(pauseOverlay)) {
                level.getRoot().getChildren().add(pauseOverlay);
                pauseOverlay.requestFocus();
            }
        });
        System.out.println("Pause overlay displayed.");
    }

    /**
     * Hides the pause overlay.
     */
    @Override
    public void handleResume() {
        Platform.runLater(() -> {
            level.getRoot().getChildren().remove(pauseOverlay);
            level.getRoot().requestFocus();
        });
        System.out.println("Pause overlay hidden.");
    }

    private void checkLevelCompletion() {
        if (allUsersAreDestroyed()) {
            actorManager.cleanup();
            pcs.firePropertyChange("lose", false, true);
        } else if (level.userHasReachedKillTarget()) {
            actorManager.cleanup();
            onLevelComplete();
        }
    }
    

    /**
     * Checks if any user's plane has been destroyed.
     *
     * @return true if any user's plane is destroyed; false otherwise.
     */
    public boolean userIsDestroyed() {
        for (UserPlane player : actorManager.getPlayers()) {
            // System.out.println("Checking player health: " + player.getHealth());
            if (player.getHealth() <= 0) {
                // System.out.println("Player destroyed: " + player);
                return true; // At least one player is destroyed
            }
        }
        return false; 
    }

    public boolean allUsersAreDestroyed() {
        return actorManager.getPlayers().stream()
                .allMatch(player -> player.getHealth() <= 0);
    }   

    /**
     * Handles KeyPressed events and delegates actions to the level's user.
     *
     * @param event The KeyEvent to process.
     */
    private void handleKeyPressed(KeyEvent event) {
        if (userPlane == null) return; // Ensure userPlane is not null
        KeyCode keyCode = event.getCode();
        switch (keyCode) {
            case UP -> userPlane.moveUp();
            case DOWN -> userPlane.moveDown();
            case LEFT -> userPlane.moveLeft();
            case RIGHT -> userPlane.moveRight();
            case SPACE -> gameStateManager.pauseGame();
            default -> {}
        }
    }

    /**
     * Handles KeyReleased events and stops the user's movement.
     *
     * @param event The KeyEvent to process.
     */
    private void handleKeyReleased(KeyEvent event) {
        if (userPlane == null) return; // Ensure userPlane is not null
        KeyCode keyCode = event.getCode();
        switch (keyCode) {
            case UP, DOWN -> userPlane.stopVertical();
            case LEFT, RIGHT -> userPlane.stopHorizontal();
            default -> {}
        }
    }

    public void onLevelComplete() {
        if (!levelCompleted) {
            levelCompleted = true;
            int nextLevelNumber = level.getCurrentLevelNumber() + 1;
            System.out.println("LevelState: Transitioning to Level " + nextLevelNumber);
            pcs.firePropertyChange("level", level.getCurrentLevelNumber(), nextLevelNumber);
        }
    }    
}
