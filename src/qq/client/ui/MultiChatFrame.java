package qq.client.ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import qq.client.manage.ManageClientConServerChatThread;
import qq.client.manage.ManageQQGroupMemberList;
import qq.common.Friend;
import qq.common.Message;
import qq.common.MessageType;
import qq.common.User;

import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
public class MultiChatFrame extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	MultiChatFrame multiChatFrame = this;
	ArrayList<Friend> groupMemberList;
	
	
	static final int WIDTH = 1000, HEIGHT = 850;
	static int X = 400, Y = 100;
	JButton minimize,maximize,close;
	JButton emoji,photoSend,fileSend,movieSend,msgSend,paint;
	JTextPane typeArea,chatArea;
	JFrame faces;
	
	String groupName = "";
	User user = null;
	
	public MultiChatFrame(User user,String groupName){
		this.user = user;
		this.groupName = groupName;
		
		JPanel back = new JPanel();
		JPanel top = new JPanel();
		JPanel mid = new JPanel();
		JPanel bottom = new JPanel();
		JPanel rightList = new JPanel();

//----------------------------基本布局		
		back.setLayout(null);
		back.add(top); top.setBounds(0, 0, WIDTH, 150);
		top.setBackground(Color.lightGray);
		back.add(rightList); rightList.setBounds(WIDTH-200,150,200,HEIGHT);
		back.add(mid); mid.setBounds(0, 150, WIDTH-200, 500);
		mid.setBackground(Color.white);
		back.add(bottom); bottom.setBounds(0, 650, WIDTH-200, 200);
		bottom.setBackground(new Color(227,227,227));
		
//-----------------------------top
//-------------------------------------top按钮
		minimize = new JButton();
		minimize.addActionListener(this);
		minimize.setContentAreaFilled(false);
		minimize.setMargin(new Insets(0,0,0,0));
		minimize.setBorderPainted(false);
		changeIcon(minimize,"img/MinimizeButtonIcon.jpg");
		
		close = new JButton();
		close.addActionListener(this);
		close.setContentAreaFilled(false);
		close.setMargin(new Insets(0,0,0,0));
		close.setBorderPainted(false);
		changeIcon(close,"img/CloseButtonIcon.jpg");
		
//-----------------------------------------top完善
		JLabel headImage = new JLabel(new ImageIcon("img/QQGroupImage.jpg"));
		
		JLabel title = new JLabel();
		title.setFont(new Font("宋体",Font.BOLD,20));
		title.setText(groupName);
		
		top.add(headImage); headImage.setBounds(20,20,100,100);
		top.add(title); title.setBounds(140,60,100,20);
		
		this.setDragable(top);
		top.setLayout(null);
		top.add(minimize); minimize.setBounds(WIDTH-70,0,35,35);
		top.add(close);	   close.setBounds(WIDTH-35,0,35,35);
//------------------------------- rightList
		BoxLayout boxLayout = new BoxLayout(rightList, BoxLayout.Y_AXIS);
		
		rightList.setLayout(boxLayout);
		JLabel member = new JLabel("群成员");
		rightList.add(member);
		addGroupMemberList(rightList);
		
//-----------------------------mid
		mid.setLayout(null);
		chatArea = new JTextPane();
		chatArea.setEditable(false); //不可编辑
		chatArea.setFont(new Font("宋体",Font.PLAIN,20));
		JScrollPane chatJsp = new JScrollPane(chatArea);
		
		mid.add(chatJsp); chatJsp.setBounds(0,0,WIDTH-200,500);
//-----------------------------bottom
		bottom.setLayout(null);
		
		
		JPanel bottomBar = new JPanel(null);
		typeArea = new JTextPane();
		typeArea.setFont(new Font("楷体",Font.PLAIN,20));
//		typeArea.addKeyListener(this);
		JScrollPane typeJsp = new JScrollPane(typeArea);
		
		msgSend = new JButton("发送"); 
		msgSend.addActionListener(this);
		
		emoji =  new JButton(); changeIcon(emoji,"img/EmojiButton2.jpg"); 
		emoji.setContentAreaFilled(false);  emoji.addActionListener(this);
		photoSend = new JButton(); changeIcon(photoSend, "img/PhotoSendButton.jpg");
		photoSend.setContentAreaFilled(false); 
		fileSend = new JButton(); changeIcon(fileSend, "img/FileSendButton.jpg");
		fileSend.setContentAreaFilled(false); 
		movieSend = new JButton();changeIcon(movieSend, "img/MovieSendButton.jpg");
		movieSend.setContentAreaFilled(false);
		paint = new JButton(); changeIcon(paint, "img/PaintButton.jpg");
		paint.setContentAreaFilled(false); 
		
		bottomBar.add(emoji); 		emoji.setBounds(10, 3, 35, 35);
		bottomBar.add(photoSend);	photoSend.setBounds(55, 3, 35, 35);
		bottomBar.add(fileSend);	fileSend.setBounds(100, 3, 35, 35);
		bottomBar.add(movieSend);	movieSend.setBounds(145, 3, 35, 35);
		bottomBar.add(paint);		paint.setBounds(190, 3, 35, 35);
		
		bottom.add(bottomBar); bottomBar.setBounds(0, 0, WIDTH-200, 40);  
		bottom.add(typeJsp); typeJsp.setBounds(0,40,WIDTH-200,120);  
		bottom.add(msgSend); msgSend.setBounds(WIDTH-300,160,80,40);
		
		this.setLayout(null);
		this.add(back); back.setBounds(0,0,WIDTH,HEIGHT);
		this.setBounds(X, Y, WIDTH, HEIGHT);
		this.setUndecorated(true);
		ImageIcon icon = new ImageIcon("img/LoginIcon2.png");
		this.setIconImage(icon.getImage()); 
		this.setVisible(true);
		
		//添加表情
		addEmojis();
	}
	
	/**
	 * 添加群成员
	 * @param memberList
	 */
	public void addGroupMemberList(JPanel rightList){
		int groupId = 1;
		groupMemberList = ManageQQGroupMemberList.getGroupMemberList(groupId);
		for(int i = 0;i<4;i++){
			Friend friend = groupMemberList.get(i);
			String name = friend.getName();
			
			JLabel blank = new JLabel(" ");
			ImageIcon image = new ImageIcon(friend.getHead());
			ImageIcon scaledImageIcon = new ImageIcon(image.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
			JLabel member = new JLabel(name,scaledImageIcon, JLabel.LEFT);
			rightList.add(member);
			rightList.add(blank);
		}
	}
	
	/**
	 * 为表情窗口添加表情
	 */
	int cnt = 0;
	private void addEmojis(){
		faces = new JFrame();
		JPanel totalP = new JPanel();
		totalP.setLayout(new GridLayout(15,7));
		totalP.setBackground(Color.white);
		JScrollPane jsp = new JScrollPane(totalP);
		for(int j=1;j<5;j++){
			for(int i=1;i<24;i++){
				JPanel faceP = new JPanel(); faceP.setBackground(Color.white);
				JLabel faceImage = new JLabel(new ImageIcon("img/MiniQQFaces/"+ i +".png"));
				JLabel faceId = new JLabel(""+i);
				faceP.add(faceId); faceId.setVisible(false);
				faceP.add(faceImage);
				faceP.addMouseListener(new MouseListener() {
					public void mouseReleased(MouseEvent e) {
					}
					public void mousePressed(MouseEvent e) {
//System.out.println("EMOJI"+e.getSource());
						faces.setVisible(false); cnt=0;
						JPanel p = (JPanel)e.getComponent();
						JLabel l = (JLabel)p.getComponent(0);
						int i = Integer.parseInt(l.getText());
						String id = ""+i;
						insertFace(id,typeArea);
					}
					public void mouseExited(MouseEvent e) {
						JPanel p = (JPanel)e.getComponent();
						p.setBackground(Color.white);
					}
					public void mouseEntered(MouseEvent e) {
						JPanel p = (JPanel)e.getComponent();
						p.setBackground(Color.lightGray);
					}
					public void mouseClicked(MouseEvent e) {
					}
				});
				totalP.add(faceP);
			}
		}
		faces.add(jsp);
		faces.setUndecorated(true);
		faces.setBounds(multiChatFrame.getX(),multiChatFrame.getY()+350,450,300);
		faces.setVisible(false);
	}
	
	/**
	 * 根据图片的id向TextPane插入图片
	 * @param id
	 */
	private void insertFace(String id,JTextPane pane) {
		ImageIcon icon = new ImageIcon("img/MiniQQFaces/"+ id +".png");
		
		StyledDocument doc = pane.getStyledDocument();
		Style style = doc.addStyle("", null);
		StyleConstants.setIcon(style, icon);
		try {
			doc.insertString(doc.getLength(), "#"+id , style);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 向TextPane中插入字符串
	 * @param str
	 */
	private void insertStr(String str){
		SimpleAttributeSet attr = new SimpleAttributeSet();
		StyledDocument doc = chatArea.getStyledDocument();
		try {
			doc.insertString(doc.getLength(), str, attr);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 处理各种事件
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == minimize){
			int state = getExtendedState();
			state = JFrame.ICONIFIED;
			this.setExtendedState(state);
		}
		
		if(e.getSource() == close){
			this.dispose();
		}
		
		if(e.getSource() == msgSend){
			String str =typeArea.getText(); 
			Message mesg = setMesg(str, MessageType.MESSAGE_CHAT);
			typeArea.setText("");
			showMesg(mesg);
			sendMesg(mesg);
		}
		
		if(e.getSource() == emoji){
			faces.setLocation(multiChatFrame.getX(), multiChatFrame.getY()+350);
			cnt +=1;			
			if(cnt%2 != 0)
				faces.setVisible(true);
			else 
				faces.setVisible(false);
		}
	}
	/**
	 * 生成消息
	 * @param str
	 */
	private Message setMesg(String str,String mesgType){
		Message mesg = new Message();
		mesg.setMesgType(mesgType);
		mesg.setContent(str);
		mesg.setSender(user.getName());
		mesg.setSenderAccount(user.getAccount());
		String receiveAccount = ""; 
		for(int i=0;i<groupMemberList.size();i++){
			if(!groupMemberList.get(i).getAccount().equals(user.getAccount())){ //不给自己发
				if(i==groupMemberList.size()-1)
					receiveAccount += groupMemberList.get(i).getAccount();
				else
					receiveAccount += groupMemberList.get(i).getAccount() + " ";
			}
		}
		mesg.setReceiver("All " + groupName);
		mesg.setReceiverAccount(receiveAccount);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String time = df.format(new Date());// new Date()为获取当前系统时间
		mesg.setTime(time);
		return mesg;
	}
	/**
	 * 发送消息
	 * @param mesg
	 */
	private void sendMesg(Message mesg){
		try {
			ObjectOutputStream oos = new ObjectOutputStream (ManageClientConServerChatThread.
					getClientServerThread(user.getAccount() + MessageType.MESSAGE_CHAT).getS().getOutputStream());
				oos.writeObject(mesg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 在聊天框中显示消息
	 */
	public void showMesg(Message mesg){
		String show = mesg.getTime() + " " + mesg.getSender() +" 说 :\n";
		insertStr(show);
		
		String content = mesg.getContent();
		char [] c = new char[content.length()];
		c = content.toCharArray();
		String str = "";
		for(int i=0;i<c.length;i++){                 //拆分字符串把所有的表情代码替换成图片
			if(c[i] == '#'){
				if(c[i] == '#'&&i+2<c.length &&(c[i+2]=='0'||c[i+2]=='1'||c[i+2] =='2'||c[i+2]=='3'||c[i+2]=='4'||c[i+2]=='5'||
				c[i+2]=='6'||c[i+2]=='7'||c[i+2]=='8'||c[i+2]=='9')){
					String id = "" + c[i+1] + c[i+2];
					insertFace(id,chatArea);
					i=i+2; //跳过这个表情
					continue;
				}
				else{
					String id = "" + c[i+1];
					insertFace(id, chatArea);
					i = i+1;
					continue;
				}
			}
			str = ""+c[i];
			insertStr(str);
		}
		insertStr("\n\n");
	}
	
	/**
	 * Button更改图标
	 */
	private void changeIcon(JButton b,String address) {
		ImageIcon image = new ImageIcon(address);  //给按钮更改图标
		b.setIcon(image);
	}
	
	/**
	 * 实现undecorated窗体的拖动
	 */
	boolean isMoved = false;
	Point prePoint = null,endPoint = null;
	private void setDragable(final JPanel p) {
		this.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				isMoved = false;// 鼠标释放了以后，是不能再拖拽的了
				p.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			public void mousePressed(MouseEvent e) {
				if(e.getY()>=0 && e.getY()<=40){
					isMoved = true;
					prePoint = new Point(e.getX(), e.getY());// 得到按下去的位置
					p.setCursor(new Cursor(Cursor.MOVE_CURSOR));
				}
			}
		});
		// 拖动时当前的坐标减去鼠标按下去时的坐标，就是界面所要移动的向量。
		this.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (isMoved) {// 判断是否可以拖拽
					endPoint = new Point(multiChatFrame.getLocation().x + e.getX() - prePoint.x,
							multiChatFrame.getLocation().y + e.getY() - prePoint.y);
					X = endPoint.x; Y = endPoint.y;
					multiChatFrame.setLocation(endPoint);
				}
			}
		});
	}


/*	public static void main(String []args){
		User user = new User();
		String name = "1群";
		new MultiChatFrame(user,name);
	}
	*/

}
