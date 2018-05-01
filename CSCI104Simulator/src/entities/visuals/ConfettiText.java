package entities.visuals;

/**
 * A custom text used to give the appearance of confetti.
 * Could be configured to enter the screen and "explode" at a certain position
 * to give the illusion of a firework.
 * 
 * @author Eric Su
 * ITP 368, Spring 2018
 * Assignment 09
 */

import java.util.Random;

import javafx.animation.PathTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class ConfettiText extends Text
{
	/* Orig. color of the text */
	private Color mColor;
	/* X position of the destination the text would expand to before it starts falling */
	private int mDestX;
	/* Y position of the destination the text would expand to before it starts falling */
	private int mDestY;
	/* X position of the origin point */
	private int mOriginX;
	/* Y position of the origin point */
	private int mOriginY;
	/* Stores the starting coordinates */
	private MoveTo mStartingCoordinates;
	/* Checks if the text has completed its expanding animation */
	private boolean mCompletedExpandAnim;
	/* Determines if this text is either part of a fireworks animation or explosion */
	private boolean mIsFireworks;
	/* Duration of fireworks expand animation in seconds */
	private int mExpandDuration;
	
	/* Randomly generated offset values */
	private double mAngleOffset;
	private double mGravity;
	private int mRotationOffset;
	
	/* Rate in which the entity starts to disappear */
	private double mFadeRate;
	
	/* RNG used for generating values */
	private static Random rand = new Random ();
	
	/** Constructor used for spawning a single instance of confetti.
	 * 	The confetti would always be created at the top of the screen, designated by x
	 * 		@param x - x Position where the confetti is spawned */
	public ConfettiText (int x)
	{
		super (x, 0, "*");
		
		mCompletedExpandAnim = true;
		/* Assigns a random color to the confetti */
		mColor = new Color (rand.nextDouble(), rand.nextDouble(), rand.nextDouble(), 1.0);
		
		this.setFont(new Font ("Consolas", 12));
		this.setFill(mColor);
		
		mGravity = GenerateBetweenRange (1.4, 2.5);
		mAngleOffset = GenerateBetweenRange (-0.2, 0.2);
		mRotationOffset = rand.nextInt(2) + 1;
		
		/* Confetti should take longer to despawn */
		mFadeRate = 0.001;
	}

	/** Constructor used for shooting up the text in a fireworks like manner
	 *  before turning into confetti.
	 *  	@param x - X pos of the fireworks explosion point
	 *  	@param y - Y pos of the fireworks explosion point
	 *  	@param destX - X pos of the position where the letter should expand to before falling
	 *  	@param destY - Y pos of the position where the letter should expand to before falling
	 *  	@param text - The String of the text object
	 *  	@param color - The initial color of the text object
	 *  	@param isFireworks - Determines if this entity is part of a firework animation or an explosion */
	public ConfettiText (int x, int y, int destX, int destY, String text, Color color, boolean isFirework)
	{
		super (x, y + 500.0, text);
		
		mOriginX = x;
		mOriginY = y;
		mColor = color;
		mDestX = destX;
		mDestY = destY;
		mIsFireworks = isFirework;
		
		/* If this text is part of a fireworks animation, shoot this text up from the
		 * bottom of the screen */
		if (mIsFireworks)
		{
			mStartingCoordinates = new MoveTo (x, y + 500.0);
			mExpandDuration = 2;
			mFadeRate = 0.02;
		}
		/* Otherwise, explode in place */
		else
		{
			mStartingCoordinates = new MoveTo (x, y);
			mExpandDuration = 1;
			mFadeRate = 0.02;
		}
		mCompletedExpandAnim = false;
		
		this.setFont(new Font ("Consolas", 10));
		this.setFill(mColor);
		
		mGravity = GenerateBetweenRange (0.1, 0.5);
		mAngleOffset = GenerateBetweenRange (-0.2, 0.2);
		mRotationOffset = rand.nextInt(2) + 1;
		
		expand();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(mAngleOffset);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((mColor == null) ? 0 : mColor.hashCode());
		result = prime * result + (mCompletedExpandAnim ? 1231 : 1237);
		result = prime * result + mDestX;
		result = prime * result + mDestY;
		result = prime * result + mExpandDuration;
		temp = Double.doubleToLongBits(mFadeRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(mGravity);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (mIsFireworks ? 1231 : 1237);
		result = prime * result + mOriginX;
		result = prime * result + mOriginY;
		result = prime * result + mRotationOffset;
		result = prime * result + ((mStartingCoordinates == null) ? 0 : mStartingCoordinates.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConfettiText other = (ConfettiText) obj;
		if (Double.doubleToLongBits(mAngleOffset) != Double.doubleToLongBits(other.mAngleOffset))
			return false;
		if (mColor == null) {
			if (other.mColor != null)
				return false;
		} else if (!mColor.equals(other.mColor))
			return false;
		if (mCompletedExpandAnim != other.mCompletedExpandAnim)
			return false;
		if (mDestX != other.mDestX)
			return false;
		if (mDestY != other.mDestY)
			return false;
		if (mExpandDuration != other.mExpandDuration)
			return false;
		if (Double.doubleToLongBits(mFadeRate) != Double.doubleToLongBits(other.mFadeRate))
			return false;
		if (Double.doubleToLongBits(mGravity) != Double.doubleToLongBits(other.mGravity))
			return false;
		if (mIsFireworks != other.mIsFireworks)
			return false;
		if (mOriginX != other.mOriginX)
			return false;
		if (mOriginY != other.mOriginY)
			return false;
		if (mRotationOffset != other.mRotationOffset)
			return false;
		if (mStartingCoordinates == null) {
			if (other.mStartingCoordinates != null)
				return false;
		} else if (!mStartingCoordinates.equals(other.mStartingCoordinates))
			return false;
		return true;
	}

	/** Shoots up the text in a fireworks like manner */
	private void expand()
	{
		/* Constructs a line to bring the object to it's desginated location
		 * in a fireworks missle like manner */
		LineTo shootUpWayPoint = new LineTo (mOriginX, mOriginY);
		/* Constructs a line from the starting position to the target */
		LineTo expandWaypoint = new LineTo (mDestX, mDestY);
		
		/* Creates a new path connecting the assets */
		Path expandPath = new Path();
		expandPath.getElements().addAll (mStartingCoordinates, shootUpWayPoint, expandWaypoint);
		
		/* Creates a new path transition */
		PathTransition expandAnimation = new PathTransition ();
		
		/* Set up its assets */
		expandAnimation.setAutoReverse(false);
		expandAnimation.setDuration(Duration.seconds(mExpandDuration));
		expandAnimation.setCycleCount(0);
		expandAnimation.setNode(this);
		expandAnimation.setPath(expandPath);
		expandAnimation.setOnFinished(e -> 
		{
			mCompletedExpandAnim = true;
		});
		expandAnimation.play();
	}
	
	/** Starts playing the confetti falling down animation
	 *  once its expand animation is completed. */
	public void update ()
	{
		if (mCompletedExpandAnim)
		{
			/* Starts falling down based on gravity */
			
			/* The idea is to create a random offset to give the illusion of confetti */
			this.setX(this.getX() + mAngleOffset);					// Angle
			this.setY(this.getY() + mGravity);						// Gravity fall rate
			this.setRotate(this.getRotate() + mRotationOffset);		// Rotation (gives the illusion of falling confetti)
			
			/* Makes the entity less visable over time */
			this.setOpacity(this.getOpacity() - mFadeRate);
		}
	}
	
	/** Returns true if this object is barely visable to the screen, labeling it ready to be
	 *  despawned */
	public boolean canDespawn ()
	{
		return (this.getOpacity() <= 0.05);
	}
	
	/**
	 * @return the mColor
	 */
	public Color getmColor()
	{
		return mColor;
	}

	/**
	 * @param mColor the mColor to set
	 */
	public void setmColor(Color mColor)
	{
		this.mColor = mColor;
	}

	/**
	 * @return the mDestX
	 */
	public int getmDestX()
	{
		return mDestX;
	}

	/**
	 * @param mDestX the mDestX to set
	 */
	public void setmDestX(int mDestX)
	{
		this.mDestX = mDestX;
	}

	/**
	 * @return the mDestY
	 */
	public int getmDestY()
	{
		return mDestY;
	}

	/**
	 * @param mDestY the mDestY to set
	 */
	public void setmDestY(int mDestY)
	{
		this.mDestY = mDestY;
	}

	/**
	 * @return the mOriginX
	 */
	public int getmOriginX()
	{
		return mOriginX;
	}

	/**
	 * @param mOriginX the mOriginX to set
	 */
	public void setmOriginX(int mOriginX)
	{
		this.mOriginX = mOriginX;
	}

	/**
	 * @return the mOriginY
	 */
	public int getmOriginY()
	{
		return mOriginY;
	}

	/**
	 * @param mOriginY the mOriginY to set
	 */
	public void setmOriginY(int mOriginY)
	{
		this.mOriginY = mOriginY;
	}

	/**
	 * @return the mStartingCoordinates
	 */
	public MoveTo getmStartingCoordinates()
	{
		return mStartingCoordinates;
	}

	/**
	 * @param mStartingCoordinates the mStartingCoordinates to set
	 */
	public void setmStartingCoordinates(MoveTo mStartingCoordinates)
	{
		this.mStartingCoordinates = mStartingCoordinates;
	}

	/**
	 * @return the mCompletedExpandAnim
	 */
	public boolean ismCompletedExpandAnim()
	{
		return mCompletedExpandAnim;
	}

	/**
	 * @param mCompletedExpandAnim the mCompletedExpandAnim to set
	 */
	public void setmCompletedExpandAnim(boolean mCompletedExpandAnim)
	{
		this.mCompletedExpandAnim = mCompletedExpandAnim;
	}

	/**
	 * @return the mAngleOffset
	 */
	public double getmAngleOffset()
	{
		return mAngleOffset;
	}

	/**
	 * @param mAngleOffset the mAngleOffset to set
	 */
	public void setmAngleOffset(double mAngleOffset)
	{
		this.mAngleOffset = mAngleOffset;
	}

	/**
	 * @return the mGravity
	 */
	public double getmGravity()
	{
		return mGravity;
	}

	/**
	 * @param mGravity the mGravity to set
	 */
	public void setmGravity(double mGravity)
	{
		this.mGravity = mGravity;
	}

	/**
	 * @return the mRotationOffset
	 */
	public int getmRotationOffset()
	{
		return mRotationOffset;
	}

	/**
	 * @param mRotationOffset the mRotationOffset to set
	 */
	public void setmRotationOffset(int mRotationOffset)
	{
		this.mRotationOffset = mRotationOffset;
	}

	/**
	 * @return the mFadeRate
	 */
	public double getmFadeRate()
	{
		return mFadeRate;
	}

	/**
	 * @param mFadeRate the mFadeRate to set
	 */
	public void setmFadeRate(double mFadeRate)
	{
		this.mFadeRate = mFadeRate;
	}
	
	/** Helper function that generates a random double between the ranges
	 *  min and max.
	 *  	@param min - Min range of generated double
	 *  	@param max - Max range of generated double
	 *  	@return A new random double between min and max */
	private double GenerateBetweenRange (double min, double max)
	{
		double seed = rand.nextDouble();
		return min + (seed * (max - min));
	}
}
