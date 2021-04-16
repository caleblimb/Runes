package game.level;

import game.Game;
import game.entity.Entity;
import game.entity.item.Item;
import game.entity.item.collectable.Collectable;
import game.entity.mob.Mob;
import game.entity.mob.Player;
import game.entity.particle.Particle;
import game.entity.projectile.Projectile;
import game.graphics.Screen;
import game.graphics.Sprite;
import game.level.tile.GameLevel;
import game.level.tile.Tile;
import game.menu.MainMenu;
import game.util.Vector2i;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Level {

	protected int width, height;
	protected int[] tilesInt;
	protected int[] tiles;
	protected static int second = 0;
	public static Level mainMenu = new MainMenu(Sprite.mainBackground);
	public static Level test = new GameLevel("res/maps/test.json");
	public static Level current = mainMenu;

	public Player player;
	protected List<Entity> entities = new ArrayList<Entity>();
	protected List<Mob> mobs = new ArrayList<Mob>();
	protected List<Item> items = new ArrayList<Item>();
	// protected List<Drop> drops = new ArrayList<Drop>();
	// protected List<GroundFloorThing> items = new
	// ArrayList<GroundFloorThing>();
	protected List<Projectile> projectiles = new ArrayList<Projectile>();
	protected List<Particle> particles = new ArrayList<Particle>();

	protected List<Player> players = new ArrayList<Player>();

	private Comparator<Node> nodeSorter = new Comparator<Node>() {
		public int compare(Node n0, Node n1) {
			if (n1.fCost < n0.fCost) return +1;
			if (n1.fCost > n0.fCost) return -1;
			return 0;
		}
	};

	public Level(int width, int height) {

		this.width = width;
		this.height = height;
		tilesInt = new int[width * height];
		generateLevel();
	}

	public static void set(String level) {
		if (level.equals("mainMenu")) current = mainMenu;
		else if (level.equals("test")) current = test;
		else System.out.println("Invalid level string");
		Game.updateLevel();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Level(String path) {
		loadLevel(path);
		loadEntities(path);
		generateLevel();
	}

	protected void generateLevel() {
	}

	protected void loadLevel(String path) {
	}

	protected void loadEntities(String path) {

	}

	public List<Projectile> getProjectiles() {
		return projectiles;
	}

	public List<Entity> getEntities() {
		return entities;
	}

	public boolean tileCollision(int x, int y, int size) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			int xt = (x - c % 2 * size + size / 2) >> 4;
			int yt = (y - c / 2 * size + size / 2) >> 4;
			if (getTile(xt, yt).solid()) solid = true;
		}
		return solid;
	}

	public void update() {
	}

	public void render(int xScroll, int yScroll, Screen screen) {
	}

	protected void remove() {
	}

	public void add(Entity e) {
		e.init(this);
		if (e instanceof Particle) {
			particles.add((Particle) e);
		} else if (e instanceof Player) {
			players.add((Player) e);
		} else if (e instanceof Mob) {
			mobs.add((Mob) e);
		} else if (e instanceof Item) {
			items.add((Item) e);
		} else if (e instanceof Projectile) {
			projectiles.add((Projectile) e);
		} else {
			entities.add(e);
			// System.out.println("Error, accidentally added an entity to the entities list int Level.add(Entity e)");
		}
	}

	public List<Player> getPlayer() {
		return players;
	}

	public Player getPlayerAt(int index) {
		return players.get(index);
	}

	public Player getClientPlayer() {
		return players.get(0);
	}

	// A* Pathfinding Algorithm
	public List<Node> findPath(Vector2i start, Vector2i goal) {
		List<Node> openList = new ArrayList<Node>();
		List<Node> closedList = new ArrayList<Node>();
		Node current = new Node(start, null, 0, getDistance(start, goal));
		openList.add(current);
		while (openList.size() > 0) {
			Collections.sort(openList, nodeSorter);
			current = openList.get(0);
			if (current.tile.equals(goal)) {
				List<Node> path = new ArrayList<Node>();
				while (current.parent != null) {
					path.add(current);
					current = current.parent;
				}
				openList.clear();
				closedList.clear();
				return path;
			}
			openList.remove(current);
			closedList.add(current);
			for (int i = 0; i < 9; i++) {
				if (i == 4) continue;
				int x = current.tile.getX();
				int y = current.tile.getY();
				int xi = (i % 3) - 1;
				int yi = (i / 3) - 1;
				Tile at = getTile(x + xi, y + yi);
				if (at == null) continue;
				if (at.solid()) continue; // causing problems when player is on solid tile
				Vector2i a = new Vector2i(x + xi, y + yi);
				double gCost = current.gCost + (getDistance(current.tile, a) == 1 ? 1 : 0.95);
				double hCost = getDistance(a, goal);
				Node node = new Node(a, current, gCost, hCost);
				if (vecInList(closedList, a) && gCost >= node.gCost) continue;
				if (!vecInList(openList, a) || gCost < current.gCost) openList.add(node);
			}
			if (openList.size() > 256) System.err.println("Error with findPath() in Level, openList is too larg!");;
		}
		closedList.clear();
		return null;
	}

	private boolean vecInList(List<Node> list, Vector2i vector) {
		for (Node n : list) {
			if (n.tile.equals(vector)) return true;
		}
		return false;
	}

	private double getDistance(Vector2i tile, Vector2i goal) {
		double dx = tile.getX() - goal.getX();
		double dy = tile.getY() - goal.getY();
		return Math.sqrt(dx * dx + dy * dy);
	}

	public List<Entity> getEntities(Entity e, int radius) {
		List<Entity> result = new ArrayList<Entity>();
		double ex = e.getX();
		double ey = e.getY();
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			if (entity.equals(e)) continue;
			double x = entity.getX();
			double y = entity.getY();

			double dx = Math.abs(x - ex);
			double dy = Math.abs(y - ey);
			double distance = Math.sqrt((dx * dx) + (dy * dy));
			if (distance <= radius) result.add(entity);
		}
		return result;
	}

	public List<Player> getPlayers(Entity e, int radius) {
		List<Player> result = new ArrayList<Player>();
		double ex = e.getX();
		double ey = e.getY();
		for (int i = 0; i < players.size(); i++) {
			Player player = players.get(i);
			double x = player.getX();
			double y = player.getY();
			double dx = Math.abs(x - ex);
			double dy = Math.abs(y - ey);
			double distance = Math.sqrt((dx * dx) + (dy * dy));
			if (distance <= radius) result.add(player);
		}
		return result;
	}

	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height || tiles.length == 0) return Tile.tile[0];
		try {
			return Tile.tile[tiles[x + y * width]];
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			return Tile.tile[0];
		}
	}

	public Tile getTile(int index) {
		if (index < 0 || index > tiles.length || tiles.length == 0) return Tile.tile[0];
		try {
			return Tile.tile[tiles[index]];
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			return Tile.tile[0];
		}
	}
}
