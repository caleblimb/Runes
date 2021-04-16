package game.level.tile;

import game.Game;
import game.entity.Entity;
import game.entity.item.Chalk;
import game.entity.item.Grave;
import game.entity.item.Sludge;
import game.entity.item.Web;
import game.entity.item.collectable.GoldCoin;
import game.entity.mob.Player;
import game.graphics.Screen;
import game.level.Level;
import game.menu.HUD;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import json.JSONArray;
import json.JSONObject;

public class GameLevel extends Level {
	HUD hud = new HUD(this);

	public GameLevel(String path) {
		super(path);
		Game.setScroll(0, 0);
	}

	protected void loadLevel(String path) {

		File file = new File(path);
		String level = "";
		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				level += line;
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}

		JSONObject obj = new JSONObject(level);
		width = obj.getJSONObject("size").getInt("width");
		height = obj.getJSONObject("size").getInt("height");
		tiles = new int[width * height];
		JSONArray arr = obj.getJSONArray("tiles");		
		for (int i = 0; i < arr.length(); i++) {
			tiles[i] = arr.optInt(i, 0)-1;
		}
	}

	protected void loadEntities(String path) {
		TileCoordinate playerSpawn = new TileCoordinate(22, 22);
		add(new Player(playerSpawn.x(), playerSpawn.y(), test, Game.key));
		
		
		TileCoordinate spawner = new TileCoordinate(14, 14);
		//add(new Grave(spawner.x(), spawner.y(), test));

		//spawner = new TileCoordinate(20, 26);
		//add(new Grave(spawner.x(), spawner.y(), test));
		spawner = new TileCoordinate(22, 58);
		add(new Grave(spawner.x(), spawner.y(), test));
		//spawner = new TileCoordinate(38, 17);
		//add(new Web(spawner.x(), spawner.y(), test));
		spawner = new TileCoordinate(47, 33);
		add(new Web(spawner.x(), spawner.y(), test));
		spawner = new TileCoordinate(39, 39);
		add(new Grave(spawner.x(), spawner.y(), test));
		//spawner = new TileCoordinate(41, 40);
		//add(new Grave(spawner.x(), spawner.y(), test));
		//spawner = new TileCoordinate(10, 32);
		//add(new Chalk(spawner.x(), spawner.y(), test));
		//spawner = new TileCoordinate(56, 50);
		//add(new Sludge(spawner.x(), spawner.y(), test));
		spawner = new TileCoordinate(46, 56);
		add(new Sludge(spawner.x(), spawner.y(), test));
		spawner = new TileCoordinate(56, 22);
		add(new Chalk(spawner.x(), spawner.y(), test)); 
	}

	public void update() {
		hud.update();
		second++;
		if (second > 59) second = 0;
		if (getClientPlayer() != null) {
			getClientPlayer().update();
			if (getClientPlayer().x < Game.getxScroll() + Game.getGameWidth() / 4 && Game.getxScroll() > 0)
				Game.scroll(-1, 0);
			if (getClientPlayer().x > Game.getxScroll() + Game.getGameWidth() * 5 / 7
					&& Game.getxScroll() < width * 16 - Game.getGameWidth()) Game.scroll(1, 0);
			if (getClientPlayer().y < Game.getyScroll() + Game.getGameHeight() / 3 && Game.getyScroll() > 0)
				Game.scroll(0, -1);
			if (getClientPlayer().y > Game.getyScroll() + Game.getGameHeight() * 2 / 3
					&& Game.getyScroll() < height * 16 - Game.getGameHeight()) Game.scroll(0, 1);
		}
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).update();
		}
		for (int i = 0; i < mobs.size(); i++) {
			mobs.get(i).update();
		}
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).update();
		}
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).update();
		}
		for (int i = 0; i < items.size(); i++) {
			items.get(i).update();
		}
		remove();
	}

	protected void remove() {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).isRemoved()) entities.remove(i);
		}
		for (int i = 0; i < mobs.size(); i++) {
			if (mobs.get(i).isRemoved()) mobs.remove(i);
		}
		for (int i = 0; i < projectiles.size(); i++) {
			if (projectiles.get(i).isRemoved()) projectiles.remove(i);
		}
		for (int i = 0; i < particles.size(); i++) {
			if (particles.get(i).isRemoved()) particles.remove(i);
		}
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).isRemoved()) items.remove(i);
		}
	}

	public void render(int xScroll, int yScroll, Screen screen) {
		screen.setOffset(xScroll, yScroll);
		int x0 = xScroll >> 4;
		int x1 = (xScroll + screen.width + 16) >> 4;
		int y0 = yScroll >> 4;
		int y1 = (yScroll + screen.height + 16) >> 4;
		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				getTile(x, y).render(x, y, screen);
			}
		}

		List<Entity> elements = new ArrayList<Entity>();
		elements.addAll(particles);
		elements.addAll(items);
		elements.addAll(mobs);
		elements.addAll(entities);
		elements.addAll(players);
		elements.addAll(projectiles);

		Collections.sort(elements, new Comparator<Entity>() {
			public int compare(Entity e1, Entity e2) {
				if (e1.renderDepth() > e2.renderDepth()) return 1;
				if (e1.renderDepth() < e2.renderDepth()) return -1;
				return 0;
			}
		});

		for (int i = 0; i < elements.size(); i++) {
			elements.get(i).render(screen);
		}
		hud.render(screen);
	}

	protected void generateLevel() {
	}

}
