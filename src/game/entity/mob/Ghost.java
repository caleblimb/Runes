package game.entity.mob;

import java.awt.Rectangle;

import game.entity.particle.Emitter;
import game.entity.particle.Emitter.Type;
import game.entity.projectile.Projectile;
import game.graphics.Sprite;
import game.level.Level;
import game.sound.Sound;

public class Ghost extends Mob {

	public Ghost(int x, int y, Level level) {
		super(x, y, level);
		animationDelay = 2;
		sprite = Sprite.ghost[0];
		totalFrames = 8;
		speed = 3;
		collisionMask = new Rectangle(16, 15);
		xOffset = 16;
		yOffset = 16;
		if (distanceTo(level.getClientPlayer()) < 32) remove();
	}

	public void update() {
		updateCollisionMask();
		if (!inRange()) remove();
		// if (random.nextInt(60) == 0) {
		// Sound.play("res/audio/sound/squeak1.wav", 0.1);
		// }
		if (distanceTo(level.getClientPlayer()) < 256) followStrait(level.getClientPlayer());
		animate();
		sprite = Sprite.ghost[currentFrame];
		for (Projectile p : level.getProjectiles()) {
			if (collisionWith(p)) {
				level.add(new Emitter((int) x, (int) y, Type.STONE, Sprite.particle_ghost, 64, 56, level));
				p.explode();
				remove();
			}
		}
	}
}
