package game.menu.component;

import game.Game;
import game.graphics.Screen;
import game.graphics.Sprite;

public class Map extends Component {

	private int xScroll, yScroll;

	public Map(int x, int y, int width, int height, Sprite sprite) {
		super(x, y, false);
		this.sprite = sprite;
		this.width = width;
		this.height = height;
	}

	public void update() {
		setScroll((Game.xScroll >> 4) - (width >> 2), (Game.yScroll >> 4) - (height >> 2));
	}

	public void setScroll(int x, int y) {
		xScroll = x;
		yScroll = y;
		if (xScroll < 0) xScroll = 0;
		if (yScroll < 0) yScroll = 0;
		if (xScroll > sprite.WIDTH - width) xScroll = sprite.WIDTH - width;
		if (yScroll > sprite.HEIGHT - height) yScroll = sprite.HEIGHT - height;
	}

	public void render(Screen screen) {
		screen.renderSprite(Game.getGameWidth() - (width + 8), 8, xScroll, yScroll, xScroll + width, yScroll + height, sprite, false);
	}
}
