package qq.client.ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import qq.client.manage.ManageChatFrame;
import qq.client.manage.ManageClientConServerChatThread;
import qq.client.manage.ManageFileSend;
import qq.client.manage.ManagePaintFrame;
import qq.client.manage.MangeImageSend;
import qq.common.Friend;
import qq.common.Message;
import qq.common.MessageType;
import qq.common.User;

import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class ChatFrame extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	ChatFrame chatFrame = this;
	
	
	static final int WIDTH = 800, HEIGHT = 850;
	static int X = 400, Y = 100;
	JPanel top,mid,bottom,bottomBar;
	JButton minimize,setting,close;
	JButton emoji,imageSend,fileSend,movieSend,msgSend,paint;
	JTextPane typeArea,chatArea;
	JFrame faces;
	
	Color theme;
	User user = null;
	Friend friend = null;
	File file = null;
	

	
	public ChatFrame(User user,Friend friend,Icon headIcon){
		this.user = user;
		this.friend = friend;
		theme = Color.lightGray;
		
		JPanel back = new JPanel();
		top = new JPanel();
		mid = new JPanel();
		bottom = new JPanel();

//----------------------------��������		
		back.setLayout(null);
		back.add(top); top.setBounds(0, 0, WIDTH, 150);
		top.setBackground(theme);
		back.add(mid); mid.setBounds(0, 150, WIDTH, 500);
		mid.setBackground(Color.white);
		back.add(bottom); bottom.setBounds(0, 650, WIDTH, 200);
		bottom.setBackground(theme);
		
//-----------------------------top
//-------------------------------------top��ť
		minimize = new JButton();
		minimize.addActionListener(this);
		minimize.setContentAreaFilled(false);
		minimize.setMargin(new Insets(0,0,0,0));
		minimize.setBorderPainted(false);
		changeIcon(minimize,"img/��½��С������.png");
		
		setting = new JButton();
		setting.addActionListener(this);                   
		setting.setContentAreaFilled(false);
		setting.setMargin(new Insets(0,0,0,0));
		setting.setBorderPainted(false);
		changeIcon(setting,"img/���ָ���.png");
		
		close = new JButton();
		close.addActionListener(this);
		close.setContentAreaFilled(false);
		close.setMargin(new Insets(0,0,0,0));
		close.setBorderPainted(false);
		changeIcon(close,"img/��½�رո���.png");
		
//-----------------------------------------top����
		JLabel headImage = new JLabel();
		headImage.setIcon(headIcon);
		
		JLabel title = new JLabel();
		title.setFont(new Font("����",Font.BOLD,20));
		title.setText(friend.getName());
		
		top.add(headImage); headImage.setBounds(20,20,100,100);
		top.add(title); title.setBounds(140,60,100,20);
		
		this.setDragable(top);
		top.setLayout(null);
		top.add(setting); setting.setBounds(WIDTH-105,0,35,35);
		top.add(minimize); minimize.setBounds(WIDTH-70,0,35,35);
		top.add(close);	   close.setBounds(WIDTH-35,0,35,35);
//-----------------------------mid
		mid.setLayout(null);
		chatArea = new JTextPane();
		chatArea.setEditable(false); //���ɱ༭
		chatArea.setFont(new Font("����",Font.PLAIN,20));
		JScrollPane chatJsp = new JScrollPane(chatArea);
		chatJsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 

		
		mid.add(chatJsp); chatJsp.setBounds(0,0,WIDTH,500);
//-----------------------------bottom
		bottom.setLayout(null);
		
		
		bottomBar = new JPanel(null);
		bottomBar.setBackground(theme);
		typeArea = new JTextPane();
		typeArea.setFont(new Font("����",Font.PLAIN,20));
		JScrollPane typeJsp = new JScrollPane(typeArea);
		typeJsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//		typeArea.addKeyListener(this);
		
		msgSend = new JButton("����"); 
		msgSend.addActionListener(this);
		
		emoji =  new JButton(); changeIcon(emoji,"img/EmojiButton2.jpg"); 
		emoji.setToolTipText("���ͱ���");
		emoji.setContentAreaFilled(false);  emoji.addActionListener(this);
		
		imageSend = new JButton(); changeIcon(imageSend, "img/PhotoSendButton.jpg");
		imageSend.setToolTipText("����ͼƬ");
		imageSend.setContentAreaFilled(false); imageSend.addActionListener(this);
		
		fileSend = new JButton(); changeIcon(fileSend, "img/FileSendButton.jpg");
		fileSend.setToolTipText("�����ļ�");
		fileSend.setContentAreaFilled(false); fileSend.addActionListener(this);
		
		movieSend = new JButton();changeIcon(movieSend, "img/MovieSendButton.jpg");
		movieSend.setToolTipText("������Ƶ");
		movieSend.setContentAreaFilled(false); movieSend.addActionListener(this);
		
		paint = new JButton(); changeIcon(paint, "img/PaintButton.jpg");
		paint.setToolTipText("��ͼ");
		paint.setContentAreaFilled(false);  paint.addActionListener(this);
		
		bottomBar.add(emoji); 		emoji.setBounds(10, 3, 35, 35);
		bottomBar.add(imageSend);	imageSend.setBounds(55, 3, 35, 35);
		bottomBar.add(fileSend);	fileSend.setBounds(100, 3, 35, 35);
		bottomBar.add(movieSend);	movieSend.setBounds(145, 3, 35, 35);
		bottomBar.add(paint);		paint.setBounds(190, 3, 35, 35);
		
		bottom.add(bottomBar); bottomBar.setBounds(0, 0, WIDTH, 40);  
		bottom.add(typeJsp); typeJsp.setBounds(0,40,WIDTH,120);  
		bottom.add(msgSend); msgSend.setBounds(WIDTH-100,160,80,40);
		
		this.setLayout(null);
		this.add(back); back.setBounds(0,0,WIDTH,HEIGHT);
		this.setBounds(X, Y, WIDTH, HEIGHT);
		this.setUndecorated(true);
		this.setIconImage(new ImageIcon("img/ϵͳͼ�긱��.png").getImage()); 
		this.setVisible(true);
		
		//��ӱ���
		addEmojis();
	}
	
	/**
	 * Ϊ���鴰����ӱ���
	 */
	private void addEmojis(){
		faces = new JFrame();
		JPanel totalP = new JPanel();
		totalP.setLayout(new GridLayout(15,7));
		totalP.setBackground(Color.white);
		JScrollPane jsp = new JScrollPane(totalP);
		for(int j=1;j<5;j++){
			for(int i=1;i<24;i++){
				JPanel faceP = new JPanel(); faceP.setBackground(Color.white);
				JLabel faceImage = new JLabel(new ImageIcon("img/"+ i +".png"));
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
		faces.setBounds(chatFrame.getX(),chatFrame.getY()+350,450,300);
		faces.setVisible(false);
	}
	
	/**
	 * ����ͼƬ��id��TextPane�������ͼƬ
	 * @param id
	 */
	private void insertFace(String id,JTextPane pane) {
		ImageIcon icon = new ImageIcon("img/"+ id +".png");
		
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
	 * ����ͼƬ������TextPane�в���ͼƬ
	 * @param imageName
	 * @param pane
	 */
	private void insertImage(String imageName,JTextPane pane){
		ImageIcon icon = new ImageIcon("D:/MiniQQServerSave/"+imageName);
		
		StyledDocument doc = pane.getStyledDocument();
		Style style = doc.addStyle("", null);
		StyleConstants.setIcon(style, icon);
		try {
			doc.insertString(doc.getLength(), "IMAGE" , style);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	/**
	 * ��TextPane�в����ַ���
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
	 * ��������¼�
	 */
	int cnt = 0;
	@Override
	public void actionPerformed(ActionEvent e) {
		//����
		if(e.getSource() == setting){
			theme = JColorChooser.showDialog(chatFrame, "��ѡ����ɫ",theme);
			top.setBackground(theme);
			bottom.setBackground(theme);
			bottomBar.setBackground(theme);
		}
		//��С��
		if(e.getSource() == minimize){
			int state = getExtendedState();
			state = JFrame.ICONIFIED;
			this.setExtendedState(state);
		}
		//�ر�
		if(e.getSource() == close){
//System.out.println(ManageChatFrame.getChatFrame(user.getAccount()+" "+friend.getAccount()));
			ManageChatFrame.removeChatFrame(user.getAccount()+" "+friend.getAccount());
//System.out.println(ManageChatFrame.getSize());			
			this.dispose();
		}
		//����������Ϣ
		if(e.getSource() == msgSend){
			String str =typeArea.getText(); 
			typeArea.setText("");
			Message mesg = setMesg(str,MessageType.MESSAGE_CHAT);
			showMesg(mesg);
			sendMesg(mesg);
		}
		//���ͱ���
		if(e.getSource() == emoji){
			faces.setLocation(chatFrame.getX(), chatFrame.getY()+350);
			cnt +=1;			
			if(cnt%2 != 0)
				faces.setVisible(true);
			else 
				faces.setVisible(false);
		}
		//�����ļ�
		if(e.getSource() == fileSend){
			JFileChooser jfc = new JFileChooser();
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			jfc.setDialogTitle("ѡ���ļ�");
			jfc.showDialog(chatFrame, "ѡ��");
			file = jfc.getSelectedFile();
			
			if(file != null){
				//���ļ�ʱ�ȷ���һ��������Ϣ ����Ϊ�ļ���
				Message mesg = setMesg(file.getName(), MessageType.MESSAGE_FILE);
				sendMesg(mesg);
			}
		}
		//������Ƶ
		if(e.getSource() == movieSend){
			JFileChooser jfc = new JFileChooser();
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			jfc.setDialogTitle("ѡ����Ƶ");
			jfc.showDialog(chatFrame, "ѡ��");
			file = jfc.getSelectedFile();
			
			if(file != null){
				String fileName = file.getName();
				Pattern p = Pattern.compile(".*\\.(mp4|rmvb|flv|mpeg|avi)");
				Matcher m = p.matcher(fileName);
				boolean isMovie = m.matches();
				if(isMovie){
					//���ļ�ʱ�ȷ���һ��������Ϣ ����Ϊ��Ƶ��
					Message mesg = setMesg(fileName, MessageType.MESSAGE_MOVIE);
					sendMesg(mesg);
				}else{
					JDialog jd = new JDialog(chatFrame);
					jd.setTitle("����");
					JLabel jl = new JLabel("��ѡ����Ƶ�ļ�",new ImageIcon("img/Error.png"),JLabel.CENTER);
					jd.add(jl);
					jd.setVisible(true);
					jd.setBounds(chatFrame.getX()+250,chatFrame.getY()+400,150,100);
				}
			}
		}
		//����ͼƬ
		if(e.getSource() == imageSend){
			
			JFileChooser jfc = new JFileChooser();
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			jfc.setDialogTitle("ѡ��ͼƬ");
			jfc.showDialog(chatFrame, "ѡ��");
			file = jfc.getSelectedFile();
			
			if(file != null){
				String fileName = file.getName();
				Pattern p = Pattern.compile(".*\\.(jpg|png|gif|JPG|PNG|GIF)");
				Matcher m = p.matcher(fileName);
				boolean isImage = m.matches();
				if(isImage){
					Message mesg = setMesg(file.getName(), MessageType.MESSAGE_PHOTO);
					MangeImageSend.sendFile(file , user, mesg);
					sendMesg(mesg);
					showImage(mesg);
				}else{
					JDialog jd = new JDialog(chatFrame);
					jd.setTitle("����");
					JLabel jl = new JLabel("��ѡ��ͼƬ",new ImageIcon("img/Error.png"),JLabel.CENTER);
					jd.add(jl);
					jd.setVisible(true);
					jd.setBounds(chatFrame.getX()+250,chatFrame.getY()+400,150,100);
				}
			}
			
		}
		
		//��ͼ
		if(e.getSource() == paint){
			Message mesg = setMesg("��ͼ",MessageType.MESSAGE_PAINT);
			sendMesg(mesg);
			PaintFrame paintFrame = new PaintFrame(mesg,chatFrame);
			ManagePaintFrame.addPaintFrame(mesg.getSenderAccount(), paintFrame);
		}
	
	}
	/**
	 * ������Ϣ
	 * @param str
	 */
	private Message setMesg(String str,String mesgType){
		Message mesg = new Message();
		mesg.setMesgType(mesgType);
		mesg.setContent(str);
		mesg.setSender(user.getName());
		mesg.setSenderAccount(user.getAccount());
		mesg.setReceiver(friend.getName());
		mesg.setReceiverAccount(friend.getAccount());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
		String time = df.format(new Date());// new Date()Ϊ��ȡ��ǰϵͳʱ��
		mesg.setTime(time);
		return mesg;
	}
	/**
	 * ������Ϣ
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
	 * �����������ʾ��Ϣ
	 */
	public void showMesg(Message mesg){
		String show = mesg.getTime() + " " + mesg.getSender() +" ˵ :\n";
		insertStr(show);
		
		String content = mesg.getContent();
		char [] c = new char[content.length()];
		c = content.toCharArray();
		String str = "";
		for(int i=0;i<c.length;i++){                 //����ַ��������еı�������滻��ͼƬ
			if(c[i] == '#'){
				if(c[i] == '#'&&i+2<c.length &&(c[i+2]=='0'||c[i+2]=='1'||c[i+2] =='2'||c[i+2]=='3'||c[i+2]=='4'||c[i+2]=='5'||
				c[i+2]=='6'||c[i+2]=='7'||c[i+2]=='8'||c[i+2]=='9')){
					String id = "" + c[i+1] + c[i+2];
					insertFace(id,chatArea);
					i=i+2; //�����������
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
	 * �����������ʾͼƬ
	 * @param mesg
	 */
	public void showImage(Message mesg){
		String show = mesg.getTime() + " " + mesg.getSender() +" ˵ :\n\n";
		insertStr(show);
		//��Ϣ����ΪͼƬ��
		insertImage(mesg.getContent(), chatArea);
		insertStr("\n\n");
	}
	/**
	 * �����ļ�����·��ѡ��ĶԻ���
	 * @return
	 */
	public void popFileChoose(Message mesg){
		JDialog jd = new JDialog(chatFrame);
		jd.setTitle("��ʾ");
		JTextArea text = null;
		String fileName = mesg.getContent();
		if(mesg.getMesgType().equals(MessageType.MESSAGE_MOVIE)){
			text = new JTextArea("�յ���Ƶ: \n" + fileName + "\n�Ƿ���գ�");
		}
		if(mesg.getMesgType().equals(MessageType.MESSAGE_FILE)){
			text = new JTextArea("�յ�һ���ļ�: \n" + fileName + "\n�Ƿ���գ�");
		}
		text.setFont(new Font("����",Font.PLAIN,15));
		JButton yes = new JButton("��"); yes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == yes){
					jd.dispose();
					JFileChooser jfc = new JFileChooser();
					jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					jfc.setDialogTitle("ѡ�����·��");
					jfc.showDialog(chatFrame, "ȷ��");
					String saveDir = jfc.getSelectedFile().getAbsolutePath();
					
					Message reply = null;
					if(mesg.getMesgType().equals(MessageType.MESSAGE_FILE)){
						reply = setMesg("ͬ�����"+saveDir, MessageType.MESSAGE_FILE);
					}
					
					if(mesg.getMesgType().equals(MessageType.MESSAGE_MOVIE)){
						reply = setMesg("ͬ�����"+saveDir, MessageType.MESSAGE_MOVIE);
					}
					sendMesg(reply);
				}
			}
		});
		JButton no = new JButton("��"); no.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == no){
					jd.dispose();
				}
			}
		});
		jd.setLayout(null); jd.setBounds(chatFrame.getX()+200,chatFrame.getY()+350,400,300);
		jd.add(text); text.setBounds(70,50,240,70);
		jd.add(yes); yes.setBounds(80,150,50,50);
		jd.add(no); no.setBounds(250,150,50,50);
		jd.setVisible(true);
	}
	/**
	 * �����ļ�
	 * @param saveDir
	 */
	public void sendFile(String saveDir){
		Message mesg = setMesg("�����ļ�", MessageType.MESSAGE_FILE);
		ManageFileSend.sendFile(file, user, mesg, saveDir);
	}
	
	/**
	 * �򿪻���
	 */
	public void openPaintBoard(Message mesg){
		PaintFrame paintFrame = new PaintFrame(mesg,chatFrame);
		ManagePaintFrame.addPaintFrame(mesg.getReceiverAccount(), paintFrame);
	}
	/**
	 * ����ͼ��
	 */
	private void changeIcon(JButton b,String address) {
		ImageIcon image = new ImageIcon(address);  //����ť����ͼ��
		b.setIcon(image);
	}
	
	/**
	 * ʵ��undecorated������϶�
	 */
	boolean isMoved = false;
	Point prePoint = null,endPoint = null;
	private void setDragable(final JPanel p) {
		this.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				isMoved = false;// ����ͷ����Ժ��ǲ�������ק����
				p.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			public void mousePressed(MouseEvent e) {
				if(e.getY()>=0 && e.getY()<=40){
					isMoved = true;
					prePoint = new Point(e.getX(), e.getY());// �õ�����ȥ��λ��
					p.setCursor(new Cursor(Cursor.MOVE_CURSOR));
				}
			}
		});
		// �϶�ʱ��ǰ�������ȥ��갴��ȥʱ�����꣬���ǽ�����Ҫ�ƶ���������
		this.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (isMoved) {// �ж��Ƿ������ק
					endPoint = new Point(chatFrame.getLocation().x + e.getX() - prePoint.x,
							chatFrame.getLocation().y + e.getY() - prePoint.y);
					X = endPoint.x; Y = endPoint.y;
					chatFrame.setLocation(endPoint);
				}
			}
		});
	}


	public static void main(String []args){
		User user = new User();
		Friend friend = new Friend();
		new ChatFrame(user, friend, null);
	}
	

}
