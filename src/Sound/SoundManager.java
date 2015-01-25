package Sound;
import java.io.IOException;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;


public class SoundManager {
	
	public static Audio walk;
	public static Audio pick_flower;
	public static Audio digging;
	public static Audio punch;
	public static Audio squelch;
	public static Audio spider;
	public static Audio stick_crack;
	public static Audio chant;
	
	public static void init(){
		try {
			walk = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("assets/Walk.wav"));
			pick_flower = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("assets/pick_flower.ogg"));
			digging = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("assets/digging.ogg"));
			punch = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("assets/punch.ogg"));
			squelch = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("assets/squelch.ogg"));
			spider = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("assets/spider.ogg"));
			stick_crack = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("assets/stick_crack.ogg"));
			chant = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("assets/chant.ogg"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("ERROR: Cannot load Audio file - " + e.getMessage());
		}
	}
	
	public static void playSound(Audio sound, float gain, boolean loop){
		sound.playAsSoundEffect(1, gain, loop);
	}
	
	public static void stopSound(Audio sound){
		sound.stop();
	}
	
	public static boolean isPlaying(Audio sound){
		return sound.isPlaying();
	}
	
}
