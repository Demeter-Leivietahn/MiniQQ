package qq.server.manage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.RoundingMode;
import java.net.Socket;
import java.text.DecimalFormat;

import qq.common.Message;
import qq.common.MessageType;

public class ServerConClientFileThread extends Thread {
	private DataInputStream dis;
	private DataOutputStream dos;
	private FileInputStream fis ;
	private FileOutputStream fos;
	private ObjectInputStream ois;
	
	private DecimalFormat df;
	
	private Socket s;


	public ServerConClientFileThread(Socket s) {
		this.s = s;
	}

	@Override
	public void run() {
		File file = null;
		//�ӿͻ���A�����ļ�
		try {
			//�ȶ�ȡ��Ϣ
			ois = new ObjectInputStream(s.getInputStream());
			Message mesg = (Message)ois.readObject();
			
			//��ȡ�ļ����ͳ���
			dis = new DataInputStream(s.getInputStream());
			String fileName = dis.readUTF();
			long fileLength = dis.readLong();
			File directory = new File("D:/MiniQQServerSave");
			if (!directory.exists()) {
				directory.mkdir();
			}
			file = new File(directory.getAbsolutePath() + File.separatorChar + fileName);
			fos = new FileOutputStream(file);

			// ��ʼ�����ļ�
			byte[] bytes = new byte[1024];
			int length = 0;
			//����ͳ��Ȳ���ȵ�ʱ��������һ�������ˣ��պ�1024�����һ��Ϊ��
			while ((length = dis.read(bytes, 0, bytes.length)) == bytes.length) {
				fos.write(bytes, 0, length);
				fos.flush();
			}
			//�ڴ˴��ٶ�һ��
			fos.write(bytes, 0, length);
			fos.flush();
			System.out.println("======== �ļ����ճɹ� [File Name��" + fileName + "] [Size��" + getFormatFileSize(fileLength)
					+ "] ========");
		
		
			//���͸��ͻ���B
			//�����ļ����ͳ���
			fis = new FileInputStream(file);
//System.out.println("before DOS");			
System.out.println(ManageServerConClientFileThread.getSize());			
			dos = new DataOutputStream(ManageServerConClientFileThread.
					getServerConClientThread(mesg.getSenderAccount() + MessageType.MESSAGE_FILE).
					s.getOutputStream());
//System.out.println("After DOS");			
			dos.writeUTF(file.getName());
			dos.flush();
			dos.writeLong(file.length());
			dos.flush();
	
			// ��ʼ�����ļ�
			System.out.println("======== ��ʼ�����ļ� ========(S��C)");
			byte[] bytes2 = new byte[1024];
			int length2 = 0;
			long progress = 0;
			while ((length2 = fis.read(bytes2, 0, bytes2.length)) != -1) {
				dos.write(bytes2, 0, length2);
				dos.flush();
				progress += length2;
				System.out.print("| " + (100 * progress / file.length()) + "% |");
			}
			System.out.println("\n======== �ļ�����ɹ� ========(S��C)");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(fis != null)		fis.close();
				if(fos != null)		fos.close();
				if(dos != null)		dos.close();
				if(dis != null)		dis.close();
				if(ois != null)     ois.close();
			} catch (IOException e) {
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
