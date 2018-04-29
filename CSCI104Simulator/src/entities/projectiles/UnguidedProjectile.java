package entities.projectiles;

import engine.GameEngine;
import entities.Entity;
import javafx.geometry.Point2D;

public class UnguidedProjectile extends EnemyProjectile
{

	public UnguidedProjectile(Entity owner, GameEngine controller) 
	{
		super(owner, controller);
		this.mSpriteScale = 15.0;
		this.mMovementSpeed = 3.5;
		this.setSprite(mController.mEnemyLaser);
		
		this.moveEntity(new Point2D (this.getX(), this.getY() + 1000));
	}

}
