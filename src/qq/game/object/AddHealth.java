package qq.game.object;

import java.awt.Graphics;
import java.util.Random;

import qq.game.frame.RaidenFrame;
import qq.game.values.StaticValue;

public class AddHealth extends GameObject {

	private int type = 0;

	private int count = 0;

	private int dir = 0;

	public AddHealth(int x, int y) {
		super(x, y, 0, 0, null);
		this.speed = 1;
		type = new Random().nextInt(1);
		this.showImage = StaticValue.addHp.get(type);
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
			dir = new Random().nextInt(50) % 2;
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
