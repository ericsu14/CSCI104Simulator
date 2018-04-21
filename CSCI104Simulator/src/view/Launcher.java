package view;



import engine.GameEngine;
import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application
{
	/* Title of the game */
	public final String mGameTitle = "CSCI 104 Simulator";
	/* Stage object */
	public Stage mStage;
	/* Main menu */
	public MainMenu mMainMenu;
	/* Game view */
	public GameView mGameView;
	/* The main game engine */
	public GameEngine mGameEngine;
	/* Dimensions of the game's width */
	public static final double mWidth = 1024.0;
	/* Dimensions of the game's height */
	public static final double mHeight = 768.0;
	
	/** Initializes the game assets and starts the game */
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		initializeScenes();
		mStage = primaryStage;
		
		mStage.setTitle(mGameTitle);
		mStage.setScene(mMainMenu.getScene());
		mStage.setHeight(mHeight);
		mStage.setWidth(mWidth);
		mStage.setResizable(false);
		primaryStage.show();
	}
	
	public static void main (String [] args)
	{
		launch(args);
	}
	
	/** Initializes the scenes for this game */
	public void initializeScenes()
	{
		mGameView = new GameView (this);
		mGameEngine = new GameEngine (mGameView);
		mMainMenu = new MainMenu(this);
		// mGameView.setGameEngine(mGameEngine);
		// mGameEngine.setGameView(mGameView);
	}
	
	/** Switches the current scene to the main menu */
	public void switchMainMenu ()
	{
		mStage.setScene(mMainMenu.getScene());
	}
	
	/** Switches to the game scene */
	public void switchGameScene()
	{
		mGameView.startNewLevel();
		mStage.setScene(mGameView.getScene());
		mStage.sizeToScene();
		mStage.setHeight(mHeight);
		mStage.setWidth(mWidth);
	}
	
	/** Returns the initialized game engine */
	public GameEngine getGameEngine()
	{
		return mGameEngine;
	}
}
