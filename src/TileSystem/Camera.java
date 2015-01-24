package TileSystem;

public class Camera {

	public float x, y;
	
	public Camera(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public void move(float x, float y){
		this.x += x;
		this.y += y;
	}
	
}
