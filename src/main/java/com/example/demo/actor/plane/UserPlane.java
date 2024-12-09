package com.example.demo.actor.plane;

import com.example.demo.effect.FlickerEffect;
import com.example.demo.handler.HealthChangeHandler;
import com.example.demo.util.GameConstant;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;

/**
 * The UserPlane class represents a fighter plane controlled by a user in the game.
 * It extends the FighterPlane class and includes additional functionality specific
 * to the user-controlled plane, such as handling health changes and applying damage.
 * 
 * <p>The UserPlane class maintains a list of HealthChangeHandlers that are notified
 * whenever the health of the plane changes. It also includes methods for adding
 * and removing these handlers.</p>
 * 
 * <p>The class provides methods for applying damage to the plane, triggering visual
 * effects when damage is taken, and checking if the plane is destroyed. It also
 * keeps track of the number of kills made by the user plane and provides methods
 * for retrieving and incrementing the kill count.</p>
 * 
 * @see <a href="https://github.com/swyannnn/CW2024/blob/master/src/main/java/com/example/demo/actor/plane/UserPlane.java">Github Source Code</a>
 * @see FighterPlane
 * @see PlaneConfig
 * @see HealthChangeHandler
 */
public class UserPlane extends FighterPlane {
    private List<HealthChangeHandler> healthChangeHandlers = new ArrayList<>();
    private int numberOfKills = GameConstant.UserPlane.NUMBER_OF_KILLS;
    private int flickerCount = GameConstant.UserPlane.DAMAGE_FLICKER_COUNT;;
    private FlickerEffect flickerEffect;


    /**
     * Constructs a UserPlane object with the specified configuration and player ID.
     *
     * @param config the configuration settings for the plane
     * @param playerId the ID of the player controlling the plane
     */
    public UserPlane(PlaneConfig config,int playerId) {
        super(config);
        this.health = config.health;
        this.flickerEffect = new FlickerEffect(this, flickerCount, 100); // 100 ms per fade
    }

    /**
     * Performs any additional updates specific to the UserPlane.
     * This method is called during the update cycle and can be overridden
     * to implement custom behavior.
     *
     * @param now The current time in nanoseconds.
     */
    @Override
    protected void performAdditionalUpdates(long now) {
        // Implement any additional updates specific to UserPlane if necessary
    }

    /**
     * Adds a HealthChangeHandler to the list of handlers that will be notified
     * when the health of the UserPlane changes.
     *
     * @param handler the HealthChangeHandler to be added
     */
    public void addHealthChangeHandler(HealthChangeHandler handler) {
        healthChangeHandlers.add(handler);
    }

    /**
     * Removes a HealthChangeHandler from the list of handlers.
     *
     * @param handler the HealthChangeHandler to be removed
     */
    public void removeHealthChangeHandler(HealthChangeHandler handler) {
        healthChangeHandlers.remove(handler);
    }

    /**
     * Notifies all registered health change handlers about the current health status.
     * This method iterates through the list of health change handlers and calls
     * their onHealthChange method, passing the current instance and its health value.
     */
    private void notifyHealthChange() {
        for (HealthChangeHandler handler : healthChangeHandlers) {
            handler.onHealthChange(this, this.health);
        }
    }

    /**
     * Applies damage to the user plane by decrementing its health.
     * Notifies any handlers of the health change and triggers a visual flicker effect if the plane is not destroyed.
     * If the health reaches zero, the plane is destroyed.
     *
     * @return true if damage was applied or the plane was destroyed, false otherwise.
     */
    @Override
    public boolean takeDamage() {
        health--;
        notifyHealthChange();

        if (!isDestroyed()) {
            Platform.runLater(() -> flickerEffect.trigger());
        }

        if (healthAtZero()) {
            destroy();
            return true; // Destruction occurred
        }
        return true; // Damage applied
    }

    /**
     * Checks if the user plane is destroyed.
     *
     * @return true if the health of the user plane is less than or equal to 0, false otherwise.
     */
    public boolean isDestroyed() {
        return health <= 0;
    }

    /**
     * Retrieves the number of kills made by the user plane.
     *
     * @return the number of kills
     */
    public int getNumberOfKills() {
        return numberOfKills;
    }

    /**
     * Increments the kill count for the user plane by one.
     */
    public void incrementKillCount() {
        numberOfKills++;
    }
}
