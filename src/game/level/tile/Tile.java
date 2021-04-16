package game.level.tile;

import game.graphics.Screen;
import game.graphics.Sprite;
import game.level.tile.terrain.WallTile;
import game.level.tile.terrain.WaterTile;
import game.level.tile.terrain.ground.GroundTile;

import java.util.Random;

public class Tile {

	public int x, y;
	public Sprite sprite;
	protected Random random = new Random();

	public static final Tile[] tile = generateTiles();
	public static final Tile voidTile = new VoidTile(Sprite.voidSprite);

	public Tile(Sprite sprite) {
		this.sprite = sprite;
	}

	public static Tile[] generateTiles() {
		Tile[] t = new Tile[1000];
		t[0] = new WaterTile(Sprite.tile[0], 8);
		t[1] = new WaterTile(Sprite.tile[1], 5);
		for (int i = 2; i <= 7; i++) {
			t[i] = new WaterTile(Sprite.tile[i], 1);
		}
		t[8] = new WaterTile(Sprite.tile[8], 3);
		for (int i = 9; i <= 29; i++) {
			t[i] = new WaterTile(Sprite.tile[i], 1);
		}
		for (int i = 30; i <= 759; i++) {
			t[i] = new GroundTile(Sprite.tile[i]);
		}
		for (int i = 760; i <= 819; i++) {
			t[i] = new WallTile(Sprite.tile[i], null);
		}		

		return t;
	}

	public void render(int x, int y, Screen screen) {
	};

	public boolean solid() {
		return false;
	}

	public int tred() {
		return 0;
	}

	public void animate() {
	}

}