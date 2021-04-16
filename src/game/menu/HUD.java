package game.menu;

import game.Game;
import game.graphics.Screen;
import game.graphics.Sprite;
import game.graphics.Text;
import game.level.Level;
import game.level.tile.*;
import game.level.tile.terrain.ground.*;
import game.menu.component.*;

import java.util.ArrayList;
import java.util.List;

public class HUD {
	protected Panel mapPanel = new Panel(Game.getGameWidth() - 72, 8);
	protected Sprite background;
	protected Sprite mapSprite;
	protected Sprite stamina = new Sprite("/graphics/healthBar.png");
	protected Sprite staminaContainer = new Sprite("/graphics/healthBarContainer.png");
	protected Sprite mapContainer = new Sprite("/graphics/mapContainer.png");
	protected int frame = 0;
	protected int delay = 0;
	protected int miniMapWidth;
	protected int miniMapHeight;// width, height
	protected Level level;

	public HUD(Level level) {
		background = Sprite.gameMenuBackground;
		this.level = level;
		miniMapWidth = level.getWidth();
		miniMapHeight = level.getHeight();
		int[] colors = createMinimap(level, miniMapWidth, miniMapHeight);
		mapSprite = new Sprite(colors, miniMapWidth, miniMapHeight);
		
		mapPanel.add(new Image(-5, -5, mapContainer));
		mapPanel.add(new Map(0, 0, 64, 64, new Sprite(colors, miniMapWidth, miniMapHeight)));
	}

	public void update() {
		mapPanel.update();
		delay++;
		if (delay > 3) {
			delay = 0;
			frame++;
		}
		if (frame > 18) frame = 0;
		background = Sprite.flippingStone[frame];
	}

	public void render(Screen screen) {	
		mapPanel.render(screen);

		//stamina
		screen.renderSprite(Game.getGameWidth() / 2 - stamina.WIDTH / 2, Game.getGameHeight() - stamina.HEIGHT, 0, 0, stamina.WIDTH
				- (stamina.WIDTH - (int) ((double) stamina.WIDTH * (level.getClientPlayer().getStamina() / 100))), stamina.HEIGHT, stamina, false);
		screen.renderSprite(Game.getGameWidth() / 2 - staminaContainer.WIDTH / 2, Game.getGameHeight()
				- staminaContainer.HEIGHT, staminaContainer, false);
	}

	public int[] createMinimap(Level level, int width, int height) {
		int[] colors = new int[width * height];

		for (int i = 0; i < width * height; i++) {
			
			colors[i] = level.getTile(i).sprite.pixels[(16*16)/2];
		}

		//for (int i = 0; i < width * height; i++) {
		//	if (level.getTile(i) instanceof VoidTile) {
		//		colors[i] = 0x000000;
		//	} else if (level.getTile(i) instanceof GrassTile) {
		//		colors[i] = 0x8dc435;
		//	} else if (level.getTile(i) instanceof FlowerTile) {
		//		colors[i] = 0xb48355;
		//	}
		//}

		return colors;
	}
}