package engine;
import java.util.ArrayList;

import entities.enemies.Enemy;
import entities.player.Player;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import view.GameView;
import view.Launcher;
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
	private ArrayList <Node> mGameEntities;
	/* The player's current score */
	private long mPlayerScore;
	/* The game's player character */
	private Player mPlayer;
	/* The amount of lives the player has remaining */
	private int mCurrentLives;
	/* A reference to the game's gameview */
	private GameView mGameView;
	/* Defines the game's left border */
	private int mLeftBorder;
	/* Defines the game's right border */
	private int mRightBorder;
	/* Defines the maximum height an entity could go before being elligible for despawning */
	private int mMaxHeight;
	/* Defines the maximum width of the playing field */
	private int mMaxWidth;
	
	public GameEngine ()
	{	
		/* Initializes the game's sprite content */
		mTestEnemySprite = new ImageView (new Image (getClass().getClassLoader().getResourceAsStream("assets/img/testEnemy.png")));
		mPlayerShipSprite = new ImageView (new Image(getClass().getClassLoader().getResourceAsStream("assets/img/playerShip.png")));
		
		/* Initializes member variables */
		setCurrentLevel(1);
		setPlayerScore(0);
		mGameEntities = new ArrayList <Node>();
		
		/* Sets up the game's borders */
		mMaxWidth = 1000;
		mLeftBorder = 100;
		mRightBorder = mLeftBorder + mMaxWidth;
	}
	
	public void update()
	{
		// TODO: 
	}
	
	public void startLevel ()
	{
		mPlayer = new Player (Launcher.mWidth / 2, Launcher.mHeight - 100.0, this);
		addChild(mPlayer);
		// TODO: Create code that reads from a data file to spawn new enemies in the game
	}
	
	public void addChild (Node child)
	{
		mGameEntities.add(child);
		mGameView.addChild(child);
	}
	
	/** Returns the center region of the playing field */
	public int getCenterRegion()
	{
		return (mMaxWidth / 2) + mLeftBorder;
	}

	/** @return the game's max. border */
	public int getMaxHeight() 
	{
		return mMaxHeight;
	}

	/** @return the game's right border */
	public int getRightBorder() 
	{
		return mRightBorder;
	}
	
	/** @return the game's left border */
	public int getLeftBorder()
	{
		return mLeftBorder;
	}

	/**
	 * @return the mCurrentLives
	 */
	public int getCurrentLives() 
	{
		return mCurrentLives;
	}

	/**
	 * @param mCurrentLives the mCurrentLives to set
	 */
	public void setCurrentLives(int mCurrentLives) 
	{
		this.mCurrentLives = mCurrentLives;
	}

	/**
	 * @return the mPlayer
	 */
	public Player getPlayer() 
	{
		return mPlayer;
	}

	/**
	 * @param mPlayer the mPlayer to set
	 */
	public void setPlayer(Player mPlayer) 
	{
		this.mPlayer = mPlayer;
	}

	/**
	 * @return the mCurrentLevel
	 */
	public int getCurrentLevel() 
	{
		return mCurrentLevel;
	}

	/**
	 * @param mCurrentLevel the mCurrentLevel to set
	 */
	public void setCurrentLevel(int mCurrentLevel) 
	{
		this.mCurrentLevel = mCurrentLevel;
	}

	/**
	 * @return the mPlayerScore
	 */
	public long getPlayerScore() 
	{
		return mPlayerScore;
	}

	/**
	 * @param mPlayerScore the mPlayerScore to set
	 */
	public void setPlayerScore(long mPlayerScore) 
	{
		this.mPlayerScore = mPlayerScore;
	}
	
	public void setGameView (GameView gameView)
	{
		mGameView = gameView;
	}
	
	public GameView getGameView ()
	{
		return mGameView;
	}
	
}
