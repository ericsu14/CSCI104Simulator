/** Defines a basic entity in this game */

package entities;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import view.Launcher;

public class Entity extends ImageView 
{
	/* Entity's movement speed */
	protected double mMovementSpeed;
	/* Animation timer that allows the entity to rotate based on its current movement */
	protected AnimationTimer mRotationAnimation;
	/* Tracks the previous coordinate from where the entity was previously at */
	protected Point2D mPreviousCoord;
	/* Pointer to the main launcher */
	protected Launcher mController;
	/* Rotation rate */
	protected double mRotationSpeed = 2.0;
	
	/** Declares a new instance of a game enemy.
	 * 		@param initPosition - The position of the screen where the enemy initially spawns at before moving to its designated
	 * 							  origin point.
	 * 		@param origin - The position where the enemy resides while waiting to attack
	 * 		@param group - The enemy's group number. */
	public Entity (double x, double y, Launcher controller)
	{
		super();
		
		mController = controller;
		
		/* Sets the current coordinates of the entity */
		this.setX(x);
		this.setY(y);
		mPreviousCoord = new Point2D (x, y);
		
		/* Init. default variables */
		mMovementSpeed = 0.0;
		
		/* Initializes basic entity animations */
		initializeBasicAnimations();
	}
	
	/** @return The entity's forward vector */
	public Point2D getForward ()
	{
		double forwardX = Math.sin(this.getRotate());
		double forwardY = -(Math.cos(this.getRotate()));
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
				else if (mTheta >= 0 && getRotate() >= Math.toDegrees(mTheta))
				{
					setRotate(getRotate() - mRotationSpeed);
				}
				else if (mTheta < 0 && getRotate() > Math.toDegrees(mTheta))
				{
					setRotate (getRotate() - mRotationSpeed);
				}
				else if (mTheta < 0 && getRotate() <= Math.toDegrees(mTheta))
				{
					setRotate (getRotate() + mRotationSpeed);
				}
				
				/* Previous coordinate is now the current coordinate */
				mPreviousCoord = new Point2D (getX(), getY());
			}
			
		};
		
	}
	
	/** Cleans up all assets used by this entity */
	public void cleanUp()
	{
		mRotationAnimation.stop();
	}
}
