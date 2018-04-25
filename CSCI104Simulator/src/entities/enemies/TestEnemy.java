package entities.enemies;

import engine.GameEngine;
import entities.player.Player;
import javafx.geometry.Point2D;

public class TestEnemy extends Enemy 
{
	public TestEnemy(EnemyPosition initPosition, Point2D origin, int group, GameEngine controller) 
	{	
		super (initPosition, origin, group, controller);
		this.mInitialMovementSpeed = 5.0;
		this.mMovementSpeed = mInitialMovementSpeed;
		this.mSpriteScale = 20.0;
		this.mPointsValue = 1000;
		this.setSprite(controller.mTestEnemySprite);
		setRotate (-90.0);
	}

	public void update ()
	{
		super.update();
	}

	@Override
	public void createAttackVectors() 
	{
		this.mPhase = EnemyPhase.kAttack;
		Player p = mController.getPlayer();
		
		/* Creates the attack vector based on the player's position */
		this.addCommand(CommandType.kPrepareAttack);
		this.addCommand(CommandType.kAttack);
		this.addCommand(CommandType.kPrepareAttack);
		this.addCommand(CommandType.kAttack);
		/* Creates the retreat vector */
		this.addCommand(CommandType.kRetreat);
		
	}
}
