package engine;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import entities.Entity;
import entities.enemies.Enemy;
import entities.enemies.EnemyPosition;
import entities.enemies.TestEnemy;
import entities.player.Player;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
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
	private ArrayList <Entity> mGameEntities;
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
	/* Animation timer used for the game loop */
	private AnimationTimer mGameLoop;
	
	public GameEngine (GameView gameView)
	{	
		mGameView = gameView;
		/* Initializes the game's sprite content */
		mTestEnemySprite = new ImageView (new Image (getClass().getClassLoader().getResourceAsStream("assets/img/testEnemy.png")));
		mPlayerShipSprite = new ImageView (new Image(getClass().getClassLoader().getResourceAsStream("assets/img/playerShip.png")));
		
		/* Initializes member variables */
		setCurrentLevel(1);
		setPlayerScore(0);
		mGameEntities = new ArrayList <Entity>();
		
		/* Sets up the game's borders */
		mMaxWidth = 1000;
		mLeftBorder = 50;
		mRightBorder = mLeftBorder + mMaxWidth;
		
		mPlayer = new Player (Launcher.mWidth / 2, Launcher.mHeight - 100.0, this);
		
		mGameLoop = new AnimationTimer ()
		{
			@Override
			public void handle(long now) 
			{
				update (now);
			}
			
			public void update (long now)
			{
				/* Updates each individual entity */
				for (Entity e : mGameEntities)
				{
					e.update();
				}
			}
		};
	}
	
	/** Starts a new level */
	public void startLevel()
	{
		spawnEnemies();
		/* TODO: Ensure that the player is only spawned once throughout this game */
		addChild (mPlayer);
		
		/* Starts the game loop */
		mGameLoop.start();
	}
	
	/** Reads a level data file from a text file and spawns in
	 *  the appropriate enemies */
	public void spawnEnemies ()
	{
		/* Defines the current iterators for enemy placement */
		double currentX, currentY;
		double xSpacing = 50.0;
		double ySpacing = 30.0;
		
		/* Curent group of the current entity */
		int currentGroup = 0;
		int currentGroupCount = 0;
		int currentGroupLimit = 1;
		
		/* Width and height of the container */
		int armyWidth = 0, armyHeight = 0;
		/* Vector of strings used to copy the contents of the text file */
		ArrayList <String> contents = new ArrayList <String>();
		/* A vector of enemies would be spawned in later */
		ArrayList <Entity> enemyContainer = new ArrayList <Entity> ();
		
		/* Reads from a text file and uses that data to spawn enemies into the game */
		String fileName = "src/assets/data/testEnemyLayout.txt";
		String currentLine = null;
		try
		{
			FileReader reader = new FileReader (fileName);
			BufferedReader br = new BufferedReader (reader);
			
			/* First calculate the dimensions of the enemy army */
			while ((currentLine = br.readLine()) != null)
			{
				currentLine = currentLine.trim();
				
				for (int i = 0; i < currentLine.length(); ++i)
				{
					if (armyWidth < (i + 1))
					{
						armyWidth = (i + 1);
					}
				}
				contents.add(currentLine);
				armyHeight += 1;
			}
		}
		
        catch(FileNotFoundException ex) 
		{
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) 
		{
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
        }
		
		System.out.println("Width: " + armyWidth + " | Height: " + armyHeight);
		
		/* Now, construct the enemies */
		currentX = this.mLeftBorder + xSpacing;
		currentY = this.mLeftBorder + ySpacing;
		
		char currentChar;
		EnemyPosition currentPosition;
		for (String line : contents)
		{
			for (int i = 0; i < line.length(); ++i)
			{
				currentChar = line.charAt(i);
				
				/* Calculates the initial spawn point based on currentX */
				if (currentX <= (Launcher.mWidth / 2.0))
				{
					currentPosition = EnemyPosition.kLeft;
				}
				else
				{
					currentPosition = EnemyPosition.kRight;
				}
				switch (currentChar)
				{
					/* Spawn test enemy */
					case 'T':
						enemyContainer.add(new TestEnemy (currentPosition, new Point2D (currentX, currentY), currentGroup, this));
						break;
					default:
						break;
				}
				++currentGroupCount;
				
				if (currentGroupCount >= currentGroupLimit)
				{
					++currentGroup;
					currentGroupCount = 0;
				}
				
				currentX += xSpacing;
				++currentGroupLimit;
			}
			currentX = this.mLeftBorder + xSpacing;
			currentY += ySpacing;
		}
		
		addChildren (enemyContainer);
	}
	
	public void addChild (Entity child)
	{
		mGameEntities.add(child);
		mGameView.addChild(child);
	}
	
	public void addChildren (ArrayList <Entity> children)
	{
		mGameEntities.addAll(children);
		mGameView.addChildren(children);
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
