package game.entity.item;

import game.entity.mob.Spider;
import game.graphics.Sprite;
import game.level.Level;

public class Web extends Item {

	public Web(int x, int y, Level level) {
		super(x, y, level);
		this.sprite = Sprite.web;
	}

	public void update() {
		if (!inRange()) return;
		if ((Math.abs(level.getClientPlayer().x - x) < 64 && Math.abs(level.getClientPlayer().y - y) < 64))
			if (random.nextInt(200) == 0)
				level.add(new Spider(getX() + random.nextInt(16) - 8, getY() + random.nextInt(16) - 8, level));
	}
}