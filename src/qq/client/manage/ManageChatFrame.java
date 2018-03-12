package qq.client.manage;

import java.util.HashMap;


import qq.client.ui.ChatFrame;


public class ManageChatFrame {
	private static HashMap<String, ChatFrame> hashMap = new HashMap<String, ChatFrame>();
	/**
	 * 加入一个聊天界面
	 * @param friendAcc
	 * @param ChatFrame
	 */
	public static void addChatFrame(String userAccAndFriendAcc, ChatFrame chatFrame) {
		hashMap.put(userAccAndFriendAcc, chatFrame);
	}
	/**
	 * 获取一个聊天界面
	 * @param friendAcc
 	 * @return
	 */
	public static ChatFrame getChatFrame(String userAccAndFriendAcc) {
		return hashMap.get(userAccAndFriendAcc);
	}
	
	public static void removeChatFrame(String userAccAndFriendAcc){
		hashMap.remove(userAccAndFriendAcc);
	}
	/**
	 * 得到map的size
	 */
	public static int getSize(){
		return hashMap.size();
	}
}
