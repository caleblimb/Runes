package game;

import game.graphics.Screen;
import game.input.Keyboard;
import game.input.Mouse;
import game.level.Level;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	private static int width = 600;
	private static int height = 332;
	public static int scale = 2;
	public static String title = "Caleb";

	public static int xScroll;
	public static int yScroll;
	private Thread thread;
	private static JFrame frame;
	public static Keyboard key;
	private static Level level;
	public static int delay = 0;
	private boolean running = false;

	private Screen screen;
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	public Game() {
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);
		screen = new Screen(width, height);
		frame = new JFrame();
		key = new Keyboard();
		level = Level.current;//
		
		addKeyListener(key);
		Mouse mouse = new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		// Mouse.setCursor(Mouse.hand);
	}

	public void run() {
		double ns = 1000000000.0 / 60.0;
		double delta = 0;

		int frames = 0;
		int updates = 0;

		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();

		requestFocus();

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			if (delta >= 1) {
				render();
				frames++;
			}
			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			if (System.currentTimeMillis() - timer >= 1000) {
				timer += 1000;
				frame.setTitle(title + "  |  " + updates + " ups, " + frames + " fps");
				frames = 0;
				updates = 0;
			}
		}

		stop();
	}

	public void update() {
		delay++;
		if (delay > Integer.MAX_VALUE - 100) delay = 0;
		key.update();
		level.update();
		if (getCursor() != Mouse.cursor) setCursor(Mouse.cursor);
	}

	public static void updateLevel() {
		level = Level.current;
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		screen.clear();

		level.render(xScroll, yScroll, screen);
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);// Important!
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		Game game = new Game();
		// game.frame.setResizable(false);
		game.frame.setTitle(Game.title);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);

		game.start();
	}

	public static int getGameWidth() {
		return width;
	}

	public static int getGameHeight() {
		return height;
	}

	public static int getWindowWidth() {
		return width * scale;
	}

	public static int getWindowHeight() {
		return height * scale;
	}

	public static Rectangle getWindowSize() {
		return frame.getBounds();
	}

	public static void setScroll(int x, int y) {
		xScroll = x;
		yScroll = y;
	}

	public static void scroll(int x, int y) {
		xScroll += x;
		yScroll += y;
	}

	public static int getxScroll() {
		return xScroll;
	}

	public static int getyScroll() {
		return yScroll;
	}
	
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}

	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}