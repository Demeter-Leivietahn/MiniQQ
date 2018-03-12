package qq.client.ui;
import javax.swing.*;

import qq.client.manage.ManageMainFrame;
import qq.common.User;

import java.awt.*;
import java.lang.management.ManagementFactory;
import java.net.*;

//������������ 

public class SplashScreen extends JWindow implements Runnable {
	private static final long serialVersionUID = 1L;

	Thread splashThread; // �����������߳�
	JProgressBar progress; // ������
	User user;
	JFrame loginFrame;
	public SplashScreen(User user,JFrame loginFrame) {
		this.user = user;
		this.loginFrame = loginFrame;
		Container container = getContentPane(); // �õ�����
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); // ���ù��
		container.add(new JLabel(new ImageIcon("img/��½��ͼ_����.jpg")), BorderLayout.NORTH); // ����ͼƬ
		container.add(new JLabel(new ImageIcon("img/��½�ȴ�.jpg")), BorderLayout.SOUTH); // ����ͼƬ
		progress = new JProgressBar(1, 100); // ʵ����������
		progress.setStringPainted(true); // �������
		progress.setString("��½��..."); // ������ʾ����
		progress.setBackground(Color.white); // ���ñ���ɫ
		container.add(progress, BorderLayout.CENTER); // ���ӽ�������������

		Dimension screen = getToolkit().getScreenSize(); // �õ���Ļ�ߴ�
		pack(); // ������Ӧ����ߴ�
		setLocation(loginFrame.getX(),loginFrame.getY()); // ���ô���λ��
	}

	public void start() {
		this.toFront(); // ����ǰ����ʾ
		splashThread = new Thread(this); // ʵ�����߳�
		splashThread.start(); // ��ʼ�����߳�
	}

	public void run() {
		setVisible(true); // ��ʾ����
		setAlwaysOnTop(rootPaneCheckingEnabled);// ����дtrue,���ִ�����ǰ
		try {
			for (int i = 0; i <= 50; i++) {
				Thread.sleep(20); // �߳�����
				progress.setValue(progress.getValue() + 2); // ���ý�����ֵ
			}
			
			ManageMainFrame.addMainFrame(user.getAccount(), new MainFrame());
			ManageMainFrame.getMainFrame(user.getAccount()).launchMainFrame(user);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		dispose(); // �ر���������
	}
}
