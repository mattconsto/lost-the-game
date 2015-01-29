package Graphics;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Renderer {
	
	private int maxSprites;
	
	private float[] batch;
	private int i = 0;
	
	public Renderer(int maxSprites){
		this.maxSprites = maxSprites;
		batch = new float[maxSprites*8];
	}
	
	//Creates a new batch
	public void begin(Texture tex){
		tex.bind();
		i = 0;
	}
	
	public void drawImage(float x, float y, float width, float height, int srcX, int srcY, int srcX2, int srcY2){
		//Top left
		batch[i++] = x;
		batch[i++] = y;
		batch[i++] = srcX;
		batch[i++] = srcY;
		batch[i++] = x;
		batch[i++] = y;
	}
	
	//Ends the current batch and sends it to the gpu
	public void end(){
		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(currentBatch.length);
	}

}
