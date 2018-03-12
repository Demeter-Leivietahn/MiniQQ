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
		// 该线程可以接收客户端的信息
		try {
			while (true) {
				// 从客户端A接收
				ois = new ObjectInputStream(s.getInputStream());
				Message mesg = (Message) ois.readObject();
				
				//聊天消息
				if(mesg.getMesgType().equals(MessageType.MESSAGE_CHAT)){
					System.out.println(mesg.getSender() + "给" + mesg.getReceiver() + "内容为：" + mesg.getContent());
	
	                // 发送给客户端B
					//群消息
					if(mesg.getReceiver().length()>=3 && mesg.getReceiver().substring(0, 3).equals("All")){
						String mesgReceiverAcc = mesg.getReceiverAccount();
						String []receiverAcc = mesgReceiverAcc.split(" ");
						for(int i=0;i<receiverAcc.length;i++){
							ServerConClientChatThread sccct = ManageServerConClientChatThread
									.getServerConClientThread(receiverAcc[i] + MessageType.MESSAGE_CHAT);
			
							//如果对方在线(打开了聊天框)  直接发过去
							if(sccct != null){  				
							oos = new ObjectOutputStream(sccct.s.getOutputStream());
							oos.writeObject(mesg);
							} else{
								ManageOffLineMessage.addMessage(mesg);
							}
						}
					}
					//单人聊天
					else{
						ServerConClientChatThread sccct = ManageServerConClientChatThread
								.getServerConClientThread(mesg.getReceiverAccount() + MessageType.MESSAGE_CHAT);
		
						System.out.println("接收人是：" + mesg.getReceiverAccount());
		
						if(sccct != null){  //如果对方在线(打开了聊天框)  直接发过去
						oos = new ObjectOutputStream(sccct.s.getOutputStream());
						oos.writeObject(mesg);
						} else{
							ManageOffLineMessage.addMessage(mesg);
						}
					}
				}
				
				//用户上线
				if(mesg.getMesgType().equals(MessageType.MESSAGE_ONLINE)){
System.out.println("上线啦!");					
//System.out.println("消息条数: " + ManageOffLineMessage.getSize());
					for(int i=0;i<ManageOffLineMessage.getSize();i++){
//System.out.println("第" + i + "条");						
						Message m = ManageOffLineMessage.getMessage(i);
//System.out.println(mesg.getSenderAccount());
						//如果消息的接收者和发送上线消息的发送者相同
						if(m.getReceiverAccount().equals(mesg.getSenderAccount())){
							//发送消息
							ServerConClientChatThread sccct = ManageServerConClientChatThread
									.getServerConClientThread(m.getReceiverAccount() + MessageType.MESSAGE_CHAT);
//System.out.println(sccct);							
							oos = new ObjectOutputStream(sccct.s.getOutputStream());
							oos.writeObject(m);
//System.out.println(m.getContent()+" "+m.getMesgType());							
							//发送后在list中移除消息
							ManageOffLineMessage.removeMessage(i--);
			
						}
					}
				}
				
				//文件消息
				if(mesg.getMesgType().equals(MessageType.MESSAGE_FILE)){
					//转发给客户端B
					ServerConClientChatThread sccct = ManageServerConClientChatThread
					.getServerConClientThread(mesg.getReceiverAccount() + MessageType.MESSAGE_CHAT);
					
					System.out.println("文件消息！接收人是：" + mesg.getReceiverAccount());
					
					if(sccct != null){  //如果对方在线
					oos = new ObjectOutputStream(sccct.s.getOutputStream());
					oos.writeObject(mesg);
					}else{
						ManageOffLineMessage.addMessage(mesg);
					}
				}
				
				//视频消息
				if(mesg.getMesgType().equals(MessageType.MESSAGE_MOVIE)){
					//转发给客户端B
					ServerConClientChatThread sccct = ManageServerConClientChatThread
					.getServerConClientThread(mesg.getReceiverAccount() + MessageType.MESSAGE_CHAT);
					
					System.out.println("视频消息！接收人是：" + mesg.getReceiverAccount());
					
					if(sccct != null){  //如果对方在线
					oos = new ObjectOutputStream(sccct.s.getOutputStream());
					oos.writeObject(mesg);
					}
				}
				
				//图片消息
				if(mesg.getMesgType().equals(MessageType.MESSAGE_PHOTO)){
					//转发给客户端B
					ServerConClientChatThread sccct = ManageServerConClientChatThread
					.getServerConClientThread(mesg.getReceiverAccount() + MessageType.MESSAGE_CHAT);
					
					System.out.println("图片消息！接收人是：" + mesg.getReceiverAccount());
					
					if(sccct != null){  //如果对方在线
					oos = new ObjectOutputStream(sccct.s.getOutputStream());
					oos.writeObject(mesg);
					}else{
						
					}
				}
				
				//画图消息
				if(mesg.getMesgType().equals(MessageType.MESSAGE_PAINT)){
					//转发给客户端B
					ServerConClientChatThread sccct = ManageServerConClientChatThread
					.getServerConClientThread(mesg.getReceiverAccount() + MessageType.MESSAGE_CHAT);
					
//					System.out.println("画图消息！接收人是：" + mesg.getReceiverAccount());
					
					if(sccct != null){  //如果对方在线
					oos = new ObjectOutputStream(sccct.s.getOutputStream());
					oos.writeObject(mesg);
					}
				}
				
				//用户信息更新
				if(mesg.getMesgType().equals(MessageType.MESSAGE_UPDATE)){
					String name = mesg.getSender();
					String signature = mesg.getContent();
					String headAddress = mesg.getReceiver();//发送者是更新的昵称 内容是签名 接收者是头像地址
					String userAcc = mesg.getSenderAccount();
					DataBase.changeUserInfo(name, signature, headAddress, userAcc);
				}
			}
		} catch (IOException e) {
//			e.printStackTrace();
			System.out.println("客户端退出");
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
