package com.example.demo.actor.plane.components;

import com.example.demo.manager.ImageManager;
import com.example.demo.util.GameConstant;

import javafx.scene.image.ImageView;


/**
 * The Shield class represents a visual shield that can be activated and deactivated
 * based on certain conditions. It extends the ImageView class to display the shield
 * image and manage its visibility and position relative to a BossPlane.
 * 
 * <p>This class handles the shield's activation probability, duration, and cooldown
 * period. It provides methods to update the shield's state, activate and deactivate
 * the shield, and check if the shield is currently active.</p>
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
     * Determines whether the shield should be activated based on the shield activation probability
     * and the number of frames since the last shield activation.
     *
     * @return {@code true} if the shield should be activated; {@code false} otherwise.
     */
    private boolean shouldActivateShield() {
        return Math.random() < shieldActivationProbability && framesSinceLastShield >= maxFramesWithoutShield;
    }

    /**
     * Checks if the shield duration has been exhausted.
     *
     * @return true if the number of frames with the shield activated is greater than or equal to the maximum allowed frames, false otherwise.
     */
    private boolean isShieldDurationExhausted() {
        return framesWithShieldActivated >= maxFramesWithShield;
    }

    /**
     * Activates the shield for the entity.
     * This method sets the shield status to active, resets the frame counters,
     * and displays the shield.
     */
    public void activateShield() {
        isShielded = true;
        framesSinceLastShield = 0;
        framesWithShieldActivated = 0;
        showShield();
    }

    /**
     * Deactivates the shield by setting the isShielded flag to false and hiding the shield.
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
