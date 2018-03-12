package qq.client.manage;

import java.util.HashMap;

import qq.client.ui.MainFrame;

public class ManageMainFrame {
	private static HashMap<String, MainFrame> hashMap = new HashMap<String, MainFrame>();
	/**
	 * 加入一个聊天界面
	 * @param userAcc
	 * @param mainFrame
	 */
	public static void addMainFrame(String userAcc, MainFrame mainFrame) {
		hashMap.put(userAcc, mainFrame);
	}
	/**
	 * 获取一个聊天界面
	 * @param userAcc
 	 * @return
	 */
	public static MainFrame getMainFrame(String userAcc) {
		return hashMap.get(userAcc);
	}
	
	/**
	 * 得到map的size
	 */
	public static int getSize(){
		return hashMap.size();
	}
}
