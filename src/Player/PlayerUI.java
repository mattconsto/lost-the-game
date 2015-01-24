package Player;
import java.util.Vector;

import org.newdawn.slick.Graphics;

import TileSystem.TileSystem;


public class PlayerUI {

	public PlayerUI(TileSystem ts) {
	}
	
	public void moveto(int x, int y){
	
	}
	
	public void render(Graphics g){
		
	}
	
	public void update(int deltaTime) {
		
	}
	
    protected Vector<PlayerReachedDestinationEvent> _listeners;
     
    public void addReachedDestinationEvent(PlayerReachedDestinationEvent listener)
    {
        if (_listeners == null)
            _listeners = new Vector<PlayerReachedDestinationEvent>();
             
        _listeners.addElement(listener);
    }
}
