import java.util.Vector;


public class PlayerUI {

	public PlayerUI(String name) {
	}
	
	public void Moveto(int x, int y){
	
	}
	
	public void Render(){
		
	}
	
	public void Update(int deltaTime) {
		
	}
	
    protected Vector<PlayerReachedDestinationEvent> _listeners;
     
    public void addReachedDestinationEvent(PlayerReachedDestinationEvent listener)
    {
        if (_listeners == null)
            _listeners = new Vector<PlayerReachedDestinationEvent>();
             
        _listeners.addElement(listener);
    }
}
