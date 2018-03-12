package qq.game.bullets;

import qq.game.object.Bullet;
import qq.game.values.Direction;
import qq.game.values.StaticValue;

public class LittleBullet extends Bullet {
	public LittleBullet(int x, int y, Direction d) {
		super(x, y, 0, 0, d);
		this.speed = 20;
		showImage = StaticValue.littleBulletImage;
		this.width = showImage.getWidth();
		this.height = showImage.getHeight();
	}

}
