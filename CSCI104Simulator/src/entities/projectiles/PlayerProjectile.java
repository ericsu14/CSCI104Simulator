package entities.projectiles;

import engine.GameEngine;
import entities.Entity;
import entities.EntityState;
import entities.EntityType;
import javafx.geometry.Point2D;

public class PlayerProjectile extends Projectile 
{
	/* True if the projectile is fired in the player's rapid fire mode */
	
	private boolean mRapidFire;
	public PlayerProjectile(Entity owner, GameEngine controller, boolean isRapidFire) 
	{
		super(owner, controller);
		mRapidFire = isRapidFire;
		this.mSpriteScale = 20.0;
		/* TODO: Set the bullet's traits depending if it is fired in rapid fire
		 * mode or not */
		if (mRapidFire)
		{
			
		}
		
		else
		{
			this.mInitialMovementSpeed = 7.0;
			this.mMovementSpeed = mInitialMovementSpeed;
			this.setSprite(controller.mPlayerBulletSprite);
			setRotate (90.0);
		}
		this.moveEntity(new Point2D (mOwner.getX(), -100));
	}

	@Override
	public void update() 
	{
		/* Checks if this projectile has collided with any active enemy
		 * in the game */
		for (Entity e : mController.getEntities())
		{
			if (e.getType() == EntityType.kEnemy
					&& e.getState() == EntityState.kActive
					&& e.intersects(this.getBoundsInLocal()))
			{
				
				// System.out.println("Collision!");
				kill(e, true);
				/* TODO: Update the player's score */
				break;
			}
		}
		
		/* Checks if this projectile has left the screen */
		if (this.getY() < 0.0)
		{
			this.setState(EntityState.kDead);
		}
		
		/* TODO: If the projectile's state is dead, then update the player's
		 * ammo pool */
	}

}
