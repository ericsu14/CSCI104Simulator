package view;

import java.util.ArrayList;

import engine.GameEngine;
import engine.GameState;
import entities.Entity;
import entities.player.MoveDirection;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

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
	/* Contains the playing field layer, where all of the gameplay assets
	 * are going to be contained */
	private Pane mGameWorld;
	/* Borderpane containing the UI layer */
	private GameUI mGameUI = null;
	/* A ref. to the main launcher */
	private Launcher mLauncher;
	/* Keeps track of the amount of left / right keys pressed */
	private int mNumDirKeys = 0;
	
	public GameView (Launcher launcher)
	{
		mLauncher = launcher;
		setupScene();
	}
	
	/** Sets up the scene for this game view */
	public void setupScene ()
	{
		mRoot = new StackPane();
		
		/* Star field layer */
		mStarField = new StarField ();
		mRoot.getChildren().add(mStarField);
		
		/* Playing field */
		mGameWorld = new Pane();
		mRoot.getChildren().add(mGameWorld);
		
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
	
	/** Plays the background animations */
	public void playAnimations()
	{
		mStarField.playAnimation();
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
				/* Checks if the current game state is running */
				if (getEngine().getGameState() == GameState.kGameRunning)
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
						mGameEngine.getPlayer().shoot();
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
				/* Checks if the current game state is running */
				if (getEngine().getGameState() == GameState.kGameRunning)
				{
					if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.RIGHT)
					{
						--mNumDirKeys;
						if (mNumDirKeys < 0)
						{
							mNumDirKeys = 0;
						}
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
		mGameEngine.setCurrentLives(3);
		mGameEngine.startLevel();
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
