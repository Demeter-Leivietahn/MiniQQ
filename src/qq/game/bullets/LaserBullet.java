package qq.game.bullets;

import qq.game.object.Bullet;
import qq.game.values.Direction;
import qq.game.values.StaticValue;
import qq.game.voice.Voice;

public class LaserBullet extends Bullet {

	public LaserBullet(int x, int y, Direction d) {
		super(x, y, 0, 0, d);
		this.speed = 39;
		this.showImage = StaticValue.laserBulletImage;
		this.width = showImage.getWidth();
		this.height = showImage.getHeight();
		new Voice(1);
	}

}
