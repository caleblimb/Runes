package game.entity;

import java.awt.Rectangle;
import java.util.Random;

import game.Game;
import game.graphics.Screen;
import game.level.Level;

public class Entity {

	public double x, y, z;
	protected Level level;
	protected int range;
	private boolean removed = false;
	protected Rectangle collisionMask;
	protected int xOffset = 0, yOffset = 0;
	protected int totalFrames;
	protected int animationDelay;
	protected int animationTick = 0;
	protected int currentFrame = 0;
	protected final Random random = new Random();

	public int renderDepth() {
		return (int) (y - z);
	}

	public Entity(int x, int y, Level level) {
		this.x = x;
		this.y = y;
		init(level);
	}

	public void update() {
	}

	public void render(Screen screen) {
	}

	protected boolean inRange() {
		if (Math.abs(x - (Game.getxScroll() + 300)) < 600 && Math.abs(y - (Game.getyScroll() + 166)) < 332)
			return true;
		return false;
	}

	protected double distanceTo(Entity e) {
		double dx = x - e.x;
		double dy = y - e.y;
		return Math.sqrt(dx * dx + dy * dy);
	}

	public void remove() {
		// remove from Level
		removed = true;
	}

	public void updateCollisionMask() {
		collisionMask.x = (int) x - collisionMask.width / 2;
		collisionMask.y = (int) y - collisionMask.height / 2;
	}

	public boolean collisionWith(Entity e) {
		int cx = collisionMask.x - e.collisionMask.x;
		int cy = collisionMask.y - e.collisionMask.y;
		if (cx >= 0) {
			if (cx > e.collisionMask.width) return false;
			if (cy >= 0) {
				if (cy > e.collisionMask.height) return false;
			} else {
				if (cy < -collisionMask.height) return false;
			}
		} else {
			if (cx < -e.collisionMask.width) return false;
			if (cy >= 0) {
				if (cy > e.collisionMask.height) return false;
			} else {
				if (cy < -collisionMask.height) return false;
			}
		}
		return true;
	}

	public boolean isRemoved() {
		return removed;
	}

	public void init(Level level) {
		this.level = level;
	}

	public int getX() {
		return (int) x;
	}

	public int getY() {
		return (int) y;
	}

	public Level getLevel() {
		return level;
	}
}
