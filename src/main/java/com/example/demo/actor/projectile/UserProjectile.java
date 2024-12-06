package com.example.demo.actor.projectile;

import com.example.demo.actor.plane.UserPlane;
import com.example.demo.util.GameConstant;

public class UserProjectile extends Projectile {
	private final UserPlane owner;

	public UserProjectile(double initialXPos, double initialYPos, UserPlane plane) {
		super(GameConstant.UserProjectile.IMAGE_NAME, GameConstant.UserProjectile.IMAGE_HEIGHT, initialXPos, initialYPos, GameConstant.UserProjectile.HORIZONTAL_VELOCITY);
		this.owner = plane;
	}
	
    public UserPlane getOwner() {
        return owner;
    }
}
