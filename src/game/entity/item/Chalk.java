package game.entity.item;

import game.entity.mob.Ghost;
import game.graphics.Sprite;
import game.level.Level;

public class Chalk extends Item {

	public Chalk(int x, int y, Level level) {
		super(x, y, level);
		this.sprite = Sprite.chalk;
	}

	public void update() {
		if (!inRange()) return;
		if ((Math.abs(level.getClientPlayer().x - x) < 256 && Math.abs(level.getClientPlayer().y - y) < 256))
			if (random.nextInt(200) == 0)
				level.add(new Ghost(getX() + random.nextInt(16) - 8, getY() + random.nextInt(16) - 8, level));
	}
}