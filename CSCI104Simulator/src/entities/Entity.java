/** Defines a basic entity in this game */

package entities;

import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import engine.GameEngine;

public abstract class Entity extends ImageView 
{
	/* The default movement speed of this entity */
	protected double mInitialMovementSpeed = 2.0;
	/* Entity's movement speed */
	protected double mMovementSpeed;
	/* Animation timer that allows the entity to rotate based on its current movement */
	protected AnimationTimer mRotationAnimation;
	/* Animation timer that allows this entity to move themselves to a desginated waypoint of
	 * the game grid */
	protected AnimationTimer mWaypointAnimation;
	/* A flag that checks if the waypoint move function has been completed */
	protected boolean mWaypointFlag = false;
	/* Tracks the previous coordinate from where the entity was previously at */
	protected Point2D mPreviousCoord;
	/* Pointer to the main launcher */
	protected GameEngine mController;
	/* Rotation rate */
	protected double mRotationSpeed = 2.0;
	/* Scale of this entity's sprite */
	protected double mSpriteScale = 10.0;
	/* The initial orientation of this entity */
	protected double mInitialOrientation = -90.0;
	/* The current state of this entity */
	protected EntityState mState;
	/* The entity's type */
	protected EntityType mType;
	/* Used to approximate the destination point by constructing a box around that point with the
	 * given offset. Once the entity reaches that area, it would then stop. */
	protected int mWaypointOffset = 2;
	/* Random number generator shared across all entities */
	protected static Random mRand = new Random();
	
	/** Declares a new instance of a game enemy.
	 * 		@param initPosition - The position of the screen where the enemy initially spawns at before moving to its designated
	 * 							  origin point.
	 * 		@param origin - The position where the enemy resides while waiting to attack
	 * 		@param group - The enemy's group number. */
	public Entity (double x, double y, GameEngine controller)
	{
		super();
		
		mState = EntityState.kActive;
		mController = controller;
		
		/* Sets the current coordinates of the entity */
		this.setX(x);
		this.setY(y);
		mPreviousCoord = new Point2D (x, y);
		mType = EntityType.kUnknown;
		
		/* Initializes basic entity animations */
		initializeBasicAnimations();
	}
	
	/** @return The entity's forward vector */
	public Point2D getForward ()
	{
		double forwardX = (Math.cos(Math.toRadians(this.getRotate())));
		double forwardY = -(Math.sin(Math.toRadians(this.getRotate())));
		Point2D forwardVector = new Point2D (forwardX, forwardY);
		forwardVector.normalize();
		return forwardVector;
	}
	
	/** Initializes the entity's basic animation timers, such as 
	 *  rotating based on its current movement */
	protected void initializeBasicAnimations ()
	{
		/* Rotation animation */
		mRotationAnimation = new AnimationTimer ()
		{	
			/* Current position of the entity */
			private Point2D mCurrentPosition = new Point2D (getX(), getY());
			/* Angle between the current position and the previous position */
			private double mTheta;
			
			/** On every frame, calculate the angle between the entity's 
			 *  previous position and the entity's current position
			 *  and add the result to the rotation angle. */
			@Override
			public void handle(long arg0) 
			{
				mCurrentPosition = new Point2D (getX(), getY());
				mCurrentPosition = mCurrentPosition.subtract(mPreviousCoord);
				
				/* Calculates the inverse tangent to get the angle change */
				mTheta = Math.atan2(mCurrentPosition.getX(), -mCurrentPosition.getY());
				
				/* Slowly rotates to the new angle based on the current theta */
				if (mTheta >= 0 && getRotate() < Math.toDegrees(mTheta))
				{
					setRotate(getRotate() + mRotationSpeed);
				}
				else if (mTheta >= 0 && getRotate() > Math.toDegrees(mTheta))
				{
					setRotate(getRotate() - mRotationSpeed);
				}
				else if (mTheta < 0 && getRotate() > Math.toDegrees(mTheta))
				{
					setRotate (getRotate() - mRotationSpeed);
				}
				else if (mTheta < 0 && getRotate() < Math.toDegrees(mTheta))
				{
					setRotate (getRotate() + mRotationSpeed);
				}
				
				/* Previous coordinate is now the current coordinate */
				mPreviousCoord = new Point2D (getX(), getY());
			}
			
		};
		
	}
	
	/** Moves an entity to a designated coordinate of the graph */
	public void moveEntity (Point2D destination)
	{
		/* Only construct a new waypoint if the corresponding flag is false */
		if (!mWaypointFlag)
		{
			mWaypointFlag = true;;
			mWaypointAnimation = new AnimationTimer ()
			{
				/* The angle the entity would have to face to in order to reach the waypoint */
				private double mTheta = 0.0;
				/* The last instance of theta */
				private double mLastTheta;
				/* The number of frames before the rotation angle is recalculated */
				private int mFramesTillSetup = 0;
				/* True if the first calculated theta is negative */
				private boolean mIsNegative = false;
				/* Used to conduct run-once animations */
				private boolean mHasStart = false;
				
				public void handle(long now)
				{
					/* Sets up the angle theta if it hasn't been setup already */
					if (mFramesTillSetup <= 0)
					{
						Point2D currentPosition = new Point2D (getX(), getY());
						Point2D waypoint = destination;
						waypoint = waypoint.subtract(currentPosition);
						mLastTheta = mTheta;
						mTheta = Math.toDegrees(Math.atan2(-waypoint.getY(), waypoint.getX()));
						
						/* There is a case when if the change of theta is between +- 300 to +- 360,
						 * the pathfinding algorithm would encounter an infinite loop, since
						 * arctan2 would force the entity to rotate their negative / positive inverses
						 * which would cause an infinite loop. This quick hack ensures this never happens.  */
						if (inRange ((int)mLastTheta, -360, -300) || inRange ((int)mLastTheta, 300, 360))
						{
							// Instantly rotates the character to theta
							setRotate (mTheta);
						}
						
						/* Used to determine if the initial theta is either negative or positive */
						if (!mHasStart)
						{
							mIsNegative = (mTheta < 0.0);
							mHasStart = true;
						}
						else
						{
							/* Some ugly allignment stuff */
							if (mIsNegative && mTheta >= 0.0)
							{
								mTheta -= 360.0;
							}
							else if (!mIsNegative && mTheta < 0.0)
							{
								mTheta += 360.0;
							}
						}
					}
					
					/* Once theta has been setup, direct the entity to face and move
					 * to the waypoint. */
					if (mTheta >= 0 && getRotate() < mTheta)
					{
						setRotate((int)(getRotate() + mRotationSpeed));
					}
					else if (mTheta >= 0 && getRotate() > mTheta)
					{
						setRotate((int)(getRotate() - mRotationSpeed));
					}
					else if (mTheta < 0 && getRotate() > mTheta)
					{
						setRotate ((int)(getRotate() - mRotationSpeed));
					}
					else if (mTheta < 0 && getRotate() < mTheta)
					{
						setRotate ((int)(getRotate() + mRotationSpeed));
					}
					
					Point2D velocity = getForward();

					velocity = velocity.multiply(mMovementSpeed);
					setX (getX() + velocity.getX());
					setY (getY() + velocity.getY());
					
					--mFramesTillSetup;
					
					/* If the entity has reached somewhere close to the destination based on the offset,
					 * stop moving. */
					if (inRange ((int)getX(), (int)destination.getX() - mWaypointOffset, (int)destination.getX() + mWaypointOffset) && inRange((int)getY(), (int)destination.getY() - mWaypointOffset, (int)destination.getY() + mWaypointOffset))
					{
						stopWaypointAnimation();
					}
				}
			};
			
			mWaypointAnimation.start();
		}
	}
	
	/** Cleans up all assets used by this entity */
	public void cleanUp()
	{
		mRotationAnimation.stop();
	}
	
	/** Scales and sets this entity's sprite asset
	 * 		@param sprite - The sprite this entity is going to use */
	public void setSprite (ImageView sprite)
	{
		setImage (sprite.getImage());
		setFitHeight(mSpriteScale);
		setFitWidth(mSpriteScale);
		if (this.getType() != EntityType.kBoss)
		{
			this.mWaypointOffset = (int)(mSpriteScale / 10);
		}
		else
		{
			this.mWaypointOffset = (int)(mSpriteScale / 20);
		}
	}
	
	/** @return the entity's current state */
	public EntityState getState()
	{
		return mState;
	}
	
	/** Sets this entity's state to a new state */
	public void setState (EntityState state)
	{
		mState = state;
	}
	
	/** Sets this entity's movement offset to a new value */
	public void setOffset (int newOffset)
	{
		mWaypointOffset = newOffset;
	}
	
	/** @return the entity's type */
	public EntityType getType ()
	{
		return mType;
	}
	
	/** Called by the game engine to update this individual entity. */
	public abstract void update();
	
	/** Allows this entity to "die" by killing off all animations */
	public void die()
	{
		if (getType() != EntityType.kPlayer)
		{
			mState = EntityState.kDead;
		}
		else
		{
			mState = EntityState.kPlayerDead;
		}
		
		/* Stops all active animations */
		if (mWaypointFlag)
		{
			mWaypointAnimation.stop();
		}
		
	}
	
	/** Allows this entity to kill off another entity */
	public void kill (Entity victim, boolean intersect)
	{
		if (intersect)
		{
			this.die();
		}
		victim.die();
	}
	
	/** Returns the scale multiplier of this entity's sprite */
	public double getSpriteScale ()
	{
		return mSpriteScale;
	}
	
	/** Returns the center X coordiante of this entity */
	public double getCenterX ()
	{
		return getX() + (this.getFitWidth() / 2.0);
	}
	
	/** Returns the center Y coordinate of this entity */
	public double getCenterY()
	{
		return getY() + (this.getFitHeight() / 2.0);
	}
	
	/** @return True if target is between min and max
	 * 		@param target - Value being compared
	 * 		@param min - Min. value of selected range
	 * 		@param max - Max. value of selected range */
	protected boolean inRange(int target, int min, int max)
	{
		return (min <= target && target <= max);
	}
	
	/** Stops the waypoint animation. Can be overridden by
	 *  other classes to have different effects. */
	protected void stopWaypointAnimation ()
	{
		mWaypointAnimation.stop();
		mMovementSpeed = mInitialMovementSpeed;
		mWaypointFlag = false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mController == null) ? 0 : mController.hashCode());
		long temp;
		temp = Double.doubleToLongBits(mInitialMovementSpeed);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(mInitialOrientation);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(mMovementSpeed);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((mPreviousCoord == null) ? 0 : mPreviousCoord.hashCode());
		result = prime * result + ((mRotationAnimation == null) ? 0 : mRotationAnimation.hashCode());
		temp = Double.doubleToLongBits(mRotationSpeed);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(mSpriteScale);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((mState == null) ? 0 : mState.hashCode());
		result = prime * result + ((mType == null) ? 0 : mType.hashCode());
		result = prime * result + ((mWaypointAnimation == null) ? 0 : mWaypointAnimation.hashCode());
		result = prime * result + (mWaypointFlag ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entity other = (Entity) obj;
		if (mController == null) {
			if (other.mController != null)
				return false;
		} else if (!mController.equals(other.mController))
			return false;
		if (Double.doubleToLongBits(mInitialMovementSpeed) != Double.doubleToLongBits(other.mInitialMovementSpeed))
			return false;
		if (Double.doubleToLongBits(mInitialOrientation) != Double.doubleToLongBits(other.mInitialOrientation))
			return false;
		if (Double.doubleToLongBits(mMovementSpeed) != Double.doubleToLongBits(other.mMovementSpeed))
			return false;
		if (mPreviousCoord == null) {
			if (other.mPreviousCoord != null)
				return false;
		} else if (!mPreviousCoord.equals(other.mPreviousCoord))
			return false;
		if (mRotationAnimation == null) {
			if (other.mRotationAnimation != null)
				return false;
		} else if (!mRotationAnimation.equals(other.mRotationAnimation))
			return false;
		if (Double.doubleToLongBits(mRotationSpeed) != Double.doubleToLongBits(other.mRotationSpeed))
			return false;
		if (Double.doubleToLongBits(mSpriteScale) != Double.doubleToLongBits(other.mSpriteScale))
			return false;
		if (mState != other.mState)
			return false;
		if (mType != other.mType)
			return false;
		if (mWaypointAnimation == null) {
			if (other.mWaypointAnimation != null)
				return false;
		} else if (!mWaypointAnimation.equals(other.mWaypointAnimation))
			return false;
		if (mWaypointFlag != other.mWaypointFlag)
			return false;
		return true;
	}

}
