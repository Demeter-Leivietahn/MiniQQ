package qq.client.manage;


import java.util.HashMap;

public class ManageClientConServerChatThread {
		/**
		 * �ͻ�������������ӵ�Map
		 */
		private static HashMap<String, ClientConServerChatThread> hashMap = new HashMap<String, ClientConServerChatThread>();

		/**
		 * �Ѵ����õ�ClientConServerThread�������hashmap
		 * @param userAcc
		 * @param thread
		 */
		public static void addClientConServerThread(String userAcc, ClientConServerChatThread thread) {
			hashMap.put(userAcc, thread);
		}

		/**
		 * ͨ��userAccȡ���߳�
		 * @param userAcc
		 * @return
		 */
		public static ClientConServerChatThread getClientServerThread(String userAcc) {
			return (ClientConServerChatThread) hashMap.get(userAcc);
		}
		
		/**
		 * �õ�map��size
		 */
		public static int getSize(){
			return hashMap.size();
		}
}
