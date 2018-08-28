package entities.projectiles.boss.cote;

import engine.GameEngine;
import entities.Entity;
import entities.projectiles.boss.BossProjectile;
import entities.sprites.Sprite;

public class Speghetti extends BossProjectile {

	public Speghetti(Entity owner, GameEngine controller) 
	{
		super(owner, controller);
		this.mSpriteScale = 100.0;
		this.mMovementSpeed = 4.4;
		this.setSprite(Sprite.kSpeghetti);
	}
	
}
