package com.example.demo.actor.plane.component;

import org.junit.jupiter.api.Test;
import com.example.demo.util.GameConstant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;


public class HeartDisplayTest {

    @BeforeEach
    void setUp() {
        // Initialize JavaFX runtime
        new JFXPanel();
        Platform.runLater(() -> new Application() {
            @Override
            public void start(Stage primaryStage) {
                // No need to show the stage
            }
        });
    }

    @Test
    void testGetContainer() {
        HeartDisplay heartDisplay = new HeartDisplay(0, 100, 100, 5);
        assertNotNull(heartDisplay.getContainer());
        assertEquals(1, heartDisplay.getContainer().getChildren().size());
    }

    @Test
    void testGetImageName() {
        HeartDisplay heartDisplay = new HeartDisplay(0, 100, 100, 3);
        assertEquals(GameConstant.UserPlane.ID1_IMAGE_NAME_DISPLAY, heartDisplay.getImageName(0));
        assertEquals(GameConstant.UserPlane.ID2_IMAGE_NAME_DISPLAY, heartDisplay.getImageName(1));
        assertEquals(null, heartDisplay.getImageName(2));
    }

    @Test
    void testSetHearts() {
        HeartDisplay heartDisplay = new HeartDisplay(0, 100, 100, 3);
        Platform.runLater(() -> {
            heartDisplay.setHearts(2);
            assertEquals(3, heartDisplay.getContainer().getChildren().size()); // 1 plane icon + 2 hearts
        });
    }
}

