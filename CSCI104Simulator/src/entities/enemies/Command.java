/** Defines the commands that are used for the enemies to either move to a location,
 *  prepare an attack, attack, or retreat in this game. */
package entities.enemies;

import javafx.geometry.Point2D;
import media.SoundType;
import view.Launcher;

import java.util.Random;

import entities.EntityType;
import entities.enemies.boss.Boss;
import entities.enemies.boss.BossAmmoType;
import entities.player.Player;

public class Command 
{
	/* The owner of this command */
	protected Enemy mOwner;
	/* The type of command the enemy is instructed to do */
	protected CommandType mType;
	/* If it is the case where this command is a move instruction, store the coordinates
	 * where the enemy would move to */
	protected Point2D mWaypoint;
	/* Defines the offset threshold. */
	protected int mOffsetThreshold = 100;
	/* A random generator that randomly computes the offset to make things more intresting */
	protected static Random mRand = new Random();
	
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
				if (mOwner.mFalseRetreatFlag)
				{
					mOwner.reload();
					mOwner.mFalseRetreatFlag = false;
				}
				
				/* For this, the enemy should travel directly towards a point near the
				 * player's current vicinity at an offset */
				double xOffset, yOffset;
				
				if (mOwner.getEntryPosition() == EnemyPosition.kLeft)
				{
					xOffset = - (mRand.nextInt(mOffsetThreshold) + 30.0);
				}
				else
				{
					xOffset = (mRand.nextInt(mOffsetThreshold) + 30.0);
				}
				
				// Calculates the random y offset where this enemy should move to. The enemy could either move either over
				// or under the player's current position
				if (mOwner.getType() != EntityType.kBoss)
				{
					yOffset = 50.0 * mRand.nextDouble() * ((Math.pow(-1, mRand.nextInt(2) - 1)));
				}
				// Unless the owner is a boss. Then hard-set the y offset to 100 for balance reasons
				else
				{
					yOffset = -100.0;
				}
				
				Player p = mOwner.getController().getPlayer();
				mOwner.setOffset((int)p.getSpriteScale());
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
				if (mOwner.mFalseRetreatFlag)
				{
					mOwner.reload();
					mOwner.mFalseRetreatFlag = false;
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
				
				if (mOwner.getType() == EntityType.kBoss)
				{
					yOffset *= 2;
				}
				
				mOwner.setOffset((int)(mOwner.getSpriteScale()));
				mWaypoint = new Point2D (mOwner.getX() + xOffset, mOwner.getY() + yOffset);
				mOwner.getController().playSound(SoundType.kEnemyPrepare);
				break;
			}
			case kRetreat:
			{
				mOwner.setOffset((int)mOwner.getSpriteScale() / 10);
				mWaypoint = mOwner.getSpawnPoint();
				break;
			}
			
			case kFalseRetreat:
			{
				mOwner.mFalseRetreatFlag = true;
				break;
			}
			
			case kBossPrepareRangedAttack:
			{
				/* First determine which side the player is currently in
				 * so this boss character could determine which side to attack */
				
				/* If player is on the left side, attack on the right side */
				if (mOwner.getController().getPlayer().getX() <= (Launcher.mWidth / 2.0))
				{
					mWaypoint = new Point2D (mOwner.getController().getRightBorder(), 100.0);
				}
				/* Otherwise, attack on the left */
				else
				{
					mWaypoint = new Point2D (mOwner.getController().getLeftBorder(), 100.0);
				}
				break;
			}
			
			case kBossRangedAttack:
			{
				/* Only execute this command if the owner is a boss character */
				if (mOwner.getType() == EntityType.kBoss)
				{
					Boss boss = (Boss) mOwner;
					boss.setAmmoType(BossAmmoType.kRanged);
					/* TODO: Fire the ranged attack */
					boss.fire();
				}
				break;
			}
			
			default:
				break;
		}
		
		return mWaypoint;
	}
}
