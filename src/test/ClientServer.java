package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientServer {

	public static void main(String[] args) throws Exception {
		Socket s = new Socket("169.254.50.3", 9000);
		
		System.out.println("client:开始发送图片");
		OutputStream out = s.getOutputStream();
		File file = new File("C:\\Users\\asus\\Desktop\\vue-cli.docx");
		
		BytePlus bp = new BytePlus();
		bp.send(out, file);
		
//		FileInputStream fis = new FileInputStream(file);
//		byte[] msg = new byte[1024];
//		while(fis.read(msg) != -1) {
//			System.out.println("go");
//			out.write(msg);
//		}
//		out.close();
//		fis.close();
//		System.out.println("发送完成");
//		
//		System.out.println("client:发送图片结束");

		
	}
}
