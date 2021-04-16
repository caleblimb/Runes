package game.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	public final int WIDTH;
	public final int HEIGHT;
	public int[] pixels;

	public static SpriteSheet oldtiles = new SpriteSheet("/graphics/roguelikeSheet.png");
	public static SpriteSheet tiles = new SpriteSheet("/graphics/tiles.png");
	public static SpriteSheet player = new SpriteSheet("/graphics/player.png");
	public static SpriteSheet armor = new SpriteSheet("/graphics/armor.png");
	public static SpriteSheet mobs = new SpriteSheet("/graphics/mobs.png");
	public static SpriteSheet flippingStone = new SpriteSheet("/graphics/flippingStone.png");
	public static SpriteSheet projectiles = new SpriteSheet("/graphics/projectiles.png");
	public static SpriteSheet mainBackground = new SpriteSheet("/graphics/menu/menu.png");
	public static SpriteSheet gameMenuBackground = new SpriteSheet("/graphics/menu/parchment.png");
	public static SpriteSheet particle = new SpriteSheet("/graphics/particles.png");
	public static SpriteSheet play = new SpriteSheet("/graphics/menu/play.png");

	public SpriteSheet(String path) {
		int w = 0;
		int h = 0;
		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			w = image.getWidth();
			h = image.getHeight();
			pixels = new int[w * h];
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}
		WIDTH = w;
		HEIGHT = h;
	}

	public int[] getPixels() {
		return pixels;
	}
	/*
	 * public SpriteSheet(String path, int size) {
	 * this.path = path;
	 * WIDTH = size;
	 * HEIGHT = size;
	 * pixels = new int[WIDTH * HEIGHT];
	 * load();
	 * }
	 * 
	 * public SpriteSheet(String path, int width, int height) {
	 * this.path = path;
	 * WIDTH = width;
	 * HEIGHT = height;
	 * pixels = new int[WIDTH * HEIGHT];
	 * load();
	 * }
	 * 
	 * private void load() {
	 * try {
	 * BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
	 * int w = image.getWidth();
	 * int h = image.getHeight();
	 * image.getRGB(0, 0, w, h, pixels, 0, w);
	 * } catch (IOException e) {
	 * e.printStackTrace();
	 * }
	 * }
	 */

}