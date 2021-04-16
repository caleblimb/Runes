package game.menu.component;

import game.graphics.Sprite;

public class Image extends Component {

	public Image(int x, int y, Sprite sprite) {
		super(x, y, false);
		this.sprite = sprite;
	}

}