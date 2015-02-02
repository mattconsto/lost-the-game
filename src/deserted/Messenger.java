package deserted;
import java.util.ArrayList;

import org.newdawn.slick.Color;

import deserted.engine.graphics.Graphics;


public class Messenger {
	
	private class Message{
		public String m;
		public Color c;
		public float length;
		public float elapsed;
		
		public Message(String message, Color color, float length){
			this.m = message;
			this.c = color;
			this.length = length;
			elapsed = 0;
		}
	}
	
	private ArrayList<Message> messages;
	
	public Messenger(){
		messages = new ArrayList<>();
	}
	
	public void addMessage(String message, Color color, float length){
		messages.add(new Message(message, color, length));
	}
	
	public void update(float delta){
		ArrayList<Message> remove = new ArrayList<>();
		for(Message m : messages){
			m.elapsed += delta;
			if(m.elapsed >= m.length)
				remove.add(m);
		}
		for(Message m : remove){
			messages.remove(m);
		}
	}
	
	public void render(Graphics g, int windowHeight){
//		for(Message m : messages){
//			g.setColor(m.c);
//			g.drawString(m.m, 8, windowHeight - 120 - messages.indexOf(m) * 25);
//		}
	}
}
