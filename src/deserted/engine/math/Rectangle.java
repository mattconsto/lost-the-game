package deserted.engine.math;

public class Rectangle {
	
	private int x, y, right, bottom;
	
	public Rectangle(int x, int y, int width, int height){
		this.x = x;	this.y = y;
		
		right = x + width;
		bottom = y + height;
	}
	
	public boolean contains(int x, int y){
		if(x >= this.x && x <= right && y >= this.y && y <= bottom)
			return true;
		return false;
	}

}
