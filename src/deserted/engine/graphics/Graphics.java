package deserted.engine.graphics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * Created by Ollie on 01/02/2015.
 */
public class Graphics {
	
	//Shape batching
	private float[] shapeBatch;
	private int[] shapeInd;
	private int numTriangles = 0;
	private int maxTriangles;
	private int shI = 0;
	//Shaders
	private int shapeVSId, shapeFSId, shapePId;
	//Objects
	private int shapeVAOId, shapeVBOId, shapeVBOIId;
	
	//Sprite Batching
	private float[] spriteBatch;
	private int[] spriteInd;
	private int numSprites = 0;
	private int maxSprites;
	private int spI = 0;
	//Shaders
	private int spriteVSId, spriteFSId, spritePId;
	//Objects
	private int spriteVAOId, spriteVBOId, spriteVBOIId;
	
	public Graphics(int maxSprites, int maxTriangles){
		this.maxSprites = maxSprites;
		this.maxTriangles = maxTriangles;
		
		shapeBatch = new float[maxTriangles*18];
		shapeInd = new int[maxTriangles*3];
		
		spriteBatch = new float[maxSprites*32];
		spriteInd = new int[maxSprites*6];
		
		initShapeBatch();
		initSpriteBatch();
		initShaders();
	}
	
	public void fillRect(){
		
	}
	
	public void drawRect(){
		
	}
	
	public void startSpriteBatch(Texture tex){
		if(tex != null)
			tex.bind();
		else{
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		}
		spI = 0;
		numSprites = 0;
	}
	
	public void endSpriteBatch(){		
		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(spI);		
		for (int j = 0; j < spI; j++) {
            verticesBuffer.put(spriteBatch[j]);
        }
        verticesBuffer.flip();
        
        IntBuffer indicesBuffer = BufferUtils.createIntBuffer(spriteInd.length);
        indicesBuffer.put(spriteInd);
        indicesBuffer.flip();
        
        GL30.glBindVertexArray(spriteVAOId);
        
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, spriteVBOId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
        
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, spriteVBOIId);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        
        GL20.glUseProgram(spritePId);
        
        GL30.glBindVertexArray(spriteVAOId);
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
         
        // Bind to the index VBO that has all the information about the order of the vertices
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, spriteVBOIId);
         
        // Draw the vertices
        GL11.glDrawElements(GL11.GL_TRIANGLES, numSprites*6, GL11.GL_UNSIGNED_INT, 0);
         
        // Put everything back to default (deselect)
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
         
        GL20.glUseProgram(0);
	}
	
	public void startShapeBatch(){
		
	}
	
	public void endShapeBatch(){
		
	}
	
	private void initShapeBatch(){
		//Setup shape objects
		shapeVAOId = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(shapeVAOId);
		
		//Create shape vbo
		shapeVBOId = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, shapeVBOId);
		
		//Set attribute pointers
		GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, 24, 0);	//Position
		GL20.glVertexAttribPointer(1, 4, GL11.GL_FLOAT, false, 24, 8);	//Color
		
		//Unbind
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
		
		shapeVBOIId = GL15.glGenBuffers();
	}
	
	private void initSpriteBatch(){
		//Setup shape objects
		spriteVAOId = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(spriteVAOId);
		
		//Create shape vbo
		spriteVBOId = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, spriteVBOId);
		
		//Set attribute pointers
		GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, 32, 0);	//Position
		GL20.glVertexAttribPointer(1, 4, GL11.GL_FLOAT, false, 32, 8);	//Color
		GL20.glVertexAttribPointer(2, 2, GL11.GL_FLOAT, false, 32, 24);	//UV Coords
		
		//Unbind
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
		
		spriteVBOIId = GL15.glGenBuffers();
	}
	
	private void initShaders(){
		//load shape shaders
		shapeVSId = loadShader("shaders/basicShape.vs", GL20.GL_VERTEX_SHADER);
		shapeFSId = loadShader("shaders/basicShape.fs", GL20.GL_FRAGMENT_SHADER);
		
		shapePId = GL20.glCreateProgram();
		GL20.glAttachShader(shapePId, shapeVSId);
		GL20.glAttachShader(shapePId, shapeFSId);
		
		GL20.glBindAttribLocation(shapePId, 0, "in_pos");
        GL20.glBindAttribLocation(shapePId, 1, "in_color");
         
        GL20.glLinkProgram(shapePId);
        GL20.glValidateProgram(shapePId);
        
        //Load sprite shaders
        spriteVSId = loadShader("shaders/basicSprite.vs", GL20.GL_VERTEX_SHADER);
		spriteFSId = loadShader("shaders/basicSprite.fs", GL20.GL_FRAGMENT_SHADER);
		
		spritePId = GL20.glCreateProgram();
		GL20.glAttachShader(spritePId, spriteVSId);
		GL20.glAttachShader(spritePId, spriteFSId);
		
		GL20.glBindAttribLocation(spritePId, 0, "in_pos");
        GL20.glBindAttribLocation(spritePId, 1, "in_color");
        GL20.glBindAttribLocation(spritePId, 2, "in_texCoord");
         
        GL20.glLinkProgram(spritePId);
        GL20.glValidateProgram(spritePId);
	}
	
	private int loadShader(String fileName, int type){
		StringBuilder shaderSource = new StringBuilder();
        int shaderId = 0;
         
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Could not read file.");
            e.printStackTrace();
            System.exit(-1);
        }
         
        shaderId = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderId, shaderSource);
        GL20.glCompileShader(shaderId);
         
        if (GL20.glGetProgrami(shaderId, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.err.println("Could not compile shader.");
            System.exit(-1);
        }
        
        return shaderId;
	}
	
	public void destroy(){   
		//Delete shape batch objects
        GL20.glUseProgram(0);
        GL20.glDetachShader(shapePId, shapeVSId);
        GL20.glDetachShader(shapePId, shapeFSId);
         
        GL20.glDeleteShader(shapeVSId);
        GL20.glDeleteShader(shapeFSId);
        GL20.glDeleteProgram(shapePId);
         
        GL30.glBindVertexArray(shapeVAOId);
         
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
         
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glDeleteBuffers(shapeVBOId);
         
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL15.glDeleteBuffers(shapeVBOIId);
         
        GL30.glBindVertexArray(0);
        GL30.glDeleteVertexArrays(shapeVAOId);
        
        //Delete sprite batch objects
        GL20.glUseProgram(0);
        GL20.glDetachShader(spritePId, spriteVSId);
        GL20.glDetachShader(spritePId, spriteFSId);
         
        GL20.glDeleteShader(spriteVSId);
        GL20.glDeleteShader(spriteFSId);
        GL20.glDeleteProgram(spritePId);
         
        GL30.glBindVertexArray(spriteVAOId);
         
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
         
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glDeleteBuffers(spriteVBOId);
         
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL15.glDeleteBuffers(spriteVBOIId);
         
        GL30.glBindVertexArray(0);
        GL30.glDeleteVertexArrays(spriteVAOId);
	}
}
