package pacman;

import java.awt.Rectangle;

public class Block {
	private int x, y;
	private Rectangle rect = new Rectangle(0, 0, 0, 0);

	public Block(int x, int y) {
		this.x = x;
		this.y = y;
		this.rect.setRect(x, y, 30, 30);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Rectangle getRect() {
		return rect;
	}
}