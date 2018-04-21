/** Defines a basic entity in this game */

package entities;

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
				/* Used to approximate the destination point by constructing a box around that point with the
				 * given offset. Once the entity reaches that area, it would then stop. */
				private int mOffset = 2;
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
					if (inRange ((int)getX(), (int)destination.getX() - mOffset, (int)destination.getX() + mOffset) && inRange((int)getY(), (int)destination.getY() - mOffset, (int)destination.getY() + mOffset))
					{
						this.stop();
						setRotate (mInitialOrientation);
						mMovementSpeed = mInitialMovementSpeed;
						mWaypointFlag = false;
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
	
	/** @return the entity's type */
	public EntityType getType ()
	{
		return mType;
	}
	
	/** Called by the game engine to update this individual entity. */
	public abstract void update();
	
	/** @return True if target is between min and max
	 * 		@param target - Value being compared
	 * 		@param min - Min. value of selected range
	 * 		@param max - Max. value of selected range */
	protected boolean inRange(int target, int min, int max)
	{
		return (min <= target && target <= max);
	}
}
