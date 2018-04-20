package view;

import engine.GameEngine;
import javafx.scene.layout.StackPane;

public class GameView 
{
	/* Stackpane used to tie all of the graphical components together */
	private StackPane mRoot;
	
	/* A reference to the created GameEngine object */
	private GameEngine mGameEngine;
	
	/* Contains the starfield background layer */
	private StarField mStarField;
	
	/* A ref. to the main launcher */
	private Launcher mLauncher;
	
	public GameView (Launcher launcher)
	{
		
	}
}
