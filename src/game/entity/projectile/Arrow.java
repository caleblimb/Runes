package game.entity.projectile;

import java.awt.Rectangle;

import game.graphics.Sprite;
import game.level.Level;

public class Arrow extends Projectile {

	public Arrow(int x, int y, Level level, double dir) {
		super(x, y, level, dir);
		range = 300;
		speed = 8;
		damage = 20;
		friction = .97;
		sprite = Sprite.rotate(Sprite.projectile_arrow, angle);
		trail = new Sprite[] { new Sprite(1, 0xffffff) };
		trailSpread = new Rectangle(2, 2);
		explosion = new Sprite[] { Sprite.particle_fire0, Sprite.particle_fire1 };
		minSpeed = .1;
		collisionMask = new Rectangle(16, 16);
		xOffset = 16 + (int) (16 * Math.cos(angle));
		yOffset = 16 + (int) (16 * Math.sin(angle));
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}

	public void update() {
		updateCollisionMask();
		if (!inRange()) return;
		if (level.tileCollision((int) (x + nx), (int) (y + ny), 8)) {
			explode();
		}
		trail(x + 32 - xOffset * 2, y + 32 - yOffset * 2);
		friction();
		move();
	}

}
