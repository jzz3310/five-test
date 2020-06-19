package five;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * 
 * @author asus
 *	用来管理提示信息
 */
public class HintMsg {
	
	//获取提示信息
	public static String get(Boolean isServlet, String label, String yesOrNo, Object...objs) {
		String result = "";
		String herRole = "房主";
		if(isServlet != null && isServlet) herRole = "对方";
		if(label.equals("joinRoom")) {
			result = "已有人加入你的房间";
			
		}else if(label.equals("startGame")) {
			result = "游戏开始";
			
		}else if(label.equals("settime")) {
			result = herRole+"不同意你的时间设置";
			if(yesOrNo.equals("yes"))result = herRole+"同意设置时间为"+objs[1]+"分钟";
			
		}else if(label.equals("change")) {
			result = herRole+"同意交换执子权";
			if(yesOrNo.equals("no"))result = herRole+"拒绝交换执子权，还是在技术上压倒TA吧";
			
		}else if(label.equals("quitRoom")) {
			if(isServlet) {
				result = herRole+"退出房间";
			}else {
				result = herRole+"关闭房间";
			}
		}else if(label.equals("undo")) {
			result = herRole+"同意悔棋";
			if(yesOrNo.equals("no")) result = herRole+"不同意悔棋";
			
		}else if(label.equals("msg")) {
			result = (String) objs[0];
			
		}
		
		return result;
	}
	
	//获取对话信息
	public static String getLink(Boolean isServlet, String label,Object...objs) {
		String result = "";
		String herRole = "房主";
		if(isServlet) herRole = "对方";
		if(label.equals("start")) {
			String hintMsg = "";
			if(objs[0].equals("1")) hintMsg = "重新";
			result = herRole+"请求"+hintMsg+"开始游戏，请确认";
		}else if(label.equals("end")) {
			result = "游戏结束，"+objs[0]+"胜利";
		}else if(label.equals("settime")) {
			String time = null;
			if(objs[0].equals("0")) {
				time = "无限制";
			}else {
				time = objs[0]+"分钟";
			}
			result = herRole+"请求设置时间为"+time+",请确认";
			
		}else if(label.equals("change")) {
			result = herRole+"请求交换执子权，你的意思呢？";
			
		}else if(label.equals("quit")) {
			result = herRole+"退出游戏";
		}else if(label.equals("undo")) {
			result = herRole+"想悔棋，是否同意？";
		}else if(label.equals("sendFile")) {
			result = herRole+"给你发送一个"+objs[0]+"文件，是否同意？";
		}
		
		return result;
		
	}
	
	private HintMsg() {}
	

}
