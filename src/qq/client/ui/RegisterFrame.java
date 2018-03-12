package qq.client.ui;

import java.awt.*;
import javax.swing.*;

import qq.common.User;
import qq.server.db.DataBase;

import java.awt.event.*;

public class RegisterFrame extends JFrame implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	User user = null;
	RegisterFrame registerFrame = this;
	
	JLabel accountL,passwordL,nameL,emailL,phoneL,signature;
	JLabel accountTip1,accountTip2,passwordTip,nameTip,emailTip,phoneTip;
	JTextField accountTF,passwordTF,nameTF,emailTF,phoneTF;
	JTextArea signatureTF;
	JLabel head;
	JButton submit,reset,select;
	
	String address = "img/11.jpg";
	
	public RegisterFrame(){
		head = new JLabel(new ImageIcon("img/11.jpg"));
		head.setBounds(20, 40, 100, 100);
//		head
		
		nameL = new JLabel("昵称: ");		nameL.setBounds(140,30,40,20);
		nameL.setFont(new Font("微软雅黑",Font.BOLD,15));
		nameTF = new JTextField(); nameTF.setBounds(140, 60, 200, 30);
		
		signature = new JLabel("个性签名: ");		signature.setBounds(140,100,70,20);
		signature.setFont(new Font("微软雅黑",Font.BOLD,15));
		signatureTF = new JTextArea(); signatureTF.setBounds(140, 130, 250, 100);
		
		accountL = new JLabel("账号: "); 	accountL.setBounds(40,275,40,20);
		accountL.setFont(new Font("微软雅黑",Font.BOLD,15));
		accountTF = new JTextField(); accountTF.setBounds(90, 270, 200, 30);
		
		passwordL = new JLabel("密码: ");	passwordL.setBounds(40,335,40,20);
		passwordL.setFont(new Font("微软雅黑",Font.BOLD,15));
		passwordTF = new JTextField(); passwordTF.setBounds(90, 330, 200, 30);
		
		emailL = new JLabel("邮箱: ");		emailL.setBounds(40,395,40,20);
		emailL.setFont(new Font("微软雅黑",Font.BOLD,15));
		emailTF = new JTextField(); emailTF.setBounds(90, 390, 200, 30);
		
		phoneL = new JLabel("电话: ");		phoneL.setBounds(40,455,40,20);
		phoneL.setFont(new Font("微软雅黑",Font.BOLD,15));
		phoneTF = new JTextField(); phoneTF.setBounds(90, 450, 200, 30);
		
		
		submit = new JButton("注册"); submit.setBounds(90,500,80,40);
		submit.addActionListener(this);
		
		reset = new JButton("重置");		reset.setBounds(220,500,80,40);
		reset.addActionListener(this);
		
		select = new JButton("选择头像"); select.setBounds(20,150,100,40);
		select.addActionListener(this);
		
		nameTip = new JLabel("*昵称不能为空"); 	nameTip.setBounds(190,30,90,20);
		nameTip.setFont(new Font("微软雅黑",Font.ITALIC,12)); nameTip.setForeground(Color.red);
		nameTip.setVisible(false);
		
		accountTip1 = new JLabel("*账号已存在"); 	accountTip1.setBounds(320,270,90,20);
		accountTip1.setFont(new Font("微软雅黑",Font.ITALIC,12)); accountTip1.setForeground(Color.red);
		accountTip1.setVisible(false);
		
		accountTip2 = new JLabel("*账号不能为空"); 	accountTip2.setBounds(320,270,90,20);
		accountTip2.setFont(new Font("微软雅黑",Font.ITALIC,12)); accountTip2.setForeground(Color.red);
		accountTip2.setVisible(false);
		
		passwordTip = new JLabel("*密码不能为空"); 	passwordTip.setBounds(320,330,90,20);
		passwordTip.setFont(new Font("微软雅黑",Font.ITALIC,12)); passwordTip.setForeground(Color.red);
		passwordTip.setVisible(false);
		
		emailTip = new JLabel("*邮箱不能为空"); 	emailTip.setBounds(320,390,90,20);
		emailTip.setFont(new Font("微软雅黑",Font.ITALIC,12)); emailTip.setForeground(Color.red);
		emailTip.setVisible(false);
		
		phoneTip = new JLabel("*手机不能为空"); 	phoneTip.setBounds(320,450,90,20);
		phoneTip.setFont(new Font("微软雅黑",Font.ITALIC,12)); phoneTip.setForeground(Color.red);
		phoneTip.setVisible(false);
		
		
		this.add(head);
		this.add(signature);this.add(signatureTF);
		this.add(accountL); this.add(accountTF);this.add(accountTip1); this.add(accountTip2);
		this.add(passwordL); this.add(passwordTF); this.add(passwordTip);
		this.add(nameL); this.add(nameTF); this.add(nameTip);
		this.add(emailL); this.add(emailTF); this.add(emailTip);
		this.add(phoneL); this.add(phoneTF); this.add(phoneTip);
		
		this.add(select);
		this.add(submit);
		this.add(reset);
		
		
		this.setTitle("注册");
		ImageIcon icon = new ImageIcon("img/系统图标副本.png");
		this.setIconImage(icon.getImage()); 
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				registerFrame.dispose();
			}
		});
		this.setLayout(null);
		this.setBounds(600,250,450,600);
		this.setResizable(false);
		this.setVisible(true);
	}
	
/*	public static void main(String []args){
		new RegisterFrame();
	}*/

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == submit){
			boolean ok = false;
			boolean exist = DataBase.checkAccount(accountTF.getText());
			
			if(exist){
				accountTip1.setVisible(true);
			}
			else{ 
				accountTip1.setVisible(false);
			}
			
			if(accountTF.getText().equals("")){
				accountTip2.setVisible(true); 
				ok = false;
			}
			else{
				accountTip2.setVisible(false);
				ok = true;
			}
			
			if(passwordTF.getText().equals("")){
				passwordTip.setVisible(true);
				ok = false;
			}
			else{
				passwordTip.setVisible(false);
				ok = true;
			}
			
			if(nameTF.getText().equals("")){
				nameTip.setVisible(true);
				ok = false;
			}
			else{
				nameTip.setVisible(false);
				ok = true;
			}
			
			if(emailTF.getText().equals("")){
				emailTip.setVisible(true);
				ok = false;
			}
			else{
				emailTip.setVisible(false);
				ok = true;
			}

			if(phoneTF.getText().equals("")){
				phoneTip.setVisible(true);
				ok = false;
			}
			else{
				phoneTip.setVisible(false);
				ok = true;
			}
			

			/**
			 * ok以后就可以把数据放到数据库了
			 */
			if(ok && !exist){
				user = new User();
				user.setAccount(accountTF.getText());
				user.setPassword(passwordTF.getText());
				user.setName(nameTF.getText());
				user.setEmail(emailTF.getText());
				user.setPhone(phoneTF.getText());
				user.setHead(address);
				user.setSignature(signatureTF.getText());
//System.out.println(user.getHead());				
//System.out.println(user.getSignature());				
				DataBase.register(user);
				registerFrame.dispose();
			}
		}
		else if(e.getSource() == reset){
			accountTF.setText("");
			passwordTF.setText("");
			nameTF.setText("");
			emailTF.setText("");
			phoneTF.setText("");
		}
		
		else if(e.getSource() == select){
			JFrame f = new JFrame();
			JPanel totalP = new JPanel();
			totalP.setLayout(new GridLayout(5,5));
			totalP.setBackground(Color.white);
			JScrollPane jsp = new JScrollPane(totalP);
			jsp.getVerticalScrollBar().setUnitIncrement(60);
				for(int i=1;i<26;i++){
					JPanel headP = new JPanel(); headP.setBackground(Color.white);
					JLabel headImage = new JLabel(new ImageIcon("img/" + i + ".jpg"));
					JLabel headAddress = new JLabel("img/" + i + ".jpg"); 
					headP.add(headAddress); headAddress.setVisible(false);
					headP.add(headImage);
					headP.addMouseListener(new MouseListener() {
						public void mouseReleased(MouseEvent e) {
						}
						public void mousePressed(MouseEvent e) { 
							f.setVisible(false); 
							JPanel p = (JPanel)e.getComponent();
							JLabel l = (JLabel)p.getComponent(0);
							address = l.getText();
							head.setIcon(new ImageIcon(address));
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
					totalP.add(headP);
				}
			f.add(jsp);
			f.setUndecorated(true);
			f.setBounds(registerFrame.getX()+50,registerFrame.getY()+50,600,500);
			f.setVisible(true);
			
		}
	}
}
