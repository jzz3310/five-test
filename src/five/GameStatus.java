package five;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import inter.AddressIp;

public class GameStatus {
	public int width = Toolkit.getDefaultToolkit().getScreenSize().width;//获取屏幕的长度
	public int height = Toolkit.getDefaultToolkit().getScreenSize().height;//获取屏幕的高度
	public BufferedImage bgImage = null;//双缓冲技术
	public String message = "黑方先行";//提示信息
	public Boolean isBlackGo = true;//判断是黑棋走还是白旗走
	public Boolean isGameStart = false;//判断游戏是否开始
	public int maxTime = 0;//定义最多拥有多少时间
	public int blackTime = 0;//黑方剩余时间
	public int whiteTime = 0;//白方剩余时间
	public String blackMessage = "无限制";//显示黑方时间信息
	public String whiteMessage = "无限制";//显示白方时间信息
	public String myColor = "黑方";//定义我的颜色
	public Boolean isOne = true;//判断是不是第一局
	public Boolean isOnline = false;//判断是否是已经联机
	public float volume = -20.0f;//定义音量  5个等级
	public int volumeLine = 3;//初始等级为3
	public Map<String,String> Text = new HashMap<>();//存储说明文件内容
	
	/**
	 * 初始化游戏状态对象
	 * 对文本初始化
	 * 对图片初始化
	 */
	public GameStatus(){
		initText(new ParseFile().map);
		InputStream input = getClass().getClassLoader().getResourceAsStream("resource/images/five.jpg");
		try {
			bgImage = ImageIO.read(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化游戏状态
	 * 何时初始化：当开始游戏，或重新开始游戏才会初始化
	 * @param myColor
	 */
	public void initGame(String myColor){
		isGameStart=true;
		isBlackGo=true;
		message = "黑方先行";
		blackTime = maxTime;
		whiteTime = maxTime;
		setTimes(maxTime/60+"");
		this.myColor = myColor;
	}
	
	/**
	 * 关于时间的设置
	 * 输入一个String类型的参数，会自动给游戏时间赋值，并且会准备好双方的时间字符串
	 * @param time
	 */
	public void setTimes(String time){
		if(time.equals("0")) {
			return;
		}
		maxTime = Integer.parseInt(time)*60;
		blackTime = maxTime;
		whiteTime = maxTime;
		blackMessage = maxTime/3600+":"+maxTime%3600/60+":"+maxTime%60;
		whiteMessage = maxTime/3600+":"+maxTime%3600/60+":"+maxTime%60;
	}
	
	/**
	 * 设置游戏的音量
	 * @param flag
	 */
	public void setVoolume(Boolean flag) {
		if(flag) {//增加音量
			if(volumeLine < 4) {
				if(volumeLine >= 1)this.volume += 20.0f;
			}else {
				if(volumeLine < 5)this.volume += 6.0f;
			}
			if(volumeLine < 5)volumeLine++;
		}else {//减音量
			if(volumeLine <= 4) {
				if(volumeLine > 1)this.volume -= 20.0f;
			}else {
				if(volumeLine <= 5)this.volume -= 6.0f;
			}
			if(volumeLine > 1)volumeLine--;
		}
	}
	
	/**
	 * 初始化提示文件文本
	 * @param map
	 */
	private void initText(Map<String,List<String>> map) {
		Set<String> keys = map.keySet();
		for (String key : keys) {
			List<String> list = map.get(key);
			String msg = "";
			for (String line : list) {
				msg += line+"\n";
			}
			Text.put(key, msg);
		}
	}
	
	/**
	 * 得到计算机的ip地址
	 * @return
	 */
	public String getIP() {
		String innetIp = null;
		try {
			innetIp = AddressIp.getInnetIp();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return innetIp;
	}
	
	/**
	 * 得到图片的ImageIcon形式
	 * @return
	 */
	public ImageIcon getImg() {
		ImageIcon img = new ImageIcon("resource/images/five.jpg");
		return img;
	}
	/**
	 * 悔棋时，游戏基本信息改变
	 */
	public void makeUndo() {
		this.isBlackGo = !this.isBlackGo;
	}
	
}
