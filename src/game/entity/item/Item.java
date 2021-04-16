package game.entity.item;

import game.entity.Entity;
import game.graphics.Screen;
import game.graphics.Sprite;
import game.level.Level;

public class Item extends Entity {

	protected Sprite sprite;

	public Item(int x, int y, Level level) {
		super(x, y, level);
	}

	public void render(Screen screen) {
		screen.renderEntity((int) this.x, (int) this.y, sprite, 0);
	}
}
