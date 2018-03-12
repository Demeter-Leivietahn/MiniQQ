package qq.game.values;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class StaticValue {

	public static BufferedImage heroImage = null;

	public static BufferedImage littleBulletImage = null;

	public static BufferedImage laserBulletImage = null;

	public static BufferedImage enemyBulletImage = null;

	public static BufferedImage bgImage = null;

	public static BufferedImage panel = null;

	public static BufferedImage gameover = null;

	public static List<BufferedImage> allEnemyImage = new ArrayList<BufferedImage>();

	public static List<BufferedImage> allChangeFire = new ArrayList<BufferedImage>();

	public static List<BufferedImage> allNumImage = new ArrayList<BufferedImage>();

	public static List<BufferedImage> allExplode = new ArrayList<BufferedImage>();

	public static BufferedImage hp = null;

	public static BufferedImage life = null;

	public static BufferedImage goOn = null;

	public static BufferedImage startbg = null;

	public static BufferedImage enter = null;

	public static BufferedImage boss = null;

	public static BufferedImage pass = null;

	public static List<BufferedImage> addHp = new ArrayList<BufferedImage>();

	public static void init() {
		try {
			
			bgImage = ImageIO.read(StaticValue.class.getClassLoader().getResourceAsStream("sky.png"));
			pass = ImageIO.read(StaticValue.class.getClassLoader().getResourceAsStream("pass.png"));
			boss = ImageIO.read(StaticValue.class.getClassLoader().getResourceAsStream("boss.png"));
			startbg = ImageIO.read(StaticValue.class.getClassLoader().getResourceAsStream("startbg.png"));
			enter = ImageIO.read(StaticValue.class.getClassLoader().getResourceAsStream("enter.png"));
			goOn = ImageIO.read(StaticValue.class.getClassLoader().getResourceAsStream("continue.png"));
			gameover = ImageIO.read(StaticValue.class.getClassLoader().getResourceAsStream("gameover.png"));
			hp = ImageIO.read(StaticValue.class.getClassLoader().getResourceAsStream("health.png"));
			life = ImageIO.read(StaticValue.class.getClassLoader().getResourceAsStream("life.png"));
			panel = ImageIO.read(StaticValue.class.getClassLoader().getResourceAsStream("panel.png"));
			heroImage = ImageIO.read(StaticValue.class.getClassLoader().getResourceAsStream("plane.png"));
			littleBulletImage = ImageIO.read(StaticValue.class.getClassLoader().getResourceAsStream("bullet.png"));
			laserBulletImage = ImageIO.read(StaticValue.class.getClassLoader().getResourceAsStream("laser.png"));
			enemyBulletImage = ImageIO.read(StaticValue.class.getClassLoader().getResourceAsStream("enemybullet.png"));
			for (int i = 1; i <= 15; i++) {
				if (i <= 5) {
					allChangeFire.add(ImageIO
							.read(StaticValue.class.getClassLoader().getResourceAsStream("changefire" + i + ".png")));
				}
				addHp.add(ImageIO.read(StaticValue.class.getClassLoader().getResourceAsStream("addhealth.png")));
				allEnemyImage.add(
						ImageIO.read(StaticValue.class.getClassLoader().getResourceAsStream("plane" + i + ".png")));
			}
			for (int i = 0; i <= 9; i++) {
				allNumImage
						.add(ImageIO.read(StaticValue.class.getClassLoader().getResourceAsStream("num" + i + ".png")));
			}
			for (int i = 1; i <= 21; i++) {
				allExplode.add(ImageIO
						.read(StaticValue.class.getClassLoader().getResourceAsStream("explode (" + i + ").png")));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
