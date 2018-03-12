package qq.client.manage;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.RoundingMode;
import java.net.Socket;
import java.text.DecimalFormat;



public class ClientConServerFileThread extends Thread{

	private Socket s = null;
	private DataInputStream dis = null;
	private FileOutputStream fos = null;
	private String saveDir = null;
	private DecimalFormat df = null;
	/**
	 * �õ�socket
	 * 
	 * @return
	 */
	public Socket getS() {
		return s;
	}

	/**
	 * �õ�Socket������ͨѶ����
	 * @param s
	 */
	public ClientConServerFileThread(Socket s) {
		this.s = s;
	}

	/**
	 * ���ñ���·��
	 * @param saveDir
	 */
	public void setSaveDir(String saveDir){
		this.saveDir = saveDir;
	}
	
	@Override
	public void run() {
		try {
			//��ȡ�ļ����ͳ���
			dis = new DataInputStream(s.getInputStream());
			String fileName = dis.readUTF();
			long fileLength = dis.readLong();
			File directory = new File(saveDir);
			if (!directory.exists()) {
				directory.mkdir();
			}
			File file = new File(directory.getAbsolutePath() + File.separatorChar + fileName);
			fos = new FileOutputStream(file);

			// ��ʼ�����ļ�
			byte[] bytes = new byte[1024];
			int length = 0;
			while ((length = dis.read(bytes, 0, bytes.length)) != -1) {
				fos.write(bytes, 0, length);
				fos.flush();
			}
			System.out.println("======== �ļ����ճɹ� [File Name��" + fileName + "] [Size��" + getFormatFileSize(fileLength)
					+ "] ========");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null)
					fos.close();
				if (dis != null)
					dis.close();
				if(s != null)
					s.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
		
	}
	/**
	 * ��ʽ���ļ���С
	 * 
	 * @param length
	 * @return
	 */
	private String getFormatFileSize(long length) {
		df = new DecimalFormat("#0.0");
		df.setRoundingMode(RoundingMode.HALF_UP);
		df.setMinimumFractionDigits(1);
		df.setMaximumFractionDigits(1);

		double size = ((double) length) / (1 << 30);
		if (size >= 1) {
			return df.format(size) + "GB";
		}
		size = ((double) length) / (1 << 20);
		if (size >= 1) {
			return df.format(size) + "MB";
		}
		size = ((double) length) / (1 << 10);
		if (size >= 1) {
			return df.format(size) + "KB";
		}
		return length + "B";
	}
}
