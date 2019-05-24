package view;

import java.util.ArrayList;

import engine.GameEngine;
import engine.GameState;
import entities.Entity;
import entities.player.MoveDirection;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import media.SoundController;
import util.CSSColor;
import util.CSSConstants;

public class GameView 
{
	/* Gameview's scene */
	private Scene mScene;
	/* Stackpane used to tie all of the graphical components together */
	private StackPane mRoot;
	/* A reference to the created GameEngine object */
	private GameEngine mGameEngine;
	/* Contains the starfield background layer */
	private StarField mStarField;
	/* Particle layer, which is a second starfield */
	private StarField mParticleLayer;
	/* Contains the playing field layer, where all of the gameplay assets
	 * are going to be contained */
	private Pane mGameWorld;
	/* Borderpane containing the UI layer */
	private GameUI mGameUI = null;
	/* A ref. to the main launcher */
	private Launcher mLauncher;
	/* Sound controller */
	private SoundController mSoundEngine;
	/* Keeps track of the amount of left / right keys pressed */
	private int mNumDirKeys = 0;
	/* The initial amount of lives the player has */
	private int mInitLives = 3;
	
	public GameView (Launcher launcher)
	{
		mLauncher = launcher;
		setupScene();
		mSoundEngine = new SoundController();
	}
	
	/** Sets up the scene for this game view */
	public void setupScene ()
	{
		mRoot = new StackPane();
		
		/* Star field layer */
		mStarField = new StarField (true);
		mRoot.getChildren().add(mStarField);
		
		/* Playing field */
		mGameWorld = new Pane();
		mRoot.getChildren().add(mGameWorld);
		
		/* Particle layer */
		mParticleLayer = new StarField(false);
		mRoot.getChildren().add(mParticleLayer);
		
		mScene = new Scene (mRoot);
		mRoot.setStyle("-fx-background-color: black");
	}
	
	/** @return the Launcher object */
	public Launcher getLauncher()
	{
		return mLauncher;
	}
	
	/** @return the game engine object */
	public GameEngine getEngine()
	{
		return mGameEngine;
	}
	
	/** @return the GameUI object */
	public GameUI getGameUI()
	{
		return mGameUI;
	}
	
	/** @return the scene object */
	public Scene getScene()
	{
		return mScene;
	}
	
	/** @return the starfield scene */
	public StarField getStarField()
	{
		return mStarField;
	}
	
	/** @return the particle layer scene */
	public StarField getParticleLayer()
	{
		return mParticleLayer;
	}
	
	/** Plays the background animations */
	public void playAnimations()
	{
		mStarField.setOptimizationFlag(mGameEngine.getOptimizationFlag());
		mParticleLayer.setOptimizationFlag(mGameEngine.getOptimizationFlag());
		mStarField.playAnimation();
		mParticleLayer.playAnimation();
	}
	
	/** Returns the sound engine */
	public SoundController getSoundEngine ()
	{
		return mSoundEngine;
	}
	
	/** Sets up the scene to start a new game */
	public void startNewGame()
	{
		/* If the game UI hasn't been made yet, create it */
		if (mGameUI == null)
		{
			/* Gets the game engine from the launcher */
			mGameEngine = mLauncher.getGameEngine();
			/* Game UI */
			mGameUI = new GameUI(this);
			mRoot.getChildren().add(mGameUI);
			
			/* Adds in movement instructions to the gameworld scene */
			mScene.setOnKeyPressed(e -> 
			{
				MoveDirection playerMove = mGameEngine.getPlayer().getMoveDirection();
				GameState currentState = getEngine().getGameState();
				/* Checks if the current game state is either in running,
				 * levelend, or newlevel */
				if (currentState == GameState.kGameRunning 
						|| currentState == GameState.kLevelEnd
						|| currentState == GameState.kNewLevel)
				{
					if (e.getCode() == KeyCode.LEFT && playerMove != MoveDirection.kLeft)
					{
						mGameEngine.getPlayer().setMoveDirection(MoveDirection.kLeft);
						++mNumDirKeys;
					}
					if (e.getCode() == KeyCode.RIGHT && playerMove != MoveDirection.kRight)
					{
						mGameEngine.getPlayer().setMoveDirection(MoveDirection.kRight);
						++mNumDirKeys;
					}
					if (e.getCode() == KeyCode.SPACE)
					{
						mGameEngine.getPlayer().setFiringFlag(true);
					}
					if (e.getCode() == KeyCode.ESCAPE)
					{
						mGameEngine.pauseGame();
					}
				}
				else
				{
					mNumDirKeys = 0;
					mGameEngine.getPlayer().setMoveDirection(MoveDirection.kNone);
				}
			});
			
			/* Resets movement once key has been released */
			mScene.setOnKeyReleased(e -> 
			{
				/* Checks if the current game state is either in running,
				 * levelend, or newlevel. */
				GameState currentState = getEngine().getGameState();
				if (currentState == GameState.kGameRunning 
						|| currentState == GameState.kLevelEnd
						|| currentState == GameState.kNewLevel)
				{
					if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.RIGHT)
					{
						--mNumDirKeys;
						if (mNumDirKeys < 0)
						{
							mNumDirKeys = 0;
						}
					}
					
					if (e.getCode() == KeyCode.SPACE)
					{
						mGameEngine.getPlayer().setFiringFlag(false);
					}
					
					if (mNumDirKeys <= 0)
					{
						mGameEngine.getPlayer().setMoveDirection(MoveDirection.kNone);
					}
				}
				else
				{
					mNumDirKeys = 0;
					mGameEngine.getPlayer().setMoveDirection(MoveDirection.kNone);
				}
			});
		}
		
		playAnimations();
		mGameEngine.setCurrentLives(mInitLives);
		// Extra life for hard mode players
		if (mGameEngine.isHardMode())
		{
			mGameEngine.setCurrentLives(mInitLives + 1); 
		}
		mGameEngine.setCurrentLevel(1);
		mGameEngine.setCurrentScore(0);
		mGameEngine.startGame();
	}
	
	/** Cleans up all assets in this view component */
	public void cleanup ()
	{
		mStarField.stopAnimation();
		try
		{
			mGameWorld.getChildren().clear();
		}
		catch (IllegalArgumentException e)
		{
			// A really cheap hack, but it works
			mGameWorld.getChildren().clear();
		}
	}
	
	/** Briefly displays a string of text at a specified coordinate of the screen.
	 * 		@param text - The text to be displayed
	 * 		@param color - The CSS Color of the text
	 * 		@param position - The point where the text is displayed */
	public void showTextOnPoint (String text, CSSColor color, Point2D position)
	{
		AnimationTimer displayText = new AnimationTimer ()
		{
			// Flag used to determine if the text is still in the delay state
			private boolean mIsDelay;
			// The amount of delay (in frames) it takes before the text is displayed on the screen
			private final int mDelayTime = 30;
			// The max amount of frames the text is displayed on screen before despawning
			private final int mMaxTime = 90;
			// Stores the current time (in frames) elapsed
			private int mCurrentTime;
			// Stores the label object to be displayed on screen
			private Label mTextObject;
			
			// Init. member variables and displays the text
			{
				mCurrentTime = mDelayTime;
				mTextObject = new Label (text);
				mTextObject.setStyle(CSSConstants.GAME_FONT_UNCOLORED + color.getColor());
				mTextObject.relocate(position.getX(), position.getY());
				mIsDelay = true;
			}
			
			@Override
			public void handle(long now) 
			{
				if (mCurrentTime <= 0)
				{	
					// If the text is still waiting to be spawned, spawn it
					if (mIsDelay)
					{
						mIsDelay = false;
						mGameWorld.getChildren().addAll(mTextObject);
						mCurrentTime = mMaxTime;
					}
					// Otherwise, remove it from the world
					else
					{
						mGameWorld.getChildren().remove(mTextObject);
						this.stop();
					}
				}
				else
				{
					--mCurrentTime;
				}
			}
		};
		
		displayText.start();
	}
	
	/** Sets the GameView's game engine */
	public void setGameEngine (GameEngine engine)
	{
		mGameEngine = engine;
	}
	
	/** Adds a vector of child nodes into the gameworld layer */
	public void addChildren (ArrayList<Entity> children)
	{
		mGameWorld.getChildren().addAll(FXCollections.observableArrayList(children));
	}
	
	/** Adds an individual child node into the gameworld layer */
	public void addChild (Entity child)
	{
		mGameWorld.getChildren().add(child);
	}
	
	/** Removes an individual child node into the gameworld layer */
	public void removeChild (Entity child)
	{
		mGameWorld.getChildren().remove(child);
	}
	
	/** Given a vector of child nodes remove all of them from the gameworld layer */
	public void removeChildren (ArrayList<Entity> deadEntities)
	{
		mGameWorld.getChildren().removeAll(FXCollections.observableArrayList(deadEntities));
	}
	
	/** Refreshes the UI to display present time stats */
	public void refreshUI ()
	{
		this.mGameUI.update();
	}
}
