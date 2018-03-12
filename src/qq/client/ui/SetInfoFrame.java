package qq.client.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import qq.client.manage.ManageClientConServerChatThread;
import qq.client.manage.ManageMainFrame;
import qq.common.Message;
import qq.common.MessageType;
import qq.common.User;

public class SetInfoFrame extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel head,name,signature;
	JTextField nameTF;
	JTextArea signatureTA;
	JButton select1,select2,submit; //1是系统自带 2是自选
	
	JFrame setInfoFrame;
	MainFrame mainFrame;
	
	User user = null;
	
	String address;
	public SetInfoFrame(User user,MainFrame mainFrame){
		this.user = user;
		this.mainFrame = mainFrame;
		address = user.getHead();
		
		setInfoFrame = new JFrame();
		head = new JLabel();
		head.setIcon(new ImageIcon(user.getHead()));
		
		name = new JLabel("昵称:");
		nameTF = new JTextField();
		nameTF.setText(user.getName());
		
		signature = new JLabel("个性签名:");
		signatureTA = new JTextArea();
		signatureTA.setText(user.getSignature());
		
		select1 = new JButton("系统头像");
		select1.addActionListener(this);
		select2 = new JButton("自定义头像");
		select2.addActionListener(this);
		submit = new JButton("确定");
		submit.addActionListener(this);	
		
		setInfoFrame.add(head); head.setBounds(20,20,100,100);
		setInfoFrame.add(name); name.setBounds(140,50,40,30);
		setInfoFrame.add(nameTF); nameTF.setBounds(180, 50, 250, 30);
		setInfoFrame.add(signature); signature.setBounds(140,90,80,30);
		setInfoFrame.add(signatureTA); signatureTA.setBounds(140, 120, 300, 150);
		
		setInfoFrame.add(select1); select1.setBounds(20,140,100,30);
		setInfoFrame.add(select2); select2.setBounds(20,190,100,30);
		setInfoFrame.add(submit); submit.setBounds(150,300,180,40);
		
		setInfoFrame.setLayout(null);
		setInfoFrame.setResizable(false);
		setInfoFrame.setBounds(mainFrame.getX()-500,mainFrame.getY()+50,500,400);
		setInfoFrame.setIconImage(new ImageIcon("img/系统图标副本.png").getImage());
		setInfoFrame.setVisible(true);
	}
	
	int cnt1 = 0;
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == select1){
			cnt1 += 1;
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
							user.setHead(address);
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
				f.setBounds(setInfoFrame.getX()+50,setInfoFrame.getY()+50,600,500);
				if(cnt1%2==1)
					f.setVisible(true);
				else
					f.setVisible(false);
		}
		
		if(e.getSource() == select2){
			JFileChooser jfc = new JFileChooser();
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			jfc.setDialogTitle("选择头像");
			jfc.showDialog(setInfoFrame, "选择");
			File file = jfc.getSelectedFile();
			
			if(file != null){
				String fileName = file.getName();
				Pattern p = Pattern.compile(".*\\.(jpg|png|gif|JPG|PNG|GIF)");
				Matcher m = p.matcher(fileName);
				boolean isImage = m.matches();
				if(isImage){
					address = file.getAbsolutePath().replaceAll("\\\\", "/");
					head.setIcon(new ImageIcon(address));
					user.setHead(address);
//System.out.println(file.getAbsolutePath());					
				}else{
					JDialog jd = new JDialog(setInfoFrame);
					jd.setTitle("错误");
					JLabel jl = new JLabel("请选择图片",new ImageIcon("img/Error.png"),JLabel.CENTER);
					jd.add(jl);
					jd.setVisible(true);
					jd.setBounds(setInfoFrame.getX()+250,setInfoFrame.getY()+400,150,100);
				}
			}
		}
		
		if(e.getSource() == submit){
			Message mesg = new Message();
			mesg.setMesgType(MessageType.MESSAGE_UPDATE);
			mesg.setSender(nameTF.getText());  //发送者是更新的昵称 内容是签名 接收者是头像地址
			mesg.setContent(signatureTA.getText());
			mesg.setReceiver(address);
			mesg.setSenderAccount(user.getAccount());
			try {
				ObjectOutputStream oos = new ObjectOutputStream (ManageClientConServerChatThread.
						getClientServerThread(user.getAccount() + MessageType.MESSAGE_CHAT).getS().getOutputStream());
				oos.writeObject(mesg);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			MainFrame mainFrame = ManageMainFrame.getMainFrame(user.getAccount());
			mainFrame.changeUserInfo(nameTF.getText(), address, signatureTA.getText());
			
			setInfoFrame.dispose();
		}
	}
	
	/*public static void main(String []args){
		User user = new User();
		user.setHead("H:/Java/java课设/MiniQQHeads/11.jpg");
		user.setName("AAA");
		user.setSignature("~~~~~~啦啦啦");
		MainFrame mainFrame = new MainFrame();
		mainFrame.setBounds(600,0,200,200);
		new SetInfoFrame(user, mainFrame);
	}*/
}
