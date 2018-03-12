package qq.client.manage;

import java.util.ArrayList;

import qq.common.Friend;
import qq.server.db.DataBase;

public class ManageQQGroupMemberList {
	public static ArrayList<Friend> getGroupMemberList(int groupId){
		ArrayList<Friend> groupMemberList = DataBase.getGroupMemberList(groupId);
		return groupMemberList;
	}
}
