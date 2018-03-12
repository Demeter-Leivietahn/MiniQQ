package qq.game.frame;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import qq.game.values.StaticValue;

public class RaidenFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4714305488509392766L;

	public static final int WINDOW_WIDTH = 800;

	public static final int WINDOW_HEIGHT = 600;

	private Controller controller = null;
	private RaidenFrame rf  = this;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new RaidenFrame();
	}

	// 构造函数
	public RaidenFrame() {
		// 设置窗体属性
		this.setTitle("QQ雷电");
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		this.setIconImage(new ImageIcon("H:/Java/大一下课设/MyThunder/src/plane.png").getImage());
		this.setResizable(false);
		this.setVisible(true);

		// 居中
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;

		this.setLocation((width - RaidenFrame.WINDOW_WIDTH) / 2, (height - RaidenFrame.WINDOW_HEIGHT) / 2);

		StaticValue.init();
		this.init();
	}

	public void draw() {
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		//设置缓冲图层
		BufferedImage bufImage = new BufferedImage(800, 600, BufferedImage.TYPE_3BYTE_BGR);
		Graphics g2 = bufImage.getGraphics();
		if (controller != null) {
			controller.paint(g2);
		}
		g.drawImage(bufImage, 0, 0, this);
	}

	// 游戏初始化
	public void init() {
		controller = new Controller(this);
		this.addKeyListener(controller);
	}

}
