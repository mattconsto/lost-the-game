package deserted.graphics;

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

public class Renderer {
	
	//private int maxSprites;
	
	private float[] batch;
	private int[] ind;
	private int i = 0;
	private int numSprites = 0;
	//private int maxSprites = 0;
	
	private int vaoId, vboId, vboiId;
	
	//Shaders
	int vsId, fsId, pId;
	
	public Renderer(int maxSprites){
		//this.maxSprites = maxSprites;
		batch = new float[maxSprites*36];
		ind = new int[maxSprites*6];
		
		// Create a new Vertex Array Object in memory and select it (bind)
        vaoId = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoId);
         
        // Create a new Vertex Buffer Object in memory and select it (bind)
        vboId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
         
        GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, //Position
                false, 36, 0);
        GL20.glVertexAttribPointer(1, 4, GL11.GL_FLOAT, //Color
                false, 36, 16);
        GL20.glVertexAttribPointer(2, 2, GL11.GL_FLOAT, //Texture
                false, 36, 8);
         
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
         
        // Deselect (bind to 0) the VAO
        GL30.glBindVertexArray(0);
         
        // Create a new VBO for the indices and select it (bind) - INDICES
        vboiId = GL15.glGenBuffers();
        
        initShaders();
	}
	
	//Creates a new batch
	public void begin(Texture tex){
		if(tex != null)
			tex.bind();
		else{
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		}
		i = 0;
		numSprites = 0;
	}
	
	public void drawImage(float x, float y, float x2, float y2, int srcX, int srcY, int srcX2, int srcY2){
		//Top left
		batch[i++] = x;
		batch[i++] = y;
		batch[i++] = srcX;
		batch[i++] = srcY;
		batch[i++] = 1;
		batch[i++] = 1;
		batch[i++] = 1;
		batch[i++] = 1;
		
		//bottom left
		batch[i++] = x;
		batch[i++] = y2;
		batch[i++] = srcX;
		batch[i++] = srcY2;
		batch[i++] = 1;
		batch[i++] = 1;
		batch[i++] = 1;
		batch[i++] = 1;
		
		//bottom right
		batch[i++] = x2;
		batch[i++] = y2;
		batch[i++] = srcX2;
		batch[i++] = srcY2;
		batch[i++] = 1;
		batch[i++] = 1;
		batch[i++] = 1;
		batch[i++] = 1;
		
		//Top right
		batch[i++] = x2;
		batch[i++] = y;
		batch[i++] = srcX2;
		batch[i++] = srcY;
		batch[i++] = 1;
		batch[i++] = 1;
		batch[i++] = 1;
		batch[i++] = 1;
		
		int n = numSprites*6;
		ind[n] = n++;
		ind[n] = n++;
		ind[n] = n;
		ind[n+1] = n++;
		ind[n+1] = n;
		ind[n+2] = (n-3);
				
		numSprites++;
	}
	
	//Ends the current batch and sends it to the gpu
	public void end(){
		if(i%32 == 0){
			FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(i);
			
			for (int j = 0; j < i; j++) {
	            verticesBuffer.put(batch[j]);
	        }
	        verticesBuffer.flip();
	        
	        IntBuffer indicesBuffer = BufferUtils.createIntBuffer(ind.length);
	        indicesBuffer.put(ind);
	        indicesBuffer.flip();
	        
	        GL30.glBindVertexArray(vaoId);
	        
	        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
	        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
	        
	        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiId);
	        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
	        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
	        
	        GL20.glUseProgram(pId);
	        
	        GL30.glBindVertexArray(vaoId);
	        GL20.glEnableVertexAttribArray(0);
	        GL20.glEnableVertexAttribArray(1);
	        GL20.glEnableVertexAttribArray(2);
	         
	        // Bind to the index VBO that has all the information about the order of the vertices
	        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiId);
	         
	        // Draw the vertices
	        GL11.glDrawElements(GL11.GL_TRIANGLES, numSprites*6, GL11.GL_UNSIGNED_INT, 0);
	         
	        // Put everything back to default (deselect)
	        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
	        GL20.glDisableVertexAttribArray(0);
	        GL20.glDisableVertexAttribArray(1);
	        GL20.glDisableVertexAttribArray(2);
	        GL30.glBindVertexArray(0);
	         
	        GL20.glUseProgram(0);
	        
	        GL11.glEnable(GL11.GL_BLEND);
		}
	}
	
	private void initShaders(){
		vsId = loadShader("shaders/basic.vs", GL20.GL_VERTEX_SHADER);
		fsId = loadShader("shaders/basic.fs", GL20.GL_FRAGMENT_SHADER);
		
		pId = GL20.glCreateProgram();
		GL20.glAttachShader(pId, vsId);
		GL20.glAttachShader(pId, fsId);
		
		GL20.glBindAttribLocation(pId, 0, "in_pos");
        GL20.glBindAttribLocation(pId, 1, "in_color");
        GL20.glBindAttribLocation(pId, 2, "in_texCoord");
         
        GL20.glLinkProgram(pId);
        GL20.glValidateProgram(pId);
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
        GL20.glUseProgram(0);
        GL20.glDetachShader(pId, vsId);
        GL20.glDetachShader(pId, fsId);
         
        GL20.glDeleteShader(vsId);
        GL20.glDeleteShader(fsId);
        GL20.glDeleteProgram(pId);
         
        GL30.glBindVertexArray(vaoId);
         
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
         
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glDeleteBuffers(vboId);
         
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL15.glDeleteBuffers(vboiId);
         
        GL30.glBindVertexArray(0);
        GL30.glDeleteVertexArrays(vaoId);
	}

}
