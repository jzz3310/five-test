package test;

public class Car {
	private int id;
	private String name;
	private String color;
	
	private static volatile Car car = null;

	private Car(int id, String name, String color) {
		this.id = id;
		this.name = name;
		this.color = color;
	}
	
	public static Car getInstance() {
		if(car == null) {
			synchronized(Car.class) {
				if (car == null) {
					System.out.println("第一次，为null");
					car = new Car(1,"法拉利","红色");
				}
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
	}
}
