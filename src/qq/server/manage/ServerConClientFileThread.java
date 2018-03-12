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
		//从客户端A接受文件
		try {
			//先读取消息
			ois = new ObjectInputStream(s.getInputStream());
			Message mesg = (Message)ois.readObject();
			
			//获取文件名和长度
			dis = new DataInputStream(s.getInputStream());
			String fileName = dis.readUTF();
			long fileLength = dis.readLong();
			File directory = new File("D:/MiniQQServerSave");
			if (!directory.exists()) {
				directory.mkdir();
			}
			file = new File(directory.getAbsolutePath() + File.separatorChar + fileName);
			fos = new FileOutputStream(file);

			// 开始接收文件
			byte[] bytes = new byte[1024];
			int length = 0;
			//读入和长度不想等的时候就是最后一波数据了，刚好1024，最后一波为空
			while ((length = dis.read(bytes, 0, bytes.length)) == bytes.length) {
				fos.write(bytes, 0, length);
				fos.flush();
			}
			//在此处再读一次
			fos.write(bytes, 0, length);
			fos.flush();
			System.out.println("======== 文件接收成功 [File Name：" + fileName + "] [Size：" + getFormatFileSize(fileLength)
					+ "] ========");
		
		
			//发送给客户端B
			//发送文件名和长度
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
	
			// 开始传输文件
			System.out.println("======== 开始传输文件 ========(S→C)");
			byte[] bytes2 = new byte[1024];
			int length2 = 0;
			long progress = 0;
			while ((length2 = fis.read(bytes2, 0, bytes2.length)) != -1) {
				dos.write(bytes2, 0, length2);
				dos.flush();
				progress += length2;
				System.out.print("| " + (100 * progress / file.length()) + "% |");
			}
			System.out.println("\n======== 文件传输成功 ========(S→C)");
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
	 * 格式化文件大小
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
