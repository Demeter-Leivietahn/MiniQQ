package qq.client.manage;

import java.util.HashMap;

public class ManageClientConServerFileThread {
	/**
	 * �ͻ�������������ӵ�Map
	 */
	private static HashMap<String, ClientConServerFileThread> hashMap =
			new HashMap<String, ClientConServerFileThread>();

	/**
	 * �Ѵ����õ�ClientConServerThread�������hashmap
	 * 
	 * @param userAcc
	 * @param thread
	 */
	public static void addClientConServerThread(String userAcc, ClientConServerFileThread thread) {
		hashMap.put(userAcc, thread);
	}

	/**
	 * ͨ��userAccȡ���߳�
	 * 
	 * @param userAcc
	 * @return
	 */
	public static ClientConServerFileThread getClientConServerThread(String userAcc) {
		return (ClientConServerFileThread) hashMap.get(userAcc);
	}
	/**
	 * �õ�map��size
	 */
	public static int getSize(){
		return hashMap.size();
	}
	
	

}
