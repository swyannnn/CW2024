package com.example.demo.actor.projectile;

import com.example.demo.util.GameConstant;

public class BossProjectile extends Projectile {
	private static final String IMAGE_NAME = GameConstant.BossProjectile.IMAGE_NAME;
	private static final int IMAGE_HEIGHT = GameConstant.BossProjectile.IMAGE_HEIGHT;
	private static final double HORIZONTAL_VELOCITY = GameConstant.BossProjectile.HORIZONTAL_VELOCITY;

	public BossProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, HORIZONTAL_VELOCITY);
	}
}
