package game.entity.projectile;

import java.awt.Rectangle;
import java.util.Random;

import game.Game;
import game.entity.Entity;
import game.entity.mob.Mob;
import game.entity.particle.Emitter;
import game.graphics.Screen;
import game.graphics.Sprite;
import game.level.Level;

public class Projectile extends Entity {

	protected final int xOrigin, yOrigin;
	protected double angle;
	protected Sprite sprite;
	protected Sprite trail[];
	protected Rectangle trailSpread;
	protected Sprite explosion[];
	protected double nx, ny;// new x, new y
	protected double distance;
	protected double speed, range, damage;
	protected double friction;
	protected double minSpeed = 1;
	protected final Random random = new Random();

	public enum Type {
		FIREBALL, ARROW;
	}

	public Projectile(int x, int y, Level level, double dir) {
		super(x, y, level);
		xOrigin = x;
		yOrigin = y;
		angle = dir;
	}

	public Sprite getSprite() {
		return sprite;
	}

	// public int getSpriteSize() {
	// return sprite.SIZE;
	// }

	public int getSpriteWidth() {
		return sprite.WIDTH;
	}

	public int getSpriteHeight() {
		return sprite.HEIGHT;
	}

	public void explode() {
		if (explosion == null) return;
		for (int i = 0; i < explosion.length; i++) {
			new Emitter((int) x, (int) y, Emitter.Type.STONE, explosion[i], 64, 56, level);
		}
		remove();
	}

	protected void trail(double x, double y) {
		if (trail == null) return;
		for (int i = 0; i < trail.length; i++) {
			new Emitter((int) x + random.nextInt(trailSpread.width >> 1) - (trailSpread.width >> 2), (int) y
					+ random.nextInt(trailSpread.height >> 1) - (trailSpread.height >> 2), Emitter.Type.WATER, trail[i], 1, 8, level);
		}
	}

	protected void friction() {
		nx *= friction;
		ny *= friction;
	}

	public static void shoot(Mob m, Type type, double dir) {
		Projectile p = null;
		switch (type) {
		case FIREBALL:
			p = new FireBall(m.getX(), m.getY(), m.getLevel(), dir);
			break;
		case ARROW:
			p = new Arrow(m.getX(), m.getY(), m.getLevel(), dir);
			break;
		default:
			return;
		}
		m.getLevel().add(p);
	}

	protected void move() {
		x += nx;
		y += ny;
		if (Math.abs(nx) < minSpeed && Math.abs(ny) < minSpeed) remove();
	}

	protected boolean inRange() {
		if (Math.abs(x - (Game.getxScroll() + 300)) < 600 && Math.abs(y - (Game.getyScroll() + 166)) < 332)
			return true;
		return false;
	}

	public void render(Screen screen) {
		screen.renderProjectile((int) x - xOffset, (int) y - yOffset, this);
	}

}
