package qq.client.manage;

import java.util.HashMap;


import qq.client.ui.ChatFrame;


public class ManageChatFrame {
	private static HashMap<String, ChatFrame> hashMap = new HashMap<String, ChatFrame>();
	/**
	 * ����һ���������
	 * @param friendAcc
	 * @param ChatFrame
	 */
	public static void addChatFrame(String userAccAndFriendAcc, ChatFrame chatFrame) {
		hashMap.put(userAccAndFriendAcc, chatFrame);
	}
	/**
	 * ��ȡһ���������
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
	 * �õ�map��size
	 */
	public static int getSize(){
		return hashMap.size();
	}
}
