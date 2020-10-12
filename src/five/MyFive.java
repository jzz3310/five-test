package five;

import javax.swing.JOptionPane;

public class MyFive {
	public static void main(String[] args) {
		FiveGame fiveGame = new FiveGame();

		JOptionPane.showMessageDialog(fiveGame, "欢迎打开五子棋");//设置一个对话框，在哪个组件中弹出，以及提示消息
//		int result = JOptionPane.showConfirmDialog(mjf, "现在开始游戏吗？");//一个确认框   0：是  1：否  2：取消
//		switch (result) {
//			case 0:
//				JOptionPane.showMessageDialog(mjf, "游戏开始");
//				break;
//			case 1:
//				JOptionPane.showMessageDialog(mjf, "游戏结束");
//				break;
//			case 2:
//				JOptionPane.showMessageDialog(mjf, "");
//				break;
//		}
		
		
//		String name = null;
//		do{
//			name = JOptionPane.showInputDialog("请输入你的姓名：");//显示一个输入框，来保存用户的信息
//			if(name == null || name.equals("")){
//				JOptionPane.showMessageDialog(mjf, "您没有输入姓名，请重新输入");
//			}
//		}while(name == null || name.equals(""));
		
	}
}
