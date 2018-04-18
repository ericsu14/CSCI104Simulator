/** Class that defines the base structure for an enemy in this game */

package entities.enemies;

import javafx.geometry.Point2D;

public class Enemy 
{
	/** Determines the position where the enemy initially spawns before
	 *  traveling to its designated position of the game */
	public static enum EntryPosition
	{
		left, right
	};
	
	/* Enemy's position in the game */
	private Point2D mDirection;
	/* Enemy's movement speed */
	private double mMovementSpeed;
	/* The coordinate in which the enemy is initially at on the grid */
	private Point2D mOriginPoint;
	/* The enemy's group number */
	private int mGroup;
	/* The enemy's initial spawn state */
	private EntryPosition mEntryPosition;
	
	/** Declares a new instance of a game enemy.
	 * 		@param initPosition - The position of the screen where the enemy initially spawns at before moving to its designated
	 * 							  origin point.
	 * 		@param origin - The position where the enemy resides while waiting to attack
	 * 		@param group - The enemy's group number. */
	public Enemy (EntryPosition initPosition, Point2D origin, int group)
	{
		mEntryPosition = initPosition;
		mOriginPoint = origin;
		mGroup = group;
	}
	
	
}
