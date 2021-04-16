package game.menu.component;

import game.graphics.Screen;
import game.graphics.Text;

public class Label extends Component {

	String text;
	int spacing = 0;
	int color = 0;

	public Label(int x, int y, String text) {
		super(x, y, false);
		this.text = text;
	}

	public void setSpacing(int spacing) {
		this.spacing = spacing;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public void render(Screen screen) {
		Text.render(x, y, spacing, color, text, screen);
	}

}
