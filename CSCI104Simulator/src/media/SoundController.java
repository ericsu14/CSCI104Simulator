package media;

import java.io.File;
import java.util.Hashtable;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundController 
{
	/* Current media object being played */
	private Media mCurrentMedia;
	/* Keeps track of the last soundtype played */
	private SoundType mCurrentType;
	/* Stores the sound files being used by this player */
	private Hashtable<SoundType, Media> mMediaCache;
	
	/* Controls the current background music being played */
	private MediaPlayer mBGMPlayer;
	/* Stores the current MusicType being played for the background music */
	private MusicType mCurrentBackgroundType;
	/* Stores the music files being used by this player */
	private Hashtable <MusicType, Media> mBGMCache;
	
	public SoundController ()
	{
		mCurrentType = SoundType.kNull;
		mMediaCache = new Hashtable <SoundType, Media> ();
		mBGMCache = new Hashtable <MusicType, Media> ();
		
		mCurrentBackgroundType = MusicType.kNull;
		
		/* Preloads the sound assets */
		for (SoundType t : SoundType.values())
		{
			if (t != SoundType.kNull)
			{
				mMediaCache.put(t, new Media (new File (t.getDirectory()).toURI().toString()));
			}
		}
		
		/* Preloads the BGM assets */
		for (MusicType t : MusicType.values())
		{
			if (t != MusicType.kNull)
			{
				mBGMCache.put(t, new Media (new File (t.getDirectory()).toURI().toString()));
			}
		}
	}
	
	/** Plays a sound of a certain sound type */
	public void playSound (SoundType type)
	{
		try
		{
			if (type != mCurrentType)
			{
				if (!mMediaCache.contains(type))
				{
					mMediaCache.put(type, new Media (new File (type.getDirectory()).toURI().toString()));
				}
				mCurrentMedia = mMediaCache.get(type);
				mCurrentType = type;
			}
			MediaPlayer player = new MediaPlayer (mCurrentMedia);
			player.play();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/** Switches the background music to a new set */
	public void playBGM (MusicType type)
	{
		
	}
	
}
