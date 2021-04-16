package game.entity.particle;

import game.entity.Entity;
import game.entity.particle.Emitter.Type;
import game.graphics.Screen;
import game.graphics.Sprite;
import game.level.Level;

public class Particle extends Entity {
	private Sprite sprite;

	private int life;
	private int time = 0;

	protected double xa, ya, za;
	protected Type type;

	public int renderDepth() {
		return (int) (y - z - 12);
	}

	public static Particle colorParticle(int x, int y, Level level, Type type, int color, int life) {
		return new Particle(x, y, level, type, new Sprite(2, color), life);
	}

	public Particle(int x, int y, Level level, Type type, Sprite sprite, int life) {
		super(x, y, level);
		this.x = x;
		this.y = y;
		this.z = random.nextFloat() + 2.0;
		this.sprite = sprite;
		this.type = type;

		this.xa = random.nextGaussian();
		this.ya = random.nextGaussian();
		this.life = life + (random.nextInt(20) - 10);
		switch (type) {
		case WATER:
			this.xa *= .1;
			this.ya *= .1;
			break;
		case GROUND:
			this.xa *= .1;
			this.ya *= .1;
			this.life = life + (random.nextInt(256));
			break;
		case STONE:
			break;
		}
	}

	public void update() {
		time++;
		// if (time >= Integer.MAX_VALUE - 1) time = 0;
		if (time > life) remove();
		za -= 0.1;

		if (z < 0) {
			z = 0;
			za *= -0.5;
			xa *= 0.4;
			ya *= 0.4;
		}
		switch (type) {
		case WATER:
			break;
		case GROUND:
			break;
		case STONE:
			break;
		}
		move(x + xa, (y + ya) + (z + za));
	}

	private void move(double x, double y) {
		if (collision(x, y)) {
			this.xa *= -.5;
			this.ya *= -.5;
			this.za *= -.5;
		}
		this.x += xa;
		this.y += ya;
		this.z += za;

	}

	public boolean collision(double x, double y) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			double xt = (x - c % 2 * 16) / 16;
			double yt = (y - c / 2 * 16) / 16;
			int ix = (int) Math.ceil(xt);
			int iy = (int) Math.ceil(yt);
			if (c % 2 == 0) ix = (int) Math.floor(xt);
			if (c / 2 == 0) iy = (int) Math.floor(yt);
			if (level.getTile(ix, iy).solid()) solid = true;
		}
		return solid;
	}

	public void render(Screen screen) {
		screen.renderSprite((int) x - 1, (int) y - (int) z, sprite, true);
	}
}
