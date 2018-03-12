package qq.client.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import qq.server.db.DataBase;

public class ForgetPaswdFrame extends JFrame implements ActionListener{
	
	JLabel tip;
	JLabel image;
	JLabel p;
	JTextField phone;
	JButton submit;
	
	JFrame f = this;
	
	public ForgetPaswdFrame(){
		f = new JFrame();
		
		tip = new JLabel("�һ�����:");
		image = new JLabel(new ImageIcon("img/��ʾ.png"));
		p = new JLabel("�ֻ���:");
		phone = new JTextField();
		submit = new JButton("ȷ��");
		submit.addActionListener(this);
		
		f.setLayout(null);
		f.add(tip); tip.setBounds(80,25,100,30);
		f.add(image); image.setBounds(20, 20, 40, 40);
		f.add(p);p.setBounds(60,60,50,30);
		f.add(phone); phone.setBounds(120, 60, 200, 30);
		f.add(submit); submit.setBounds(150,110,60,30);
		f.setBounds(100,100,400,200);
		f.setTitle("�һ�����");
		ImageIcon icon = new ImageIcon("img/��ȫ.png");
		f.setIconImage(icon.getImage()); 
		f.setVisible(true);
	}
	
	/*public static void main(String []args){
		new ForgetPaswdFrame();
	}*/

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == submit){
			String paswd = DataBase.findPassword(phone.getText());
			JDialog jd = new JDialog();
			JLabel l1 = new JLabel("����������:");
			JLabel l2 = new JLabel(paswd,JLabel.CENTER);
			jd.add(l1); l1.setBounds(100,30,100,30);
			jd.add(l2); l2.setBounds(100,90,100,30);
			
			jd.setBounds(f.getX()+50,f.getY()+50,300,200);
			jd.setVisible(true);
		}
	}
}
