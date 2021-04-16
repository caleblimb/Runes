package game.entity.projectile;

import java.awt.Rectangle;

import game.graphics.Sprite;
import game.level.Level;

public class FireBall extends Projectile {

	public FireBall(int x, int y, Level level, double dir) {
		super(x, y, level, dir);
		range = 300;
		speed = 7;
		damage = 20;
		friction = .97;
		sprite = Sprite.projectile_wizard;
		trail = new Sprite[] { Sprite.particle_fire0, Sprite.particle_fire1 };
		trailSpread = new Rectangle(8, 8);
		explosion = new Sprite[] { Sprite.particle_fire0, Sprite.particle_fire1 };
		minSpeed = .3;
		collisionMask = new Rectangle(16, 16);
		xOffset = 8;
		yOffset = 8;

		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}

	public void update() {
		updateCollisionMask();
		if (!inRange()) return;
		if (level.tileCollision((int) (x + nx), (int) (y + ny), 8)) {
			explode();
		}
		trail(x, y);
		friction();
		move();
	}

}
