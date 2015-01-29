package Graphics;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.PNGDecoder;

public class Texture {
	
	private int handle;
	private int width, height;
	
	public Texture(String fileName){
		ByteBuffer buf = null;
		try{
			InputStream in = new FileInputStream(fileName);
			PNGDecoder d = new PNGDecoder(in);
			
			width = d.getWidth();
		    height = d.getHeight();
		     
		    // Decode the PNG file in a ByteBuffer
		    buf = ByteBuffer.allocateDirect(4 * d.getWidth() * d.getHeight());
		    d.decode(buf, d.getWidth() * 4, d.RGBA);
		    buf.flip();
		     
		    in.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		handle = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, handle);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
	}
	
	public void bind(){
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, handle);
		GL13.glActiveTexture(handle);
	}
	
	public void destroy(){
		GL11.glDeleteTextures(handle);
	}
	
	public int getWidth(){
		return width;
	}
	
}
