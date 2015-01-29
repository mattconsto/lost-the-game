package Sound;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.newdawn.slick.Music;
import org.newdawn.slick.MusicListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/**
 * Singleton that controls the music and sounds of our game.
 * 
 * @author Matthew
 */
public class SoundManager {
	private static SoundManager instance = null;
	private static MusicAdapter adapter  = new SoundManager.MusicAdapter();
	
	protected HashMap<String, Sound> sounds;
	protected ArrayList<Music>       music;
	
	protected boolean playing = false;
	protected int     track   = -1;
	
	/**
	 * Create a new sound manager
	 * @param playing Do we want background music to start?
	 */
	public SoundManager(boolean playing) {
	    try {
	    	sounds = new HashMap<String, Sound>();
	    	
	        for (File file : new File(getClass().getResource("/sounds").toURI()).listFiles()) {
	            if (file.isFile() && file.canRead()) {
	            	try {
	                    sounds.put(file.getName().substring(0, file.getName().lastIndexOf(".")).toLowerCase(), new Sound(file.getPath()));
	                } catch (SlickException e) {
	                	System.err.println(e.getMessage());
	                }
	            }
	        }
        } catch (URISyntaxException e) {
        	System.err.println(e.getMessage());
        }

	    try {
	    	music = new ArrayList<Music>();
	    	
	        for (File file : new File(getClass().getResource("/music").toURI()).listFiles()) {
	            if (file.isFile() && file.canRead()) {
	            	try {
	                    music.add(new Music(file.getPath()));
	                } catch (SlickException e) {
	                	System.err.println(e.getMessage());
	                }
	            }
	        }
        } catch (URISyntaxException e) {
        	System.err.println(e.getMessage());
        }
	    
	    
	    // If we have music, lets start playing it.
	    if(music.size() > 0) {
	    	this.playing = playing;
	    	
	    	if(playing) {
	    		track = new Random().nextInt(music.size());
	    		music.get(track).play();
	    		music.get(track).addListener(adapter);
	    	}
	    }
	}
	
	public static SoundManager getInstance(boolean playing) {
		if(instance == null) instance = new SoundManager(playing);
		return instance;
	}
	
	/**
	 * Attempt to play the specified sound effect at full volume and default pitch.
	 * @param name Name of the sound effect
	 */
	public static void playSound(String name) {
		playSound(name, 1, 1);
	}
	
	/**
	 * Attempt to play the specified sound effect
	 * @param name Name of the sound effect
	 * @param volume Volume
	 * @param pitch Pitch
	 */
	public static void playSound(String name, float volume, float pitch) {
		if(instance == null) getInstance(false);
		
		if(instance.sounds.containsKey(name.toLowerCase())) {
			instance.sounds.get(name.toLowerCase()).play(pitch, volume);
		}
	}
	
	/**
	 * Set whether or not we want the background music playing
	 * @param playing Do we want it playing?
	 */
	public static void setPlaying(boolean playing) {
		if(instance == null) getInstance(false);
		
		instance.playing = playing;
		
		if(instance.music.size() > 0) {
    		if(playing) {
    			if(!instance.music.get(instance.track).playing()) instance.music.get(instance.track).play();
    		} else {
    			instance.music.get(instance.track).pause();
    		}
		}
	}
	
	/**
	 * Get if the background music is playing
	 * @return True if it is playing
	 */
	public static boolean getPlaying() {
		return instance != null && instance.playing;
	}

	/**
	 * Triggered when the Music ends, plays the next song
	 * @param What just ended.
	 */
    public static void musicEnded(Music ended) {
    	// Don't really want other functions calling this!
    	if(instance.music.size() > 0 && ended == instance.music.get(instance.track)) {
    		instance.track = new Random().nextInt(instance.music.size());
        	instance.music.get(instance.track).play();
        	instance.music.get(instance.track).addListener(adapter);
    	}
    }
    
    /**
     * MusicAdapter takes the event and passes it to our singleton.
     * @author Matthew
     */
    private static class MusicAdapter implements MusicListener {
        public void musicEnded  (Music ended)            {SoundManager.musicEnded(ended);}
        public void musicSwapped(Music arg0, Music arg1) {}
    }
}
