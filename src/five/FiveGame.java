package five;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.TransferHandler;
import javax.swing.filechooser.FileSystemView;

import inter.Client;
import inter.InterChat;
import inter.Servlet;
import test.BytePlus;

public class FiveGame  extends JFrame implements MouseListener,Runnable,ActionListener{
	
	private Thread t = new Thread(this);//倒计时的线程类
	private GameStatus gs;//游戏基本功能
	private Chess chess;//下棋功能
	private InterChat chat;//聊天功能
	private Servlet servlet;//服务端
	private Client client;//客户端
	private UndoX ux;//悔棋功能
	private JButton jButton1 = new JButton("开始游戏");//开始游戏按钮 
	private JButton jButton2 = new JButton("游戏设置");//游戏设置按钮 
	private JButton jButton3 = new JButton("游戏说明");//游戏说明按钮 
	private JButton jButton4 = new JButton("认输");//认输按钮 
	private JButton jButton5 = new JButton("关于");//关于按钮 
	private JButton jButton6 = new JButton("退出");//退出按钮 
	
	public FiveGame(){
		gs = new GameStatus();
		chess = new Chess();
		chat = InterChat.getInstance();
		ux = new UndoX();
		//对窗体进行简单的设置  标题  尺寸  关闭方式  是否可见  是否可以随意改变大小  添加鼠标监听器
		this.setTitle("五子棋                                                 你的ip："+gs.getIP());
		this.setBounds((gs.width-700)/2, (gs.height-800)/2, 700, 800);
		this.setResizable(false);
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		chat.addToModel(this);
//		this.setJButtons();
		this.addMouseListener(this);
//		t.start();//线程启动
//		t.suspend();//线程挂起
	}
	
	@Override
	public void paint(Graphics g) {
		//双缓冲技术
		BufferedImage bi = new BufferedImage(700, 800, BufferedImage.TYPE_INT_ARGB);
		Graphics2D cg = bi.createGraphics();
		cg.drawImage(gs.bgImage, 0, 0, this);
		cg.setFont(new Font("黑体", Font.BOLD, 20));
		cg.setColor(Color.WHITE);
		cg.drawString("游戏信息：", 300, 70);
		cg.drawString(gs.message, 400, 70);
		cg.drawString("我是"+gs.myColor, 565, 370);
		cg.setFont(new Font("宋体", Font.BOLD, 18));
		cg.setColor(Color.BLACK);
		cg.drawString("黑方时间：", 35, 665);
		cg.drawString(gs.blackMessage, 130, 665);
		cg.drawString("白方时间：", 350, 665);
		cg.drawString(gs.whiteMessage, 445, 665);
		//绘制音量条
		cg.setStroke(new BasicStroke(5.0f));
		cg.setColor(Color.WHITE);
		cg.drawLine(676, 262, 676, 399);
		
		cg.setStroke(new BasicStroke(1.0f));
		cg.setColor(Color.BLACK);
		//绘制棋盘
		for(int i = 0; i < 20; i ++){
			cg.drawLine(10, 100+25*i, 510, 100+25*i);
			cg.drawLine(35+25*i, 100, 35+25*i, 600);
		}
		//中：260  350  135  225；  135   175 ；385   475； 385   225
		cg.fillOval(258, 348, 5, 5);
		cg.fillOval(133, 222, 5, 5);
		cg.fillOval(132, 473, 5, 5);
		cg.fillOval(383, 473, 5, 5);
		cg.fillOval(383, 222, 5, 5);
		//聊天信息
		cg.drawLine(400, 705, 400, 800);
		cg.drawLine(173, 716, 371, 716);
		cg.drawLine(173, 716, 173, 744);
		cg.drawLine(173, 744, 371, 744);
		cg.drawLine(371, 716, 371, 744);
		
		for(int i = 0; i < 21; i ++){
			for(int j = 0; j < 21; j ++){
				if(chess.allChess[i][j] == 1){
					cg.fillOval(25*i, 25*j+90, 20, 20);					
				}else if(chess.allChess[i][j] == 2){
					cg.setColor(Color.WHITE);
					cg.fillOval(25*i, 25*j+90, 20, 20);
					cg.setColor(Color.BLACK);
					cg.drawOval(25*i, 25*j+90, 20, 20);
				}
			}
		}
		
		g.drawImage(bi, 0,0,this);
		
		//动态更新音量条
		g.setColor(Color.blue);
		System.out.println("声音等级："+gs.volumeLine);
		g.drawLine(676, (399-34*(gs.volumeLine-1)), 676, 399);
		g.fillRect(674, 402-35*(gs.volumeLine-1), 5, 35*(gs.volumeLine-1));
		
		if(gs.volumeLine == 1) {
			//当声音等级最低时，加上静音标记
			g.setColor(Color.WHITE);
			g.drawLine(663, 196,686, 223);
			g.drawLine(663, 223,686, 196);
		}
		chat.refresh();
		
	}
	
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//获取鼠标点击的横坐标和纵坐标
		int X = e.getX();
		int Y = e.getY();
		System.out.println(X+"===="+Y);
		//当坐标是落在棋盘中，进行以下判断
		//没有开始游戏，并且点击到了棋盘
		if(!gs.isGameStart && X >= 10 && X <= 510 && Y >= 100 && Y <= 600){
			if(servlet == null && client == null){
				int flag = JOptionPane.showConfirmDialog(this, "确认开启五子棋房间吗？");
				if(flag == 0){
					servlet = Servlet.getInstance(this);
					servlet.start();
				}
			}else{
				JOptionPane.showMessageDialog(this, "已创建房间或已加入房间\n          请点击开始游戏");
			}
		}else if(gs.isGameStart && X >= 10 && X <= 510 && Y >= 100 && Y <= 600){
			//游戏开始，点击到棋盘
			//对坐标进行判断，判断存入那个二位数组中
			if(!(X < 23) ){
				chess.x = (X-20)/25+1;
			}else{
				chess.x = 0;
			}
			if(!(Y < 113)){
				chess.y = (Y-110)/25+1;
			}else{
				chess.y = 0;
			}
			//判断落子的地方是否已经有棋子，没有棋子才能落子
			System.out.println(gs.myColor+"-"+gs.isBlackGo+"-"+gs.isGameStart);
			if(this.gs.myColor.equals("黑方") && this.gs.isBlackGo || this.gs.myColor.equals("白方") && !this.gs.isBlackGo){
				if(chess.canDown(gs)){
					//播放棋子落下时的音乐
					new AudioPlayer().player(gs.volume);
					this.ux.setZeroChess(false);
					this.ux.setContinueUx(false);
					if(client == null){
						servlet.sendClient("go,"+chess.x+","+chess.y);
					}else if(servlet == null){
						client.sendSever("go,"+chess.x+","+chess.y);
					}
					this.repaint();
					//调用方法判断当前游戏是否已经结束
					if(chess.checkWin()){
						//结束时，游戏将不能继续
						gs.isGameStart=false;
						ux.setZeroChess(true);
						//游戏胜利，告诉另一方
						if(client == null){
							servlet.sendClient("end,"+gs.myColor);
						}else if(servlet == null){
							client.sendSever("end,"+gs.myColor);
						}
						//提示玩家游戏结束，一方获得胜利
						JOptionPane.showMessageDialog(this, "游戏结束，"+gs.myColor+"胜利");
						gs.isOne = false;
//						if(!gs.isGameStart){
//							int f = JOptionPane.showConfirmDialog(this, "是否重新开始游戏");
//							if(f == 0){
//								this.gs.initGame(gs.myColor.equals("白方")?"黑方":"白方");
//								this.chess.initChess();
//								if(client == null){
//									servlet.sendClient("start,"+gs.myColor+",0,"+gs.maxTime/60);
//								}else if(servlet == null){
//									client.sendSever("start,,"+gs.myColor+",0,"+gs.maxTime/60);
//								}
//								this.repaint();
//							}
//						}
					}
				}else{
					JOptionPane.showMessageDialog(this, "当前位置已经有棋子，请重新落子");
				}
			}
		}else{
			//判断是哪个按钮
			isButton(X,Y);
		}
	}
    
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	//判断当前点击的是不是按钮，以及点击按钮之后的操作
	public void isButton(int X,int Y){
		Integer flag = null;
		Integer reStart = 0;
		if(X >= 555 && X <= 655 && Y >= 120 && Y <= 160){//开始游戏
			//开始游戏按钮
			this.startButton(flag, reStart);
			
		}else if(X >= 550 && X <= 655 && Y >= 195 && Y <= 245){//游戏设置
			//游戏设置按钮
			this.gameSettings();
			
		}else if(X >= 555 && X <= 660 && Y >= 275 && Y <= 325){//游戏说明
			//游戏说明按钮
			this.gamesHows();
			
		}else if(X >= 565 && X <= 650 && Y >= 350 && Y <= 375){//交换执子
			//交换执子权按钮
			this.exChange();
			
		}else if(X >= 555 && X <= 660 && Y >= 395 && Y <= 440){//认输
			//认输按钮
			this.giveUp(flag);
			
		}else if(X >= 550 && X <= 660 && Y >= 470 && Y <= 515){//关于 
			//关于按钮
			this.aboutUs();
			
		}else if(X >= 550 && X <= 660 && Y >= 555 && Y <= 600){//退出
			//退出按钮
			this.quitGame();
			
		}else if(X >= 55 && X <= 220 && Y >= 30 && Y <= 75){//关闭房间，退出房间
			//游戏左上方的WEGAME标识，是关闭或退出房间的按钮
			this.closeRoom();
			
		}else if(X >= 664 && X <= 688 && Y >= 234 && Y <= 257) {//增加音量
			//增加音量按钮
			this.upVolume();
			
		}else if(X >= 665 && X <= 689 && Y >= 403 && Y <= 427) {//降低音量
			//降低音量按钮
			this.downVolume();
			
		}else if(X >= 210 && X <= 330 && Y >= 760 && Y <= 790) {//发送消息
			//发送消息按钮
			if(servlet != null || client != null)this.sendMsg();

		}else if(X >= 50 && X <= 155 && Y >= 715 && Y <= 745) {//悔棋功能
			//悔棋按钮
			if(servlet != null || client != null)if(gs.isGameStart && !ux.getZeroChess() && !ux.getContinueUx())this.undo();
			
		}
	}
	
	public void startButton(Integer flag ,Integer reStart) {
		if(client == null && servlet == null){
			String ip = JOptionPane.showInputDialog(this, "请输入房主的IP地址：");
			if(ip != null && ip.trim().length() != 0 ){
				try {
					client = Client.getInstance(ip,this);
					if(client.s != null){
						//当联机之后，添加一个监听器，监听窗口是否关闭
						this.addWindowListener(new WindowAdapter() {
							public void windowClosing(WindowEvent e) {
							try {
								client.sendSever("quit,");//向对方发送离开信息
								client.s.close();
								client.out.close();
								client.in.close();
								chat.setNoLine();
								System.exit(0);
							} catch (Exception ex) {
							}
							}
						});
						this.gs.myColor = "白方";
						this.gs.isOnline = true;
						client.start();
						client.sendSever("hint,joinRoom,已有人加入你的房间");
						chat.setOnline();
						this.repaint();
					}else {
						Client.setNull();
						client = null;
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}					
			}
		}else{
			if(!gs.isGameStart){
				flag = JOptionPane.showConfirmDialog(this, "是否开始游戏？");
				if(!gs.isOne) {
					reStart = 1;
				}
			}else{
				flag = JOptionPane.showConfirmDialog(this, "是否重新开始游戏？");
				reStart = 1;
			}
		}
		if(flag != null && flag == 0){
			String youColor = gs.myColor;
			System.out.println(gs.isOnline+"联机");
			if(gs.isOnline){
				if(reStart == 0) {
					if(gs.myColor.equals("黑方")){
						youColor = "白方";
					}else{
						youColor = "黑方";
					}
				}
				if(servlet == null){
					client.sendSever("start,"+youColor+","+reStart+","+gs.maxTime/60);
				}else{
					servlet.sendClient("start,"+youColor+","+reStart+","+gs.maxTime/60);
				}
				this.repaint();					
			}else {
				JOptionPane.showMessageDialog(this, "还没有人加入你的房间，不能开始游戏");
			}
		}
	}
	
	public void gameSettings() {
		if(gs.isGameStart){
			JOptionPane.showMessageDialog(this, "游戏已经开始，不能设置时间，请在游戏开始之前或结束之后设置时间");
			return;
		}else {
			if(servlet == null && client == null) {
				JOptionPane.showMessageDialog(this, "请先加入游戏，再设置时间限制");
				return;
			}else if(client == null) {
				if(!this.gs.isOnline) {
					JOptionPane.showMessageDialog(this, "暂时无人加入您的房间，请等对方加入之后设置");
					return;
				}
			}
		}
		
		String time = JOptionPane.showInputDialog("请输入游戏时间限制(分钟):\n最小时间限制为2分钟\n初始默认无时间限制\n输入0表示没有时间限制");
		do{
			try {
				if(time == null){
					break;
				}else if(Integer.parseInt(time) < 2 && Integer.parseInt(time) != 0){
					JOptionPane.showMessageDialog(this, "！您输入的分钟数太小了 ！");
					time = JOptionPane.showInputDialog("请输入游戏时间限制(分钟):\n最小时间限制为2分钟");
				}else{
					if(servlet == null){
						client.sendSever("settime,"+time);
					}else if(client == null){
						servlet.sendClient("settime,"+time);
					}
//					JOptionPane.showMessageDialog(this, "设置成功，时间限制为"+Integer.parseInt(time)+"分钟");
//					gs.setTimes(time);
//					t.resume();//线程再次启动
					break;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "！请输入正确的分钟数 ！");
				time = JOptionPane.showInputDialog("请输入游戏时间限制(分钟):\n最小时间限制为2分钟");
			}
		}while(true);
		this.repaint();
	}
	
	public void gamesHows() {
		JOptionPane.showMessageDialog(this, gs.Text.get("shows"));
	}
	
	public void giveUp(Integer flag) {
		if(!gs.isGameStart){
			JOptionPane.showMessageDialog(this, "游戏还没开始，怎么能认输呢？");
			return;
		}
		flag = JOptionPane.showConfirmDialog(this, "是否认输？");
		if(flag == 0){
			String color = "黑方";
			if(gs.myColor.equals(color)){
				color = "白方";
			}
			if(client == null){
				servlet.sendClient("end,"+gs.myColor+"认输");
			}else if(servlet == null){
				client.sendSever("end,"+gs.myColor+"认输");
			}
			gs.isGameStart=false;
			gs.isOne=false;
			JOptionPane.showMessageDialog(this, "游戏结束，"+color+"胜利");
			
		}
	}
	
	public void aboutUs() {
		JOptionPane.showMessageDialog(this,gs.Text.get("about"));
	}
	
	public void quitGame() {
		int quit = JOptionPane.showConfirmDialog(this, "确认退出游戏吗？");
		if(quit == 0){
			if(client != null) {
				client.sendSever("quit,");
				try {
					client.s.close();
					client.out.close();
					client.in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else if(servlet != null) {
				servlet.sendClient("quit,");
				try {
					servlet.ss.close();
					servlet.pw.close();
					servlet.in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			System.gc();
			System.exit(0);
		}
	}
	
	public void closeRoom() {
		if(servlet != null){
			int sign = JOptionPane.showConfirmDialog(this, "确认关闭此房间？");
			if(sign == 0){
				if(gs.isOnline) {
					gs.isOnline = false;
					gs.isGameStart=false;
					gs.isOne=true;
					servlet.sendClient("hint,quitRoom,房主关闭此房间");
				}
				try {
					//关闭套接字
					servlet.ss.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Servlet.setNull();
				servlet = null;
				
			}
		}
		if(client != null){
			int sign = JOptionPane.showConfirmDialog(this, "确认退出此房间？");
			if(sign == 0){
				gs.isOnline = false;
				gs.isGameStart=false;
				gs.isOne=true;
				client.sendSever("hint,quitRoom,对方退出此房间");
				try {
					client.s.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Client.setNull();
				client = null;
			}
		}
		System.gc();
	}
	
	public void exChange() {
		if(!gs.isGameStart && gs.isOnline){
			int sign = JOptionPane.showConfirmDialog(this, "是否请求与对方交换执子权？");
			if(sign == 0){
				if(servlet == null){
					client.sendSever("change,"+gs.myColor);
				}else if(client == null){
					servlet.sendClient("change,"+gs.myColor);
				}
			}
		}
	}
	
	public void upVolume() {
		this.gs.setVoolume(true);
		this.repaint();
	}
	
	public void downVolume() {
		this.gs.setVoolume(false);
		this.repaint();
	}
	
	public void sendMsg() {
		if(chat.getText() == null || chat.getText().equals(""))return;
		if(servlet == null) {
			client.sendSever(chat.msg+","+chat.getText());
		}else{
			servlet.sendClient(chat.msg+","+chat.getText());
		}
		chat.sendToText();
	}
	
	public void undo() {
		//先判断是否是自己下棋，之后自己下过，才能有悔棋功能
		if(this.gs.myColor.equals("黑方") && !this.gs.isBlackGo || this.gs.myColor.equals("白方") && this.gs.isBlackGo) {
			if(ux.spiteDisturb()) {
				if(ux.nowCount > 0){
					if(servlet == null) {
						System.out.println(chess.x+"===="+chess.y);
						client.sendSever("undo,");
					}else{
						System.out.println(chess.x+"===="+chess.y);
						servlet.sendClient("undo,");
					}
				}else{
					JOptionPane.showMessageDialog(this, "悔棋次数已用完");
				}
			}else {
				JOptionPane.showMessageDialog(this, "请求太频繁，请等待");							
			}
		}
		
	}
	
	@Override
	public void run() {
		if(gs.maxTime >= 120){
			while(true){
				if(gs.isBlackGo && gs.isGameStart){
					gs.blackTime--;
					gs.blackMessage = gs.blackTime/3600+":"+gs.blackTime%3600/60+":"+gs.blackTime%60;
					if(gs.blackTime == 0){
						JOptionPane.showMessageDialog(this, "黑方超时，游戏结束，白方胜利");
						gs.isGameStart =false;
						gs.isOne = false;
					}
				}else if(gs.isGameStart){
					gs.whiteTime--;
					gs.whiteMessage = gs.whiteTime/3600+":"+gs.whiteTime%3600/60+":"+gs.whiteTime%60;
					if(gs.whiteTime == 0){
						JOptionPane.showMessageDialog(this, "白方超时，游戏结束，黑方胜利");
						gs.isGameStart =false;
						gs.isOne = false;
					}
				}
				this.repaint();
				if(!gs.isGameStart) {
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	//处理协议字符串
	public void disPostMsg(String msg){
		if(!gs.isOnline && msg != null)gs.isOnline=true;
		System.out.println("协议字符串："+msg);
		String[] split = msg.split(",");
		if(split[0].equals("hint")){//一方有操作，给另一方提示
			if(split[1].equals("joinRoom")) {
				//当有人加入房间后，添加监听器，监听窗口是否关闭
				this.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						try {
							servlet.sendClient("quit,");//向对方发送离开信息
							servlet.ss.close();
							servlet.pw.close();
							servlet.in.close();
							System.exit(0);
						} catch (Exception ex) {
						}
					}
				});
				chat.setOnline();
				JOptionPane.showMessageDialog(this, HintMsg.get(isServlet(), split[1], null, null));
			}else if(split[1].equals("startGame")){
				if(split.length == 3){
					chess.initChess();
					gs.initGame(split[2]);
					ux.initUndox();
					this.repaint();
					t = new Thread(this);
					t.start();
				}
				this.gs.isBlackGo = true;
				JOptionPane.showMessageDialog(this, HintMsg.get(isServlet(), split[1], null, null));
			}else if(split[1].equals("settime")){
				if(split[2].equals("yes")){
					this.gs.setTimes(split[3]);
//					t.resume();//线程再次启动
					this.repaint();
				}
				JOptionPane.showMessageDialog(this, HintMsg.get(isServlet(), split[1], split[2], Arrays.copyOfRange(split, 2, split.length)));
			}else if(split[1].equals("change")){
				if(split[2].equals("yes")) {
					if(gs.myColor.equals("黑方")){
						gs.myColor = "白方";
					}else{
						gs.myColor = "黑方";					
					}					
				}
				JOptionPane.showMessageDialog(this, HintMsg.get(isServlet(),split[1],split[2],null));
				this.repaint();
			}else if(split[1].equals("quitRoom")){
				JOptionPane.showMessageDialog(this, HintMsg.get(isServlet(),split[1],null,null));
				if(servlet == null){
					try {
						client.s.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Client.setNull();
					client = null;
					gs.isOnline = false;
					gs.isGameStart=false;
					gs.isOne=true;
				}else if(client == null){
					try {
						servlet.ss.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Servlet.setNull();
					servlet = Servlet.getInstance(this);
					servlet.start();
					gs.isOnline=false;
					gs.isGameStart=false;
					gs.isOne=true;
				}
			}else if(split[1].equals("undo")) {
				//悔棋实现
				if(split[2].equals("yes")) {
					ux.setContinueUx(true);
					chess.makeUndo();
					gs.makeUndo();
					ux.nowCount--;
					this.repaint();
				}
				
				JOptionPane.showMessageDialog(this, HintMsg.get(isServlet(),split[1],split[2],null));
			}else if(split[1].equals("msg")) {
				JOptionPane.showMessageDialog(this, HintMsg.get(isServlet(), "msg", null, split[2]));
			} else if(split[1].equals("sendFile")) {
				if(split[2].equals("yes")) {
					try {
						System.out.println("文件为"+chat.file+",是否存在："+chat.file.exists()+","+chat.msg);
						if(servlet == null) {
							new BytePlus().send(client.s.getOutputStream(), chat.file);
						}else {
							new BytePlus().send(servlet.s.getOutputStream(), chat.file);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
		} else if(split[0].equals("start")){//游戏开始
			int flag = JOptionPane.showConfirmDialog(this, HintMsg.getLink(isServlet(), split[0], Arrays.copyOfRange(split, 2, split.length)));
			if(flag == 0){
				String youColor = null;
				gs.myColor=split[1];
				if(split[1].equals("黑方")){
					youColor = "白方";
				}else{
					youColor = "黑方";					
				}
				chess.initChess();
				ux.initUndox();
				System.out.println("我的颜色:"+gs.myColor);
				gs.initGame(gs.myColor);
				gs.isGameStart = true;
				if(client == null){
					servlet.sendClient("hint,startGame,"+youColor);
				}else if(servlet == null){
					client.sendSever("hint,startGame,"+youColor);
				}
				if(!split[3].equals("0")){
					gs.setTimes(split[3]);
					t = new Thread(this);
					t.start();
				}
				this.repaint();
			}else{
				if(client == null){
					servlet.sendClient("hint,msg,房主取消开始游戏");
				}else if(servlet == null){
					client.sendSever("hint,msg,对方取消开始游戏");
				}
			}
		} else if(split[0].equals("go")){//当有一方下棋之后
			this.chess.x = Integer.parseInt(split[1]);
			this.chess.y = Integer.parseInt(split[2]);
			this.chess.canDown(gs);
			ux.setContinueUx(false);
			this.repaint();
		} else if(split[0].equals("end")){
			this.gs.isGameStart=false;
			this.gs.isOne = false;
			JOptionPane.showMessageDialog(this, HintMsg.getLink(isServlet(), split[0], split[1]));
		} else if(split[0].equals("settime")){
			
			int flag = JOptionPane.showConfirmDialog(this, HintMsg.getLink(isServlet(), split[0], split[1]));
			if(flag == 0){
				if(servlet == null){
					client.sendSever("hint,settime,yes,"+split[1]);
				}else if(client == null){
					servlet.sendClient("hint,settime,yes,"+split[1]);
				}
				this.gs.setTimes(split[1]);
//				if(!time.equals("0")) {
//					t.resume();//线程再次启动					
//				}
				this.repaint();
			}else{
				if(servlet == null){
					client.sendSever("hint,settime,no");
				}else if(client == null){
					servlet.sendClient("hint,settime,no");
				}
			}
		} else if(split[0].equals("change")){
			int sign = JOptionPane.showConfirmDialog(this, HintMsg.getLink(isServlet(), split[0], null));
			if(sign == 0){
				if(servlet == null){
					client.sendSever("hint,change,yes");
				}else if(client == null){
					servlet.sendClient("hint,change,yes");
				}
				gs.myColor = split[1];
				this.repaint();
			}else{
				if(servlet == null){
					client.sendSever("hint,change,no");
				}else if(client == null){
					servlet.sendClient("hint,change,no");
				}
			}
		} else if(split[0].equals("quit")) {
			JOptionPane.showMessageDialog(this, HintMsg.getLink(isServlet(), split[0], null));
			chat.setNoLine();
			if(client == null) {
				this.gs.isGameStart = false;
				this.chess = new Chess();
				this.servlet.interrupt();
				Servlet.setNull();
				servlet = null;
			}else if(servlet == null) {
				this.gs.isGameStart = false;
				this.chess = new Chess();
				this.client.interrupt();
				Client.setNull();
				client = null;
			}
		} else if(split[0].equals("sendMsg")) {
			String text = split[1];
			chat.acceptToText(text);
		} else if(split[0].equals("sendFile")) {
			String filepath = split[1];
			int flag = JOptionPane.showConfirmDialog(this, HintMsg.getLink(isServlet(), split[0], filepath.substring(filepath.lastIndexOf("."))));
			if(flag == 0) {
				try {
					File file = createFile(filepath);
					if(servlet == null) {
						client.sendSever("hint,sendFile,yes");
						new BytePlus().receive(client.s.getInputStream(), file);
					}else {
						servlet.sendClient("hint,sendFile,yes");
						new BytePlus().receive(servlet.s.getInputStream(), file);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else {
				
			}
			
			
		} else if(split[0].equals("undo")) {
			int flag = JOptionPane.showConfirmDialog(this, HintMsg.getLink(isServlet(), split[0], null));
			if(flag == 0) {
				if(servlet == null) {
					client.sendSever("hint,undo,yes");
				}else {
					servlet.sendClient("hint,undo,yes");
				}
				chess.makeUndo();
				gs.makeUndo();
				ux.setContinueUx(true);
				this.repaint();
			}else {
				if(servlet == null) {
					client.sendSever("hint,undo,no");
				}else {
					servlet.sendClient("hint,undo,no");
				}
			}
		}
		
		
	}
	
//	public static void main(String[] args) {
//		Boolean flag = true;
//		Boolean w = null != null? flag : (null != null? false : null);
//		Boolean a = null != null? true : null;
//		System.out.println(w);
//	}
	
	//完成创建文件  默认在桌面
	private File createFile(String fileName) throws IOException {
		FileSystemView fsv = FileSystemView.getFileSystemView();
		String desk = fsv.getHomeDirectory().getPath();   //获取本机桌面路径
		File file = new File(desk+"\\"+fileName);
		int i = 1;
		while(true) {
			if(!file.exists()) {
				file.createNewFile();
				break;
			}
			String newFileName = fileName.replace(".", "("+i+").");
			file = new File(desk+"\\"+newFileName);
			i++;
		}
		return file;
	}

	public Boolean isServlet() {
		Boolean flag = true;
		return servlet != null? flag : (client != null? false : null) ;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==jButton1) {
			
		}else if(e.getSource() == jButton2) {
			
		}else if(e.getSource() == jButton3) {
			
		}else if(e.getSource() == jButton4) {
			
		}else if(e.getSource() == jButton5) {
			
		}else if(e.getSource() == jButton6) {
			
		}
		
	}
	
	private void setJButtons() {
		//去掉字体和边框的距离
		
		 jButton1.setFocusPainted(false); jButton1.setBounds(550,95,100,40);
		 jButton1.setFont(new Font("Dialog", 0, 14));
		 jButton1.addActionListener(this);
		 
		 jButton2.setFocusPainted(false); jButton2.setBounds(553,176,94,40);
		 jButton2.setFont(new Font("Dialog", 0, 14));
		 jButton2.addActionListener(this);
		 
		 jButton3.setFocusPainted(false); jButton3.setBounds(555,256,97,40);
		 jButton3.setFont(new Font("Dialog", 0, 14));
		 jButton3.addActionListener(this);
		 
		 jButton4.setFocusPainted(false); jButton4.setBounds(554,374,98,37);
		 jButton4.setFont(new Font("Dialog", 0, 14));
		 jButton4.addActionListener(this);
		 
		 jButton5.setFocusPainted(false); jButton5.setBounds(555,448,96,38);
		 jButton5.setFont(new Font("Dialog", 0, 14));
		 jButton5.addActionListener(this);
		 
		 jButton6.setFocusPainted(false); jButton6.setBounds(554,530,100,40);
		 jButton6.setFont(new Font("Dialog", 0, 14));
		 jButton6.addActionListener(this);
		 
		 this.add(jButton1);
		 this.add(jButton2);
		 this.add(jButton3);
		 this.add(jButton4);
		 this.add(jButton5);
		 this.add(jButton6);
		
	}

}
