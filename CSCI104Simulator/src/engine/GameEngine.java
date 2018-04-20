package engine;
import java.util.ArrayList;

import entities.enemies.Enemy;
import entities.player.Player;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import view.GameView;
public class GameEngine 
{
	/* SPRITE ASSETS*/
	/* Stores the image file for the dummy test enemy */
	public ImageView mTestEnemySprite;
	/* Stores the image file for the playership */
	public ImageView mPlayerShipSprite;
	
	/* The current level the player is in */
	private int mCurrentLevel;
	/* A vector of game enemies */
	private ArrayList <Enemy> mGameEnemies;
	/* The player's current score */
	private long mPlayerScore;
	/* The game's player character */
	private Player mPlayer;
	/* The amount of lives the player has remaining */
	private int mCurrentLives;
	/* A reference to the game's gameview */
	private GameView mGameView;
	
	
	public GameEngine (GameView gameView)
	{
		/* Initializes the game's sprite content */
		mTestEnemySprite = new ImageView (new Image (getClass().getClassLoader().getResourceAsStream("assets/img/testEnemy.png")));
		mPlayerShipSprite = new ImageView (new Image(getClass().getClassLoader().getResourceAsStream("assets/img/playerShip.png")));
		
		
		/* Initializes member variables */
		mCurrentLevel = 1;
		mPlayerScore = 0;
		mPlayer = new Player(0, 0, this);
		mGameEnemies = new ArrayList <Enemy> ();
	}
	
	
}
