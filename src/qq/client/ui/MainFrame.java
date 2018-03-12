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
System.out.println("����������Ϣ");			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		new Thread(new PaintThread()).start();
	
	}

	/**
	 * ��ʼ��Frame
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
		
//----------------------�������
		back.add(top, new GBC(0, 0, 1, 1).setFill(GBC.BOTH).setIpad(WIDTH, 200).setWeight(100, 50));
		top.setBackground(theme);
		back.add(mid, new GBC(0, 1, 1, 1).setFill(GBC.BOTH).setIpad(WIDTH, 550).setWeight(100, 50));
		mid.setBackground(Color.white);
		back.add(bottom, new GBC(0, 3, 1, 1).setFill(GBC.BOTH).setIpad(WIDTH, 50).setWeight(100, 50));
		bottom.setBackground(theme);
		
//----------------------С����
//---------------------------------top����
//-----------------------------------------����ͼ��+ʵ�ּ���
		setting = new JButton();
		setting.setToolTipText("����");
		setting.addActionListener(this);
        setting.setContentAreaFilled(false);   //������button��������
        setting.setMargin(new Insets(0, 0, 0, 0));
        setting.setBorderPainted(false);
		changeIcon(setting,"img/���ָ���.png");
		
		minimize = new JButton();
		minimize.setToolTipText("��С��");
		minimize.addActionListener(this);
		minimize.setContentAreaFilled(false);
		minimize.setMargin(new Insets(0,0,0,0));
		minimize.setBorderPainted(false);
		changeIcon(minimize,"img/��½��С������.png");
		
		close = new JButton();
		close.setToolTipText("�ر�");
		close.addActionListener(this);
		close.setContentAreaFilled(false);
		close.setMargin(new Insets(0,0,0,0));
		close.setBorderPainted(false);
		changeIcon(close,"img/��½�رո���.png");

		
//----------------------------------------------topBarʵ����ק ���ϵͳ��ť		
		topBar = new JPanel();
		mainFrame.setDragable(topBar,mainFrame);
		topBar.setBounds(0, 0, WIDTH, 40); topBar.setBackground(theme);
		topBar.setLayout(null);
		topBar.add(setting);   setting.setBounds(WIDTH-105,0,35,35);
		topBar.add(minimize);  minimize.setBounds(WIDTH-70,0,35,35);
		topBar.add(close);	   close.setBounds(WIDTH-35,0,35,35);
		
//-----------------------------------------------top�������
		headImage = new JLabel();
		headImage.setToolTipText("�޸ĸ�������");
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
		userName.setFont(new Font("����",Font.BOLD,20));
		userName.setText(user.getName());
		String [] statusArr = {"����","�������","æµ","����"};
		JComboBox status = new JComboBox(statusArr);
		
		top.setLayout(null);
		top.add(topBar);
		top.add(headImage); headImage.setBounds(20,40,100,100);
		top.add(personalSignature); personalSignature.setBounds(140,100,220,40);
		top.add(userName); userName.setBounds(140,60,100,20);
		top.add(vip);vip.setBounds(220, 60, 20, 20);
		top.add(status); status.setBounds(250,60,80,20);
		
		
		
//----------------------------------mid����
		card = new CardLayout();
		mid.setLayout(null);
//-----------------------------------------���midBar������� 
		midBar = new JPanel(null);
		
		midList = new JPanel(card);
		JPanel friendPanel = new JPanel();            
		JPanel groupPanel = new JPanel();
		JPanel latestPanel = new JPanel();
		
		friend = new JButton();
		friend.setToolTipText("�ҵĺ���");
		friend.addActionListener(this);
		friend.setContentAreaFilled(false);
		friend.setBorderPainted(false);
		changeIcon(friend,"img/FriendButton.png");
		
		group = new JButton();
		group.setToolTipText("Ⱥ��");
		group.addActionListener(this);
		group.setBorderPainted(false);
		group.setContentAreaFilled(false);
		changeIcon(group,"img/GroupButton.png");
		
		latest = new JButton();
		latest.setToolTipText("�����ϵ��");
		latest.addActionListener(this);
		latest.setBorderPainted(false);
		latest.setContentAreaFilled(false);
		changeIcon(latest,"img/LatestButton.png");
		
		midBar.add(friend); friend.setBounds(0,0,WIDTH/3,50);
		midBar.add(group);	group.setBounds(WIDTH/3,0,WIDTH/3,50);
		midBar.add(latest);	latest.setBounds(WIDTH*2/3,0,WIDTH/3,50);
//-------------------------------------------����midList(ʹ��cardlayout����)   ###������(���ں��ѹ�ϵ��Ⱥ��ϵ���г�)
/**
 * --------------------------------------------------------------------------------------�ҵĺ���
 */
		FlowLayout flow = new FlowLayout();
		flow.setAlignment(FlowLayout.LEFT);
		
		JPanel myFriendP = new JPanel(flow); myFriendP.setPreferredSize(new Dimension(350, 20));  
		JLabel myFriendL = new JLabel("�ҵĺ���");
		myFriendL.setIcon(new ImageIcon("img/Arrow1.jpg"));
		myFriendP.add(myFriendL); 
		
		
		JPanel dormitoryP = new JPanel(flow);  dormitoryP.setPreferredSize(new Dimension(350, 20));
		dormitoryP.setForeground(Color.blue);
		JLabel blackFriendL = new JLabel("����");
		blackFriendL.setIcon(new ImageIcon("img/Arrow1.jpg"));
		dormitoryP.add(blackFriendL);
		
		JScrollPane jsp = new JScrollPane(friendPanel);
		jsp.getVerticalScrollBar().setUnitIncrement(10);   //���Ĺ������Ļ����ٶ�
		BoxLayout boxLayout=new BoxLayout(friendPanel, BoxLayout.Y_AXIS); 
		friendPanel.setLayout(boxLayout);
		
		friendPanel.add(myFriendP); 
		myFriend = addFriendList(friendPanel);
		friendPanel.add(dormitoryP);
		dormitory = addDormitoryList(friendPanel);
		
		
		//�Ժ����б�Ӽ���
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
 * --------------------------------------------------------------------------------------------Ⱥ��
 */
		BoxLayout boxLayout1=new BoxLayout(groupPanel, BoxLayout.Y_AXIS);
		groupPanel.setLayout(null);
		JLabel groupL = new JLabel("Ⱥ��");
		groupPanel.add(groupL); groupL.setBounds(150,0,30,30);
		JPanel group1 = new JPanel(null);
		JLabel groupId1 = new JLabel("1");
		JLabel groupName = new JLabel("1Ⱥ");
		JLabel groupHead = new JLabel(new ImageIcon("img/GroupHead.jpg"));
		group1.add(groupId1); groupId1.setVisible(false);
		group1.add(groupHead); 	groupHead.setBounds(0,5,60,60);
		group1.add(groupName);	groupName.setBounds(100,15,100,50);groupName.setFont(new Font("����",Font.BOLD,15));
		group1.addMouseListener(new GroupMouseListener());
		
		JPanel group2 = new JPanel(null);
		JLabel groupId2 = new JLabel("2");
		JLabel groupName2 = new JLabel("2Ⱥ");
		JLabel groupHead2 = new JLabel(new ImageIcon("img/GroupHead.jpg"));
		group2.add(groupId2); groupId2.setVisible(false);
		group2.add(groupHead2); 	groupHead2.setBounds(0,5,60,60);
		group2.add(groupName2);	groupName2.setBounds(100,15,100,50);groupName.setFont(new Font("����",Font.BOLD,15));
		group2.addMouseListener(new GroupMouseListener());
		
		groupPanel.add(group1); group1.setBounds(0,30,WIDTH,GROUP_ROW_HEIGHT);
		groupPanel.add(group2); group2.setBounds(0,100,WIDTH,GROUP_ROW_HEIGHT);
		
		JLabel l3 = new JLabel("�����ϵ��");
		latestPanel.add(l3);
		
		midList.add(jsp,"friend");
		midList.add(groupPanel, "group");
		midList.add(latestPanel,"latest");
		
		mid.add(midBar); midBar.setBounds(0,0,WIDTH,50); midBar.setBackground(theme);
		mid.add(midList); midList.setBounds(0,50,WIDTH,500);
		
		
//----------------------------------bottom����
		bottom.setLayout(null);
		thunderGame = new JButton();
		thunderGame.setToolTipText("QQ�׵���Ϸ");
		thunderGame.addActionListener(this);
		thunderGame.setIcon(new ImageIcon("img/QQ��Ϸ.png"));
		thunderGame.setContentAreaFilled(false);   //������button��������
		thunderGame.setMargin(new Insets(0, 0, 0, 0));
		thunderGame.setBorderPainted(false);
		
		search = new JButton();
		search.setToolTipText("����");
		search.addActionListener(this);
		search.setIcon(new ImageIcon("img/����.png"));
		search.setContentAreaFilled(false);   
		search.setMargin(new Insets(0, 0, 0, 0));
		search.setBorderPainted(false);
		
		appBox = new JButton();
		appBox.setToolTipText("Ӧ�ú���");
		appBox.addActionListener(this);
		appBox.setIcon(new ImageIcon("img/Ӧ�ú���.png"));
		appBox.setContentAreaFilled(false);   
		appBox.setMargin(new Insets(0, 0, 0, 0));
		appBox.setBorderPainted(false);
		
		qqLive = new JButton();
		qqLive.setToolTipText("QQLive");
		qqLive.addActionListener(this);
		qqLive.setIcon(new ImageIcon("img/ֱ��.png"));
		qqLive.setContentAreaFilled(false);   
		qqLive.setMargin(new Insets(0, 0, 0, 0));
		qqLive.setBorderPainted(false);
		
		qZone = new JButton();
		qZone.setToolTipText("Ӧ������");
		qZone.addActionListener(this);
		qZone.setIcon(new ImageIcon("img/�ռ�.png"));
		qZone.setContentAreaFilled(false);   
		qZone.setMargin(new Insets(0, 0, 0, 0));
		qZone.setBorderPainted(false);
		
		qqMusic = new JButton();
		qqMusic = new JButton();
		qqMusic.setToolTipText("QQ����");
		qqMusic.addActionListener(this);
		qqMusic.setIcon(new ImageIcon("img/QQ����.png"));
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
		ImageIcon icon = new ImageIcon("img/ϵͳͼ�긱��.png");
		frame.setIconImage(icon.getImage()); 
		frame.setBounds(X, Y, WIDTH, HEIGHT);
		frame.setUndecorated(true);                    //ȥ��ϵͳ�Դ��߿�
		frame.setVisible(true);
	}
	/**
	 * �����û���Ϣ
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
	 * ��Ӻ����б�
	 * @param friendPanel  �����б�������
	 * @return
	 */
	public JPanel[] addFriendList(JPanel friendPanel){       //�õ�listһ��һ����ӽ�ȥ
//		int userId = 1;                 //�����û�idȥ��ȡ��Ӧ��������Ϣ
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
			panel[i].add(nameL);  nameL.setFont(new Font("����",Font.BOLD,15));
			panel[i].add(signature);	signature.setFont(new Font("����",Font.ITALIC,15)); 
			signature.setForeground(Color.gray);
			
			panel[i].setVisible(false);
			panel[i].addMouseListener(new MyFriendMouseListener());
			
			friendPanel.add(panel[i]);
		}
		return panel;  //���ع���Ĵ洢���ѵ�panel����
	}
	
	/**
	 * ��������������
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
			panel[i].add(nameL);  nameL.setFont(new Font("����",Font.BOLD,15));
			panel[i].add(signature);	signature.setFont(new Font("����",Font.ITALIC,15)); 
			signature.setForeground(Color.gray);
			
			panel[i].setVisible(false);
			panel[i].addMouseListener(new MyFriendMouseListener());
			
			friendPanel.add(panel[i]);
		}
		return panel;  //���ع���Ĵ洢���ѵ�panel����
	}
	
	/**
	 * ���������û�����Ϣ��
	 * @param mesg
	 */
	public void popChatFrame(Message mesg){
		JDialog jd = new JDialog();
		jd.setTitle("����Ϣ��!");
		JLabel jl = new JLabel( mesg.getSender() + "���㷢����Ϣ",new ImageIcon("img/QQ��Ϣ.png"),JLabel.CENTER);
		jd.add(jl);
		jd.setBounds(mainFrame.getX(),mainFrame.getY()+100,250,150);
		jd.setIconImage(new ImageIcon("img/ϵͳͼ�긱��.png").getImage());
		jd.setVisible(true);
	}
	/**
	 * �������а�ť���¼�
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == setting){
			theme = JColorChooser.showDialog(mainFrame, "��ѡ����ɫ",theme);
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
//System.out.println("��С��");			
			int state = getExtendedState();
			state = JFrame.ICONIFIED;
			mainFrame.setExtendedState(state);
		}
		
		else if(e.getSource() == close){
			JFrame alert = new JFrame("��ʾ");
			alert.setLayout(null);
			alert.setResizable(false);
			int x = X+50,y = Y+100,weight = 250,height = 200;
			alert.setBounds(x,y,weight,height);
			
			JLabel jl = new JLabel("ȷ���˳���");
			JLabel image = new JLabel(new ImageIcon("img/��ʾ.png"));
			jl.setFont(new Font("����",Font.BOLD,20));
			JButton yes = new JButton("��");
			JButton no = new JButton("��");
			
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
			
			ImageIcon icon = new ImageIcon("img/ϵͳͼ��.png");
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
	 * ʵ��undecorated������϶�
	 */
	boolean isMoved = false;
	Point prePoint = null,endPoint = null;
	private void setDragable(JPanel p,JFrame mainFrame) {
		this.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				isMoved = false;// ����ͷ����Ժ��ǲ�������ק����
				p.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			public void mousePressed(MouseEvent e) {
//System.out.println("���������");				
				if(e.getY()>=0 && e.getY()<=40)
					isMoved = true;
				prePoint = new Point(e.getX(), e.getY());// �õ�����ȥ��λ��
				p.setCursor(new Cursor(Cursor.MOVE_CURSOR));
			}
		});
		// �϶�ʱ��ǰ�������ȥ��갴��ȥʱ�����꣬���ǽ�����Ҫ�ƶ���������
		this.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (isMoved) {// �ж��Ƿ������ק
					endPoint = new Point(mainFrame.getLocation().x + e.getX() - prePoint.x,
							mainFrame.getLocation().y + e.getY() - prePoint.y);
					X = endPoint.x; Y = endPoint.y;
					mainFrame.setLocation(endPoint);
				}
			}
		});
	}

	

	/**
	 * ����ͼ��
	 */
	private void changeIcon(JButton b,String address) {
		ImageIcon image = new ImageIcon(address);  //����ť����ͼ��
		b.setIcon(image);
	}
	
	/**
	 * �̳�GridBagconstraints ������д
	 */
	class GBC extends GridBagConstraints {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		// ��ʼ�����Ͻ�λ��
		public GBC(int gridx, int gridy) {
			this.gridx = gridx;
			this.gridy = gridy;
		}

		// ��ʼ�����Ͻ�λ�ú���ռ����������
		public GBC(int gridx, int gridy, int gridwidth, int gridheight) {
			this.gridx = gridx;
			this.gridy = gridy;
			this.gridwidth = gridwidth;
			this.gridheight = gridheight;
		}

		// ���뷽ʽ
		public GBC setAnchor(int anchor) {
			this.anchor = anchor;
			return this;
		}

		// �Ƿ����켰���췽��
		public GBC setFill(int fill) {
			this.fill = fill;
			return this;
		}

		// x��y�����ϵ�����
		public GBC setWeight(double weightx, double weighty) {
			this.weightx = weightx;
			this.weighty = weighty;
			return this;
		}

		// �ⲿ���
		public GBC setInsets(int distance) {
			this.insets = new Insets(distance, distance, distance, distance);
			return this;
		}

		// �����
		public GBC setInsets(int top, int left, int bottom, int right) {
			this.insets = new Insets(top, left, bottom, right);
			return this;
		}

		// �����
		public GBC setIpad(int ipadx, int ipady) {
			this.ipadx = ipadx;
			this.ipady = ipady;
			return this;
		}

	}

	/**
	 * �����ҵĺ���Panel��mouseListener
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
			l.setFont(new Font("����",Font.BOLD,20));
		}
	
		@Override
		public void mouseReleased(MouseEvent e) {
			JPanel p = (JPanel)e.getSource();
			JLabel l = (JLabel) p.getComponent(2);
			l.setFont(new Font("����",Font.BOLD,15));
		}
	}

	/**
	 * ����Ⱥ��mouseListener
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
			l.setFont(new Font("����",Font.BOLD,20));
		}
	
		@Override
		public void mouseReleased(MouseEvent e) {
			JPanel p = (JPanel)e.getSource();
			JLabel l = (JLabel) p.getComponent(2);
			l.setFont(new Font("����",Font.BOLD,15));
		}
		
	}
	/**
	 * һֱ�ػ�mainFrame
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
		user.setName("����");
		new MainFrame().launchMainFrame(user);
	}*/
}
