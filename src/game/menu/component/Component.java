package game.menu.component;

import game.graphics.Screen;
import game.graphics.Sprite;
import game.input.Mouse;
import game.util.Vector2i;

import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

public class Component {
	public int x, y;
	public int xOffset, yOffset;
	public int width, height;
	public Vector2i position;
	protected Sprite sprite;
	protected boolean round;
	protected boolean mouseUpdate = false;

	public Component(int x, int y, boolean round) {
		this.x = x;
		this.y = y;
		this.round = round;
	}

	public void addOffset(int x, int y) {
		this.x += x;
		this.y += y;
	}

	public void update() {
	}

	protected void mouseUpdate() {
		if (mouseUpdate) return;
	}

	protected boolean mouseOver() {
		if (round) {
			Ellipse2D.Double bounds = new Ellipse2D.Double(x, y, width, height);
			if (bounds.contains(Mouse.getPositionInGame())) return true;
		} else {
			Rectangle bounds = new Rectangle(x, y, width, height);
			if (bounds.contains(Mouse.getPositionInGame())) return true;
		}
		return false;
	}

	public void render(Screen screen) {
		screen.renderSprite(x, y, sprite, false);
	}

}
