package game.entity.item;

import game.entity.mob.Skeleton;
import game.graphics.Sprite;
import game.level.Level;

public class Grave extends Item {

	public Grave(int x, int y, Level level) {
		super(x, y, level);
		this.sprite = Sprite.tombstone;
	}

	public void update() {
		if (!inRange()) return;
		if ((Math.abs(level.getClientPlayer().x - x) < 128 && Math.abs(level.getClientPlayer().y - y) < 128))
			if (random.nextInt(60) == 0)
				level.add(new Skeleton(getX() + random.nextInt(16) - 8, getY() + random.nextInt(16) - 8, level));
	}
}