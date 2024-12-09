package com.example.demo.actor.plane;

/**
 * The EnemyPlane class represents an enemy fighter plane in the game.
 * It extends the FighterPlane class and can be configured with specific
 * settings for enemy planes.
 *
 * <p>This class provides a constructor to initialize the enemy plane
 * with a given configuration and a method to perform additional updates
 * specific to the enemy plane during the update cycle.</p>
 *
 * @see <a href="https://github.com/swyannnn/CW2024/blob/master/src/main/java/com/example/demo/actor/plane/EnemyPlane.java">Github Source Code</a>
 * @see FighterPlane
 * @see PlaneConfig
 */
public class EnemyPlane extends FighterPlane {
    /**
     * Constructs an EnemyPlane object.
     *
     * @param config The configuration of the enemy plane.
     */
    public EnemyPlane(PlaneConfig config) {
        super(config);
    }

    /**
     * Performs any additional updates specific to the EnemyPlane.
     * This method is called during the update cycle and can be overridden
     * to implement custom behavior for the EnemyPlane.
     *
     * @param now The current time in nanoseconds.
     */
    @Override
    protected void performAdditionalUpdates(long now) {
        // Implement any additional updates specific to EnemyPlane if necessary
    }
}