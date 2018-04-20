package view;



import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Launcher extends Application
{
	/* Title of the game */
	public static final String mGameTitle = "CSCI 104 Simulator";
	/* Stage object */
	public Stage mStage;
	/* Main menu */
	public MainMenu mMainMenu;
	/* Dimensions of the game's width */
	public static final double mWidth = 1024.0;
	/* Dimensions of the game's height */
	public static final double mHeight = 768.0;
	/* Stores the image file for the dummy test enemy */
	public ImageView mTestEnemySprite;
	
	/** Initializes the game assets and starts the game */
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		/* Init. image content */
		mTestEnemySprite = new ImageView (new Image (getClass().getClassLoader().getResourceAsStream("assets/img/testEnemy.png")));
		
		initializeScenes();
		mStage = primaryStage;
		
		primaryStage.setTitle(mGameTitle);
		primaryStage.setScene(mMainMenu.getScene());
		primaryStage.setHeight(mHeight);
		primaryStage.setWidth(mWidth);
		primaryStage.show();
	}
	
	public static void main (String [] args)
	{
		launch(args);
	}
	
	/** Initializes the scenes for this game */
	public void initializeScenes()
	{
		mMainMenu = new MainMenu(this);
	}
	
	/** Switches the current scene to the main menu */
	public void switchMainMenu ()
	{
		mStage.setScene(mMainMenu.getScene());
	}
}
