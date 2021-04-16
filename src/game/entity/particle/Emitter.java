package game.entity.particle;

import game.entity.Entity;
import game.graphics.Sprite;
import game.level.Level;

public class Emitter extends Entity {

	public enum Type {
		WATER, GROUND, STONE
	}

	public Emitter(int x, int y, Type type, Sprite sprite, int amount, int life, Level level) {
		super(x, y, level);
		for (int i = 0; i < amount; i++) {
			level.add(new Particle(x, y, level, type, sprite, life));
		}
		remove();
	}
	
	public Emitter(int x, int y, Type type, int color, int amount, int life, Level level) {
		super(x, y, level);
		for (int i = 0; i < amount; i++) {
			level.add(Particle.colorParticle(x, y, level, type, color, life));
		}
		remove();
	}
}
