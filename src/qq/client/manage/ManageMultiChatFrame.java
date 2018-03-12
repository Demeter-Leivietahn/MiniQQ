package qq.client.manage;

import java.util.HashMap;

import qq.client.ui.MultiChatFrame;;

public class ManageMultiChatFrame {
	private static HashMap<String, MultiChatFrame> hashMap = new HashMap<String, MultiChatFrame>();
	/**
	 * 加入一个聊天界面
	 * @param friendAcc
	 * @param MultiChatFrame
	 */
	public static void addMultiChatFrame(String groupName, MultiChatFrame multiChatFrame) {
		hashMap.put(groupName, multiChatFrame);
	}
	/**
	 * 获取一个聊天界面
	 * @param friendAcc
 	 * @return
	 */
	public static MultiChatFrame getMultiChatFrame(String groupName) {
		return hashMap.get(groupName);
	}
	
	/**
	 * 得到map的size
	 */
	public static int getSize(){
		return hashMap.size();
	}
}
