import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;
import org.lwjgl.openal.*;
import org.newdawn.slick.openal.*;
import org.newdawn.slick.util.ResourceLoader;

public class VanishedSound {
	/**<NEWLINE>
	 * Loads the various sound effect libraries
	 */
//	private Audio oggSoundEffect;
//	private Audio wavJumpSoundEffect;
	private Audio wavPickUpSoundEffect;
	private Audio wavWalkSoundEffect;
//	private Audio aifSoundEffect;
//	private Audio oggStream;
//	private Audio modStream;


	/**Start the test**/
public void start(){
	initGL(800,600);
	init();
	
	while(true){
		update();
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		render();
		
		Display.update();
		Display.sync(100);
		
		if(Display.isCloseRequested()){
			Display.destroy();
			AL.destroy();
			System.exit(0);
		}
	}
}

/**
*Initialize the GL Display**parameters width and height of the display
**/
private void initGL(int width, int height){
	try{
		Display.setDisplayMode(new DisplayMode(width, height));
		Display.create();
		Display.setVSyncEnabled(true);
	}catch(LWJGLException e){
		e.printStackTrace();
		System.exit(0);
	}
	GL11.glEnable(GL11.GL_TEXTURE_2D);
	GL11.glShadeModel(GL11.GL_SMOOTH);
	GL11.glDisable(GL11.GL_DEPTH_TEST);
	GL11.glDisable(GL11.GL_LIGHTING);
	
	GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
GL11.glClearDepth(1);
GL11.glEnable(GL11.GL_BLEND);
GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

GL11.glViewport(0,0,width,height);
	GL11.glMatrixMode(GL11.GL_MODELVIEW);
	GL11.glMatrixMode(GL11.GL_PROJECTION);
	GL11.glLoadIdentity();
	GL11.glOrtho(0, width, height, 0, 1, -1);
	GL11.glMatrixMode(GL11.GL_MODELVIEW);
}

/**Initialize Resources **
 
 */
public void init(){
	try{
		//play oggs by loading into sound
		wavWalkSoundEffect= AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("/home/j/gamejam2/assets//home/j/Music/Walk.wav"));
		
		//setting up OGG stream
		oggStream= AudioLoader.getStreamingAudio("OGG", ResourceLoader.getResource("/home/j/gamejam2/assets//home/j/Music/J  Cole - Revenge Of The Dreamers Lyrics HD-ihG_PPhOvl0.ogg") );
		
		//play wav sound effect
		wavPickUpSoundEffect = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("/home/j/gamejam2/assets//home/j/Music/PickUp.wav"));
		
	}catch(IOException e){
		e.printStackTrace();
		
	}
}
//**Game Loop Update **/


	public void update() {
	while (Keyboard.next()) {
		if (Keyboard.getEventKeyState()) {
			if (Keyboard.getEventKey() == Keyboard.KEY_UP) {
				// play as a one off sound effect
				wavWalkSoundEffect.playAsSoundEffect(1.0f, 1.0f, false);
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
				// play as a one off sound effect
				wavWalkSoundEffect.playAsSoundEffect(1.0f, 1.0f, false);
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_LEFT) {
				// play as a one off sound effect
				wavWalkSoundEffect.playAsSoundEffect(1.0f, 1.0f, false);
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT) {
				// play as a one off sound effect
				wavWalkSoundEffect.playAsSoundEffect(1.0f, 1.0f, false);
			}
		}
	}

	// polling is required to allow streaming to get a chance to
	// queue buffers.
	SoundStore.get().poll(0);
	
}
	
	/**Game loop Render**
	 * 
	 */
	public void render(){
		
	}

}







