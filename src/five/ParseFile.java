package five;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;


//解析游戏说明文本
public class ParseFile {
	public Map<String,List<String>> map = new HashMap<>();

	public ParseFile() {
		try {
			initShows();
			initAbout();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//初始化游戏说明的文本
	private void initShows() throws IOException {
		ArrayList<String> list = new ArrayList<String>();
		InputStream in = ParseFile.class.getClassLoader().getResourceAsStream("resource/shows/shows.properties");
		BufferedReader read = new BufferedReader(new InputStreamReader(in,"UTF-8"));
		String msg = null;
		try {
			while((msg = read.readLine()) != null) {
				list.add(msg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		map.put("shows", list);
		in.close();
		read.close();
	}
	
	//初始化关于的文本
	private void initAbout() throws IOException {
		ArrayList<String> list = new ArrayList<String>();
		InputStream in = ParseFile.class.getClassLoader().getResourceAsStream("resource/shows/about.properties");
		BufferedReader read = new BufferedReader(new InputStreamReader(in,"UTF-8"));
		String msg = null;
		try {
			while((msg = read.readLine()) != null) {
				list.add(msg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		map.put("about", list);
		in.close();
		read.close();
	}
	
}
