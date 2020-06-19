package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FileTest {
	public static void main(String[] args) throws IOException {
		//169.254.50.3
		ServerSocket ss = new ServerSocket(9000);
		
		Socket s = ss.accept();
		
		System.out.println("连接成功");
		File file = new File("C:\\Users\\asus\\Desktop\\vue-cli(1).docx");
		file.createNewFile();
		System.out.println("开始接受图片");
		InputStream in = s.getInputStream();
		FileOutputStream fos = new FileOutputStream(file);
		new BytePlus().receive(in, file);
		
//		FileOutputStream fos = new FileOutputStream(file);
//		byte[] buf = new byte[1024];
//		int len = 0;
//		while((len = in.read(buf)) != -1) {
//			System.out.println("come");
//			fos.write(buf,0,len);
//		}
//		fos.close();
//		in.close();
//		System.out.println("接受成功");


			
		
		
	}
}
