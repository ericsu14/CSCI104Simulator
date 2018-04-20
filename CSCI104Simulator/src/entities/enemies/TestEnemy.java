package entities.enemies;

import engine.GameEngine;
import entities.Entity;
import javafx.geometry.Point2D;

public class TestEnemy extends Entity 
{
	public TestEnemy(double x, double y, GameEngine controller) 
	{	
		super(x, y, controller);
		this.mInitialMovementSpeed = 3.0;
		this.mMovementSpeed = mInitialMovementSpeed;
		this.mSpriteScale = 20.0;
		this.setSprite(controller.mTestEnemySprite);
		setRotate (-90.0);
		testMovement();
	}
	
	
	public void testMovement ()
	{
		this.moveEntity(new Point2D (100.0, 313.0));
	}
}
