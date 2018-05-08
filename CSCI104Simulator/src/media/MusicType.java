package media;

public enum MusicType 
{
	kNull (MusicStyle.kNone, ""),
	kBossMusic1 (MusicStyle.kBoss, "src/assets/BGM/BossBGM1.mp3");
	
	private MusicStyle mStyle;
	private String mDirectory;
	
	MusicType (MusicStyle style, String source)
	{
		mStyle = style;
		mDirectory = source;
	}
	
	public MusicStyle getStyle()
	{
		return mStyle;
	}
	
	public String getDirectory ()
	{
		return mDirectory;
	}
}
