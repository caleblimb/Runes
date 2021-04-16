package game.menu.component;

import game.graphics.Screen;

import java.util.ArrayList;
import java.util.List;

public class Panel {

	public int x, y;
	protected List<Panel> panels = new ArrayList<Panel>();
	protected List<Component> components = new ArrayList<Component>();

	public Panel(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void add(Panel p) {
		p.addOffset(x, y);
		panels.add(p);
	}

	public void add(Component c) {
		c.addOffset(x, y);
		components.add(c);
	}

	public void addOffset(int x, int y) {
		this.x += x;
		this.y += y;
	}

	public void update() {
		for (int i = 0; i < panels.size(); i++)
			panels.get(i).update();
		for (int i = 0; i < components.size(); i++)
			components.get(i).update();
	}

	public void render(Screen screen) {
		for (int i = 0; i < panels.size(); i++)
			panels.get(i).render(screen);
		for (int i = 0; i < components.size(); i++)
			components.get(i).render(screen);
	}

}
