package test;

import java.util.Arrays;
import java.util.Random;
import java.util.Vector;

public class Type extends Thread{
	public static int money = 100;
	public static int count = 10;
	public static void main(String[] args) {
		int[] arr = new int[]{34,8,64,51,32,21};
		for(int i = 1; i < arr.length; i ++){
			int temp = arr[i];
			for(int j = i; j > 0 && arr[j] < arr[j-1]; j --){
				arr[j] = arr[j-1];
				arr[j-1] = temp;
			}
		}
		System.out.println(Arrays.toString(arr));
		System.out.println(10.0/-3);
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
