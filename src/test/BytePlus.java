package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
/**
 * 用来传输图片
 * @author asus
 * 通过getData方法可获得文件的字节码数组
 */
public class BytePlus {
	private int length = 1024;
	private byte[] buf = new byte[length];
	private int newLen = 0;
	
	//返回文件数据
	public byte[] getData(File file) {
		try {
			formStreamToByte(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		byte[] data = new byte[newLen];
		System.out.println("最后发送数组容量大小："+newLen);
		System.arraycopy(buf, 0, data, 0, newLen);
		return data;
	}
	
	private void formStreamToByte(File file) throws Exception {
		FileInputStream fis = new FileInputStream(file);
		
		byte[] temp = new byte[1024];
		
		while(fis.read(temp) != -1) {
			append(temp);
		}
		fis.close();
	}
	
	//对数据进行追加
	private void append(byte[] msg) {
		arrayToBig(msg);
		System.out.println("数组长度"+buf.length+",型长度"+newLen+",msglength:"+msg.length);
		System.arraycopy(msg, 0, buf, newLen, msg.length);
		newLen += msg.length;
	}
	
	//
	private int getZeroIndex(byte[] temp) {
		if(temp.length == 0)return 0;
		int index = temp.length/2;
		int min = 0;
		int max = temp.length-1;
		while(true) {
			if(index - 1 >= 0 && temp[index] == 0 && temp[index-1] != 0) {
				break;
			}
			if(temp[index] == 0) {
				max = index;
				if(index == 0) break; 
				index = max / 2;
			}else {
				if(index == max) return index+1;
				index += (max-min)/2;
				min = index;
			}
		}
		return index;
	}
	
	private void arrayToBig(byte[] msg) {
		int oldIndex = getZeroIndex(buf);
		int newIndex = getZeroIndex(msg);
		System.out.println("old:"+oldIndex+",new:"+newIndex);
		while((oldIndex + newIndex) > (length - 1)) {
			buf = Arrays.copyOf(buf, length += length/2);
			System.out.println("扩容一次，大小为"+length);
		}
	}
	
	public static void main(String[] args) {
		
	}
	
	public void send (OutputStream out, File file) {
		new Thread() {
			@Override
			public void run() {
				try {
					FileInputStream fis = new FileInputStream(file);
					byte[] msg = new byte[1024];
					while(fis.read(msg) != -1) {
						System.out.println("go");
						out.write(msg);
					}
					out.close();
					fis.close();
					System.out.println("发送完成");
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}.start();

	}
	
	public void receive (InputStream in, File file) {
		new Thread() {
			@Override
			public void run() {
				try {
					FileOutputStream fos = new FileOutputStream(file);
					byte[] buf = new byte[1024];
					int len = 0;
					while((len = in.read(buf)) != -1) {
						System.out.println("come");
						fos.write(buf,0,len);
					}
					fos.close();
					in.close();
					System.out.println("接受成功");
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}.start();

	}
	
	
	
}
