package game.menu;

import game.graphics.Screen;
import game.graphics.Sprite;
import game.menu.component.Button;
import game.menu.component.Panel;

public class MainMenu extends Menu {

	Panel buttons = new Panel(250, 0);

	public MainMenu(Sprite background) {
		super(background);
		tiles = new int[0];
		buttons.add(new Button(0, 100, true, Sprite.play, Sprite.playHover, Sprite.playPress));
	}

	public void update() {
		buttons.update();
	}

	public void render(int xScroll, int yScroll, Screen screen) {
		screen.setOffset(xScroll, yScroll);
		screen.renderSprite(0, 0, background, false);
		buttons.render(screen);
	}

}
