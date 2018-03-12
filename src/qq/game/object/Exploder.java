package qq.game.object;

import java.awt.Graphics;

import qq.game.values.StaticValue;
import qq.game.voice.Voice;

public class Exploder extends GameObject {

	private int times = 0;

	public Exploder(int x, int y) {
		super(x, y, 0, 0, null);
		// TODO Auto-generated constructor stub
		new Voice(0);
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(StaticValue.allExplode.get(times++), this.x, this.y, null);
		if (times >= 20) {
			this.die();
		}

	}

	@Override
	public void move() {
		// TODO Auto-generated method stub

	}

}
