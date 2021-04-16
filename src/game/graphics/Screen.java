package game.graphics;

import game.entity.projectile.Projectile;
import game.level.tile.Tile;

import java.util.Random;

public class Screen {

	public int width, height;
	public int[] pixels;

	public final int MAP_SIZE = 64;
	public final int MAP_SIZE_MASK = MAP_SIZE - 1;

	public int xOffset, yOffset;

	public int[] tiles = new int[MAP_SIZE * MAP_SIZE];

	private Random random = new Random();

	public Screen(int width, int height) {
		this.width = width;
		this.height = height;

		pixels = new int[width * height];

		for (int i = 0; i < MAP_SIZE * MAP_SIZE; i++) {
			tiles[i] = random.nextInt(0xFFFFFF);
		}
	}

	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}

	public void renderSprite(int xp, int yp, Sprite sprite, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int y = 0; y < sprite.getHeight(); y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.getWidth(); x++) {
				int xa = x + xp;
				if (xa < -sprite.WIDTH || xa < 0 || xa >= width || ya < 0 || ya >= height) break;// fix
																									// logic
				if (xa < 0) xa = 0;
				int col = sprite.pixels[x + y * sprite.getWidth()];
				if (col != 0xffff00ff) pixels[xa + ya * width] = col;
			}
		}
	}

	public void renderSprite(int xp, int yp, int leftMargin, int topMargin, int rightMargin, int bottomMargin, Sprite sprite, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		if (leftMargin < 0 || topMargin < 0 || rightMargin > sprite.WIDTH || bottomMargin > sprite.HEIGHT) {
			System.out.println("Error in Margin");
			return;
		}
		for (int y = topMargin; y < bottomMargin; y++) {
			int ya = y + yp - topMargin;
			for (int x = leftMargin; x < rightMargin; x++) {
				int xa = x + xp - leftMargin;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;
				int col = sprite.pixels[x + y * sprite.getWidth()];
				if (col != 0xffff00ff) pixels[xa + ya * width] = col;
			}
		}
	}

	public void renderTile(int xp, int yp, Tile tile) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < tile.sprite.HEIGHT; y++) {
			int ya = y + yp;
			for (int x = 0; x < tile.sprite.WIDTH; x++) {
				int xa = x + xp;
				if (xa < -tile.sprite.WIDTH || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;
				int col = tile.sprite.pixels[x + y * tile.sprite.WIDTH];
				if (col != 0xffff00ff) pixels[xa + ya * width] = col;
			}
		}
	}

	public void renderTile(int xp, int yp, Tile tile, Sprite background) {
		if (background == null) renderTile(xp, yp, tile);
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < tile.sprite.HEIGHT; y++) {
			int ya = y + yp;
			for (int x = 0; x < tile.sprite.WIDTH; x++) {
				int xa = x + xp;
				if (xa < -tile.sprite.WIDTH || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;
				int bcol = background.pixels[x + y * background.WIDTH];
				if (bcol != 0xffff00ff) pixels[xa + ya * width] = bcol;
				int col = tile.sprite.pixels[x + y * tile.sprite.WIDTH];
				if (col != 0xffff00ff) pixels[xa + ya * width] = col;
			}
		}
	}

	public void renderTile(int xp, int yp, Tile tile, int rotation) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < tile.sprite.HEIGHT; y++) {
			int ya = y + yp;
			int ys = y;
			if (rotation % 2 == 0) {
				ys = tile.sprite.HEIGHT - 1 - y;// width?
			}
			for (int x = 0; x < tile.sprite.WIDTH; x++) {
				int xa = x + xp;
				int xs = x;
				if (rotation < 3) {
					xs = tile.sprite.WIDTH - 1 - x;// height?
				}
				if (xa < -tile.sprite.WIDTH || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;
				int col = tile.sprite.pixels[xs + ys * tile.sprite.WIDTH];
				if (col != 0xffff00ff) pixels[xa + ya * width] = col;
			}
		}
	}

	public void renderProjectile(int xp, int yp, Projectile p) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < p.getSpriteHeight(); y++) {
			int ya = y + yp;
			for (int x = 0; x < p.getSpriteWidth(); x++) {
				int xa = x + xp;
				if (xa < -p.getSpriteWidth() || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;
				int col = p.getSprite().pixels[x + y * p.getSpriteWidth()];
				if (col != 0xffff00ff) pixels[xa + ya * width] = col;
			}
		}
	}

	public void renderEntity(int xp, int yp, Sprite sprite, int tred) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < sprite.HEIGHT - tred; y++) {
			int ya = y + yp;
			int ys = y;
			for (int x = 0; x < sprite.WIDTH; x++) {
				int xa = x + xp;
				int xs = x;
				if (xa < -sprite.WIDTH || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;
				int col = sprite.pixels[xs + ys * sprite.WIDTH];
				if (col != 0xffff00ff) pixels[xa + ya * width] = col;
			}
		}
	}

	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	public void renderTextCharacter(int xp, int yp, Sprite sprite, int color, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int y = 0; y < sprite.getHeight(); y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.getWidth(); x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
				int col = sprite.pixels[x + y * sprite.getWidth()];
				if (col != 0xffff00ff) pixels[xa + ya * width] = col;
			}
		}
	}

}