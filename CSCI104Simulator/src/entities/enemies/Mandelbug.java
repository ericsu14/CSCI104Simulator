package entities.enemies;

import engine.GameEngine;
import javafx.geometry.Point2D;

public class Mandelbug extends Enemy
{

	public Mandelbug(EnemyPosition initPosition, Point2D origin, int group, GameEngine controller) 
	{
		super(initPosition, origin, group, controller);
		
		this.mInitialMovementSpeed = 3.0;
		this.mMovementSpeed = mInitialMovementSpeed;
		this.mSpriteScale = 20.0;
		this.setSprite(controller.mMandelBugSprite);
		setRotate (-90.0);
	}

	@Override
	public void createAttackVectors() 
	{
		
	}
	
	@Override
	public void fire()
	{
		
	}
	
}