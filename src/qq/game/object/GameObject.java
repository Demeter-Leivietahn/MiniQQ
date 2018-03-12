package qq.game.object;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import qq.game.frame.RaidenFrame;
import qq.game.values.Direction;

public abstract class GameObject {

	protected int x = 0;

	protected int y = 0;

	protected int width = 0;

	protected int height = 0;

	protected int speed = 0;

	protected Direction d;

	protected BufferedImage showImage = null;

	protected boolean dead = false;

	public abstract void paint(Graphics g);

	public abstract void move();

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public GameObject(int x, int y, int width, int height, Direction d) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.d = d;
	}

	public boolean isDead() {
		return dead;
	}

	public void die() {
		this.dead = true;
	}

	public void checkBound() {
		if (this.x < 0) {
			x = 0;
		}
		if (this.x > RaidenFrame.WINDOW_WIDTH - this.width) {
			x = RaidenFrame.WINDOW_WIDTH - this.width;
		}
		if (this.y < 0) {
			y = 0;
		}
		if (this.y > RaidenFrame.WINDOW_HEIGHT - this.height) {
			y = RaidenFrame.WINDOW_HEIGHT - this.height;
		}
	}

	public Rectangle getRect() {
		return new Rectangle(this.x, this.y, this.width, this.height);
	}
}

