package entities.player;
import engine.GameEngine;
import entities.Entity;
import javafx.scene.input.KeyCode;

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
		
		setSprite (controller.mPlayerShipSprite);
		
		/* TODO: Initialize key listeners to control the player's movement */
		mController.getGameView().getScene().setOnKeyPressed(e -> 
		{
			/* Moves the playership left */
			if (e.getCode() == KeyCode.LEFT)
			{
				this.setX(getX() - mMovementSpeed);
				
				/* TODO: Check for game boundries */
			}
			
			/* Moves the playership right */
			if (e.getCode() == KeyCode.RIGHT)
			{
				this.setX(getX() + mMovementSpeed);
				
				/* TODO: Check for game boundries */
			}
			
			if (e.getCode() == KeyCode.SPACE)
			{
				/* TODO: Fire the ship's projectiles */
			}
			
		});
	}

	@Override
	public void update() 
	{
		/* TODO: Improve wonky movement controls here */
		/* TODO: Check if the playership has collided with any other entity in the game */
		
	}
	
}
