package inter;

import java.awt.Color;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.TransferHandler;
import javax.swing.text.DefaultCaret;

/**
 * 
 * @author asus
 *聊天组件
 */
public class InterChat {
	private JTextArea jTextArea1;   //显示聊天记录
	private JScrollPane jScrollPane1;//文本组件
	private JTextField jTextField3; //输入信息框
	public Boolean isFile = false;
	public String msg = "sendMsg";
	public File file;
	private static InterChat chat;
	
	/**
	 * 当联机时，聊天框解禁，并初始化
	 */
	public void setOnline() {
		jTextArea1.setForeground(Color.BLACK);
		jTextArea1.setText("");
		jTextField3.setEnabled(true);
		jTextField3.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					if(getText() == null || getText().equals(""))return;
					if(Client.isExist()) {
						Client client = Client.getClient();
						client.sendSever("sendMsg,"+getText());
					}else if(Servlet.isExist()) {
						Servlet servlet = Servlet.getServlet();
						servlet.sendClient("sendMsg,"+getText());
					}
					sendToText();
				}
			}
		});
		jTextField3.setTransferHandler(new TransferHandler() //可以发送文件
        {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean importData(JComponent comp, Transferable t) {
                try {
                    Object o = t.getTransferData(DataFlavor.javaFileListFlavor);
 
                    String filepath = o.toString();
                    if (filepath.startsWith("[")) {
                        filepath = filepath.substring(1);
                    }
                    if (filepath.endsWith("]")) {
                        filepath = filepath.substring(0, filepath.length() - 1);
                    }
                    System.out.println(filepath);
                    InterChat temp = InterChat.getInstance();
                    file = new File(filepath);
                    jTextField3.setText(filepath.substring(filepath.lastIndexOf("\\")+1));
                    temp.isFile = true;
                    temp.msg = "sendFile";
                    return true;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
            @Override
            public boolean canImport(JComponent comp, DataFlavor[] flavors) {
                for (int i = 0; i < flavors.length; i++) {
                    if (DataFlavor.javaFileListFlavor.equals(flavors[i])) {
                        return true;
                    }
                }
                return false;
            }
        });
	}
	
	/**
	 * 启动应用初始化聊天框
	 */
	public InterChat() {
		jTextArea1 = new JTextArea(); 
		jScrollPane1 = new JScrollPane();
		jTextField3 = new JTextField();
		jScrollPane1.setBounds(400, 680, 290, 90);
		jTextField3.setBounds(170, 690, 200, 30);
		jScrollPane1.getViewport().add(jTextArea1);
		DefaultCaret caret = (DefaultCaret)jTextArea1.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		setNoLine();
	}
	
	/**
	 * 将聊天框添加进模块
	 * @param jframe
	 */
	public void addToModel(JFrame jframe) {
		jframe.add(jScrollPane1);
		jframe.add(jTextField3);
	}
	
	/**
	 * 往聊天框里
	 * @param text
	 */
	public void append(String text) {
		jTextArea1.append(text);
	}
	
	public String getText() {
		return jTextField3.getText().trim();
	}
	
	public void clearText() {
		jTextField3.setText("");
	}
	
	public void setNoLine() {
		jTextField3.setEnabled(false);
		jTextArea1.setEditable(false);
		jTextArea1.setForeground(Color.RED);
		append("- - - - - -未进行联机- - - - - - -");
	}
	
	public void refresh() {
		jTextArea1.setText(jTextArea1.getText());
		jTextField3.setText(jTextField3.getText());
	}
	
	public void sendToText() {
		String text = jTextField3.getText();
		clearText();
		text = "me : "+text+"\n";
		append(text);
	}
	
	public void acceptToText(String text) {
		text = "her : "+text+"\n";
		append(text);
		clearText();
	}
	
	public static InterChat getInstance() {
		if(chat == null) {
			chat = new InterChat();
		}
		return chat;
	}
	
}
