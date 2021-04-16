package game.entity.item.collectable;

import game.entity.item.Item;
import game.graphics.Screen;
import game.graphics.Sprite;
import game.level.Level;

public class Collectable extends Item {
	protected String name = "Tomato";
	protected Sprite sprite;
	protected int tred = 0;

	public Collectable(int x, int y, Level level) {
		super(x, y, level);
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return this.name;
	}

	public static void createFromString(String name, int x, int y, Level level) {
		switch (name) {
		case "GoldCoin":
			level.add(new GoldCoin(x, y, level));
			break;
		default:
			level.add(new Collectable(x, y, level));
			break;
		}
	}
	
	public void render(Screen screen) {
		screen.renderEntity((int) this.x, (int) this.y, this.sprite, this.tred);
	}

}
