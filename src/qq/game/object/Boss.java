package qq.game.object;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import qq.game.bullets.EnemyBullet;
import qq.game.values.Direction;
import qq.game.values.StaticValue;

public class Boss extends GameObject {

	private BufferedImage showImage = null;

	private float health = 500;

	private int dir = 0;

	public Boss(int x, int y) {
		super(x, y, 0, 0, null);
		this.speed = 5;
		showImage = StaticValue.boss;
		this.width = showImage.getWidth();
		this.height = showImage.getHeight();
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		// ªÊ÷∆BOSS
		g.drawImage(showImage, x, y, null);
		// ªÊ÷∆boss—™Ãı
		for (int i = 0; i < this.health; i++) {
			g.drawImage(StaticValue.hp, 150 + i, 50, null);
		}

	}

	public boolean divHealth(double d) {
		this.health -= d;
		if (this.health <= 0) {
			die();
			return true;
		}
		return false;
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		if (this.y < 70) {
			this.y += speed;
		}
		if (new Random().nextInt(100) > 90) {
			int temp = new Random().nextInt(3);
			if (temp == 0) {
				dir = -1;
			} else if (temp == 1) {
				dir = 0;
			} else if (temp == 2) {
				dir = 1;
			}
		}
		this.x += dir * speed;
		this.checkBound();
	}

	public EnemyBullet fire() {
		return new EnemyBullet(this.x + this.width / 2, this.y + this.width / 2, Direction.D);
	}

}
