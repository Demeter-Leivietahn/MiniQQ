package qq.client.ui;

import java.awt.*;
import javax.swing.*;

import qq.client.manage.ManageChatFrame;
import qq.client.manage.ManageClientConServerChatThread;
import qq.client.manage.ManageMultiChatFrame;
import qq.client.manage.ManageQQFriendList;
import qq.common.Friend;
import qq.common.Message;
import qq.common.MessageType;
import qq.common.User;
import qq.game.frame.RaidenFrame;

import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainFrame extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	MainFrame mainFrame;
	
	static final int WIDTH = 360, HEIGHT = 800, GROUP_ROW_HEIGHT = 70;
	static int X = 1000, Y = 0;
	
	JPanel back;
	JPanel top,mid,bottom;
	JPanel topBar,midBar;
	JLabel headImage, personalSignature,userName;
	JLabel vip;
	JButton setting,minimize,close;
	JButton friend,group,latest;
	JButton thunderGame,search,appBox,qqLive,qZone,qqMusic;
	CardLayout card;
	JPanel midList;
	JPanel[] myFriend ;
	JPanel[] dormitory ;
	
	Color theme;
	
	User user = null;
	
	public void launchMainFrame(User user) {
		this.user = user;
		mainFrame = new MainFrame();
		initialize(mainFrame);
		
		try {
			ObjectOutputStream oos = new ObjectOutputStream(ManageClientConServerChatThread.
					getClientServerThread(user.getAccount() + MessageType.MESSAGE_CHAT).getS().getOutputStream());
			Message mesg = new Message();
			mesg.setMesgType(MessageType.MESSAGE_ONLINE);
			mesg.setSenderAccount(user.getAccount());
			oos.writeObject(mesg);
System.out.println("发送上线消息");			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		new Thread(new PaintThread()).start();
	
	}

	/**
	 * 初始化Frame
	 * @param frame
	 */
	private void initialize(JFrame frame) {
		theme = Color.gray;
		back = new JPanel();
		back.setLayout(new GridBagLayout());
		back.setSize(WIDTH, HEIGHT);

		top = new JPanel();
		mid = new JPanel();
		bottom = new JPanel();
		
//----------------------基本面板
		back.add(top, new GBC(0, 0, 1, 1).setFill(GBC.BOTH).setIpad(WIDTH, 200).setWeight(100, 50));
		top.setBackground(theme);
		back.add(mid, new GBC(0, 1, 1, 1).setFill(GBC.BOTH).setIpad(WIDTH, 550).setWeight(100, 50));
		mid.setBackground(Color.white);
		back.add(bottom, new GBC(0, 3, 1, 1).setFill(GBC.BOTH).setIpad(WIDTH, 50).setWeight(100, 50));
		bottom.setBackground(theme);
		
//----------------------小部件
//---------------------------------top布局
//-----------------------------------------更改图标+实现监听
		setting = new JButton();
		setting.setToolTipText("换肤");
		setting.addActionListener(this);
        setting.setContentAreaFilled(false);   //不绘制button的内容区
        setting.setMargin(new Insets(0, 0, 0, 0));
        setting.setBorderPainted(false);
		changeIcon(setting,"img/齿轮副本.png");
		
		minimize = new JButton();
		minimize.setToolTipText("最小化");
		minimize.addActionListener(this);
		minimize.setContentAreaFilled(false);
		minimize.setMargin(new Insets(0,0,0,0));
		minimize.setBorderPainted(false);
		changeIcon(minimize,"img/登陆最小化副本.png");
		
		close = new JButton();
		close.setToolTipText("关闭");
		close.addActionListener(this);
		close.setContentAreaFilled(false);
		close.setMargin(new Insets(0,0,0,0));
		close.setBorderPainted(false);
		changeIcon(close,"img/登陆关闭副本.png");

		
//----------------------------------------------topBar实现拖拽 添加系统按钮		
		topBar = new JPanel();
		mainFrame.setDragable(topBar,mainFrame);
		topBar.setBounds(0, 0, WIDTH, 40); topBar.setBackground(theme);
		topBar.setLayout(null);
		topBar.add(setting);   setting.setBounds(WIDTH-105,0,35,35);
		topBar.add(minimize);  minimize.setBounds(WIDTH-70,0,35,35);
		topBar.add(close);	   close.setBounds(WIDTH-35,0,35,35);
		
//-----------------------------------------------top面板完善
		headImage = new JLabel();
		headImage.setToolTipText("修改个人资料");
		headImage.setIcon(new ImageIcon(user.getHead()));
		headImage.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {
				user.setName(userName.getText());
				user.setSignature(personalSignature.getText());
				user.setHead(user.getHead());
				new SetInfoFrame(user, mainFrame);
			}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {}
		});
		vip = new JLabel(new ImageIcon("img/vip.png"));
		personalSignature = new JLabel(user.getSignature());
		userName = new JLabel();
		userName.setFont(new Font("宋体",Font.BOLD,20));
		userName.setText(user.getName());
		String [] statusArr = {"在线","请勿打扰","忙碌","隐身"};
		JComboBox status = new JComboBox(statusArr);
		
		top.setLayout(null);
		top.add(topBar);
		top.add(headImage); headImage.setBounds(20,40,100,100);
		top.add(personalSignature); personalSignature.setBounds(140,100,220,40);
		top.add(userName); userName.setBounds(140,60,100,20);
		top.add(vip);vip.setBounds(220, 60, 20, 20);
		top.add(status); status.setBounds(250,60,80,20);
		
		
		
//----------------------------------mid布局
		card = new CardLayout();
		mid.setLayout(null);
//-----------------------------------------添加midBar及其组件 
		midBar = new JPanel(null);
		
		midList = new JPanel(card);
		JPanel friendPanel = new JPanel();            
		JPanel groupPanel = new JPanel();
		JPanel latestPanel = new JPanel();
		
		friend = new JButton();
		friend.setToolTipText("我的好友");
		friend.addActionListener(this);
		friend.setContentAreaFilled(false);
		friend.setBorderPainted(false);
		changeIcon(friend,"img/FriendButton.png");
		
		group = new JButton();
		group.setToolTipText("群聊");
		group.addActionListener(this);
		group.setBorderPainted(false);
		group.setContentAreaFilled(false);
		changeIcon(group,"img/GroupButton.png");
		
		latest = new JButton();
		latest.setToolTipText("最近联系人");
		latest.addActionListener(this);
		latest.setBorderPainted(false);
		latest.setContentAreaFilled(false);
		changeIcon(latest,"img/LatestButton.png");
		
		midBar.add(friend); friend.setBounds(0,0,WIDTH/3,50);
		midBar.add(group);	group.setBounds(WIDTH/3,0,WIDTH/3,50);
		midBar.add(latest);	latest.setBounds(WIDTH*2/3,0,WIDTH/3,50);
//-------------------------------------------绘制midList(使用cardlayout布局)   ###待完善(对于好友关系和群关系的列出)
/**
 * --------------------------------------------------------------------------------------我的好友
 */
		FlowLayout flow = new FlowLayout();
		flow.setAlignment(FlowLayout.LEFT);
		
		JPanel myFriendP = new JPanel(flow); myFriendP.setPreferredSize(new Dimension(350, 20));  
		JLabel myFriendL = new JLabel("我的好友");
		myFriendL.setIcon(new ImageIcon("img/Arrow1.jpg"));
		myFriendP.add(myFriendL); 
		
		
		JPanel dormitoryP = new JPanel(flow);  dormitoryP.setPreferredSize(new Dimension(350, 20));
		dormitoryP.setForeground(Color.blue);
		JLabel blackFriendL = new JLabel("宿舍");
		blackFriendL.setIcon(new ImageIcon("img/Arrow1.jpg"));
		dormitoryP.add(blackFriendL);
		
		JScrollPane jsp = new JScrollPane(friendPanel);
		jsp.getVerticalScrollBar().setUnitIncrement(10);   //更改滚动条的滑动速度
		BoxLayout boxLayout=new BoxLayout(friendPanel, BoxLayout.Y_AXIS); 
		friendPanel.setLayout(boxLayout);
		
		friendPanel.add(myFriendP); 
		myFriend = addFriendList(friendPanel);
		friendPanel.add(dormitoryP);
		dormitory = addDormitoryList(friendPanel);
		
		
		//对好友列表加监听
		myFriendP.addMouseListener(new MouseAdapter() {
			int clickCnt = 0;
			@Override
			public void mouseClicked(MouseEvent e) {
				clickCnt++;
				if( clickCnt%2 == 1){
					for(int i=0;i<myFriend.length;i++){
						myFriend[i].setVisible(true);
						JLabel l = (JLabel) myFriendP.getComponent(0);
						l.setIcon(new ImageIcon("img/Arrow2.jpg"));
					}
				}else{
					for(int i=0;i<myFriend.length;i++){
						myFriend[i].setVisible(false);
						JLabel l = (JLabel) myFriendP.getComponent(0);
						l.setIcon(new ImageIcon("img/Arrow1.jpg"));
					}
				}
			}
		});
		
		dormitoryP.addMouseListener(new MouseAdapter() {
			int clickCnt = 0;
			@Override
			public void mouseClicked(MouseEvent e) {
				clickCnt++;
				if( clickCnt % 2 == 1){
					for(int i=0;i<dormitory.length;i++){
						dormitory[i].setVisible(true);
						JLabel l = (JLabel) dormitoryP.getComponent(0);
						l.setIcon(new ImageIcon("img/Arrow2.jpg"));
					}
				}else{
					for(int i=0;i<dormitory.length;i++){
						dormitory[i].setVisible(false);
						JLabel l = (JLabel) dormitoryP.getComponent(0);
						l.setIcon(new ImageIcon("img/Arrow1.jpg"));
					}
				}
			}
		});
		
/**
 * --------------------------------------------------------------------------------------------群聊
 */
		BoxLayout boxLayout1=new BoxLayout(groupPanel, BoxLayout.Y_AXIS);
		groupPanel.setLayout(null);
		JLabel groupL = new JLabel("群聊");
		groupPanel.add(groupL); groupL.setBounds(150,0,30,30);
		JPanel group1 = new JPanel(null);
		JLabel groupId1 = new JLabel("1");
		JLabel groupName = new JLabel("1群");
		JLabel groupHead = new JLabel(new ImageIcon("img/GroupHead.jpg"));
		group1.add(groupId1); groupId1.setVisible(false);
		group1.add(groupHead); 	groupHead.setBounds(0,5,60,60);
		group1.add(groupName);	groupName.setBounds(100,15,100,50);groupName.setFont(new Font("宋体",Font.BOLD,15));
		group1.addMouseListener(new GroupMouseListener());
		
		JPanel group2 = new JPanel(null);
		JLabel groupId2 = new JLabel("2");
		JLabel groupName2 = new JLabel("2群");
		JLabel groupHead2 = new JLabel(new ImageIcon("img/GroupHead.jpg"));
		group2.add(groupId2); groupId2.setVisible(false);
		group2.add(groupHead2); 	groupHead2.setBounds(0,5,60,60);
		group2.add(groupName2);	groupName2.setBounds(100,15,100,50);groupName.setFont(new Font("宋体",Font.BOLD,15));
		group2.addMouseListener(new GroupMouseListener());
		
		groupPanel.add(group1); group1.setBounds(0,30,WIDTH,GROUP_ROW_HEIGHT);
		groupPanel.add(group2); group2.setBounds(0,100,WIDTH,GROUP_ROW_HEIGHT);
		
		JLabel l3 = new JLabel("最近联系人");
		latestPanel.add(l3);
		
		midList.add(jsp,"friend");
		midList.add(groupPanel, "group");
		midList.add(latestPanel,"latest");
		
		mid.add(midBar); midBar.setBounds(0,0,WIDTH,50); midBar.setBackground(theme);
		mid.add(midList); midList.setBounds(0,50,WIDTH,500);
		
		
//----------------------------------bottom布局
		bottom.setLayout(null);
		thunderGame = new JButton();
		thunderGame.setToolTipText("QQ雷电游戏");
		thunderGame.addActionListener(this);
		thunderGame.setIcon(new ImageIcon("img/QQ游戏.png"));
		thunderGame.setContentAreaFilled(false);   //不绘制button的内容区
		thunderGame.setMargin(new Insets(0, 0, 0, 0));
		thunderGame.setBorderPainted(false);
		
		search = new JButton();
		search.setToolTipText("搜索");
		search.addActionListener(this);
		search.setIcon(new ImageIcon("img/搜索.png"));
		search.setContentAreaFilled(false);   
		search.setMargin(new Insets(0, 0, 0, 0));
		search.setBorderPainted(false);
		
		appBox = new JButton();
		appBox.setToolTipText("应用盒子");
		appBox.addActionListener(this);
		appBox.setIcon(new ImageIcon("img/应用盒子.png"));
		appBox.setContentAreaFilled(false);   
		appBox.setMargin(new Insets(0, 0, 0, 0));
		appBox.setBorderPainted(false);
		
		qqLive = new JButton();
		qqLive.setToolTipText("QQLive");
		qqLive.addActionListener(this);
		qqLive.setIcon(new ImageIcon("img/直播.png"));
		qqLive.setContentAreaFilled(false);   
		qqLive.setMargin(new Insets(0, 0, 0, 0));
		qqLive.setBorderPainted(false);
		
		qZone = new JButton();
		qZone.setToolTipText("应用中心");
		qZone.addActionListener(this);
		qZone.setIcon(new ImageIcon("img/空间.png"));
		qZone.setContentAreaFilled(false);   
		qZone.setMargin(new Insets(0, 0, 0, 0));
		qZone.setBorderPainted(false);
		
		qqMusic = new JButton();
		qqMusic = new JButton();
		qqMusic.setToolTipText("QQ音乐");
		qqMusic.addActionListener(this);
		qqMusic.setIcon(new ImageIcon("img/QQ音乐.png"));
		qqMusic.setContentAreaFilled(false);   
		qqMusic.setMargin(new Insets(0, 0, 0, 0));
		qqMusic.setBorderPainted(false);
		
		bottom.add(search); search.setBounds(10,5,40,40);
		bottom.add(thunderGame); thunderGame.setBounds(70,10,35,35);
		bottom.add(qqLive); qqLive.setBounds(130,10,35,35);
		bottom.add(qZone); qZone.setBounds(190,10,35,35);
		bottom.add(qqMusic); qqMusic.setBounds(250, 10, 35, 35);
		bottom.add(appBox); appBox.setBounds(310,10,35,35);
		
		frame.add(back);
		ImageIcon icon = new ImageIcon("img/系统图标副本.png");
		frame.setIconImage(icon.getImage()); 
		frame.setBounds(X, Y, WIDTH, HEIGHT);
		frame.setUndecorated(true);                    //去掉系统自带边框
		frame.setVisible(true);
	}
	/**
	 * 更新用户信息
	 * @param name
	 * @param head
	 * @param signature
	 */
	public void changeUserInfo(String name,String head,String signature){
		userName.setText(name);
		headImage.setIcon(new ImageIcon(head));
		personalSignature.setText(signature);
//		mainFrame.repaint();
	}
	/**
	 * 添加好友列表
	 * @param friendPanel  好友列表的主面板
	 * @return
	 */
	public JPanel[] addFriendList(JPanel friendPanel){       //得到list一个一个添加进去
//		int userId = 1;                 //根据用户id去获取相应的朋友信息
		ArrayList<Friend> friendList = ManageQQFriendList.getFriendList();
		JPanel[] panel = new JPanel[friendList.size()];
		for(int i = 0;i<friendList.size();i++){
			Friend friend = friendList.get(i);
			
			FlowLayout flow = new FlowLayout();
			flow.setHgap(10);
			flow.setAlignment(FlowLayout.LEFT);
			
			panel[i] = new JPanel();  
			panel[i].setLayout(flow); 
			
			JLabel friendAcc = new JLabel(String.valueOf(friend.getAccount()));
			JLabel headImage = new JLabel();
			ImageIcon image = new ImageIcon(friend.getHead());
			ImageIcon scaledImageIcon = new ImageIcon(image.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
			headImage.setIcon(scaledImageIcon);

			JLabel nameL = new JLabel(friend.getName());
			JLabel signature = new JLabel(friend.getSignature());
			
			panel[i].add(friendAcc); friendAcc.setVisible(false);
			panel[i].add(headImage);
			panel[i].add(nameL);  nameL.setFont(new Font("宋体",Font.BOLD,15));
			panel[i].add(signature);	signature.setFont(new Font("楷体",Font.ITALIC,15)); 
			signature.setForeground(Color.gray);
			
			panel[i].setVisible(false);
			panel[i].addMouseListener(new MyFriendMouseListener());
			
			friendPanel.add(panel[i]);
		}
		return panel;  //返回构造的存储好友的panel数组
	}
	
	/**
	 * 加入宿舍分组好友
	 * @param friendPanel
	 * @return
	 */
	private JPanel[] addDormitoryList(JPanel friendPanel) {
		ArrayList<Friend> friendList = ManageQQFriendList.getFriendList();
		JPanel[] panel = new JPanel[4];
		for(int i = 0;i<4;i++){
			Friend friend = friendList.get(i);
			
			FlowLayout flow = new FlowLayout();
			flow.setHgap(15);
			flow.setAlignment(FlowLayout.LEFT);
			
			panel[i] = new JPanel();  
			panel[i].setLayout(flow); 
			
			JLabel friendAcc = new JLabel(String.valueOf(friend.getAccount()));
			JLabel headImage = new JLabel();
			ImageIcon image = new ImageIcon(friend.getHead());
			ImageIcon scaledImageIcon = new ImageIcon(image.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
			headImage.setIcon(scaledImageIcon);

			JLabel nameL = new JLabel(friend.getName());
			JLabel signature = new JLabel(friend.getSignature());
			
			panel[i].add(friendAcc); friendAcc.setVisible(false);
			panel[i].add(headImage);
			panel[i].add(nameL);  nameL.setFont(new Font("宋体",Font.BOLD,15));
			panel[i].add(signature);	signature.setFont(new Font("楷体",Font.ITALIC,15)); 
			signature.setForeground(Color.gray);
			
			panel[i].setVisible(false);
			panel[i].addMouseListener(new MyFriendMouseListener());
			
			friendPanel.add(panel[i]);
		}
		return panel;  //返回构造的存储好友的panel数组
	}
	
	/**
	 * 弹窗告诉用户来消息了
	 * @param mesg
	 */
	public void popChatFrame(Message mesg){
		JDialog jd = new JDialog();
		jd.setTitle("来消息了!");
		JLabel jl = new JLabel( mesg.getSender() + "给你发了消息",new ImageIcon("img/QQ消息.png"),JLabel.CENTER);
		jd.add(jl);
		jd.setBounds(mainFrame.getX(),mainFrame.getY()+100,250,150);
		jd.setIconImage(new ImageIcon("img/系统图标副本.png").getImage());
		jd.setVisible(true);
	}
	/**
	 * 处理所有按钮的事件
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == setting){
			theme = JColorChooser.showDialog(mainFrame, "请选择颜色",theme);
//System.out.println(theme);
			top.setBackground(theme);
			topBar.setBackground(theme);
			bottom.setBackground(theme);
			friend.setBackground(theme);
			group.setBackground(theme);
			latest.setBackground(theme);
			midBar.setBackground(theme);
		}
		
		else if(e.getSource() == minimize){
//System.out.println("最小化");			
			int state = getExtendedState();
			state = JFrame.ICONIFIED;
			mainFrame.setExtendedState(state);
		}
		
		else if(e.getSource() == close){
			JFrame alert = new JFrame("提示");
			alert.setLayout(null);
			alert.setResizable(false);
			int x = X+50,y = Y+100,weight = 250,height = 200;
			alert.setBounds(x,y,weight,height);
			
			JLabel jl = new JLabel("确定退出？");
			JLabel image = new JLabel(new ImageIcon("img/提示.png"));
			jl.setFont(new Font("宋体",Font.BOLD,20));
			JButton yes = new JButton("是");
			JButton no = new JButton("否");
			
			yes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mainFrame.dispose();
					alert.dispose();
				}
			});
			
			no.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					alert.dispose();
				}
			});
			
			ImageIcon icon = new ImageIcon("img/系统图标.png");
			alert.setIconImage(icon.getImage()); 	
			alert.add(jl); jl.setBounds(100,5,200,100);
			alert.add(image); image.setBounds(45, 35, 40, 40);
			alert.add(yes); yes.setBounds(60,120,50,30);
			alert.add(no);  no.setBounds(140,120,50,30);
			
			alert.setVisible(true);
		}
		
		else if(e.getSource() == friend){
			card.show(midList, "friend");
		}
		
		else if(e.getSource() == group){
			card.show(midList, "group");
		}
		
		else if(e.getSource() == latest){
			card.show(midList, "latest");
		}
		
		else if(e.getSource() == thunderGame){
			new RaidenFrame();
		}
	}
	
	/**
	 * 实现undecorated窗体的拖动
	 */
	boolean isMoved = false;
	Point prePoint = null,endPoint = null;
	private void setDragable(JPanel p,JFrame mainFrame) {
		this.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				isMoved = false;// 鼠标释放了以后，是不能再拖拽的了
				p.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			public void mousePressed(MouseEvent e) {
//System.out.println("按下了鼠标");				
				if(e.getY()>=0 && e.getY()<=40)
					isMoved = true;
				prePoint = new Point(e.getX(), e.getY());// 得到按下去的位置
				p.setCursor(new Cursor(Cursor.MOVE_CURSOR));
			}
		});
		// 拖动时当前的坐标减去鼠标按下去时的坐标，就是界面所要移动的向量。
		this.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (isMoved) {// 判断是否可以拖拽
					endPoint = new Point(mainFrame.getLocation().x + e.getX() - prePoint.x,
							mainFrame.getLocation().y + e.getY() - prePoint.y);
					X = endPoint.x; Y = endPoint.y;
					mainFrame.setLocation(endPoint);
				}
			}
		});
	}

	

	/**
	 * 更改图标
	 */
	private void changeIcon(JButton b,String address) {
		ImageIcon image = new ImageIcon(address);  //给按钮更改图标
		b.setIcon(image);
	}
	
	/**
	 * 继承GridBagconstraints 便于书写
	 */
	class GBC extends GridBagConstraints {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		// 初始化左上角位置
		public GBC(int gridx, int gridy) {
			this.gridx = gridx;
			this.gridy = gridy;
		}

		// 初始化左上角位置和所占行数和列数
		public GBC(int gridx, int gridy, int gridwidth, int gridheight) {
			this.gridx = gridx;
			this.gridy = gridy;
			this.gridwidth = gridwidth;
			this.gridheight = gridheight;
		}

		// 对齐方式
		public GBC setAnchor(int anchor) {
			this.anchor = anchor;
			return this;
		}

		// 是否拉伸及拉伸方向
		public GBC setFill(int fill) {
			this.fill = fill;
			return this;
		}

		// x和y方向上的增量
		public GBC setWeight(double weightx, double weighty) {
			this.weightx = weightx;
			this.weighty = weighty;
			return this;
		}

		// 外部填充
		public GBC setInsets(int distance) {
			this.insets = new Insets(distance, distance, distance, distance);
			return this;
		}

		// 外填充
		public GBC setInsets(int top, int left, int bottom, int right) {
			this.insets = new Insets(top, left, bottom, right);
			return this;
		}

		// 内填充
		public GBC setIpad(int ipadx, int ipady) {
			this.ipadx = ipadx;
			this.ipady = ipady;
			return this;
		}

	}

	/**
	 * 用于我的好友Panel的mouseListener
	 * @author 11314
	 *
	 */
	class MyFriendMouseListener implements MouseListener{
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getClickCount() == 2){
				JPanel p = (JPanel) e.getSource();
				JLabel friendAcc = (JLabel)p.getComponent(0);
				JLabel friendName = (JLabel) p.getComponent(2);
				JLabel head = (JLabel) p.getComponent(1);
				Icon headIcon = head.getIcon();
				
				Friend friend = new Friend();
				friend.setAccount(friendAcc.getText());
				friend.setName(friendName.getText());
				
				ChatFrame chatFrame = new ChatFrame(user,friend,headIcon);
				ManageChatFrame.addChatFrame(user.getAccount()+" "+friend.getAccount(), chatFrame);
	//System.out.println("add : " + user.getAccount()+" "+friend.getAccount());			
			}
		}
	
		@Override
		public void mouseEntered(MouseEvent e) {
			JPanel p = (JPanel)e.getSource();
			p.setBackground(Color.LIGHT_GRAY);
		}
	
		@Override
		public void mouseExited(MouseEvent e) {
			JPanel p = (JPanel)e.getSource();
			p.setBackground(null);
		}
	
		@Override
		public void mousePressed(MouseEvent e) {
			JPanel p = (JPanel)e.getSource();
			JLabel l = (JLabel) p.getComponent(2);
			l.setFont(new Font("宋体",Font.BOLD,20));
		}
	
		@Override
		public void mouseReleased(MouseEvent e) {
			JPanel p = (JPanel)e.getSource();
			JLabel l = (JLabel) p.getComponent(2);
			l.setFont(new Font("宋体",Font.BOLD,15));
		}
	}

	/**
	 * 用于群的mouseListener
	 * @author 11314
	 *
	 */
	class GroupMouseListener implements MouseListener{
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getClickCount() == 2){
				JPanel p = (JPanel) e.getSource();
				JLabel groupNameL = (JLabel)p.getComponent(2);
				String groupName = groupNameL.getText();
				
				MultiChatFrame multiChatFrame = new MultiChatFrame(user, groupName);
				ManageMultiChatFrame.addMultiChatFrame(groupName, multiChatFrame);
	//System.out.println("add : " + user.getAccount()+" "+friend.getAccount());			
			}
		}
	
		@Override
		public void mouseEntered(MouseEvent e) {
			JPanel p = (JPanel)e.getSource();
			p.setBackground(Color.LIGHT_GRAY);
		}
	
		@Override
		public void mouseExited(MouseEvent e) {
			JPanel p = (JPanel)e.getSource();
			p.setBackground(null);
		}
	
		@Override
		public void mousePressed(MouseEvent e) {
			JPanel p = (JPanel)e.getSource();
			JLabel l = (JLabel) p.getComponent(2);
			l.setFont(new Font("宋体",Font.BOLD,20));
		}
	
		@Override
		public void mouseReleased(MouseEvent e) {
			JPanel p = (JPanel)e.getSource();
			JLabel l = (JLabel) p.getComponent(2);
			l.setFont(new Font("宋体",Font.BOLD,15));
		}
		
	}
	/**
	 * 一直重画mainFrame
	 * @author 11314
	 *
	 */
	private class PaintThread implements Runnable{
		public void run() {
			while(true){
				mainFrame.repaint();
				try{
					Thread.sleep(50);	
				}catch(InterruptedException e){
					e.printStackTrace();
				}
			}
		}
	}

/*	public static void main(String[] args) {
		User user = new User();
		user.setName("周旭");
		new MainFrame().launchMainFrame(user);
	}*/
}
