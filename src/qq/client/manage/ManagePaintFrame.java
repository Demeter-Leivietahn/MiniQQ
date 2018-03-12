package qq.client.manage;

import java.util.HashMap;

import qq.client.ui.PaintFrame;

public class ManagePaintFrame {
	private static HashMap<String, PaintFrame> hashMap = new HashMap<String, PaintFrame>();
	/**
	 * ����һ���������
	 * @param friendAcc
	 * @param ChatFrame
	 */
	public static void addPaintFrame(String userAcc, PaintFrame paintFrame) {
		hashMap.put(userAcc, paintFrame);
	}
	/**
	 * ��ȡһ���������
	 * @param friendAcc
 	 * @return
	 */
	public static PaintFrame getPaintFrame(String userAcc) {
		return hashMap.get(userAcc);
	}
	
	
	public static void removePaintFrame(String userAcc){
		hashMap.remove(userAcc);
	}
	/**
	 * �õ�map��size
	 */
	public static int getSize(){
		return hashMap.size();
	}
}
