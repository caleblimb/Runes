package game.level.tile.terrain.ground;

import game.graphics.Screen;
import game.graphics.Sprite;
import game.level.tile.Tile;

public class GroundTile extends Tile {

	public GroundTile(Sprite sprite) {
		super(sprite);
	}

	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);
	}

}
