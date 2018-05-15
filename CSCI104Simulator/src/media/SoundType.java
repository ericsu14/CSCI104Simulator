package media;

public enum SoundType 
{
	kNull (""),
	kPlayerShoot ("src/assets/sounds/playerShoot.mp3"), 
	kUnguidedProjectile ("src/assets/sounds/unguidedProjectile.wav"),
	kEnemyExplode ("src/assets/sounds/enemyExplosion.wav"),
	kEnemyHit ("src/assets/sounds/enemyHit.wav"),
	kPlayerExplode ("src/assets/sounds/playerExplosion.wav"),
	kEnemyPrepare ("src/assets/sounds/enemyPrepare.wav"),
	kShieldHit ("src/assets/sounds/shieldHit.wav"),
	kShieldDie ("src/assets/sounds/shieldDie.wav"),
	kEnemyDie ("src/assets/sounds/enemyDie.wav"),
	kEnemyDie2 ("src/assets/sounds/enemyDie2.wav"),
	kExtraLife ("src/assets/sounds/extraLife.mp3"),
	kFireworkBlast ("src/assets/sounds/fireworkBlast.wav"),
	kBossDie ("src/assets/sounds/bossDie.mp3"),
	kCoteHit ("src/assets/sounds/coteHit.wav"),
	kCoteTaunt1 ("src/assets/sounds/coteTaunt1.wav"),
	kCoteTaunt2 ("src/assets/sounds/coteTaunt2.wav"),
	kCoteTaunt3 ("src/assets/sounds/coteTaunt3.wav");
	
	private String mDirectory;
	
	/** Constructs a new sound type with a passed in directory */
	SoundType (String dir)
	{
		mDirectory = dir;
	}
	
	/** @return the directory where this sound file is located */
	String getDirectory()
	{
		return mDirectory;
	}
}
