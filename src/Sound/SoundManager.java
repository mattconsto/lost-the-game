package Sound;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	private static SoundManager  instance = null;
	private static MusicAdapter  adapter  = new SoundManager.MusicAdapter();
	
	protected Map<String, Sound> sounds   = new HashMap<String, Sound>();
	protected List<Music>        music    = new ArrayList<Music>();
	
	protected boolean playing = false;
	protected boolean shuffle = false;
	protected int     track   = -1;
	
	/**
	 * Create a new sound manager
	 * @param playing Do we want background music to start?
	 */
	private SoundManager(boolean playing) {
	    System.out.format("SoundManager\tInstance Created\n");
		
	    try {
	    	sounds.clear();
	    	
	        for (File file : new File(getClass().getResource("/sounds").toURI()).listFiles()) {
	            if (file.isFile() && file.canRead()) {
	            	try {
	                    sounds.put(file.getName().substring(0, file.getName().lastIndexOf(".")).toLowerCase(), new Sound(file.getPath()));
	                } catch (SlickException e) {
	            	    System.err.format("SoundManager\tError loading: %s\n", file.getName());
	                }
	            }
	        }
        } catch (URISyntaxException e) {
        	System.err.println(e.getMessage());
        }
	    System.out.format("SoundManager\t%d sound(s) loaded.\n", sounds.size());

	    try {
	    	music.clear();
	    	
	        for (File file : new File(getClass().getResource("/music").toURI()).listFiles()) {
	            if (file.isFile() && file.canRead()) {
	            	try {
	                    music.add(new Music(file.getPath()));
	                } catch (SlickException e) {
	            	    System.err.format("SoundManager\tError loading: %s\n", file.getName());
	                }
	            }
	        }
        } catch (URISyntaxException e) {
        	System.err.println(e.getMessage());
        }
	    System.out.format("SoundManager\t%d music track(s) loaded.\n", music.size());
	    
	    
	    // If we have music, lets start playing it.
	    if(music.size() > 0) {
	    	this.playing = playing;
	    	
	    	if(playing) {
	    		track = new Random().nextInt(music.size());
        	    System.out.format("SoundManager\tNow playing track %d.\n", track);
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
	public static boolean isPlaying() {
		return instance != null && instance.playing;
	}
	
	/**
	 * Sets the background music shuffling.
	 * @param shuffling DO we want to shuffle?
	 */
	public static void setShuffling(boolean shuffling) {
		if(instance == null) getInstance(false);
		
		instance.shuffle = shuffling;
	}

	/**
	 * Get if the background music is shuffling
	 * @return True if it is shuffling
	 */
	public static boolean isShuffling() {
		return instance != null && instance.shuffle;
	}
	

	/**
	 * Triggered when the Music ends, plays the next song
	 */
    public static void nextMusic() {
    	instance.track = (instance.shuffle) ? new Random().nextInt(instance.music.size()) : instance.track + 1;
    	
    	if(instance.track >= instance.music.size()) instance.track = 0;
    	
    	if(instance.playing) {
        	instance.music.get(instance.track).play();
        	instance.music.get(instance.track).addListener(adapter);
    	}
    }
    
    /**
     * MusicAdapter takes the event and passes it to our singleton.
     * @author Matthew
     */
    private static class MusicAdapter implements MusicListener {
        public void musicEnded  (Music ended)            {SoundManager.nextMusic();}
        public void musicSwapped(Music arg0, Music arg1) {}
    }
}
