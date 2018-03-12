package qq.server.manage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import qq.server.db.DataBase;
import qq.server.manage.ManageServerConClientChatThread;
import qq.server.manage.ServerConClientChatThread;

import qq.common.Message;
import qq.common.MessageType;

public class ServerConClientChatThread extends Thread{

	Socket s;
	ObjectInputStream ois;
	ObjectOutputStream oos;
	public ServerConClientChatThread(Socket s) {
		this.s = s;
	}

	@Override
	public void run() {
		// ���߳̿��Խ��տͻ��˵���Ϣ
		try {
			while (true) {
				// �ӿͻ���A����
				ois = new ObjectInputStream(s.getInputStream());
				Message mesg = (Message) ois.readObject();
				
				//������Ϣ
				if(mesg.getMesgType().equals(MessageType.MESSAGE_CHAT)){
					System.out.println(mesg.getSender() + "��" + mesg.getReceiver() + "����Ϊ��" + mesg.getContent());
	
	                // ���͸��ͻ���B
					//Ⱥ��Ϣ
					if(mesg.getReceiver().length()>=3 && mesg.getReceiver().substring(0, 3).equals("All")){
						String mesgReceiverAcc = mesg.getReceiverAccount();
						String []receiverAcc = mesgReceiverAcc.split(" ");
						for(int i=0;i<receiverAcc.length;i++){
							ServerConClientChatThread sccct = ManageServerConClientChatThread
									.getServerConClientThread(receiverAcc[i] + MessageType.MESSAGE_CHAT);
			
							//����Է�����(���������)  ֱ�ӷ���ȥ
							if(sccct != null){  				
							oos = new ObjectOutputStream(sccct.s.getOutputStream());
							oos.writeObject(mesg);
							} else{
								ManageOffLineMessage.addMessage(mesg);
							}
						}
					}
					//��������
					else{
						ServerConClientChatThread sccct = ManageServerConClientChatThread
								.getServerConClientThread(mesg.getReceiverAccount() + MessageType.MESSAGE_CHAT);
		
						System.out.println("�������ǣ�" + mesg.getReceiverAccount());
		
						if(sccct != null){  //����Է�����(���������)  ֱ�ӷ���ȥ
						oos = new ObjectOutputStream(sccct.s.getOutputStream());
						oos.writeObject(mesg);
						} else{
							ManageOffLineMessage.addMessage(mesg);
						}
					}
				}
				
				//�û�����
				if(mesg.getMesgType().equals(MessageType.MESSAGE_ONLINE)){
System.out.println("������!");					
//System.out.println("��Ϣ����: " + ManageOffLineMessage.getSize());
					for(int i=0;i<ManageOffLineMessage.getSize();i++){
//System.out.println("��" + i + "��");						
						Message m = ManageOffLineMessage.getMessage(i);
//System.out.println(mesg.getSenderAccount());
						//�����Ϣ�Ľ����ߺͷ���������Ϣ�ķ�������ͬ
						if(m.getReceiverAccount().equals(mesg.getSenderAccount())){
							//������Ϣ
							ServerConClientChatThread sccct = ManageServerConClientChatThread
									.getServerConClientThread(m.getReceiverAccount() + MessageType.MESSAGE_CHAT);
//System.out.println(sccct);							
							oos = new ObjectOutputStream(sccct.s.getOutputStream());
							oos.writeObject(m);
//System.out.println(m.getContent()+" "+m.getMesgType());							
							//���ͺ���list���Ƴ���Ϣ
							ManageOffLineMessage.removeMessage(i--);
			
						}
					}
				}
				
				//�ļ���Ϣ
				if(mesg.getMesgType().equals(MessageType.MESSAGE_FILE)){
					//ת�����ͻ���B
					ServerConClientChatThread sccct = ManageServerConClientChatThread
					.getServerConClientThread(mesg.getReceiverAccount() + MessageType.MESSAGE_CHAT);
					
					System.out.println("�ļ���Ϣ���������ǣ�" + mesg.getReceiverAccount());
					
					if(sccct != null){  //����Է�����
					oos = new ObjectOutputStream(sccct.s.getOutputStream());
					oos.writeObject(mesg);
					}else{
						ManageOffLineMessage.addMessage(mesg);
					}
				}
				
				//��Ƶ��Ϣ
				if(mesg.getMesgType().equals(MessageType.MESSAGE_MOVIE)){
					//ת�����ͻ���B
					ServerConClientChatThread sccct = ManageServerConClientChatThread
					.getServerConClientThread(mesg.getReceiverAccount() + MessageType.MESSAGE_CHAT);
					
					System.out.println("��Ƶ��Ϣ���������ǣ�" + mesg.getReceiverAccount());
					
					if(sccct != null){  //����Է�����
					oos = new ObjectOutputStream(sccct.s.getOutputStream());
					oos.writeObject(mesg);
					}
				}
				
				//ͼƬ��Ϣ
				if(mesg.getMesgType().equals(MessageType.MESSAGE_PHOTO)){
					//ת�����ͻ���B
					ServerConClientChatThread sccct = ManageServerConClientChatThread
					.getServerConClientThread(mesg.getReceiverAccount() + MessageType.MESSAGE_CHAT);
					
					System.out.println("ͼƬ��Ϣ���������ǣ�" + mesg.getReceiverAccount());
					
					if(sccct != null){  //����Է�����
					oos = new ObjectOutputStream(sccct.s.getOutputStream());
					oos.writeObject(mesg);
					}else{
						
					}
				}
				
				//��ͼ��Ϣ
				if(mesg.getMesgType().equals(MessageType.MESSAGE_PAINT)){
					//ת�����ͻ���B
					ServerConClientChatThread sccct = ManageServerConClientChatThread
					.getServerConClientThread(mesg.getReceiverAccount() + MessageType.MESSAGE_CHAT);
					
//					System.out.println("��ͼ��Ϣ���������ǣ�" + mesg.getReceiverAccount());
					
					if(sccct != null){  //����Է�����
					oos = new ObjectOutputStream(sccct.s.getOutputStream());
					oos.writeObject(mesg);
					}
				}
				
				//�û���Ϣ����
				if(mesg.getMesgType().equals(MessageType.MESSAGE_UPDATE)){
					String name = mesg.getSender();
					String signature = mesg.getContent();
					String headAddress = mesg.getReceiver();//�������Ǹ��µ��ǳ� ������ǩ�� ��������ͷ���ַ
					String userAcc = mesg.getSenderAccount();
					DataBase.changeUserInfo(name, signature, headAddress, userAcc);
				}
			}
		} catch (IOException e) {
//			e.printStackTrace();
			System.out.println("�ͻ����˳�");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
	
	public void close(){
		try {
			if(ois != null)
				ois.close();
			if(oos != null)
				oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
