package qq.client.manage;


import java.util.HashMap;

public class ManageClientConServerChatThread {
		/**
		 * 客户端与服务器连接的Map
		 */
		private static HashMap<String, ClientConServerChatThread> hashMap = new HashMap<String, ClientConServerChatThread>();

		/**
		 * 把创建好的ClientConServerThread对象放入hashmap
		 * @param userAcc
		 * @param thread
		 */
		public static void addClientConServerThread(String userAcc, ClientConServerChatThread thread) {
			hashMap.put(userAcc, thread);
		}

		/**
		 * 通过userAcc取得线程
		 * @param userAcc
		 * @return
		 */
		public static ClientConServerChatThread getClientServerThread(String userAcc) {
			return (ClientConServerChatThread) hashMap.get(userAcc);
		}
		
		/**
		 * 得到map的size
		 */
		public static int getSize(){
			return hashMap.size();
		}
}
