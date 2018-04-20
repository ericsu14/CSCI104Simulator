/** Class that defines the base structure for an enemy in this game */

package entities.enemies;

import entities.Entity;
import javafx.geometry.Point2D;
import view.Launcher;

public class Enemy extends Entity
{
	/** Determines the position where the enemy initially spawns before
	 *  traveling to its designated position of the game */
	public static enum EntryPosition
	{
		left, right
	};
	
	/* The coordinate in which the enemy is initially at on the grid */
	protected Point2D mOriginPoint;
	/* The enemy's group number */
	protected int mGroup;
	/* The enemy's initial spawn state */
	protected EntryPosition mEntryPosition;
	
	/** Declares a new instance of a game enemy.
	 * 		@param initPosition - The position of the screen where the enemy initially spawns at before moving to its designated
	 * 							  origin point.
	 * 		@param origin - The position where the enemy resides while waiting to attack
	 * 		@param group - The enemy's group number. 
	 * 		@param controller - A reference to the main Launcher class*/
	public Enemy (EntryPosition initPosition, Point2D origin, int group, Launcher controller)
	{
		super(origin.getX(), origin.getY(), controller);
		mEntryPosition = initPosition;
		mOriginPoint = origin;
		mGroup = group;
		
		/* Enemy is initially facing downwards */
		setRotate(Math.toRadians(270.0));
	}
	
}
