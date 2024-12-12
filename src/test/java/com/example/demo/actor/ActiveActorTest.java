package com.example.demo.actor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.demo.manager.ImageManager;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

/**
 * JUnit tests for the ActiveActor class focusing on movement methods.
 */
public class ActiveActorTest {

    private TestActor actor;
    private MockedStatic<ImageManager> mockedImageManager;

    /**
     * A simple concrete subclass of ActiveActor for testing purposes.
     */
    private static class TestActor extends ActiveActor {

        /**
         * Constructs a TestActor with the specified parameters.
         *
         * @param imageName    the name of the image file to be used for the actor
         * @param imageHeight  the height of the image
         * @param initialXPos  the initial X position of the actor
         * @param initialYPos  the initial Y position of the actor
         */
        public TestActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
            super(imageName, imageHeight, initialXPos, initialYPos);
        }

        @Override
        public void update(long now) {
            // No operation for testing
        }

        @Override
        public boolean takeDamage() {
            // Simple implementation for testing
            return true;
        }
    }

    @BeforeEach
    void setUp() {
        new JFXPanel(); 

        // Mock the static ImageManager.getImage method
        mockedImageManager = mockStatic(ImageManager.class);
        Image dummyImage = new Image("https://via.placeholder.com/150"); // Use a valid placeholder image URL
        mockedImageManager.when(() -> ImageManager.getImage(anyString())).thenReturn(dummyImage);

        actor = new TestActor("test.png", 150, 100.0, 200.0);
    }

    @AfterEach
    void tearDown() {
        // Close the mocked ImageManager to prevent memory leaks
        mockedImageManager.close();
    }

    @Test
    void testMoveHorizontally_Positive() {
        // Initial translateX should be 0.0
        assertEquals(0.0, actor.getTranslateX(), "Initial TranslateX should be 0.0");

        // Move horizontally by 50.0 units to the right
        double horizontalMove = 50.0;
        actor.moveHorizontally(horizontalMove);

        // Verify that translateX has been updated correctly
        assertEquals(horizontalMove, actor.getTranslateX(),
                "TranslateX should be " + horizontalMove + " after moving horizontally.");
    }

    @Test
    void testMoveHorizontally_Negative() {
        // Move horizontally by -30.0 units to the left
        double horizontalMove = -30.0;
        actor.moveHorizontally(horizontalMove);

        // Verify that translateX has been updated correctly
        assertEquals(horizontalMove, actor.getTranslateX(),
                "TranslateX should be " + horizontalMove + " after moving horizontally.");
    }

    @Test
    void testMoveVertically_Positive() {
        // Initial translateY should be 0.0
        assertEquals(0.0, actor.getTranslateY(), "Initial TranslateY should be 0.0");

        // Move vertically by 40.0 units downwards
        double verticalMove = 40.0;
        actor.moveVertically(verticalMove);

        // Verify that translateY has been updated correctly
        assertEquals(verticalMove, actor.getTranslateY(),
                "TranslateY should be " + verticalMove + " after moving vertically.");
    }

    @Test
    void testMoveVertically_Negative() {
        // Move vertically by -20.0 units upwards
        double verticalMove = -20.0;
        actor.moveVertically(verticalMove);

        // Verify that translateY has been updated correctly
        assertEquals(verticalMove, actor.getTranslateY(),
                "TranslateY should be " + verticalMove + " after moving vertically.");
    }

    @Test
    void testMultipleMovements() {
        // Move horizontally by 25.0 units
        actor.moveHorizontally(25.0);
        assertEquals(25.0, actor.getTranslateX(), "TranslateX should be 25.0 after first horizontal move.");

        // Move vertically by -15.0 units
        actor.moveVertically(-15.0);
        assertEquals(-15.0, actor.getTranslateY(), "TranslateY should be -15.0 after first vertical move.");

        // Move horizontally by -10.0 units
        actor.moveHorizontally(-10.0);
        assertEquals(15.0, actor.getTranslateX(), "TranslateX should be 15.0 after second horizontal move.");

        // Move vertically by 30.0 units
        actor.moveVertically(30.0);
        assertEquals(15.0, actor.getTranslateX(), "TranslateX should remain 15.0 after vertical move.");
        assertEquals(15.0, actor.getTranslateY(), "TranslateY should be 15.0 after second vertical move.");
    }
}
