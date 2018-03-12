package qq.game.object;

import java.awt.Graphics;
import java.util.Random;

import qq.game.frame.RaidenFrame;
import qq.game.values.StaticValue;

public class ChangeFire extends GameObject {

	private Random random = null;

	private int type = 0;

	private int count = 0;

	private int dir = 0;

	public ChangeFire(int x, int y) {
		super(x, y, 0, 0, null);
		this.speed = 1;
		random = new Random();
		type = random.nextInt(5);
		this.showImage = StaticValue.allChangeFire.get(type);
		this.width = showImage.getWidth();
		this.height = showImage.getHeight();
	}

	public int getType() {
		return type;
	}

	@Override
	public void paint(Graphics g) {
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
