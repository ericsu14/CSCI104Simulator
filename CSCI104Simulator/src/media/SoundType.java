package media;

public enum SoundType 
{
	kNull (""),
	kPlayerShoot ("playerShoot.mp3"), 
	kUnguidedProjectile ("unguidedProjectile.wav"),
	kEnemyExplode ("enemyExplosion.wav"),
	kEnemyHit ("enemyHit.wav"),
	kPlayerExplode ("playerExplosion.wav"),
	kEnemyPrepare ("enemyPrepare.wav"),
	kShieldHit ("shieldHit.wav"),
	kShieldDie ("shieldDie.wav"),
	kEnemyDie ("enemyDie.wav"),
	kEnemyDie2 ("enemyDie2.wav"),
	kExtraLife ("extraLife.mp3"),
	kFireworkBlast ("fireworkBlast.wav"),
	kBossDie ("bossDie.mp3"),
	kCoteHit ("coteHit.wav"),
	kCoteTaunt1 ("coteTaunt1.wav"),
	kCoteTaunt2 ("coteTaunt2.wav"),
	kCoteTaunt3 ("coteTaunt3.wav"),
	kCoteTaunt4 ("coteTaunt4.wav");
	
	private String mDirectory;
	private final String mRootURL = "src/assets/sounds/";
	
	/** Constructs a new sound type with a passed in directory */
	SoundType (String dir)
	{
		mDirectory = mRootURL + dir;
	}
	
	/** @return the directory where this sound file is located */
	String getDirectory()
	{
		return mDirectory;
	}
}
