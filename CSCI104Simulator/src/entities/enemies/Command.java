/** Defines the commands that  */
package entities.enemies;

import javafx.geometry.Point2D;
import java.util.Random;

import entities.player.Player;

public class Command 
{
	/* The owner of this command */
	private Enemy mOwner;
	/* The type of command the enemy is instructed to do */
	private CommandType mType;
	/* If it is the case where this command is a move instruction, store the coordinates
	 * where the enemy would move to */
	private Point2D mWaypoint;
	/* Defines the offset threshold. */
	private int mOffsetThreshold = 100;
	/* A random generator that randomly computes the offset to make things more intresting */
	private static Random mRand = new Random();
	/* True if an attack move command is used */
	private boolean mAttackMoveFlag = false;
	
	/** Constructs a new command that is either an attack or retreat command.
	 *  In those situations, declaring a waypoint is not necessary.
	 *  
	 *   The internal waypoint defaults to the spawn point of the owner. */
	public Command (CommandType type, Enemy owner)
	{
		mType = type;
		mOwner = owner;
		mWaypoint = mOwner.getSpawnPoint();
	}
	
	public Command (CommandType type, Enemy owner, Point2D waypoint)
	{
		mType = type;
		mOwner = owner;
		mWaypoint = waypoint;
	}
	
	/** Executes the command by automatically calculating the waypoint based on
	 *  the type of command passed in. */
	public Point2D execute ()
	{
		switch (mType)
		{
			case kAttack:
			{
				/*  Reloads the ship's cannons if an attack move has just been
				 *  recently used */
				if (mAttackMoveFlag)
				{
					mOwner.reload();
					mAttackMoveFlag = false;
				}
				
				/* For this, the enemy should travel directly towards a point near the
				 * player's current vacinity at an offset */
				double xOffset, yOffset;
				
				if (mOwner.getEntryPosition() == EnemyPosition.kLeft)
				{
					xOffset = - (mRand.nextInt(mOffsetThreshold) + 30.0);
				}
				else
				{
					xOffset = (mRand.nextInt(mOffsetThreshold) + 30.0);
				}
				
				yOffset = -100.0;
				
				mOwner.setOffset(10);
				Player p = mOwner.getController().getPlayer();
				mWaypoint = new Point2D (p.getX() + xOffset, p.getY() + yOffset);
				break;
			}
			case kPrepareAttack:
			{
				/* For this, the enemy should travel in a parabolic arc away
				 * from the enemy's current direction of facing before attacking. */
				double xOffset, yOffset;
				
				/*  Reloads the ship's cannons if an attack move has just been
				 *  recently used */
				if (mAttackMoveFlag)
				{
					mOwner.reload();
					mAttackMoveFlag = false;
				}
				
				if (mOwner.getEntryPosition() == EnemyPosition.kLeft)
				{
					xOffset = -(mRand.nextInt(mOffsetThreshold) + 30.0);
					mOwner.setRotate(315);
				}
				else
				{
					xOffset = (mRand.nextInt(mOffsetThreshold) + 30.0);
					mOwner.setRotate(-135);
				}
				yOffset = -50.0;
				
				mOwner.setOffset((int)(mOwner.getSpriteScale()));
				mWaypoint = new Point2D (mOwner.getX() + xOffset, mOwner.getY() + yOffset);
				break;
			}
			case kRetreat:
			{
				mOwner.setOffset((int)mOwner.getSpriteScale() / 10);
				mWaypoint = mOwner.getSpawnPoint();
				break;
			}
			
			case kAttackMove:
			{
				mAttackMoveFlag = true;
				break;
			}
			
			default:
				break;
		}
		
		return mWaypoint;
	}
}
