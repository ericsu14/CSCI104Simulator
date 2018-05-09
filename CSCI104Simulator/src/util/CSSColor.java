package util;

public enum CSSColor 
{
	kWhite("-fx-text-fill: white;"), 
	kRed("-fx-text-fill: red;"), 
	kYellow("-fx-text-fill: yellow"),
	kBlue("-fx-text-fill: blue"),
	kGreen("-fx-text-fill: green");
	
	private String mColor;
	
	CSSColor (String color)
	{
		mColor = color;
	}
	
	
	/** Returns the CSS color data tied to this color */
	public String getColor ()
	{
		return mColor;
	}
	
}
