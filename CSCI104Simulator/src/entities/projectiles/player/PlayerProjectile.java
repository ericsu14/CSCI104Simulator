package entities.projectiles.player;

import engine.GameEngine;
import entities.Entity;
import entities.EntityState;
import entities.EntityType;
import entities.player.Player;
import entities.projectiles.Projectile;
import javafx.geometry.Point2D;

public class PlayerProjectile extends Projectile 
{
	/* True if the projectile is fired in the player's rapid fire mode */
	private boolean mRapidFire;
	/* The threshold percentile of this projectile's movement speed increase */
	private double mMovementBonunsThreshold = 0.35;
	
	public PlayerProjectile(Entity owner, GameEngine controller, boolean isRapidFire) 
	{
		super(owner, controller);
		mRapidFire = isRapidFire;
		this.mSpriteScale = 20.0;
		this.setX(getX() + 5.0);
		
		/* Reduces the movement bonus threshold to 15% on hard mode */
		if (mController.isHardMode())
		{
			mMovementBonunsThreshold = 0.15;
		}
		
		/* TODO: Set the bullet's traits depending if it is fired in rapid fire
		 * mode or not */
		if (mRapidFire)
		{
			
		}
		
		else
		{
			this.mInitialMovementSpeed = 11.0;
			this.setSprite(controller.mPlayerBulletSprite);
			setRotate (90.0);
			
			/* Movement speed is increased based on the current level
			 * (increase of 1% for each level, with a max. of 35%) */
			this.mInitialMovementSpeed +=  this.mInitialMovementSpeed * ((mController.getCurrentLevel() - 1) / 100.0);
			if (this.mInitialMovementSpeed > (11.0 + (11.0 * mMovementBonunsThreshold)))
			{
				this.mInitialMovementSpeed = 11.0 + (11.0 * mMovementBonunsThreshold);
			}
			
			this.mMovementSpeed = mInitialMovementSpeed;
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
			if ((e.getType() == EntityType.kEnemy || e.getType() == EntityType.kBoss)
					&& e.getState() == EntityState.kActive
					&& e.intersects(this.getBoundsInLocal()))
			{
				kill(e, true);
				/* Spawns an explosion if this projectile hits a boss */
				if (e.getType() == EntityType.kBoss)
				{
					/* Spawns an explosion every time the boss is hit */
					mController.getGameView().getParticleLayer().spawnExplosion((int)this.getX(), (int)this.getY());
				}
				break;
			}
		}
		
		/* Checks if this projectile has left the screen */
		if (this.getY() < 0.0)
		{
			die();
		}
		
	}
	
	/** Overridden die method that updates the player's ammo pool once this entity is
	 *  despawned */
	@Override
	public void die()
	{
		super.die();
		Player p = (Player) mOwner;
		p.incrementPlayerAmmo();
	}

}
