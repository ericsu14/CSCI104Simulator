/** Class that defines the base structure for an enemy in this game */

package entities.enemies;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import engine.GameEngine;
import entities.Entity;
import entities.EntityState;
import entities.EntityType;
import javafx.geometry.Point2D;
import view.Launcher;

public abstract class Enemy extends Entity
{	
	/* The coordinate in which the enemy is initially at on the grid */
	protected Point2D mOriginPoint;
	/* The enemy's group number */
	protected int mGroup;
	/* The enemy's initial spawn state */
	protected EnemyPosition mEntryPosition;
	/* The amount of points this enemy is worth */
	protected long mPointsValue;
	/* The initial spawn point of the enemy */
	protected Point2D mSpawnPoint;
	/* A queue of waypoints where the enemy would be instructed to move to */
	protected Queue <Point2D> mWaypointQueue;
	/* The current phase that the enemy is in */
	protected EnemyPhase mPhase;
	/* The number of waypoints in this enemy's spawning phase */
	protected int mNumSpawnWaypoints;
	/* The number of waypoints in this enemy's attack phase */
	protected int mNumAttackWaypoints;
	/* The number of waypoints in this enemy's retreat phase */
	protected int mNumRetreatWaypoints;
	
	/** Declares a new instance of a game enemy.
	 * 		@param initPosition - The position of the screen where the enemy initially spawns at before moving to its designated
	 * 							  origin point.
	 * 		@param origin - The position where the enemy resides while waiting to attack
	 * 		@param group - The enemy's group number. 
	 * 		@param controller - A reference to the game engine */
	public Enemy (EnemyPosition initPosition, Point2D origin, int group, GameEngine controller)
	{
		super(origin.getX(), origin.getY(), controller);
		mState = EntityState.kJustSpawned;
		mType = EntityType.kEnemy;
		mEntryPosition = initPosition;
		mOriginPoint = origin;
		mGroup = group;
		mWaypointQueue = new LinkedList <Point2D>();
		
		/* Based on the passed initial position, calculate the initial spawn point
		 * of the enemy based on the passed position */
		double screenCenterWidth = Launcher.mWidth / 2;
		double offset = screenCenterWidth / 2;
		
		if (initPosition == EnemyPosition.kLeft)
		{
			mSpawnPoint = new Point2D (screenCenterWidth - offset, -100.0);
			mWaypointQueue.add(new Point2D (mSpawnPoint.getX() - (offset / 4.0), Launcher.mHeight / 2.0));
		}
		else
		{
			mSpawnPoint = new Point2D (screenCenterWidth + offset, -100.0);
			mWaypointQueue.add(new Point2D (mSpawnPoint.getX() - (offset / 4.0), Launcher.mHeight / 2.0));
		}
		
		this.setX(mSpawnPoint.getX());
		this.setY(mSpawnPoint.getY());
		
		mWaypointQueue.add(mOriginPoint);
		mPhase = EnemyPhase.kSpawning;
		mNumSpawnWaypoints = mWaypointQueue.size();
	}

	/** A fairly basic update method that allows this entity to compute move instructions. */
	@Override
	public void update() 
	{
		/* Checks if there is anything in the waypoint queue.
		 * If so, start moving to the location. */
		if (!mWaypointQueue.isEmpty())
		{
			if (!mWaypointFlag)
			{
				this.moveEntity(mWaypointQueue.remove());
			}
		}
		
		/* Collision detection */
		ArrayList <Entity> currentGameEntities = mController.getEntities();
		
		for (Entity e : currentGameEntities)
		{
			if (e != this)
			{
				if (e.intersects(this.getBoundsInLocal()))
				{
					/* TODO: Kill the player and the enemy */
					if (e.getType() == EntityType.kPlayer)
					{
						
					}
				}
			}
		}
		
	}
	
	/** Overridden die method for the enemy */
	@Override
	public void die ()
	{
		super.die();
		
		/* Adds this enemy's score to the player's total score */
		mController.setPlayerScore(mController.getPlayerScore() + this.getScore());
	}
	
	/** Overridden stopWaypointAnimation method for the enemy */
	@Override
	protected void stopWaypointAnimation ()
	{
		super.stopWaypointAnimation();
		
		/* Changes the entity's current phase based on its last phase */
		switch (mPhase)
		{
			case kSpawning:
			{
				/* First check if the enemy has reached it's spawn location */
				if (mNumSpawnWaypoints > 1)
				{
					--mNumSpawnWaypoints;
				}
				else
				{
					mPhase = EnemyPhase.kIdle;
					mNumSpawnWaypoints = 0;
				}
				break;
			}
			case kAttack:
			{
				if (mNumAttackWaypoints > 1)
				{
					--mNumAttackWaypoints;
				}
				else
				{
					mPhase = EnemyPhase.kRetreat;
					mNumAttackWaypoints = 0;
				}
				break;
			}
			case kRetreat:
			{
				if (mNumRetreatWaypoints > 1)
				{
					--mNumRetreatWaypoints;
				}
				else
				{
					mPhase = EnemyPhase.kIdle;
					mNumRetreatWaypoints = 0;
				}
				break;
			}
			default:
			{
				break;
			}
		}
	}
	
	/** Adds a new point to this enemy's waypoint queue */
	public void addWaypoint (Point2D waypoint)
	{
		mWaypointQueue.add(waypoint);
	}
	
	/** @return this enemy's score value */
	public long getScore()
	{
		return mPointsValue;
	}
	
	/** @return this enemy's phase */
	public EnemyPhase getPhase ()
	{
		return mPhase;
	}
	
	/** @return the enemy's assigned attack group */
	public int getAttackGroup ()
	{
		return mGroup;
	}
	
	/** Calculates the enemy's attack vector. Must be overridden by other classes */
	public abstract void createAttackVectors();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((mEntryPosition == null) ? 0 : mEntryPosition.hashCode());
		result = prime * result + mGroup;
		result = prime * result + ((mOriginPoint == null) ? 0 : mOriginPoint.hashCode());
		result = prime * result + (int) (mPointsValue ^ (mPointsValue >>> 32));
		result = prime * result + ((mSpawnPoint == null) ? 0 : mSpawnPoint.hashCode());
		result = prime * result + ((mWaypointQueue == null) ? 0 : mWaypointQueue.hashCode());
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
		Enemy other = (Enemy) obj;
		if (mEntryPosition != other.mEntryPosition)
			return false;
		if (mGroup != other.mGroup)
			return false;
		if (mOriginPoint == null) {
			if (other.mOriginPoint != null)
				return false;
		} else if (!mOriginPoint.equals(other.mOriginPoint))
			return false;
		if (mPointsValue != other.mPointsValue)
			return false;
		if (mSpawnPoint == null) {
			if (other.mSpawnPoint != null)
				return false;
		} else if (!mSpawnPoint.equals(other.mSpawnPoint))
			return false;
		if (mWaypointQueue == null) {
			if (other.mWaypointQueue != null)
				return false;
		} else if (!mWaypointQueue.equals(other.mWaypointQueue))
			return false;
		return true;
	}

}
