/** Class that defines the base structure for an enemy in this game */

package entities.enemies;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import engine.GameEngine;
import engine.GameState;
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
	protected Queue <Command> mCommandQueue;
	/* The current phase that the enemy is in */
	protected EnemyPhase mPhase;
	/* The number of waypoints in this enemy's spawning phase */
	protected int mNumSpawnWaypoints;
	/* The number of waypoints in this enemy's attack phase */
	protected int mNumAttackWaypoints;
	/* The number of waypoints in this enemy's retreat phase */
	protected int mNumRetreatWaypoints;
	/* The total ammo pool this enemy has */
	protected int mCurrentAmmo;
	/* The max ammo pool this enemy has */
	protected int mMaxAmmoPool;
	/* Controls the enemy's rate of fire in shots per frame */
	protected int mShotsPerFrame = 10;
	/* Cooldown timer for the enemy's cannons */
	protected int mCooldown;
	/* Determines if this enemy has just used an attack move */
	public boolean mAttackMoveFlag = false;
	
	
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
		mCooldown = mShotsPerFrame;
		mCommandQueue = new LinkedList <Command>();
		
		/* Based on the passed initial position, calculate the initial spawn point
		 * of the enemy based on the passed position */
		double screenCenterWidth = Launcher.mWidth / 2;
		double offset = screenCenterWidth / 2;
		
		if (initPosition == EnemyPosition.kLeft)
		{
			mSpawnPoint = new Point2D (screenCenterWidth - offset, -100.0);
			mCommandQueue.add(new Command (CommandType.kMove, this, new Point2D (mSpawnPoint.getX() - (offset / 4.0), Launcher.mHeight / 2.0)));
		}
		else
		{
			mSpawnPoint = new Point2D (screenCenterWidth + offset, -100.0);
			mCommandQueue.add(new Command (CommandType.kMove, this, new Point2D (mSpawnPoint.getX() - (offset / 4.0), Launcher.mHeight / 2.0)));
		}
		
		this.setX(mSpawnPoint.getX());
		this.setY(mSpawnPoint.getY());
		
		mCommandQueue.add(new Command (CommandType.kMove, this, mOriginPoint));
		mPhase = EnemyPhase.kSpawning;
		mNumSpawnWaypoints = mCommandQueue.size();
	}

	/** A fairly basic update method that allows this entity to compute move instructions. */
	@Override
	public void update() 
	{
		/* Checks if there is anything in the waypoint queue.
		 * If so, start moving to the location. */
		if (!mCommandQueue.isEmpty())
		{
			if (!mWaypointFlag)
			{
				this.moveEntity(mCommandQueue.remove().execute());
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
					if (e.getType() == EntityType.kPlayer && e.getState() == EntityState.kActive)
					{
						kill (e, true);
					}
				}
			}
		}
		
		/* Starts spawning projectiles when this enemy is attacking
		 * and the game state is still running */
		if (mPhase == EnemyPhase.kAttack && mController.getGameState() == GameState.kGameRunning)
		{
			if (mCooldown <= 0)
			{
				fire();
				mCooldown = mShotsPerFrame;
			}
			--mCooldown;
		}
		/* If this enemy is in the attacking stage, but the game state changes
		 * from running, stop the current attacking routine. */
		else if (mPhase == EnemyPhase.kAttack && mController.getGameState() != GameState.kGameRunning)
		{
			stopWaypointAnimation();
		}
	}
	
	/** Overridden die method for the enemy */
	@Override
	public void die ()
	{
		super.die();
		
		/* Adds this enemy's score to the player's total score */
		mController.setPlayerScore(mController.getPlayerScore() + this.getScore());
		mController.getGameView().getStarField().spawnExplosion((int)getCenterX(), (int)getCenterY());
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
					mCurrentAmmo = mMaxAmmoPool;
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
					mCurrentAmmo = mMaxAmmoPool;
				}
				break;
			}
			default:
			{
				break;
			}
		}
		
		/* Resets enemy orientation if its new state is Idle */
		if (mPhase == EnemyPhase.kIdle)
		{
			setRotate (mInitialOrientation);
		}
	}
	
	/** Adds a new move command to the enemy's instruction queue */
	public void addWaypoint (Point2D waypoint)
	{
		mCommandQueue.add(new Command (CommandType.kMove, this, waypoint));
	}
	
	/** Adds in a new command with a specified type (other than kMove) */
	public void addCommand (CommandType type)
	{
		mCommandQueue.add(new Command (type, this));
		
		if (type == CommandType.kAttack || type == CommandType.kAttackMove || type == CommandType.kPrepareAttack)
		{
			++mNumAttackWaypoints;
			mCooldown = this.mShotsPerFrame;
		}
		
		if (type == CommandType.kRetreat)
		{
			++mNumRetreatWaypoints;
		}
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
	
	/** @return the enemy's spawn point */
	public Point2D getSpawnPoint()
	{
		return mOriginPoint;
	}
	
	/** @return the enemy's position of the game world */
	public EnemyPosition getEntryPosition ()
	{
		return mEntryPosition;
	}
	
	/** @return a pointer to the main engine */
	public GameEngine getController ()
	{
		return mController;
	}
	
	/** Sets this enemy's projectile cooldown timer to a new value */
	public void setCurrentCooldown (int cooldown)
	{
		mCooldown = cooldown;
	}
	
	/** Reloads the enemy's cannons */
	public void reload ()
	{
		this.mCurrentAmmo = this.mMaxAmmoPool;
	}
	
	/** Spawns an enemy projectile */
	public abstract void fire();
	
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
		result = prime * result + ((mCommandQueue == null) ? 0 : mCommandQueue.hashCode());
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
		if (mCommandQueue == null) {
			if (other.mCommandQueue != null)
				return false;
		} else if (!mCommandQueue.equals(other.mCommandQueue))
			return false;
		return true;
	}

}
