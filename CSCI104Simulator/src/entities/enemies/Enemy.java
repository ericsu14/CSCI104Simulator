/** Class that defines the base structure for an enemy in this game */

package entities.enemies;

import engine.GameEngine;
import entities.Entity;
import javafx.geometry.Point2D;

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
	
	/** Declares a new instance of a game enemy.
	 * 		@param initPosition - The position of the screen where the enemy initially spawns at before moving to its designated
	 * 							  origin point.
	 * 		@param origin - The position where the enemy resides while waiting to attack
	 * 		@param group - The enemy's group number. 
	 * 		@param controller - A reference to the game engine */
	public Enemy (EnemyPosition initPosition, Point2D origin, int group, GameEngine controller)
	{
		super(origin.getX(), origin.getY(), controller);
		mEntryPosition = initPosition;
		mOriginPoint = origin;
		mGroup = group;
		
		/* Enemy is initially facing downwards */
		setRotate(Math.toRadians(270.0));
	}
	
}
