package entities.player;
import engine.GameEngine;
import entities.Entity;
import entities.EntityType;
import javafx.geometry.Point2D;

public class Player extends Entity
{
	/* The player's direction of moving */
	private MoveDirection mCurrentDirection;
	/* The player's own rotation angle */
	private double mPlayerRotation;
	
	/** Constructs a new playership
	 * 		@param x - x Position where the player would spawn
	 * 		@param y - y Position where the player would spawn
	 * 		@param controller - A pointer to the main launcher class */
	public Player (double x, double y, GameEngine controller)
	{
		super (x, y, controller);
		mMovementSpeed = 6.0;
		mSpriteScale = 30.0;
		mPlayerRotation = 90.0;
		mType = EntityType.kPlayer;
		mCurrentDirection = MoveDirection.kNone;
		mPlayerRotation = this.getRotate();
		setSprite (controller.mPlayerShipSprite);
	}

	@Override
	public void update() 
	{
		/* Moves this character's position if the playe is set to move either left or right */
		if (mCurrentDirection != MoveDirection.kNone)
		{
			double moveX;
			/* Sets the player's current rotation based on the updated direction */
			if (mCurrentDirection == MoveDirection.kRight)
			{
				mPlayerRotation = 0.0;
			}
			else
			{
				mPlayerRotation = 180.0;
			}
			
			/* Moves the player in that current direction */
			Point2D velocity = getForward();
			velocity = velocity.multiply(mMovementSpeed);
			moveX = getX() + velocity.getX();
			
			/* Checks if the player is attempting to move out of the game's set boundries */
			if (inRange ((int)moveX, mController.getLeftBorder(), mController.getRightBorder()))
			{
				this.setX(getX() + velocity.getX());
			}
		}
		
		else
		{
			mPlayerRotation = 90.0;
		}
		
	}
	
	/** Because the player's sprite never rotates, the player would have to use a slightly altered forward vector calculation algorithm
	 *  that uses the player's "hidden rotation angle" */
	@Override
	public Point2D getForward ()
	{
		double forwardX = (Math.cos(Math.toRadians(mPlayerRotation)));
		double forwardY = -(Math.sin(Math.toRadians(mPlayerRotation)));
		Point2D forwardVector = new Point2D (forwardX, forwardY);
		forwardVector.normalize();
		return forwardVector;
	}
	
	/** Sets the player's move direction */
	public void setMoveDirection (MoveDirection direction)
	{
		mCurrentDirection = direction;
	}
	
	/** @return the player's move direction */
	public MoveDirection getMoveDirection()
	{
		return mCurrentDirection;
	}
}
