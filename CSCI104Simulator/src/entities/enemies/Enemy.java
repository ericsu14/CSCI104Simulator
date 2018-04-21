/** Class that defines the base structure for an enemy in this game */

package entities.enemies;

import java.util.LinkedList;
import java.util.Queue;

import engine.GameEngine;
import entities.Entity;
import entities.EntityState;
import entities.EntityType;
import javafx.geometry.Point2D;
import view.Launcher;

public class Enemy extends Entity
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
		
	}

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
	}
	
	/** Adds a new point to this enemy's waypoint queue */
	public void addWaypoint (Point2D waypoint)
	{
		mWaypointQueue.add(waypoint);
	}
	
}
