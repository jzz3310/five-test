package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Car {
	private int id;
	private String name;
	private String color;
	
	private static volatile Car car = null;

	private Car(int id, String name, String color) {
		super();
		this.id = id;
		this.name = name;
		this.color = color;
	}
	
	public static Car getInstance() {
		synchronized(Car.class) {
            if (car == null) {
            	System.out.println("第一次，为null");
                car = new Car(1,"法拉利","红色");
            }
        }
		return car;
	}
	
	public static void main(String[] args) {
		for(int i = 0; i < 50; i ++) {
			Thread t = new Thread() {
				public void run() {
					Car car = Car.getInstance();
					System.out.println(this.getName()+"---"+car);
				};
			};
			t.setName("thread-"+i);
			t.start();
		}
		
//		String msg = "年龄：%d\n";
//		System.out.printf(msg,1);
//		PrintStream printf = System.out.printf("年龄：%d\n",1);
//		
//		System.out.println();
//		byte  b1 = 3,b2 = 4,b;
//		b = b1+b2;
		
	}
	
	
	
	
}	
