package qq.client.manage;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;


import qq.client.model.QQClient;
import qq.client.ui.ChatFrame;
import qq.common.Message;
import qq.common.MessageType;
import qq.common.User;

public class ManageFileSend {

	
	public static void chooseDir(Message mesg){
		//先让客户端B选择储存路径并启动接受线程
		ChatFrame chatFrame = ManageChatFrame.
				getChatFrame(mesg.getReceiverAccount()+" "+mesg.getSenderAccount());
		//拿到保存路径 mesg.Content是文件名
		chatFrame.popFileChoose(mesg);
		
	}
	
	public static void sendFile(File file, User user,Message mesg,String saveDir) {
		new QQClient().createFileThread(user);
		
		ClientConServerFileThread ccsft = ManageClientConServerFileThread.
				getClientConServerThread(user.getAccount() + MessageType.MESSAGE_FILE);
		
			ccsft.setSaveDir(saveDir);
			//启动线程
			ccsft.start();
		
		
			//客户端A发送给服务器
			FileInputStream fis = null;
			DataOutputStream dos = null;
			ObjectOutputStream oos = null;
			try {
				fis = new FileInputStream(file);
				dos = new DataOutputStream(ccsft.getS().getOutputStream());
				oos = new ObjectOutputStream(ccsft.getS().getOutputStream());
				//先发送一个消息
				oos.writeObject(mesg);
				
				// 文件名和长度
				dos.writeUTF(file.getName());
				dos.flush();
				dos.writeLong(file.length());
				dos.flush();
	
				// 开始传输文件
				System.out.println("======== 开始传输文件 ========(C→S)");
				byte[] bytes = new byte[1024];
				int length = 0;
				long progress = 0;
				while ((length = fis.read(bytes, 0, bytes.length)) != -1) {
					dos.write(bytes, 0, length);
					dos.flush();
					progress += length;
					System.out.print("| " + (100 * progress / file.length()) + "% |");
				}
				System.out.println("\n======== 文件传输成功 ========(C→S)");
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
}
