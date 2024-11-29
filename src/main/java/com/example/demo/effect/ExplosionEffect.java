package com.example.demo.effect;

import com.example.demo.manager.ImageManager;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class ExplosionEffect {
    private final ImageView explosionView;
    private final Timeline timeline;

    /**
     * Constructs an ExplosionEffect.
     *
     * @param x The X position of the explosion.
     * @param y The Y position of the explosion.
     * @param explosionImages Array of explosion images.
     */
    public ExplosionEffect(double x, double y) {
        Image[] explosionImages = ImageManager.getInstance().getImageSequence("explosion", 7);
        explosionView = new ImageView(explosionImages[0]); // Set the first frame
        explosionView.setTranslateX(x);
        explosionView.setTranslateY(y);
        explosionView.setFitWidth(50);
        explosionView.setFitHeight(50);

        timeline = new Timeline();
        for (int i = 0; i < explosionImages.length; i++) {
            int frameIndex = i;
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(100 * i), e -> {
                explosionView.setImage(explosionImages[frameIndex]);
            }));
        }
        timeline.setCycleCount(1);
        timeline.setOnFinished(e -> explosionView.setVisible(false));
    }

    /**
     * Starts the explosion animation.
     */
    public void play() {
        timeline.play();
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
