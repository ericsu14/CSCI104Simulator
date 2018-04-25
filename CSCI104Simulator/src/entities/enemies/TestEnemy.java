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
		this.addWaypoint(new Point2D (p.getX() - 10.0, p.getY() - 200.0));
		this.addWaypoint(new Point2D (p.getX() - 150.0, p.getY() - 200.0));
		/* Creates the retreat vector */
		this.addWaypoint(this.mOriginPoint);
		
		this.mNumAttackWaypoints = 2;
		this.mNumRetreatWaypoints = 1;
		this.setRotate(90.0);
	}
}
