package game.entity.mob;

import java.awt.Rectangle;

import game.entity.particle.Emitter;
import game.entity.particle.Emitter.Type;
import game.entity.projectile.Projectile;
import game.graphics.Sprite;
import game.level.Level;

public class Skeleton extends Mob {

	public Skeleton(int x, int y, Level level) {
		super(x, y, level);
		animationDelay = 5;
		sprite = Sprite.skeleton[0];
		totalFrames = 8;
		collisionMask = new Rectangle(14, 14);
		xOffset = 16;
		yOffset = 22;
		if (distanceTo(level.getClientPlayer()) < 32) remove();
	}

	public void update() {
		updateCollisionMask();
		if (!inRange()) remove();
		if (distanceTo(level.getClientPlayer()) < 256) followAround(level.getClientPlayer());
		animate();
		sprite = Sprite.skeleton[currentFrame];
		for (Projectile p : level.getProjectiles()) {
			if (collisionWith(p)) {
				level.add(new Emitter((int) x, (int) y, Type.STONE, Sprite.particle_skeleton, 64, 56, level));
				p.explode();
				remove();
			}
		}
	}
}
