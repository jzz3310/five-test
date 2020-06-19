package test;

import java.util.Random;

public class Type extends Thread{
	public static int money = 100;
	public static int count = 10;
	public static void main(String[] args) {
		for(int i = 0; i < 10; i ++) {
			Type t = new Type();
			t.setName("线程"+i);
			t.start();
		}

		
	}
	
	@Override
	public void run() {
		revice();
	}
	
	public void revice() {
		synchronized (this) {
			int price = new Random().nextInt(money);
			float f = new Random().nextFloat();
			
			
			if(count == 1); 
			count --;
			
		}
	}
	
}
