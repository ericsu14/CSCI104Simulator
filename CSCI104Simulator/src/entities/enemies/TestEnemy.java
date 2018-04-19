package entities.enemies;

import entities.Entity;
import javafx.geometry.Point2D;
import view.Launcher;

public class TestEnemy extends Entity {
	
	/* Scale of the images */
	private final static double kSpriteScale = 20.0;
	private final static double kSpriteHeightOffset = 0.0;

	public TestEnemy(double x, double y, Launcher controller) 
	{
		super(x, y, controller);
		
		/* Initializes the enemy's sprite */
		setImage(mController.mTestEnemy.getImage());
		
		setFitHeight(kSpriteScale + kSpriteHeightOffset);
		setFitWidth(kSpriteScale);
		setRotate (-90.0);
		
		testMovement();
	}
	
	
	public void testMovement ()
	{
		this.moveEntity(new Point2D (266.0, 313.0));
	}
}
