package com.example.demo.actor;

public interface Destructible {

	/**
	 * Method to take damage.
	 */
	boolean takeDamage();


	/**
	 * Method to destroy the actor.
	 */
	void destroy();
	
}
