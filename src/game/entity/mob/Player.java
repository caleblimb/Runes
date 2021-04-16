package game.entity.mob;

import game.entity.projectile.Projectile;
import game.graphics.Screen;
import game.graphics.Sprite;
import game.input.Keyboard;
import game.input.Mouse;
import game.level.Level;

import java.awt.Rectangle;

public class Player extends Mob {

	private Keyboard input;
	private Sprite sprite;
	private Sprite armor;
	private double stamina = 100;
	private int reload = 0;

	Projectile p;

	public Player(int x, int y, Level level, Keyboard input) {
		super(x, y, level);
		this.input = input;
		animationDelay = 3;
		sprite = Sprite.player_down[0];
		armor = Sprite.armor_down;
		totalFrames = 8;
		// speed = 5;
		collisionMask = new Rectangle(15, 15);
		xOffset = 16;
		yOffset = 23;
	}

	public void update() {
		updateCollisionMask();
		int xa = 0, ya = 0;
		if (input.shift) speed = 2;
		else speed = 1;
		if (input.up) ya -= speed;
		if (input.down) ya += speed;
		if (input.left) xa -= speed;
		if (input.right) xa += speed;
		if (Mouse.getButton() == 1) {
		}
		if (xa != 0 || ya != 0) {
			move(xa, ya);
			animationTick++;
		}
		updateShooting();
		animate();
		if (dir == 0) {
			sprite = Sprite.player_up[currentFrame];
			armor = Sprite.armor_up;
		}
		if (dir == 1) {
			sprite = Sprite.player_right[currentFrame];
			armor = Sprite.armor_right;
		}
		if (dir == 2) {
			sprite = Sprite.player_down[currentFrame];
			armor = Sprite.armor_down;
		}
		if (dir == 3) {
			sprite = Sprite.player_left[currentFrame];
			armor = Sprite.armor_left;
		}
	}

	public double getStamina() {
		return stamina;
	}

	private void updateShooting() {
		if (Mouse.getButton() == 1) {
			double dir = Math.atan2(Mouse.getPositionInLevel().y - y, Mouse.getPositionInLevel().x - x);
			if (reload == 0) {
				if (stamina > 5) {
					Projectile.shoot(this, Projectile.Type.FIREBALL, dir);
					stamina -= 5;
					reload += 10;
				}
			}
		}
		if (Mouse.getButton() == 3) {
			double dir = Math.atan2(Mouse.getPositionInLevel().y - y, Mouse.getPositionInLevel().x - x);
			if (reload == 0) {
				if (stamina > 5) {
					Projectile.shoot(this, Projectile.Type.ARROW, dir);
					stamina -= 2;
					reload += 10;
				}
			}
		}
		if (reload > 0) reload--;
		if (stamina < 100) {
			stamina += .05;
		}
	}

	public void render(Screen screen) {
		screen.renderEntity((int) x - xOffset, (int) y - yOffset, sprite, tred);
		//screen.renderEntity((int) x - (sprite.WIDTH >> 1), (int) y - (sprite.HEIGHT >> 1), armor, tred);
	}

}
