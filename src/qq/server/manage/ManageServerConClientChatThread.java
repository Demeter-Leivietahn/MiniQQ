package qq.server.manage;

import java.util.HashMap;

public class ManageServerConClientChatThread {
	// 客户端通信线程集合
	public static HashMap<String, ServerConClientChatThread> hashMap = new HashMap<String, ServerConClientChatThread>();

	/**
	 * 向map添加一个客户端通讯线程
	 * @param userAcc
	 * @param scct
	 */
	public static void addServerConClientThread(String userAcc, ServerConClientChatThread scct) {
		hashMap.put(userAcc, scct);
	}

	/**
	 * 根据userAcc获取一个客户端通讯线程
	 * @param userAcc
	 * @return
	 */
	public static ServerConClientChatThread getServerConClientThread(String userAcc) {
		return hashMap.get(userAcc);
	}
	
	/**
	 * 得到map的size
	 */
	public static int getSize(){
		return hashMap.size();
	}
}
