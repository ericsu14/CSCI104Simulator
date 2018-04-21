package entities.player;
import engine.GameEngine;
import entities.Entity;
import entities.EntityType;

public class Player extends Entity
{
	/** Constructs a new playership
	 * 		@param x - x Position where the player would spawn
	 * 		@param y - y Position where the player would spawn
	 * 		@param controller - A pointer to the main launcher class */
	public Player (double x, double y, GameEngine controller)
	{
		super (x, y, controller);
		mMovementSpeed = 10.0;
		this.mSpriteScale = 30.0;
		mType = EntityType.kPlayer;
		setSprite (controller.mPlayerShipSprite);
	}

	@Override
	public void update() 
	{
		/* TODO: Check if the playership has collided with any other entity in the game */
		
	}
	
}
