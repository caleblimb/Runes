package game.level.tile.terrain.ground;

import game.graphics.Screen;
import game.graphics.Sprite;
import game.level.tile.Tile;

public class PathTile extends Tile {

	public PathTile(Sprite sprite) {
		super(sprite);
	}

	public void render(int x, int y, Screen screen) {
		int rotation = (x + y) % 4;
		screen.renderTile(x << 4, y << 4, this, rotation);
	}

}
