package test;

public class CarTest {
	public static void main(String[] args) {
		//  null不是对象，是一个特殊的值
		//  valueOf  装箱
		//  booleanValue  拆箱
//		boolean a = null != null ? true : null;
		/**
		 * 解：boolean a = null;
		 * 用基本数据类型接收引用数据类型，自动拆箱，null.booleanValue  报空指针
		 * 
		 */
		
		Boolean a1 = null != null ? true : null;
		/**
		 * 解：Boolean a1 = null;
		 * 用引用类型接收引用类型，无拆装箱，无报错
		 */
		
		Boolean b = null;
		Boolean b1 = null == null ? b : true;
		
		

		
	}
}
