package entities.projectiles.enemies;

import engine.GameEngine;
import entities.Entity;
import entities.sprites.Sprite;
import javafx.geometry.Point2D;
import media.SoundType;

public class UnguidedProjectile extends EnemyProjectile
{

	public UnguidedProjectile(Entity owner, GameEngine controller) 
	{
		super(owner, controller);
		this.mSpriteScale = 15.0;
		this.mMovementSpeed = 3.5 + ((double)controller.getCurrentLevel() / 10.0);
		/* Restricts the movement speed of this projectile
		 * so the game does not get impossible */
		if (this.mMovementSpeed > 5.2 || mController.isHardMode())
		{
			this.mMovementSpeed = 5.2;
		}
		
		this.setSprite(Sprite.kEnemyLaser);
		
		this.moveEntity(new Point2D (this.getX(), this.getY() + 1000));
		mController.getGameView().getSoundEngine().playSound(SoundType.kUnguidedProjectile);
	}

}
