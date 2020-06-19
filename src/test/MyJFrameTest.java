package test;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MyJFrameTest extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {
		MyJFrameTest myJFrameTest = new MyJFrameTest();
	}
	
	public MyJFrameTest(){
		this.setSize(400,200);
		this.setTitle("五子棋                                                 你的ip：");
		this.setResizable(false);
		this.setLayout(null);
        JButton button1=new JButton ("上");
        JButton button3=new JButton("中");
        JButton button4=new JButton("右");
        JButton button5=new JButton("下");
        button1.setBounds(50, 50, 50, 50);
        button3.setBounds(50, 50, 100, 100);
//        button4.setBounds(50, 50, 100, 100);
//        button5.setBounds(50, 50, 100, 100);
        
        this.add(button1);
        this.add(button3);
//        this.add(button4);
//        this.add(button5);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
//		this.setSize(400,200);
//        this.setLayout(new BorderLayout());    //为Frame窗口设置布局为BorderLayout
//        JButton button1=new JButton ("上");
//        JButton button3=new JButton("中");
//        JButton button4=new JButton("右");
//        JButton button5=new JButton("下");
//        JPanel jp1 = new JPanel();
//        JPanel jp2 = new JPanel();
//        JPanel jp3 = new JPanel();
//        JPanel jp4 = new JPanel();
//        jp3.setLayout(new GridLayout(4,1));
//        jp3.add(button1);
//        jp3.add(button3);
//        jp3.add(button4);
//        jp3.add(button5);
//        this.add(jp1,BorderLayout.NORTH);
//        this.add(jp2,BorderLayout.CENTER);
//        this.add(jp3,BorderLayout.EAST);
//        this.add(jp4,BorderLayout.SOUTH);
//        this.setBounds(300,200,600,300);
//        this.setVisible(true);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.repaint();
	}
	
	@Override
	public void paint(Graphics g) {
//		Graphics2D cg = (Graphics2D)g;
//		g.drawLine(20, 20, 120, 120);
		// TODO Auto-generated method stub
		super.paintComponents(g);
		this.paintChildr(g);
	}
	
	private void paintChildr(Graphics g) {
		// TODO Auto-generated method stub
		g.drawLine(20, 20, 120, 120);
	}
	


}
