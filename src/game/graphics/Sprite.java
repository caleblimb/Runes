package game.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {

	private int x, y;
	public final int WIDTH, HEIGHT;
	public int[] pixels;
	private SpriteSheet sheet;

	// menus
	public static Sprite mainBackground = new Sprite("/graphics/menu/menu.png");
	public static Sprite gameMenuBackground = new Sprite("/graphics/menu/parchment.png");
	public static Sprite play = new Sprite(100, 0, 0, SpriteSheet.play);
	public static Sprite playHover = new Sprite(100, 0, 1, SpriteSheet.play);
	public static Sprite playPress = new Sprite(100, 0, 2, SpriteSheet.play);

	public static Sprite water0 = new Sprite(16, 0, 0, SpriteSheet.oldtiles);
	public static Sprite water1 = new Sprite(16, 1, 0, SpriteSheet.oldtiles);

	public static Sprite voidSprite = new Sprite(16, 0x1B87E0);

	public static Sprite tombstone = new Sprite("/graphics/tombstone.png");
	public static Sprite web = new Sprite("/graphics/spiderweb.png");
	public static Sprite chalk = new Sprite("/graphics/ghostchalk.png");
	public static Sprite slimepuddle = new Sprite("/graphics/slime.png");
	public static Sprite goldCoin = new Sprite("/graphics/coin.png");

	// projectile sprites
	public static Sprite projectile_wizard = new Sprite(16, 0, 0, SpriteSheet.projectiles);
	public static Sprite projectile_arrow = new Sprite(32, 1, 0, SpriteSheet.projectiles);

	public static Sprite particle_fire0 = new Sprite(3, 0, 0, SpriteSheet.particle);
	public static Sprite particle_fire1 = new Sprite(3, 1, 0, SpriteSheet.particle);
	public static Sprite particle_ice0 = new Sprite(3, 0, 1, SpriteSheet.particle);
	public static Sprite particle_ice1 = new Sprite(3, 1, 1, SpriteSheet.particle);
	public static Sprite particle_normal = new Sprite(2, 0x888888);
	public static Sprite particle_skeleton = new Sprite(2, 0x777777);
	public static Sprite particle_slime = new Sprite(2, 0x8ae6f6);
	public static Sprite particle_spider = new Sprite(2, 0x222222);
	public static Sprite particle_ghost = new Sprite(2, 0x000000);
	public static Sprite particle_grass = new Sprite(2, 0x84bb2c);
	public static Sprite particle_dirt = new Sprite(2, 0xa07247);
	public static Sprite particle_sand = new Sprite(2, 0xd9caa9);
	public static Sprite particle_water = new Sprite(2, 0x8ed4db);

	public static Sprite[] player_up = spriteArray(32, 0, 8, SpriteSheet.player);
	public static Sprite[] player_right = spriteArray(32, 1, 8, SpriteSheet.player);
	public static Sprite[] player_down = spriteArray(32, 2, 8, SpriteSheet.player);
	public static Sprite[] player_left = spriteArray(32, 3, 8, SpriteSheet.player);
	public static Sprite armor_up = new Sprite(32, 0, 0, SpriteSheet.armor);
	public static Sprite armor_right = new Sprite(32, 0, 1, SpriteSheet.armor);
	public static Sprite armor_down = new Sprite(32, 0, 2, SpriteSheet.armor);
	public static Sprite armor_left = new Sprite(32, 0, 3, SpriteSheet.armor);
	public static Sprite[] skeleton = spriteArray(32, 0, 8, SpriteSheet.mobs);
	public static Sprite[] ghost = spriteArray(32, 1, 8, SpriteSheet.mobs);
	public static Sprite[] spider = spriteArray(32, 2, 8, SpriteSheet.mobs);
	public static Sprite[] slime = spriteArray(32, 3, 8, SpriteSheet.mobs);

	public static Sprite[] flippingStone = spriteArray(64, 0, 19, SpriteSheet.flippingStone);
	public static Sprite[] tile = spriteArray(16, SpriteSheet.tiles, 5, 164);

	public static Sprite[] spriteArray(int size, int row, int amount, SpriteSheet sheet) {
		Sprite[] array = new Sprite[amount];
		for (int i = 0; i < amount; i++) {
			array[i] = new Sprite(size, i, row, sheet);
		}
		return array;
	}

	public static Sprite[] spriteArray(int size, SpriteSheet sheet, int width, int height) {
		Sprite[] array = new Sprite[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				array[x + y * width] = new Sprite(size, x, y, sheet);
			}
		}
		return array;
	}

	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		WIDTH = size;
		HEIGHT = size;
		pixels = new int[WIDTH * HEIGHT];
		this.x = x * WIDTH;
		this.y = y * HEIGHT;
		this.sheet = sheet;
		load();
	}

	public Sprite(String path) {
		int w = 0;
		int h = 0;
		int[] pixels = null;
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
		this.pixels = new int[w * h];
		for (int y = 0; y < this.HEIGHT; y++) {
			for (int x = 0; x < this.WIDTH; x++) {
				this.pixels[x + y * this.WIDTH] = pixels[(x) + (y) * w];
			}
		}
	}

	public Sprite(int size, int color) {
		WIDTH = size;
		HEIGHT = size;
		pixels = new int[WIDTH * HEIGHT];
		setColor(color);
	}

	public Sprite(int[] colors, int width, int height) {
		WIDTH = width;
		HEIGHT = height;
		pixels = colors;
	}

	public static Sprite rotate(Sprite sprite, double angle) {
		return new Sprite(rotate(sprite.pixels, sprite.WIDTH, sprite.HEIGHT, angle), sprite.WIDTH, sprite.HEIGHT);
	}

	private static int[] rotate(int[] pixels, int width, int height, double angle) {
		int[] result = new int[width * height];

		double nx_x = rot_x(-angle, 1.0, 0.0);
		double nx_y = rot_y(-angle, 1.0, 0.0);
		double ny_x = rot_x(-angle, 0.0, 1.0);
		double ny_y = rot_y(-angle, 0.0, 1.0);

		double x0 = rot_x(-angle, -width / 2.0, -height / 2.0) + width / 2.0;
		double y0 = rot_y(-angle, -width / 2.0, -height / 2.0) + height / 2.0;

		for (int y = 0; y < height; y++) {
			double x1 = x0;
			double y1 = y0;
			for (int x = 0; x < width; x++) {
				int xx = (int) x1;
				int yy = (int) y1;
				int col = 0;
				if (xx < 0 || xx >= width || yy < 0 || yy >= height) col = 0xffff00ff;
				else col = pixels[xx + yy * width];
				result[x + y * width] = col;
				x1 += nx_x;
				y1 += nx_y;
			}
			x0 += ny_x;
			y0 += ny_y;
		}

		return result;
	}

	private static double rot_x(double angle, double x, double y) {
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		return x * cos + y * -sin;// -to reverse direction
	}

	private static double rot_y(double angle, double x, double y) {
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		return x * sin + y * cos;//
	}

	private void setColor(int color) {
		for (int i = 0; i < WIDTH * HEIGHT; i++) {
			pixels[i] = color;
		}
	}

	public int getWidth() {
		return WIDTH;
	}

	public int getHeight() {
		return HEIGHT;
	}

	private void load() {
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				pixels[x + y * WIDTH] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.WIDTH];// width?
			}
		}
	}
}