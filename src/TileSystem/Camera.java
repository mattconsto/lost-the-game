package TileSystem;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.geom.Point;

public class Camera {

	public float x, y; //center position in world space
	public float zoom = 1;
	public int tileRes;
	private Point windowSize;
	
	public Camera(float x, float y, int tileRes, Point windowSize){
		this.x = x;
		this.y = y;
		this.tileRes = tileRes;
		this.windowSize = windowSize;
	}
	
	public void move(float x, float y){
		this.x += x;
		this.y += y;
	}
	
	public void zoom(float zoomDelta){
		zoom = zoom + zoomDelta;
		if (zoom >= 2)
			zoom = 2;
		if (zoom <= 0.5f)
			zoom = 0.5f;
	}
	
	public Vector2f screenToWorldPos(int scX, int scY){
		float resTimesScale = tileRes * zoom;
		float xd = scX - windowSize.getX()/2;
		float yd = scY - windowSize.getY()/2;
		return new Vector2f(xd/resTimesScale + x, yd/resTimesScale + y);
	}
	
	public Vector2f worldToScreenPos(float worldX, float worldY){
		float resTimesScale = tileRes * zoom;
		float xd = x - worldX;
		float yd = y - worldY;
		return new Vector2f(windowSize.getX()/2 - xd*resTimesScale, windowSize.getY()/2 - yd*resTimesScale);
	}
	
	public Vector2f getOffsets(){
		float resTimesZoom = tileRes * zoom;
		return new Vector2f((resTimesZoom*x)-windowSize.getX()/2, (resTimesZoom*y)-windowSize.getY()/2);
	}
	
}
