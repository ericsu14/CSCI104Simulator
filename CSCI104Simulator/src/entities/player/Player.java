package entities.player;
import entities.Entity;
import javafx.scene.input.KeyCode;
import view.Launcher;

public class Player extends Entity
{
	
	/** Constructs a new playership
	 * 		@param x - x Position where the player would spawn
	 * 		@param y - y Position where the player would spawn
	 * 		@param controller - A pointer to the main launcher class */
	public Player (double x, double y, Launcher controller)
	{
		super (x, y, controller);
		
		setSprite (controller.mPlayerShipSprite);
		
		/* TODO: Initialize key listeners to control the player's movement */
		this.setOnKeyPressed(e -> 
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
	
	
}
