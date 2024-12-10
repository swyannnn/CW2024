package com.example.demo.manager;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.state.*;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.lang.reflect.Field;

public class StateManagerTest {

    @Mock
    private Stage mockStage;

    @Mock
    private ActorManager mockActorManager;

    @Mock
    private CollisionManager mockCollisionManager;

    @Mock
    private GameLoopManager mockGameLoopManager;

    @Mock
    private AudioManager mockAudioManager;

    @Mock
    private StateFactory mockStateFactory;

    @Mock
    private GameState mockMainMenuState;

    @Mock
    private GameState mockLevelState;

    @Mock
    private GameState mockWinState;

    @Mock
    private GameState mockLoseState;

    private StateManager stateManager;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        stateManager = new StateManager(mockStage, mockActorManager, mockCollisionManager,
                                       mockGameLoopManager, mockAudioManager, 1);

        // Inject mockStateFactory using reflection
        Field stateFactoryField = StateManager.class.getDeclaredField("stateFactory");
        stateFactoryField.setAccessible(true);
        stateFactoryField.set(stateManager, mockStateFactory);
    }

    @Test
    public void testGoToMainMenu() {
        // Arrange
        when(mockStateFactory.createMainMenuState()).thenReturn(mockMainMenuState);

        // Act
        stateManager.goToMainMenu();

        // Assert
        verify(mockStateFactory).createMainMenuState();
        verify(mockMainMenuState).initialize();
        // Optionally, verify currentState if accessible
    }

    @Test
    public void testGoToLevel() {
        // Arrange
        int levelNumber = 2;
        when(mockStateFactory.createLevelState(levelNumber)).thenReturn(mockLevelState);

        // Act
        stateManager.goToLevel(levelNumber);

        // Assert
        verify(mockStateFactory).createLevelState(levelNumber);
        verify(mockLevelState).initialize();
        // Optionally, verify currentState if accessible
    }

    @Test
    public void testGoToWinState() {
        // Arrange
        when(mockStateFactory.createWinState()).thenReturn(mockWinState);

        // Act
        stateManager.goToWinState();

        // Assert
        verify(mockStateFactory).createWinState();
        verify(mockWinState).initialize();
        // Optionally, verify currentState if accessible
    }

    @Test
    public void testGoToLoseState() {
        // Arrange
        when(mockStateFactory.createLoseState()).thenReturn(mockLoseState);

        // Act
        stateManager.goToLoseState();

        // Assert
        verify(mockStateFactory).createLoseState();
        verify(mockLoseState).initialize();
        // Optionally, verify currentState if accessible
    }

    // Additional tests for other functionalities...
}
