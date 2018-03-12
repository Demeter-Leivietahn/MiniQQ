package qq.client.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.*;

import qq.client.manage.ManageClientConServerChatThread;
import qq.common.Friend;
import qq.common.Message;
import qq.common.MessageType;
import qq.common.User;

public class PaintFrame extends JFrame implements ActionListener {
	
	JButton chooseColor,eraser;
	JTextField strokeSize;
	JLabel penSize;
	
	JFrame paintFrame = this;
	
	Message mesg;
	Canva canva ;
	Color color;

	public PaintFrame(Message mesg, ChatFrame chatFrame) {
		this.mesg = mesg;
		color = Color.black;
				
		paintFrame = new JFrame();
		canva = new Canva();
		
		chooseColor = new JButton();
		chooseColor.setIcon(new ImageIcon("img/画笔颜色按钮.jpg"));
		chooseColor.setContentAreaFilled(false);   
		chooseColor.setMargin(new Insets(0, 0, 0, 0));
		chooseColor.setBorderPainted(false);
		chooseColor.addActionListener(this);
		
		eraser = new JButton();
		eraser.setIcon(new ImageIcon("img/橡皮按钮.png"));
		eraser.setContentAreaFilled(false);   //不绘制button的内容区
		eraser.setMargin(new Insets(0, 0, 0, 0));
		eraser.setBorderPainted(false);
		eraser.addActionListener(this);
		
		strokeSize = new JTextField("15");
		penSize = new JLabel("画笔大小:");
		paintFrame.setLayout(null);
		paintFrame.add(canva); canva.setBounds(0,30,500,370);
		paintFrame.add(chooseColor); chooseColor.setBounds(20,0,60,30);
		paintFrame.add(eraser); eraser.setBounds(100,0,60,30);
		paintFrame.add(penSize); penSize.setBounds(200,0,60,30);
		paintFrame.add(strokeSize); strokeSize.setBounds(270, 0, 100, 30);
		canva.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		paintFrame.setTitle("画图");
		paintFrame.setIconImage(new ImageIcon("img/系统图标副本.png").getImage());
		paintFrame.setBounds(chatFrame.getX() + 50, chatFrame.getY() + 250, 500, 400);
		paintFrame.setResizable(false);
		paintFrame.setVisible(true);
	}
	
	public Canva getCanva(){
		return canva;
	}

	public class Canva extends Canvas implements MouseMotionListener, MouseListener {
		int X,Y;
		public Canva() {
			setBackground(Color.white);
			setSize(500, 370);
			this.addMouseListener(this);
			this.addMouseMotionListener(this);
		}
		
		public void send(String str){
			try {
System.out.println(str);				
				ObjectOutputStream oos = new ObjectOutputStream( ManageClientConServerChatThread.
						getClientServerThread(mesg.getSenderAccount() + MessageType.MESSAGE_CHAT).getS().getOutputStream());
				mesg.setContent(str);
				mesg.setMesgType(MessageType.MESSAGE_PAINT);
				oos.writeObject(mesg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void setPaint(int x,int y,float size,int r,int g,int b){
			X = x; Y = y;
			strokeSize.setText(String.valueOf(size));
			color = new Color(r,g,b);
		}
		public void paint(Graphics g) {// 画布的paint方法，
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(color);
			g2.setStroke(new BasicStroke(Float.parseFloat(strokeSize.getText())));
			g2.drawLine(X, Y, X, Y);
		}

		@Override
		public void update(Graphics g){
			paint(g);
		}
		@Override
		public void mouseDragged(MouseEvent e) {
			X = e.getX(); Y = e.getY();
			send(X + " " + Y + " " + strokeSize.getText() +" " + color.getRed()+" "+color.getGreen()+" "+color.getBlue());
			canva.repaint();
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		public void mouseMoved(MouseEvent e) {
		}

		public void mouseClicked(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}
	}
	
	int cnt = 0;
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == chooseColor){
			color = JColorChooser.showDialog(paintFrame, "请选择颜色",color);
		}
		
		if(e.getSource() == eraser){
			
		}
		
		if(e.getSource() == eraser){
			cnt += 1;
			if(cnt%2==1){
				color = Color.white;
			}else{
				color = Color.black;
			}
		}
	}
	
	public static void main(String[] args) {
		User user = new User();
		Friend friend = new Friend();
		Icon headIcon = null;
		ChatFrame c = new ChatFrame(user, friend, headIcon);
		Message mesg = null;
		c.setLocation(200, 100);
		new PaintFrame(mesg, c);
	}


}
