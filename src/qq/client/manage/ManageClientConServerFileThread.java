package qq.client.manage;

import java.util.HashMap;

public class ManageClientConServerFileThread {
	/**
	 * 客户端与服务器连接的Map
	 */
	private static HashMap<String, ClientConServerFileThread> hashMap =
			new HashMap<String, ClientConServerFileThread>();

	/**
	 * 把创建好的ClientConServerThread对象放入hashmap
	 * 
	 * @param userAcc
	 * @param thread
	 */
	public static void addClientConServerThread(String userAcc, ClientConServerFileThread thread) {
		hashMap.put(userAcc, thread);
	}

	/**
	 * 通过userAcc取得线程
	 * 
	 * @param userAcc
	 * @return
	 */
	public static ClientConServerFileThread getClientConServerThread(String userAcc) {
		return (ClientConServerFileThread) hashMap.get(userAcc);
	}
	/**
	 * 得到map的size
	 */
	public static int getSize(){
		return hashMap.size();
	}
	
	

}
