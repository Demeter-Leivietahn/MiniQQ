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
		//���ÿͻ���Bѡ�񴢴�·�������������߳�
		ChatFrame chatFrame = ManageChatFrame.
				getChatFrame(mesg.getReceiverAccount()+" "+mesg.getSenderAccount());
		//�õ�����·�� mesg.Content���ļ���
		chatFrame.popFileChoose(mesg);
		
	}
	
	public static void sendFile(File file, User user,Message mesg,String saveDir) {
		new QQClient().createFileThread(user);
		
		ClientConServerFileThread ccsft = ManageClientConServerFileThread.
				getClientConServerThread(user.getAccount() + MessageType.MESSAGE_FILE);
		
			ccsft.setSaveDir(saveDir);
			//�����߳�
			ccsft.start();
		
		
			//�ͻ���A���͸�������
			FileInputStream fis = null;
			DataOutputStream dos = null;
			ObjectOutputStream oos = null;
			try {
				fis = new FileInputStream(file);
				dos = new DataOutputStream(ccsft.getS().getOutputStream());
				oos = new ObjectOutputStream(ccsft.getS().getOutputStream());
				//�ȷ���һ����Ϣ
				oos.writeObject(mesg);
				
				// �ļ����ͳ���
				dos.writeUTF(file.getName());
				dos.flush();
				dos.writeLong(file.length());
				dos.flush();
	
				// ��ʼ�����ļ�
				System.out.println("======== ��ʼ�����ļ� ========(C��S)");
				byte[] bytes = new byte[1024];
				int length = 0;
				long progress = 0;
				while ((length = fis.read(bytes, 0, bytes.length)) != -1) {
					dos.write(bytes, 0, length);
					dos.flush();
					progress += length;
					System.out.print("| " + (100 * progress / file.length()) + "% |");
				}
				System.out.println("\n======== �ļ�����ɹ� ========(C��S)");
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
}
