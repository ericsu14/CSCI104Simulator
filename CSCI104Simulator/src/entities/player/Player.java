package entities.player;
import engine.GameEngine;
import entities.Entity;
import entities.EntityState;
import entities.EntityType;
import entities.projectiles.PlayerProjectile;
import javafx.geometry.Point2D;

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
	
	/** Constructs a new playership
	 * 		@param x - x Position where the player would spawn
	 * 		@param y - y Position where the player would spawn
	 * 		@param controller - A pointer to the main launcher class */
	public Player (double x, double y, GameEngine controller)
	{
		super (x, y, controller);
		mMovementSpeed = 6.0;
		mSpriteScale = 30.0;
		mPlayerRotation = 90.0;
		mType = EntityType.kPlayer;
		mCurrentDirection = MoveDirection.kNone;
		mPlayerRotation = this.getRotate();
		mMaxAmmoPool = 2;
		mCurrentAmmo = mMaxAmmoPool;
		setSprite (controller.mPlayerShipSprite);
	}

	@Override
	public void update() 
	{
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
		
	}
	
	/** Because the player's sprite never rotates, the player would have to use a slightly altered forward vector calculation algorithm
	 *  that uses the player's "hidden rotation angle" */
	@Override
	public Point2D getForward ()
	{
		double forwardX = (Math.cos(Math.toRadians(mPlayerRotation)));
		double forwardY = -(Math.sin(Math.toRadians(mPlayerRotation)));
		Point2D forwardVector = new Point2D (forwardX, forwardY);
		forwardVector.normalize();
		return forwardVector;
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
	
	/** Fires a projectile at the player's current position */
	public void shoot()
	{
		if (mCurrentAmmo > 0)
		{
			mController.queueEntity(new PlayerProjectile (this, mController, false));
			--mCurrentAmmo;
		}
	}
	
	/** Overridden die method */
	public void die()
	{
		super.die();
		
		/* Sets this player's opacity to 0 */
		this.setOpacity(0);
	}
	
	/** Respawns the player */
	public void respawn()
	{
		/* Sets the player's state to active */
		mState = EntityState.kActive;
		
		/* Makes this player visable to the game again */
		this.setOpacity(1.0);
		
		/* Recenters this player's coordinates to the center of the screen */
		Point2D centerCoordinates = mController.getPlayerCenterCoordinates();
		this.setX(centerCoordinates.getX());
		this.setY(centerCoordinates.getY());
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((mCurrentDirection == null) ? 0 : mCurrentDirection.hashCode());
		long temp;
		temp = Double.doubleToLongBits(mPlayerRotation);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (mCurrentDirection != other.mCurrentDirection)
			return false;
		if (Double.doubleToLongBits(mPlayerRotation) != Double.doubleToLongBits(other.mPlayerRotation))
			return false;
		return true;
	}
}
