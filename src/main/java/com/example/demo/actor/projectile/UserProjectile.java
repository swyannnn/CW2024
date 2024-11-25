package com.example.demo.actor.projectile;

import com.example.demo.actor.plane.UserPlane;
import com.example.demo.controller.Controller;

public class UserProjectile extends Projectile {
	private final UserPlane owner;
	private static final String IMAGE_NAME = "userfire.png";
	private static final int IMAGE_HEIGHT = 125;
	private static final int HORIZONTAL_VELOCITY = 15;

	public UserProjectile(double initialXPos, double initialYPos, UserPlane owner, Controller controller) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, HORIZONTAL_VELOCITY, controller);
		this.owner = owner;
	}
	
    public UserPlane getOwner() {
        return owner;
    }
}
