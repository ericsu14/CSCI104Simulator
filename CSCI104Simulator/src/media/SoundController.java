package media;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundController 
{
	/* Current media object being played */
	private Media mCurrentMedia;
	/* Keeps track of the last soundtype played */
	private SoundType mCurrentType;
	
	
	public SoundController ()
	{
		mCurrentType = SoundType.kNull;
	}
	
	/** Plays a sound of a certain sound type */
	public void playSound (SoundType type)
	{
		try
		{
			if (type != mCurrentType)
			{
				mCurrentType = type;
				mCurrentMedia = new Media (new File (mCurrentType.getDirectory()).toURI().toString());
			}
			MediaPlayer player = new MediaPlayer (mCurrentMedia);
			player.play();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
