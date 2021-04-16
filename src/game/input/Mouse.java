package game.input;

import game.Game;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener, MouseMotionListener {

	private static int mouseX = -1;
	private static int mouseY = -1;
	private static int mouseB = -1;
	public static Cursor cursor;
	public static Cursor hand = new Cursor(Cursor.CROSSHAIR_CURSOR);
	static java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
	static Image image = toolkit.getImage("mobs.png");
	public static Cursor pointer = toolkit.createCustomCursor(image, new Point(0, 0), "pointer");

	public static void setCursor(Cursor c) {
		cursor = c;
	}

	public static int getX() {
		return mouseX;
	}

	public static int getY() {
		return mouseY;
	}

	public static Point getPosition() {
		return new Point(mouseX, mouseY);
	}

	public static Point getPositionInGame() {
		Rectangle window = Game.getWindowSize();
		return new Point((int) ((double) mouseX / ((double) window.width / (double) Game.getGameWidth())), (int) ((double) mouseY / ((double) window.height / (double) Game.getGameHeight())));
	}

	public static Point getPositionInLevel() {
		Rectangle window = Game.getWindowSize();
		return new Point((int) (((double) mouseX / ((double) window.width / (double) Game.getGameWidth())) + (double) Game.getxScroll()), (int) (((double) mouseY / ((double) window.height / (double) Game.getGameHeight())) + (double) Game.getyScroll()));
	}

	public static int getButton() {
		return mouseB;
	}

	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		mouseB = e.getButton();
	}

	public void mouseReleased(MouseEvent e) {
		mouseB = -1;
	}

}