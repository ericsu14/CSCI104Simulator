package engine;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import entities.Entity;
import entities.EntityState;
import entities.EntityType;
import entities.enemies.Bohrbug;
import entities.enemies.EnemyPosition;
import entities.enemies.Heisenbug;
import entities.enemies.Mandelbug;
import entities.enemies.TestEnemy;
import entities.player.Player;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
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
	/* Stores the image file for the bohrbug, formally known as the test enemy sprite */
	public ImageView mBohrbugSprite;
	/* Stores the image file for the heisenbug */
	public ImageView mHeisenbugSprite;
	/* Stores the image file for the mandelbug */
	public ImageView mMandelBugSprite;
	/* Stores the image file for the player bullet */
	public ImageView mPlayerBulletSprite;
	
	/* The current level the player is in */
	private int mCurrentLevel;
	/* A vector of game enemies */
	private ArrayList <Entity> mGameEntities;
	/* Vector of dead game entities */
	private ArrayList <Entity> mDeadEntities;
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
	/* Flag that ensures that the player ship is only spawned once throughout the entire lifespan of this application */
	private boolean spawnedPlayerFlag = false;
	
	public GameEngine (GameView gameView)
	{	
		mGameView = gameView;
		/* Initializes the game's sprite content */
		mTestEnemySprite = new ImageView (new Image (getClass().getClassLoader().getResourceAsStream("assets/img/testEnemy.png")));
		mPlayerShipSprite = new ImageView (new Image(getClass().getClassLoader().getResourceAsStream("assets/img/playerShip.png")));
		mBohrbugSprite = new ImageView (new Image(getClass().getClassLoader().getResourceAsStream("assets/img/testEnemy.png")));
		mHeisenbugSprite = new ImageView (new Image(getClass().getClassLoader().getResourceAsStream("assets/img/heisenbug.png")));
		mMandelBugSprite = new ImageView (new Image(getClass().getClassLoader().getResourceAsStream("assets/img/mandelBug.png")));
		mPlayerBulletSprite = new ImageView (new Image(getClass().getClassLoader().getResourceAsStream("assets/img/playerBullet.png")));
		
		/* Initializes member variables */
		setCurrentLevel(1);
		setPlayerScore(0);
		mGameEntities = new ArrayList <Entity>();
		mDeadEntities = new ArrayList <Entity>();
		
		/* Sets up the game's borders */
		mMaxWidth = (int)Launcher.mWidth;
		mLeftBorder = 50;
		mRightBorder = mMaxWidth - mLeftBorder - 24;
		
		mPlayer = new Player (Launcher.mWidth / 2, Launcher.mHeight - 100.0, this);
		
		mGameLoop = new AnimationTimer ()
		{
			/* Variables used for calculating the initial spawn animation for game enemies */
			private int mSpawnIntervals = 0;
			private int mMaxSpawnInterval = 15;
			private Entity mFocusedEnemy = null;
			
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
					/* Sets the current focused enemy to the first enemy that has just been spawned in */
					if (mFocusedEnemy == null)
					{
						if (e.getState() == EntityState.kJustSpawned)
						{
							mFocusedEnemy = e;
						}
					}
					
					/* Ensures that only one (just spawned in enemy) could move to their location once every 
					 * mMaxSpawnInterval frames */
					if (e.getState() == EntityState.kJustSpawned && e == mFocusedEnemy)
					{
						if (mSpawnIntervals < mMaxSpawnInterval)
						{
							mSpawnIntervals++;
						}
						else
						{
							mFocusedEnemy.update();
							mFocusedEnemy.setState(EntityState.kActive);
							mFocusedEnemy = null;
							mSpawnIntervals = 0;
						}
					}
					/* If this entity is NOT a "just spawned entity" (or dead), continue to update it as normal */
					else if (e.getState() == EntityState.kActive)
					{
						e.update();
						
						/* TODO: Check if the entity just died */
						if (e.getState() == EntityState.kDead)
						{
							/* Checks if this entity is a player. If so, do not delete it */
							if (e.getType() == EntityType.kPlayer)
							{
								// TODO: 
							}
							
							else
							{
								mDeadEntities.add(e);
							}
						}
						
					}
				}
				
				/* Removes all dead game entities */
				if (!mDeadEntities.isEmpty())
				{
					// System.out.println("Removing " + mDeadEntities.size() + " entities from the game...");
					removeChildren(mDeadEntities);
					mDeadEntities.clear();
				}
			}
		};
	}
	
	/** Starts a new level */
	public void startLevel()
	{
		spawnEnemies();
		/* TODO: Ensure that the player is only spawned once throughout this game */
		if (!spawnedPlayerFlag)
		{
			spawnedPlayerFlag = true;
			addChild (mPlayer);
		}
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
		double ySpacing = 40.0;
		
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
			br.close();
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
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
					/* Spawn Bohrbug */
					case 'B':
						enemyContainer.add(new Bohrbug (currentPosition, new Point2D (currentX, currentY), currentGroup, this));
						break;
					/* Spawns heisenbug */
					case 'H':
						enemyContainer.add(new Heisenbug (currentPosition, new Point2D (currentX, currentY), currentGroup, this));
						break;
					/* Spawns mandelbug */
					case 'M':
						enemyContainer.add(new Mandelbug (currentPosition, new Point2D (currentX, currentY), currentGroup, this));
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
	
	/** Adds a new entity into the game.
	 * 		@param child - The new entity being added */
	public void addChild (Entity child)
	{
		mGameEntities.add(child);
		mGameView.addChild(child);
	}
	
	/** Adds an arraylist of entities into the game
	 * 		@param children - The container of children being added */
	public void addChildren (ArrayList <Entity> children)
	{
		mGameEntities.addAll(children);
		mGameView.addChildren(children);
	}
	
	public void removeChildren (ArrayList<Entity> children)
	{
		mGameEntities.removeAll(children);
		mGameView.removeChildren(children);
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
	
	/** Sets the game's view component to a new view
	 * 		@param gameView - The new gameView component */
	public void setGameView (GameView gameView)
	{
		mGameView = gameView;
	}
	
	/** @return the game's view component */
	public GameView getGameView ()
	{
		return mGameView;
	}
	
	/** @return the ArrayList of entities currently in this game */
	public ArrayList <Entity> getEntities()
	{
		return this.mGameEntities;
	}
	
}
