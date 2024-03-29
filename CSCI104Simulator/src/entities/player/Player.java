package entities.player;
import engine.GameEngine;
import entities.Entity;
import entities.EntityState;
import entities.EntityType;
import entities.projectiles.player.PlayerProjectile;
import entities.sprites.Sprite;
import javafx.geometry.Point2D;
import media.SoundType;

public class Player extends Entity
{
	/* The player's direction of moving */
	private MoveDirection mCurrentDirection;
	/* The player's own rotation angle */
	private double mPlayerRotation;
	/* The player's current ammo */
	private int mCurrentAmmo;
	/* The player's max ammo pool */
	private int mMaxAmmoPool;
	/* Flag used to determine if this ship is firing a projectile */
	private boolean mFiringFlag;
	/* Rate of fire of the ship's cannons */
	private int mFramesPerShot = 9;
	/* The current time before the ship could fire its next shot */
	private int mCurrentFramesPerShot;
	/* When the player respawns, the player would be invincible for 
	 * a certain amount of frames */
	private int mInvincibilityFrames = 240;
	/* Timer for the player's invincibility */
	private int mInvincibilityTimer;
	/* Flag used to determine if this player is invincible */
	private boolean mInvincible;
	
	/** Constructs a new playership
	 * 		@param x - x Position where the player would spawn
	 * 		@param y - y Position where the player would spawn
	 * 		@param controller - A pointer to the main launcher class */
	public Player (double x, double y, GameEngine controller)
	{
		super (x, y, controller);
		this.setCache(true);
		
		mMovementSpeed = 6.0;
		mSpriteScale = 30.0;
		mPlayerRotation = 90.0;
		mType = EntityType.kPlayer;
		mCurrentDirection = MoveDirection.kNone;
		mPlayerRotation = this.getRotate();
		mMaxAmmoPool = 2;
		mCurrentAmmo = mMaxAmmoPool;
		mFiringFlag = false;
		mInvincible = false;
		mCurrentFramesPerShot = mFramesPerShot;
		setSprite (Sprite.kPlayerShip);
	}

	@Override
	public void update() 
	{
		/* Updates the player's invincibility */
		if (this.mInvincible)
		{
			if (this.mInvincibilityTimer <= 0)
			{
				makeMortal();
			}
			else
			{
				--this.mInvincibilityTimer;
			}
		}
		/* Moves this character's position if the playe is set to move either left or right */
		if (mCurrentDirection != MoveDirection.kNone)
		{
			double moveX;
			/* Sets the player's current rotation based on the updated direction */
			if (mCurrentDirection == MoveDirection.kRight)
			{
				mPlayerRotation = 0.0;
			}
			else
			{
				mPlayerRotation = 180.0;
			}
			
			/* Moves the player in that current direction */
			Point2D velocity = getForward();
			velocity = velocity.multiply(mMovementSpeed);
			moveX = getX() + velocity.getX();
			
			/* Checks if the player is attempting to move out of the game's set boundries */
			if (inRange ((int)moveX, mController.getLeftBorder(), mController.getRightBorder()))
			{
				this.setX(getX() + velocity.getX());
			}
		}
		
		else
		{
			mPlayerRotation = 90.0;
		}
		
		/* Fires the ship's cannons if the firing flag is true */
		if (mFiringFlag)
		{
			/* Checks if the current frames per shot has reached zero.
			 * If so, fire ze cannons */
			if (mCurrentFramesPerShot <= 0 && mCurrentAmmo > 0)
			{
				mController.queueEntity(new PlayerProjectile (this, mController, false));
				--mCurrentAmmo;
				mCurrentFramesPerShot = mFramesPerShot;
				mController.getGameView().getSoundEngine().playSound(SoundType.kPlayerShoot);
			}
			else
			{
				--mCurrentFramesPerShot;
			}
		}
	}
	
	/** Overridden because the player's sprite never rotates */
	@Override
	public double getRotationAngle () {
		return this.mPlayerRotation;
	}
	
	/** Sets the player's move direction */
	public void setMoveDirection (MoveDirection direction)
	{
		mCurrentDirection = direction;
	}
	
	/** @return the player's move direction */
	public MoveDirection getMoveDirection()
	{
		return mCurrentDirection;
	}
	
	/** Sets the player's firing flag */
	public void setFiringFlag (boolean newFlag)
	{
		if (mFiringFlag != newFlag)
		{
			mFiringFlag = newFlag;
			mCurrentFramesPerShot = 0;
		}
	}
	
	/** Overridden die method */
	public void die()
	{
		if (!this.mInvincible)
		{
			super.die();
			/* Sets this player's opacity to 0 */
			this.setOpacity(0);
			setFiringFlag (false);
			mController.playSound(SoundType.kPlayerExplode);
		}
	}
	
	/** Respawns the player */
	public void respawn()
	{
		/* Sets the player's state to active */
		mState = EntityState.kActive;
		
		/* Makes this player visible to the game again */
		this.setOpacity(1.0);
		
		/* Recenters this player's coordinates to the center of the screen */
		Point2D centerCoordinates = mController.getPlayerCenterCoordinates();
		this.setX(centerCoordinates.getX());
		this.setY(centerCoordinates.getY());
		
		/* Makes this player invincible for a short amount of time */
		makeInvincible ();
	}
	
	/** Increments the player's current ammo (capped at the player's max
	 *  capacity) */
	public void incrementPlayerAmmo ()
	{
		if (mCurrentAmmo < mMaxAmmoPool)
		{
			++mCurrentAmmo;
		}
	}
	
	/** Sets the player's max ammo pool to a new value*/
	public void setMaxAmmoPool (int ammo)
	{
		mMaxAmmoPool = ammo;
	}
	
	/** @return the player's max ammo pool count */
	public int getMaxAmmoPool()
	{
		return mMaxAmmoPool;
	}
	
	/** @return the player's current ammo */
	public int getPlayerAmmo ()
	{
		return mCurrentAmmo;
	}
	
	/** Makes this player invincible */
	public void makeInvincible ()
	{
		this.mInvincible = true;
		mInvincibilityTimer = this.mInvincibilityFrames;
		this.setOpacity(0.3);
	}
	
	/** Revokes this player's invincible status */
	public void makeMortal()
	{
		this.mInvincible = false;
		mInvincibilityTimer = this.mInvincibilityFrames;
		this.setOpacity(1.0);
	}

}
