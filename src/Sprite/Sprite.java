package Sprite;

import org.newdawn.slick.geom.Point;

public class Sprite {
	
	private Point texCoord;
	private boolean walkable = true;
	private boolean onGround = false;
	private float scale = 1;
	
	public Sprite(Point texCoord, boolean walkable, boolean onGround, float scale){
		this.texCoord = texCoord;
		this.walkable = walkable;
		this.onGround = onGround;
		this.scale = scale;
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

}
