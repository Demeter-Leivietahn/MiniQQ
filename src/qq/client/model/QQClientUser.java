package qq.client.model;

import qq.common.User;

public class QQClientUser {
	QQClient client = new QQClient();
	/**
	 * 检验登陆合法性
	 * @param account
	 * @param password
	 * @return
	 */
	public boolean checkUser(User user){
		return client.sendLoginInfoToServer(user);
	}
	
	
/*	public static void main(String []args){
		System.out.println(new QQClientUser().checkUser(new User()));
	}*/
}
