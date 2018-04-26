package entities.projectiles;

import engine.GameEngine;
import entities.Entity;
import entities.EntityType;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;

public abstract class Projectile extends Entity 
{
	/* A ref. to the entity that shot this projectile */
	protected Entity mOwner;
	/* The maximum angle in which this projectile could turn while
	 * tracking an enemy player */
	protected double mMaxTurnRadius;
	/* The min. angle in which this projectile could turn while
	 * tracking an enemy player */
	protected double mMinTurnRadius;
	/* Animation timer used for allowing a projectile to
	 * track an enemy */
	protected AnimationTimer mTrackAnimation;
	/* Flag used to determine if this projectile is tracking an enemy */
	protected boolean mIsTracking = false;
	/* The max Y value threshold a projectile could travel before being unable to
	 * rotate anymore */
	protected double mMaxY;
	
	/** Constructor
	 * 		@param owner - The entity who shot this projectile
	 * 		@param controler - A ref. to the main controller  */
	public Projectile (Entity owner, GameEngine controller)
	{
		super (owner.getX(), owner.getY(), controller);
		
		mOwner = owner;
		this.mType = EntityType.kProjectile;
		mMaxY = controller.getPlayer().getY() - 100.0;
	}
	
	/** Allows this projectile to track another entity.
	 * 		@param victim - The victim who is going to be shot */
	public void trackEntity (Entity victim)
	{
		if (!mIsTracking)
		{
			mTrackAnimation = new AnimationTimer ()
			{
				/* The angle between this projectile and the target entity */
				private double mTheta;
				
				/** Animation timer used to allow this entity to track to its assigned
				 *  target */
				public void handle (long now)
				{
					Point2D currentPosition = new Point2D (getX(), getY());
					Point2D victimPosition = new Point2D (victim.getX(), victim.getY());
					
					victimPosition = victimPosition.subtract(currentPosition);
					mTheta = Math.toDegrees(Math.atan2(-victimPosition.getY(), victimPosition.getX()));
					
					/* Limits the turn radius of this projectile */
					if (mTheta < mMinTurnRadius)
					{
						mTheta = mMinTurnRadius;
					}
					
					else if (mTheta > mMaxTurnRadius)
					{
						mTheta = mMaxTurnRadius;
					}
					
					if (getY() <= mMaxY)
					{
						setRotate (mTheta);
					}
					Point2D velocity = getForward();
					velocity = velocity.multiply(mMovementSpeed);
					
					setX (getX() + velocity.getX());
					setY (getY() + velocity.getY());
				}
			};
			
			mTrackAnimation.start();
		}
	}
	
	/** Returns the owner who fired this projectile */
	public Entity getOwner()
	{
		return mOwner;
	}
	
	/** Overrides the existing die method to stop the newly added 
	 *  tracking animation. */
	@Override
	public void die()
	{
		super.die();
		if (mIsTracking)
		{
			mTrackAnimation.stop();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (mIsTracking ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(mMaxTurnRadius);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(mMinTurnRadius);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((mOwner == null) ? 0 : mOwner.hashCode());
		result = prime * result + ((mTrackAnimation == null) ? 0 : mTrackAnimation.hashCode());
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
		Projectile other = (Projectile) obj;
		if (mIsTracking != other.mIsTracking)
			return false;
		if (Double.doubleToLongBits(mMaxTurnRadius) != Double.doubleToLongBits(other.mMaxTurnRadius))
			return false;
		if (Double.doubleToLongBits(mMinTurnRadius) != Double.doubleToLongBits(other.mMinTurnRadius))
			return false;
		if (mOwner == null) {
			if (other.mOwner != null)
				return false;
		} else if (!mOwner.equals(other.mOwner))
			return false;
		if (mTrackAnimation == null) {
			if (other.mTrackAnimation != null)
				return false;
		} else if (!mTrackAnimation.equals(other.mTrackAnimation))
			return false;
		return true;
	}
	
}
