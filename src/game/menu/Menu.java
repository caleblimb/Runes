package game.menu;

import game.graphics.Screen;
import game.graphics.Sprite;
import game.level.Level;
import game.menu.component.Button;

import java.util.ArrayList;
import java.util.List;

public class Menu extends Level {
	protected Sprite background;

	public Menu(Sprite background) {
		super(background.getWidth(), background.getHeight());
		this.background = background;
	}

	public void render(Screen screen) {
		screen.renderSprite(0, 0, background, false);
	}

	public void update() {
	}

}