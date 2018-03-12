package qq.server.manage;

import java.util.ArrayList;

import qq.common.Message;

public class ManageOffLineMessage {
	public static ArrayList<Message> al = new ArrayList<Message>();
	
	public static void addMessage(Message mesg){
		al.add(mesg);
	}
	
	public static Message getMessage(int index){
		return al.get(index);
	}
	
	public static void removeMessage(int index){
		al.remove(al.get(index));
	}
	public static int getSize(){
		return al.size();
	}
}
