package entities.projectiles.boss;

import engine.GameEngine;
import entities.Entity;
import entities.projectiles.enemies.EnemyProjectile;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;

public class BossProjectile extends EnemyProjectile
{
	
	/* Custom rotate */
	protected double mProjectileRotate;
	
	public BossProjectile(Entity owner, GameEngine controller) 
	{
		super(owner, controller);
		this.mSpriteScale = 40.0;
		this.mMovementSpeed = 7.4;
		this.setSprite(mController.mBinaryTree);
		mProjectileRotate = this.getRotate();
		
		this.mMaxTurnRadius = -45.0;
		this.mMinTurnRadius = -135.0;
		this.trackEntity(controller.getPlayer());
	}
	
	@Override
	public Point2D getForward() 
	{
		double forwardX = (Math.cos(Math.toRadians(mProjectileRotate)));
		double forwardY = -(Math.sin(Math.toRadians(mProjectileRotate)));
		Point2D forwardVector = new Point2D (forwardX, forwardY);
		forwardVector.normalize();
		return forwardVector;
	}
	
	@Override
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
					/* Wacky rotation animation */
					setRotate (getRotate() + 10.0);
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
						mProjectileRotate = mTheta;
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
}
