package view;

import engine.GameEngine;
import javafx.scene.Scene;
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
	private Pane mPlayingField;
	/* Borderpane containing the UI layer */
	private GameUI mGameUI;
	/* A ref. to the main launcher */
	private Launcher mLauncher;
	
	public GameView (Launcher launcher)
	{
		mLauncher = launcher;
		mGameEngine = mLauncher.getGameEngine();
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
		mPlayingField = new Pane();
		mRoot.getChildren().add(mPlayingField);
		
		/* Game UI */
		mGameUI = new GameUI(this);
		mRoot.getChildren().add(mGameUI);
		mRoot.setStyle("-fx-background-color: black");
		
		mScene = new Scene (mRoot);
	}
	
	/** @return the Launcher object */
	public Launcher getLauncher()
	{
		return mLauncher;
	}
	
	public GameEngine getEngine()
	{
		return mGameEngine;
	}
	
	/** @return the scene object */
	public Scene getScene()
	{
		return mScene;
	}
	
	/** Plays the background animations */
	public void playAnimations()
	{
		mStarField.playAnimation();
	}
	
	/** Sets up the scene to start a new level */
	public void startNewLevel()
	{
		// TODO: 
		playAnimations();
	}
	
	/** Sets the GameView's game engine */
	public void setGameEngine (GameEngine engine)
	{
		mGameEngine = engine;
	}
	
}
