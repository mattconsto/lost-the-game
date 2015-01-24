package TileSystem;

public class Camera {

	public float x, y;
	private int mapSize;
	
	public Camera(float x, float y, int mapSize){
		this.x = x;
		this.y = y;
		this.mapSize = mapSize;
	}
	
	public void move(float x, float y){
		this.x += x;
		this.y += y;
	}
	
}
