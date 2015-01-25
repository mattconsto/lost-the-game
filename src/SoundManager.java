import java.io.IOException;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;


public class SoundManager {
	
	public static Audio walk;
	
	public static void init(){
		try {
			walk = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("assets/walk.wav"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("ERROR: Cannot load Audio file - " + e.getMessage());
		}
	}
	
	public static void playSound(Audio sound){
		sound.playAsSoundEffect(1, 1, true);
	}
	
	public static void stopSound(Audio sound){
		sound.stop();
	}
	
	public static boolean isPlaying(Audio sound){
		return sound.isPlaying();
	}
	
}
