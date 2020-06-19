package five;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 解析配置文件
 * @author asus
 *	游戏的基本属性
 *	端口号
 */
public class AppConfig {
	
	private Map<String,String> properties = new HashMap<String,String>();
	
	/**
	 * 构造时自动装配map
	 */
	public AppConfig() {
		try {
			initPro();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化配置文件
	 * @throws Exception
	 */
	private void initPro() throws Exception {
		InputStream in = ParseFile.class.getClassLoader().getResourceAsStream("resource/pro/application.properties");
		BufferedReader read = new BufferedReader(new InputStreamReader(in,"UTF-8"));
		String msg = null;
		while((msg = read.readLine()) != null) {
			String[] split = msg.split(":");
			String key = split[0].trim();
			String value = split[1].trim();
			properties.put(key, value);
		}
	}
	
	/**
	 * 通过方法获取配置文件中的指定属性
	 * @param key
	 * @return
	 */
	public String get(String key) {
		return properties.get(key);
	}
	
}
