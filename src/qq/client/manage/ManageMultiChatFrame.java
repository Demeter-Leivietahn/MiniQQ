package qq.client.manage;

import java.util.HashMap;

import qq.client.ui.MultiChatFrame;;

public class ManageMultiChatFrame {
	private static HashMap<String, MultiChatFrame> hashMap = new HashMap<String, MultiChatFrame>();
	/**
	 * ����һ���������
	 * @param friendAcc
	 * @param MultiChatFrame
	 */
	public static void addMultiChatFrame(String groupName, MultiChatFrame multiChatFrame) {
		hashMap.put(groupName, multiChatFrame);
	}
	/**
	 * ��ȡһ���������
	 * @param friendAcc
 	 * @return
	 */
	public static MultiChatFrame getMultiChatFrame(String groupName) {
		return hashMap.get(groupName);
	}
	
	/**
	 * �õ�map��size
	 */
	public static int getSize(){
		return hashMap.size();
	}
}
