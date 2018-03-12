package qq.client.model;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import qq.client.manage.ClientConServerChatThread;
import qq.client.manage.ClientConServerFileThread;
import qq.client.manage.ManageClientConServerChatThread;
import qq.client.manage.ManageClientConServerFileThread;
import qq.common.Message;
import qq.common.MessageType;
import qq.common.User;

public class QQClient {

	public  Socket s = null;
	private DataInputStream dis = null;
	private ObjectOutputStream oos = null;
	

	/**
	 * ������������û���½��Ϣ
	 * @param user
	 * @return
	 */
	public boolean sendLoginInfoToServer(User user) {
		connect();
		boolean login = false;
		try {
			oos = new ObjectOutputStream(s.getOutputStream());
			//�����������͵�½��Ϣ
			Message mesg = new Message();
			mesg.setMesgType(MessageType.MESSAGE_LOGIN);
			oos.writeObject(mesg);
			//����user(������û�)
			oos.writeObject(user);
			//�õ�����������
			dis = new DataInputStream(s.getInputStream());
			login = dis.readBoolean();
			if(login){
				//�����ͻ��˺ͷ��������ӵ�ͨѶ�߳�
				ClientConServerChatThread ccsct = new ClientConServerChatThread(s);
				//ͨѶ�̼߳��뼯��
				ManageClientConServerChatThread.
				addClientConServerThread(user.getAccount() + MessageType.MESSAGE_CHAT, ccsct);
				//����ͨѶ�߳�
				ccsct.start();
			} else{
				disconnect();
				oos.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return login;
	}
	
	public void createFileThread(User user){
		connect();
		try {
			oos = new ObjectOutputStream(s.getOutputStream());
			//�����ļ��������Ϣ
			Message mesg = new Message(); mesg.setMesgType(MessageType.MESSAGE_FILE);
			oos.writeObject(mesg);
			//����user�û�(���ڼ���map)
			oos.writeObject(user);
			//�����ͻ��˺ͷ��������ӵ��ļ������߳�
			ClientConServerFileThread ccsft = new ClientConServerFileThread(s);
			//�ļ������̼߳��뼯��
			ManageClientConServerFileThread.
			addClientConServerThread(user.getAccount() + MessageType.MESSAGE_FILE, ccsft);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * ���ӷ�����
	 */
	private void connect() {
		try {
			s = new Socket("127.0.0.1", 8888);// ����Ҫָ������ip�Ͷ˿ں�
			System.out.println("����������ӳɹ�!");
		} catch (UnknownHostException e) {
			System.out.println("�Ҳ�������");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("�����������");
			e.printStackTrace();
		}
	}
	/**
	 * �Ͽ�����
	 */
	public void disconnect() {
		try {
			dis.close();
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*public static void main(String []args){
		QQClient client = new QQClient();
//		chatFrame = new ChatFrame();
//		LoginFrame loginFrame = new LoginFrame();
		client.connect();
//		chatFrame.launchChatFrame(client);
//		loginFrame.launchLoginFrame(client);
	}*/
	

}
