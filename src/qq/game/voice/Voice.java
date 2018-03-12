package qq.game.voice;

import java.io.IOException;
import java.io.InputStream;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Voice implements Runnable {
	private int type = 0;

	public Voice(int type) {
		this.type = type;
		new Thread(this).start();
	}

	public void run() {
		// TODO Auto-generated method stub
		InputStream in = null;
		if (type == 0) {
			in = Voice.class.getClassLoader().getResourceAsStream("explode.au");
		}
		if (type == 1) {
			in = Voice.class.getClassLoader().getResourceAsStream("fire.au");
		}
		if (type == 2) {
			in = Voice.class.getClassLoader().getResourceAsStream("dang.au");
		}
		try {
			AudioStream as = new AudioStream(in);
			AudioPlayer.player.start(as);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
