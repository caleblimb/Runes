package game.entity.mob;

import game.Game;
import game.entity.Entity;
import game.entity.item.collectable.Collectable;
import game.entity.particle.Emitter;
import game.entity.particle.Emitter.Type;
import game.graphics.Screen;
import game.graphics.Sprite;
import game.level.Level;
import game.level.Node;
import game.util.Vector2i;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Mob extends Entity {

	public enum Armor {
		STEEL;
	}

	protected Sprite sprite;

	protected int dir = 2;
	protected boolean moving = false;
	protected int tred = 0;
	protected int speed = 1;
	protected Sprite pSprite = Sprite.particle_normal;
	private List<Node> path = null;
	protected double xa = 0, ya = 0;
	protected Map<String, Integer> inventory = new HashMap<String, Integer>();

	public Mob(int x, int y, Level level) {
		super(x, y, level);
	}

	public void move(double xa, double ya) {
		if (xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			return;
		}
		if (xa > 0) dir = 1;
		if (xa < 0) dir = 3;
		if (ya > 0) dir = 2;
		if (ya < 0) dir = 0;

		while (xa != 0) {
			if (Math.abs(xa) > 1) {
				if (!tileCollision(abs(xa), (int) ya)) {
					this.x += abs(xa);
				}
				xa -= abs(xa);
			} else {
				if (!tileCollision(abs(xa), (int) ya)) {
					this.x += xa;
				}
				xa = 0;
			}
		}
		while (ya != 0) {
			if (Math.abs(ya) > 1) {
				if (!tileCollision((int) xa, abs(ya))) {
					this.y += abs(ya);
				}
				ya -= abs(ya);
			} else {
				if (!tileCollision((int) xa, abs(ya))) {
					this.y += ya;
				}
				ya = 0;
			}
		}
		footStep();
	}

	private int abs(double value) {
		if (value < 0) return -1;
		return 1;
	}

	public void update() {
	}

	protected boolean inRange() {
		if (Math.abs(x - (Game.getxScroll() + 300)) < 600 && Math.abs(y - (Game.getyScroll() + 166)) < 332)
			return true;
		return false;
	}

	// private boolean mobCollision(int xa, int ya) {
	// int x0 = (int) ((x + xa) - l), x1 = (int) ((x + xa) + r), y0 = (int) ((y
	// + ya) - t), y1 = (int) ((y + ya) + b);
	//
	// return false;
	// }

	private boolean tileCollision(int xa, int ya) {
		boolean solid = false;
		// for (int c = 0; c < 4; c++) {
		// whaaaaa? e. 75
		// int xt = ((x + xa) + c % 2 * 12 - 6) / 16;
		// int yt = ((y + ya) + c / 2 * 12 + 3) / 16;
		// if (level.getTile(xt, yt).solid()) solid = true;
		// }

		// int x0 = (int) (((x + xa) - l) / 16), x1 = (int) (((x + xa) + r) /
		// 16), y0 = (int) (((y + ya) - t) / 16), y1 = (int) (((y + ya) + b) /
		// 16);
		int x0 = (int) ((xa + collisionMask.x) / 16), x1 = (int) ((xa + collisionMask.x + collisionMask.width) / 16), y0 = (int) ((ya + collisionMask.y) / 16), y1 = (int) ((ya
				+ collisionMask.y + collisionMask.height) / 16);

		if (level.getTile(x0, y0).solid()) solid = true;
		if (level.getTile(x1, y0).solid()) solid = true;
		if (level.getTile(x0, y1).solid()) solid = true;
		if (level.getTile(x1, y1).solid()) solid = true;

		tred = (level.getTile(x1, y0).tred() + level.getTile(x1, y1).tred() + level.getTile(x0, y1).tred() + level.getTile(x0, y0).tred()) / 4;
		return solid;
	}

	protected void animate() {
		if (animationTick >= animationDelay) {
			currentFrame++;
			animationTick = 0;
		}
		if (currentFrame >= totalFrames) currentFrame = 0;
	}

	protected void footStep() {
		int color = level.getTile((int) x / 16, ((int) y + 16) / 16).sprite.pixels[random.nextInt(255)];
		if (random.nextInt(5) == 0)
			level.add(new Emitter((int) x + random.nextInt(18) - 9, (int) y + 12 + random.nextInt(2), Type.GROUND, color, 1, 256, level));
		/*
		 * if (level.getTile((int) x / 16, ((int) y + 16) / 16) instanceof
		 * GrassTile) {
		 * level.add(new Emitter((int) x + random.nextInt(18) - 9, (int) y + 12
		 * + random.nextInt(2), Type.GROUND, Sprite.particle_dirt, 1, -224,
		 * level));
		 * level.add(new Emitter((int) x + random.nextInt(18) - 9, (int) y + 12
		 * + random.nextInt(4), Type.GROUND, Sprite.particle_grass, 1, 256,
		 * level));
		 * }
		 * if (level.getTile((int) x / 16, ((int) y + 16) / 16) instanceof
		 * DirtTile) {
		 * level.add(new Emitter((int) x + random.nextInt(18) - 9, (int) y + 12
		 * + random.nextInt(4), Type.GROUND, Sprite.particle_dirt, 1, 256,
		 * level));
		 * }
		 * if (level.getTile((int) x / 16, ((int) y + 16) / 16) instanceof
		 * SandTile) {
		 * level.add(new Emitter((int) x + random.nextInt(18) - 9, (int) y + 12
		 * + random.nextInt(4), Type.GROUND, Sprite.particle_sand, 1, 256,
		 * level));
		 * }
		 * if (level.getTile((int) x / 16, ((int) y + 16) / 16) instanceof
		 * WaterTile) {
		 * pSprite = Sprite.particle_water;
		 * level.add(new Emitter((int) x + random.nextInt(16) - 8, (int) y + 12
		 * + random.nextInt(8) - tred, Type.WATER, pSprite, 1, 46, level));
		 * }
		 */
	}

	protected void followStrait(Entity e) {
		animationTick++;
		xa = 0;
		ya = 0;
		double dir = Math.atan2(e.y - y, e.x - x);
		xa = speed * Math.cos(dir);
		ya = speed * Math.sin(dir);
		if (xa != 0 || ya != 0) {
			move(xa, ya);
		}
	}

	protected void followAround(Entity e) {
		animationTick++;
		xa = 0;
		ya = 0;
		int px = level.getPlayerAt(0).getX();
		int py = level.getPlayerAt(0).getY();
		Vector2i start = new Vector2i(getX() >> 4, getY() >> 4);
		Vector2i destination = new Vector2i(px >> 4, py >> 4);
		if (Game.delay % 6 == 0) path = level.findPath(start, destination); // delay is to improve preformance
		if (path != null) {
			if (path.size() > 0) {
				Vector2i vec = path.get(path.size() - 1).tile;
				if (x < vec.getX() << 4) xa++;
				if (x > vec.getX() << 4) xa--;
				if (y < vec.getY() << 4) ya++;
				if (y > vec.getY() << 4) ya--;
			}
		}
		if (xa != 0 || ya != 0) {
			move(xa, ya);
		}
	}

	// Joe
	protected void pickUpCollectable(Collectable obj) {
		String name = obj.getName();
		int count = this.inventory.get(name);
		this.inventory.put(name, count + 1);
		obj.remove();
	}

	protected void dropInventoryItem(String item) {
		int count = this.inventory.get(item);
		if (count > 0) {
			this.inventory.put(item, count - 1);
			Collectable.createFromString(item, (int) this.x, (int) this.y, level);
		}

	}

	public void render(Screen screen) {
		screen.renderEntity((int) x - xOffset, (int) y - yOffset, sprite, tred);
	}
}