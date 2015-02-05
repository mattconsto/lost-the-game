package deserted.engine.graphics;

/**
 * Created by Ollie on 01/02/2015.
 */
public class Graphics {
	
	//Shape batching
	private float[] shapeBatch;
	private int[] shapeInd;
	private int numTriangles = 0;
	private int maxTriangles;
	//Shaders
	private int shapeVSId, shapeFSId, shapePId;
	//Objects
	private int shapeVAOId, shapeVBOId, shapeVBOIId;
	
	//Sprite Batching
	private float[] spriteBatch;
	private int[] spriteInd;
	private int numSprites = 0;
	private int maxSprites;
	//Shaders
	private int spriteVSId, spriteFSId, spritePId;
	//Objects
	private int spriteVAOId, spriteVBOId, spriteVBOIId;
	
	public Graphics(int maxSprites, int maxTriangles){
		this.maxSprites = maxSprites;
		this.maxTriangles = maxTriangles;
		
		shapeBatch = new float[maxTriangles*6];
	}
	
	public void startSpriteBatch(){
		
	}
	
	public void endSpriteBatch(){
		
	}
	
	public void startShapeBatch(){
		
	}
	
	public void endShapeBatch(){
		
	}
	
	public void fillRect(){
		
	}
	
	public void drawRect(){
		
	}
	
}
