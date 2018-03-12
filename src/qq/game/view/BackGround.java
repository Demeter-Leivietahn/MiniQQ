package qq.game.view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import qq.game.frame.RaidenFrame;
import qq.game.values.State;
import qq.game.values.StaticValue;

public class BackGround {

	private BufferedImage bgImage = StaticValue.bgImage;

	private BufferedImage gameover = StaticValue.gameover;

	private BufferedImage goOn = StaticValue.goOn;

	private BufferedImage startbg = StaticValue.startbg;

	private BufferedImage enter = StaticValue.enter;

	private BufferedImage pass = StaticValue.pass;

	private State state = null;

	private int y = 0;

	public void paint(Graphics g) {
		if (y >= RaidenFrame.WINDOW_HEIGHT)
			y = 0;
		g.drawImage(bgImage, 0, y, null);
		g.drawImage(bgImage, 0, y - RaidenFrame.WINDOW_HEIGHT, null);
		y++;
		if (state == State.End) {
			g.drawImage(gameover, (RaidenFrame.WINDOW_WIDTH - gameover.getWidth()) / 2,
					(RaidenFrame.WINDOW_HEIGHT - gameover.getHeight()) / 2, null);
		}
		if (state == State.Pause) {
			g.drawImage(goOn, (RaidenFrame.WINDOW_WIDTH - goOn.getWidth()) / 2,
					(RaidenFrame.WINDOW_HEIGHT - goOn.getHeight()) / 2, null);
		}
		if (state == State.Start) {
			g.drawImage(startbg, 0, 0, null);
			g.drawImage(enter, RaidenFrame.WINDOW_WIDTH - enter.getWidth() - 5,
					RaidenFrame.WINDOW_HEIGHT - enter.getHeight() - 10, null);
		}
		if (state == State.Pass) {
			g.drawImage(pass, (RaidenFrame.WINDOW_WIDTH - pass.getWidth()) / 2,
					(RaidenFrame.WINDOW_HEIGHT - pass.getHeight()) / 2, null);
		}
	}

	public void setState(State state) {
		this.state = state;
	}
}
