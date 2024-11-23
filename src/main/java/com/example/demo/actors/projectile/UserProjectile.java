package com.example.demo.actors.projectile;

import com.example.demo.actors.planes.UserPlane;

public class UserProjectile extends Projectile {
	private final UserPlane owner;
	private static final String IMAGE_NAME = "userfire.png";
	private static final int IMAGE_HEIGHT = 125;
	private static final int HORIZONTAL_VELOCITY = 15;

	public UserProjectile(double initialXPos, double initialYPos, UserPlane owner) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
		this.owner = owner;
	}
	
    public UserPlane getOwner() {
        return owner;
    }

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}
	
	@Override
	public void updateActor() {
		updatePosition();
	}
	
}
