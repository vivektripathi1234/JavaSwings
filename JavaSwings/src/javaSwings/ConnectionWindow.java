/**
 * 
 */
package javaSwings;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * @author Vivek
 *
 */
public class ConnectionWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Socket cSock;
	
	private DataInputStream dataIn;
	private DataOutputStream dataOut;
	
	private JFrame jf;
	private JTextField tf;
	private JTextArea ta;
	private JButton btn;
	
	/**
	 * 
	 */
	private void initializeUI() {
		jf = new JFrame();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		tf = new JTextField();
		tf.setBounds(10, 250, 200, 40);
		jf.getContentPane().add(tf);
		
		ta = new JTextArea(250,250);
		ta.setBounds(10, 10, 250, 240);
		ta.setEditable(false);
		jf.getContentPane().add(ta);
		
		btn = new JButton("send");
		btn.setBounds(240, 250, 80, 30);
		jf.getContentPane().add(btn);
		
		jf.setSize(350, 350);
		jf.setLayout(null);
		jf.setVisible(true);
	}
	
	/**
	 * 
	 */
	private void connect() {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String readString = "";
				
				try {
					dataIn = new DataInputStream(cSock.getInputStream());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				while(!readString.equals("stop")){
					try {
						readString = dataIn.readUTF();
						String serverString = cSock.getRemoteSocketAddress()+"--> ";
						serverString = serverString.concat(readString);
						ta.append(serverString);
						ta.append("\n");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
		
		try {
			dataOut = new DataOutputStream(cSock.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(this, e.toString());
			e.printStackTrace();
		}
		
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String you;
				
				you = tf.getText();
				
				try {
					dataOut.writeUTF(you);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				you = "You--> "+you;
				
				tf.setText(null);
				ta.append(you);
				ta.append("\n");
			
			}
		});
		
	}

	/**
	 * @throws HeadlessException
	 */
	public ConnectionWindow(Socket cSock) throws HeadlessException {
		// TODO Auto-generated constructor stub
		this.cSock = cSock;
		
		initializeUI();
		connect();
	}

}
