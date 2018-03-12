package qq.client.ui;
import javax.swing.*;

import qq.client.manage.ManageMainFrame;
import qq.common.User;

import java.awt.*;
import java.lang.management.ManagementFactory;
import java.net.*;

//程序启动界面 

public class SplashScreen extends JWindow implements Runnable {
	private static final long serialVersionUID = 1L;

	Thread splashThread; // 进度条更新线程
	JProgressBar progress; // 进度条
	User user;
	JFrame loginFrame;
	public SplashScreen(User user,JFrame loginFrame) {
		this.user = user;
		this.loginFrame = loginFrame;
		Container container = getContentPane(); // 得到容器
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); // 设置光标
		container.add(new JLabel(new ImageIcon("img/登陆主图_副本.jpg")), BorderLayout.NORTH); // 增加图片
		container.add(new JLabel(new ImageIcon("img/登陆等待.jpg")), BorderLayout.SOUTH); // 增加图片
		progress = new JProgressBar(1, 100); // 实例化进度条
		progress.setStringPainted(true); // 描绘文字
		progress.setString("登陆中..."); // 设置显示文字
		progress.setBackground(Color.white); // 设置背景色
		container.add(progress, BorderLayout.CENTER); // 增加进度条到容器上

		Dimension screen = getToolkit().getScreenSize(); // 得到屏幕尺寸
		pack(); // 窗口适应组件尺寸
		setLocation(loginFrame.getX(),loginFrame.getY()); // 设置窗口位置
	}

	public void start() {
		this.toFront(); // 窗口前端显示
		splashThread = new Thread(this); // 实例化线程
		splashThread.start(); // 开始运行线程
	}

	public void run() {
		setVisible(true); // 显示窗口
		setAlwaysOnTop(rootPaneCheckingEnabled);// 或者写true,保持窗口最前
		try {
			for (int i = 0; i <= 50; i++) {
				Thread.sleep(20); // 线程休眠
				progress.setValue(progress.getValue() + 2); // 设置进度条值
			}
			
			ManageMainFrame.addMainFrame(user.getAccount(), new MainFrame());
			ManageMainFrame.getMainFrame(user.getAccount()).launchMainFrame(user);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		dispose(); // 关闭启动画面
	}
}
