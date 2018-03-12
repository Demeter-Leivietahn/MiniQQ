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
		
		
		login = new JButton("�� ½");
		login.setBackground(new Color(9,163,220));
		login.setFont(new Font("����",Font.PLAIN,16));
		login.setForeground(Color.white);
		login.setBorderPainted(false);
		login.addActionListener(this);
		
		minimize = new JButton();
		minimize.setIcon(new ImageIcon("img/��½��С������.png"));
		minimize.setContentAreaFilled(false);
		minimize.setMargin(new Insets(0,0,0,0));
		minimize.setBorderPainted(false);
		minimize.addActionListener(this);
		
		close = new JButton();
		close.setIcon(new ImageIcon("img/��½�رո���.png"));
		close.setContentAreaFilled(false);
		close.setMargin(new Insets(0,0,0,0));
		close.setBorderPainted(false);
		close.addActionListener(this);
		
		register = new JLabel("ע���˺�");  
        register.setCursor(new Cursor(Cursor.HAND_CURSOR));  
        register.setFont(new Font("����",Font.PLAIN,15));
        register.setForeground(new Color(38,133,227));  
        register.addMouseListener(this);
        
        forget = new JLabel("�һ�����");  
        forget.setCursor(new Cursor(Cursor.HAND_CURSOR));  
        forget.setFont(new Font("����",Font.PLAIN,15));
        forget.setForeground(new Color(38,133,227));  
        forget.addMouseListener(this);
        
        QRcode = new JLabel();
        QRcode.setIcon(new ImageIcon("img/��½��ά��0.png"));
        QRcode.addMouseListener(this);
        
        
        memberAdd = new JLabel();
        memberAdd.setIcon(new ImageIcon("img/��½�����û�0.png"));
        memberAdd.addMouseListener(this);
        
		
		String [] statusArr = {"����","�������","æµ","����"};
		JComboBox status = new JComboBox(statusArr);
		status.setBackground(new Color(235,243,249));
		JCheckBox remember = new JCheckBox("��ס����");
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
		
//		---------------------------------------------------------���������Ӻ�λ���趨
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
	 * �ı�ϵͳ�Դ���ͼ��
	 * @param frame
	 */
	private void changeIcon(JFrame frame){              
//		Toolkit tool=frame.getToolkit(); //�õ�һ��Toolkit���� 
//		Image image=tool.getImage("H:/Java/MiniQQImages/LoginIcon2.png"); //��tool��ȡͼ�� 
		ImageIcon icon = new ImageIcon("img/ϵͳͼ�긱��.png");
		frame.setIconImage(icon.getImage()); 
	}
	/**
	 * ����ͼ��
	 * @param frame
	 */
	private void addImage(JFrame frame){                
		JLabel left = new JLabel(new ImageIcon("img/��½ͷ��.png")); 
		frame.add(left); left.setBounds(20,210, 129, 149);
		JLabel top = new JLabel(new ImageIcon("img/��½��ͼ����.jpg")); 
		frame.add(top); top.setBounds(0,0, 540, 220);
	}
	
	/**
	 *�������Ե�actionEvent 
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
				account.setText("�û��������벻��ȷ");
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
			QRcode.setIcon(new ImageIcon("img/��½��ά��1.png"));
		}
		
		if(e.getSource() == memberAdd){
			memberAdd.setIcon(new ImageIcon("img/��½�����û�1.png"));
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource() == QRcode){
			QRcode.setIcon(new ImageIcon("img/��½��ά��0.png"));
		}
		
		if(e.getSource() == memberAdd){
			memberAdd.setIcon(new ImageIcon("img/��½�����û�0.png"));
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
	 * ʵ��undecorated������϶�
	 */
	boolean isMoved = false;
	Point prePoint = null,endPoint = null;
	private void setDragable(JFrame frame) {
		frame.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				isMoved = false;// ����ͷ����Ժ��ǲ�������ק����
			}

			public void mousePressed(MouseEvent e) {
//System.out.println("���������");				
				if(e.getY()>=0 && e.getY()<=40)
					isMoved = true;
				prePoint = new Point(e.getX(), e.getY());// �õ�����ȥ��λ��
			}
		});
		// �϶�ʱ��ǰ�������ȥ��갴��ȥʱ�����꣬���ǽ�����Ҫ�ƶ���������
		frame.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (isMoved) {// �ж��Ƿ������ק
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
