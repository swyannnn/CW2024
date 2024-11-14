package com.example.demo.level;

import javafx.scene.Group;

public class LevelViewLevelTwo extends LevelView {
	private final Group root;
	
	public LevelViewLevelTwo(Group root, int heartsToDisplay) {
		super(root, heartsToDisplay);
		this.root = root;
	}
}
