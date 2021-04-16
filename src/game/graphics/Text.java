package game.graphics;

public class Text {

	private static SpriteSheet arial = new SpriteSheet("/fonts/Arial_8.png");
	private static String charIndex = "!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
	private static Sprite[] characters = Sprite.spriteArray(8, 0, charIndex.length(), arial);

	public Text() {

	}

	public void render(int x, int y, String text, Screen screen) {
		render(x, y, 0, 0, text, screen);
	}

	public static void render(int x, int y, int spacing, int color, String text, Screen screen) {
		int xOffset = 0;
		int line = 0;
		for (int i = 0; i < text.length(); i++) {
			xOffset += 8 + spacing;
			char currentChar = text.charAt(i);
			if (currentChar == '\n') {
				xOffset = 0;
				line += 1;
			}
			int index = charIndex.indexOf(currentChar);
			if (index == -1) continue;
			screen.renderTextCharacter(xOffset, y + (line * 10), characters[index], color, false);
		}
	}
}
