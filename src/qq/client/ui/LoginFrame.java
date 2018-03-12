package qq.client.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import qq.client.model.QQClientUser;
import qq.common.User;
import qq.server.db.DataBase;
public class LoginFrame extends JFrame implements ActionListener,MouseListener {
	private static final long serialVersionUID = 1L;
	
	static int X = 600 ,Y = 250;
	
	JFrame loginFrame;
	JButton login,minimize,close;
	JLabel register,forget,QRcode,memberAdd;
	JTextField account;
	TextField password;
	
	public void launchLoginFrame(){
//		this.client = client;
		loginFrame = new JFrame();
		
		account = new JTextField();
		password = new TextField();
		password.setEchoChar('*');
		
		
		login = new JButton("登 陆");
		login.setBackground(new Color(9,163,220));
		login.setFont(new Font("黑体",Font.PLAIN,16));
		login.setForeground(Color.white);
		login.setBorderPainted(false);
		login.addActionListener(this);
		
		minimize = new JButton();
		minimize.setIcon(new ImageIcon("img/登陆最小化副本.png"));
		minimize.setContentAreaFilled(false);
		minimize.setMargin(new Insets(0,0,0,0));
		minimize.setBorderPainted(false);
		minimize.addActionListener(this);
		
		close = new JButton();
		close.setIcon(new ImageIcon("img/登陆关闭副本.png"));
		close.setContentAreaFilled(false);
		close.setMargin(new Insets(0,0,0,0));
		close.setBorderPainted(false);
		close.addActionListener(this);
		
		register = new JLabel("注册账号");  
        register.setCursor(new Cursor(Cursor.HAND_CURSOR));  
        register.setFont(new Font("黑体",Font.PLAIN,15));
        register.setForeground(new Color(38,133,227));  
        register.addMouseListener(this);
        
        forget = new JLabel("找回密码");  
        forget.setCursor(new Cursor(Cursor.HAND_CURSOR));  
        forget.setFont(new Font("黑体",Font.PLAIN,15));
        forget.setForeground(new Color(38,133,227));  
        forget.addMouseListener(this);
        
        QRcode = new JLabel();
        QRcode.setIcon(new ImageIcon("img/登陆二维码0.png"));
        QRcode.addMouseListener(this);
        
        
        memberAdd = new JLabel();
        memberAdd.setIcon(new ImageIcon("img/登陆增加用户0.png"));
        memberAdd.addMouseListener(this);
        
		
		String [] statusArr = {"在线","请勿打扰","忙碌","隐身"};
		JComboBox status = new JComboBox(statusArr);
		status.setBackground(new Color(235,243,249));
		JCheckBox remember = new JCheckBox("记住密码");
		remember.setBackground(new Color(235,243,249));
		
		loginFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {   
				loginFrame.dispose();
			}
		});
		
		loginFrame.getContentPane().setBackground(Color.white);
		loginFrame.setBounds(X, Y, 540, 400);
		loginFrame.setLayout(null);
		loginFrame.setResizable(false);
		loginFrame.setUndecorated(true);
		loginFrame.getContentPane().setBackground(new Color(235,243,249));
		setDragable(loginFrame);
		loginFrame.setVisible(true);
		
//		---------------------------------------------------------各种组件添加和位置设定
		loginFrame.add(account); account.setBounds(170,240,250,35);
//		loginFrame.add(nameL);nameL.setBounds(150,180,60,40);
		
		loginFrame.add(password); password.setBounds(170,275,250,35);
//		loginFrame.add(passwordL); passwordL.setBounds(150, 220, 250, 35);
		
		loginFrame.add(login);login.setBounds(170, 350, 250, 40);		
//		frame.add(register); register.setBounds(150,300,100,40);
		
		loginFrame.add(register); register.setBounds(430, 235, 60, 40);
		loginFrame.add(forget); forget.setBounds(430, 275, 60, 40);

		
		loginFrame.add(remember); remember.setBounds(165,320,100,25);		
		loginFrame.add(status); status.setBounds(315, 320, 105, 25);
		
		loginFrame.add(minimize); minimize.setBounds(480,0,30,30);
		loginFrame.add(close); close.setBounds(510,0,30,30);
		
		loginFrame.add(QRcode); QRcode.setBounds(510, 370, 30, 30);
		loginFrame.add(memberAdd); memberAdd.setBounds(0, 370, 30, 30);
		
		changeIcon(loginFrame);
		addImage(loginFrame);
	}
	
	/**
	 * 改变系统自带的图标
	 * @param frame
	 */
	private void changeIcon(JFrame frame){              
//		Toolkit tool=frame.getToolkit(); //得到一个Toolkit对象 
//		Image image=tool.getImage("H:/Java/MiniQQImages/LoginIcon2.png"); //由tool获取图像 
		ImageIcon icon = new ImageIcon("img/系统图标副本.png");
		frame.setIconImage(icon.getImage()); 
	}
	/**
	 * 增加图像
	 * @param frame
	 */
	private void addImage(JFrame frame){                
		JLabel left = new JLabel(new ImageIcon("img/登陆头像.png")); 
		frame.add(left); left.setBounds(20,210, 129, 149);
		JLabel top = new JLabel(new ImageIcon("img/登陆主图副本.jpg")); 
		frame.add(top); top.setBounds(0,0, 540, 220);
	}
	
	/**
	 *处理所以的actionEvent 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == login){
			
			QQClientUser qqClientUser = new QQClientUser();
			User user = new User();
			user.setAccount(account.getText());
			user.setPassword(password.getText());
		
			if(qqClientUser.checkUser(user)){
				user = DataBase.update(user.getAccount());
				
			
				new SplashScreen(user,loginFrame).start();
				loginFrame.dispose();
			}
			else{
				password.setText("");
				account.setText("用户名或密码不正确");
			}
		}
		
		if(e.getSource() == minimize){
			int state = getExtendedState();
			state = JFrame.ICONIFIED;
			loginFrame.setExtendedState(state);
		}
		
		if(e.getSource() == close){
			loginFrame.dispose();
		}
					
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getSource() == QRcode){
			QRcode.setIcon(new ImageIcon("img/登陆二维码1.png"));
		}
		
		if(e.getSource() == memberAdd){
			memberAdd.setIcon(new ImageIcon("img/登陆增加用户1.png"));
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource() == QRcode){
			QRcode.setIcon(new ImageIcon("img/登陆二维码0.png"));
		}
		
		if(e.getSource() == memberAdd){
			memberAdd.setIcon(new ImageIcon("img/登陆增加用户0.png"));
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getSource() == forget){
			new ForgetPaswdFrame();
		}
		if(e.getSource() == register){
			new RegisterFrame();
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
	
	/**
	 * 实现undecorated窗体的拖动
	 */
	boolean isMoved = false;
	Point prePoint = null,endPoint = null;
	private void setDragable(JFrame frame) {
		frame.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				isMoved = false;// 鼠标释放了以后，是不能再拖拽的了
			}

			public void mousePressed(MouseEvent e) {
//System.out.println("按下了鼠标");				
				if(e.getY()>=0 && e.getY()<=40)
					isMoved = true;
				prePoint = new Point(e.getX(), e.getY());// 得到按下去的位置
			}
		});
		// 拖动时当前的坐标减去鼠标按下去时的坐标，就是界面所要移动的向量。
		frame.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (isMoved) {// 判断是否可以拖拽
					endPoint = new Point(loginFrame.getLocation().x + e.getX() - prePoint.x,
							loginFrame.getLocation().y + e.getY() - prePoint.y);
					X = endPoint.x; Y = endPoint.y;
					loginFrame.setLocation(endPoint);
				}
			}
		});
	}
	
	public static void main(String []args){
		new LoginFrame().launchLoginFrame();
	}

}
