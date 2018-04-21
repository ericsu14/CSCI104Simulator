package entities.enemies;

import engine.GameEngine;
import entities.Entity;
import javafx.geometry.Point2D;

public class TestEnemy extends Enemy 
{
	public TestEnemy(EnemyPosition initPosition, Point2D origin, int group, GameEngine controller) 
	{	
		super (initPosition, origin, group, controller);
		this.mInitialMovementSpeed = 3.0;
		this.mMovementSpeed = mInitialMovementSpeed;
		this.mSpriteScale = 20.0;
		this.setSprite(controller.mTestEnemySprite);
		setRotate (-90.0);
	}


}
