package game.level.tile.terrain;

import game.graphics.Screen;
import game.graphics.Sprite;
import game.level.tile.Tile;

public class WaterTile extends Tile {

	private int tred;

	public WaterTile(Sprite sprite, int tred) {
		super(sprite);
		this.tred = tred;
	}

	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);
	}

	public void animate() {
		if (sprite == Sprite.water0) sprite = Sprite.water1;
		else sprite = Sprite.water0;
	}

	public int tred() {
		return tred;
	}
}