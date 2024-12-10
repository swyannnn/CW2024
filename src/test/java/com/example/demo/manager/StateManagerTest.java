package com.example.demo.manager;

import static org.mockito.Mockito.*;
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
        Field stateFactoryField = StateManager.class.getDeclaredField("stateFactory");
        stateFactoryField.setAccessible(true);
        stateFactoryField.set(stateManager, mockStateFactory);
    }

    @Test
    public void testGoToMainMenu() {
        when(mockStateFactory.createMainMenuState()).thenReturn(mockMainMenuState);
        stateManager.goToMainMenu();
        verify(mockStateFactory).createMainMenuState();
        verify(mockMainMenuState).initialize();
    }

    @Test
    public void testGoToLevel() {
        int levelNumber = 2;
        when(mockStateFactory.createLevelState(levelNumber)).thenReturn(mockLevelState);
        stateManager.goToLevel(levelNumber);
        verify(mockStateFactory).createLevelState(levelNumber);
        verify(mockLevelState).initialize();
    }

    @Test
    public void testGoToWinState() {
        when(mockStateFactory.createWinState()).thenReturn(mockWinState);
        stateManager.goToWinState();
        verify(mockStateFactory).createWinState();
        verify(mockWinState).initialize();
    }

    @Test
    public void testGoToLoseState() {
        when(mockStateFactory.createLoseState()).thenReturn(mockLoseState);
        stateManager.goToLoseState();
        verify(mockStateFactory).createLoseState();
        verify(mockLoseState).initialize();
    }
}
