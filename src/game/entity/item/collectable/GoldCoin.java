package game.entity.item.collectable;

import game.graphics.Sprite;
import game.level.Level;

public class GoldCoin extends Collectable {
	
	public GoldCoin(int x, int y, Level level) {
		super(x, y, level);
		name = "GoldCoin";
		sprite = Sprite.goldCoin;
		// TODO Auto-generated constructor stub
	}

}
