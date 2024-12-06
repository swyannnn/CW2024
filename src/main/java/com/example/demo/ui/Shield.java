package com.example.demo.ui;

import com.example.demo.manager.ImageManager;
import com.example.demo.util.GameConstant;

import javafx.scene.image.ImageView;

/**
 * Shield class encapsulates all shield-related functionalities.
 */
public class Shield extends ImageView {

    // Constants
    private static final String IMAGE_PATH = "shield.png"; // Path to shield image
    private static final int SHIELD_SIZE = 150;

    // Position offsets relative to BossPlane
    private static final int shieldXPositionOffset = GameConstant.BossShield.X_POSITION_OFFSET;
    private static final int shieldYPositionOffset = GameConstant.BossShield.Y_POSITION_OFFSET;

    // Shield activation parameters
    private final double shieldActivationProbability;
    private static final int maxFramesWithShield = GameConstant.BossShield.MAX_FRAMES_WITH_SHIELD;
    private static final int maxFramesWithoutShield = GameConstant.BossShield.MAX_FRAMES_WITHOUT_SHIELD;

    // Shield state
    private boolean isShielded;
    private int framesWithShieldActivated;
    private int framesSinceLastShield;

    /**
     * Constructs a Shield instance with specified parameters.
     *
     * @param shieldXPositionOffset  X-axis offset from BossPlane
     * @param shieldYPositionOffset  Y-axis offset from BossPlane
     * @param shieldActivationProbability Probability to activate shield each frame
     * @param maxFramesWithShield    Maximum frames the shield remains active
     * @param maxFramesWithoutShield Maximum frames before shield can be reactivated
     */
    public Shield(double shieldActivationProbability) {
        super();
        this.setImage(ImageManager.getImage(IMAGE_PATH));
        this.setVisible(false);
        this.setFitHeight(SHIELD_SIZE);
        this.setFitWidth(SHIELD_SIZE);
        this.shieldActivationProbability = shieldActivationProbability;

        this.isShielded = false;
        this.framesWithShieldActivated = 0;
        this.framesSinceLastShield = maxFramesWithoutShield; // Initialize to allow immediate activation
    }

    /**
     * Updates the shield's state based on current frames.
     *
     * @param translateX Current translateX of BossPlane
     * @param translateY Current translateY of BossPlane
     */
    public void updateShieldState(double layoutX, double layoutY) {
        if (isShielded) {
            // Position the shield relative to BossPlane
            double newShieldX = layoutX + shieldXPositionOffset;
            double newShieldY = layoutY + shieldYPositionOffset;
            setLayoutX(newShieldX);
            setLayoutY(newShieldY);
            framesWithShieldActivated++;

            // Check if shield duration is exhausted
            if (isShieldDurationExhausted()) {
                deactivateShield();
            }
        } else {
            // Increment frames since last shield deactivation
            if (framesSinceLastShield < maxFramesWithoutShield) {
                framesSinceLastShield++;
            }

            // Determine if shield should be activated
            if (shouldActivateShield()) {
                activateShield();
            }
        }
    }

    /**
     * Determines whether the shield should be activated based on probability.
     *
     * @return True if shield should be activated, else false
     */
    private boolean shouldActivateShield() {
        return Math.random() < shieldActivationProbability && framesSinceLastShield >= maxFramesWithoutShield;
    }

    /**
     * Checks if the shield's active duration is exhausted.
     *
     * @return True if shield duration is exhausted, else false
     */
    private boolean isShieldDurationExhausted() {
        return framesWithShieldActivated >= maxFramesWithShield;
    }

    /**
     * Activates the shield.
     */
    public void activateShield() {
        isShielded = true;
        framesSinceLastShield = 0;
        framesWithShieldActivated = 0;
        showShield();
    }

    /**
     * Deactivates the shield.
     */
    public void deactivateShield() {
        isShielded = false;
        hideShield();
    }

    /**
     * Shows the shield visually.
     */
    public void showShield() {
        this.setVisible(true);
    }

    /**
     * Hides the shield visually.
     */
    public void hideShield() {
        this.setVisible(false);
    }

    /**
     * Checks if the shield is currently active.
     *
     * @return True if shield is active, else false
     */
    public boolean isShielded() {
        return isShielded;
    }
}
