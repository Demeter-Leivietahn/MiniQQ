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
	 * 向服务器发送用户登陆信息
	 * @param user
	 * @return
	 */
	public boolean sendLoginInfoToServer(User user) {
		connect();
		boolean login = false;
		try {
			oos = new ObjectOutputStream(s.getOutputStream());
			//给服务器发送登陆消息
			Message mesg = new Message();
			mesg.setMesgType(MessageType.MESSAGE_LOGIN);
			oos.writeObject(mesg);
			//发送user(待检测用户)
			oos.writeObject(user);
			//得到服务器反馈
			dis = new DataInputStream(s.getInputStream());
			login = dis.readBoolean();
			if(login){
				//创建客户端和服务器连接的通讯线程
				ClientConServerChatThread ccsct = new ClientConServerChatThread(s);
				//通讯线程加入集合
				ManageClientConServerChatThread.
				addClientConServerThread(user.getAccount() + MessageType.MESSAGE_CHAT, ccsct);
				//启动通讯线程
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
			//发送文件传输的消息
			Message mesg = new Message(); mesg.setMesgType(MessageType.MESSAGE_FILE);
			oos.writeObject(mesg);
			//发送user用户(用于加入map)
			oos.writeObject(user);
			//创建客户端和服务器连接的文件传输线程
			ClientConServerFileThread ccsft = new ClientConServerFileThread(s);
			//文件传输线程加入集合
			ManageClientConServerFileThread.
			addClientConServerThread(user.getAccount() + MessageType.MESSAGE_FILE, ccsft);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 连接服务器
	 */
	private void connect() {
		try {
			s = new Socket("127.0.0.1", 8888);// 参数要指定主机ip和端口号
			System.out.println("与服务器连接成功!");
		} catch (UnknownHostException e) {
			System.out.println("找不到主机");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("输入输出错误");
			e.printStackTrace();
		}
	}
	/**
	 * 断开连接
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
