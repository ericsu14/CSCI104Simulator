package media;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class SoundController 
{
	/** Sound player */
	/* Current media object being played */
	private Media mCurrentMedia;
	/* Keeps track of the last soundtype played */
	private SoundType mCurrentType;
	/* Stores the sound files being used by this player */
	private Hashtable<SoundType, Media> mMediaCache;
	/* MAX volume of the sounds */
	private double mSoundVolume = 0.15;
	
	/** BGM Player */
	/* Controls the current background music being played */
	private MediaPlayer mBGMPlayer = null;
	/* Stores the music files being used by this player */
	private Hashtable <MusicType, MediaPlayer> mBGMCache;
	/* Stores the current playlist of media files being played */
	private Queue <MediaPlayer> mPlaylist;
	/* MAX volume of the BGM */
	private double mBGMVolume = 0.30;
	/* An audioclip that is capable of being overwritten by new requests */
	private AudioClip mReusableSound = null;
	
	public SoundController ()
	{
		mCurrentType = SoundType.kNull;
		mMediaCache = new Hashtable <SoundType, Media> ();
		mBGMCache = new Hashtable <MusicType, MediaPlayer> ();
		mPlaylist = new LinkedList <MediaPlayer> ();
		
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
				MediaPlayer media = new MediaPlayer (new Media (new File (t.getDirectory()).toURI().toString()));
				media.setAutoPlay(false);
				media.setBalance(0.0);
				media.setVolume(mBGMVolume);
				media.setOnEndOfMedia(() -> 
				{
					// Adds itself back to the playlist
					media.seek(Duration.ZERO);
					media.stop();
					mPlaylist.add(media);
					// Plays the next media in the playlist
					if (!mPlaylist.isEmpty())
					{	
						mBGMPlayer = mPlaylist.remove();
						mBGMPlayer.play();
					}
				}); 
				mBGMCache.put(t, media);
			}
		}
		
	}
	
	/** Plays a sound of a certain sound type */
	public void playSound (SoundType type)
	{
		// NULL check
		if (type == SoundType.kNull)
		{
			return;
		}
		
		if (type != mCurrentType)
		{
			if (!mMediaCache.contains(type))
			{
				mMediaCache.put(type, new Media (new File (type.getDirectory()).toURI().toString()));
			}
			mCurrentMedia = mMediaCache.get(type);
			mCurrentType = type;
		}
			
		AudioClip player = new AudioClip (mCurrentMedia.getSource());
		player.setVolume(mSoundVolume);
		player.play();
	}
	
	/** Plays a sound clip that could be interrupted and overridden by new sound requests
	 * 		@param type - The type of sound to be played */
	public void playSoundOverwritable (SoundType type)
	{
		// NULL check
		if (type == SoundType.kNull)
		{
			return;
		}
		
		if (mReusableSound != null)
		{
			mReusableSound.stop();
		}
		
		mReusableSound = new AudioClip (mMediaCache.get(type).getSource());
		mReusableSound.setVolume(mSoundVolume);
		mReusableSound.play();
	}
	
	/** Reinitializes the BGM playlist to a new style of music */
	public void setPlaylist (MusicStyle style)
	{	
		mPlaylist.clear();
		
		/* Stops the current music being played */
		if (mBGMPlayer != null)
		{
			mBGMPlayer.stop();
		}
		
		ArrayList <MediaPlayer> playlist = new ArrayList <MediaPlayer>();
		/* Gathers the new music playlist based on the passed music style */
		if (style != MusicStyle.kNone)
		{
			for (MusicType type : MusicType.values())
			{
				if (type.getStyle() == style)
				{
					playlist.add(mBGMCache.get(type));
				}
			}
			
			/* Randomly shuffles the music playlist */
			Collections.shuffle(playlist);
			/* Inserts the elements back into the queue */
			for (MediaPlayer media : playlist)
			{
				mPlaylist.add(media);
			}
			
			mBGMPlayer = mPlaylist.remove();
			mBGMPlayer.play();
		}
	}
	
	/** Stops and destroys the current overwritable sound being played */
	public void stopOverwritableSound ()
	{
		if (mReusableSound != null)
		{
			mReusableSound.stop();
			mReusableSound = null;
		}
	}
	
}
