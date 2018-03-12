package qq.client.manage;

import java.util.HashMap;

import qq.client.ui.MainFrame;

public class ManageMainFrame {
	private static HashMap<String, MainFrame> hashMap = new HashMap<String, MainFrame>();
	/**
	 * ����һ���������
	 * @param userAcc
	 * @param mainFrame
	 */
	public static void addMainFrame(String userAcc, MainFrame mainFrame) {
		hashMap.put(userAcc, mainFrame);
	}
	/**
	 * ��ȡһ���������
	 * @param userAcc
 	 * @return
	 */
	public static MainFrame getMainFrame(String userAcc) {
		return hashMap.get(userAcc);
	}
	
	/**
	 * �õ�map��size
	 */
	public static int getSize(){
		return hashMap.size();
	}
}
