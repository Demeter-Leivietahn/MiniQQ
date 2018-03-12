package qq.client.manage;

import java.io.*;
import java.net.*;


import qq.client.ui.ChatFrame;
import qq.client.ui.MainFrame;
import qq.client.ui.MultiChatFrame;
import qq.client.ui.PaintFrame;
import qq.client.ui.PaintFrame.Canva;
import qq.common.Message;
import qq.common.MessageType;

public class ClientConServerChatThread extends Thread {
	
	private Socket s = null;
	private boolean connected = false;
	ObjectInputStream ois;
	
	/**
	 * 得到socket
	 * @return
	 */
	public Socket getS() {
		return s;
	}
	
	/**
	 * 得到Socket并建立通讯链接
	 * @param s
	 */
	public ClientConServerChatThread(Socket s) {
		this.s = s;
	}

	@Override
	public void run() {
		connected = true;
		try {
			while (connected) { //读取从服务器发来的消息
				ois = new ObjectInputStream(s.getInputStream());
				Message mesg = (Message) ois.readObject();
				
				//聊天消息
				if(mesg.getMesgType().equals(MessageType.MESSAGE_CHAT)){
					String str = mesg.getSender() + "对 " + mesg.getReceiver() +" 说 :\n" + mesg.getContent();				
					System.out.println(str);
	                //群聊
					if(mesg.getReceiver().startsWith("All")){
						String receiveGroup = mesg.getReceiver().substring(4, mesg.getReceiver().length()); 
						MultiChatFrame multiChatFrame = ManageMultiChatFrame.getMultiChatFrame(receiveGroup);
						if(multiChatFrame != null)
							multiChatFrame.showMesg(mesg);
					}
					//单人
					else{
						ChatFrame chatFrame = ManageChatFrame.
								getChatFrame(mesg.getReceiverAccount()+" "+mesg.getSenderAccount());
						if(chatFrame != null)
							chatFrame.showMesg(mesg);  //不为空就发送过去  如果空的话再解决
						else{
System.out.println("收到消息");							
							ManageMainFrame.getMainFrame(mesg.getReceiverAccount()).popChatFrame(mesg);
System.out.println(mesg.getSender() + "对 " + mesg.getReceiver() +" 说 :\n" + mesg.getContent());
							while(chatFrame == null){
								chatFrame = ManageChatFrame.
										getChatFrame(mesg.getReceiverAccount()+" "+mesg.getSenderAccount());
							}
							chatFrame.showMesg(mesg);  
						}
					}
				}
				
				//文件消息
				if(mesg.getMesgType().equals(MessageType.MESSAGE_FILE)){
					if(mesg.getContent().substring(0, 4).equals("同意接收")){
						ChatFrame chatFrame = ManageChatFrame.
								getChatFrame(mesg.getReceiverAccount()+" "+mesg.getSenderAccount());
						chatFrame.sendFile(mesg.getContent().substring(4));
					}
					else{
						ChatFrame chatFrame = ManageChatFrame.
								getChatFrame(mesg.getReceiverAccount()+" "+mesg.getSenderAccount());
						while(chatFrame == null){
							chatFrame = ManageChatFrame.
									getChatFrame(mesg.getReceiverAccount()+" "+mesg.getSenderAccount());
						}
						ManageFileSend.chooseDir(mesg);
					}
				}
				
				//视频消息
				if(mesg.getMesgType().equals(MessageType.MESSAGE_MOVIE)){
					if(mesg.getContent().substring(0, 4).equals("同意接收")){
						ChatFrame chatFrame = ManageChatFrame.
								getChatFrame(mesg.getReceiverAccount()+" "+mesg.getSenderAccount());
						chatFrame.sendFile(mesg.getContent().substring(4));
					}
					else{
						ManageFileSend.chooseDir(mesg);
					}
				}
				
				//图片消息
				if(mesg.getMesgType().equals(MessageType.MESSAGE_PHOTO)){
					ChatFrame chatFrame = ManageChatFrame.
							getChatFrame(mesg.getReceiverAccount()+" "+mesg.getSenderAccount());
					if(chatFrame != null)
						chatFrame.showImage(mesg); 
				}
				
				//画图消息
				if(mesg.getMesgType().equals(MessageType.MESSAGE_PAINT)){
					if(mesg.getContent().equals("画图")){
						ChatFrame chatFrame = ManageChatFrame.
								getChatFrame(mesg.getReceiverAccount()+" "+mesg.getSenderAccount());
						if(chatFrame != null)
							chatFrame.openPaintBoard(mesg);
					}
					else{
						PaintFrame paintFrame = ManagePaintFrame.getPaintFrame(mesg.getReceiverAccount());
//System.out.println(mesg.getReceiverAccount());						
//System.out.println(paintFrame);						
						Canva canva = paintFrame.getCanva();
						String[] a = mesg.getContent().split(" ");
System.out.println(mesg.getContent());
System.out.println(a[0]+" " +a[1]+" "+a[2]+" "+a[3]+" "+a[4]+" "+a[5]);						
						int x = Integer.parseInt(a[0]);
						int y = Integer.parseInt(a[1]);
						float stroke = Float.parseFloat(a[2]);
						int r = Integer.parseInt(a[3]);
						int g = Integer.parseInt(a[4]);
						int b = Integer.parseInt(a[5]);
						canva.setPaint(x, y,stroke,r,g,b);
						canva.repaint();
					}
				}
			}
		} catch (EOFException e) { // 此处的catch用于处理关掉客户端时服务器出现的EOF Exception
			System.out.println("客户端已关闭");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally { // finally执行 最稳妥
			try {
				if (s != null)
					s.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		/*	if(clientThread != null)
				clients.remove(this);*/
		}
	}
	

}