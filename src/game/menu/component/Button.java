package game.menu.component;

import game.graphics.Sprite;
import game.input.Mouse;
import game.level.Level;

public class Button extends Component {

	private Sprite idle, hover, pressed;
	private boolean selected;

	public Button(int x, int y, boolean round, Sprite idle, Sprite hover, Sprite pressed) {
		super(x, y, round);
		this.width = idle.getWidth();
		this.height = idle.getHeight();
		this.sprite = idle;
		this.idle = idle;
		this.hover = hover;
		this.pressed = pressed;
	}

	public void update() {
		if (mouseOver() && Mouse.getButton() == 1) press();
		if (selected && Mouse.getButton() != 1) release();
		if (!selected) {
			if (mouseOver()) sprite = hover;
			else sprite = idle;
		}
	}

	protected void press() {
		selected = true;
		sprite = pressed;
	}

	protected void release() {
		selected = false;
		if (mouseOver()) Level.set("test");
	}
}
