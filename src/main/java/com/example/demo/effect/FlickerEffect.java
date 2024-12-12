package com.example.demo.effect;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * The FlickerEffect class provides a reusable flicker animation for JavaFX Nodes.
 * It handles the visual flickering by alternating the opacity of the target node.
 * 
 * @see <a href="https://github.com/swyannnn/CW2024/blob/master/src/main/java/com/example/demo/effect/FlickerEffect.java">Github Source Code</a>
 */
public class FlickerEffect {
    private final Node target;
    private final int flickerCount;
    private final double fadeDurationMillis;
    private boolean isFlickering = false;

    /**
     * Constructs a FlickerEffect with the specified parameters.
     *
     * @param target             the JavaFX Node to apply the flicker effect to
     * @param flickerCount       the number of times the node should flicker
     * @param fadeDurationMillis the duration of each fade in/out in milliseconds
     */
    public FlickerEffect(Node target, int flickerCount, double fadeDurationMillis) {
        this.target = target;
        this.flickerCount = flickerCount;
        this.fadeDurationMillis = fadeDurationMillis;
    }

    /**
     * Triggers the flicker effect on the target node.
     * If the node is already flickering, subsequent calls are ignored until the current flicker completes.
     */
    public void trigger() {
        if (isFlickering) {
            return; // Prevent multiple concurrent flickers
        }

        isFlickering = true;

        SequentialTransition flickerTransition = new SequentialTransition();

        for (int i = 0; i < flickerCount; i++) {
            // Fade out
            FadeTransition fadeOut = new FadeTransition(Duration.millis(fadeDurationMillis), target);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);

            // Fade in
            FadeTransition fadeIn = new FadeTransition(Duration.millis(fadeDurationMillis), target);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);

            // Add both fade transitions to the sequential transition
            flickerTransition.getChildren().addAll(fadeOut, fadeIn);
        }

        // Reset the flicker flag once the animation completes
        flickerTransition.setOnFinished(event -> isFlickering = false);
        flickerTransition.play();
    }

    /**
     * Checks if the flicker effect is currently active.
     *
     * @return true if flickering, false otherwise
     */
    public boolean isFlickering() {
        return isFlickering;
    }
}
