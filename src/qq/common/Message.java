package qq.common;

import java.io.Serializable;

public class Message implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String mesgType;
	
	private int friendId;

	private String sender;	
	
	private String receiver;	
	
	private String content;	
	
	private String time;
	
	private String receiverAccount;
	
	private String senderAccount;
	
	public String getSenderAccount() {
		return senderAccount;
	}

	public void setSenderAccount(String senderAccount) {
		this.senderAccount = senderAccount;
	}

	public String getMesgType() {
		return mesgType;
	}

	public void setMesgType(String mesgType) {
		this.mesgType = mesgType;
	}
	
	public int getFriendId() {
		return friendId;
	}

	public void setFriendId(int friendId) {
		this.friendId = friendId;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setReceiverAccount(String receiverAccount) {
		this.receiverAccount  = receiverAccount;
	}
	
	public String getReceiverAccount(){
		return receiverAccount;
	}


}
