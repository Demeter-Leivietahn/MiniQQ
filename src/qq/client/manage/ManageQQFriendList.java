package qq.client.manage;

import java.util.ArrayList;

import qq.common.Friend;
import qq.server.db.DataBase;

public class ManageQQFriendList {
	public static ArrayList<Friend> getFriendList(/*int userId*/){
		ArrayList<Friend> friendList = DataBase.getFriendList(/*userId*/);
		return friendList;
	}
}
