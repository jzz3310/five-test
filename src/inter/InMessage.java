package inter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class InMessage extends Thread{
	private BufferedReader in = null;
	private Socket s = null;
	public String msg = null;
	
	@Override
	public void run() {
		while(true){
			try {
				msg = in.readLine();
				 
			} catch (IOException e) {
				continue;
			}
		}
	}
	
	public InMessage(Socket s){
		this.s = s;
		try {
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
