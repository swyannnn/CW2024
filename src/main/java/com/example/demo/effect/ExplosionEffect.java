package com.example.demo.effect;

import com.example.demo.manager.ImageManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;


/**
 * The ExplosionEffect class represents an explosion animation effect.
 * It initializes an ImageView for the explosion and creates a Timeline
 * to animate the explosion frames.
 * 
 * @see <a href="https://github.com/swyannnn/CW2024/blob/master/src/main/java/com/example/demo/effect/ExplosionEffect.java">Github Source Code</a>
 */
public class ExplosionEffect {
    private final ImageView explosionView;
    private final Timeline timeline;

    /**
     * Constructs an ExplosionEffect at the specified coordinates.
     *
     * @param x The X position of the explosion.
     * @param y The Y position of the explosion.
     */
    public ExplosionEffect(double x, double y) {
        this.explosionView = initializeExplosionView(x, y);
        this.timeline = createExplosionAnimation();
    }

    /**
     * Initializes the ImageView for the explosion.
     *
     * @param x The X position of the explosion.
     * @param y The Y position of the explosion.
     * @return The initialized ImageView.
     */
    private ImageView initializeExplosionView(double x, double y) {
        Image[] explosionImages = ImageManager.getImageSequence("explosion", 7);

        // Initialize the ImageView with the first explosion frame
        ImageView imageView = new ImageView(explosionImages[0]);
        imageView.setTranslateX(x);
        imageView.setTranslateY(y);
        imageView.setFitWidth(50); // Adjust size as needed
        imageView.setFitHeight(50); // Adjust size as needed

        return imageView;
    }

    /**
     * Creates the explosion animation timeline.
     *
     * @return The configured Timeline for the explosion animation.
     */
    private Timeline createExplosionAnimation() {
        // Retrieve the explosion image sequence
        Image[] explosionImages = ImageManager.getImageSequence("explosion", 7);

        Timeline explosionTimeline = new Timeline();

        // Add KeyFrames to the timeline for each explosion image
        for (int i = 0; i < explosionImages.length; i++) {
            final int frameIndex = i;
            KeyFrame keyFrame = new KeyFrame(
                Duration.millis(100 * i), // 100 ms per frame
                event -> explosionView.setImage(explosionImages[frameIndex])
            );
            explosionTimeline.getKeyFrames().add(keyFrame);
        }

        // Ensure the timeline plays only once
        explosionTimeline.setCycleCount(1);

        return explosionTimeline;
    }

    /**
     * Starts the explosion animation.
     */
    public void play() {
        timeline.play();
    }

    /**
     * Sets a callback to be executed when the explosion animation finishes.
     *
     * @param handler The event handler to execute on animation completion.
     */
    public void setOnFinished(EventHandler<ActionEvent> handler) {
        timeline.setOnFinished(handler);
    }

    /**
     * Gets the ImageView representing the explosion.
     *
     * @return The ImageView of the explosion.
     */
    public ImageView getExplosionView() {
        return explosionView;
    }
}
