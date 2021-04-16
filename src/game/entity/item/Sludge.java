package game.entity.item;

import game.entity.mob.Slime;
import game.graphics.Sprite;
import game.level.Level;

public class Sludge extends Item {

	public Sludge(int x, int y, Level level) {
		super(x, y, level);
		this.sprite = Sprite.slimepuddle;
	}

	public void update() {
		if (!inRange()) return;
		if ((Math.abs(level.getClientPlayer().x - x) < 64 && Math.abs(level.getClientPlayer().y - y) < 64))
			if (random.nextInt(200) == 0)
				level.add(new Slime(getX() + random.nextInt(16) - 8, getY() + random.nextInt(16) - 8, level));
	}
}