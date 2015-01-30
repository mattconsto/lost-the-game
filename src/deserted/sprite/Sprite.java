package deserted.sprite;

import org.newdawn.slick.geom.Point;

public class Sprite {
	
	public static float elapsedTime;
	
	private Point texCoord;
	private boolean walkable = true;
	private boolean onGround = false;
	private boolean animated = false;
	private int frames;
	private float frameInterval;
	private float scale = 1;
	
	public Sprite(Point texCoord, boolean walkable, boolean onGround, float scale){
		this.texCoord = texCoord;
		this.walkable = walkable;
		this.onGround = onGround;
		this.scale = scale;
	}
	
	public Sprite(Point texCoord, boolean walkable, boolean onGround, float scale, int frames, float frameRate){
		this.texCoord = texCoord;
		this.walkable = walkable;
		this.onGround = onGround;
		this.scale = scale;
		this.animated = true;
		this.frames = frames;
		
		frameInterval = 1/frameRate;
	}
	
	//Assuming the sprite is 32x32 in a horizontal line in the texture.
	public Point getTexCoord(float timeOffset){
		if(!animated)
			return getTexCoord();
		
		int xOff = (int)texCoord.getX()+32*((int)((elapsedTime+timeOffset)/frameInterval)%frames);
		return new Point(xOff, texCoord.getY());
	}
	
	public Point getTexCoord(){
		return texCoord;
	}
	
	public boolean isWalkable(){
		return walkable;
	}
	
	public boolean isOnGround(){
		return onGround;
	}
	
	public float getScale(){
		return scale;
	}
	
	public boolean isAnimated(){
		return animated;
	}

}
