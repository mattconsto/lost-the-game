package deserted.engine.graphics;

public class TextureRegion {
	
	public float x, y, right, bottom;
	
	public TextureRegion(Texture tex, int x, int y, int width, int height){
		this.x = (float)x / (float)tex.getWidth();
		this.y = (float)y / (float)tex.getHeight();
		this.right = this.x + (float)width/(float)tex.getWidth();
		this.bottom = this.y + (float)height/(float)tex.getHeight();
	}

}
