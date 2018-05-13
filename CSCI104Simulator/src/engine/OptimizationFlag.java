package engine;

public enum OptimizationFlag 
{
	kPerformance ("Fast", "Optimizes all of the game's firework / explosion animations to use roughly 50% less resources, thus making gameplay"
			+ "		and transitions between levels smoother. However, the quality of the fireworks will suffer."), 
	kDefault ("Default", "Only optimize the in-game explosion animations for peformance. The fireworks that appear once every end of level would be retained."), 
	kQuality ("Pretty", "Retains the original quality of the game's firework / explosion animations. However, gameplay peformance may suffer (especially on lower end computers).");
	
	private String mName;
	private String mDescription;
	
	OptimizationFlag (String name, String description)
	{
		mName = name;
		mDescription = description;
	}
	
	public String getName ()
	{
		return mName;
	}
	
	public String getDescription ()
	{
		return mDescription;
	}
}
