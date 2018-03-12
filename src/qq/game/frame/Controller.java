package qq.game.frame;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import qq.game.bullets.EnemyBullet;
import qq.game.bullets.LaserBullet;
import qq.game.bullets.LittleBullet;
import qq.game.object.AddHealth;
import qq.game.object.Boss;
import qq.game.object.ChangeFire;
import qq.game.object.Enemy;
import qq.game.object.Exploder;
import qq.game.object.GameObject;
import qq.game.object.Hero;
import qq.game.values.Direction;
import qq.game.values.State;
import qq.game.values.StaticValue;
import qq.game.view.BackGround;
import qq.game.voice.Voice;

public class Controller implements Runnable, KeyListener {

	private BackGround bg = null;

	private Hero hero = null;

	private List<GameObject> allGameObject = new ArrayList<GameObject>();

	private boolean FIRE = false;

	private RaidenFrame frame = null;

	private int life = 3;

	private Thread t = new Thread(this);

	private int bulletType = 0;

	private Boss boss = null;

	private int count = 0;

	private boolean Gaming = true;

	public Controller(RaidenFrame frame) {
		bg = new BackGround();
		this.frame = frame;
		t.start();
	}

	// 接收JFrame引用
	public void recJFrame(RaidenFrame frame) {
		this.frame = frame;
	}

	public void paint(Graphics g) {
		if (this.bg != null) {
			bg.paint(g);
		}
		for (int i = 0; i < this.allGameObject.size(); i++) {
			allGameObject.get(i).paint(g);
		}

		// 绘制命条数
		if (hero != null) {
			for (int i = 0; i < this.life; i++) {
				g.drawImage(StaticValue.life, 650 + i * 40, 560, null);
			}
		}

	}

	public void addGameObject(GameObject go) {
		this.allGameObject.add(go);
	}

	public void reset() {
		hero = new Hero(RaidenFrame.WINDOW_WIDTH / 2, RaidenFrame.WINDOW_HEIGHT - 50);
		this.allGameObject.removeAll(allGameObject);
		this.allGameObject.add(hero);
		this.bulletType = 0;
	}

	@Override
	public void keyPressed(KeyEvent k) {
		// TODO Auto-generated method stub'
		// Esc暂停
		if (k.getKeyCode() == 27) {
			this.Gaming = !this.Gaming;
			if (this.Gaming) {
				new Thread(this).start();
			}
		}
		// 打印键值
		// System.out.println(k.getKeyCode());

		if (hero == null) {
			if (k.getKeyCode() == 10) {
				this.reset();
			}
		}
		if (hero != null) {
			if (k.getKeyCode() == 38) {
				hero.moveUp();
			}
			if (k.getKeyCode() == 40) {
				hero.moveDown();
			}
			if (k.getKeyCode() == 37) {
				hero.moveLeft();
			}
			if (k.getKeyCode() == 39) {
				hero.moveRight();
			}
			if (k.getKeyCode() == 82 && this.life > 0 && hero.isDead()) {
				this.reset();
				this.life--;
			}
			if (k.getKeyCode() == 32) {
				this.FIRE = true;
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent k) {
		// TODO Auto-generated method stub
		if (hero != null) {
			if (k.getKeyCode() == 38) {
				hero.moveUpStop();
			}
			if (k.getKeyCode() == 40) {
				hero.moveDownStop();
			}
			if (k.getKeyCode() == 37) {
				hero.moveLeftStop();
			}
			if (k.getKeyCode() == 39) {
				hero.moveRightStop();
			}
			if (k.getKeyCode() == 32) {
				this.FIRE = false;
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (Gaming) {

			// 触发boss
			if (hero != null && boss == null && this.hero.getScore() == 9999) {
				this.allGameObject.removeAll(allGameObject);
				this.allGameObject.add(hero);
				boss = new Boss(400, 0);
				this.allGameObject.add(boss);
			}
			// 设置游戏状态背景
			if (hero != null) {
				if (hero.isDead()) {
					boss = null;
					// 结束背景
					if (this.life == 0) {
						bg.setState(State.End);
					} else {
						bg.setState(State.Pause);
					}

				} else {
					if (boss != null && boss.isDead()) {
						bg.setState(State.Pass);
					} else {
						bg.setState(State.Playing);
					}
				}
			} else {
				// 开始背景
				bg.setState(State.Start);
			}
			if (hero != null && !hero.isDead()) {
				// 随机产生敌人
				if (boss == null && this.Gaming && new Random().nextInt(1000) > 985 - hero.getScore() / 50) {
					this.allGameObject.add(new Enemy(new Random().nextInt(600) + 100, 0));
				}
				// 敌人开火或BOSS开火
				for (int i = 0; i < this.allGameObject.size(); i++) {
					GameObject go = this.allGameObject.get(i);
					if (go instanceof Enemy) {
						if (new Random().nextInt(100) > 96) {
							this.allGameObject.add(((Enemy) go).fire());
						}
					}
					if (go instanceof Boss) {
						if (new Random().nextInt(100) > 90) {
							this.allGameObject.add(((Boss) go).fire());
						}
					}
				}
				// 随机产生增益物品
				if (new Random().nextInt(1000) > 998 && this.Gaming)
					this.allGameObject.add(new ChangeFire(new Random().nextInt(600) + 100, 0));
				if (new Random().nextInt(1000) > 998 && this.Gaming)
					this.allGameObject.add(new AddHealth(new Random().nextInt(600) + 100, 0));
				// 碰撞检测
				for (int i = 0; i < this.allGameObject.size(); i++) {
					GameObject go1 = allGameObject.get(i);
					if (go1 instanceof Hero) {
						for (int j = 0; j < this.allGameObject.size(); j++) {
							GameObject go2 = allGameObject.get(j);
							// 我军飞机与增益物品相撞
							if (go2 instanceof ChangeFire) {
								if (!go1.getRect().intersection(go2.getRect()).isEmpty()) {
									this.bulletType = ((ChangeFire) go2).getType();
									go2.die();
									((Hero) go1).addScore(100);
								}
							}
							if (go2 instanceof AddHealth) {
								if (!go1.getRect().intersection(go2.getRect()).isEmpty()) {
									if (((AddHealth) go2).getType() == 0) {
										hero.divHealth(-20);
									}
									go2.die();
									((Hero) go1).addScore(100);
								}
							}
							// 我军飞机与敌机相撞
							if (go2 instanceof Enemy) {
								if (!go1.getRect().intersection(go2.getRect()).isEmpty()) {
									go2.die();
									this.allGameObject.add(new Exploder(go2.getX(), go2.getY()));
									((Hero) go1).divHealth(8);
								}
							}
							// 我军飞机与敌军子弹相撞
							if (go2 instanceof EnemyBullet) {
								if (!go1.getRect().intersection(go2.getRect()).isEmpty()) {
									go2.die();
									((Hero) go1).divHealth(4);
								}
							}
							// 我军飞机与敌军Boss相撞
							if (go2 instanceof Boss) {
								if (!go1.getRect().intersection(go2.getRect()).isEmpty()) {
									((Hero) go1).divHealth(8);

								}
							}
						}
					}
					if (go1 instanceof LittleBullet || go1 instanceof LaserBullet) {
						for (int j = 0; j < this.allGameObject.size(); j++) {
							GameObject go2 = allGameObject.get(j);
							/*
							 * //我军飞机子弹与增益物品相撞 if(go2 instanceof ChangeFire){
							 * if(!go1.getRect().intersection(go2.getRect()).
							 * isEmpty()){ go2.die(); hero.addScore(200); } }
							 */
							// 我军飞机子弹与敌机相撞
							if (go2 instanceof Enemy) {
								if (!go1.getRect().intersection(go2.getRect()).isEmpty()) {
									go2.die();
									this.allGameObject.add(new Exploder(go2.getX(), go2.getY()));
									this.hero.addScore(5);
								}
							}
							// 我军子弹与boss相撞
							if (go2 instanceof Boss) {
								if (!go1.getRect().intersection(go2.getRect()).isEmpty()) {
									if (((Boss) go2).divHealth(0.1)) {
										this.allGameObject.add(new Exploder(go2.getX(), go2.getY()));
										this.allGameObject.add(new Exploder(go2.getX() + 100, go2.getY() + 50));
										this.allGameObject.add(new Exploder(go2.getX() + 200, go2.getY()));
										this.allGameObject.add(new Exploder(go2.getX() + 120, go2.getY()));
										this.allGameObject.add(new Exploder(go2.getX() + 40, go2.getY() + 60));
										this.allGameObject.add(new Exploder(go2.getX() + 100, go2.getY() + 50 + 60));
										this.allGameObject.add(new Exploder(go2.getX() + 150, go2.getY() + 60));
										this.allGameObject.add(new Exploder(go2.getX() + 230, go2.getY() + 60));
									}
									new Voice(2);
									go1.die();
								}
							}
						}
					}
				}
				// 开火
				count++;
				if (FIRE && count % 4 == 0) {
					if (this.bulletType == 0) {
						this.allGameObject
								.add(new LittleBullet(hero.getX() + hero.getWidth() / 2, hero.getY(), Direction.U));
					}
					if (this.bulletType == 1) {
						this.allGameObject
								.add(new LittleBullet(hero.getX() + hero.getWidth() / 4, hero.getY(), Direction.U));
						this.allGameObject
								.add(new LittleBullet(hero.getX() + hero.getWidth() * 3 / 4, hero.getY(), Direction.U));
					}
					if (this.bulletType == 2) {
						this.allGameObject
								.add(new LittleBullet(hero.getX() + hero.getWidth() / 2, hero.getY(), Direction.U));
						this.allGameObject
								.add(new LittleBullet(hero.getX() + hero.getWidth() / 2, hero.getY(), Direction.LUU));
						this.allGameObject
								.add(new LittleBullet(hero.getX() + hero.getWidth() / 2, hero.getY(), Direction.RUU));
					}
					if (this.bulletType == 3) {
						this.allGameObject
								.add(new LittleBullet(hero.getX() + hero.getWidth() / 2, hero.getY(), Direction.U));
						this.allGameObject
								.add(new LittleBullet(hero.getX() + hero.getWidth() / 2, hero.getY(), Direction.LU));
						this.allGameObject
								.add(new LittleBullet(hero.getX() + hero.getWidth() / 2, hero.getY(), Direction.RU));
						this.allGameObject
								.add(new LittleBullet(hero.getX() + hero.getWidth() / 2, hero.getY(), Direction.LUU));
						this.allGameObject
								.add(new LittleBullet(hero.getX() + hero.getWidth() / 2, hero.getY(), Direction.RUU));
					}
					if (this.bulletType == 4) {
						this.allGameObject
								.add(new LaserBullet(hero.getX() + hero.getWidth() / 2, hero.getY(), Direction.U));
					}

				}
			}
			// 消除死亡物体
			for (int i = 0; i < this.allGameObject.size(); i++) {
				if (this.allGameObject.get(i).isDead()) {
					this.allGameObject.remove(i);
				}
			}
			// 打印游戏物体数量
			// System.out.println(this.allGameObject.size());

			// 游戏物体 移动
			for (int i = 0; i < this.allGameObject.size(); i++) {
				this.allGameObject.get(i).move();
			}

			frame.draw();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}