package qq.server.manage;

import java.util.HashMap;

public class ManageServerConClientChatThread {
	// �ͻ���ͨ���̼߳���
	public static HashMap<String, ServerConClientChatThread> hashMap = new HashMap<String, ServerConClientChatThread>();

	/**
	 * ��map���һ���ͻ���ͨѶ�߳�
	 * @param userAcc
	 * @param scct
	 */
	public static void addServerConClientThread(String userAcc, ServerConClientChatThread scct) {
		hashMap.put(userAcc, scct);
	}

	/**
	 * ����userAcc��ȡһ���ͻ���ͨѶ�߳�
	 * @param userAcc
	 * @return
	 */
	public static ServerConClientChatThread getServerConClientThread(String userAcc) {
		return hashMap.get(userAcc);
	}
	
	/**
	 * �õ�map��size
	 */
	public static int getSize(){
		return hashMap.size();
	}
}
