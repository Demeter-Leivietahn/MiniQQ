package qq.server.db;

import java.sql.*;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import qq.common.Friend;
import qq.common.User;

public class DataBase {
	/**
	 * JDBC
	 * @return
	 */
	public static Connection getConnection(){
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost/miniqq";
			conn = DriverManager.getConnection(url,"root","root");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * �����˺��Ƿ��ظ�
	 * @param account
	 * @return
	 */
	public static boolean checkAccount(String account){
		Connection conn = null;
		String sql = "select * from user where account=?";
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, account);
			ResultSet rs = pstmt.executeQuery(); 
			rs.last();
//System.out.println("�������� : "+rs.getRow());
			if(rs.getRow() > 0)  //�м�¼��˵���˺Ŵ���
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
		
	}
	
	/**
	 * ע��
	 * @param user
	 * @return
	 */
	public static boolean register(User user){
		Connection conn = null;
		String sql = "insert into user (id,account,password,name,phone,email,head,signature) values(null,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = null;
		boolean flag  = false;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getAccount());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getName());
			pstmt.setString(4, user.getPhone());
			pstmt.setString(5, user.getEmail());
			pstmt.setString(6, user.getHead());
			pstmt.setString(7, user.getSignature());
			flag = pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * login������Ϸ���
	 * @param account
	 * @param password
	 * @return
	 */
	public static boolean login(String account,String password){
		Connection conn = null;
		String sql = "select * from user where account=?";
		PreparedStatement pstmt = null;
		try {			
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, account);
			ResultSet rs = pstmt.executeQuery(); 
			while(rs.next()){
				String correctPswd = rs.getString("password");
				if(correctPswd.equals(password))
					return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * �����ֻ����һ�����
	 * @param phoneNum
	 * @return
	 */
	public static String findPassword(String phoneNum){
		Connection conn = null;
		String sql = "select * from user where phone=?";
		PreparedStatement pstmt = null;
		String str = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, phoneNum);
			ResultSet rs = pstmt.executeQuery(); 
			while(rs.next()){
				str = rs.getString("password");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}
	/**
	 * ����user��account�õ����е���Ϣ
	 * @param account
	 * @return
	 */
	public static User update(String account){
		User user = new User();
		Connection conn = null;
		String sql = "select * from user where account=?";
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, account);
			ResultSet rs = pstmt.executeQuery(); 
			while(rs.next()){
				user.setName(rs.getString("name"));
				user.setAccount(rs.getString("account"));
				user.setPassword(rs.getString("password"));
				user.setPhone(rs.getString("phone"));
				user.setEmail(rs.getString("email"));
				user.setHead(rs.getString("head"));
				user.setSignature(rs.getString("signature"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	/**
	 * ����userAcc������Ϣ
	 * @param name
	 * @param signature
	 * @param headAddress
	 * @param userAcc
	 */
	public static void changeUserInfo(String name,String signature,String headAddress,String userAcc){
		Connection conn = null;
		String sql = "update user set name = ? ,head = ?,signature = ? where account = ? ";
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, headAddress);
			pstmt.setString(3, signature);
			pstmt.setString(4, userAcc);
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ����userId�õ���Ӧ��friend��Ϣ
	 * @param userId
	 * @return
	 */
	public static ArrayList<Friend> getFriendList(/*int userId*/){
		ArrayList<Friend> friendList = new ArrayList<Friend>();
		Friend friend = null;
		Connection conn = null;
		String sql = "select * from user";
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
//			pstmt.setInt(1, userId);
			ResultSet rs = pstmt.executeQuery(); 
			while(rs.next()){
				friend = new Friend();
				friend.setAccount(rs.getString("account"));
				friend.setName(rs.getString("Name"));
				friend.setHead(rs.getString("head"));
				friend.setSignature(rs.getString("signature"));
				friendList.add(friend);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return friendList;
	}
	/**
	 * ����groupId�õ�Ⱥ��Ա��Ϣ
	 * @param groupId
	 * @return
	 */
	public static ArrayList<Friend> getGroupMemberList(int groupId){
		ArrayList<Friend> groupMemberList = new ArrayList<Friend>();
		Friend friend = null;
		Connection conn = null;
		String sql = "select * from user";
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
//			pstmt.setInt(1, groupId);
			ResultSet rs = pstmt.executeQuery(); 
			while(rs.next()){
				friend = new Friend();
				friend.setAccount(rs.getString("account"));
				friend.setName(rs.getString("Name"));
				friend.setHead(rs.getString("head"));
				groupMemberList.add(friend);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return groupMemberList;
	}
	
	/*public static void main(String []args){
		System.out.println(findPassword("100"));
	}*/
}
