package inter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

import five.AppConfig;
import five.FiveGame;
/**
 * 服务端
 * @author asus
 *
 */
public class Servlet extends Thread{
	private AppConfig ac = new AppConfig();  //程序配置文件
	private static Servlet servlet;  //服务端单例对象
	public Socket s = null;  //连接的客户端的套接字
	public String msg;  //用来发送消息
	public FiveGame fg;  //获取页面对象
	public ServerSocket ss;  //服务端套接字
	public PrintWriter pw = null;  //输出流
	public BufferedReader in = null;  //缓冲输入流
	
	
	/**
	 * 发送消息给客户端
	 * @param msg
	 */
	public void sendClient(String msg){
		
		try {
			pw = new PrintWriter(s.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pw.println(msg);
		pw.flush();
	}
	
	/**
	 * 线程用来接收消息
	 */
	@Override
	public void run() {
		try {
			int port = Integer.parseInt(ac.get("port"));
			ss = new ServerSocket(port, 1, InetAddress.getLocalHost());
			System.out.println("等待链接");
			System.out.println(ss.getInetAddress().toString());
			try {
				s = ss.accept();
			} catch (Exception e1) {
				return;
			}
			System.out.println("联机成功");
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			while(true){
				try {
					msg = in.readLine();
					fg.disPostMsg(msg);
				} catch (Exception e) {
					break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Servlet(FiveGame fg){
		this.fg = fg;
	}
	
	
	/**
	 * 获取单例对象
	 * 如果存在返回已存在对象，不存在创建对象
	 * @param fg
	 * @return
	 */
	public static Servlet getInstance(FiveGame fg) {
		if(servlet == null) {
			servlet = new Servlet(fg);
		}
		return servlet;
	}
	
	
	/**
	 * 清空单例对象
	 */
	public static void setNull() {
		servlet = null;
	}
	
	//得到单例对象
	/**
	 * 直接得到服务端对象，无论有值无值
	 * @return
	 */
	public static Servlet getServlet() {
		return servlet;
	}
	
	
	/**
	 * 判断单例对象是否存在
	 * @return
	 */
	public static Boolean isExist() {
		return servlet != null;
	}
	
}
