package qq.server.model;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import qq.server.manage.ManageServerConClientChatThread;
import qq.server.manage.ManageServerConClientFileThread;
import qq.server.manage.ServerConClientChatThread;
import qq.server.manage.ServerConClientFileThread;
import qq.client.manage.ManageClientConServerChatThread;
import qq.common.Message;
import qq.common.MessageType;
import qq.common.User;
import qq.server.db.DataBase;

public class QQServer {
	
	private static QQServer server;
	
	public static void main(String[] args) {
		server = new QQServer();
		server.start();
	}
	
	/**
	 * ����������
	 */
	public void start(){
		ServerSocket ss = null;
		boolean started = false;
		
		try {
			ss = new ServerSocket(8888); // ָ���˿ں�
		} catch (BindException e) {
			System.out.println("�˿���ռ��");
		} catch (IOException e) {
			System.out.println("����������ʧ��");
			e.printStackTrace();
		}

		try {
			started = true;
			while (started) {
				boolean legal = false;
				//�����þֲ�������ԭ������ΪҪΪÿһ������������ӵĿͻ��˽���һ��������Socket
				Socket s = ss.accept(); 
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				//���ܿͻ�����Ϣ
				Message mesg = (Message)ois.readObject();
				//����ȥһ��user
				User user = (User) ois.readObject();    
				
				//��½��Ϣ  ����½�Ϸ��Բ�����ͨѶ�߳�
				if(mesg.getMesgType().equals(MessageType.MESSAGE_LOGIN)){
					//���user�Ƿ��½�Ϸ�
					legal = DataBase.login(user.getAccount(), user.getPassword());  
					//����Client��sendLoginInfo������½�Ƿ�Ϸ�
					DataOutputStream dos = new DataOutputStream(s.getOutputStream());
					dos.writeBoolean(legal);  
					
					if(legal){
					// ����һ���̣߳��ø��߳���ÿͻ��˱���ͨѶ
					ServerConClientChatThread sccct = new ServerConClientChatThread(s);
					// ����ͨѶ�̼߳���
					ManageServerConClientChatThread.
					addServerConClientThread(user.getAccount() + MessageType.MESSAGE_CHAT, sccct);
					// ������ÿͻ���ͨѶ���߳�
					sccct.start();
					
					System.out.println("�ͻ������ӳɹ� ");// + \n��ǰ���� " + ManageServerConClientChatThread.getSize() + "��");
					}
				}
				
				if(mesg.getMesgType().equals(MessageType.MESSAGE_FILE)){
					// ��һ�������ļ�������߳�
					ServerConClientFileThread sccft = new ServerConClientFileThread(s);
					// �����ļ������̼߳���
					ManageServerConClientFileThread.
					addServerConClientThread(user.getAccount() + MessageType.MESSAGE_FILE, sccft);
					//�����߳�
					sccft.start();
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally { 
			try {
				ss.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * �ͻ����˳�
	 */
	public void clientQuit(){
		System.out.println("��ǰ���� " + (ManageClientConServerChatThread.getSize()) + " ��"); //���ô˷�����ʱ��ûremove ����Ҫ-1
	}
	/**
	 * ����QQClientConServerList
	 * @return
	 *//*
	public List<ClientConServerThread> getList(){
		return clients;
	}*/
}
