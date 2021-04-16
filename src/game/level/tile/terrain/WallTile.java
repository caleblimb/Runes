package game.level.tile.terrain;

import game.graphics.Screen;
import game.graphics.Sprite;
import game.level.tile.Tile;

public class WallTile extends Tile {
	//protected Sprite back;

	public WallTile(Sprite sprite, Sprite back) {
		super(sprite);
		//this.back = back;
	}

	public void render(int x, int y, Screen screen) {
		//screen.renderTile(x << 4, y << 4, this, back); was
		screen.renderTile(x << 4, y << 4, this);
	}

	public boolean solid() {
		return true;
	}
}