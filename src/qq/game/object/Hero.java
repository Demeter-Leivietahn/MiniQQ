package qq.game.object;

import java.awt.Color;
import java.awt.Graphics;

import qq.game.values.StaticValue;

public class Hero extends GameObject {

	private boolean UP, DOWN, LEFT, RIGHT;

	private int score = 0;

	private int health = 40;

	public void divHealth(int hp) {
		this.health -= hp;
		if (health >= 60) {
			this.health = 60;
		}
		if (health <= 0) {
			this.die();
		}
	}

	public Hero(int x, int y) {
		super(x, y, 0, 0, null);
		this.speed = 10;
		showImage = StaticValue.heroImage;
		this.width = showImage.getWidth();
		this.height = showImage.getHeight();
	}

	public int getScore() {
		return score;
	}

	public void addScore(int score) {
		this.score += score;
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(showImage, x, y, null);

		// 绘制分数
		g.drawImage(StaticValue.panel, 0, 20, null);
		Color old = g.getColor();
		g.setColor(Color.CYAN);
		g.drawString("得分：", 30, 50);
		g.setColor(old);
		if (score > 9999) {
			score = 9999;
		}
		g.drawImage(StaticValue.allNumImage.get(score / 1000), 65, 40, null);
		g.drawImage(StaticValue.allNumImage.get(score / 100 % 10), 80, 40, null);
		g.drawImage(StaticValue.allNumImage.get(score / 10 % 10), 95, 40, null);
		g.drawImage(StaticValue.allNumImage.get(score % 10), 110, 40, null);

		// 绘制血条
		for (int i = 0; i < health; i++) {
			g.drawImage(StaticValue.hp, 20 + i * (StaticValue.hp.getWidth() + 1), 550, null);
		}
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		if (this.UP) {
			this.y -= this.speed;
		}

		if (this.DOWN) {
			this.y += this.speed;
		}

		if (this.LEFT) {
			this.x -= this.speed;
		}

		if (this.RIGHT) {
			this.x += this.speed;
		}
		checkBound();

	}

	public void moveUp() {
		this.UP = true;
	}

	public void moveDown() {
		this.DOWN = true;
	}

	public void moveLeft() {
		this.LEFT = true;
	}

	public void moveRight() {
		this.RIGHT = true;
	}

	public void moveUpStop() {
		this.UP = false;
	}

	public void moveDownStop() {
		this.DOWN = false;
	}

	public void moveLeftStop() {
		this.LEFT = false;
	}

	public void moveRightStop() {
		this.RIGHT = false;
	}

}
