package qq.game.bullets;

import java.awt.Graphics;

import qq.game.object.Bullet;
import qq.game.values.Direction;
import qq.game.values.StaticValue;

public class EnemyBullet extends Bullet {

	public EnemyBullet(int x, int y, Direction d) {
		super(x, y, 0, 0, d);
		this.speed = 3;
		this.showImage = StaticValue.enemyBulletImage;
		this.width = showImage.getWidth();
		this.height = showImage.getHeight();
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(showImage, x - width / 2, y, null);
	}
}