package qq.server.manage;

import java.util.HashMap;

public class ManageServerConClientFileThread {
	public static HashMap<String, ServerConClientFileThread> hashMap = 
			new HashMap<String, ServerConClientFileThread>();

	/**
	 * ��map���һ���ͻ����ļ������߳�
	 * 
	 * @param userAcc
	 * @param scct
	 */
	public static void addServerConClientThread(String userAcc, ServerConClientFileThread sccft) {
		hashMap.put(userAcc, sccft);
	}

	/**
	 * ����userAcc��ȡһ���ͻ����ļ������߳�
	 * 
	 * @param userAcc
	 * @return
	 */
	public static ServerConClientFileThread getServerConClientThread(String userAcc) {
		return hashMap.get(userAcc);
	}
	
	/**
	 * �õ�map��size
	 */
	public static int getSize(){
		return hashMap.size();
	}
}
