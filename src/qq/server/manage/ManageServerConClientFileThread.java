package qq.server.manage;

import java.util.HashMap;

public class ManageServerConClientFileThread {
	public static HashMap<String, ServerConClientFileThread> hashMap = 
			new HashMap<String, ServerConClientFileThread>();

	/**
	 * 向map添加一个客户端文件传输线程
	 * 
	 * @param userAcc
	 * @param scct
	 */
	public static void addServerConClientThread(String userAcc, ServerConClientFileThread sccft) {
		hashMap.put(userAcc, sccft);
	}

	/**
	 * 根据userAcc获取一个客户端文件传输线程
	 * 
	 * @param userAcc
	 * @return
	 */
	public static ServerConClientFileThread getServerConClientThread(String userAcc) {
		return hashMap.get(userAcc);
	}
	
	/**
	 * 得到map的size
	 */
	public static int getSize(){
		return hashMap.size();
	}
}
