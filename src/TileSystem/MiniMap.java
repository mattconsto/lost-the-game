package TileSystem;

import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import Player.PlayerUI;

public class MiniMap {
	
	TileSystem ts;
	List<PlayerUI> players;
	
	public MiniMap(TileSystem ts, List<PlayerUI> players){
		this.ts = ts;
		this.players = players;
	}
	
	public void render(Graphics g){
		g.setColor(Color.gray.darker());
		g.fillRect(5, 55, ts.size, ts.size);
		for(int x = 0; x < ts.size; x += 2){
			for(int y = 0; y < ts.size; y += 2){
				if(ts.getTile(x, y).vis > 0){
					switch(ts.getTile(x, y).id){
						case WATER:
							g.setColor(Color.blue);
							break;
						case DIRT:
							g.setColor(Color.orange);
							break;
						case GRASS:
							g.setColor(Color.green);
							break;
						case SNOW:
							g.setColor(Color.lightGray);
							break;
						default:
							g.setColor(Color.orange);
							break;
					}
					g.fillRect(x+5, y+55, 2, 2);
				}
			}
		}
		g.setColor(Color.red);
		for(PlayerUI p : players){
			g.fillRect(p.location.x+4, p.location.y+54, 3, 3);
		}
		int x = (int)(ts.camera.x - (ts.camera.windowSize.getX()/ts.resTimesScale)/2);
		int y = (int)(ts.camera.y - (ts.camera.windowSize.getY()/ts.resTimesScale)/2);
		g.drawRect(x+5, y+55, ts.camera.windowSize.getX()/ts.resTimesScale, ts.camera.windowSize.getY()/ts.resTimesScale);
	}
	
	public void goTo(int mouseX, int mouseY){
		ts.camera.x = (mouseX - 5);
		ts.camera.y = (mouseY - 55);
		ts.camera.isFollowing = false;
	}
	
	public boolean isWithin(int mouseX, int mouseY){
		if(mouseX > 5 && mouseX < ts.size+5 && mouseY > 55 && mouseY < ts.size+55)
			return true;
		return false;
	}
	
}
