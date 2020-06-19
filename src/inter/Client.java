package inter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

import five.AppConfig;
import five.FiveGame;
/**
 * 客户端
 * @author asus
 *
 */
public class Client extends Thread{
	private AppConfig ac = new AppConfig(); //程序配置文件
	private static Client client;  //单例客户端
	public Socket s = null;  //客户端套接字
	public String msg;  //用来接受消息
	public FiveGame fg;  //页面对象
	public PrintWriter out = null; //输出流
	public BufferedReader in = null;  //缓冲输入流
	
	
	/**
	 * 用来发送消息
	 * 发送消息给服务端
	 * @param msg
	 */
	public void sendSever(String msg){
		try {
			out = new PrintWriter(s.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println(msg);
		out.flush();
	}
	
	/**
	 * 线程用来接受消息
	 */
	@Override
	public void run() {
		
		try {
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(true){
			try {
				msg = in.readLine();
				fg.disPostMsg(msg);
			} catch (Exception e) {
				break;
			}
		}
		
	}
	
	public Client(String ip,FiveGame fg){
		try {
			s=new Socket();
			int port = Integer.parseInt(ac.get("port"));
			int timeout = Integer.parseInt(ac.get("timeout"));
			s.connect(new InetSocketAddress(ip, port), timeout);
			this.fg = fg;
		} catch (Exception e) {
			s = null;
			fg.disPostMsg("hint,msg,没有此房主IP");
		}
	}
	
	
	/**
	 * 单例对象获取
	 * @param ip
	 * @param fg
	 * @return
	 */
	public static Client getInstance(String ip,FiveGame fg) {
		if(client == null) {
			client = new Client(ip,fg);
		}
		return client;
	}
	
	
	/**
	 * 获取client对象，建议和isExist配合使用
	 * @return
	 */
	public static Client getClient() {
		return client;
	}
	
	
	/**
	 * 判断单例对象是否存在
	 * @return
	 */
	public static Boolean isExist() {
		return client != null;
	}
	
	/**
	 * 单例对象赋值为null
	 * 清空单例对象
	 */
	public static void setNull() {
		client = null;
	}
	
}
