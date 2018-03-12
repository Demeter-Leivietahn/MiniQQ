package qq.game.object;

import java.awt.Graphics;
import java.util.Random;

import qq.game.bullets.EnemyBullet;
import qq.game.frame.RaidenFrame;
import qq.game.values.Direction;
import qq.game.values.StaticValue;

public class Enemy extends GameObject {

	private int enemyType = 0;

	private Random random = null;

	private int count = 0;

	private int dir = 0;

	private Bullet blt = null;

	public Enemy(int x, int y) {
		super(x, y, 0, 0, null);
		this.speed = 2;
		random = new Random();
		enemyType = random.nextInt(15);
		this.showImage = StaticValue.allEnemyImage.get(enemyType);
		this.width = showImage.getWidth();
		this.height = showImage.getHeight();
	}

	public Bullet fire() {
		int type = random.nextInt(3);
		if (this.x < RaidenFrame.WINDOW_WIDTH / 3) {
			if (type == 0) {
				blt = new EnemyBullet(x + this.width / 2, y + this.height / 2, Direction.D);
			} else {
				blt = new EnemyBullet(x + this.width / 2, y + this.height / 2, Direction.RD);
			}
		} else if (this.x > RaidenFrame.WINDOW_WIDTH * 2 / 3) {
			if (type == 0) {
				blt = new EnemyBullet(x + this.width / 2, y + this.height / 2, Direction.D);
			} else {
				blt = new EnemyBullet(x + this.width / 2, y + this.height / 2, Direction.LD);
			}
		} else {
			if (type == 0) {
				blt = new EnemyBullet(x + this.width / 2, y + this.height / 2, Direction.D);
			} else if (type == 1) {
				blt = new EnemyBullet(x + this.width / 2, y + this.height / 2, Direction.LD);
			} else if (type == 2) {
				blt = new EnemyBullet(x + this.width / 2, y + this.height / 2, Direction.RD);
			}
		}

		return blt;
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(showImage, x, y, null);
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		this.y += speed;
		count++;
		if (this.count % 50 == 0) {
			dir = random.nextInt(50) % 2;
		}
		if (dir == 0) {
			this.x -= speed;
		} else {
			this.x += speed;
		}
		checkBound();
	}

	@Override
	public void checkBound() {
		// TODO Auto-generated method stub
		if (this.x < 0 || this.y < 0 || this.x > RaidenFrame.WINDOW_WIDTH || this.y > RaidenFrame.WINDOW_HEIGHT) {
			this.die();
		}
	}

}