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
	 * 启动服务器
	 */
	public void start(){
		ServerSocket ss = null;
		boolean started = false;
		
		try {
			ss = new ServerSocket(8888); // 指定端口号
		} catch (BindException e) {
			System.out.println("端口已占用");
		} catch (IOException e) {
			System.out.println("服务器启动失败");
			e.printStackTrace();
		}

		try {
			started = true;
			while (started) {
				boolean legal = false;
				//这里用局部变量的原因是因为要为每一个与服务器连接的客户端建立一个独立的Socket
				Socket s = ss.accept(); 
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				//接受客户端消息
				Message mesg = (Message)ois.readObject();
				//读进去一个user
				User user = (User) ois.readObject();    
				
				//登陆消息  检测登陆合法性并创建通讯线程
				if(mesg.getMesgType().equals(MessageType.MESSAGE_LOGIN)){
					//检测user是否登陆合法
					legal = DataBase.login(user.getAccount(), user.getPassword());  
					//告诉Client的sendLoginInfo方法登陆是否合法
					DataOutputStream dos = new DataOutputStream(s.getOutputStream());
					dos.writeBoolean(legal);  
					
					if(legal){
					// 单开一个线程，让该线程与该客户端保持通讯
					ServerConClientChatThread sccct = new ServerConClientChatThread(s);
					// 加入通讯线程集合
					ManageServerConClientChatThread.
					addServerConClientThread(user.getAccount() + MessageType.MESSAGE_CHAT, sccct);
					// 启动与该客户端通讯的线程
					sccct.start();
					
					System.out.println("客户端连接成功 ");// + \n当前在线 " + ManageServerConClientChatThread.getSize() + "人");
					}
				}
				
				if(mesg.getMesgType().equals(MessageType.MESSAGE_FILE)){
					// 开一个用于文件传输的线程
					ServerConClientFileThread sccft = new ServerConClientFileThread(s);
					// 加入文件传输线程集合
					ManageServerConClientFileThread.
					addServerConClientThread(user.getAccount() + MessageType.MESSAGE_FILE, sccft);
					//启动线程
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
	 * 客户端退出
	 */
	public void clientQuit(){
		System.out.println("当前在线 " + (ManageClientConServerChatThread.getSize()) + " 人"); //调用此方法的时候还没remove 所以要-1
	}
	/**
	 * 返回QQClientConServerList
	 * @return
	 *//*
	public List<ClientConServerThread> getList(){
		return clients;
	}*/
}
